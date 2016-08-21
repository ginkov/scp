<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading" style="font-size:14px; height:52px;">
			客户 / <a href="<c:url value="/customer/orgtype/list"/>" class='glink'>组织类型</a> / 详情 <span id="pPhynetMsg"></span>
			<sec:authorize access="hasAnyRole('ADMIN','SUPER')">
				<a class="btn btn-default btn-sm pull-right" href="<c:url value="/customer/orgtype/edit/${ot.id}"/>" style="width: 100px;"><i class="fa fa-pencil"></i> 修改</a>
				<button style="width:60px; margin-right: 20px;" class="btn btn-danger btn-sm pull-right" data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 删除</button>
			</sec:authorize>
		</div>
		<div class="panel-body form-horizontal">
			<div class="form-group">
				<label class="col-md-2 control-label" for="name">用户组织类型</label>
				<div class="col-md-9">
					<input id="name" class="form-control" value="${ot.name}" readonly/>
				</div>
			</div>

			<div style="padding-bottom: 5px; margin-top: 40px; margin-bottom: 25px; border-bottom: 1px #eee solid;">
				相关订单</div>

			<%-- 引入订单列表模板 --%>
			<%@ include file = "/WEB-INF/pages/orders.jsp" %>

		</div> <!-- /Panel body -->
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
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/customer/orgtype/del/${ot.id}" />">删除</a>
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