<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading"  style="font-size:14px; height:52px;">
				客户列表 <span id="pPhynetMsg"></span>
				<a class="btn btn-success btn-sm pull-right" href="<c:url value="/customer/customer/input"/>" style="margin-right:5px; width: 100px;">
						<i class="fa fa-plus"></i> 添加
				</a>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-hover" id="customer_list">
					<thead>
						<tr>
							<th>名称</th>
							<th>组织类型</th>
							<th>登记日期</th>
							<th>电话</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${customerList}" var="customer">
						<tr>
							<td style="width:120px;"><a href="detail/${customer.id}" class='glink'>${customer.name}</a>
							<td><a href="<c:url value="/customer/orgtype/detail/${customer.orgType.id}" />" class="glink">${customer.orgType.name}</a>
							<td><fmt:formatDate value="${customer.registerTime}" pattern="yyyy-MM-dd"/>
							<td>${customer.phone1}
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div> <!-- /Panel body -->
		</div> <!-- /Panel -->

		<!-- Error Alerts  -->	
		<%@ include file = "/WEB-INF/pages/alert.jsp" %>

</div> <!-- ./padding -->

<script type="text/javascript">
 $(document).ready(function() {
    $('#customer_list').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "lengthChange": false
    	, orderClasses: false
	});
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
} );
</script>

