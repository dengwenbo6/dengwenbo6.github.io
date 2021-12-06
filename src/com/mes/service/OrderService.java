package com.mes.service;

import com.mes.pojo.Cart;
import com.mes.pojo.Order;
import com.mes.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    public String createOrder(Cart cart, Integer userId);


    public  List<Order> showAllOrders();

    public void sendOrder(String orderId);

    public List<OrderItem> showOrderDetail(String orderId);

    public List<Order> showMyOrders(int userId);

    public void receiverOrder(String orderId);
}
