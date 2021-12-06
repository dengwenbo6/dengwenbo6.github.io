<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单管理</title>
<%--		静态包含base标签、css样式，jquary文件--%>
<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			$("button.sendOrder").click(function () {
				location.href = "${basePath}OrderServlet?action=sendOrder&id="+$(this).parent().parent().find("td:first").text();
			})
		})
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">订单管理系统</span>
<%--			静态包含管理订单的菜单栏--%>
			<%@ include file="/pages/common/manager_menu.jsp"%>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>订单号</td>
				<td>日期</td>
				<td>金额</td>
				<td>详情</td>
				<td>发货</td>
				
			</tr>		

			<c:forEach items="${sessionScope.orders}" var="order">
			<tr>
				<td>${order.order_id}</td>
				<td>${order.create_time}</td>
				<td>${order.price}</td>
				<td><a href="OrderServlet?action=showOrderDetail&id=${order.order_id}">查看详情</a></td>
<%--				当状态为0表示未发货，需要点击发货来进行发货--%>
				<c:if test="${order.status==0}">
<%--					<td><button class="sendOrder">发货</button></td>--%>
					<td><a href="OrderServlet?action=sendOrder&id=${order.order_id}">发货</a></td>
				</c:if>
				<c:if test="${order.status==1}">
				<td>已发货，待签收</td>
				</c:if>
				<c:if test="${order.status==2}">
				<td>已签收</td>
				</c:if>
			</tr>
			</c:forEach>
		</table>
	</div>

	<%--	静态包含页脚内容--%>
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>