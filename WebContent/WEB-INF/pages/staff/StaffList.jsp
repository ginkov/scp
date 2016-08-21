<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading" style="font-size:14px; height:52px;">
				员工列表 <span id="pPhynetMsg"></span>
				<a class="btn btn-success btn-sm pull-right" href="<c:url value="/staff/input"/>" style="margin-right:5px; width: 100px;">
						<i class="fa fa-plus"></i> 添加
				</a>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-hover" id="staff">
					<thead>
						<tr>
							<th>账户</th>
							<th>姓名或昵称</th>
							<th>权限</th>
						    <sec:authorize access="hasRole('SUPER')">
						    	<th style="width: 15px;"></th>
						    	<th style="width: 15px;"></th>
						    </sec:authorize>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${staffList}" var="s">
						<tr>
							<td>${s.name}
							<td>${s.description}
							<td>
								<c:forEach items="${s.roles}" var="r">
									${r.role} &nbsp; &nbsp;
								</c:forEach>
						    <sec:authorize access="hasRole('SUPER')">
						    	<td><a href="<c:url value="/staff/edit/${s.id}"/>"><i class="fa fa-pencil"></i></a>
						    	<td><a href="<c:url value="/staff/del/${s.id}"/>"><i class="fa fa-trash"></i></a>
						    </sec:authorize>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div> <!-- /Panel body -->
		</div> <!-- /Panel -->
</div> <!-- ./padding -->

<!-- Error Alerts  -->	
<%@ include file = "/WEB-INF/pages/alert.jsp" %>

<script type="text/javascript">
 $(document).ready(function() {
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
    $('#staff').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "lengthChange": false
    	, orderClasses : false
    	, ordering: false
//    	, columnDefs: [{targets:[1,2], orderable: false }]
	});
} );
</script>