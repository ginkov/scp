<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div style="display:none;" id="price_helper" data-pricelist='${pricelist}'> </div>

<div class="padding-md">
	<div class="panel panel-default">
		<form:form modelAttribute="order" id="orderForm" name="orderForm" cssClass="form-horizontal" action="save" method="post" acceptCharset='utf-8'>
			<div class="panel-heading" style="padding-bottom: 25px; font-size: 14px;">
				<a href="<c:url value="/sale/order/list"/>" class='glink'>订单列表</a> / ${order.sn}<span class="text-danger"><form:errors path="*"/></span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id" />
				<form:hidden path="sn" />
				<div class="form-group" style="margin-top: 15px">
					<label class="col-md-2 control-label" for="customer">客户 ${order.customer.name}</label>
					<div class="col-md-4">
						<form:hidden path="customer.id" id="customerId"/>
						<form:select id="customer" cssClass="form-control select2" path="customer.nameAndPhone" 
								data-placeholder="输入或选择，自由输入后按空格" value="${order.customer.name}" required="true">
							<form:option label="" value=""/>
							<form:options items="${customers}" itemLabel="nameAndPhone" itemValue="nameAndPhone"/>
						</form:select>
					</div>
					<label class="col-md-1 control-label" for="userSaleType">营销类型</label>
					<div class="col-md-4">
						<form:select id="userSaleType" cssClass="form-control select2" path="userSaleType.id" data-placeholder="请选择">
							<form:option label="" value=""/>
							<form:options items="${userSaleTypes}" itemLabel="name" itemValue="id" />
						</form:select>
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
						<fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd" var="odate"/>
						<form:input id="orderDate" cssClass="form-control datepicker" path="orderDate" value="${odate}" style="padding-left:15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label" for="deliverDate">交付日期</label>
					<div class="col-md-2">
						<div class="input-group">
						<form:input id="deliverDate" cssClass="form-control datepicker" path="deliverDate" style="padding-left:15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label" for="channelName">销售渠道</label>
					<div class="col-md-3">
						<form:input id="channelName" cssClass="form-control" path="channelName" style="padding-left:15px;"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label" for="listPrice"> 总列表价</label>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${order.listPrice}" var="listPrice" pattern=".00"/>
						<form:input id="listPrice" cssClass="form-control" path="listPrice" value="${listPrice}" readonly="true"/>
						</div>
					</div>
					<label class="col-md-1 control-label" for="discountPrice"> 实际价格</label>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${order.discountPrice}" var="discountPrice" pattern=".00"/>
						<form:input id="discountPrice" cssClass="form-control" path="discountPrice" value="${discountPrice}" readonly="true" />
						</div>
					</div>
					<label class="col-md-1 control-label" for="discount"> 整体折扣</label>
					<div class="col-md-3">
						<fmt:formatNumber value="${order.discount}" var="discount" pattern=".00"/>
						<form:input id="discount" cssClass="form-control" path="discount" value="${discount}" readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label"> 付款状态</label>
					<div class="col-md-2">
						<form:select cssClass="chosen-select" path="payStatus" value="${payStatus}">
							<form:option value="UNPAID" label="未付款" selected="true"/>
							<form:option value="DOWNPAID" label="已付定金"/>
							<form:option value="PAID" label="已付全款"/>
						</form:select>
					</div>
					<label class="col-md-1 control-label"> 收到全款时间</label>
					<div class="col-md-2">
						<div class="input-group">
						<form:input cssClass="form-control datepicker" path="payDate" style="padding-left:15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>
			</div> <!-- /Panel body -->

			<div class="panel-footer">
				<div style="padding-bottom: 25px; margin-bottom: 25px; border-bottom: 1px #eee solid;">
					货品清单	
					<input class="btn btn-default btn-md pull-right" type="button" value="添加/更新货品"
						onclick="document.orderForm.action='input'; document.orderForm.submit();"/>
				</div>
				<div class="form-group">
					<div class="col-md-3 col-md-offset-1" style="text-align:center;">产品部件或套装</div>
					<div class="col-md-1" style="padding-left: 27px;">数量</div>
					<div class="col-md-1" style="padding-left: 27px;">标价(￥)</div>
					<div class="col-md-1" style="padding-left: 27px;">折扣</div>
					<div class="col-md-2 text-right" style="padding-right: 28px;">折扣价</div>
					<div class="col-md-2 text-right" style="padding-right: 28px;">小计</div>
				</div>
				<c:if test="${not empty order.items}">
  				<c:forEach var="item" items="${order.items}" varStatus="vs">
		 			<div class="form-group">
					<label class="col-md-1 control-label">${vs.index + 1}</label>
					<form:hidden path="items[${vs.index}].id"/>
					<div class="col-md-3">
						<form:select cssClass="chosen-select" path="items[${vs.index}].prodSelling.id" data-placeholder="请选择"
							onchange="productChanged(this.getAttribute('data-id'))" id='prod${length}' data-id="${length}">
							<form:option value="" label=""/>
							<c:forEach var="optGrp" items="${prodOpts}" varStatus="optGrpIndex">
								<optgroup label="${optGrp.key}">
									<form:options items="${optGrp.value}" itemLabel="name" itemValue="id"/>
								</optgroup>
							</c:forEach>
						</form:select>
						<%-- 
						<form:select cssClass="chosen-select" path="items[${vs.index}].prodSelling.id"
							onchange="productChanged(this.getAttribute('data-id'))"  id='prod${vs.index}' data-id="${vs.index}"
							items="${prodSellings}" itemLabel="name" itemValue="id" value="${item.prodSelling.id}" data-placeholder="选择产品组件">
						</form:select>
						--%>
					</div>
					<div class="col-md-1">
						<form:input path="items[${vs.index}].quantity" type="number" min="0" 
							id='qty${vs.index}' cssClass="form-control" value="${item.quantity}" data-id="${vs.index}"
							onchange="qtyChanged(this.getAttribute('data-id))" />
					</div>
					<div class="col-md-1">
						<fmt:formatNumber value="${item.listPrice}" pattern=".00" var="itemListPrice"/>
						<form:input path="items[${vs.index}].listPrice"  cssClass="form-control text-right" value="${itemListPrice}"
							id='listprice${vs.index}' readonly="true"/>
					</div>
					<div class="col-md-1">
						<form:input path="items[${vs.index}].discount" type="number" min="0" max="1" step="0.01" 
							id='discount${vs.index}' cssClass="form-control" value="${item.discount}" data-id="${vs.index}"
							onchange="discountChanged(this.getAttribute('data-id))" />
					</div>
					<%--
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<fmt:formatNumber value="${item.discountPrice}" pattern=".00" var="itemDiscountPrice"/>
						<form:input path="items[${vs.index}].discountPrice"  cssClass="form-control" type="number" min="0" step="0.01"
							id='discountprice${vs.index}' value="${itemDiscountPrice}" data-id="${vs.index}"
							onchange="discountPriceChanged(this.getAttribute('data-id))" />
						</div>
					</div>
					 --%>
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
						<fmt:formatNumber value="${item.totalPrice}" pattern=".00" var="itemsubtotal"/>
						<form:input path="items[${vs.index}].totalPrice"  cssClass="form-control text-right" 
							id='subtotal${vs.index}' value="${itemsubtotal}" readonly="true"/>
						</div>
					</div>
					</div><!-- form-group  -->
				</c:forEach>
				</c:if>
     			<c:set var="length" value="${fn:length(order.items)}" />
				<div class="form-group">
					<label class="col-md-1 control-label">${length + 1}</label>
					<form:hidden path="items[${length}].id"/>
					<div class="col-md-3">
					<!--  
						<form:select cssClass="chosen-select" path="items[${length}].prodSelling.id" 
							onchange="productChanged(this.getAttribute('data-id'))" id='prod${length}' data-id="${length}"
							items="${prodSellings}" itemLabel="name" itemValue="id" data-placeholder="选择产品组件" >
							<option value="0">请选择产品</option>
						</form:select>
					-->
						<form:select cssClass="chosen-select" path="items[${length}].prodSelling.id" data-placeholder="请选择"
							onchange="productChanged(this.getAttribute('data-id'))" id='prod${length}' data-id="${length}">
							<form:option value="" label=""/>
							<c:forEach var="optGrp" items="${prodOpts}" varStatus="optGrpIndex">
								<optgroup label="${optGrp.key}">
									<form:options items="${optGrp.value}" itemLabel="name" itemValue="id"/>
								</optgroup>
							</c:forEach>
						</form:select>
					</div>
					<div class="col-md-1">
						<form:input path="items[${length}].quantity" type="number" min="0" data-id="${length}"
							id='qty${length}' cssClass="form-control" placeholder="数量" 
							onchange="qtyChanged(this.getAttribute('data-id'))"/>
					</div>
					<div class="col-md-1">
						<form:input path="items[${length}].listPrice"  cssClass="form-control text-right" placeholder="标价"
							id="listprice${length}" readonly="true"/>
					</div>
					<div class="col-md-1">
						<form:input path="items[${length}].discount" type="number" data-id="${length}"
							id='discount${length}' min="0" max="1" step="0.01" cssClass="form-control" placeholder="折扣"
							onchange="discountChanged(this.getAttribute('data-id'))"/>
					</div>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<form:input path="items[${length}].discountPrice"  cssClass="form-control" data-id="${length}"
							type="number" min="0" step="0.01"
							id="discountprice${length}" placeholder="折扣价" onchange="discountPriceChanged(this.getAttribute('data-id))"/>
						</div>
					</div>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<form:input path="items[${length}].totalPrice"  cssClass="form-control text-right" 
							id='subtotal${length}' placeholder="小计" readonly="true"/>
						</div>
					</div>
				</div><!-- form-group  -->
			</div><!-- panel-footer -->
		</form:form>
	</div> <!-- /Panel -->
