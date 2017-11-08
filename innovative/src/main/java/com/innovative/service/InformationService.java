package com.innovative.service;


import com.google.common.collect.Lists;
import com.innovative.bean.FileBean;
import com.innovative.bean.Information;
import com.innovative.bean.Sections;
import com.innovative.bean.TechInformationApprouver;
import com.innovative.bean.TechInformationCollection;
import com.innovative.dao.FileDao;
import com.innovative.dao.InformationDao;
import com.innovative.dao.SectionsDao;
import com.innovative.dao.TechInformationApprouverDao;
import com.innovative.dao.TechInformationCollectionDao;
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
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 科技资讯,添加了elastic search搜索;
 * 向数据库添加数据时,要同时创建索引,进行删除和修改操作时,索引库也要进行相应的操作,
 * 使数据库中的数据与索引库保持一致。
 * @author cj
 *
 */
@Service
public class InformationService {

    @Autowired
    private InformationDao informationDao;
    @Autowired
    private SectionsDao sectionsDao;
    //Elasticsearch客户端
    @Autowired
    private TransportClient client;
    @Autowired
    private MessageService messageService;
    //科技资讯点赞
    @Autowired
    private TechInformationApprouverDao techInformationApprouverDao;
    //科技资讯收藏
    @Autowired
    private TechInformationCollectionDao techInformationCollectiondao;
    @Autowired
    private FileDao fileDao;
/**
 * 添加科技资讯
 * @param sections
 * @return
 */
	public boolean addInformation(Information information) {
		//根据图片的id查出图片的URL
		String imgId=information.getImgid();
		//更新图片状态为add
		fileDao.updateFile(imgId);
		List<FileBean> listFile=fileDao.getFileById(imgId, "information");
		System.out.println(listFile);
		if (!listFile.isEmpty()) {			
			information.setImgUrl(listFile.get(0).getUrl());
		}
		
		// 先添加到数据库
		information.setId(Misc.base58Uuid());
		boolean flag=informationDao.addInformation(information);
		//数据库添加成功后，再添加索引库
    	if (flag) {
    		//判断索引是否存在
    		IndicesExistsResponse  indicesExistsResponse = client.admin().indices().exists( 
    	                        new IndicesExistsRequest().indices(new String[]{"information_index"})).actionGet();
    		boolean isExist=indicesExistsResponse.isExists();
    		//若索引不存在，则新建索引
    		if (!isExist) {
    			//生成索引的映射信息
    			XContentBuilder mapping=mapping();
    			PutMappingRequest putMappingRequest=Requests.putMappingRequest("information_index").type("information").source(mapping);
    			//创建索引
    			client.admin().indices().prepareCreate("information_index").execute().actionGet();
    			//为索引添加映射
    			client.admin().indices().putMapping(putMappingRequest).actionGet();
			}
    		
			//向索引库中添加信息
			XContentBuilder builder=addInformation2(information);
			@SuppressWarnings("unused")
			IndexResponse response=client.prepareIndex("information_index","information",information.getId()).setSource(builder).get();
    	}
    	
		return flag;
	}
/**
 * 修改科技资讯
 * @param sections
 * @return
 */
	public boolean updateInformation(Information information) {
		Information informationOld = informationDao.getInformationById(information.getId(), information.getUpdateBy());
		// 先修改数据库中的科技资讯信息
		boolean flag=informationDao.updateInformation(information);
		//成功之后，再修改索引库中的内容
		if (flag) {
			String id=information.getId();
			//根据id从索引库删除科技资讯
			client.prepareDelete("information_index","information",id).get();
			//从数据库中查出更新后的数据
			Information info=informationDao.getInformationById(id, information.getUpdateBy());
			//再次添加到索引库
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				client.prepareIndex("information_index","information",id).setSource(
						XContentFactory.jsonBuilder().startObject()
						.field("id",id)
						.field("resource",info.getResource())
						.field("title",info.getTitle())
						.field("time",info.getTime())
						.field("resume",info.getResume())
						.field("sectors",info.getSectors())
						.field("tags",info.getTags())
						.field("cotent",info.getCotent())
						.field("createAt",sdf.format(info.getCreateAt()))
						.field("createBy",info.getCreateBy())
						.field("updateAt",sdf.format(info.getUpdateAt()))
						.field("updateBy",info.getUpdateBy())
						.field("state",info.getState())
						.field("imgUrl",info.getImgUrl())
						.field("count",info.getCount())
						.endObject()).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//如果 科技专栏的发布者与修改人不同则  给创建人发消息
			if(null!=information.getUpdateBy() &&  information.getUpdateBy().equals(informationOld.getCreateBy())){
				//审核状态被修改
				if(information.getState()!=null &&(!information.getState().endsWith(informationOld.getState()))){
					//增加消息推送（科技资讯审核）
					 messageService.insertMessage(informationOld.getCreateBy(), informationOld.getId(), Config.KJ_ZX_SH, 1);
				}else{
					//增加消息推送(科技资讯修改)
					 messageService.insertMessage(informationOld.getCreateBy(), informationOld.getId(), Config.KJ_ZX_XG, 1);
				}
					
				
			}
		}
		
		return flag;
	}
	/**
	 * 根据id查询科技资讯
	 * @param id
	 * @return
	 */
	public Information getInformationById(String id,String userid) {
		// 先查询科技资讯详情
		Information information=informationDao.getInformationById(id,userid);
		if (information!=null) {
			//阅读量+1
			int count=information.getCount();
			information.setCount(count+1);
			informationDao.countUpdate(information);
			
			try {
			UpdateResponse response = client.prepareUpdate("information_index","information",id)
					.setDoc(XContentFactory.jsonBuilder()
							.startObject()
								.field("count",count+1)
							.endObject()).get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//再查其点赞数
			Integer approuverNum=techInformationApprouverDao.getTotalApprouver(id);
			if (approuverNum==null) {
				approuverNum=0;
			}
			information.setApprouverNum(String.valueOf(approuverNum));			
		}
		return information;
	}
	/**
	 * 查询科技资讯列表
	 * @param page
	 * @param state 
	 * @return
	 */
	public  Map<String, Object> getInformationLists(Integer page, String state) {
		   //PageInfo中的显示条数设的是10
		   PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       List<Information> informations = informationDao.getInformationLists( pageInfo.getStartIndex(), pageInfo.getPageSize(),state);
	       int totalCount = informationDao.getTotalCountNum(state);
		
	        Map<String, Object> map = new HashMap<>();
	        map.put("items", informations);
	        map.put("totalCount", totalCount);
	        map.put("Count", pageInfo.getPageSize());
	        map.put("itemCount", pageInfo.getPageSize());
	        map.put("offset", pageInfo.getStartIndex());
	        map.put("limit", pageInfo.getPageSize());
	        return map;
	}
	/**
	 * 删除科技资讯
	 * @param id
	 * @return
	 */
	public boolean deleteInformation(String id) {
		// 先从数据库中删除
		boolean flag=informationDao.deleteInformation(id);
		//若是删除成功，则再从索引库中删除
		if (flag) {
			@SuppressWarnings("unused")
			DeleteResponse response=client.prepareDelete("information_index","information",id).get();
		}
		return flag;
	}
	
	/**
	 * 根据关键字搜索（elasticsearch）
	 * @param key
	 * @return
	 */
	public JsonResult queryByKey(String key,Integer page){
		PageInfo pageInfo = new PageInfo();
	    pageInfo.setCurrentPageNum(page);
	    
		List<Map> listInformation = Lists.newArrayList();
		SearchRequestBuilder qBuilder=client.prepareSearch("information_index").setTypes("information");
		BoolQueryBuilder builder=QueryBuilders.boolQuery()
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
		qBuilder.setQuery(builder).setFrom(pageInfo.getStartIndex()).setSize(pageInfo.getPageSize());
		//发出查询请求
		SearchResponse response = qBuilder.execute().actionGet();	
		SearchHits hits = response.getHits();
		for(SearchHit hit:hits){
			listInformation.add(hit.getSource());
		}

		//不分页再查一次
		qBuilder.setQuery(builder);
		SearchResponse response2 = qBuilder.execute().actionGet();
		SearchHits hits2 = response.getHits();
		
		Map<String, Object> map = new HashMap<>();
        map.put("items", listInformation);
        map.put("totalCount", hits2.totalHits);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
        
		return new JsonResult(true, map);
	}
	/**
     * 指定科技资讯的索引库映射
     * @return
     */
    private XContentBuilder mapping(){
    	XContentBuilder builder=null;
    	try{
    		builder=XContentFactory.jsonBuilder().startObject()
    				//索引类型为information
    				.startObject("information")
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
	    						.field("type","date")
								.field("format","yyyy-MM-dd HH:mm:ss")
    						.endObject()
    						//创建人
    						.startObject("createBy")
	    						.field("type","text")
								.field("analyzer","ik_max_word")
    						.endObject()
    						//修改时间
    						.startObject("updateAt")
    							.field("type","date")
    							.field("format","yyyy-MM-dd HH:mm:ss")
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
    						.startObject("count")
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
     * @param information
     * @return
     */
    private XContentBuilder addInformation2(Information information){
    	XContentBuilder builder=null;
    	String nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	try {
    		builder=XContentFactory.jsonBuilder().startObject()
					.field("id",information.getId())
					.field("resource",information.getResource())
					.field("title",information.getTitle())
					.field("time",information.getTime())
					.field("resume",information.getResume())
					.field("sectors",information.getSectors())
					.field("tags",information.getTags())
					.field("cotent",information.getCotent())
					.field("createAt",nowTime)
					.field("createBy",information.getCreateBy())
					.field("updateAt",nowTime)
					.field("updateBy",information.getUpdateBy())
					.field("state","0")
					.field("imgUrl",information.getImgUrl())
					.field("count",0)
					.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return builder;
    }
    /**
     * 给科技资讯点赞增加记录 主要有俩个操作（1 给计数加1  2 给增加点赞记录） 
     * @param techInformationApprouver
     * @return
     */
    @Transactional
	public Information addApprouver(TechInformationApprouver techInformationApprouver) {
		int todayIsApprouver = techInformationApprouverDao.isTodayApprouverTechInfornaion(techInformationApprouver.getApprouverBy(), techInformationApprouver.getInformationId());
		if(todayIsApprouver == 0){
			//增加点赞记录
			techInformationApprouverDao.insertTechInfornaionApprouver(techInformationApprouver);
			//点赞次数加1
			informationDao.updateInformationApprouverNum(techInformationApprouver.getInformationId());
			return informationDao.getInformationById(techInformationApprouver.getInformationId(),techInformationApprouver.getApprouverBy());
		}else{
			return null;
		}
		
		
		
	}
    /**
     * 收藏科技资讯
     * @param techInformationCollection
     * @return
     */
	public boolean collectionTechInformation(TechInformationCollection techInformationCollection) {
		
	int iscollect =	techInformationCollectiondao.isCollectionTechInformation(techInformationCollection.getCollectBy(), techInformationCollection.getInformationId());
	if(iscollect == 0){
		//增加科技资讯收藏消息
		messageService.insertMessage(techInformationCollection.getCollectBy(), techInformationCollection.getId(), Config.KJ_ZX_SSH, 1);
		//增加收藏记录
		return techInformationCollectiondao.insertTechInformationCollection(techInformationCollection);
	}else{
		return false;
	}
		
	}
	
	/**
	 * 科技资讯、科技专栏的综合查询，每个查10条数据
	 * @param page
	 * @param state
	 * @return
	 */
	public JsonResult getInformationAndSectionLists(Integer page, String state){
		   //PageInfo中的显示条数设的是10
		   PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       //科技资讯列表
	       List<Information> informations = informationDao.getInformationLists( pageInfo.getStartIndex(), pageInfo.getPageSize(),state);
	       //科技专栏列表
	       List<Sections> sections = sectionsDao.getSectionsLists(pageInfo.getStartIndex(), pageInfo.getPageSize(), state);
	       Map<String, Object> map = new HashMap<>();
	       map.put("information", informations);
	       map.put("section", sections);
	       return new JsonResult(true, map);
	}
}
