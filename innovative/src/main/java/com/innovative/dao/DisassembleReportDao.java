package com.innovative.dao;

import com.innovative.bean.DisassembleReport;

/**
 * 拆解报告的持久层接口
 * @author fzy
 * @version 2.0
 * */
public interface DisassembleReportDao {
	/**
	 * 拆解报告上传后，在数据库中保存添加记录
	 * @param list 图片地址，批量新增
     * @return
	 * */
	public int saveDisassembleReport(DisassembleReport report);
	
	/**
	 * 根据id查询出对应的拆解报告
	 * @param id 拆解报告id
	 * @return
	 * */
	public DisassembleReport getDisassembleReportById(Integer id);
	
	/**
	 * 根据id删除拆解报告
	 * @param id 拆解报告id
	 * @return
	 * */
	public int deleteDisassembleReportById(Integer id);
	
	/**
	 * 修改拆解报告
	 * @param report 拆解报告的实体
	 * @return
	 * */
	public int updateDisassembleReport(DisassembleReport report);
}
