<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="padding-md">
	<div class="panel panel-default">
			<form:form commandName="orgType" cssClass="form-horizontal" action="${pageContext.request.contextPath}/customer/orgtype/update" method="post" acceptCharset='utf-8'>
			<div class="panel-heading" style="font-size:14px; height:52px;">
				客户 / <a href="<c:url value="/customer/orgtype/list"/>" class='glink'>组织类型</a> / 修改 <span id="pPhynetMsg"></span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-md-3 control-label" for="name">组织类型</label>
					<div class="col-md-7">
						<form:input id="name" cssClass="form-control" path="name"/>
					</div>
				</div>
			</div> <!-- /Panel body -->
			</form:form>
		</div> <!-- /Panel -->
</div> <!-- ./padding -->
