<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
					<label class="col-md-3 control-label">权限组</label>
					<c:set var="len" value="${fn:length(roles)}"/>
					<c:set var="res" value="${len%3}"/>
					<c:set var="cut" value="${len - res}"/>
					<div class="col-md-7" style="padding-top: 2px;">
						<c:forEach var="i" begin="0" end="${cut-1}" step="3">
							<div class="row" style="margin-bottom: 5px;">
							<label class="label-checkbox col-md-4"> 
								<form:checkbox
									path="roles" cssClass="checkbox" value="${roles[i].role}" /> 
								<span class="custom-checkbox"></span> 
								${roles[i].description}
							</label>
							<label class="label-checkbox col-md-4">
								<form:checkbox 
									path="roles" cssClass="checkbox" value="${roles[i+1].role}" />
								<span class="custom-checkbox"></span>
								${roles[i+1].description}
							</label>
							<label class="label-checkbox col-md-4"> 
								<form:checkbox
									path="roles" cssClass="checkbox" value="${roles[i+2].role}" />
									<span class="custom-checkbox"></span> 
									${roles[i+2].description}
							</label>
							</div>
						</c:forEach>
						<div class="row" style="margin-bottom:5px;">
						<c:forEach var="i" begin="${cut}" end="${len-1}">
							<label class="label-checkbox col-md-4"> 
								<form:checkbox
									path="roles" cssClass="checkbox" value="${roles[i].role}" /> 
								<span class="custom-checkbox"></span> 
								${roles[i].description}
							</label>
						</c:forEach>
						</div>
					</div>
				</div>
			</div> <!-- /Panel body -->
		</form:form>
	</div> <!-- /Panel -->
</div> <!-- ./padding -->
