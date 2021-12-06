package com.mes.web;

import com.mes.pojo.User;
import com.mes.service.UserService;
import com.mes.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    // 导入业务逻辑接口
    private UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1.登录页面获取请求参数（用户名和密码）
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 2. 调用service.Login()处理业务
        User login = userService.login(new User(null, username, password, null));
        if(login==null){
            //如果login==null 表示登录失败  返回登录页面
            request.setAttribute("msg","用户或密码错误");
            request.setAttribute("username",username);
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);

        }else {
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request,response);
        }
    }
}
