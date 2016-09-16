<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading" style="height: 56px;">
			<span style="font-size: 18px;">发票记录</span> 
			<a href="<c:url value="/finance/invoice_upload/select"/>" style="padding-left:15px;" class="glink">批量导入</a>
			<a class="btn btn-success btn-md pull-right" href="<c:url value="/finance/invoice/input"/>" style="margin-right:5px; width: 100px;">
				<i class="fa fa-plus"></i> 新发票
			</a>
		</div>
		<div class="panel-body">
			<div class="row" style="padding: 3px; margin: 5px 10px; border-bottom: 1px solid lightgrey; text-align:center; background-color:#f8f8f8" >
				<span style="font-weight: bold;">发票/支出</span> 已匹配：( <fmt:formatNumber value="${invAmtPaired}" type="currency"/>
				/ <fmt:formatNumber value="${expAmtPaired}" type="currency"/> ) ，
				未匹配：(<fmt:formatNumber value="${invAmtUnPaired}" type="currency"/>
				/  <fmt:formatNumber value="${expAmtUnPaired}" type="currency"/> )
			</div>
			<div class="row" style="text-align:center; padding:3px 10px; margin: 20px 20px 0 20px;">
				<div class="col-md-5">已匹配发票: <fmt:formatNumber value="${totalOut}" type="currency"/></div>
				<div class="col-md-2">差额</div>
				<div class="col-md-5">已匹配支出: <fmt:formatNumber value="${totalIn}" type="currency"/></div>
			</div>
			<c:forEach items="${i2eList}" var="i2e">
			<div class="row" style="text-align:center; padding: 4px 0 2px 0; margin: 0 20px;
					 background-color:#f8f8f8; border: 0.5px solid #ddd; border-radius: 3px;">
				<div class="col-md-5"><fmt:formatNumber value="${i2e.isum}" type="currency"/></div>
				<div class="col-md-2" 
						style="margin: 0; border-left: 0.5px solid #bbb; border-right: 0.5px solid #bbb; font-weight: bold;">
					<fmt:formatNumber value="${i2e.bal}" type="currency"/></div>
				<div class="col-md-5"><fmt:formatNumber value="${i2e.esum}" type="currency"/></div>
			</div>
			<div class="row" style="padding: 5px 0; margin: 0 20px;">
				<div class="col-md-5">
					<c:forEach items="${i2e.il}" var="inv">
					<div class="row">
						<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}/finance/invoice/detail/${inv.id}">${inv.sn}</a></div>
						<div class="col-md-2 no-overflow">${inv.type.description}</div>
						<div class="col-md-4 no-overflow">${inv.description}</div>
						<div class="col-md-3" style="text-align:right;"><fmt:formatNumber value="${inv.amount}" type="currency"/></div>
					</div>
					</c:forEach>
				</div>
				<div class="col-md-2" style="text-align:center;"></div>
				<div class="col-md-5">
					<c:forEach items="${i2e.el}" var="exp">
					<div class="row">
						<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}/finance/expense/detail/${exp.id}">${exp.sn}</a></div>
						<div class="col-md-2 no-overflow">${exp.t2.name}</div>
						<div class="col-md-4 no-overflow">${exp.summary}</div>
						<div class="col-md-3" style="text-align:right;"><fmt:formatNumber value="${exp.amount}" type="currency"/></div>
					</div>
					</c:forEach>
				</div>
			</div>
			</c:forEach>
			<div style="margin-top: 5px; height: 40px; border-top: 1px solid #eee;"></div>
			<div style="margin: 0 12px;">
				<div class="row" style="font-size: 16px; margin: 5px 0; padding: 5px 0; border-bottom: 1px solid lightgrey;">
					<span>未使用发票</span>
					<a class="btn btn-default btn-sm pull-right" href="<c:url value="/finance/invoice/autopair"/>" style="width: 100px;"><i class="fa fa-exchange"></i> 自动匹配</a>
				</div>
				<table class="table table-striped table-hover" id="invoice">
					<thead>
						<tr>
							<th>发票号</th>
							<th style="text-align:right;">日期</th>
							<th style="text-align:right;">供应商</th>
							<th style="text-align:right;">财务类别</th>
							<th style="text-align:right;">内容描述</th>
							<th style="text-align:right;">原始发票</th>
							<th style="text-align:right;">金额(￥)</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${unused}" var="inv">
						<tr>
							<td><a href="<c:url value="/finance/invoice/detail/${inv.id}"/>" class="glink">${inv.sn}</a>
							<td style="text-align:right;"><fmt:formatDate value="${inv.date}" pattern='yyyy-MM-dd'/>
							<td style="text-align:right;">${inv.issuer}
							<td style="text-align:right;">${inv.type.description}
							<td style="text-align:right;">${inv.description}
							<td style="text-align:right;">${inv.original ? "是" : ""}
							<td style="text-align:right;"><fmt:formatNumber type="number" pattern="#,###.00" value="${inv.amount}"/>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			</div> <!-- /Panel body -->
		</div> <!-- /Panel -->
</div> <!-- ./padding -->

<!-- Error Alerts  -->	
<%@ include file = "/WEB-INF/pages/alert.jsp" %>

<script type="text/javascript">
$(document).ready(function() {
	if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
    $('#invoice').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "lengthChange": false
    	, "pageLength": 25
    	, "order": [[ 0, "desc" ]]
    	, orderClasses : false
    	, columnDefs: [{targets:[4], orderable: false }]
	});
 });
</script>