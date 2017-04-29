<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
			<div style="margin: 0 2px;">
				<table class="table table-striped table-hover" id="orders">
					<thead>
						<tr>
							<th>订单号</th>
							<th style="max-width: 200px; text-align:center;">货品</th>
							<sec:authorize access="hasAnyRole('FINANCE')">
								<th><i class="fa fa-check-square-o"></i></th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${orders}" var="order">
						<tr>
							<td><a href="<c:url value="/sale/order/detail/${order.id}?mobile"/>" class="glink">${order.sn}</a>
							<td>${order.prodSummary}
							<sec:authorize access="hasAnyRole('FINANCE')">
								<td>
								<c:if test="${order.payStatus != 'PAID'}">
									<a href="<c:url value="/sale/order/fullpay/${order.id}?mobile" />" class="btn btn-xs btn-default">确认收全款</a>
								</c:if>
							</sec:authorize>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>