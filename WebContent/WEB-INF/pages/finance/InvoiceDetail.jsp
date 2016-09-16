<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading" style="font-size:14px; height:52px;">
			<span><a href="<c:url value="/finance/invoice/list"/>" class='glink'>发票</a> / ${invoice.sn} </span>
			<sec:authorize access="hasAnyRole('ADMIN','SUPER')">
				<a id="btnEdit" class="btn btn-default btn-sm pull-right" href="<c:url value="/finance/invoice/edit/${invoice.id}"/>" style="width: 100px;"><i class="fa fa-pencil"></i> 修改</a>
				<button id="btnDel" style="width:60px; margin-right: 20px;" class="btn btn-danger btn-sm pull-right" data-toggle="modal" data-target="#modalConfirmDel"><i class="fa fa-trash"></i> 删除</button>
			</sec:authorize>
		</div>
		<div class="panel-body form-horizontal">
			<div style="margin:15px 0px; padding:25px 0px 15px 0px; border: dotted 1px #ccc; background-color: #ffffe0;">
				<!-- 供应商与发票号 -->
				<div class="form-group">
					<label for="issuer" class="col-md-2 control-label">供应商</label>
					<div class="col-md-5">
						<input id="issuer" class="form-control" value="${invoice.issuer}" readonly/>
					</div>
					<label class="col-md-1 control-label" for="sn">发票号</label>
					<div class="col-md-3">
						<input id="sn" class="form-control" value="${invoice.sn}" readonly/>
					</div>
				</div>
				<!-- 日期、金额 -->
				<div class="form-group">
					<label class="col-md-2 control-label" for="date">日期</label>
					<div class="col-md-2">
						<div class="input-group">
						<input id="date" class="form-control" value="<fmt:formatDate value="${invoice.date}" pattern="yyyy-MM-dd"/>" style="padding-left: 15px;" readonly/>
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						</div>
					</div>
					<label class="col-md-1 control-label" for="amount">金额</label>
					<div class="col-md-2">
						<div class="input-group">
						<span class="input-group-addon">￥</span>
						<input type="number" min="0" step="0.01" id="amount" class="form-control" value="${invoice.amount}"
							readonly/>
						</div>
					</div>
					<label class="col-md-1 control-label" for="t2">支出类型</label>
					<div class="col-md-3">
						<input class="form-control" value="${invoice.type.description}" readonly/>
					</div>
				</div>
				<!-- 描述 -->
				<div class="form-group">
					<label class="col-md-2 control-label" for="description">内容描述</label>
					<div class="col-md-9">
						<input class="form-control" readonly value=${invoice.description} />
					</div>					
				</div>
				<!-- 类别 -->
				<div class="form-group">
					<label class="col-md-2 control-label">原始发票</label>
					<div class="col-md-2">
						<input class="form-control" value='${invoice.original?"是":"否"}' readonly />
					</div>
					<label class="col-md-1 control-label">已匹配</label>
					<div class="col-md-2">
						<input class="form-control" value='${invoice.used?"是":"否"}' readonly />
					</div>
				</div>
			</div> <!-- /单据格式 -->
		</div> <!-- /Panel body -->
		<c:if test="${invoice.used}">
		<div class="panel-footer">
			<div class="row" style="font-size: 14px; padding:5px 0; margin:5px 10px;"> 发票/支出匹配
				<a id="btnDetach" class="btn btn-default btn-sm pull-right" href="<c:url value="/finance/invoice/detach/${invoice.id}"/>" style="width: 100px;"><i class="fa fa-random"></i> 解除匹配</a>
			</div>
			<div class="row" style="text-align:center; padding:4px 10px 2px 10px; margin: 0 10px; background-color: #eee;
					border-bottom: 0.5px solid #ddd; border-radius: 3px;">
				<div class="col-md-5">发票</div>
				<div class="col-md-2" style="border-left: 1px solid #ccc; border-right: 1px solid #ccc;">匹配</div>
				<div class="col-md-5">支出</div>
			</div>
			<div class="row" style="padding: 5px 0; margin: 0 10px 5px 10px;">
				<div class="col-md-5">
					<c:forEach items="${il}" var="inv">
					<div class="row">
						<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}/finance/invoice/detail/${inv.id}">${inv.sn}</a></div>
						<div class="col-md-2 no-overflow">${inv.type.description}</div>
						<div class="col-md-4 no-overflow">${inv.description}</div>
						<div class="col-md-3" style="text-align:right;"><fmt:formatNumber value="${inv.amount}" type="currency"/></div>
					</div>
					</c:forEach>
				</div>
				<div class="col-md-2" style="text-align:center;"></div>
				<div class="col-md-5">
					<c:forEach items="${el}" var="exp">
					<div class="row">
						<div class="col-md-3" style="text-align:center;"><a class="glink" href="${pageContext.request.contextPath}/finance/expense/detail/${exp.id}">${exp.sn}</a></div>
						<div class="col-md-2 no-overflow">${exp.t2.name}</div>
						<div class="col-md-4 no-overflow">${exp.summary}</div>
						<div class="col-md-3" style="text-align:right;"><fmt:formatNumber value="${exp.amount}" type="currency"/></div>
					</div>
					</c:forEach>
				</div>
			</div>
		</div><!-- panel-footer -->
		</c:if>
	</div> <!-- /Panel -->
</div> <!-- ./padding -->

<div class="modal fade" id="modalConfirmDel">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-body">
				确定要删除吗?
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">取消</button>
        		<a type="button" class="btn btn-sm btn-danger" href="<c:url value="/finance/invoice/del/${invoice.id}" />">删除</a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function() {
    $('#orders').DataTable({
		language: {url: '${pageContext.request.contextPath}/static/Endless-1.5.1/i18n/dataTable-Chinese.json'}
    	, "bJQueryUI": true
    	, "sPaginationType": "full_numbers"
    	, orderClasses : false
    	, columnDefs: [{targets:[6], orderable: false }]
	});
 } );
</script>