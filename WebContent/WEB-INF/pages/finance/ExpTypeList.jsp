<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- <div style="display:none;" id="group_helper" data-grouplist='${grouplist}'></div>--%>
<div class="padding-md">
	<div class="panel panel-default">
			<div class="panel-heading" style="font-size:14px; height:52px;">
				支出类型
			    <sec:authorize access="hasAnyRole('FINANCE','SUPER')">
				<a class="btn btn-success btn-sm pull-right" href="#" data-toggle="modal" data-target="#modalExpT1New" style="margin-right:5px; width: 100px;">
					<i class="fa fa-plus"></i> 添加
				</a>
				<button class="btn btn-default btn-sm pull-right" onclick="edit1()" style="margin-right:15px; width: 80px;">
						<i class="fa fa-pencil"></i> 修改 
				</button>
				</sec:authorize>
			</div> <!-- .panel-heading -->
			<div class="panel-body">
			<div id="t1Tree">
				<ul>
				<c:forEach items="${t1s}" var="t1">
					<li data-id="t1-${t1.id}">${t1.name}
						<ul>
						<c:forEach items="${t1.t2list}" var="t2">
							<li data-id="t2-${t2.id}">${t2.name}		
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


<div class="modal fade" id="modalExpT1New">
<div class="modal-dialog modal-md">
<form action="<c:url value="/finance/exptype/input"/>" class="form-horizontal" method="post">
<div class="modal-content">
	<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
		<h5>添加支出类别</h5>
	</div>
	<div class="modal-body">
		<div class="form-group" style="padding-top: 20px;">
			<label class="col-md-3 control-label" for="group">支出类别</label>
			<div class="col-md-7">
				<input id="group" name="t1Names" class="form-control" />
			</div>
		</div>
		<div class="form-group">
			<label class="col-md-3 control-label" for="t2">种类</label>
			<div class="col-md-7">
				<input id="t2" name="t2Name" class="form-control" required="required" />
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

</div> <!-- ./padding -->


<script src="${pageContext.request.contextPath}/static/magicsuggest/magicsuggest-min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/magicsuggest/magicsuggest-min.css">

<script type="text/javascript">
 var edit1 = function() {
	 var selectedNode = $("#t1Tree").jstree("get_selected", true);
	 if(selectedNode){
		 var tid = selectedNode[0].data.id;
		 $('<form action="${pageContext.request.contextPath}/finance/exptype/edit" method="POST"><input type="hidden" name="tid" value="'+tid+'"></form>')
		 		.appendTo($(document.body))
		 		.submit();
	 }
 }

 $(document).ready(function() {
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
	$('#t1Tree').jstree(
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
	$("#modalExpT1New").on("shown.bs.modal", function(evt){
		$('#group').magicSuggest({
  			data: ${t1NameList} 
  			, noSuggestionText: "新类别"
  			, placeholder: "选择现有类别，或输入新类别"
			, maxSelection: 1
			, maxSelectionRenderer: function(){return "OK";}
  			, highlight: false
		});
	});
} );
</script>