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
			<div class="brand"><a href="<c:url value="/index"/>"><img alt="Brand" src="${pageContext.request.contextPath}/static/img/logo.png" style="height: 23px;"></a> 佳耘 SCP </div>
			<button type="button" class="navbar-toggle pull-left hide-menu" id="menuToggle">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<ul class="nav-notification clearfix">
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown"><i class='fa fa-user fa-lg'></i> <%=request.getUserPrincipal().getName()%> <i class="fa fa-angle-down fa-lg"></i></a>
					<ul class="dropdown-menu task dropdown-1" style="width: 60px;">
						<li> <a class="clearfix" href="<c:url value="/staff/passwd/change"/>">修改密码 <i class="fa fa-pencil"></i></a>
						<li> <a class="clearfix" href="<c:url value="/logout"/>">退出 <i class="fa fa-sign-out"></i></a>
					</ul>
				</li>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown">
					<i class="fa fa-question-circle fa-lg"></i>
					</a>
					<ul class="dropdown-menu task dropdown-2" style="width: 60px;">
						<li> <a class="clearfix" href="#" data-toggle="modal" data-target="#modal-help-about">关于</a></li>
						<!-- <li> <a class="clearfix" href="static/html/help/help.html" target="_blank">使用说明</a></li> -->
					</ul>
				</li>
			</ul>
    </div>
    <!-- End of Top Navigation -->

    <!-- Sidebar Menu -->
    <aside class="skin-6">
    	<div class="main-menu">
		    <ul>
		    	<!-- 开始分析，两级菜单 -->
			    <li>
			    	<a href="<c:url value="/sale/order/list"/>">
						<span class="menu-icon"> <i class="fa fa-gift fa-lg"></i> </span>
						<span class="text">销售</span>
						<span class="menu-hover"></span>
					</a>
				</li>
				<!-- 财务 -->				
				<li class="openable">
			    	<a href="#">
						<span class="menu-icon"> <i class="fa fa-usd fa-lg"></i> </span>
						<span class="text">财务</span>
						<span class="menu-hover"></span>
					</a>
					<ul class="submenu">
						<li> <a href="<c:url value="/finance/balance/list"/>"> <span class="submenu-label">账户</span> </a> </li>
						<li> <a href="<c:url value="/finance/expense/list"/>"> <span class="submenu-label">支出</span> </a> </li>
					</ul>
				</li>

			    <!-- 客户-->
			    <li>
				    <a href="<c:url value="/customer/customer/list"/>">
					    <span class="menu-icon"> <i class="fa fa-heart"></i> </span>
					    <span class="text"> 客户 </span>
					    <span class="menu-hover"></span>
				    </a>
			    </li>

			    <!-- 产品 -->
			    <!-- 
			    <li class="openable">
			    	<a href="#">
						<span class="menu-icon"> <i class="fa fa-leaf"></i> </span>
						<span class="text">产品</span>
						<span class="menu-hover"></span>
					</a>
					<ul class="submenu">
						<li> <a href="<c:url value="/product/category/list"/>"><span class="submenu-label">产品类别</span></a></li>
						<li> <a href="<c:url value="/product/series/list"/>"><span class="submenu-label">产品系列</span></a></li>
						<li> <a href="<c:url value="/product/model/list"/>"> <span class="submenu-label">产品型号</span> </a> </li>
					</ul>
			    </li> -->
			    <!-- 管理 -->
			    <sec:authorize access="hasAnyRole('ADMIN','SUPER')">
			    <li class="openable">
			    	<a href='#'>
						<span class="menu-icon"> <i class="fa fa-cog"></i> </span>
						<span class="text">管理</span>
						<span class="menu-hover"></span>
			    	</a>	
			    	<ul class="submenu">
			    		<li class="openable">
			    			<a href='#'> <span class="submenu-label">客户</span> </a>
			    			<ul class="submenu third-level">
								<li> <a href="<c:url value="/sale/usertype/list"/>"> <span class="submenu-label">优惠类型</span> </a> </li>
								<li> <a href="<c:url value="/customer/orgtype/list"/>"> <span class="submenu-label">组织类型</span> </a> </li>
			    			</ul>
			    		</li>
			    		<li class="openable">
			    			<a href='#'> <span class="submenu-label">产品</span> </a>
			    			<ul class="submenu third-level">
								<li> <a href="<c:url value="/product/category/list"/>"> <span class="submenu-label">类型</span> </a> </li>
								<li> <a href="<c:url value="/product/part/list"/>"> <span class="submenu-label">部件</span> </a> </li>
								<li> <a href="<c:url value="/product/combo/list"/>"> <span class="submenu-label">套装</span> </a> </li>
			    			</ul>
			    		</li>
			    		<li class="openable">
			    			<a href='#'><span class="submenu-label">财务</span></a>
			    			<ul class="submenu third-level">
								<li> <a href="<c:url value="/finance/exptype/list"/>"> <span class="submenu-label">支出类型</span> </a> </li>
			    			</ul>
			    		</li>
						<li> <a href="<c:url value="/staff/list"/>"><span class="submenu-label">系统</span></a></li>
			    	</ul>
			    </li>
			    </sec:authorize>
		    </ul>
    </div>
    </aside>
    <!-- End of Sidebar Menu -->

    <!-- Main Container -->
    <div id="main-container">
    	<jsp:include page="/WEB-INF/pages/${pageContent}.jsp"/>
	</div>
    <!-- End of Main Container -->

    <!-- Footer -->
    <footer>
    </footer> <!-- End of Footer -->

</div> <!-- End of Main Wrapper -->

    <!--Modal-->
		<div class="modal fade" id="modal-help-about">
  			<div class="modal-dialog modal-sm">
    			<div class="modal-content">
      				<div class="modal-header">
        				<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4>关于 SCP</h4>
      				</div>
				    <div class="modal-body">
				        <p>管理 销售、客户关系、产品</p>
			        	<br>
			        	<p> 目前系统刚刚上线，不免会有很多错误和不方便的地方
			        	<p> 请使用中及时反馈。感谢！
			        	<p>
			        	<p class="pull-right">v1.0 佳耘科技 2016</p>
			        	<br>
				    </div>
				    <div class="modal-footer">
				        <button class="btn btn-sm btn-success" data-dismiss="modal">关闭</button>
				    </div>
			  	</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->

</body>