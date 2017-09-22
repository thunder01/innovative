package com.innovative.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovative.bean.Demand;
import com.innovative.bean.FileBean;
import com.innovative.bean.Order;
import com.innovative.bean.ProjectApproval;
import com.innovative.bean.Report;
import com.innovative.dao.DemandDao;
import com.innovative.dao.FileDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;
import com.innovative.dao.ReportDao;
import com.innovative.utils.PageInfo;

@Service("reportService")
@Transactional
public class ReportService{
	@Resource
	private ReportDao reportDao;
	@Resource
	private OrderDao orderDao;
	@Resource
	private DemandDao demandDao;
	@Resource
	private ProjectApprovalDao projectApprovalDao;
	@Resource
	private FileDao fileDao;
	
	/**
	 * 添加一个报告
	 * @param report
	 */
	public Map<String, Object> addReport(Report report) {
		Map<String, Object> map=new HashMap<>();
		fileDao.updateFile(report.getFileid());
		
		if(report!=null){
			/*查询报告对应的需求名称*/
			Order order = orderDao.selectOrderByOrderId(report.getOrder_id());	
			if(order!=null){
				Demand demand = demandDao.getDemand(order.getDemandId());
				if(demand!=null){
					report.setDemand_name(demand.getName());
				}
			}
			
			int result=reportDao.addReport(report);//添加报告
			map.put("orderid", report.getOrder_id());
			map.put("type", report.getType());
			map.put("report_id", report.getId());
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
	 * 通过订单的id和报告的类型来查找报告的集合
	 * @param order_id 订单id
	 * @param type 报告类型
	 * @param pageNum
	 * @return
	 */
	public Map<String, Object> findReportByOrderId(Integer order_id,String type,Integer pageNum) {
		Map<String, Object> map = new HashMap<String,Object>();
		
		PageInfo pageInfo = new PageInfo();
        pageInfo.setCurrentPageNum(pageNum);
        
		
		map.put("order_id", order_id);
		map.put("type", type);
		map.put("Count", pageInfo.getPageSize());
	    map.put("itemCount", pageInfo.getPageSize());
	    map.put("limit", pageInfo.getPageSize());
	    map.put("pageNum",pageInfo.getPageSize());
	    map.put("offset",pageInfo.getStartIndex());		
		map.put("pageSize",pageInfo.getPageSize());
		map.put("startIndex",pageInfo.getStartIndex());
		map.put("totalCount", reportDao.findReportCountByOrder_id(map));
		map.put("orderid", order_id);
		
		/*根据订单id、报告类型查询所有的报告*/
		List<Report> list = reportDao.findReportListByOrder_id(map);	
		
		/*未所有想报告添加需求名*/
		for(Report report:list){
			if (orderDao.selectOrderByOrderId(order_id)!=null) {
				
				if (orderDao.selectOrderByOrderId(order_id).getDemandId()!=null) {
					/*根据立项信息查询订单id，根据订单id查询订单信息，根据需求id查询需求信息*/
					Demand demand = demandDao.getDemand(orderDao.selectOrderByOrderId(order_id).getDemandId());
					if (demand!=null) {
						report.setDemand_name(demand.getName());
					}
				}	
			}		
		}	
		
		if (list.size()>0) {
			map.put("result", 1);
				
		}else {
			map.put("result", 0);
		}			  
		map.put("items",list);
		return map;
	}
	
	/**
	 * 根据报告id查询报告详情
	 * @param reportid
	 * @param approval_id
	 * @param type
	 * @return
	 */
	public Map<String, Object> findReportById(Integer reportid,Integer type){
		Map<String, Object> map=new HashMap<>();
		Report report=reportDao.findReportById(reportid);
		System.out.println(report);
		/*獲取文件id*/
		String fileid=report.getFileid();
		if (fileid!=null) {
			List<FileBean> listFiles=fileDao.getFileById(fileid, "orderReport");
			report.setList(listFiles);
		}
		
		map.put("orderid", report.getOrder_id());
		map.put("item", report);
		map.put("type", type);
		return map;
	}
}
