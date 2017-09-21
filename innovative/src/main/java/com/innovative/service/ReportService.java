package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Demand;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.bean.Report;
import com.innovative.dao.DemandDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;
import com.innovative.dao.ReportDao;
import com.innovative.utils.PageInfo;

@Service("reportService")
public class ReportService{
	@Resource
	private ReportDao reportDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private DemandDao demandDao;
	@Resource
	private ProjectApprovalDao projectApprovalDao;
	/**
	 * 添加一个报告
	 * @param report
	 */
	@Transactional

	public Map<String, Object> addReportAndOrder_report(Report report) {
		Map<String, Object> map=new HashMap<>();
		if(report!=null){
			int result=reportDao.addReport(report);//添加报告
			map.put("orderid", report.getOrder_id());
			map.put("type", report.getType());
			map.put("report_id", report.getId());
			map.put("result", result);
			map.put("item", result);
			Order order = orderDao.selectOrderByOrderId(report.getOrder_id());
			if(order!=null){
				Demand demand = demandDao.getDemand(order.getDemandId());
				if(demand!=null){
					report.setDemand_name(demand.getName());
				}
			}
		}
		return map;	
	}
	
	/**
	 * 更新报告的删除状态，默认是0，改成1让其在页面不显示
	 * @param id
	 */
	@Transactional
	public int updateReportDeleted(Integer id,String userid) {
		Map<String, Object> map=new HashMap<>();
		map.put("id", id);
		map.put("userid", userid);
		return reportDao.updateReportDeleted(map);
	}
	
	/**
	 * 更新报告
	 * @param report
	 */
	@Transactional
	public int updateReport(Report report) {
		return reportDao.updateReport(report);
	}
	
	/**
	 * 通过立项表单的id和报告的类型来查找报告的集合
	 * @param order_id 订单id
	 * @param approva_id 立项表单id
	 * @param type 报告类型
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> findReportById(Integer approva_id,String type,Integer pageNum) {
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("approva_id", approva_id);
		map.put("type", type);
		map.put("pageNum",pageInfo.getPageSize());
		map.put("offset",pageInfo.getStartIndex());
		map.put("pageSize",pageInfo.getPageSize());
		map.put("startIndex",pageInfo.getStartIndex());
		Demand demand=null;
		List<Report> list = reportDao.findReportListByApp_id(map);
		ProjectApproval projectApproval = projectApprovalDao.findApprovalById(approva_id);
		if (list!=null && list.size()>0) {
			map.put("result", 1);
			for(Report report:list){
				if (projectApproval!=null&&orderDao.selectOrderByOrderId(projectApproval.getOrder_id())!=null) {
					map.put("orderid", projectApproval.getOrder_id());
					if (orderDao.selectOrderByOrderId(projectApproval.getOrder_id()).getDemandId()!=null) {
						demand = demandDao.getDemand(orderDao.selectOrderByOrderId(projectApproval.getOrder_id()).getDemandId());
					}	
				}	
				if (demand!=null) {
					report.setDemand_name(demand.getName());
				}
			}	
		}else {
			map.put("result", 0);
		}	
		map.put("totalCount", reportDao.findReportCountByApp_id(map));
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		map.put("items",list);
		return map;
	}
	
	public Map<String, Object> findReportById(Integer reportid,Integer orderid,Integer type){
		Map<String, Object> map=new HashMap<>();
		Report report=reportDao.findReportById(reportid);
		map.put("item", report);
		map.put("orderid", orderid);
		map.put("type", type);
		return map;
	}
}
