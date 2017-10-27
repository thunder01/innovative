package com.innovative.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.innovative.bean.Integral;
import com.innovative.dao.IntegralDao;

@Service
public class IntegralService {
	
	@Resource
	private IntegralDao integralDao;
	
	public int managerIntegral(int type,String userid){
		Integral newIntegral = new Integral();
		newIntegral.setUserid(userid);
		Integral integral = integralDao.getIntegral(userid);
		switch (type) {
		case 1://登录： 首次登录获得100分，再次登录获得10分；每日最多2次，封顶20分。
			if(integral!=null){
				if(integral.getLogin_count()<2){//判断登入次数是否为2次，每天从0开始
					integral.setLogin_count(integral.getLogin_count()+1);
					integral.setIntegral(10);
					integralDao.updateIntegral(integral);
				}else {
					integral.setLogin_count(integral.getLogin_count()+1);
					integral.setIntegral(0);
					integralDao.updateIntegral(integral);
				}
			}else {
				integralDao.addIntegral(newIntegral);
			}
			break;
		case 2://留言： 获取20分。资源库详情页若留言者提供有用信息，寻源工程师可任意打赏50积分，（点击一次弹框确认后可以给50分，固定按钮。只能点击一次）。
			if(integral!=null){
				integral.setIntegral(20);
				integralDao.updateIntegral(integral);
			}
			break;
		case 3://上传科技信息推特：获取20分。
			if(integral!=null){
				integral.setIntegral(20);
				integralDao.updateIntegral(integral);
			}
			break;
		case 4://科技信息推特中信息的收藏+转发+评论+点赞等总数>10获取50分。
			break;
		case 5://创新资源上传专家、合作机构、行业协会、技术报告、方案、仪器设备：获取100分。
			break;
		case 6://创新资源详情页进入次数>5次，奖励上传人50分。（待定）
			break;
		case 7://技术报告和方案下载奖励上传人20分。
			break;
		default:
			break;
		}
		return 0;
	}
}
