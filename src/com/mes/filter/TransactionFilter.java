package com.mes.filter;

import com.mes.utils.JdbcUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "TransactionFilter",urlPatterns = "/*")
public class TransactionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request,response);
            JdbcUtils.commitAndClose();  // 提交事务
        } catch (Exception e) {
            JdbcUtils.rollbackAndClose();  //回滚事务
            e.printStackTrace();
            throw new RuntimeException(e);  // 将错误抛给Tomcat服务器进行展示
        }
    }
}
