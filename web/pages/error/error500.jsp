<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>书城首页</title>
<%--		静态包含base标签、css样式，jquary文件--%>
<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			$("button.addCart").click(function () {
				var bookId = $(this).attr("bookId");
				location.href = "http://localhost:8080//BookCityProject/CartServlet?action=addItem&id="+bookId;
			});
		})
	</script>
</head>
<body>

<a href="index.jsp">返回首页</a>
服务器出错
	<%--	静态包含页脚内容--%>
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>