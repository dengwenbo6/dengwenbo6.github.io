package com.mes.dao;

import com.mes.pojo.Order;

import java.util.List;

public interface OrderDao {
    // 保存订单
    public int saveOrder(Order order);

    public List<Order> queryOrders();


    public void changeOrderStatus(String orderId,Integer status);

    public List<Order> queryOrdersByUserId(int userId);
}
