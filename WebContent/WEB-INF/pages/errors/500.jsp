<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台错误</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/bootstrap/css/bootstrap.min.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/bootstrap/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container text-center" style="margin-top: 60px;">
	<h2><span class="text-default">500</span> <span class="text-success">抱歉，系统内部出错了</span></h2>
	<p><a href="${pageContext.request.contextPath}/"><i class='fa fa-2x fa-home'></i></a></p>
	<div class='row'>
		<div class='col-md-2 text-right'>错误:</div>
		<div class='col-md-10'>${pageContext.exception}</div>
	</div>
	<div class='row'>
		<div class='col-md-2 text-right'>链接:</div>
		<div class='col-md-10'>${pageContext.errorData.requestURI}</div>
	</div>
	<div class='row'>
		<div class='col-md-2 text-right' onclick="show()">详情:</div>
		<div class='col-md-10' id='divDetail'>
			<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
				<p>${trace}</p>
			</c:forEach>		
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	function show() {
		$('#divDetail').toggle();
	}
	
	$(function(){show();});
</script>
</html>