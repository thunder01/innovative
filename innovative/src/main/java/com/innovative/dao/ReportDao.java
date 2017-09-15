package com.innovative.dao;

import java.util.List;
import java.util.Map;

import com.innovative.bean.Report;

/**
 * 报告的持久层接口
 * @author huang
 *
 */
public interface ReportDao {
	/**
	 * 添加一个报告
	 * @param report
	 */
	public int addReport(Report report);
	/**
	 *添加报告的同时向xacx_order_report表中插入一个数据 
	 * @param orderId
	 */
	public int addOrder_Report(Map<String, Object> map);
	/**
	 * 把报告的删除状态改变
	 * @param report
	 */
	public int updateReportDeleted(Integer id);
	
	public int deleteOrder_reportByReport_id(Integer report_id);
	/**
	 * 更新报告信息
	 * @param report
	 */
	public int updateReport(Report report);
	/**
	 * 通过报告的id来查询报告信息
	 * @param id
	 * @return
	 */
	public Report findReportById(Map<String, Object> map);
	/**
	 * 通过类型查找报告的集合
	 * @return
	 */
	public List<Report> findReportList(String type);
	/**
	 * 给同一订单的报告排序
	 * @param order_id
	 * @return
	 */
	public List<Report> rankReport(Integer order_id);
}
