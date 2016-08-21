<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading" style="font-size:14px; height:52px;">
			<span><a href="<c:url value="/finance/expense/list"/>" class='glink'>支出</a> / ${er.sn} </span>
			<sec:authorize access="hasAnyRole('ADMIN','SUPER')">
				<a id="btnDel" class="btn btn-default btn-sm pull-right" href="<c:url value="/finance/expense/edit/${er.id}"/>" style="width: 100px;"><i class="fa fa-pencil"></i> 修改</a>
				<button id="btnEdit" style="width:60px; margin-right: 20px;" class="btn btn-danger btn-sm pull-right" data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 删除</button>
			</sec:authorize>
		</div>
		<div class="panel-body form-horizontal">
			<div class="form-group">
				<label class="col-md-2 control-label">付款人</label>
				<div class="col-md-2">
					<input class="form-control" value="${er.staff.name}" readonly />
				</div>
				<label class="col-md-offset-5 col-md-1 control-label">记录人</label>
				<div class="col-md-2">
					<input class="form-control" value="${er.owner.name}" readonly />
				</div>
			</div>
			<div style="margin:15px 0px; padding:25px 0px 15px 0px; border: dotted 1px #ccc; background-color: #ffffe0;">
			<div class="form-group">
				<label class="col-md-2 control-label">发生日期</label>
				<div class="col-md-2">
					<input class="form-control" value="<fmt:formatDate value="${er.date}" pattern="yyyy-MM-dd"/>" readonly/>
				</div>
				<label class="col-md-1 control-label">类别</label>
				<div class="col-md-2">
					<input class="form-control" value="${er.t2.t1.name}" readonly/>
				</div>
				<label class="col-md-1 control-label">种类</label>
				<div class="col-md-3">
					<input class="form-control" value="${er.t2.name}" readonly/>
				</div>
			</div>
			<!-- 供应商与发票号 -->
			<div class="form-group">
				<label for="supplier" class="col-md-2 control-label">供应商</label>
				<div class="col-md-5">
					<input class="form-control" value="${er.supplierName}" readonly/>
				</div>
				<label class="col-md-1 control-label">发票号</label>
				<div class="col-md-3">
					<input class="form-control" value="${er.invoiceNum}" readonly/>
				</div>
			</div>
			<!-- 支出名称与金额 -->
			<div class="form-group">
				<label class="col-md-2 control-label">商品名称</label>
				<div class="col-md-5">
					<input class="form-control" value="${er.expName}" readonly/>
				</div>
				<label class="col-md-1 control-label">金额</label>
				<div class="col-md-3">
					<div class="input-group">
						<span class="input-group-addon">￥</span>
						<input class="form-control text-right" value="<fmt:formatNumber value="${er.amount}" pattern=".00"/>" readonly>
					</div>
				</div>
			</div>
			<!-- 摘要 -->
			<div class="form-group">
				<label class="col-md-2 control-label">描述</label>
				<div class="col-md-9">
					<textarea class="form-control" readonly style="resize: none;">${er.summary}</textarea>
				</div>					
			</div>
			</div> <!-- /单据格式 -->
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
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/finance/expense/del/${er.id}" />">删除</a>
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