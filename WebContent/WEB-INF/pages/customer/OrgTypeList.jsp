<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading" style="font-size:14px; height:52px;">
				客户 / 组织类型 <span id="pPhynetMsg"></span>
				<a class="btn btn-success btn-sm pull-right" href="<c:url value="/customer/orgtype/input"/>" style="margin-right:5px; width: 100px;">
						<i class="fa fa-plus"></i> 添加
				</a>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-hover" id="customer_orgtype">
					<thead>
						<tr>
							<th>名称</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${orgTypeList}" var="ot">
						<tr>
							<td><a href="detail/${ot.id}" class='glink'>${ot.name}</a>
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
    $('#customer_orgtype').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "lengthChange": false
    	, "sPaginationType": "full_numbers"
    	, "orderClasses": false
    	//, columnDefs: [{targets:[1], orderable: false }]
	});

    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
} );
</script>