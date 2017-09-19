package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Demand;
import com.innovative.bean.Report;
import com.innovative.dao.DemandDao;
import com.innovative.dao.OrderDao;
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
	/**
	 * 添加一个报告，并向xacx_order_report添加对应的需求id和添加报告的id
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
			
			reportDao.addOrder_Report(map);//向中间表添加
			map.put("result", result);
			map.put("item", result);
		}
		
		return map;
		
	}
	
	/**
	 * 更新报告的删除状态，默认是0，改成1让其在页面不显示
	 * @param id
	 */
	@Transactional
	public int updateReportDeleted(Integer id) {
		reportDao.updateReportDeleted(id);
		return reportDao.deleteOrder_reportByReport_id(id);
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
	 * 通过订单order_id和报告的类型来查询报告
	 * @param order_id
	 * @param type
	 * @return
	 */
	public Map<String, Object> findReportById(Integer order_id,String type,Integer pageNum) {
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("orderid", order_id);
		map.put("type", type);
		map.put("pageNum",pageInfo.getPageSize());
		map.put("offset",pageInfo.getStartIndex());
		int totalCount = reportDao.getTotalCount(map);
		Demand demand=null;
		List<Report> list = reportDao.findReportById(map);
		if (list!=null && list.size()>0) {
			map.put("result", 1);
			for(Report report:list){
				if (orderDao.selectOrderById(order_id)!=null) {		
					if (orderDao.selectOrderById(order_id).getDemandId()!=null) {
						demand = demandDao.getDemand(orderDao.selectOrderById(order_id).getDemandId());
					}	
				}	
				if (demand!=null) {
					report.setDemand_name(demand.getName());
				}
			}	
		}else {
			map.put("result", 0);
		}	
		
		map.put("totalCount", totalCount);
        map.put("Count", pageInfo.getPageSize());
        map.put("itemCount", pageInfo.getPageSize());
        map.put("offset", pageInfo.getStartIndex());
        map.put("limit", pageInfo.getPageSize());
		map.put("items",list);
		return map;
	}
	/**
	 * 通过type类型查询报告
	 * @param type
	 * @return
	 */
	public List<Report> findReportList(String type) {
		List<Report> list = reportDao.findReportList(type);
		//此处也是需要需求的名字
		return list;
	}
	/**
	 * 查询同一订单下的所有报告，根据创建时间排序
	 * @param order_id
	 * @return
	 */
	public List<Report> rankReport(Integer order_id){
//		System.out.println("-------"+order_id);
		List<Report> list = reportDao.rankReport(order_id);
		Demand demand = demandDao.getDemand(orderDao.selectOrderById(order_id).getDemandId());
		for (Report report : list) {
			report.setDemand_name(demand.getName());
		}
//		System.out.println("======="+list);
		return list;
	}
	
	public Map<String, Object> findReportId(Integer reportid,Integer orderid,Integer type){
		Map<String, Object> map=new HashMap<>();
		Report report=reportDao.findReportId(reportid);
		map.put("item", report);
		map.put("orderid", orderid);
		map.put("type", type);
		
		return map;
	}
	
	
}
