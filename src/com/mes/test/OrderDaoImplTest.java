package com.mes.test;

import com.mes.dao.OrderDao;
import com.mes.dao.impl.OrderDaoImpl;
import com.mes.pojo.Order;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDaoImplTest {
    public OrderDao orderDao = new OrderDaoImpl();
    @Test
    public void saveOrder() {
        int i = orderDao.saveOrder(new Order("134569", new Date(), new BigDecimal(100), 0, 1));
        System.out.println(i);

    }

    @Test
    public void queryOrders() {
        List<Order> orders = orderDao.queryOrders();
        for (Order order : orders) {
            System.out.println(order);
        }

    }
    @Test
    public void changeOrderStatus() {
        orderDao.changeOrderStatus("134568",1);

    }

    @Test
    public void queryOrdersByUserId() {
        List<Order> orders = orderDao.queryOrdersByUserId(5);
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}