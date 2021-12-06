package com.mes.dao.impl;

import com.mes.dao.OrderItemDao;
import com.mes.pojo.OrderItem;

import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {
    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into t_order_item(name,count,price,total_price,order_id) value(?,?,?,?,?)";
        return update(sql,orderItem.getName(),orderItem.getCount(),orderItem.getPrice(), orderItem.getTotal_price(), orderItem.getOrder_id());
    }

    @Override
    public List<OrderItem> queryOrderItemsByOrderId(String orderId) {
        String sql = "SELECT `id`,`name`,`count`,`price`,`total_price`,`order_id` FROM t_order_item WHERE order_id=?";
        List<OrderItem> orderItems = queryForList(OrderItem.class, sql, orderId);
        return orderItems;
    }
}
