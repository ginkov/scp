<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div style="display:none;" id="t1_helper" data-t1list='${t1names}'> </div>
<div style="display:none;" id="t2_helper" data-t2list='${t2names}'> </div>
<div style="display:none;" id="login_helper" data-login='${pageContext.request.userPrincipal.name}'> </div>

<div class="padding-md">
	<form:form modelAttribute="er" name="erForm" cssClass="form-horizontal" action="${pageContext.request.contextPath}/finance/expense/save?mobile" method="post" acceptCharset='utf-8'>
	<div class="panel panel-default">
			<div class="panel-heading" style="font-size:14px; height:52px;">
				<span><a href="<c:url value="/finance/expense/list?mobile"/>" class='glink'>支出</a> / 新支出</span>
				<input id="btnSumbit" class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id"/>
				<form:hidden path="sn"/>
				<form:hidden path="owner.name" value="${pageContext.request.userPrincipal.name}"/>
				<div class="form-group">
					<label class="col-xs-3 label-xs">付款人</label>
					<div class="col-xs-8">
						<form:select id="staff" cssClass="chosen-select" path="staff.name" data-placeholder="请选择" required="true">
							<form:option value="" label=""/>
							<form:options items="${staffs}" itemLabel="description" itemValue="name"/>
						</form:select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 label-xs" for="date">日期</label>
					<div class="col-xs-8">
						<div class="input-group">
						<form:input id="date" cssClass="form-control datepicker" path="date" style="padding-left: 15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 label-xs" for="t1">类别</label>
					<div class="col-xs-8">
						<select id="t1" class="chosen-select" data-placeholder="请选择" required onchange="updateT2()">
						</select>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 label-xs" for="t2">种类</label>
					<div class="col-xs-8">
						<form:select id="t2" path="t2.name" cssClass="chosen-select" data-placeholder="请选择" required="true">
						</form:select>
					</div>
				</div>

				<!-- 供应商与发票号 -->
				<div class="form-group">
					<label for="supplier" class="col-xs-3 label-xs">供应商</label>
					<div class="col-xs-8">
						<form:input id="supplier" cssClass="form-control" path="supplierName"/>
					</div>
				</div>

				<div class="form-group">
					<label class="col-xs-3 label-xs" for="invoiceNum">发票号</label>
					<div class="col-xs-8">
						<form:input id="invoiceNum" cssClass="form-control" path="invoiceNum"/>
					</div>
				</div>

				<!-- 支出名称与金额 -->
				<div class="form-group">
					<label class="col-xs-3 label-xs" for="name">商品名称</label>
					<div class="col-xs-8">
						<form:input id="name" path="expName" cssClass="form-control" required="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 label-xs" for="amount">金额</label>
					<div class="col-xs-8">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${er.amount}" var="amount" pattern=".00"/>
						<form:input type="number" min="0" step="0.01" id="amount" cssClass="form-control" path="amount" required="true" value="${amount}"/>
						</div>
					</div>
				</div>
				<!-- 摘要 -->
				<div class="form-group">
					<label class="col-xs-3 label-xs" for="summary">描述</label>
					<div class="col-xs-8">
						<form:textarea id="summary" path="summary" cssClass="form-control" style="resize: none;"/>
					</div>					
				</div>
			</div> <!-- /Panel body -->
		</div> <!-- /Panel -->
	</form:form>
	<!-- Error Alerts  -->
	<%@ include file = "/WEB-INF/pages/alert.jsp" %>
</div> <!-- ./padding -->

<script type="text/javascript">
var t1list = {},
	t2list = {};

var updateOpts = function(target, opts){
	var output = ['<option value=""></option>'];

	$.each(opts, function(val, key)
	{
	  output.push('<option value="'+ key +'">'+ key +'</option>');
	});
	$(target).html(output.join(''));
}

var updateT2 = function(){
	var t1 = $('#t1').val();
	updateOpts('#t2', t2list[t1]);
	$('#t2').trigger("chosen:updated");
}

$().ready(function(){
	t1list = $('#t1_helper').data('t1list');
	t2list = $('#t2_helper').data('t2list');
	login  = $('#login_helper').data('login');
	$('#staff').val(login);
	updateOpts('#t1', t1list);
	$('.chosen-select').chosen({"search_contains":true});
	$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
	if (document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
});
</script>
