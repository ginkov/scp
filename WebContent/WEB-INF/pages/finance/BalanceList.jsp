<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading" style="height:52px;">
			<span style="font-size:18px;"> 账务记录</span>
			<%-- 
			<a class="btn btn-success btn-sm pull-right" href="<c:url value="/customer/orgtype/input"/>" style="margin-right:5px; width: 100px;">
				<i class="fa fa-plus"></i> 添加
			</a>
			--%>
		</div>
		<div class="panel-body">
		<div class="row" style="font-size: 14px; text-align:center; padding-bottom: 5px; margin-bottom: 0px; border-bottom: 0.5px solid #ddd;">
			<div class="col-md-5">总支出: <fmt:formatNumber value="${totalOut}" type="currency"/></div>
			<div class="col-md-2">总余额: <fmt:formatNumber value="${balance}" type="currency"/></div>
			<div class="col-md-5">总营收: <fmt:formatNumber value="${totalIn}" type="currency"/></div>
		</div>
		<c:forEach items="${frWeeks}" var="frWeek">
		<div class="row" style="text-align:center; padding: 5px 0; background-color:#f8f8f8; border-bottom: 0.5px solid #ddd;">
			<div class="col-md-5">支: <fmt:formatNumber value="${frWeek.outAmount}" type="currency"/></div>
			<div class="col-md-2" style="border-left: 0.5px solid #bbb; border-right: 0.5px solid #bbb; font-weight: bold;"><fmt:formatDate value="${frWeek.date}" pattern="yyyy-MM-dd"/></div>
			<div class="col-md-5">收: <fmt:formatNumber value="${frWeek.inAmount}" type="currency"/></div>
		</div>
		<div class="row" style="padding: 5px 0; border-bottom: 0.5px solid #ddd;">
			<div class="col-md-5">
			<c:forEach items="${frWeek.out}" var="fr">
				<div class="row">
					<div class="col-md-1" style="text-align:center;"><fmt:formatDate value="${fr.date}" pattern="MM-dd"/></div>
					<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}${fr.url}">${fr.sn}</a></div>
					<div class="col-md-2">${fr.type}</div>
					<div class="col-md-4">${fr.description}</div>
					<div class="col-md-2" style="text-align:right;"><fmt:formatNumber value="${fr.amount}" type="currency"/></div>
				</div>
			</c:forEach>
			</div>
			<div class="col-md-2" style="text-align:center;"><fmt:formatNumber value="${frWeek.bal}" type="currency"/></div>
			<div class="col-md-5">
			<c:forEach items="${frWeek.in}" var="fr">
				<div class="row">
					<c:choose>
					<c:when test="${fr.action == 'AR' }">
						<div class="col-md-1 text-danger" style="text-align:center;"><fmt:formatDate value="${fr.date}" pattern="MM-dd"/></div>
						<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}${fr.url}">${fr.sn}</a></div>
						<div class="col-md-2 text-danger">${fr.type}</div>
						<div class="col-md-4 text-danger">${fr.description}</div>
						<div class="col-md-2 text-danger" style="text-align:right;"><fmt:formatNumber value="${fr.amount}" type="currency"/></div>
					</c:when>
					<c:otherwise>
						<div class="col-md-1" style="text-align:center;"><fmt:formatDate value="${fr.date}" pattern="MM-dd"/></div>
						<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}${fr.url}">${fr.sn}</a></div>
						<div class="col-md-2">${fr.type}</div>
						<div class="col-md-4">${fr.description}</div>
						<div class="col-md-2" style="text-align:right;"><fmt:formatNumber value="${fr.amount}" type="currency"/></div>
					</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>
			</div>
		</div>
		</c:forEach>
		</div> <!-- /Panel body -->
	</div> <!-- /Panel -->

	<!-- Error Alerts  -->	
	<%@ include file = "/WEB-INF/pages/alert.jsp" %>
</div> <!-- ./padding -->

<script type="text/javascript">
 $(document).ready(function() {
    $('#customer_orgtype').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "lengthChange": false
    	, "sPaginationType": "full_numbers"
    	, "orderClasses": false
    	//, columnDefs: [{targets:[1], orderable: false }]
	});

    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
} );
</script>