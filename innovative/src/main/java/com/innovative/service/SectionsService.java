package com.innovative.service;

import com.google.common.collect.Lists;
import com.innovative.bean.Sections;
import com.innovative.dao.SectionsDao;
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
/**
 * 添加科技专栏
 * @param sections
 * @return
 */
	public boolean addSection(Sections sections) {
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
		return flag;
	}
/**
 * 修改科技专栏
 * @param sections
 * @return
 */
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
			Sections section2=getSectionById(id);
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
						.field("createAt",sdf.format(section2.getCreateAt()))
						.field("createBy",section2.getCreateBy())
						.field("updateAt",sdf.format(section2.getUpdateAt()))
						.field("updateBy",section2.getUpdateBy())
						.field("state","0")//tips 这里要修改为section2.getState()
						.endObject())
						.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//如果 科技专栏的发布者与修改人不同则  给创建人发消息
			if(null!=sections.getUpdateBy() &&  sections.getUpdateBy().equals(sectionOld.getCreateBy())){
				//审核状态被修改
				if(sections.getState()!=null &&(!sections.getState().endsWith(sectionOld.getState()))){
					//增加消息推送（科技专栏审核）
					 messageService.insertMessage(sectionOld.getCreateBy(), sectionOld.getId(), Config.KJ_ZL_SH, 1);
				}else{
					//增加消息推送(科技专栏修改)
					 messageService.insertMessage(sectionOld.getCreateBy(), sectionOld.getId(), Config.KJ_ZL_XG, 1);
				}
					
				
			}
			
		}
		return flag;
	}
	/**
	 * 根据id查询科技专栏
	 * @param id
	 * @return
	 */
	public Sections getSectionById(String id) {
		// TODO Auto-generated method stub
		return sectionsDao.getSectionById(id);
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
	public boolean deleteSection(String id) {
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
	public JsonResult queryByKey(String key){
		List<Map> listSections = Lists.newArrayList();
		SearchRequestBuilder qBuilder=client.prepareSearch("sections_index").setTypes("sections");
		BoolQueryBuilder builder=QueryBuilders.boolQuery()
				.should(QueryBuilders.matchQuery("title",key))
				.should(QueryBuilders.matchQuery("sectors",key))
				.should(QueryBuilders.matchQuery("tags",key))
				.should(QueryBuilders.matchQuery("resume",key))
				.should(QueryBuilders.matchQuery("cotent",key));
		//结果分页
		qBuilder.setQuery(builder).setFrom(0).setSize(10);
		//发出查询请求
		SearchResponse response = qBuilder.execute().actionGet();
		
		SearchHits hits = response.getHits();
		for(SearchHit hit:hits){
			listSections.add(hit.getSource());
		}
		System.out.println(hits);
		if (listSections.size()==0) {
			return new JsonResult(false, "没有结果");
		}
		return new JsonResult(true, listSections);
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
					.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return builder;
    }

}
