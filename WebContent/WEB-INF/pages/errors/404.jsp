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
<div class="container" style="margin-top: 160px;">
<h3><a href="${pageContext.request.contextPath}/"><img alt="真是抱歉!" src="${pageContext.request.contextPath}/static/img/sorry.png" style="width:80px;"></a><span class="label label-default">非常抱歉，找不到您要的网页。点我返回...</span> </h3>
</div>
</body>
</html>