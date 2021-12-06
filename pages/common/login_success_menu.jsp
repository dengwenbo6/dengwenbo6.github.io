<%--
  Created by IntelliJ IDEA.
  User: 文博
  Date: 2021/10/22
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <span>欢迎<span class="um_span">${sessionScope.username}</span>光临wenbo书城</span>
    <a href="OrderServlet?action=showMyOrder">我的订单</a>
    <a href="UserServlet?action=logout">注销</a>&nbsp;&nbsp;
    <a href="index.jsp">返回</a>
</div>