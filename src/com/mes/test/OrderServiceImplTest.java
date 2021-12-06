package com.mes.test;

import com.mes.pojo.Cart;
import com.mes.pojo.CartItem;
import com.mes.pojo.Order;
import com.mes.pojo.OrderItem;
import com.mes.service.OrderService;
import com.mes.service.impl.OrderServiceImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImplTest {
    public OrderService orderService = new OrderServiceImpl();
    @Test
    public void createOrder() {
        Cart cart = new Cart();
        cart.addItem(new CartItem(1,"1234567",1,new BigDecimal(10),new BigDecimal(10)));
        cart.addItem(new CartItem(2,"1234567",1,new BigDecimal(20),new BigDecimal(20)));
        cart.addItem(new CartItem(1,"1852as",3,new BigDecimal(10),new BigDecimal(30)));
        String orderId = orderService.createOrder(cart, 1);
        System.out.println(orderId);
    }

    @Test
    public void showAllOrders() {
        // 调用Dao接口queryOrders() 查询订单信息
        for (Order order : orderService.showAllOrders()) {
            System.out.println(order);
        }
    }

    @Test
    public void sendOrder() {
        // 调用service层的接口，实现改变订单状态的功能
        orderService.sendOrder("134568");
    }
    @Test
    public void showOrderDetail() {
        List<OrderItem> orderItems = orderService.showOrderDetail("134568");
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }
    }
    @Test
    public void showMyOrders() {
        List<Order> orders = orderService.showMyOrders(5);
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}