package com.innovative.service;


import com.alibaba.druid.util.StringUtils;
import com.innovative.bean.LoggerUser;
import com.innovative.bean.Sections;
import com.innovative.bean.Solution;
import com.innovative.bean.User;
import com.innovative.dao.*;
import com.innovative.utils.PageInfo;

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolutionService {

    @Autowired
    private SolutionDao solutionDao;
    @Autowired
    FileDao fileDao;
    @Autowired
    IntegralService integralService;
    @Autowired
    private SectionsService sectionsService;
    @Autowired
    private LoggerUserDao loggerUserDao;
    @Autowired
    UserService userService;
    @Autowired
    private TransportClient client;

    /**
     * 根据id获取方案
     *
     * @param id 方案id
     * @return
     */
    public Solution getSolutionById(String id) {

    	Solution solution = solutionDao.getSolutionById(id);
		if(solution!=null){
			//文件图片我们都改到一张专门的表来存储
		   List<String> urllist = fileDao.getPhotoByMOdAndId(id, "programPhoto");
		   if(urllist != null && urllist.size() > 0 )
			   solution.setPictures(urllist.get(0));
		   User user = userService.getUser(solution.getCreatedBy());
 		  if(user!=null){
  			integralService.managerIntegral(5, solution.getCreatedBy(), solution.getId());
 		  }
		}
		return solution;

    }

    /**
     * 分页条件查询方案list
     *
     * @param pageNum  页码
     * @param sectors 
     * @param keyword 
     * @return
     */
    public Map<String, Object> getSolutionListByCondition(int pageNum, String sectors, String keyword) {


    	  if (!StringUtils.isEmpty(sectors)) {
              sectors = "{" + sectors + "}";
          }
    	 String key1 = "%" + keyword + "%".trim();
 	     String key2 = "{" + keyword + "}".trim();

        //获取分页信息
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        int offset = pageInfo.getStartIndex();
        int limit = pageInfo.getPageSize();

        //分页条件查询
        List<Solution> items = solutionDao.getSolutionListByCondition( offset, limit,sectors,key1,key2);
        for(Solution so : items){
        	if(null == so|| "".equals(so.getId()))
        		continue;
        	 List<String> urllist = fileDao.getPhotoByMOdAndId(so.getId(), "programPhoto");
  		   if(urllist != null && urllist.size() > 0 )
  			   so.setPictures(urllist.get(0));
        }
        int totalCount = solutionDao.getCountByCondition(sectors,key1,key2);

        //数据组装
        Map<String, Object> map = new HashMap<>();
        map.put("items", items);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());

        return map;

    }

    /**
     * 新增技术报告,同时将信息添加到科技专栏
     *
     * @param solution 参数集合
     * @return
     */
    @Transactional
    public boolean insertSolution(Solution solution) {

        int result = solutionDao.insertSolution(solution);
        //增加成功
        if(result>0){
            //添加到科技专栏
            Sections sections=tranSolutionToSection(solution);
            //此处修改是把我们把之前的添加科技专栏与添加方案两个表添加问题
            //sectionsService.addSection(sections);
            //设置为审批通过
            //String sectionId=sectionsService.getIdByFirstId(solution.getId(),"2");
            sections.setState("1");
           // sections.setId(sectionId);
            //sectionsService.updateSection(sections);
            //添加到科技专栏索引库
           //判断索引库是否已经存在
			IndicesExistsResponse  indicesExistsResponse = client.admin().indices().exists( 
                    new IndicesExistsRequest().indices(new String[]{"sections_index"})).actionGet();
			boolean is_exist=indicesExistsResponse.isExists();
			//若索引不存在，则新建索引
			if (!is_exist) {
				//生成索引的映射信息
    			XContentBuilder mapping=mapping();
    			PutMappingRequest putMappingRequest=Requests.putMappingRequest("sections_index").type("sections").source(mapping);
    			//创建索引
    			client.admin().indices().prepareCreate("sections_index").execute().actionGet();
    			//为索引添加映射
    			client.admin().indices().putMapping(putMappingRequest).actionGet();
			}
			//向索引库中添加信息
			XContentBuilder builder=addSections2(sections);
			@SuppressWarnings("unused")
			IndexResponse response=client.prepareIndex("sections_index","sections",sections.getId()).setSource(builder).get();
            
            //添加积分
            integralService.managerIntegral(11, solution.getCreatedBy(), solution.getId());
        	//添加日志
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","方案",solution.getId(),solution.getName());
        	loggerUserDao.addLog(loggerUser);
        }
		 return fileDao.updateFile(solution.getId());
    }

    /**
     * 修改技术报告信息
     * @param solution 参数集合
     * @return
     */
    @Transactional
    public boolean updateSolution(Solution solution) {
        //删除之前的文件
        fileDao.deleteFiles(solution.getId(),"programFile");
        //新增新的文件
    	fileDao.updateFile(solution.getId());
        int result = solutionDao.updateSolution(solution);

        //若技术方案修改成功，再修改科技专栏
        if (result>0){
            /*//查询修改后的方案信息
            Solution solution1=solutionDao.getSolutionById(solution.getId());
            //查询对应的科技专栏id
            String sectionId=sectionsService.getIdByFirstId(solution.getId(),"2");

            if (sectionId!=null&&!("".equals(sectionId))){
                //修改科技专栏
                System.err.print("xiugai"+solution1);
                Sections sections=tranSolutionToSection(solution1);
                sections.setId(sectionId);
                sectionsService.updateSection(sections);
            }else {
                //添加科技资讯
                Sections sections=tranSolutionToSection(solution1);
                sectionsService.addSection(sections);
                //设置为审批通过
                sections.setState("1");
                sections.setId(sectionId);
                sectionsService.updateSection(sections);
            }*/
        	String id=solution.getId();
			//根据id从索引库删除科技专栏
			client.prepareDelete("sections_index","sections",id).get();
			//再次添加到索引库
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				client.prepareIndex("sections_index","sections",id).setSource(
						XContentFactory.jsonBuilder().startObject()
						.field("id",id)
						.field("resource","内部资源/方案")
						.field("title",solution.getName())
						.field("resume",solution.getSummary())
						.field("sectors",solution.getSectors())
						.field("tags",solution.getTags())
						.field("cotent",solution.getContent())
						.field("createAt",solution.getCreatedAt())//sdf.format(section2.getCreateAt())
						.field("createBy",solution.getCreatedBy())
						.field("updateAt",solution.getUpdatedAt())//sdf.format(section2.getUpdateAt())
						.field("updateBy",solution.getUpdatedBy())
						.field("state","1")
						.field("imgUrl",solution.getPictures())
						.endObject())
						.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//添加日志
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","方案",solution.getId(),solution.getName());
            loggerUserDao.addLog(loggerUser);
        }

        return result > 0 ;
    }

    @Transactional
	public boolean deleteSolution(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);

        //删除科技专栏
       /* String sectionid=sectionsService.getIdByFirstId(id,"2");
        if (sectionid!=null&&!("".equals(sectionid))){
            sectionsService.deleteSection(sectionid);
        }*/
		
        LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"删除","方案",id,solutionDao.getSolutionById(id).getName());
        loggerUserDao.addLog(loggerUser);
        boolean flag = solutionDao.deleteSolution(id);
        //删除索引库
        if (flag) {
			@SuppressWarnings("unused")
			DeleteResponse response=client.prepareDelete("sections_index","sections",id).get();
		}
		return flag;
	}

    /**
     * 把技术方案信息封装到科技专栏实体
     * @param solution
     * @return
     */
    private Sections tranSolutionToSection(Solution solution){
        Sections sections=new Sections();
        sections.setTitle(solution.getName());
        sections.setResource("内部资源/方案");
        sections.setTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        sections.setSectors(solution.getSectors());
        sections.setTags(solution.getTags());
        sections.setResume(solution.getSummary());
        sections.setCotent(solution.getContent());
        sections.setCreateBy(solution.getCreatedBy());
        //sections.setImgid(solution.getPictures());
        sections.setId(solution.getId());
        //sections.setType("2");

        return  sections;
    }
    
    /**
     * 添加科技资讯信息到索引库
     * @param sections
     * @return
     */
    private XContentBuilder addSections2(Sections sections){
    	XContentBuilder builder=null;
    	String nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	try {
    		builder=XContentFactory.jsonBuilder().startObject()
					.field("id",sections.getId())
					.field("resource",sections.getResource())
					.field("title",sections.getTitle())
					.field("time",sections.getTime())
					.field("resume",sections.getResume())
					.field("sectors",sections.getSectors())
					.field("tags",sections.getTags())
					.field("cotent",sections.getCotent())
					.field("createAt",nowTime)
					.field("createBy",sections.getCreateBy())
					.field("updateAt",nowTime)
					.field("updateBy",sections.getUpdateBy())
					.field("state","0")
					.field("imgUrl",sections.getImgUrl())
					.field("count",sections.getCount())
					.field("firstid",sections.getFirstid())
					.field("type",sections.getType())
					.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return builder;
    }
    /**
     * 指定科技专栏的索引库映射
     * @return
     */
    private XContentBuilder mapping(){
    	XContentBuilder builder=null;
    	try{
    		builder=XContentFactory.jsonBuilder().startObject()
    				//索引类型为information
    				.startObject("sections")
    					//指定属性
    					.startObject("properties")
    						//科技资讯的id
    						.startObject("id")
    							.field("type","text")
    						.endObject()
    						//来源
    						.startObject("resource")
    							.field("type","text")
    							.field("analyzer","ik_max_word")
    						.endObject()
    						//主题
    						.startObject("title")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//时间
    						.startObject("time")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//摘要
    						.startObject("resume")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//行业领域
    						.startObject("sectors")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//标签
    						.startObject("tags")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//内容
    						.startObject("cotent")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//创建时间
    						.startObject("createAt")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//创建人
    						.startObject("createBy")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//修改时间
    						.startObject("updateAt")
    							.field("type","text")
    							.field("analyzer","ik_max_word")
    						.endObject()
    						//修改人
    						.startObject("updateBy")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//审批状态,0是未审批,1是已经审批
    						.startObject("state")
    							.field("type","text")
    						.endObject()
    						//图片地址
    						.startObject("imgUrl")
    							.field("type","text")
    						.endObject()
    						//阅读量
    						.startObject()
    							.field("count","text")
    						.endObject()
							//firstid
							.startObject()
								.field("firstid","text")
							.endObject()
							//type 1 技术报告 2 方案
							.startObject()
								.field("type","text")
							.endObject()
    					.endObject()
    				.endObject()
    			.endObject();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return builder;
    }
}
