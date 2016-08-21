<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="padding-md">
	<div class="panel panel-default">
			<form:form modelAttribute="staffChangePasswordForm" cssClass="form-horizontal" action="${pageContext.request.contextPath}/staff/passwd/submit" method="post" acceptCharset='utf-8'>
			<div class="panel-heading" style="font-size:14px; height:52px;">
				修改密码 / ${staffName} <span class="text-danger">${error}</span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="修改"/>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label class="col-md-3 control-label" for="oldpass">旧密码:</label>
					<div class="col-md-7">
						<form:password id="oldpass" cssClass="form-control" path="oldPass" required='true'/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label" for="description">姓名或昵称:</label>
					<div class="col-md-7">
						<form:input id="description" cssClass="form-control" path="description"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label" for="newpass1">新密码:</label>
					<div class="col-md-7">
						<form:password id="newpass1" cssClass="form-control" path="newPass1"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label" for="newpass2">重复新密码:</label>
					<div class="col-md-7">
						<form:password id="newpass2" cssClass="form-control" path="newPass2"/>
					</div>
				</div>
			</div> <!-- /Panel body -->
			</form:form>
		</div> <!-- /Panel -->
</div> <!-- ./padding -->