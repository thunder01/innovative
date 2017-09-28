package com.innovative.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.innovative.bean.Demand;
import com.innovative.bean.FileBean;
import com.innovative.bean.Order;
import com.innovative.bean.Report;
import com.innovative.dao.DemandDao;
import com.innovative.dao.FileDao;
import com.innovative.dao.OrderDao;
import com.innovative.dao.ProjectApprovalDao;
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
	@Resource
	private ProjectApprovalDao projectApprovalDao;
	@Resource
	private FileDao fileDao;
	
	/**
	 * 添加一个报告，一次add操作无需事务
	 * @param report
	 */
	public Map<String, Object> addReport(Report report) {
		Map<String, Object> map=new HashMap<>();
		if(report!=null){
			fileDao.updateFile(report.getFileid());		
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
	 * 更新报告的删除状态，默认是0，改成1让其在页面不显示，一次更新操作
	 * @param id 报告的id
	 * @param userid 用户的id
	 * @return
	 */
	public int updateReportDeleted(Integer id,String userid) {
		Map<String, Object> map=new HashMap<>();
		map.put("id", id);
		map.put("userid", userid);
		return reportDao.updateReportDeleted(map);
	}
	
	/**
	 * 更新报告(修改报告)
	 * @param report
	 * @return
	 */
	@Transactional
	public Map<String, Object> updateReport(Report report) {
		Map<String, Object> map=new HashMap<>();
		String field=report.getFileid();//查询报告拥有的文件id
		fileDao.updateFile(field);//跟新文件信息
		List<FileBean> listFiles=fileDao.getFileById(field, "orderReport");
		int result=reportDao.updateReport(report);//更新报告信息
		
		Report r = reportDao.findReportById(report.getId());
		r.setList(listFiles);
		map.put("item",r);
		map.put("result", result);
		map.put("orderid", r.getOrder_id());
		map.put("reportid", r.getId());
		map.put("type", r.getType());
		return map;
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
		map.put("orderid", order_id);
		map.put("totalCount", reportDao.findReportCountByOrder_id(map));
		
		/*根据订单id、报告类型查询所有的报告*/
		List<Report> list = reportDao.findReportListByOrder_id(map);			
		/*为所有的报告添加需求名*/
		for(Report report:list){
			Order order=orderDao.selectOrderByOrderId(order_id);//根据订单id查询订单信息
			if (order!=null) {			
				/*根据立项信息查询订单id，根据订单id查询订单信息，根据需求id查询需求信息*/
				Demand demand = demandDao.getDemand(order.getDemandId());
				if (demand!=null) {
					report.setDemand_name(demand.getName());
				}
			}		
		}	
		
		if (list!=null&&list.size()>0) {
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
	 * @param type(1、2、3、4、5、6)
	 * @return
	 */
	public Map<String, Object> findReportById(Integer reportid,Integer type){
		Map<String, Object> map=new HashMap<>();
		Report report=reportDao.findReportById(reportid);//查出报告的详细信息
		/*获取报告的文件id*/
		String fileid=report.getFileid();
		if (fileid!=null) {
			List<FileBean> listFiles=fileDao.getFileById(fileid, "orderReport");//找出所有的相关文件
			report.setList(listFiles);
		}
		
		map.put("reportid", report.getId());
		map.put("orderid", report.getOrder_id());
		map.put("item", report);
		map.put("type", type);
		return map;
	}
	
	/**
	 * 查询用户的文件上传记录
	 * @param userid
	 * @return
	 */
	public Map<String, Object> getMyFile(String userid){
		Map<String, Object> map=new HashMap<>();
		List<FileBean> list = reportDao.getMyFile(userid);
		map.put("files", list);
		return map;
	}
}
