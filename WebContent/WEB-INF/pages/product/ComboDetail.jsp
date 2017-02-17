<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading" style="font-size:14px; height:52px;">
			<span><a href="<c:url value="/product/combo/list"/>" class='glink'>产品套装</a> / 详情 </span>
			<sec:authorize access="hasAnyRole('PRODUCT','SUPER')">
				<a class="btn btn-default btn-sm pull-right" href="<c:url value="/product/combo/edit/${combo.id}"/>" style="width: 100px;"><i class="fa fa-pencil"></i> 修改</a>
				<button style="width:60px; margin-right: 20px;" class="btn btn-danger btn-sm pull-right" data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 删除</button>
			</sec:authorize>
		</div>
		<div class="panel-body form-horizontal">
			<div class="form-group">
				<label class="col-md-2 control-label" for="name">名称</label>
				<div class="col-md-4">
					<input id="name" class="form-control" value="${combo.name}" readonly/>
				</div>
				<label class="col-md-1 control-label" for="price">列表价</label>
				<div class="col-md-4">
					<input id="price" class="form-control" value="<fmt:formatNumber type="currency" value="${combo.listprice}" />" readonly />
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label" for="description">描述</label>
				<div class="col-md-9">
					<input id="description" class="form-control"
						value="${combo.description}" readonly />
				</div>
			</div>
			<!-- 产品系列与价格 -->
			<div class="form-group">
				<label class="col-md-2 control-label" for="c1">功能类型</label>
				<div class="col-md-4">
					<input id="c1" class="form-control"
						value="${combo.c2.c1.name}" readonly />
				</div>
				<label class="col-md-1 control-label" for="c2">功能子类</label>
				<div class="col-md-4">
					<input id="c2" class="form-control"
						value="${combo.c2.name}" readonly />
				</div>
			</div>
			<!-- 上线/下线时间 -->
			<div class="form-group">
				<label for="online_date" class="col-md-2 control-label">上线日期</label>
				<div class="col-md-4">
					<input id="online_date" class="form-control" value="<fmt:formatDate value="${combo.onlineDate}" pattern="yyyy-MM-dd"/>" readonly/>
				</div>
				<label class="col-md-1 control-label" for="offline_date">下线日期</label>
				<div class="col-md-4">
					<input id="offline_date" class="form-control" value="<fmt:formatDate value="${combo.offlineDate}" pattern="yyyy-MM-dd"/>" readonly/>
				</div>
			</div>
			
			<div style="margin: 20px 0px; padding: 5px; border-bottom: 1px lightgrey solid">
				套装详情
			</div>
			<div style="margin: 0 20px;">
			<table id="partItems" class="table table-stripped table-hover">
			<thead>
				<tr>
					<th>部件
					<th class="text-right">类型
					<th class="text-right">子类
					<th class="text-right">列表价
					<th class="text-right">数量
				</tr>	
			</thead>
			<tbody>
			<c:forEach var="partItem" items="${combo.partslist}" varStatus="vs">
				<tr>
					<td>${partItem.part.name}
					<td class="text-right">${partItem.part.c2.c1.name}
					<td class="text-right">${partItem.part.c2.name}
					<td class="text-right">${partItem.part.listprice}
					<td class="text-right">${partItem.quantity}
				</tr>
			</c:forEach>
			</tbody>
			</table>
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
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/product/combo/del/${combo.id}" />">删除</a>
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