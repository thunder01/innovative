package com.innovative.service;

import com.innovative.bean.Sections;
import com.innovative.dao.SectionsDao;
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
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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
	 * @return
	 */
	public  Map<String, Object> getSectionLists(Integer page) {
		 PageInfo pageInfo = new PageInfo();
	       pageInfo.setCurrentPageNum(page);
	       List<Sections> sections = sectionsDao.getSectionsLists( pageInfo.getStartIndex(), pageInfo.getPageSize());
	       int totalCount = sectionsDao.getTotalCountNum();
		
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
