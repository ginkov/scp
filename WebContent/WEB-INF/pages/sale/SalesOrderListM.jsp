<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-xs">
	<div class="panel panel-default">
			<div class="panel-heading" style="height: 52px;">
				<span style="font-size: 14px;">订单列表</span>
				<sec:authorize access="hasAnyRole('PRODUCT','SUPER')">
					<a class="btn btn-success pull-right" href="<c:url value="/sale/order/input?mobile"/>" style="width: 100px;">
						<i class="fa fa-plus"></i> 新订单
					</a>
				</sec:authorize>
			</div>
			<div class="panel-body">
				<%-- 引入订单列表模板 --%>
				<%@ include file = "/WEB-INF/pages/ordersM.jsp" %>
			</div> <!-- /Panel body -->
		</div> <!-- /Panel -->

		<!-- Error Alerts  -->	
		<%@ include file = "/WEB-INF/pages/alert.jsp" %>
</div> <!-- ./padding -->

<script type="text/javascript">
 $(document).ready(function() {
    $('#orders').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "lengthChange": false
    	, "pageLength": 10
    	, "order": [[ 0, "desc" ]]
    	, orderClasses : false
	});
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
 });
</script>