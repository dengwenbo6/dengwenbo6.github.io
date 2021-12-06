package com.mes.dao.impl;

import com.mes.dao.OrderDao;
import com.mes.pojo.Order;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {

    @Override
    public int saveOrder(Order order) {
        String sql = "insert into t_order(order_id,create_time,price,status,user_id) value(?,?,?,?,?)";
        return update(sql,order.getOrder_id(),order.getCreate_time(),order.getPrice(),order.getStatus(), order.getUser_id());
    }

    @Override
    public List<Order> queryOrders() {
        String sql = "SELECT `order_id`,`create_time`,`price`,`status`,`user_id` FROM `t_order`;";
        return queryForList(Order.class,sql);
    }

    @Override
    public void changeOrderStatus(String orderId,Integer status) {
        String sql = "UPDATE t_order SET `status`=? WHERE order_id=?";
        int update = update(sql,status,orderId);
    }

    @Override
    public List<Order> queryOrdersByUserId(int userId) {
        String sql = "SELECT `order_id`,`create_time`,`price`,`status`,`user_id` FROM `t_order` where user_id=?";
        return queryForList(Order.class,sql,userId);
    }
}
