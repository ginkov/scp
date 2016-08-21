<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="padding-md">
	<div class="panel panel-default">
			<form:form modelAttribute="subgrp" cssClass="form-horizontal" action="${pageContext.request.contextPath}/product/category/l2update" method="post" acceptCharset='utf-8'>
			<div class="panel-heading" style="font-size:14px; height:52px;">
				<a href="<c:url value="/product/category/list"/>" class='glink'>产品功能类型</a> / 子类 / 修改 <span id="pPhynetMsg"></span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
				<a class="btn btn-danger btn-sm pull-right" style="margin-right: 10px; width: 60px;" href="<c:url value="/product/category/l2del/${subgrp.id}"/>"><i class="fa fa-trash"></i> 删除</a>
				<a class="btn btn-default btn-sm pull-right" style="margin-right: 20px; width: 60px;" href="<c:url value="/product/category/list"/>">取消</a>
			</div>
			<div class="panel-body">
				<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-md-3 control-label" for="name">子类名</label>
					<div class="col-md-7">
						<form:input id="name" cssClass="form-control" path="name"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label" for="grp">所属功能类型</label>
					<div class="col-md-7">
						<form:input id="grp" cssClass="form-control" path="c1.name" readonly="true"/>
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
