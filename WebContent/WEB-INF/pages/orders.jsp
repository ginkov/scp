<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
			<div style="margin: 0 20px;">
				<table class="table table-striped table-hover" id="orders">
					<thead>
						<tr>
							<th>订单号</th>
							<th style="text-align:right;">下单日期</th>
							<th style="text-align:right;">销售渠道</th>
							<th style="text-align:right;">实际价格(￥)</th>
							<th style="text-align:right;">付款状态</th>
							<th style="max-width: 200px; text-align:center;">货品</th>
							<sec:authorize access="hasAnyRole('SALES')">
								<th style="text-align:right;">客户</th>
								<th style="text-align:right;">营销类型</th>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('FINANCE')">
								<th>收款</th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${orders}" var="order">
						<tr>
							<td><a href="<c:url value="/sale/order/detail/${order.id}"/>" class="glink">${order.sn}</a>
							<td style="text-align:right;"><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd"/>
							<td style="text-align:right;">${order.channelName}
							<td style="text-align:right;"><fmt:formatNumber type="number" pattern="#,###.00" value="${order.discountPrice}"/>
							<td style="text-align:right;">
								<c:choose>
									<c:when test="${order.payStatus == 'PAID' }">
										${order.payStatus.description}
									</c:when>
									<c:otherwise>
										<span class="text-danger">${order.payStatus.description}</span>
									</c:otherwise>	
								</c:choose>
							<td>${order.prodSummary}
							<sec:authorize access="hasAnyRole('SALES')">
								<td style="text-align:right;">
									<a href="<c:url value="/customer/customer/detail/${order.customer.id}" />" class="glink">${order.customer.name}</a>
								<td style="text-align:right;">
									<a href="<c:url value="/sale/usertype/detail/${order.userSaleType.id}" />" class="glink">${order.userSaleType.name}</a>
							</sec:authorize>
							<sec:authorize access="hasAnyRole('FINANCE')">
								<td>
								<c:if test="${order.payStatus != 'PAID'}">
									<a href="<c:url value="/sale/order/fullpay/${order.id}" />" class="btn btn-xs btn-default">确认收全款</a>
								</c:if>
							</sec:authorize>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>