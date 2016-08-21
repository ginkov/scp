<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/static/favicon.ico" type="image/x-icon" />
    <link href="${pageContext.request.contextPath}/static/Endless-1.5.1/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/font-awesome.min.css" rel="stylesheet" >
	<link href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/endless.min.css" rel="stylesheet">
	<style type="text/css">
		body { font-family: "Microsoft YaHei", sans-serif, monospace; }
	</style>
  </head>
  <body>
		<div class="login-wrapper">
			<div class="text-center">
				<h2 style="font-weight:bold">
					<!-- <span class="text-success">佳耘</span> <span style="color:#ccc; text-shadow:0 1px #fff">销售・客户关系・产品 管理</span> -->
				</h2>
			</div>
			<div class="login-widget animation-delay1">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<div style="margin-left: 10px;">
							<img src="${pageContext.request.contextPath}/static/img/logo.png" style="height: 35px;">
						</div>

					</div> <!-- panel heading -->

				<div class="panel-body">
					<!-- <form class="form-horizontal" method="POST" action="j_security_check"> -->
					<c:url value="/login" var="loginUrl" />
					<form class="form-horizontal" method="POST" action="${loginUrl}">
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 col-xs-offset-1 control-label"><i class="fa fa-user fa-lg"></i></label>
							<div class="col-sm-7 col-xs-7">
							<input type="text" placeholder="username" class="form-control input-sm" name="username" autofocus>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 col-xs-2 col-xs-offset-1 control-label"> <i class="fa fa-key fa-md"></i></label>
							<div class="col-sm-7 col-xs-7">
							<input type="password" placeholder="password" class="form-control input-sm" name="password">
							</div>
						</div>

						<hr/>

						<button type="submit" class="btn btn-success btn-md pull-right">登录</button>
					</form>
				</div>
			</div><!-- /panel -->
		</div><!-- /login-widget -->
	</div><!-- /login-wrapper -->

  </body>

</html>
