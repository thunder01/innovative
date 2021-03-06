package com.innovative.service;

import com.google.common.collect.Lists;
import com.innovative.bean.*;
import com.innovative.dao.*;
import com.innovative.utils.Config;
import com.innovative.utils.JsonResult;
import com.innovative.utils.Misc;
import com.innovative.utils.PageInfo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 科技专栏
 * @author cj
 *
 */
@Service
public class SectionsService {

    @Autowired
    private SectionsDao sectionsDao;
    @Autowired
    private TransportClient client;
    @Autowired
    MessageService messageService;
    @Autowired
    TechSectionsApprouverDao techSectionsApprouverDao;
    @Autowired
    TechSectionsCollectionDao techSectionsCollectionDao;
    @Autowired
    FileDao fileDao;
    @Autowired
	LoggerUserDao loggerUserDao;
/**
 * 添加科技专栏
 * @param sections
 * @return
 */
	@Transactional
	public boolean addSection(Sections sections) {
		//根据图片的id查出图片的URL
		String imgId=sections.getImgid();
		//更新图片状态为add
		fileDao.updateFile(imgId);
		List<FileBean> listFile=fileDao.getFileById(imgId, "sections");
		if (!listFile.isEmpty()) {			
			sections.setImgUrl(listFile.get(0).getUrl());
		}
		
		// 先往数据库中添加科技专栏信息
		sections.setId(Misc.base58Uuid());
		boolean flag=sectionsDao.addSection(sections);
		//若成功，再向索引库添加数据
		if (flag){
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
		}

		LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"上传","科技专题",sections.getId(),sections.getTitle());
		loggerUserDao.addLog(loggerUser);

		return flag;
	}
