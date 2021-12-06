package com.mes.web;

import com.google.gson.Gson;
import com.mes.pojo.Book;
import com.mes.pojo.Cart;
import com.mes.pojo.CartItem;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends BaseServlet {
    private BookService bookService = new BookServiceImpl();

    /**
     * 修改商品数量
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获得参数，商品id和修改的商品数量
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 1);
        // 2.从session中拿到购物车对象，调用方法删除商品
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.updateCount(id,count);
            // 3. 重定向到购物车页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
    /**
     * // 删除商品项
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1.获得参数，商品id
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 2.从session中拿到购物车对象，调用方法删除商品
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.deleteItem(id);
            // 3. 重定向到购物车页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
    /**
     * // 清空购物车
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void clearItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 从session中拿到购物车对象，调用方法删除商品
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.clear();
            //  重定向到购物车页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用bookService 获得图书信息
        Book book = bookService.queryBookById(id);
        // 将图书信息转换成商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        // 防止session丢失
        HttpSession session = request.getSession();
        String id1 = session.getId();
        Cookie jsessionid = new Cookie("JSESSIONID", id1);
        response.addCookie(jsessionid);
        // 设置最后一添加到购物车的商品名称到session中
        session.setAttribute("lastname",cartItem.getName());
        // 将商品项添加到购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            request.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
        System.out.println(cart);
        // 重定向到商品首页
        response.sendRedirect(request.getHeader("Referer"));
    }
    protected void ajaxAddItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取商品编号
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        // 调用bookService 获得图书信息
        Book book = bookService.queryBookById(id);
        // 将图书信息转换成商品项
        CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());
        // 防止session丢失
        HttpSession session = request.getSession();
        String id1 = session.getId();
        Cookie jsessionid = new Cookie("JSESSIONID", id1);
        response.addCookie(jsessionid);
        // 设置最后一添加到购物车的商品名称到session中
        session.setAttribute("lastname",book.getName());
        // 将商品项添加到购物车
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart == null){
            cart = new Cart();
            request.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
        System.out.println(cart);
        // 6. 将返回的数据（购物车的总商品数量和商品名称）包装成map对象,并且转换成Json字符串返回给用户
        Map<String,Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("lastname",cartItem.getName());
        stringObjectMap.put("totalCount",cart.getTotalCount());
        Gson gson = new Gson();
        String toJson = gson.toJson(stringObjectMap);

        // 使用输出流返回字符串
        response.getWriter().write(toJson);
    }

}
