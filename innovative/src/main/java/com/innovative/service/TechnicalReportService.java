package com.innovative.service;


import com.innovative.bean.LoggerUser;
import com.innovative.bean.Sections;
import com.innovative.bean.TechnicalReport;
import com.innovative.bean.User;
import com.innovative.dao.*;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;

import org.apache.commons.lang3.StringUtils;
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
public class TechnicalReportService {
    @Autowired
    private TechnicalReportDao technicalReportDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private IntegralService integralService;
    @Autowired
    private SectionsService sectionsService;
    @Autowired
    private LoggerUserDao loggerUserDao;
    @Autowired
    UserService userService;
    @Autowired
    private TransportClient client;

    /**
     * 根据id获取技术报告
     *
     * @param id 技术报告id
     * @return
     */
    public TechnicalReport getTechnicalReportById(String id) {

    	TechnicalReport technicalReport = technicalReportDao.getTechnicalReportById(id);
		if(technicalReport!=null){
		   List<String> url = fileDao.getPhotoByMOdAndId(id, "reportPhoto");
		   if(url != null && url.size() > 0 )
			   technicalReport.setPictures(url.get(0));
		   User user = userService.getUser(technicalReport.getCreatedBy());
 		  if(user!=null){
  			integralService.managerIntegral(5, technicalReport.getCreatedBy(), technicalReport.getId());
 		  }
		}
		return technicalReport;
    }

    /**
     * 分页条件查询技术报告list
     *
     * @param pageNum  页码
     * @param sectors 
     * @param keyword 
     * @return
     */
    public Map<String, Object> getTechnicalReportListByCondition(int pageNum, String sectors, String keyword) {


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
        List<TechnicalReport> items = technicalReportDao.getTechnicalReportListByCondition(offset, limit,sectors,key1,key2);
        for(TechnicalReport te : items){
        	if(null == te || "".equals(te.getId()))
        		continue;
        	  List<String> url = fileDao.getPhotoByMOdAndId(te.getId(), "reportPhoto");
   		   if(url != null && url.size() > 0 )
   			   te.setPictures(url.get(0));
        }
        int totalCount = technicalReportDao.getCountByCondition(sectors,key1,key2);

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
     * 新增技术报告,同时将信息添加到科技专栏表中。
     *
     * @param technicalReport 参数集合
     * @return
     */
    @Transactional
    public boolean insertTechnicalReport(TechnicalReport technicalReport) {

        int result = technicalReportDao.insertTechnicalReport(technicalReport);

        //增加成功
        if(result>0){
            //添加到科技专栏
            Sections sections=addSectionByTechnicalReport(technicalReport);
            //修改逻辑不把技术报告添加到科技专栏的表里我们单独只增加索引库
            //sectionsService.addSection(sections);
            //设置为审批通过
           // String sectionId=sectionsService.getIdByFirstId(technicalReport.getId(),"1");
            sections.setState("1");
            //sections.setId(sectionId);
            //sectionsService.updateSection(sections);
           //添加到es索引库
            
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
            integralService.managerIntegral(10, technicalReport.getCreatedBy(), technicalReport.getId());
           //添加日志
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","技术报告",technicalReport.getId(),technicalReport.getName());
            loggerUserDao.addLog(loggerUser);
        }

        return fileDao.updateFile(technicalReport.getId());
    }

    /**
     * 修改技术报告信息
     *
     * @param technicalReport 参数集合
     * @return
     */
    @Transactional
    public boolean updateTechnicalReport(TechnicalReport technicalReport) {
        //修改技术报告
        int result = technicalReportDao.updateTechnicalReport(technicalReport);
        //删除之前的文件
        fileDao.deleteFiles(technicalReport.getId(),"reportFile");
        //新增新的文件
        fileDao.updateFile(technicalReport.getId());

        if (result>0){
            //查询技术报告修改后的内容
            TechnicalReport technicalReport1=technicalReportDao.getTechnicalReportById(technicalReport.getId());

            //从科技专栏表中查找信息是否存在，若存在再修改、若不存在则添加到科技专栏表
            /*String sectionsId=sectionsService.getIdByFirstId(technicalReport.getId(),"1");
            if (sectionsId!=null&&!("".equals(sectionsId))){
                //修改科技专栏
                Sections sections=addSectionByTechnicalReport(technicalReport1);
                sections.setId(sectionsId);
                sectionsService.updateSection(sections);
            }else {
                Sections sections=addSectionByTechnicalReport(technicalReport1);
                sectionsService.addSection(sections);
            }*/
            //添加技术报告到索引库
        	String id=technicalReport1.getId();
			//根据id从索引库删除科技专栏
			client.prepareDelete("sections_index","sections",id).get();
			//再次添加到索引库
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				client.prepareIndex("sections_index","sections",id).setSource(
						XContentFactory.jsonBuilder().startObject()
						.field("id",id)
						.field("resource","内部资源/技术报告")
						.field("title",technicalReport1.getName())
						.field("resume",technicalReport1.getSummary())
						.field("sectors",technicalReport1.getSectors())
						.field("tags",technicalReport1.getTags())
						.field("cotent",technicalReport1.getContent())
						.field("createAt",technicalReport1.getCreatedAt())//sdf.format(section2.getCreateAt())
						.field("createBy",technicalReport1.getCreatedBy())
						.field("updateAt",technicalReport1.getUpdatedAt())//sdf.format(section2.getUpdateAt())
						.field("updateBy",technicalReport1.getUpdatedBy())
						.field("state","1")
						.field("imgUrl",technicalReport1.getPictures())
						.endObject())
						.get();
			} catch (IOException e) {
				e.printStackTrace();
			}

            //添加日志
            LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","技术报告",technicalReport.getId(),technicalReport.getName());
            loggerUserDao.addLog(loggerUser);
        }

        return result > 0 ;
    }

    @Transactional
	public boolean deleteTechnicalReport(String id) {
		if(null == id || "".equals(id)){
			return false;
		}
		//删除上传的附件
		fileDao.deleteFile(id);

		/*//查出关联的科技资讯的id
        String sectionId=sectionsService.getIdByFirstId(id,"1");
        //若是存在，再删除
        if (sectionId!=null&&!("".equals(sectionId))){
            //删除科技专栏
            sectionsService.deleteSection(sectionId);
        }*/
      
        //添加日志
        LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"删除","技术报告",id,technicalReportDao.getTechnicalReportById(id).getName());
        loggerUserDao.addLog(loggerUser);
        boolean flag = technicalReportDao.deleteTechnicalReport(id);
        //删除索引库
        if (flag) {
			@SuppressWarnings("unused")
			DeleteResponse response=client.prepareDelete("sections_index","sections",id).get();
		}
		return flag;
	}

    /**
     把技术报告信息封装到技术资讯实体中,注意：此处没有设置id
     */
	private Sections addSectionByTechnicalReport(TechnicalReport technicalReport){
        Sections sections=new Sections();
        sections.setTitle(technicalReport.getName());
        sections.setResource("内部资源/技术报告");
        sections.setTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        sections.setSectors(technicalReport.getSectors());
        sections.setTags(technicalReport.getTags());
        sections.setResume(technicalReport.getSummary());
        sections.setCotent(technicalReport.getContent());
        sections.setCreateBy(technicalReport.getCreatedBy());
        sections.setImgid(technicalReport.getPictures());
        sections.setId(technicalReport.getId());
        sections.setType("1");

        return sections;
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
}
