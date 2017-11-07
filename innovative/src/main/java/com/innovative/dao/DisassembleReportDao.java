package com.innovative.dao;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.DisassembleReport;

/**
 * 拆解报告的持久层接口
 * @author fzy
 * @version 2.0
 * */
public interface DisassembleReportDao {
	
	/**
	 * getDisassembleByOrderid 根据订单id获取对应的拆解报告信息
	 * @param orderid
	 * @return
	 * */
	public DisassembleReport getDisassembleByOrderid(Integer orderid);
	
	/**
	 * 添加拆解报告
	 * @param dReport
	 * @return
	 */
	public Integer saveDisassembleReport(DisassembleReport dReport);
	
	/**
	 * 删除拆解报告，记录删除人时间
	 * @param dReport
	 * @return
	 */
	public Integer deleteDisassembleReportById(Integer id);
	
	/**
	 * 根据id查询拆解报告详情
	 * @param id
	 * @return
	 */
	public DisassembleReport getDisassembleReportById(Integer id);
	
	/**
	 * 修改拆解报告
	 * @param dReport
	 * @return
	 */
	public Integer updateDisassembleReportById(DisassembleReport dReport);
	
	/**
	 * 根据订单id查询拆解报告id
	 * @param orderid
	 * @return
	 */
	public Integer getIdByOrderId(Integer orderid);
	
	/**
	 * 根据id删除拆解报告
	 * @param id
	 * @return
	 */
	public Integer deleteDisassembleReportByIdReal(Integer id);
	/**
	 * 根据id删除拆解报告(更改状态)
	 * @param id
	 * @param delete_by
	 * @return
	 */
	public Integer deleteDisassembleReport(@Param("id")Integer id,@Param("delete_by")String delete_by);
}
