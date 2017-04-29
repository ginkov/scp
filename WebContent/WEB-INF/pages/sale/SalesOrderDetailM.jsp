<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-xs">
	<div class="panel panel-default">
		<div class="panel-heading" style="font-size:14px; height:48px;">
			<div>
				<a href="<c:url value="/sale/order/list?mobile"/>" class="glink">订单</a> / ${order.sn}<span id="pPhynetMsg"></span>
			    <sec:authorize access="hasAnyRole('SALES','SUPER')">
					<a class="btn btn-default btn-sm pull-right" href="<c:url value="/sale/order/edit/${order.id}?mobile"/>"><i class="fa fa-pencil"></i> 修改</a>
					<button style="margin-right: 10px;" class="btn btn-danger btn-sm pull-right" data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 删除</button>
				</sec:authorize>
			</div>
		</div>
		<div class="panel-body form-horizontal">
			<table class="table">
				<tr>
					<th class="active">客户
					<td>${order.customer.name}
					<th class="active">营销类型
					<td>${order.userSaleType.name}
				</tr>
				<tr>
					<th class="active">下单日期
					<td><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd"/>
					<th class="active">交货日期
					<td><fmt:formatDate value="${order.deliverDate}" pattern="yyyy-MM-dd"/>
				</tr>
				<tr>
					<th class="active">销售渠道
					<td>${order.channelName}
					<th class="active">实际售价
					<td style="font-weight: bold;" class="text-primary"><fmt:formatNumber value="${order.discountPrice}" type="currency"/>
				</tr>
				<tr>
					<th class="active">付款状态
					<td>${order.payStatus.description}
					<th class="active">付全款时间
					<td><fmt:formatDate value="${order.payDate}" pattern="yyyy-MM-dd"/>
				</tr>
				<tr>
					<th class="active">备注
					<td>${order.description}
				</tr>
			</table>

			<div style="margin: 20px 0px; padding: 5px; border-bottom: 1px lightgrey solid">
				订单详情
			</div>
			<table class="table table-hover">
			<thead>
				<tr>
					<th>产品
					<th class="text-right">数量
					<th class="text-right">售价
					<th class="text-right">小计
				</tr>	
			</thead>
			<tbody>
			<c:forEach var="orderItem" items="${order.items}" varStatus="vs">
				<tr>
					<td>${orderItem.prodSelling.name}
					<td class="text-right">${orderItem.quantity}
					<td class="text-right"><fmt:formatNumber type="currency" value="${orderItem.discountPrice}"/>
					<td class="text-right"><fmt:formatNumber type="currency" value="${orderItem.totalPrice}"/>
				</tr>
			</c:forEach>
			</tbody>
			</table>
		</div> <!-- /Panel body -->
		<div class="panel-footer">
			<div style="padding-top: 5px; padding-bottom: 25px; margin-bottom: 5px; border-bottom: 1px #eee solid;">
				服务记录	
				<a class="btn btn-default btn-sm pull-right" 
					href="<c:url value="/sale/servicerecord/edit/${order.id}?mobile"/>">
					<i class="fa fa-wrench"></i> 管理服务记录
				</a>
			</div>
			<c:forEach var="sr" items="${order.serviceRecords}">
			<div class="row" style="margin: 1px; padding: 3px 0; background-color: #eee;">
				<div class="col-xs-4"><i class='fa fa-calendar'></i> <fmt:formatDate pattern="yyyy-MM-dd" value="${sr.time}"/></div>
				<div class="col-xs-4"><i class='fa fa-user'></i> ${sr.servicePerson}</div>
				<div class="col-xs-4"><i class='fa fa-wrench'></i> ${sr.type }</div>
			</div>
			<div class="row">
				<div class="col-xs-11" style="margin: 3px 0 5px 15px;">${sr.record}</div>
			</div>
			</c:forEach>
		</div><!-- panel-footer -->
	</div> <!-- /Panel -->
</div> <!-- ./padding -->

<div class="modal fade" id="modalConfirmDel">
	<div class="modal-dialog modal-xs">
		<div class="modal-content">
			<div class="modal-body">
				确定要删除这个定单吗?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/sale/order/del/${order.id}?mobile" />">删除</a>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
  $(document).ready(function() {
/*     $('#orderItems').DataTable({
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
	}); */
/*     $('#tableServiceRecord').DataTable({
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
    }) */
  });
</script>