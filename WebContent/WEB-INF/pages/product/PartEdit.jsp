<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="padding-md">
	<form:form modelAttribute="part" cssClass="form-horizontal" action="${pageContext.request.contextPath}/product/part/update" method="post" acceptCharset='utf-8'>
	<div class="panel panel-default">
		<div class="panel-heading" style="font-size:14px; height:52px;">
			<span><a href="<c:url value="/product/part/list"/>" class='glink'>产品部件</a> / 修改</span>
			<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
		</div>
		<div class="panel-body">
			<form:hidden path="id"/>
			<div class="form-group">
					<label class="col-md-2 control-label" for="name">型号名</label>
					<div class="col-md-9">
						<form:input id="name" cssClass="form-control" path="name"/>
					</div>
				</div>
			<div class="form-group">
					<label class="col-md-2 control-label" for="description">描述</label>
					<div class="col-md-9">
						<form:input id="description" cssClass="form-control" path="description"/>
					</div>
				</div>
			<!-- 产品系列与价格 -->
			<div class="form-group">
					<label class="col-md-2 control-label" for="c2">功能类型</label>
					<div class="col-md-4">
						<form:select id="c2" cssClass="chosen-select" path="c2.id" data-placeholder="请选择">
							<form:option value="" label=""/>
							<c:forEach var="optGrp" items="${c1list}" varStatus="optGrpIndex">
								<optgroup label="${optGrp.name}">
									<form:options items="${optGrp.c2list}" itemLabel="name" itemValue="id"/>
								</optgroup>
							</c:forEach>
						</form:select>
					</div>
					<label class="col-md-1 control-label" for="price">列表价</label>
					<div class="col-md-4">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<form:input type="number" min="0" step="0.01" id="price" cssClass="form-control" path="listprice"/>
						</div>
					</div>
				</div>
			<!-- 上线/下线时间 -->
			<div class="form-group">
					<label for="online_date" class="col-md-2 control-label">上线日期</label>
					<div class="col-md-4">
						<div class="input-group">
						<form:input id="online_date" cssClass="form-control datepicker" path="onlineDate" style="padding-left: 15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label" for="offline_date">下线日期</label>
					<div class="col-md-4">
						<div class="input-group">
						<form:input id="offline_date" cssClass="form-control datepicker" path="offlineDate" style="padding-left: 15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>
		</div> <!-- /Panel body -->
	</div> <!-- /Panel -->
	</form:form>
	
	<!-- Error Alerts  -->	
	<%@ include file = "/WEB-INF/pages/alert.jsp" %>
</div> <!-- ./padding -->

<script type="text/javascript">
$().ready(
	function(){
		$('.chosen-select').chosen();
		$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
		if (document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
	});
</script>
