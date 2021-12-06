package com.mes.web;

import com.mes.pojo.Cart;
import com.mes.pojo.Order;
import com.mes.pojo.OrderItem;
import com.mes.pojo.User;
import com.mes.service.OrderService;
import com.mes.service.impl.OrderServiceImpl;
import com.mes.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderServiceImpl();

    /**
     * 生产订单
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.从session中获得购物车的信息
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        // 2. 获得用户id的参数
        User user = (User) request.getSession().getAttribute("user");
        // 这里需要判断用户是否登录
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/pages/user/login.jsp");
            return;
        }
        Integer userId = user.getId();
        // 3.调用Service层的接口CreateOrder来创建订单，并返回订单号
        String orderId = null;
        try {
            orderId = orderService.createOrder(cart, userId);
            JdbcUtils.commitAndClose();  // 提交事务并关闭
        } catch (Exception e) {
            JdbcUtils.rollbackAndClose(); // 回滚事务
            e.printStackTrace();
        }
        // 5.将订单号返回给界面
        request.getSession().setAttribute("orderId", orderId);
        // 4. 跳转到订单创建完成的界面
        response.sendRedirect(request.getContextPath() + "/pages/cart/checkout.jsp");
    }

    /**
     * 展示所有订单
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void showAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.使用Service层的接口 showAllOrders() 查询全部订单，并且返回订单类
        List<Order> orders = orderService.showAllOrders();
        // 2.将订单类保存到session中
        request.getSession().setAttribute("orders", orders);
        // 3.重定向到/pages/manager/order_manager.jsp 页面
        response.sendRedirect(request.getContextPath() + "/pages/manager/order_manager.jsp");
    }

    protected void sendOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 先获得OrderId
        String orderId = request.getParameter("id");
        // 2. 调用Service层的接口，OrderService.sendOrder(OrderId) 来改变订单状态
        orderService.sendOrder(orderId);
        // 3. 重定向到原来的界面
        response.sendRedirect(request.getContextPath()+"/OrderServlet?action=showAllOrders");
    }
    protected void showOrderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取订单id字符串
        String orderId = request.getParameter("id");
        // 2.调用Service 程序，展示订单详情，返回订单项对象
        List<OrderItem> orderItems = orderService.showOrderDetail(orderId);
        // 3. 将订单项对象保存到request中
        request.getSession().setAttribute("orderItems",orderItems);
        // 4. 重定向到订单详情页/pages/order/order_detail.jsp
        response.sendRedirect(request.getContextPath()+"/pages/order/order_detail.jsp");
    }

    protected void showMyOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.现获取用户id userId
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        // 2. 调用Service层的接口 查找订单，返回订单列表
        List<Order> orders = orderService.showMyOrders(userId);
        // 3. 将订单列表保存到session中
        request.getSession().setAttribute("orders",orders);
        // 4. 重定向到我的订单页面
        response.sendRedirect(request.getContextPath()+"/pages/order/order.jsp");
    }

    /**
     * 签收订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void receiverOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.先获取订单id
        String orderId = request.getParameter("orderId");
        // 2. 调用Service接口
        orderService.receiverOrder(orderId);
        // 3. 重定向到我的订单界面
        response.sendRedirect("OrderServlet?action=showMyOrder");
    }
}







