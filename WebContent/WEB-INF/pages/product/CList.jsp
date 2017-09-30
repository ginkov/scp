<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div style="display:none;" id="group_helper" data-grouplist='${grouplist}'></div>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading" style="font-size:14px; height:52px;">
				产品功能类型
			    <sec:authorize access="hasAnyRole('PRODUCT','SUPER')">
				<a class="btn btn-success btn-sm pull-right" href="#" data-toggle="modal" data-target="#modalProdC1New" style="margin-right:5px; width: 100px;">
					<i class="fa fa-plus"></i> 添加
				</a>
				<button class="btn btn-default btn-sm pull-right" onclick="edit1()" style="margin-right:15px; width: 80px;">
						<i class="fa fa-pencil"></i> 修改 
				</button>
				</sec:authorize>
			</div> <!-- .panel-heading -->
			<div class="panel-body">
			<div id="grpTree">
				<ul>
				<c:forEach items="${grps}" var="grp">
					<li data-id="grp-${grp.id}">${grp.name}
						<ul>
						<c:forEach items="${grp.c2list}" var="subgrp">
							<li data-id="subgrp-${subgrp.id}">${subgrp.name}		
						</c:forEach>
						</ul>
					</li>
				</c:forEach>
				</ul>
			</div>
			</div> <!-- Panel body -->
			<div class="panel-footer">
			</div>
		</div> <!-- /Panel -->


<div class="modal fade" id="modalProdC1New">
<div class="modal-dialog modal-md">
<form action="<c:url value="/product/category/input"/>" class="form-horizontal" method="post">
<div class="modal-content">
	<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
		<h5>添加产品功能类型</h5>
	</div>
	<div class="modal-body">
		<div class="form-group" style="padding-top: 20px;">
			<label class="col-md-3 control-label" for="group">产品功能类型</label>
			<div class="col-md-7">
				<input id="group" name="c1Names" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-3 control-label" for="subgrp">功能子类</label>
			<div class="col-md-7">
				<input id="subgrp" name="c2Name" class="form-control" required="required" />
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<input type="submit" class="btn btn-md btn-success" value="确定" />
	</div>
</div> <!-- /.modal-content -->
</form>
</div>
</div> 

<!-- error message modal -->
<%@ include file = "/WEB-INF/pages/alert.jsp" %>
<%-- 		
<c:if test="${not empty err}">
<div class="modal" id="modalAlert">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-body" style="text-align:center">
				<h5 class="text-danger">${err}</h5>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-sm btn-default" data-dismiss="modal">确定</button>
			</div>
		</div>
	</div>
</div> <!-- ./modal -->
</c:if>
--%>

</div> <!-- ./padding -->


<script src="${pageContext.request.contextPath}/static/magicsuggest/magicsuggest-min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/magicsuggest/magicsuggest-min.css">

<script type="text/javascript">
 var edit1 = function() {
	 var selectedNode = $("#grpTree").jstree("get_selected", true);
	 if(selectedNode){
		 console.log(selectedNode[0].data.id); 
		 var gid = selectedNode[0].data.id;
		 $('<form action="${pageContext.request.contextPath}/product/category/edit" method="POST"><input type="hidden" name="gid" value="'+gid+'"></form>')
		 		.appendTo($(document.body))
		 		.submit();
	 }
 }

 $(document).ready(function() {
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
	$('#grpTree').jstree(
   		{
//   			'plugins': ['wholerow'],
			'core': {
				'multiple': false
			    , 'themes': {
			        'name': 'proton',
			        'responsive': true
			    }
			}
		}   
	);
	$("#modalProdC1New").on("shown.bs.modal", function(evt){
		$('#group').magicSuggest({
  			data: $("#group_helper").data("grouplist") 
  			, noSuggestionText: "新功能类型"
  			, placeholder: "选择现有类型，或输入新类型"
			, maxSelection: 1
			, maxSelectionRenderer: function(){return "OK";}
  			, highlight: false
		});
	});
} );
</script>