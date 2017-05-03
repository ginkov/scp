<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="display:none;" id="price_helper" data-pricelist='${pricelist}'> </div>

<div class="padding-xs">
	<div class="panel panel-default">
		<form:form modelAttribute="order" id="orderForm" name="orderForm" cssClass="form-horizontal" action="save?mobile" method="post" acceptCharset='utf-8'>
			<div class="panel-heading" style="padding-bottom: 20px; font-size: 16px;">
				<a href="<c:url value="/sale/order/list?mobile"/>" class='glink'>
					订单列表</a> / ${order.sn}<span class="text-danger"><form:errors path="*"/></span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id" />
				<form:hidden path="sn" />
				<div class="form-group" style="margin-bottom: 0px;">
					<label class="col-xs-3 label-xs">客户</label>
					<div class="col-xs-8">
						<form:hidden path="customer.id" id="customerId"/>
						<form:select id="customer" cssClass="form-control select2" path="customer.nameAndPhone" 
								data-placeholder="输入或选择，自由输入后按空格" value="${order.customer.name}" required="true">
							<form:option label="" value=""/>
							<form:options items="${customers}" itemLabel="nameAndPhone" itemValue="nameAndPhone"/>
						</form:select>
					</div>
				</div>
				<div class="row" style="margin-bottom: 3px;">
					<div class="glink" data-toggle="collapse" data-target="#divMoreProfile" style="height:30px;">
						<div class="col-xs-10  col-xs-offset-2 text-center"><i class="fa fa-angle-down fa-2x"></i></div>
					</div>
				</div>
				<div class="collapse" id="divMoreProfile" style="padding: 10px 0 2px 0;">	
					<div class="form-group">
						<label class="col-xs-3 label-xs">营销类型</label>
						<div class="col-xs-8">
							<form:select id="userSaleType" cssClass="form-control" path="userSaleType.id" data-placeholder="请选择">
								<form:option label="普通用户" value="3" selected="true"/>
								<form:options items="${userSaleTypes}" itemLabel="name" itemValue="id" />
							</form:select>
						</div>
					</div>
					<form:hidden id="description" path="description" value="手机录入"/>
					<div class="form-group">
						<label class="col-xs-3 label-xs" for="orderDate">下单日期</label>
						<div class="col-xs-8">
							<div class="input-group">
							<fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" var="odate"/>
							<form:input id="orderDate" cssClass="form-control datepicker" path="orderDate" value="${odate}" style="padding-left:15px;"/>
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							</div>
						</div>
					</div>
					<form:hidden path="deliverDate" value="${odate}"/>
					<div class="form-group">
						<label class="col-xs-3 label-xs" for="channelName">销售渠道</label>
						<div class="col-xs-8">
							<form:input id="channelName" cssClass="form-control" path="channelName" style="padding-left:15px;"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3 label-xs"> 付款状态</label>
						<div class="col-xs-8">
						<form:select path="payStatus" value="${payStatus}" cssClass="form-control">
							<form:option value="UNPAID" label="未付款" selected="true"/>
							<form:option value="DOWNPAID" label="已付定金"/>
							<form:option value="PAID" label="已付全款"/>
						</form:select>
						</div>
					</div>
				</div>
				<div class="form-group" style="margin-bottom: 3px;">
					<label class="col-xs-3 label-xs" for="discountPrice"> 总价格</label>
					<div class="col-xs-8">
 						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${order.discountPrice}" var="discountPrice" pattern=".00"/>
						<form:input id="discountPrice" cssClass="form-control" path="discountPrice" value="${discountPrice}" readonly="true" />
						</div>
					</div>
				</div>
				<form:hidden path="discount" value="${discount}"/>
				<form:hidden path="payDate"/>
				<form:hidden path="listPrice" value="${order.listPrice}" />
			</div> <!-- /Panel body -->

			<div class="panel-footer">
				<div style="padding-bottom: 25px; margin-bottom: 25px; border-bottom: 1px #eee solid;">
					商品清单	
					<input class="btn btn-default btn-md pull-right" type="button" value="添加"
						onclick="document.orderForm.action='input?mobile'; document.orderForm.submit();"/>
				</div>
  				<c:forEach var="item" items="${order.items}" varStatus="vs">
		 		<div class="form-group">
		 			<label class="label-xs col-xs-2">产品</label>
					<div class="col-xs-10">
						<form:select cssClass="form-control" path="items[${vs.index}].prodSelling.id" data-placeholder="请选择"
							onchange="productChanged(${vs.index})" id='prod${vs.index}' data-id="${vs.index}">
							<form:option value="" label=""/>
							<c:forEach var="optGrp" items="${prodOpts}" varStatus="optGrpIndex">
								<optgroup label="${optGrp.key}">
									<form:options items="${optGrp.value}" itemLabel="name" itemValue="id"/>
								</optgroup>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="label-xs col-xs-2">数量</label>
					<div class="col-xs-4">
						<form:input path="items[${vs.index}].quantity" type="number" min="0" 
							id='qty${vs.index}' cssClass="form-control" value="${item.quantity}" data-id="${vs.index}"
							onchange="qtyChanged(${vs.index})" />
					</div>
					<label class="label-xs col-xs-2">售价</label>
					<div class="col-xs-4">
						<fmt:formatNumber value="${item.discountPrice}" pattern=".00" var="itemDiscountPrice"/>
						<form:input path="items[${vs.index}].discountPrice"  cssClass="form-control"
							type="number" min="0" step="0.01"
							data-id="${vs.index}" id="discountprice${vs.index}" onchange="discountPriceChanged(${vs.index})"
							value="${itemDiscountPrice}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="label-xs col-xs-2">小计</label>
					<div class="col-xs-10" style="padding-bottom: 10px; border-bottom: 0.5px solid #ccc;">
						<fmt:formatNumber value="${item.totalPrice}" pattern=".00" var="itemsubtotal"/>
						<form:input path="items[${vs.index}].totalPrice"  cssClass="form-control text-right" 
							id='subtotal${vs.index}' value="${itemsubtotal}" readonly="true"/>
					</div>
				</div><!-- form-group  -->
				</c:forEach>
			</div><!-- panel-footer -->
		</form:form>
	</div> <!-- /Panel -->
</div> <!-- ./padding -->
		
<!-- Error Alerts  -->	
<%@ include file = "/WEB-INF/pages/alert.jsp" %>

<script src="<c:url value="/static/js/priceCalculatorM.js" />"></script>
<script>
	$().ready(function(){
			    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
				pricelist = ${pricelist};
// 				console.log(pricelist);

				$('.chosen-select').chosen({"search_contains":true});
				$('.chosen-nosearch').chosen({"disable_search":true});
				$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
				$('.select2').select2({
					tags: true,
					tokenSeparators: [',', ' ']
				});
			});
</script>