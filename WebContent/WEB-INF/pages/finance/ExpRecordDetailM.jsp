<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<div class="padding-xs">
	<div class="panel panel-default">
		<div class="panel-heading" style="font-size:14px; height:52px;">
			<span><a href="<c:url value="/finance/expense/list?mobile"/>" class='glink'>支出</a> / ${er.sn} </span>
			<sec:authorize access="hasAnyRole('ADMIN','SUPER','USER')">
				<a id="btnEdit" class="btn btn-default btn-sm pull-right" href="<c:url value="/finance/expense/edit/${er.id}?mobile"/>" style="width: 100px;"><i class="fa fa-pencil"></i> 修改</a>
				<button id="btnDel" style="width:60px; margin-right: 20px;" class="btn btn-danger btn-sm pull-right" data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 删除</button>
			</sec:authorize>
		</div>
		<div class="panel-body form-horizontal">
			<table class="table">
				<tr>
					<th class="active">付款人
					<td>${er.staff.description}
					<th class="active">记录人
					<td>${er.owner.description}
				</tr>
				<tr>
					<th class="active">日期
					<td><fmt:formatDate value="${er.date}" pattern="yyyy-MM-dd"/>
					<th class="active">发票号
					<td>${er.invoiceNum}
				</tr>
				<tr>
					<th class="active">类别
					<td>${er.t2.t1.name}
					<th class="active">种类
					<td>${er.t2.name}
				</tr>
				<tr>
					<th class="active">供应商
					<td>${er.supplierName}
					<th class="active">金额
					<td style="font-weight: bold; color: white; background-color: #033;">
						<fmt:formatNumber value="${er.amount}" type="currency"/>
				</tr>
				<tr>
					<th class="active">名称
					<td>${er.expName}
				</tr>
				<tr>
					<th class="active">说明
					<td>${er.summary}
				</tr>
			</table>
		</div> <!-- /Panel body -->
	</div> <!-- /Panel -->
</div> <!-- ./padding -->

<div class="modal fade" id="modalConfirmDel">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-body">
				确定要删除吗?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/finance/expense/del/${er.id}?mobile" />">删除</a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	var login = "${pageContext.request.userPrincipal.name}";
	var owner = "${er.owner.name}";
	if(owner != login){
		//$("#btnDel").prop("disabled", "true");
		//$("#btnEdit").prop("disabled", "true");
		$("#btnEdit").remove();
		$("#btnDel").remove();
	}
    $('#orders').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, orderClasses : false
    	, columnDefs: [{targets:[6], orderable: false }]
	});
 } );
</script>