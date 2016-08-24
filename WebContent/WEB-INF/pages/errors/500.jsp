<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台错误</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless1.5.1/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container" style="margin-top: 80px;">
<h3><a href="${pageContext.request.contextPath}/"><img alt="真是抱歉!" src="${pageContext.request.contextPath}/static/img/sorry.png" style="width:120px;"></a><span class="label label-default">非常抱歉，后台出错了！点我返回...</span> </h3>

<br>
<table class="table">
	<tr>
		<td width="20%"><b>错误:</b></td>
		<td>${pageContext.exception}</td>
	</tr>
	<tr>
		<td><b>URI:</b></td>
		<td>${pageContext.errorData.requestURI}</td>
	</tr>
	<tr>
		<td><b>状态代码:</b></td>
		<td>${pageContext.errorData.statusCode}</td>
	</tr>
	<tr>
		<td><b>错误追溯:</b></td>
		<td>
			<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
				<p>${trace}</p>
			</c:forEach>
		</td>
	</tr>
</table>
</div>
</body>
</html>