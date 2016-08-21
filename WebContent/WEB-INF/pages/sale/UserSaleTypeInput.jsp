<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="padding-md">
	<div class="panel panel-default">
		<form:form commandName="userSaleType" cssClass="form-horizontal" action="save" method="post" acceptCharset='utf-8'>
			<div class="panel-heading" style="font-size:14px; height:52px;">
				<a href="<c:url value="/sale/usertype/list"/>" class="glink">优惠类型</a> / 添加 <span id="pPhynetMsg"></span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="添加"/>
			</div>
			<div class="panel-body">
				<form:input path="id" type="hidden"/>
				<div class="form-group">
					<label class="col-md-3 control-label" for="name">营销类型</label>
					<div class="col-md-7">
						<form:input id="name" cssClass="form-control" path="name"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label" for="description">描述</label>
					<div class="col-md-7">
						<form:input id="description" cssClass="form-control" path="description"/>
					</div>
				</div>
			</div> <!-- /Panel body -->
		</form:form>
	</div> <!-- /Panel -->
</div> <!-- ./padding -->
