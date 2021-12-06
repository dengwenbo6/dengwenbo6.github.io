package com.mes.web;

import com.google.gson.Gson;
import com.mes.pojo.User;
import com.mes.service.UserService;
import com.mes.service.impl.UserServiceImpl;
import com.mes.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends BaseServlet {
    protected void ajaxExistsUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取请求的参数username
        String username = request.getParameter("username");
        // 2. 调用Service层的方法判断用户名是否存在
        UserServiceImpl userService = new UserServiceImpl();
        boolean existsUsername = userService.existsUsername(username);
        // 把返回的结果封装成Map对象
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("existsUsername",existsUsername);
        // 将Map封装成Json对象返回
        Gson gson = new Gson();
        String toJson = gson.toJson(hashMap);

        // 通过响应的字符输出流，将值返回
        response.getWriter().write(toJson);
    }


    /**
     * 注销
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 销毁session中用户登录的信息
        request.getSession().invalidate();
        // 2. 重定向到首页
        response.sendRedirect(request.getContextPath());
    }
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService userService = new UserServiceImpl();
        // 1.登录页面获取请求参数（用户名和密码）
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 2. 调用service.Login()处理业务
        User login = userService.login(new User(null, username, password, null));
        if (login == null) {
            //如果login==null 表示登录失败  返回登录页面
            request.setAttribute("msg", "用户或密码错误");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request, response);

        } else {
            // 否则表示登陆成功
            // 这时需要保存用户名到session中，方便登录成功后在界面显示用户名，这里直接保存用户对象
            HttpSession session = request.getSession();
            session.setAttribute("username",username);
            session.setAttribute("user",login);
            String id = session.getId();
            Cookie jsessionid = new Cookie("JSESSIONID", id);
            response.addCookie(jsessionid);
            // 跳转到登陆成功界面
            request.getRequestDispatcher("/pages/user/login_success.jsp").forward(request, response);
        }
    }

    /**
     * 解决防止session丢失的情况，需要重定向注册页面
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected void registCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {
        // 先创建一个session，并将session保存到cookie中
        HttpSession session = request.getSession();
        String id = session.getId();
        Cookie jsessionid = new Cookie("JSESSIONID", id);
        response.addCookie(jsessionid);
        response.sendRedirect("pages/user/regist.jsp");

    }
    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {
        UserService userService = new UserServiceImpl();
        HttpSession session = request.getSession();
        // 获取session中的验证码
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        // 删除session中的验证码
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);

        // 因为有密码，所以必须使用post请求
        // 1.获取请求的参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email  = request.getParameter("email");
        String code = request.getParameter("code");

        //  将请求中的参数传递给javaBean对象，然后将对象返回
        User user =WebUtils.copyParamToBean(request.getParameterMap(),new User());


        // 2. 验证验证码是否正确  ---要求验证码为abcde    使用equalsIgnoreCase 进行比较时需要忽略大小写
        if(token.equalsIgnoreCase(code)){
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
                session.setAttribute("username",username);
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
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        String action = request.getParameter("action");
//////        if ("login".equals(action)) {
//////            login(request,response);
//////        } else if ("regist".equals(action)) {
//////            regist(request,response);
//////        }
//////        代替if else 调用方法
////        try {
////            //  获取action业务鉴别字符串，获取相应的业务
////            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
////            //  调用目标业务逻辑
////            method.invoke(this,request,response);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
}
