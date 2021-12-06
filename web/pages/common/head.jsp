<%--
  Created by IntelliJ IDEA.
  User: 文博
  Date: 2021/10/22
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--实现动态的获取ip地址和端口号，以及项目名称--%>
<% String basePath = request.getScheme()
        +"://"
        +request.getServerName()
        +":"
        +request.getServerPort()
        +"/"
        +request.getContextPath()
        +"/";
    pageContext.setAttribute("basePath",basePath);
%>
<%--<%=basePath %>--%>
<!--写base标签，永远固定相对路径跳转的结果-->
<base href="<%=basePath %>">

<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