</div> <!-- ./padding -->
		
<!-- Error Alerts  -->	
<%@ include file = "/WEB-INF/pages/alert.jsp" %>

<script src="/scp/static/magicsuggest/magicsuggest-min.js"></script>
<script src="<c:url value="/static/js/priceCalculator.js" />"></script>
<link rel="stylesheet" href="/scp/static/magicsuggest/magicsuggest-min.css">
<script>

/*
	var pricelist = {};	

	function updateListPrice(id){
		var dbid = $('#prod'+id).val();
		$('#listprice'+id).val(Number(pricelist[dbid]).toFixed(2));
	}
	function updateDiscount(id){
		var listprice = $('#listprice'+id).val();
		var discountprice = $('#discountprice'+id).val();
		$('#discount'+id).val(Number(discountprice/listprice).toFixed(2));
	}
	function updateDiscountPrice(id){
		var listprice = $('#listprice'+id).val();
		var discount = $('#discount'+id).val();
		$('#discountprice'+id).val(Number(listprice*discount).toFixed(2));
	}
	function updateSubtotal(id){
		var qty = $('#qty'+id).val();
		var discountprice = $('#discountprice'+id).val();
		$('#subtotal'+id).val(Number(qty*discountprice).toFixed(2));
	}
	function setQty(id, v) {
		$('#qty'+id).val(v)
	}
	//=================
	function productChanged(id){
		updateListPrice(id);
		updateDiscountPrice(id);
		setQty(id,1)
		updateSubtotal(id);
	}
	function qtyChanged(id){
		updateListPrice(id);
		updateDiscountPrice(id);
		updateSubtotal(id);
	}
	function discountChanged(id){
		updateDiscountPrice(id);
		updateSubtotal(id);
	}
	function discountPriceChanged(id){
		updateDiscount(id);
		updateSubtotal(id);
	}
	*/
	$().ready(function(){
			    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
				pricelist = $('#price_helper').data('pricelist');
				/*
				$('#customer').magicSuggest({
					data: $('#customer_helper').data('customerlist')
		 			, noSuggestionText: "新客户"
		 	  		, placeholder: "选择现有客户，或输入新客户名"
		 			, maxSuggestions: 10
		 			, maxDropHeight: 140
		 			, maxSelectionRenderer: function(){return "OK";}
		 	  		, highlight: false
		 	  		, useZebraStyle: true
				}); */
				$('.chosen-select').chosen({"search_contains":true});
				$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
				$('.select2').select2({
				//$('#customer').select2({
					tags: true,
					tokenSeparators: [',', ' ']
				});
			});
</script>