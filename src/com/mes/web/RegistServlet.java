package com.mes.web;

import com.mes.pojo.User;
import com.mes.service.UserService;
import com.mes.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class RegistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        // 因为有密码，所以必须使用post请求
        // 1.获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email  = request.getParameter("email");
        String code = request.getParameter("code");
        // 2. 验证验证码是否正确  ---要求验证码为abcde    使用equalsIgnoreCase 进行比较时需要忽略大小写
        if("abcde".equalsIgnoreCase(code)){
            // 正确
            //  3.检查用户名是否正确  这里需要调用service层的 业务逻辑 检查用户名
            if(userService.existsUsername(username)){
                System.out.println("用户名["+username+"]已存在，不可用");
                // 设置错误显示信息
                request.setAttribute("mes","用户名已存在！！");
                request.setAttribute("email",email);
                request.setAttribute("username",username);
                // 如果存在，说明用户名不可以用  跳转回到注册页
                request.getRequestDispatcher("/pages/user/regist.jsp").forward(request,response);

            }else {
                // 如果不存在，说明用户名可以用 调用service保存数据，跳到注册成功界面
                userService.registerUser(new User(null,username,password,email));
                request.getRequestDispatcher("/pages/user/regist_success.jsp").forward(request,response);
            }

        }else {
            // 不正确

            // 设置错误显示信息
            request.setAttribute("msg","验证码错误！");
            request.setAttribute("email",email);
            request.setAttribute("username",username);
            //  跳回注册页面
            System.out.println("验证码["+code+"]错误");
            request.getRequestDispatcher("/pages/user/regist.jsp").forward(request,response);

        }
    }
}
