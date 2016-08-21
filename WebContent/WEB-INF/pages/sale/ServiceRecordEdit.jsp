<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
		<c:url value="/sale/servicerecord/update" var="updateUrl"/>
		<form:form modelAttribute="order" cssClass="form-horizontal" action="${updateUrl}" method="post" acceptCharset='utf-8'>
		<div class="panel-heading">
			<a href="<c:url value="/sale/order/list"/>">订单列表</a> / ${order.sn} / 服务记录 <span id="pPhynetMsg"></span>
		</div>
		<div class="panel-body">
			<form:hidden path="id"/>
			<form:hidden path="sn"/>
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
				<label class="col-md-2 control-label" for="orderDate">下单日期</label>
				<div class="col-md-2">
					<input id='orderDate' class='form-control' value="<fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd"/>" readonly />
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
					<input class="form-control" value="<fmt:formatNumber value="${order.discount}" pattern=".000" />" readonly>
				</div>
			</div>

			<div style="margin: 40px 5px; padding: 5px 5px 15px 5px; border-bottom: 1px lightgrey solid;">
				服务记录
				<input class="btn btn-success btn-xs pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
  			<c:forEach var="sr" items="${order.serviceRecords}" varStatus="vs">
  			<div style="background-color: #f8f8f8; padding: 10px 0 1px 0; margin: 15px 0;">
		 		<div class="form-group">
					<label class="col-md-1 control-label">${vs.index + 1}</label>
					<form:hidden path="serviceRecords[${vs.index}].id"/>
					<label class="col-md-1 control-label">日期</label>
					<div class="col-md-2">
						<div class="input-group">
						<form:input path="serviceRecords[${vs.index}].time" cssClass="form-control datepicker"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label">服务人</label>
					<div class="col-md-2">
						<form:input cssClass="form-control" path="serviceRecords[${vs.index}].servicePerson"/>
					</div>
					<label class="col-md-1 control-label">服务类型</label>
					<div class="col-md-3">
						<form:select cssClass="chosen-select" path="serviceRecords[${vs.index}].type" >
							<option value="定期追踪">定期追踪</option>
							<option value="故障维修">故障维修</option>
							<option value="主动更换">主动更换</option>
						</form:select>
					</div>
				</div> <!-- form-group -->
				<div class="form-group">
					<label class="col-md-2 control-label">记录</label>
					<div class="col-md-9">
						<form:textarea path="serviceRecords[${vs.index}].record" cssClass="form-control" style="resize:none;"/>
					</div>
				</div><!-- form-group  -->
			</div>
			</c:forEach>
		</div> <!-- /Panel body -->

		<div class="panel-footer">
			<div style="padding-top: 15px; margin-bottom: 25px;">
					货品清单	
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
					<td>${orderItem.prodSelling.name}
					<td class="text-right">${orderItem.quantity}
					<td class="text-right"><fmt:formatNumber type="currency" value="${orderItem.listPrice}"/>
					<td class="text-right">${orderItem.discount}
					<td class="text-right"><fmt:formatNumber type="currency" value="${orderItem.discountPrice}"/>
					<td class="text-right"><fmt:formatNumber type="currency" value="${orderItem.totalPrice}"/>
				</tr>
			</c:forEach>
			</tbody>
			</table>
			</div>
		</div><!-- panel-footer -->
		</form:form>
	</div> <!-- /Panel -->
</div> <!-- ./padding -->
<script type="text/javascript" src="<c:url value="/static/js/priceCalculator.js" />"></script>
<script type="text/javascript">
	var pricelist = {};	
	$().ready(
			function(){
				pricelist = $('#opt_helper').data('pricelist');
				$('.chosen-select').chosen({'disable_search': true});
			});
</script>
