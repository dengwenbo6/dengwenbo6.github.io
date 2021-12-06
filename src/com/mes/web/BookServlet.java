package com.mes.web;

import com.mes.pojo.Book;
import com.mes.pojo.Page;
import com.mes.service.BookService;
import com.mes.service.impl.BookServiceImpl;
import com.mes.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/manager/BookServlet")
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取参数编号  pageNo，pageSize
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        // 2. 调用service获得page对象
        Page<Book> page = bookService.page(pageNo,pageSize);
        page.setUrl("manager/BookServlet?action=page");
        // 3. 将图书信息保存到request域中
        request.setAttribute("page",page);
        // 4. 请求转发到book_edit.jsp 页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);

    }

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 添加之后跳转到最后一页
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 0);
        pageNo+=1;
        // 1. 获取请求的参数  封装成Book对象
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        // 2.调用service层的接口  BookService.addBook()保存图书
        bookService.addBook(book);
        // 3. 跳到图书列表页面，
//        request.getRequestDispatcher("/manager/BookServlet?action=page").forward(request,response);
            // 这里使用F5会重新访问这个请求，所以请求转发就会再一次添加一本图书，所以使用重定向就不会有这样的问题
        // 这里的路径需要加上项目名称     request.getContextPath()
        response.sendRedirect(request.getContextPath()+"/manager/BookServlet?action=page&pageNo="+pageNo);
    }
    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取图书的id
        // 为了方便将id转换为int型，将这个方法封装到WebUtils中
//        String id = request.getParameter("id");
//            // 转换类型为int型
//        int i = 0;
//        try {
//            i = Integer.parseInt(id);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
        int id = WebUtils.parseInt(request.getParameter("id"), 0);

        // 2. 调用service接口删除图书
        bookService.deleteBookById(id);

        // 3. 重定向到图书管理页面
        response.sendRedirect(request.getContextPath()+"/manager/BookServlet?action=page&pageNo="+request.getParameter("pageNo"));
    }
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获取请求的参数，封装成Book对象
        Book book = WebUtils.copyParamToBean(request.getParameterMap(), new Book());
        // 2. 调用service方法修改图书信息
        bookService.updateBook(book);
        // 3. 重定向到图书管理页面
        response.sendRedirect(request.getContextPath()+"/manager/BookServlet?action=page&pageNo="+request.getParameter("pageNo"));
    }
    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 通过BookService获得全部图书
        List<Book> books = bookService.queryBooks();
        // 2. 把全部图书保存到request域中
        request.setAttribute("books",books);
        // 3. 请求转发到pages/manage/manager.jsp页面
        request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);

    }
    protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取图书编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 2. 调用service获得需要修改的图书信息
        Book book = bookService.queryBookById(id);
        // 3. 将图书信息保存到request域中
        request.setAttribute("book",book);
        // 4. 请求转发到book_edit.jsp 页面
        request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request,response);

    }
}
