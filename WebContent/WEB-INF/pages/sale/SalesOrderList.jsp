<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setTimeZone value="GMT"/>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading" style="height: 68px;">
				<span style="font-size: 18px;">订单列表</span> 
				<a href="<c:url value="/sale/order_upload/select"/>" style="padding-left:15px;" class="glink">批量导入</a>
				<a class="btn btn-success btn-lg pull-right" href="<c:url value="/sale/order/input"/>" style="margin-right:5px; width: 100px;">
						<i class="fa fa-plus"></i> 新订单
				</a>
			</div>
			<div class="panel-body">
				<%-- 引入订单列表模板 --%>
				<%@ include file = "/WEB-INF/pages/orders.jsp" %>
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
    	, "pageLength": 25
    	, orderClasses : false
    	, columnDefs: [{targets:[7], orderable: false }]
	});
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
 });
</script>