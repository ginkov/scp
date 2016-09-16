<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="padding-md">
	<form:form modelAttribute="invoice" name="invForm" cssClass="form-horizontal" action="${pageContext.request.contextPath}/finance/invoice/update" method="post" acceptCharset='utf-8'>
	<div class="panel panel-default">
			<div class="panel-heading" style="font-size:14px; height:52px;">
				<span><a href="<c:url value="/finance/invoice/list"/>" class='glink'>发票</a> / ${invoice.sn}</span>
				<input id="btnSumbit" class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id"/>
				<div style="margin:15px 0px; padding:25px 0px 15px 0px; border: dotted 1px #ccc; background-color: #ffffe0;">
				<!-- 供应商与发票号 -->
				<div class="form-group">
					<label for="issuer" class="col-md-2 control-label">供应商</label>
					<div class="col-md-5">
						<form:input id="issuer" cssClass="form-control" path="issuer"/>
					</div>
					<label class="col-md-1 control-label" for="sn">发票号</label>
					<div class="col-md-3">
						<form:input id="sn" cssClass="form-control" path="sn"/>
					</div>
				</div>
				<!-- 日期、金额 -->
				<div class="form-group">
					<label class="col-md-2 control-label" for="date">日期</label>
					<div class="col-md-2">
						<div class="input-group">
						<form:input id="date" cssClass="form-control datepicker" path="date" style="padding-left: 15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label" for="amount">金额</label>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<form:input type="number" min="0" step="0.01" id="amount" cssClass="form-control" path="amount"
							required="true"/>
						</div>
					</div>
					<label class="col-md-1 control-label" for="t2">支出类型</label>
					<div class="col-md-3">
						<form:select path="type" cssClass="chosen-select" data-placeholder="请选择" required="true">
							<form:options items="${invType}" itemLabel="description"/>	
						</form:select>
					</div>
				</div>
				<!-- 描述 -->
				<div class="form-group">
					<label class="col-md-2 control-label" for="description">内容描述</label>
					<div class="col-md-9">
						<form:textarea id="description" path="description" cssClass="form-control" style="resize: none;"/>
					</div>					
				</div>
				<!-- 类别 -->
				<div class="form-group">
					<label class="col-md-2 control-label">原始发票</label>
					<div class="col-md-4" style="margin-top: 6px;">
					    <label class="label-radio inline">
                    	    <form:radiobutton path="original" value="true" />
                        	<span class="custom-radio"></span> 是
                    	</label>
                    	<label class="label-radio inline">	
                    	    <form:radiobutton path="original" value="false" selected="true"/>
                    	    <span class="custom-radio"></span> 否
                    	</label>
					</div>
				</div>
				</div> <!-- /单据格式 -->
		</div> <!-- /Panel body -->

		<c:if test="${invoice.used}">
		<div class="panel-footer">
			<button id="btnDel" style="width:60px; margin-right: 20px;" class="btn btn-danger btn-sm pull-right" 
				data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 解除匹配</button>
			<div class="row" style="padding: 5px 0; border-bottom: 0.5px solid #ddd;">
				<div class="col-md-5">
					<c:forEach items="${il}" var="inv">
					<div class="row">
						<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}/finance/invoice/detail/${inv.id}">${inv.sn}</a></div>
						<div class="col-md-2 no-overflow">${fr.type}</div>
						<div class="col-md-4 no-overflow">${fr.description}</div>
						<div class="col-md-3" style="text-align:right;"><fmt:formatNumber value="${inv.amount}" type="currency"/></div>
					</div>
					</c:forEach>
				</div>
				<div class="col-md-2" style="text-align:center;">---</div>
				<div class="col-md-5">
					<c:forEach items="${el}" var="exp">
					<div class="row">
						<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}/finance/expense/detail/${exp.id}">${exp.sn}</a></div>
						<div class="col-md-2 no-overflow text-danger">${exp.t2.name}</div>
						<div class="col-md-4 no-overflow text-danger">${exp.summary}</div>
						<div class="col-md-3 text-danger" style="text-align:right;"><fmt:formatNumber value="${exp.amount}" type="currency"/></div>
					</div>
					</c:forEach>
				</div>
			</div>				
		</div><!-- panel-footer -->
		</c:if>
	</div> <!-- /Panel -->
	</form:form>
	<!-- Error Alerts  -->
	<%@ include file = "/WEB-INF/pages/alert.jsp" %>
</div> <!-- ./padding -->
<div class="modal fade" id="modalConfirmDel">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-body">
				确定要解除匹配吗?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/finance/invoice/unpair/${invoice.id}" />">删除</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$().ready(function(){
	$('.chosen-select').chosen({"disable_search":true});
	$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
	if (document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
});
</script>