/**
 * 修改科技专栏
 * @param sections
 * @return
 */
	@Transactional
	public boolean updateSection(Sections sections) {
		Sections sectionOld = sectionsDao.getSectionById(sections.getId());
		// 先修改数据库
		boolean flag=sectionsDao.updateSection(sections);
		
		//成功之后，再修改索引库
		if (flag) {
			String id=sections.getId();
			//根据id从索引库删除科技专栏
			client.prepareDelete("sections_index","sections",id).get();
			//从数据库中查出更新后的数据
			Sections section2=sectionsDao.getSectionById(id);
			//再次添加到索引库
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				client.prepareIndex("sections_index","sections",id).setSource(
						XContentFactory.jsonBuilder().startObject()
						.field("id",id)
						.field("resource",section2.getResource())
						.field("title",section2.getTitle())
						.field("time",section2.getTime())
						.field("resume",section2.getResume())
						.field("sectors",section2.getSectors())
						.field("tags",section2.getTags())
						.field("cotent",section2.getCotent())
						.field("createAt",section2.getCreateAt())//sdf.format(section2.getCreateAt())
						.field("createBy",section2.getCreateBy())
						.field("updateAt",section2.getUpdateAt())//sdf.format(section2.getUpdateAt())
						.field("updateBy",section2.getUpdateBy())
						.field("state",section2.getState())
						.field("imgUrl",section2.getImgUrl())
						.field("count",section2.getCount())
						.field("firstid",section2.getFirstid())
						.field("type",section2.getType())
						.endObject())
						.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//如果 科技专栏的发布者与修改人不同则  给创建人发消息
			if(null!=sections.getUpdateBy() &&  !sections.getUpdateBy().equals(sectionOld.getCreateBy())){
				//审核状态被修改
				if(sections.getState()!=null &&(!sections.getState().endsWith(sectionOld.getState()))){
					//增加消息推送（科技专栏审核）
					 messageService.insertMessage(sectionOld.getCreateBy(), sectionOld.getId(), Config.KJ_ZL_SH, 1);
					 messageService.updateMsgCount(sectionOld.getCreateBy());
				}else{
					//增加消息推送(科技专栏修改)
					 messageService.insertMessage(sectionOld.getCreateBy(), sectionOld.getId(), Config.KJ_ZL_XG, 1);
					 messageService.updateMsgCount(sectionOld.getCreateBy());
				}	
			}		
		}

		LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"修改","科技专题",sections.getId(),sections.getTitle());
		loggerUserDao.addLog(loggerUser);
		return flag;
	}
	/**
	 * 根据id查询科技专栏
	 * @param id
	 * @return
	 */
	public Sections getSectionById(String id) {
		Sections sections=sectionsDao.getSectionById(id);
		if (sections!=null) {
			//阅读量+1
			int count=sections.getCount();
			sections.setCount(count+1);
			sectionsDao.countUpdate(sections);
			
			try {
				client.prepareUpdate().setDoc(XContentFactory.jsonBuilder()
						.startObject()
							.field("count",count+1)
						.endObject()
						).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//再查其点赞数
			Integer approuverNum=techSectionsApprouverDao.getTotalApprouver(id);
			if (approuverNum==null) {
				approuverNum=0;
			}
			sections.setApprouverNum(String.valueOf(approuverNum));
		}
		return sectionsDao.getSectionById(id);
	}
	/**
	 * 根据id查询科技专栏
	 * @param id
	 * @return
	 */
	public Sections getSectionByIdAndUserid(String id,String userid) {
		// TODO Auto-generated method stub
		return sectionsDao.getSectionByIdAndUserid(id, userid);
	}
	/**
	 * 查询科技专栏列表
	 * @param page
	 * @param state 1是后台（全部显示） 2是前台（只显示审核通过的）
	 * @return
	 */
	public  Map<String, Object> getSectionLists(Integer page, String state) {
		 PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       List<Sections> sections = sectionsDao.getSectionsLists( pageInfo.getStartIndex(), pageInfo.getPageSize(),state);
	       int totalCount = sectionsDao.getTotalCountNum(state);
		
	        Map<String, Object> map = new HashMap<>();
	        map.put("items", sections);
	        map.put("totalCount", totalCount);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
	        return map;
	}
	/**
	 * 删除科技专栏
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean deleteSection(String id) {
		LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"删除","科技专题",id,sectionsDao.getSectionById(id).getTitle());
		loggerUserDao.addLog(loggerUser);

		// 先从数据库中删除
		boolean flag=sectionsDao.deleteSection(id);
		if (flag) {
			@SuppressWarnings("unused")
			DeleteResponse response=client.prepareDelete("sections_index","sections",id).get();
		}
		return flag;
	}
	
	/**
	 * 根据关键字搜索（elastic search）
	 * @param key
	 * @return
	 */
	public JsonResult queryByKey(String key,Integer page){
		PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPageNum(page);
		
		List<Map> listSections = Lists.newArrayList();
		SearchRequestBuilder qBuilder=client.prepareSearch("sections_index").setTypes("sections");
		BoolQueryBuilder builder=QueryBuilders.boolQuery()
				.must(QueryBuilders.matchQuery("state","1"))
				.should(QueryBuilders.matchQuery("title",key))
				.should(QueryBuilders.matchQuery("sectors",key))
				.should(QueryBuilders.matchQuery("tags",key))
				.should(QueryBuilders.matchQuery("resume",key))
				.should(QueryBuilders.matchQuery("cotent",key))
				//通配符再查
				.should(QueryBuilders.wildcardQuery("title","*"+key+"*"))
				.should(QueryBuilders.wildcardQuery("sectors","*"+key+"*"))
				.should(QueryBuilders.wildcardQuery("tags","*"+key+"*"))
				.should(QueryBuilders.wildcardQuery("resume","*"+key+"*"))
				.should(QueryBuilders.wildcardQuery("cotent","*"+key+"*"));
		//结果分页
		qBuilder.setQuery(builder).setFrom(0).setSize(10);
		//发出查询请求
		SearchResponse response = qBuilder.execute().actionGet();
		
		SearchHits hits = response.getHits();
		for(SearchHit hit:hits){
			listSections.add(hit.getSource());
		}
		
		//不分页再查一次
		qBuilder.setQuery(builder);
		SearchResponse response2 = qBuilder.execute().actionGet();
		SearchHits hits2 = response.getHits();
		
		Map<String, Object> map = new HashMap<>();
        map.put("items", listSections);
        map.put("totalCount", hits2.totalHits);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		return new JsonResult(true, map);
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
    /**
     * 给科技专栏增加点赞
     * @param techSectionsApprouver
     * @return
     */
    @Transactional
	public Sections addApprouver(TechSectionsApprouver techSectionsApprouver) {
		int todayIsApprouver = techSectionsApprouverDao.isTodayApprouverTechInfornaion(techSectionsApprouver.getApprouverBy(), techSectionsApprouver.getSectionId());
		if(todayIsApprouver == 0){
			//增加点赞记录
			techSectionsApprouverDao.insertTechApprouver(techSectionsApprouver);
			//点赞次数加1
			sectionsDao.updateSectionApprouverNum(techSectionsApprouver.getSectionId());

			LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"点赞","科技专题",techSectionsApprouver.getSectionId(),sectionsDao.getSectionById(techSectionsApprouver.getSectionId()).getTitle());
			loggerUserDao.addLog(loggerUser);

			return sectionsDao.getSectionByIdAndUserid(techSectionsApprouver.getSectionId(),techSectionsApprouver.getApprouverBy()) ;  
		}else{
			return null;
		}
		
	}
	/**
	 * 收藏科技专栏
	 * @param techSectionsCollection
	 * @return
	 */
	@Transactional
	public boolean collectionTechSection(TechSectionsCollection techSectionsCollection) {
		int isCollection = techSectionsCollectionDao.isCollectionSections(techSectionsCollection.getCollectBy(), techSectionsCollection.getSectionId());
		if(isCollection == 0){
			Sections sections = sectionsDao.getSectionById(techSectionsCollection.getSectionId());
			messageService.insertMessage(sections.getCreateBy(), techSectionsCollection.getId(), Config.KJ_ZL_SSH, 1);
			messageService.updateMsgCount(sections.getCreateBy());

			LoggerUser loggerUser=new LoggerUser(MDC.get("userid"),"收藏","科技专题",techSectionsCollection.getSectionId(),sectionsDao.getSectionById(techSectionsCollection.getSectionId()).getTitle());
			loggerUserDao.addLog(loggerUser);

			return techSectionsCollectionDao.insertTechSectionsCollection(techSectionsCollection);
		}else{
			return false;
		}
	}

	/**
	 * 根据技术报告方案（一期）的id查询科技专栏的id
	 * @param firstid
	 * @return
	 */
	String getIdByFirstId(String firstid,String type){
		return sectionsDao.getIdByFirstId(firstid,type);
	}

}
