<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<form class="form-horizontal" action="${pageContext.request.contextPath}/product/comboTemplate/update" method="post">
		<div class="panel panel-default">
			<div class="panel-heading" style="height:52px;">
				<span style="font-size:18px;">选择套装模板</span>
				<input class="btn btn-success btn-sm pull-right" style="width: 100px;" type="submit" value="保存"/>
			</div>
			<div class="panel-body">
				<div style="margin:3px 20px; padding: 5px 0; border-bottom: 1px solid #aaa;">
					<label class="label-radio">
						<c:choose>
							<c:when test="${selected == 0 }">
								<input type="radio" name="selected" value="0" checked>	
							</c:when>
							<c:otherwise>
								<input type="radio" name="selected" value="0">
							</c:otherwise>
						</c:choose>
                        <span class="custom-radio"></span>
                        <span>都不选</span>
					</label>
				</div>
				<c:forEach items="${combos}" var="combo" varStatus="vs">
				<div style="margin:0 20px; padding:5px 0; border-bottom: 1px solid #aaa;">
					<label class="label-radio" style="margin: 3px 0;">
						<c:choose>
							<c:when test="${selected == combo.id}">
								<input type="radio" name="selected" value="${combo.id}" checked>	
							</c:when>
							<c:otherwise>
								<input type="radio" name="selected" value="${combo.id}">
							</c:otherwise>
						</c:choose>
                        <span class="custom-radio"></span>
						<span style="padding-top: 2px; vertical-align: middle;">
							${combo.name}
							<a href="#" class="glink" onclick="showhide(${vs.index})"> ...</a>
						</span>
					</label>
					<div style="margin:5px 15px;" id="detail${vs.index}">
					<table class="table table-striped table-hover" >
					<thead>
						<tr>
							<th style="width:150px;">部件名
							<th style="width: 80px;">数量
						</th>
					</thead>
					<tbody>
					<c:forEach items="${combo.partslist}" var="ppi">
					<tr>
						<td>${ppi.part.name}  
						<td>${ppi.quantity}
					</tr>
					</c:forEach>
					</tbody>
					</table>
					</div>
				</div>
				</c:forEach>
			</div> <!-- /Panel body -->
		</div> <!-- /Panel -->
	</form>
		
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

</div> <!-- ./padding -->
<script type="text/javascript">
var len = ${fn:length(combos)};
var hide = [];
for(var i=0; i<len; i++) {
	hide[i] = 1;
}
function showhide(id){
	if(hide[id]){
		$("#detail"+id).show();
		hide[id] = 0;
	}
	else {
		$("#detail"+id).hide();
		hide[id] = 1;
	}
}
function hideAll(){
	for(var i=0; i<len; i++) {
		$("#detail"+i).hide();
		hide[i] = 1;
	}	
}
 $(document).ready(function() {
	hideAll();
    if(document.getElementById("modalAlert")) { $("#modalAlert").modal("show"); }
});
</script>