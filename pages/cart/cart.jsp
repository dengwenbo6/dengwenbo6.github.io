<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
<%--		静态包含base标签、css样式，jquary文件--%>
<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			$("a.deleteItem").click(function () {
				return confirm("你确定要删除【"+$(this).parent().parent().find("td:first").text()+"】吗？")
			})
			$("#clearCart").click(function () {
				return confirm("你确定要清空购物车吗？")
			})
			// 给修改的数字绑定，当内容发生改变就触发事件
			$(".updateCount").change(function () {
				if (confirm("你确定将商品【"+$(this).parent().parent().find("td:first").text()+"】修改数量为："+$(this).val()+"吗？")) {
					var id = $(this).attr("bookId");
					location.href = "${basePath}CartServlet?action=updateCount&count="+$(this).val()+"&id="+id;
				}else{
					// defaultValue 表示表单项中的默认value值
					this.value = this.defaultValue;
				}

			})
		})
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">购物车</span>
			<c:if test="${not empty sessionScope.user}" >
				<%@ include file="/pages/common/login_success_menu.jsp"%>
			</c:if>
			<c:if test="${empty sessionScope.user}">
				<div>
				<a href="index.jsp">返回商城</a>
				</div>
			</c:if>
	</div>
	
	<div id="main">
	
		<table>

			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>
			<c:if test="${empty sessionScope.cart.items}">
				<tr>
					<td colspan="5"><a href="index.jsp">亲，当前购物车为空！快去浏览商品吧~</a></td>
				</tr>
			</c:if>
			<c:if test="${ not empty sessionScope.cart.items}">
			<c:forEach items="${sessionScope.cart.items}" var="entry">
				<tr>
					<td>${entry.value.name}</td>
					<td>
						<input type="text" bookId="${entry.value.id}" class="updateCount" style="width: 80px" value="${entry.value.count}">
					</td>
					<td>${entry.value.price}</td>
					<td>${entry.value.totalPrice}</td>
					<td><a class="deleteItem" href="CartServlet?action=deleteItem&id=${entry.value.id}">删除</a></td>
				</tr>
			</c:forEach>
			</c:if>
			</table>
			<c:if test="${not empty sessionScope.cart.items}">
				<div class="cart_info">
					<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
					<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
					<span class="cart_span"><a id="clearCart" href="CartServlet?action=clearItem">清空购物车</a></span>
					<span class="cart_span"><a href="OrderServlet?action=createOrder">去结账</a></span>
				</div>
			</c:if>
	</div>

	<%--	静态包含页脚内容--%>
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>