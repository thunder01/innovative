package com.innovative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovative.bean.DisassembleReport;
import com.innovative.dao.DisassembleReportDao;

/**
 * 拆解报告的业务逻辑类
 * @author fzy
 * @version 1.0
 * */
@Service
public class DisassembleReportService {
	@Autowired
	private DisassembleReportDao reportDao;//注入持久层接口
	
	/**
	 * 拆解报告上传后，在数据库中保存添加记录
	 * @param list 图片地址，批量新增
    * @return
	 * */
	public int saveDisassembleReport(DisassembleReport report){
		return reportDao.saveDisassembleReport(report);
	}
	
	
}
