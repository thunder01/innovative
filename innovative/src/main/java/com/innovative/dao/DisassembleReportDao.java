package com.innovative.dao;

import com.innovative.bean.DisassembleReport;

/**
 * 拆解报告的持久层接口
 * @author fzy
 * @version 1.0
 * */
public interface DisassembleReportDao {
	/**
	 * 拆解报告上传后，在数据库中保存添加记录
	 * @param list 图片地址，批量新增
     * @return
	 * */
	public int saveDisassembleReport(DisassembleReport report);
}
