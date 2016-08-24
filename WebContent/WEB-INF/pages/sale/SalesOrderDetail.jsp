<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading" style="font-size:14px; height:52px;">
				<a href="<c:url value="/sale/order/list"/>" class="glink">订单列表</a> / ${order.sn}<span id="pPhynetMsg"></span>
			    <sec:authorize access="hasAnyRole('ADMIN','SUPER')">
				<a style="width:100px;" class="btn btn-default btn-sm pull-right" href="<c:url value="/sale/order/edit/${order.id}"/>"><i class="fa fa-pencil"></i> 修改</a>
				<button style="width:60px; margin-right: 20px;" class="btn btn-danger btn-sm pull-right" data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 删除</button>
				</sec:authorize>
		</div>
		<div class="panel-body form-horizontal">
			<div class="form-group">
				<label class="col-md-2 control-label" for="customer">客户</label>
				<div class="col-md-4">
					<input class="form-control" value="${order.customer.name}" readonly>
				</div>
				<label class="col-md-1 control-label" for="userSaleType">营销类型</label>
				<div class="col-md-4">
					<input class="form-control" value="${order.userSaleType.name}" readonly>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label" for="description">备注</label>
				<div class="col-md-9">
					<input class="form-control" value="${order.description}" readonly>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label">下单日期</label>
				<div class="col-md-2">
					<input class='form-control' value="<fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd"/>" readonly />
				</div>
				<label class="col-md-1 control-label" for="deliverDate">交付日期</label>
				<div class="col-md-2">
					<input id='deliverDate' class='form-control' value="<fmt:formatDate value="${order.deliverDate}" pattern="yyyy-MM-dd"/>" readonly />
				</div>
				<label class="col-md-1 control-label" for="channelName">销售渠道</label>
				<div class="col-md-3">
					<input id='channelName' class='form-control' value="${order.channelName}" readonly />
				</div>
			</div>

			<div class="form-group">
				<label class="col-md-2 control-label" for="listPrice"> 列表价</label>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon">￥</span>
						<input class="form-control text-right" value="<fmt:formatNumber value="${order.listPrice}" pattern=".00"/>" readonly>
					</div>
				</div>
				<label class="col-md-1 control-label" for="discountPrice"> 实售价格</label>
				<div class="col-md-2">
					<div class="input-group">
						<span class="input-group-addon">￥</span>
						<input class="form-control text-right" value="<fmt:formatNumber value="${order.discountPrice}" pattern=".00"/>" readonly>
					</div>
				</div>
				<label class="col-md-1 control-label" for="discount"> 总体折扣</label>
				<div class="col-md-3">
					<input class="form-control" value="<fmt:formatNumber value="${order.discount}" type="percent" />" readonly>
				</div>
			</div>
			<div class="form-group">
				<label class="col-md-2 control-label"> 付款状态</label>
				<div class="col-md-2">
					<input class="form-control" value="${order.payStatus.description}" readonly>
				</div>
				<label class="col-md-1 control-label"> 收到全款时间</label>
				<div class="col-md-2">
					<div class="input-group">
					<input class='form-control' value="<fmt:formatDate value="${order.payDate}" pattern="yyyy-MM-dd"/>" readonly />
					<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					</div>
				</div>
			</div>
			<div style="margin: 20px 0px; padding: 5px; border-bottom: 1px lightgrey solid">
				订单详情
			</div>
			<div style="margin: 0 20px;">
			<table id="orderItems" class="table table-stripped table-hover">
			<thead>
				<tr>
					<th>产品
					<th class="text-right">数量
					<th class="text-right">单价
					<th class="text-right">折扣
					<th class="text-right">折扣价
					<th class="text-right">小计
				</tr>	
			</thead>
			<tbody>
			<c:forEach var="orderItem" items="${order.items}" varStatus="vs">
				<tr>
					<%-- <td><a href="<c:url value="/product/selling/detail/${orderItem.prodSelling.id}"/>">${orderItem.prodSelling.name}</a>--%>
					<td>${orderItem.prodSelling.name}
					<td class="text-right">${orderItem.quantity}
					<td class="text-right"><fmt:formatNumber type="currency" value="${orderItem.listPrice}"/>
					<td class="text-right"><fmt:formatNumber type="percent"  value="${orderItem.discount}"/>
					<td class="text-right"><fmt:formatNumber type="currency" value="${orderItem.discountPrice}"/>
					<td class="text-right"><fmt:formatNumber type="currency" value="${orderItem.totalPrice}"/>
				</tr>
			</c:forEach>
			</tbody>
			</table>
			</div>
		</div> <!-- /Panel body -->
		<div class="panel-footer">
			<div style="padding-top: 5px; padding-bottom: 15px; margin-bottom: 15px; border-bottom: 1px #eee solid;">
				服务记录	
				<a style="width:100px;" class="btn btn-default btn-xs pull-right" href="<c:url value="/sale/servicerecord/edit/${order.id}"/>">管理服务记录</a>
			</div>
			<div style="margin: 0 20px;">
			<table class="table table-stripped table-hover" id="tableServiceRecord">
				<thead>
					<tr>
						<th>时间
						<th>服务人
						<th>类型
						<th>记录
					</tr>
				</thead>
				<tbody>
				<c:forEach var="sr" items="${order.serviceRecords}">
					<tr>
						<td><fmt:formatDate pattern="yyyy-MM-dd" value="${sr.time}"/>
						<td>${sr.servicePerson}
						<td>${sr.type}
						<td>${sr.record}
					</tr>	
				</c:forEach>
				</tbody>
			</table>
			</div>
		</div><!-- panel-footer -->
	</div> <!-- /Panel -->
</div> <!-- ./padding -->

<div class="modal fade" id="modalConfirmDel">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-body">
				确定要删除这个定单吗?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/sale/order/del/${order.id}" />">删除</a>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
  $(document).ready(function() {
    $('#orderItems').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "lengthChange": false
    	, "orderClasses": false
    	, "searching": false
    	, "paging": false
    	, "info": false
  //  	, columnDefs: [
  //  		{targets:[0,1,2,3], orderable: false }
  //  	]
	});
    $('#tableServiceRecord').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "lengthChange": false
    	, "orderClasses": false
    	, "searching": false
    	, "paging": false
    	, "info": false
      	, columnDefs: [
      		{targets:[2], orderable: false }
      	]
    })
  });
</script>