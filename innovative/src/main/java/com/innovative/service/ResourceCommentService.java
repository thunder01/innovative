package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Association;
import com.innovative.bean.Equipment;
import com.innovative.bean.Expert;
import com.innovative.bean.Organization;
import com.innovative.bean.ResourceComment;
import com.innovative.bean.Solution;
import com.innovative.bean.TechnicalReport;
import com.innovative.bean.User;
import com.innovative.dao.AssociationDao;
import com.innovative.dao.EquipmentDao;
import com.innovative.dao.ExpertDao;
import com.innovative.dao.OrganizationDao;
import com.innovative.dao.ResourceCommentDao;
import com.innovative.dao.SolutionDao;
import com.innovative.dao.TechnicalReportDao;
import com.innovative.utils.PageInfo;
/**
 * 创新资源评论逻辑层
 * @author huang
 *
 */
@Service
public class ResourceCommentService {
	@Resource
	private ResourceCommentDao resourceCommentDao;
	@Resource
	private ExpertDao expertDao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private AssociationDao associationDao;
	@Resource
	private TechnicalReportDao technicalReportDao;
	@Resource
	private SolutionDao solutionDao;
	@Resource
	private EquipmentDao equipmentDao;
	@Resource
	private IntegralService integralService;
	@Resource
	private UserService userService;
	
	/**
	 * 添加一个评论
	 * @param resourceComment
	 * @return
	 */
	@Transactional
	public Map<String, Object> addResourceComment(ResourceComment resourceComment){
		User user = userService.getUser(resourceComment.getComment_by());
		resourceComment.setComment_username(user.getUserName());
		Map<String, Object> map = new HashMap<>();
		resourceCommentDao.addResourceComment(resourceComment);
		integralService.managerIntegral(2, resourceComment.getComment_by(), resourceComment.getId()+"");
//		//类型type:1专家、2合作机构、3行业协会、4技术报告、5方案、6一起设备
//		if(resourceComment.getType()==1){
//			Expert expert = expertDao.getExpert(resourceComment.getResource_id());
//			map.put("expert", expert);
//		}
//		if(resourceComment.getType()==2){
//			Organization organization = organizationDao.getOrganization(resourceComment.getResource_id());
//			map.put("organization", organization);
//		}
//		if(resourceComment.getType()==3){
//			Association association = associationDao.getAssociation(resourceComment.getResource_id());
//			map.put("association", association);
//		}
//		if(resourceComment.getType()==4){
//			TechnicalReport technicalReport = technicalReportDao.getTechnicalReportById(resourceComment.getResource_id());
//			map.put("technicalReport", technicalReport);
//		}
//		if(resourceComment.getType()==5){
//			Solution solution = solutionDao.getSolutionById(resourceComment.getResource_id());
//			map.put("solution", solution);
//		}
//		if(resourceComment.getType()==6){
//			Equipment equipment = equipmentDao.getEquipmentById(resourceComment.getResource_id());
//			map.put("equipment", equipment);
//		}
//		List<ResourceComment> resourceComments = resourceCommentDao.getComment(resourceComment.getType(), resourceComment.getResource_id());
		ResourceComment resourceComment2 = resourceCommentDao.getResourceComment(resourceComment.getId());
		map.put("comment", resourceComment2);
		return map;
	} 
	
	/**
	 * 详情页点赞
	 * @param resourceComment
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateResourceComment(ResourceComment resourceComment){
		Map<String, Object> map = new HashMap<>();
		resourceCommentDao.updateResourceComment(resourceComment);
		resourceComment = resourceCommentDao.getResourceComment(resourceComment.getId());
		integralService.managerIntegral(13, resourceComment.getComment_by(), resourceComment.getResource_id());
		//类型type:1专家、2合作机构、3行业协会、4技术报告、5方案、6一起设备
		if(resourceComment.getType()==1){
			Expert expert = expertDao.getExpert(resourceComment.getResource_id());
			map.put("expert", expert);
		}
		if(resourceComment.getType()==2){
			Organization organization = organizationDao.getOrganization(resourceComment.getResource_id());
			map.put("organization", organization);
		}
		if(resourceComment.getType()==3){
			Association association = associationDao.getAssociation(resourceComment.getResource_id());
			map.put("association", association);
		}
		if(resourceComment.getType()==4){
			TechnicalReport technicalReport = technicalReportDao.getTechnicalReportById(resourceComment.getResource_id());
			map.put("technicalReport", technicalReport);
		}
		if(resourceComment.getType()==5){
			Solution solution = solutionDao.getSolutionById(resourceComment.getResource_id());
			map.put("solution", solution);
		}
		if(resourceComment.getType()==6){
			Equipment equipment = equipmentDao.getEquipmentById(resourceComment.getResource_id());
			map.put("equipment", equipment);
		}
		List<ResourceComment> resourceComments = resourceCommentDao.getComment(resourceComment.getType(), resourceComment.getResource_id());
		map.put("comments", resourceComments);
		return map;
	}
	/**
	 * 评论页点赞
	 * @param resourceComment
	 * @return
	 */
	public ResourceComment update(ResourceComment resourceComment){
		User user = userService.getUser(resourceComment.getComment_by());
		if(user!=null){
			resourceComment.setComment_username(user.getUserName());
		}
		ResourceComment comment=resourceCommentDao.getResourceComment(resourceComment.getId());
		if(comment.getEnjoy_by()==null){
			resourceCommentDao.updateResourceComment(resourceComment);
			comment =resourceCommentDao.getResourceComment(resourceComment.getId());
			integralService.managerIntegral(13, comment.getComment_by(), comment.getId()+"");
		}
		return comment;
	}
	/**
	 * 评论详情页分页查询
	 * @param type
	 * @param resource_id
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> getComments(Integer type,String resource_id,Integer pageNum){  
		Map<String, Object> map = new HashMap<>();
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        pageInfo.setPageSize(5);
		int totalCount = resourceCommentDao.getCountResourceCommentByResourcd_idAndType(type, resource_id);
		List<ResourceComment> resourceComments = resourceCommentDao.getResourceCommentByResourcd_idAndType(type, resource_id,pageInfo.getStartIndex(), pageInfo.getPageSize());
		map.put("items", resourceComments);
        map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		return map;
	}
	/**
	 * 获得前五条评论
	 * @param resource_id
	 * @param type
	 * @return
	 */
	public Map<String, Object> getResourceComment(String resource_id,Integer type){
		List<ResourceComment> list = resourceCommentDao.getComment(type, resource_id);
		int totalCount = resourceCommentDao.getCountResourceCommentByResourcd_idAndType(type, resource_id);
		Map<String, Object> map = new HashMap<>();
		map.put("count", totalCount);
		map.put("comments", list);
		return map;
	}
}
