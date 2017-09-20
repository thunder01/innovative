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
	 * 把报告的删除状态改变
	 * @param map 里面有userid和报告的id
	 */
	public int updateReportDeleted(Map<String, Object> map);
	
	/**
	 * 更新报告信息
	 * @param report
	 */
	public int updateReport(Report report);
	
	/**
	 * 通过报告id来查询报告信息
	 * @param id
	 * @return
	 */
	public Report findReportById(Integer id);
	
	/**
	 * 通过立项表单的id和报告的类型来查找报告的集合
	 * @param map(approval_id,type,pageSize,startIndex)
	 * @return
	 */
	public List<Report> findReportListByApp_id(Map<String, Object> map);
	
	/**
	 * 通过立项表单的id和报告的类型来查找报告的数量
	 * @param map(approval_id,type)
	 * @return
	 */
	public int findReportCountByApp_id(Map<String, Object> map);

	/**
	 * 通过订单id来找所有报告并按创建时间排序 
	 * @param order_id
	 * @return
	 */
	public List<Report> rankReport(Integer order_id);
}
