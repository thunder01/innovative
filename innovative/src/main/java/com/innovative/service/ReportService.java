package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Report;
import com.innovative.dao.ReportDao;

@Service("reportService")
public class ReportService{
	@Resource
	private ReportDao reportDao;
	/**
	 * 添加一个报告，并向xacx_order_report添加对应的需求id和添加报告的id
	 * @param report
	 */
	@Transactional
	public int addReportAndOrder_report(Report report) {
		reportDao.addReport(report);
		Map<String, Object> map = new HashMap<>();
		map.put("order_id", report.getOrder_id());
		map.put("report_id", report.getId());
		return reportDao.addOrder_Report(map);
		
	}
	
	/**
	 * 更新报告的删除状态，默认是0，改成1让其在页面不显示
	 * @param id
	 */
	@Transactional
	public int updateReportDeleted(Integer id) {
		return reportDao.updateReportDeleted(id);
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
	public Report findReportById(Integer order_id,String type) {
		System.out.println(">>>>>>>>>>"+order_id+">>>>>>>"+type);
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("order_id", order_id);
		map.put("type", type);
		Report report = reportDao.findReportById(map);
		//还需要需求的名字
//		report.setDemand_name(demand_name);
		return report;
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
		System.out.println("-------"+order_id);
		List<Report> list = reportDao.rankReport(order_id);
		System.out.println("======="+list);
		//此处也是需要需求的名字
		return list;
	}
	
	
}
