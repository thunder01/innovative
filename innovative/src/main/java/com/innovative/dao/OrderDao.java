package com.innovative.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovative.bean.Order;
import com.innovative.bean.Report;

/**
 * 订单信息处理的持久层接口
 * @author fzy
 * */
public interface OrderDao {
	/**
	 * 新增订单信息
	 * @param userid 需求工程师id
	 * @param demandid 需求id
	 * @return 受影响的行数
	 * */
	public int insertOrder(@Param("demandId") Integer demandId,@Param("create_byId") String create_byId);
	
	/**
	 * 根据用户id查出其所有的订单信息（已通过拆解报告确认，或未进行确认的）
	 * @param userid 用户的id
	 * @return
	 * */
	public List<Order> selectOrderListByUserId(@Param("userid")String userid,@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
	
	/**
	 * 根据订单id删除订单信息
	 * */
	public int deleteByOrderId(Integer orderid);
	
	/**
	 * 根据订单id查询订单信息
	 * @param orderid 订单id
	 * @return
	 * */
	public Order selectOrderByOrderId(Integer orderid);
	
	/**
	 * 我的订单条数
	 * @param userid
	 * @return
	 */
	public int getTotalCOuntMyorder(String userid);
	
	/**
	 * 根据订单id查出需求id
	 * @param order_id
	 * @return
	 */
	public int getDemandIdByOrderId(Integer order_id);
	

	/**
	 * 根据需求id查出订单id
	 * @param demandid
	 * @return
	 */
	public int getOrderIdByDemandId(Integer demandid);
	
	/**
	 * 确认拆解报告
	 * @param order_id
	 * @return
	 */
	public int updateConfirm_status(Integer order_id);
	
	/**
	 * 拆解报告通过
	 * @param order_id
	 * @return
	 */
	public int updatePass_status(Order order);
	
	/**
	 * 通过订单的id查询需求工程师的id
	 * @param order_id
	 * @return
	 */
	public String findCreate_by_idById(Integer order_id);
	
	/**
	 * 项目评分
	 * @param order
	 * @return
	 */
	public int proEvaluate(Order order);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Order getOrderById(Integer id);
}
