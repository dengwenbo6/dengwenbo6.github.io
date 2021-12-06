package com.mes.test;

import com.mes.dao.OrderItemDao;
import com.mes.dao.impl.OrderItemDaoImpl;
import com.mes.pojo.OrderItem;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemDaoImplTest {
    public OrderItemDao orderItemDao = new OrderItemDaoImpl();
    @Test
    public void saveOrderItem() {
        orderItemDao.saveOrderItem(new OrderItem(null,"13245",45,new BigDecimal(452),new BigDecimal(452),"134568"));

    }

    @Test
    public void queryOrderItemsByOrderId() {
        List<OrderItem> orderItems = orderItemDao.queryOrderItemsByOrderId("134568");
        for (OrderItem orderItem : orderItems) {
            System.out.println(orderItem);
        }
    }

}