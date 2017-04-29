<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-xs">
	<div class="panel panel-default">
			<div class="panel-heading" style="height: 56px;">
				<span style="font-size: 18px;">支出列表</span> 
				<a class="btn btn-success btn-md pull-right" href="<c:url value="/finance/expense/input?mobile"/>" style="margin-right:5px; width: 100px;">
						<i class="fa fa-plus"></i> 新支出
				</a>
			</div>
			<div class="panel-body">
			<div style="border-bottom: 0.5px solid #ddd; padding-bottom:5px; margin-bottom: 15px;">
				<span style="font-size: 14px;">本期总支出: <fmt:formatNumber value="${totalExp}" type="currency"/></span>

			<!--  数据分析的部分，以后再考虑
				<a href="#" style="font-size: 14px; margin-left: 15px;" class="glink" onclick="showBI()"> 数据分析 </a>
				<span id="spStatus" style="font-size: 14px; font-weight: bold; color: #aaa; margin-left:5px;">收起</span>
			</div>
			<div style="height: 300px;" id="divBI">
				<div class="row" style="padding: 15px;">
					<button class="col-md-1 btn btn-default btn-xs">类别</button>
				</div>
				-->

			</div>
			<div style="margin: 0;">
				<table class="table table-striped table-hover" id="expense">
					<thead>
						<tr>
							<th style="text-align:right;">日期</th>
							<th style="text-align:right;">种类</th>
							<th style="text-align:right;">商品名</th>
							<th style="text-align:right;">金额</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${ers}" var="er">
						<tr>
							<td style="text-align:right;">
								<a href="<c:url value="/finance/expense/detail/${er.id}?mobile"/>" class="glink">
									<fmt:formatDate value="${er.date}" pattern='yyyy-MM-dd'/>
								</a>
							<td style="text-align:right;">${er.t2.name}
							<td style="text-align:right;">${er.expName}
							<td style="text-align:right;"><fmt:formatNumber type="currency" value="${er.amount}"/>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			</div> <!-- /Panel body -->
		</div> <!-- /Panel -->
</div> <!-- ./padding -->

<!-- Error Alerts  -->	
<%@ include file = "/WEB-INF/pages/alert.jsp" %>

<script type="text/javascript">
var showBI = function(){
	var status = $('#spStatus').text();
	console.log(status);
	if(status =='展开'){
		$('#divBI').show();	
		$('#spStatus').text('收起');
	}
	else {
		$('#divBI').hide();
		$('#spStatus').text('展开');
	}
};

$(document).ready(function() {
	if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
    $('#expense').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, "lengthChange": false
    	, "pageLength": 10 
    	, "order": [[ 0, "desc" ]]
    	, orderClasses : false
    	, columnDefs: [{targets:[2], orderable: false }]
	});
	showBI();
 });
</script>