<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找不到网页</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless1.5.1/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container text-center" style="margin-top: 160px;">
	<h2><span class='text-default'>404</span> <span class='text-success'>找不到页面</span></h2>
	<p>
		<a href="${pageContext.request.contextPath}/">
			<i class='fa fa-2x fa-home'></i>
		</a>
	</p>
</div>
</body>
</html>