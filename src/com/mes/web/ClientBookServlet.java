package com.mes.web;

import com.mes.pojo.Book;
import com.mes.pojo.Page;
import com.mes.service.BookService;
import com.mes.service.impl.BookServiceImpl;
import com.mes.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ClientBookServlet", value = "/Client/BookServlet")
public class ClientBookServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();


    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("clientBookServlet");
        // 进入页面先创建session，并将session保存到cookie中
        HttpSession session = request.getSession();
        String id = session.getId();
        Cookie jsessionid = new Cookie("JSESSIONID", id);
        response.addCookie(jsessionid);
        // 1. 获取参数编号  pageNo，pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        // 2. 调用service获得page对象
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("Client/BookServlet?action=page");
        // 3. 将图书信息保存到request域中
        request.setAttribute("page",page);
        // 4. 请求转发到book_edit.jsp 页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request,response);
    }
    protected void pageByPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("clientBookServlet");
        // 1. 获取参数编号  pageNo，pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(request.getParameter("min"), 0);
        int max = WebUtils.parseInt(request.getParameter("max"), Integer.MAX_VALUE);
        // 2. 调用service获得page对象
        Page<Book> page = bookService.pageByPrice(pageNo,pageSize,min,max);
        StringBuilder stringBuilder = new StringBuilder("Client/BookServlet?action=pageByPrice");
        // 如果min有值，就追加到参数中
        if(request.getParameter("min")!= null){
            stringBuilder.append("&min=").append(request.getParameter("min"));
        }
        // 如果max有值，就追加到参数中去
        if(request.getParameter("max")!= null){
            stringBuilder.append("&max=").append(request.getParameter("max"));
        }

        page.setUrl(stringBuilder.toString());
        // 3. 将图书信息保存到request域中
        request.setAttribute("page",page);
        // 4. 请求转发到book_edit.jsp 页面
        request.getRequestDispatcher("/pages/client/index.jsp").forward(request,response);
    }
}
