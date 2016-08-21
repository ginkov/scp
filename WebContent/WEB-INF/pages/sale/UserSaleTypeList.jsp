<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading" style="font-size:14px; height:52px;">
				优惠类型 <span id="pPhynetMsg"></span>
				<a class="btn btn-success btn-sm pull-right" href="<c:url value="/sale/usertype/input"/>" style="margin-right:5px; width:100px;">
						<i class="fa fa-plus"></i> 添加
				</a>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-hover" id="user_sale_type">
					<thead>
						<tr>
							<th>名称</th>
							<th>描述</th>
							<!-- <th></th> -->
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${userSaleTypeList}" var="ut">
						<tr>
							<td style="width:120px;"><a href="detail/${ut.id}" class="glink">${ut.name}</a>
							<td>${ut.description}
							<%-- <td style="width:15px;"><a href="del/${ut.id}"><i class="fa fa-trash"></i></a> --%>
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
    $('#user_sale_type').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, orderClasses : false
    	, columnDefs: [{targets:[1], orderable: false }]
	});
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
} );
</script>