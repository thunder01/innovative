package com.innovative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovative.bean.Order;
import com.innovative.dao.OrderDao;


/**
 * 处理订单业务逻辑
 * @author fzy
 * */
@Service
public class OrderService {
	@Autowired OrderDao orderDao;
	
	/**
	 * 新增订单信息
	 * @param order 订单对象
	 * @return 受影响的行数
	 * */
	public int insertOrder(Order order){
		return orderDao.insertOrder(order);
	}
	
	/**
	 * 寻源工程师下单操作，更新已有的订单
	 * @param order 订单对象
	 * @return
	 * */
	public int updateOrderLate_byId(Order order){
		return orderDao.updateOrderLate_byId(order);
	}
	
	/**
	 * 根据立项表单的id,查找出对应的订单id
	 * @param id 立项表单id
	 * @return
	 * */
	public int selectOrderIdByApproval(Integer id){
		return orderDao.selectOrderIdByApproval(id);
	}
}
