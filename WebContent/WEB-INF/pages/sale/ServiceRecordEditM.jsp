<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
		<c:url value="/sale/servicerecord/update?mobile" var="updateUrl"/>
		<form:form modelAttribute="order" cssClass="form-horizontal" action="${updateUrl}" method="post" acceptCharset='utf-8'>
		<div class="panel-heading">
			<a href="<c:url value="/sale/order/list"/>">订单列表</a> / ${order.sn} / 服务记录 <span id="pPhynetMsg"></span>
		</div>
		<div class="panel-body">
			<form:hidden path="id"/>
			<form:hidden path="sn"/>

			<div style="margin: 0px 5px 10px 5px; padding: 5px 5px 15px 5px; border-bottom: 1px lightgrey solid;">
				服务记录
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
  			<c:forEach var="sr" items="${order.serviceRecords}" varStatus="vs">
  			<div style="background-color: #f8f8f8; padding: 10px 0 1px 0; margin: 15px 0;">
				<form:hidden path="serviceRecords[${vs.index}].id"/>
  				<div class="form-group">
					<label class="col-xs-3 label-xs">日期</label>
					<div class="col-xs-8">
						<div class="input-group">
						<form:input path="serviceRecords[${vs.index}].time" cssClass="form-control datepicker"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
  				</div>
  				<div class="form-group">
					<label class="col-xs-3 label-xs">服务人</label>
					<div class="col-xs-8">
						<form:input cssClass="form-control" path="serviceRecords[${vs.index}].servicePerson"/>
					</div>
  				</div>
  				<div class="form-group">
					<label class="col-xs-3 label-xs">服务类型</label>
					<div class="col-xs-8">
						<form:select cssClass="chosen-select" path="serviceRecords[${vs.index}].type" >
							<option value="定期追踪">定期追踪</option>
							<option value="故障维修">故障维修</option>
							<option value="主动更换">主动更换</option>
						</form:select>
					</div>
  				</div>
				<div class="form-group">
					<label class="col-xs-3 label-xs">记录</label>
					<div class="col-xs-8">
						<form:textarea path="serviceRecords[${vs.index}].record" cssClass="form-control" style="resize:none;"/>
					</div>
				</div><!-- form-group  -->
			</div>
			</c:forEach>
		</div> <!-- /Panel body -->
		</form:form>
	</div> <!-- /Panel -->
</div> <!-- ./padding -->
<script type="text/javascript">
	var pricelist = {};	
	$().ready(
			function(){
				pricelist = $('#opt_helper').data('pricelist');
				$('.chosen-select').chosen({'disable_search': true});
			});
</script>
