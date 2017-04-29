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
    <script type="text/javascript">
    	document.cookie = "mobile=true";
    </script>
  </head>
  <body>
		<div class="login-wrapper">
			<div class="login-widget animation-delay1">
				<div class="panel panel-default">
					<div class="panel-heading clearfix">
						<div class="col-xs-10 col-xs-offset-1">
							<img src="${pageContext.request.contextPath}/static/img/logo.png" style="height: 60px;">
						</div>
					</div> <!-- panel heading -->

				<div class="panel-body">
					<c:url value="/login?m=true" var="loginUrl" />
					<form class="form-horizontal" method="POST" action="${loginUrl}">
						<div class="form-group">
							<div class="col-xs-10 col-xs-offset-1" >
							<input type="text" class="form-control input-lg" name="username" autofocus
								placeholder="username" style="font-size: 18px;">
							</div>
						</div>
						<div class="form-group">
							<div class="col-xs-10 col-xs-offset-1" >
							<input type="password"  class="form-control input-lg" name="password"
								placeholder="password" style="font-size: 18px;">
							</div>
						</div>

						<hr/>
						<button type="submit" class="btn btn-success btn-lg col-xs-10 col-xs-offset-1">登录</button>
					</form>
				</div>
			</div><!-- /panel -->
		</div><!-- /login-widget -->
	</div><!-- /login-wrapper -->

  </body>

</html>
