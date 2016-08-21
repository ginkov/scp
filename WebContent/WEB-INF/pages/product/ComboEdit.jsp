<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div style="display:none;" id="part_helper" data-partsummary='${partSummary}'> </div>
<div class="padding-md">
	<c:url value="/product/combo/edit/${combo.id}" var="editUrl" />
	<form:form modelAttribute="combo" name="comboForm" cssClass="form-horizontal" action="${pageContext.request.contextPath}/product/combo/update" method="post" acceptCharset='utf-8'>
	<div class="panel panel-default">
			<div class="panel-heading" style="font-size:14px; height:52px;">
				<span><a href="<c:url value="/product/combo/list"/>" class='glink'>产品套装</a> / 修改</span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-md-2 control-label" for="name">名称</label>
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
							<form:options items="${c2list}" itemLabel="name" itemValue="id"/>
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
						<form:input id="online_date" cssClass="form-control datepicker" path="onlineDate" style="padding-left:15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label" for="offline_date">下线日期</label>
					<div class="col-md-4">
						<div class="input-group">
						<form:input id="offline_date" cssClass="form-control datepicker" path="offlineDate" style="padding-left:15px;"/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
				</div>
			</div> <!-- /Panel body -->
			<div class="panel-footer">
				<div style="padding-bottom: 15px; margin-bottom: 25px; border-bottom: 1px #eee solid;">
					部件列表
					<input style="width:100px;" class="btn btn-default btn-xs pull-right" type="button" value="添加/更新"
						onclick="document.comboForm.action='${editUrl}'; document.comboForm.submit();"/>
				</div>
				<div class="form-group">
					<div class="col-md-3 col-md-offset-2" style="text-align:center;">部件</div>
					<div class="col-md-1" style="padding-left: 27px;">类型</div>
					<div class="col-md-2" style="padding-left: 27px;">子类</div>
					<div class="col-md-2" style="padding-left: 27px;">列表价 (￥)</div>
					<div class="col-md-2" style="padding-left: 27px;">数量</div>
				</div>
  				<c:forEach var="item" items="${combo.partslist}" varStatus="vs">
  					<form:hidden path="partslist[${vs.index}].id"/>
		 			<div class="form-group">
					<label class="col-md-2 control-label">${vs.index + 1}</label>
					<div class="col-md-3">
						<form:select cssClass="form-control chosen-select" path="partslist[${vs.index}].part.id" data-placeholder="请选择"
							data-id="${vs.index}" id="part${vs.index}" onchange="partChanged(this.getAttribute('data-id'))">
							<form:option value="" label=""/>
							<c:forEach var="optGrp" items="${partOpts}" varStatus="optGrpIndex">
								<optgroup label="${optGrp.key}">
									<form:options items="${optGrp.value}" itemLabel="name" itemValue="id"/>
								</optgroup>
							</c:forEach>
						</form:select>
					</div>
					<div class="col-md-1">
						<form:input id="c1${vs.index}" cssClass="form-control" path="partslist[${vs.index}].part.c2.c1.name" readonly="true"/>
					</div>
					<div class="col-md-2">
						<form:input id="c2${vs.index}" cssClass="form-control" path="partslist[${vs.index}].part.c2.name" readonly="true"/>
					</div>
					<div class="col-md-2">
						<fmt:formatNumber value="${item.part.listprice}" pattern=".00" var="itemListPrice"/>
						<form:input path="partslist[${vs.index}].part.listprice"  cssClass="form-control text-right" 
							data-id="${vs.index}" id="price${vs.index}" value="${itemListPrice}" readonly="true"/>
					</div>
					<div class="col-md-1">
						<form:input path="partslist[${vs.index}].quantity" type="number" cssClass="form-control" min="0"
							data-id="${vs.index}" id="qty${vs.index}" />
					</div>
					</div><!-- form-group  -->
				</c:forEach>
			</div><!-- panel-footer -->
		</div> <!-- /Panel -->
	</form:form>
</div> <!-- ./padding -->

<script type="text/javascript">
var ps = $('#part_helper').data('partsummary');
function partChanged(id){
	var partId = $('#part'+id).val();
	$('#c1'+id).val(ps[partId]['c1']);
	$('#c2'+id).val(ps[partId]['c2']);
	$('#price'+id).val(Number(ps[partId]['price']).toFixed(2));
	$('#qty'+id).val(1);
}
$().ready(
	function(){
		$('.chosen-select').chosen();
		$('.datepicker').datepicker({format: 'yyyy-mm-dd'});
	});
</script>
