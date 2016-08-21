<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_CN" scope="session"/>
			<div style="margin: 0 20px;">
				<table class="table table-striped table-hover" id="orders">
					<thead>
						<tr>
							<th>订单号</th>
							<th style="text-align:right;">客户</th>
							<th style="text-align:right;">营销类型</th>
							<th style="text-align:right;">下单日期</th>
							<th style="text-align:right;">销售渠道</th>
							<!-- <th style="text-align:right;">交付日期</th> -->
							<!-- <th style="text-align:right;">列表价格</th> -->
							<th style="text-align:right;">实际价格</th>
							<th style="max-width: 200px; text-align:center;">货品</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${orders}" var="order">
						<tr>
							<td><a href="<c:url value="/sale/order/detail/${order.id}"/>" class="glink">${order.sn}</a>
							<td style="text-align:right;">
								<a href="<c:url value="/customer/customer/detail/${order.customer.id}" />" class="glink">${order.customer.name}</a>
							<td style="text-align:right;">
								<a href="<c:url value="/sale/usertype/detail/${order.userSaleType.id}" />" class="glink">${order.userSaleType.name}</a>
							<td style="text-align:right;"><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd"/>
							<td style="text-align:right;">${order.channelName}
							<%-- <td style="text-align:right;"><fmt:formatDate value="${order.deliverDate}" pattern="yyyy-MM-dd"/>--%>
							<%-- <td style="text-align:right;"><fmt:formatNumber type="currency" value="${order.listPrice}"/> --%>
							<td style="text-align:right;"><fmt:formatNumber type="currency" value="${order.discountPrice}"/>
							<td>${order.prodSummary}
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>