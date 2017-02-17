<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading" style="height:52px;">
				<span style="font-size:18px;">套装列表</span>
			    <sec:authorize access="hasAnyRole('PRODUCT','SUPER')">
				<a href="<c:url value="/product/combo_upload/select"/>" style="padding-left:15px;" class="glink">批量导入</a>
				<a class="btn btn-success btn-sm pull-right" href="<c:url value="/product/combo/input"/>" style="margin-right:15px; width: 100px;">
						添加 <i class="fa fa-cube"></i>
				</a>
				</sec:authorize>
			</div>
			<div class="panel-body">
				<table class="table table-striped table-hover" id="prod_combo">
					<thead>
						<tr>
							<th style="width:120px;">名称</th>
							<th class="text-right" style="width:120px;">产品类别</th>
							<th class="text-right" style="width:120px;">子类</th>
							<th class="text-right">列表价</th>
							<th class="text-right">上线时间</th>
							<th class="text-right">下线时间</th>
							<!-- <th style="width:15px;"></th> -->
						</tr>
					</thead>
					<tbody>
					
					<c:forEach items="${combolist}" var="part">
						<tr>
							<td><a href="detail/${part.id}" class='glink'>${part.name}</a>
							<td class="text-right">${part.c2.c1.name}
							<td class="text-right">${part.c2.name}
							<td class="text-right"><fmt:setLocale value="zh_CN" scope="session"/><fmt:formatNumber type="currency" value="${part.listprice}" />
							<td style="text-align:right;"><fmt:formatDate value="${part.onlineDate}" pattern="yyyy-MM-dd"/>
							<td style="text-align:right;"><fmt:formatDate value="${part.offlineDate}" pattern="yyyy-MM-dd"/>
						</tr>
					</c:forEach>
					</tbody>
				</table>
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
    $('#prod_combo').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "orderClasses": false
    	, columnDefs: [
//    		{targets:[3], orderable: false }
    	]
	});

    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
} );
</script>