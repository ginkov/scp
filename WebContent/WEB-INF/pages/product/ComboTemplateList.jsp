<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading" style="height:52px;">
				<span style="font-size:18px;">套装模板</span>
			    <sec:authorize access="hasAnyRole('ADMIN','SUPER')">
				<a class="btn btn-success btn-sm pull-right" href="<c:url value="/product/comboTemplate/edit"/>" style="margin-right:15px; width: 100px;">
						选择模板 <i class="fa fa-pencil"></i>
				</a>
				</sec:authorize>
			</div>
			<div class="panel-body">
				<c:choose>
				<c:when test="${hasTemplate}">
					<div>
						<div style="margin:0 20px; padding:5px 0; font-weight:bold;">${template.name}</div>
						<div style="margin:5px 15px;">
						<table class="table table-striped table-hover" >
						<thead>
						<tr>
							<th style="width:150px;">部件名
							<th style="width: 80px;">数量
						</th>
						</thead>
						<tbody>
						<c:forEach items="${template.partslist}" var="ppi">
							<tr>
								<td>${ppi.part.name}  
								<td>${ppi.quantity}
							</tr>
						</c:forEach>
						</tbody>
						</table>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<div>当前没有选中的模板</div>
				</c:otherwise>
				</c:choose>
			</div> <!-- /Panel body -->
		</div> <!-- /Panel -->
		
<c:if test="${not empty err}">
<div class="modal" id="modalAlert">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-body" style="text-align:center">
				<h5 class="text-danger">${err}</h5>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">确定</button>
			</div>
		</div>
	</div>
</div> <!-- ./modal -->
</c:if>

</div> <!-- ./padding -->
<script type="text/javascript">
 $(document).ready(function() {
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
});
</script>