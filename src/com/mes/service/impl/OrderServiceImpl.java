package com.mes.service.impl;

import com.mes.dao.BookDao;
import com.mes.dao.OrderDao;
import com.mes.dao.OrderItemDao;
import com.mes.dao.impl.BookDaoImpl;
import com.mes.dao.impl.OrderDaoImpl;
import com.mes.dao.impl.OrderItemDaoImpl;
import com.mes.pojo.*;
import com.mes.service.OrderService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();

    /**
     * 签收订单接口
     * @param orderId
     */
    @Override
    public void receiverOrder(String orderId) {
        // 调用Dao接口改变订单状态
        // 设定改变订单状态为已签收，status==2
        Integer status = 2;
        orderDao.changeOrderStatus(orderId,status);

    }

    @Override
    public List<Order> showMyOrders(int userId) {
        // 从Dao层获取订单 ,返回订单列表
        List<Order> orders = orderDao.queryOrdersByUserId(userId);
        return orders;
    }

    /**
     * 展示订单详情页
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItem> showOrderDetail(String orderId) {
        // 调用Dao接口实现通过订单ID查询订单项，并且返回订单项列表
        List<OrderItem> orderItems = orderItemDao.queryOrderItemsByOrderId(orderId);
        return orderItems;
    }

    /**
     * 展示所有订单
     * @return
     */
    @Override
    public List<Order> showAllOrders() {
        // 调用Dao接口queryOrders() 查询订单信息
        List<Order> orders = orderDao.queryOrders();
        return orders;
    }

    /**
     * 发货
     * @param orderId
     */
    @Override
    public void sendOrder(String orderId) {
        // 调用service层的接口，实现改变订单状态的功能
        // 当发货时，改变订单状态为1，表示已发货
        Integer status = 1;
        orderDao.changeOrderStatus(orderId,status);
    }

    @Override
    public String createOrder(Cart cart, Integer userId) {
        // 这里还需要orderId 为了保证唯一性，需要用时间戳加用户名进行保存
        String orderId =  System.currentTimeMillis()+""+userId;
        orderDao.saveOrder(new Order(orderId,new Date(),cart.getTotalPrice(),0,userId));

        // 遍历购物车中的每一个商品项，保存作为订单项
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            // 拿到购物车中的每一个商品项
            CartItem value = entry.getValue();
            // 生成一个订单项
            OrderItem orderItem = new OrderItem(null, value.getName(), value.getCount(), value.getPrice(), value.getTotalPrice(), orderId);
            // 将订单项保存到订单项表中
            orderItemDao.saveOrderItem(orderItem);

            // 更新书籍的库存和销量
            // 先拿到一本书，更改信息
            Book book = bookDao.queryBookById(value.getId());
            book.setSales(book.getSales()+value.getCount());
            book.setStock(book.getStock()-value.getCount());
            // 将更改后的图书信息保存到数据库中
            bookDao.updateBook(book);
        }
        // 清空购物车
        cart.clear();
        return orderId;
    }
}
