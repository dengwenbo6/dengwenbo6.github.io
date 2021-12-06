<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>
<%--		静态包含base标签、css样式，jquary文件--%>
<%@ include file="/pages/common/head.jsp"%>

	<script type="text/javascript">
		$(function () {
			//  给删除按钮添加单击事件，单击事件返回true表示执行跳转操作，返回false表示不执行跳转操作
			$("a.deleteClass").click(function () {
				// confirm 表示提示框函数，内部的变量就是显示的文本
				// 当点击确定时就会返回 true 点击取消就会返回false
				// 这里this表示现在取到的a标签对象，需要找到爷爷标签，在寻到到第一个表示书名的标签页的内容
				return confirm("确定要删除【"+$(this).parent().parent().find("td:first").text()+"】吗？")
			})
		})
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">图书管理系统</span>
			<%--			静态包含管理订单的菜单栏--%>
			<%@ include file="/pages/common/manager_menu.jsp"%>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>名称</td>
				<td>价格</td>
				<td>作者</td>
				<td>销量</td>
				<td>库存</td>
				<td colspan="2">操作</td>
			</tr>


			<c:forEach items="${requestScope.page.items}" var="book">
				<tr>
					<td>${book.name}</td>
					<td>${book.price}</td>
					<td>${book.author}</td>
					<td>${book.sales}</td>
					<td>${book.stock}</td>
					<td><a href="manager/BookServlet?action=getBook&id=${book.id}&method=update&pageNo=${requestScope.page.pageNo}">修改</a></td>
					<td><a class="deleteClass" href="manager/BookServlet?action=delete&id=${book.id}&pageNo=${requestScope.page.pageNo}">删除</a></td>
				</tr>
			</c:forEach>

			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="pages/manager/book_edit.jsp?method=add&pageNo=${requestScope.page.pageTotal}">添加图书</a></td>
			</tr>	
		</table>
<%--		静态包含分页条--%>
		<%@include file="/pages/common/page_nav.jsp"%>

	</div>



	<%--	静态包含页脚内容--%>
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>