<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="padding-md">
	<div class="panel panel-default">
		<form:form modelAttribute="customer" id="customerForm" name="customerForm" cssClass="form-horizontal" action="save" method="post" acceptCharset='utf-8'>
			<div class="panel-heading"  style="font-size:14px; height:52px;">
				<a href="<c:url value="/customer/customer/list"/>" class="glink">客户</a> / 添加 <span id="pPhynetMsg"></span>
				<input style="width:100px;" class="btn btn-success btn-sm pull-right" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name">用户名</label>
					<div class="col-md-9">
						<form:input id="name" cssClass="form-control" path="name"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="orgtype">组织类型</label>
					<div class="col-md-4">
						<form:select id="orgtype" cssClass="chosen-select" path="orgType.id"
							items="${orgTypes}" itemLabel="name" itemValue="id">
						</form:select>
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
			</div> <!-- /Panel body -->
			<div class="panel-footer">
				<div style="padding-bottom: 15px; margin-bottom: 25px; border-bottom: 1px #eee solid;">
					添加地址
					<input style="width:100px;" class="btn btn-default btn-xs pull-right" type="button" value="保存地址"
						onclick="document.customerForm.action='input'; document.customerForm.submit();"/>
				</div>
				<c:if test="${not empty customer.addresses}">
  				<c:forEach var="address" items="${customer.addresses}" varStatus="vs">
		 			<div class="form-group">
					<label class="col-md-2 control-label">地址 ${vs.index + 1}</label>
					<form:hidden path="addresses[${vs.index}].id"/>
					<div class="col-md-1">
						<%-- <form:input path="addresses[${vs.index}].province" cssClass="form-control" value="${address.province}" /> --%>
						<form:input path="addresses[${vs.index}].province" cssClass="form-control"/>
					</div>
					<div class="col-md-1">
						<form:input path="addresses[${vs.index}].city"  cssClass="form-control" value="${address.city}"/>
					</div>
					<div class="col-md-5">
						<form:input path="addresses[${vs.index}].street" cssClass="form-control" value="${address.street}"/>
					</div>
					<div class="col-md-2">
						<form:input path="addresses[${vs.index}].zipcode" cssClass="form-control" value="${address.zipcode}"/>
					</div>
					</div><!-- form-group  -->
				</c:forEach>
				</c:if>
     			<c:set var="length" value="${fn:length(customer.addresses)}" />
				<div class="form-group">
					<label class="col-md-2 control-label">用户地址 ${length + 1}</label>
					<form:hidden path="addresses[${length}].id"/>
					<div class="col-md-1">
						<form:input path="addresses[${length}].province" cssClass="form-control" placeholder="省/市" />
					</div>
					<div class="col-md-1">
						<form:input path="addresses[${length}].city"  cssClass="form-control" placeholder="市/区/县" />
					</div>
					<div class="col-md-5">
						<form:input path="addresses[${length}].street" cssClass="form-control" placeholder="具体门牌"/>
					</div>
					<div class="col-md-2">
						<form:input path="addresses[${length}].zipcode" cssClass="form-control" placeholder="邮编"/>
					</div>
				</div><!-- form-group  -->
			</div><!-- panel-footer -->
		</form:form>

	</div> <!-- /Panel -->
</div> <!-- ./padding -->


<script type="text/javascript">
$().ready(
		function(){
			$('.chosen-select').chosen();
		});
/*  $(document).ready(function() {
    $('#addresses').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "lengthChange": false
    	, "orderClasses": false
    	, "searching": false
    	, "ordering": false
    	, "paging": false
    	, "info": false
    	, columnDefs: [
    		{targets:[0,1,2,3,4,5], orderable: false }
    	]
	});
} ); */
</script>