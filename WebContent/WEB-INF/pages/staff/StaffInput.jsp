<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="padding-md">
	<div class="panel panel-default">
		<form:form modelAttribute="staffForm" cssClass="form-horizontal" action="save" method="post" acceptCharset='utf-8'>
			<div class="panel-heading" style="font-size:14px; height:52px;">
				<a href="<c:url value="/staff/list"/>" class="glink">员工列表</a> / 添加 <span id="pPhynetMsg"></span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="添加"/>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label class="col-md-3 control-label" for="name">账号</label>
					<div class="col-md-7">
						<form:input id="name" cssClass="form-control" path="name"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label" for="description">姓名或昵称</label>
					<div class="col-md-7">
						<form:input id="description" cssClass="form-control" path="description"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label" for="pass">初始密码</label>
					<div class="col-md-7">
						<form:input id="pass" cssClass="form-control" path="pass"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-2 col-md-offset-3">
					<label class="label-checkbox">
						<form:checkbox path="isUser" cssClass="checkbox" label="业务(USER)"/> <span class="custom-checkbox"></span>
					</label>
					</div>
					<div class="col-md-2">
					<label class="label-checkbox">
						<form:checkbox path="isAdmin" cssClass="checkbox" label="管理员(ADMIN)"/> <span class="custom-checkbox"></span>
					</label>
					</div>
				</div>
			</div> <!-- /Panel body -->
		</form:form>
	</div> <!-- /Panel -->
</div> <!-- ./padding -->
