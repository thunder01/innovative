package com.innovative.dao;

import java.util.List;

import com.innovative.bean.Order;

/**
 * 订单信息处理的持久层接口
 * @author fzy
 * */
public interface OrderDao {
	/**
	 * 新增订单信息
	 * @param order 订单对象
	 * @return 受影响的行数
	 * */
	public int insertOrder(Order order);
	
	/**
	 * 根据需求工程师的id，查出其所有订单
	 * @param id 用户id
	 * @return
	 * */
	public List<Order> selectDemandByUserId(Integer id);
	
	/**
	 * 根据寻源工程师的id，查出其所有订单
	 * @param id 用户id
	 * @return
	 * */
	public List<Order> selectSourceByUserId(Integer id);
	
	/**
	 * 根据立项表单的id,查找出对应的订单id
	 * @param id 立项表单id
	 * @return
	 * */
	public int selectOrderIdByApproval(Integer id);
	
	/**
	 * 寻源工程师下单操作，更新已有的订单
	 * @param order 订单对象
	 * @return
	 * */
	public int updateOrderLate_byId(Order order);
	
	/**
	 * 根据订单id查询出订单信息
	 * @param orderid 订单id
	 * @return
	 * */
	public Order selectOrderById(Integer orderid);
	
	/**
	 * 根据订单id查询对应的需求id
	 * @param orderid 订单id
	 * @return
	 * */
	public int selectDisassemble(Integer orderid);
	
	/**
	 * 根据定单id，查询出立项表单 的id
	 * @param order
	 * @return
	 * */
	public int selectApproval(Integer orderid);
	
	/**
	 * 更新订单表的拆解报告id信息
	 * @param order
	 * @return
	 * */
	public int updateDisassembleReport(Order order);
	
	/**
	 * 更新订单的立项表单信息
	 * @param order
	 * @return
	 * */
	public int updateProjectApproval(Order order);	
	/**
	 * 通过需求id来查询订单信息
	 * @param demand_id
	 * @return
	 */
	public Order selectOrderByDemand_id(Integer demand_id);
}
