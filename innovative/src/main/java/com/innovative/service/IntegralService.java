package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Integral;
import com.innovative.bean.IntegralResource;
import com.innovative.bean.ResourceComment;
import com.innovative.dao.AssociationDao;
import com.innovative.dao.EquipmentDao;
import com.innovative.dao.ExpertDao;
import com.innovative.dao.IntegralDao;
import com.innovative.dao.IntegralResourceDao;
import com.innovative.dao.OrganizationDao;
import com.innovative.dao.ResourceCommentDao;
import com.innovative.dao.SolutionDao;
import com.innovative.dao.TechnicalReportDao;
import com.innovative.utils.PageInfo;
/**
 * 积分逻辑层
 * @author huang
 *
 */
@Service
public class IntegralService {
	
	@Resource
	private IntegralDao integralDao;
	@Resource
	private IntegralResourceDao integralResourceDao;
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
	/**
	 * 积分功能 
	 * @param type 积分类型(1登录OK、2是资源库详情页若留言OK、3上传科技信息推特OK、4科技信息推特中信息的收藏OK+转发OK+评论OK+点赞OK、5创新资源详情页进入次数、
	 * 6技术报告的方案下载、7创新资源上传专家OK、8创新资源上传合作机构OK、9创新资源上传行业协会OK、10创新资源上传技术报告OK、11创新资源上传方案OK、
	 * 12创新资源上传仪器设备OK、13资源库详情页若留言者提供有用信息，寻源工程师可任意打赏50积分OK)
	 * @param userid 用户id(登入的时候把登入的userid发来)
	 * @param resource_id 资源的id
	 * @return
	 */
	@Transactional
	public int managerIntegral(int type,String userid,String resource_id){
		int result=0;
		Integral integral = new Integral();
		integral.setUserid(userid);
		integral.setType(type);
		integral.setResource_id(resource_id);
		switch (type) {
		case 1://登录： 首次登录获得100分，再次登录获得10分；每日最多2次，封顶20分。
			if(integralDao.isFirstLogin(userid)==0){
				integral.setIntegral(110);
				integral.setContent("首次登入额外获得100分、登入获得10分");
			}else {
				if(integralDao.todayLoginCount(userid)<2){
					integral.setContent("登入获得10分");
					integral.setIntegral(10);
				}/*else {
					int count = integralDao.todayLoginCount(userid)+1;
					integral.setContent("今天第"+count+"次登入，不再获得积分");
					integral.setIntegral(0);
				}*/
			}
			result = integralDao.addIntegral(integral);
			break;
		case 2://留言： 获取20分。
			integral.setContent("评论获得20积分");
			integral.setIntegral(20);
			result = integralDao.addIntegral(integral);
			break;
		case 3://上传科技信息推特：获取20分。
			integral.setContent("上传科技信息推特获得20积分");
			integral.setIntegral(20);
			result = integralDao.addIntegral(integral);
			break;
		case 4://科技信息推特中信息的收藏+转发+评论+点赞等总数>10获取50分。收藏、转发、评论、点赞最后都得进行积分处理
			int countBySzpd = integralDao.getCountBySzpd(type, resource_id);
			if(countBySzpd==10){
				integral.setContent("上传科技信息推特的收藏+转发+评论+点赞总数大于10，获得50积分");
				integral.setIntegral(50);
			}else {
				integral.setIntegral(0);
			}
			result =integralDao.addIntegral(integral);
			break;
		case 5://创新资源详情页进入次数>5次，奖励上传人50分。（待定）
			int count = integralDao.getCountByResource_id(resource_id);
			count = count +1;
			if(count==5){
				integral.setContent("您的创新资源详情页被查看的次数超过5次");
				integral.setIntegral(50);
			}else {
				integral.setContent("当日您的创新资源详情被查看"+count+"次");
				integral.setIntegral(0);
			}
			result = integralDao.addIntegral(integral);
			break;
		case 6://技术报告的方案下载奖励上传人20分。
			integral.setContent("您的方案下载被下载获得20积分");
			integral.setIntegral(20);
			result = integralDao.addIntegral(integral);
			break;
		//7-12算一个类型的，都是资源创新上传*****************************************************************************	
		case 7://创新资源上传专家100分
			integral.setContent("您上传专家获得100积分");
			integral.setIntegral(100);
			result = integralDao.addIntegral(integral);
			break;
		case 8://创新资源上传合作机构100分
			integral.setContent("您上传合作机构获得100积分");
			integral.setIntegral(100);
			result = integralDao.addIntegral(integral);
			break;
		case 9://创新资源上传行业协会获取100分。
			integral.setContent("您上传行业协会获得100积分");
			integral.setIntegral(100);
			result = integralDao.addIntegral(integral);
			break;
		case 10://创新资源上传技术报告获取100分。
			integral.setContent("您上传技术报告获得100积分");
			integral.setIntegral(100);
			result = integralDao.addIntegral(integral);
			break;
		case 11://创新资源上传方案获取100分。
			integral.setContent("您上传方案获得100积分");
			integral.setIntegral(100);
			result = integralDao.addIntegral(integral);
			break;
		case 12://创新资源上传仪器设备获取100分。
			integral.setContent("您上传仪器设备获得100积分");
			integral.setIntegral(100);
			result = integralDao.addIntegral(integral);
			break;
		case 13://资源库详情页若留言者提供有用信息，寻源工程师可任意打赏50积分，（点击一次弹框确认后可以给50分，固定按钮。只能点击一次）
			ResourceComment resourceComment = resourceCommentDao.getResourceComment(Integer.parseInt(resource_id));
			integral.setUserid(resourceComment.getComment_by());
			integral.setContent("您的评论被打赏了50分");
			integral.setIntegral(50);
			result = integralDao.addIntegral(integral);
			//**************************************作者同时也应该减少50分
			/*通过resourc找到上传人，减去50分*/
			integral.setContent("您打赏了他人50分");
			integral.setIntegral(-50);
			integral.setUserid(resourceComment.getEnjoy_by());
			result = integralDao.addIntegral(integral);
			break;
		default:
			break;
		}
		return result;
	}
	
	
	/**
	 * 根据类型来判断点击的是哪个按钮：1当天、2七天内、3本月内
	 * @param userid
	 * @param type
	 * @return
	 */
	public Map<String, Object> getIntegral(String userid,int type,Integer pageNum){
		PageInfo pageInfo=new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
		Map<String, Object> map = new HashMap<>();
			//List<Integral> datelist = integralDao.getThisDayIntegralDetail(userid, pageInfo.getStartIndex(), pageInfo.getPageSize());
			int datetotalCount = integralDao.getCountThisDay(userid);
			map.put("datetotalCount",datetotalCount);//当日数量
			//map.put("items", datelist);
			//List<Integral> weeklist = integralDao.getThisWeekIntegralDetail(userid, pageInfo.getStartIndex(), pageInfo.getPageSize());
			int weektotalCount = integralDao.getCountThisWeek(userid);
			map.put("weektotalCount",weektotalCount);//当周数量
			//map.put("items", weeklist);
			List<Integral> list = integralDao.getThisMonthIntegralDetail(userid, pageInfo.getStartIndex(), pageInfo.getPageSize());
			int mouthtotalCount = integralDao.getCountThisMonth(userid);
			map.put("mouthtotalCount",mouthtotalCount);//当月数量
			map.put("items",list);//积分明细list
            map.put("sum",integralDao.sumCount(userid));//总共数量
            map.put("Count", pageInfo.getPageSize());
            map.put("itemCount", pageInfo.getPageSize());
            map.put("offset", pageInfo.getStartIndex());
            map.put("limit", pageInfo.getPageSize());
		return map;
	}
	
	
	
	
	
	
}
