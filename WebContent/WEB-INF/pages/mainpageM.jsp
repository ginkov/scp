<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<% request.setCharacterEncoding("utf-8"); %>
<!doctype html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
	<title>${pageTitle}</title>

	<link rel="shortcut icon" href="${pageContext.request.contextPath}/static/favicon.ico" type="image/x-icon" />

	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/pace.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/colorbox/colorbox.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/jquery.dataTables_themeroller.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/TableTools-2.2.4/css/dataTables.tableTools.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/datepicker.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/endless.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Endless-1.5.1/css/endless-skin.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/chosen_v1.5.1/chosen.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/select2-4.0.3/select2.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/jstree/dist/themes/proton/style.min.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/uncompressed/pace.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/jquery.popupoverlay.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/uncompressed/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/TableTools-2.2.4/js/dataTables.tableTools.min.js"></script>
<!-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/locale/bootstrap-datepicker.zh-CN.js" charset="UTF-8"></script> -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/uncompressed/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/modernizr.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/jquery.cookie.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/Endless-1.5.1/js/endless/endless.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/chosen_v1.5.1/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/select2-4.0.3/select2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/jstree/dist/jstree.min.js"></script>
	<style type="text/css">
		body { font-family: "Microsoft YaHei",  sans-serif, monospace; }
		.label-xs {
			margin-top:7px;
			text-align:right;
		}
		.pointer {
			cursor: pointer;
			-webkit-touch-callout: none;
    		-webkit-user-select: none;
    		-khtml-user-select: none;
    		-moz-user-select: none;
    		-ms-user-select: none;
			user-select: none;
		}
		.glink {
			color: #b8860b; font-weight: bold; 
		}
		.no-overflow{
			overflow:hidden; white-space: nowrap; text-overflow:ellipsis;
		}
	</style>
</head>
<body>
	<!-- 采用 endless admin 前端框架构建  -->
	<!-- Main Wrapper -->
    <div id="wrapper">
<!-- Top Navigation -->
    <div id="top-nav" class="fixed skin-6">
			<ul class="nav-notification clearfix">
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown">
						<i class="fa fa-bars fa-lg"></i>
					</a>
					<ul class="dropdown-menu task dropdown-1" style="font-size:14px;">
						<li> 
							<a class="clearfix" href="<c:url value="/sale/order/list?mobile"/>">
								<i class="fa fa-gift fa-lg"></i> 订单管理
							</a>
						</li>
						<li> 
							<a class="clearfix" href="<c:url value="/finance/expense/list?mobile"/>">
								<i class="fa fa-filter fa-lg"></i> 支出管理
							</a>
						</li>
						<li> 
							<a class="clearfix" href="<c:url value="/staff/passwd/change?mobile"/>">
								<i class="fa fa-lock fa-lg"></i> 修改密码 
							</a>
						</li>
						<li> 
							<a class="clearfix" href="<c:url value="/logout?mobile"/>">
								<i class="fa fa-sign-out fa-lg"></i> 退出
							</a>
						</li>
					</ul>
				</li>
			</ul>
    </div>
    <!-- End of Top Navigation -->

 
    <!-- Main Container -->
    <div id="main-container">
    	<jsp:include page="/WEB-INF/pages/${pageContent}.jsp"/>
	</div>
    <!-- End of Main Container -->

    <!-- Footer -->
    <footer>
    </footer> <!-- End of Footer -->

</div> <!-- End of Main Wrapper -->
</body>