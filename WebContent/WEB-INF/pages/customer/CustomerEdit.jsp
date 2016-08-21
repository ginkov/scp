<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="padding-md">
	<div class="panel panel-default">
		<c:url var="updateURL" value="/customer/customer/update"/>
		<form:form modelAttribute="customer" name="customerForm" cssClass="form-horizontal" action="${updateURL}" method="post" acceptCharset='utf-8'>
			<div class="panel-heading"  style="font-size:14px; height:52px;">
				<a href="<c:url value="/customer/customer/list"/>" class="glink">客户</a> / 修改 <span id="pPhynetMsg"></span>
				<input style="width:100px;" class="btn btn-success btn-sm pull-right" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name">名称</label>
					<div class="col-md-9">
						<form:input id="name" cssClass="form-control" path="name"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="orgtype">组织类型</label>
					<div class="col-md-4">
						<form:select id="orgtype" cssClass="chosen-select" path="orgType.id"
							items="${orgTypes}" itemLabel="name" itemValue="id"/>
					</div>
					<label class="col-md-1 control-label" for="contactName">联系人</label>
					<div class="col-md-4">
						<form:input id="contactName" cssClass="form-control" path="contactName"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="phone1">电话 1</label>
					<div class="col-md-4">
						<form:input id="phone1" cssClass="form-control" path="phone1"/>
					</div>
					<label class="col-md-1 control-label" for="phone2">电话 2</label>
					<div class="col-md-4">
						<form:input id="phone2" cssClass="form-control" path="phone2"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="phone3">电话 3</label>
					<div class="col-md-4">
						<form:input id="phone3" cssClass="form-control" path="phone3"/>
					</div>
					<label class="col-md-1 control-label" for="phone4">电话 4</label>
					<div class="col-md-4">
						<form:input id="phone4" cssClass="form-control" path="phone4"/>
					</div>
				</div>
				<form:hidden path="nameAndPhone"/>
			</div> <!-- /Panel body -->
			<div class="panel-footer">
				<div style="padding-bottom: 15px; margin-bottom: 25px; border-bottom: 1px #eee solid;">
					编辑地址
					<input style="width:100px;" class="btn btn-default btn-xs pull-right" type="button" value="保存地址"
						onclick="document.customerForm.action='<c:url value="/customer/customer/edit/${customer.id}"/>'; document.customerForm.submit();"/>
				</div>
				<c:if test="${not empty customer.addresses}">
  				<c:forEach var="address" items="${customer.addresses}" varStatus="vs">
		 			<div class="form-group">
					<form:hidden path="addresses[${vs.index}].id"/>
					<label class="col-md-2 control-label">地址 ${vs.index + 1}</label>
					<div class="col-md-1">
						<%-- <form:input path="addresses[${vs.index}].province" cssClass="form-control" value="${address.province}" placeholder="省/市" /> --%>
						<form:input path="addresses[${vs.index}].province" cssClass="form-control" placeholder="省/市" />
					</div>
					<div class="col-md-1">
						<form:input path="addresses[${vs.index}].city"  cssClass="form-control" placeholder="市/区/县"/>
					</div>
					<div class="col-md-5">
						<form:input path="addresses[${vs.index}].street" cssClass="form-control" placeholder="街道门牌"/>
					</div>
					<div class="col-md-2">
						<form:input path="addresses[${vs.index}].zipcode" cssClass="form-control" placeholder="邮编"/>
					</div>
					</div><!-- form-group  -->
				</c:forEach>
				</c:if>
			</div><!-- panel-footer -->
		</form:form>

	</div> <!-- /Panel -->
</div> <!-- ./padding -->

<script type="text/javascript">
	$().ready(
			function(){
				$('.chosen-select').chosen();
			});
</script>