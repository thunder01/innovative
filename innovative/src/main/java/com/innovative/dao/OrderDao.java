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
	
}
