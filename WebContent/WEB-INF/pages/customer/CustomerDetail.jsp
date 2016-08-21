<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading"  style="font-size:14px; height:52px;">
				<a href="<c:url value="/customer/customer/list"/>" class='glink'>客户列表</a> / ${customer.name}<span id="pPhynetMsg"></span>
			    <sec:authorize access="hasAnyRole('ADMIN','SUPER')">
				<a style="width:100px;" class="btn btn-default btn-sm pull-right" href="<c:url value="/customer/customer/edit/${customer.id}"/>"><i class="fa fa-pencil"></i> 修改</a>
				<button style="width:60px; margin-right: 20px;" class="btn btn-danger btn-sm pull-right" data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 删除</button>
				</sec:authorize>
		</div>
		<div class="panel-body form-horizontal">
<%-- 			因为上面的题头中有客户名了，所以这里把客户名去掉
				<div class="form-group">
				<label class="col-md-2 control-label" for="name">用户名</label>
				<div class="col-md-9">
					<input class="form-control" value="${customer.name}" readonly>
				</div>
			</div> --%>
			<div class="form-group">
				<label class="col-md-2 control-label" for="name">组织类型</label>
				<div class="col-md-3">
					<input id="name" class="form-control" value="${customer.orgType.name}" readonly/>
				</div>
				<label class="col-md-1 control-label" for="contact">联系人</label>
				<div class="col-md-2">
					<input id='contact' class='form-control' value="${customer.contactName}" readonly />
				</div> 
				
				<label class="col-md-1 control-label" for='reg_date'>注册日期</label>
				<div class="col-md-2">
					<input id='reg_date' class='form-control' value="<fmt:formatDate value="${customer.registerTime}" pattern="yyyy-MM-dd"/>" readonly />
				</div> 			
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label" for="phone1">电话 1</label>
				<div class="col-md-4">
					<input class="form-control" value="${customer.phone1}" readonly>
				</div>
				<label class="col-md-1 control-label" for="phone2">电话 2</label>
				<div class="col-md-4">
					<input class="form-control" value="${customer.phone2}" readonly>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label" for="phone3">电话 3</label>
				<div class="col-md-4">
					<input class="form-control" value="${customer.phone3}" readonly>
				</div>
				<label class="col-md-1 control-label" for="phone4">电话 4</label>
				<div class="col-md-4">
					<input class="form-control" value="${customer.phone4}" readonly>
				</div>
			</div>
			<c:forEach var="address" items="${customer.addresses}" varStatus="vs">
				<div class="form-group">
					<label class="col-md-2 control-label">地址 ${vs.index + 1}</label>
					<div class="col-md-9">
						<input class="form-control" value="${address.province} ${address.city} ${address.street} 邮编 ${address.zipcode}" readonly />
					</div>
				</div>
			</c:forEach>
		</div> <!-- /Panel body -->
		<div class="panel-footer" style="padding-bottom: 50px;">
			<div style="padding-bottom: 15px; margin-bottom: 25px; border-bottom: 1px #eee solid;">
				历史订单	
			</div>

			<%-- 引入订单列表模板 --%>
			<%@ include file = "/WEB-INF/pages/orders.jsp" %>

		</div><!-- panel-footer -->
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
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/customer/customer/del/${customer.id}" />">删除</a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
 $(document).ready(function() {
    $('#orders').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, orderClasses : false
    	, columnDefs: [{targets:[6], orderable: false }]
	});
 } );
</script>