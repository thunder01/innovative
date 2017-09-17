package com.innovative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.DisassembleReport;
import com.innovative.bean.Order;
import com.innovative.dao.DisassembleReportDao;
import com.innovative.dao.OrderDao;

/**
 * 拆解报告的业务逻辑类
 * @author fzy
 * @version 2.0
 * */
@Service
public class DisassembleReportService {
	@Autowired
	private DisassembleReportDao reportDao;//注入持久层接口
	@Autowired
	private OrderDao orderDao;
	
	/**
	 * 拆解报告上传后，在数据库中保存添加记录
	 * @param repor 拆解报告信息
	 * @param orderid 订单id
     * @return
	 * */
	@Transactional
	public int saveDisassembleReport(DisassembleReport report,Integer orderid){
		/*首先向拆解报告表中添加一条记录，并返回其主键*/
		int num = reportDao.saveDisassembleReport(report);
		/*然后更新订单表中的拆解报告id信息*/
		return orderDao.updateDisassembleReport(new Order(orderid,report.getId()));	 
	}
	
	/**
	 * 根据id查询出对应的拆解报告
	 * @param id 拆解报告id
	 * @return
	 * */
	public DisassembleReport getDisassembleReportById(Integer orderid){		
		/*先根据订单id查询拆解报告id*/
		Integer disassembleId=orderDao.selectDisassemble(orderid);
		if (disassembleId!=null) {
			/*然后根据拆解报告id查询拆解信息*/
			return reportDao.getDisassembleReportById(disassembleId);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 根据id删除拆解报告
	 * @param id 拆解报告id
	 * @return
	 * */
	public int deleteDisassembleReportById(Integer id){
		return reportDao.deleteDisassembleReportById(id);
	}
	
	/**
	 * 修改拆解报告
	 * @param report 拆解报告的实体
	 * @return
	 * */
	public int updateDisassembleReport(DisassembleReport report){
		return reportDao.updateDisassembleReport(report);
	}
	
}
