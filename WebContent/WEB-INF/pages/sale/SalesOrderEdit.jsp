<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div style="display:none;" id="opt_helper" data-pricelist='${pricelist}'>
</div>
<div class="padding-md">
	<div class="panel panel-default">
		<c:url value="/sale/order/update" var="updateUrl"/>
		<c:url value="/sale/order/edit/${order.id}" var="editUrl" />
		<form:form modelAttribute="order" name="orderForm" cssClass="form-horizontal" action="${updateUrl}" method="post" acceptCharset='utf-8'>
			<div class="panel-heading" style="font-size:14px; height:52px;">
				<a href="<c:url value="/sale/order/list"/>" class="glink">订单列表</a> / 修改 ${order.sn} <span id="pPhynetMsg"></span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id"/>
				<form:hidden path="sn"/>
				<div class="form-group">
					<label class="col-md-2 control-label" for="customer">客户</label>
					<div class="col-md-4">
						<form:select id="customer" cssClass="form-control" path="customer.id"
							items="${customers}" itemLabel="nameAndPhone" itemValue="id"/>
					</div>
					<label class="col-md-1 control-label" for="userSaleType">营销类型</label>
					<div class="col-md-4">
						<form:select id="userSaleType" cssClass="form-control" path="userSaleType.id"
							items="${userSaleTypes}" itemLabel="name" itemValue="id"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="description">备注</label>
					<div class="col-md-9">
						<form:input id="description" cssClass="form-control" path="description"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="orderDate">下单日期</label>
					<div class="col-md-2">
						<div class="input-group">
						<form:input id="orderDate" cssClass="form-control datepicker" path="orderDate" style="padding-left: 15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label" for="deliverDate">交付日期</label>
					<div class="col-md-2">
						<div class="input-group">
						<form:input id="deliverDate" cssClass="form-control datepicker" path="deliverDate" style="padding-left: 15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label" for="channelName">销售渠道</label>
					<div class="col-md-3">
						<form:input id="channelName" cssClass="form-control" path="channelName" style="padding-left: 15px;"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="listPrice"> 列表价</label>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${order.listPrice}" pattern=".00" var="listPrice"/>
						<form:input id="listPrice" cssClass="form-control text-right" path="listPrice" value="${listPrice}" readonly="true" />
						</div>
					</div>
					<label class="col-md-1 control-label" for="discountPrice"> 实际价格</label>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${order.discountPrice}" pattern=".00" var="discountPrice"/>
						<form:input id="discountPrice" cssClass="form-control text-right" path="discountPrice" value="${discountPrice}" readonly="true" />
						</div>
					</div>
					<label class="col-md-1 control-label" for="discount"> 整体折扣</label>
					<div class="col-md-3">
						<fmt:formatNumber value="${order.discount}" type="percent" var="discount"/>
						<form:hidden path="discount"/>
						<input id="discount" class="form-control" value="${discount}" readonly />
					</div>
				</div>
			</div> <!-- /Panel body -->

			<div class="panel-footer">
				<div style="padding-bottom: 15px; margin-bottom: 25px; border-bottom: 1px #eee solid;">
					货品清单	
					<input style="width:100px;" class="btn btn-default btn-xs pull-right" type="button" value="添加/更新货品"
						onclick="document.orderForm.action='${editUrl}'; document.orderForm.submit();"/>
				</div>
				<div class="form-group">
					<div class="col-md-2 col-md-offset-2" style="text-align:center;">产品</div>
					<div class="col-md-1" style="padding-left: 27px;">数量</div>
					<div class="col-md-1" style="padding-left: 27px;">标价 (￥)</div>
					<div class="col-md-1" style="padding-left: 27px;">折扣</div>
					<div class="col-md-2 text-right" style="padding-right: 28px;">折扣价</div>
					<div class="col-md-2 text-right" style="padding-right: 28px;">小计</div>
				</div>
  				<c:forEach var="item" items="${order.items}" varStatus="vs">
  					<form:hidden path="items[${vs.index}].id"/>
		 			<div class="form-group">
					<label class="col-md-2 control-label">${vs.index + 1}</label>
					<div class="col-md-2">
						<form:select cssClass="form-control chosen-select" path="items[${vs.index}].prodSelling.id" data-placeholder="请选择"
							data-id="${vs.index}" id="prod${vs.index}" onchange="productChanged(this.getAttribute('data-id'))">
							<form:option value="" label=""/>
							<c:forEach var="optGrp" items="${prodOpts}" varStatus="optGrpIndex">
								<optgroup label="${optGrp.key}">
									<form:options items="${optGrp.value}" itemLabel="name" itemValue="id"/>
								</optgroup>
							</c:forEach>
						</form:select>
					</div>
					<div class="col-md-1">
						<form:input path="items[${vs.index}].quantity" type="number" cssClass="form-control" min="0"
							data-id="${vs.index}" id="qty${vs.index}" onchange="qtyChanged(this.getAttribute('data-id'))" />
					</div>
					<div class="col-md-1">
						<fmt:formatNumber value="${item.listPrice}" pattern=".00" var="itemListPrice"/>
						<form:input path="items[${vs.index}].listPrice"  cssClass="form-control text-right" 
							data-id="${vs.index}" id="listprice${vs.index}" 
							value="${itemListPrice}" readonly="true"/>
					</div>
					<div class="col-md-1">
						<form:input path="items[${vs.index}].discount" type="number" min="0" max="1" step="0.01"
							data-id="${vs.index}" id="discount${vs.index}" onchange="discountChanged(this.getAttribute('data-id'))"
							cssClass="form-control"/>
					</div>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${item.discountPrice}" pattern=".00" var="itemDiscountPrice"/>
						<form:input path="items[${vs.index}].discountPrice"  cssClass="form-control"
							type="number" min="0" step="0.01"
							data-id="${vs.index}" id="discountprice${vs.index}" onchange="discountPriceChanged(this.getAttribute('data-id'))"
							value="${itemDiscountPrice}"/>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${item.totalPrice}" pattern=".00" var="itemTotalPrice"/>
						<form:input path="items[${vs.index}].totalPrice"  cssClass="form-control text-right" 
							data-id="${vs.index}" id="subtotal${vs.index}"
							value="${itemTotalPrice}" readonly="true"/>
						</div>
					</div>
					</div><!-- form-group  -->
				</c:forEach>
				<c:forEach var="sr" items="${order.serviceRecords}" varStatus="i">
					<form:hidden path="serviceRecords[${i.index}].id"/>
					<form:hidden path="serviceRecords[${i.index}].time"/>
					<form:hidden path="serviceRecords[${i.index}].type"/>
					<form:hidden path="serviceRecords[${i.index}].record"/>
				</c:forEach>
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
				$('.chosen-select').chosen({"search_contains":true});
				$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
			});
</script>
