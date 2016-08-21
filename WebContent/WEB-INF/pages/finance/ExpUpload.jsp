<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="zh_CN" scope="session"/>
<div class="padding-md">
	<div class="panel panel-default">
		<div class="panel-heading">
			<a href="<c:url value="/finance/expense/list"/>" style="font-size: 18px;" class="glink">支出列表 /</a> 
			<span style="font-size:14px; padding-left:10px;"> 通过 <a href="<c:url value="/static/help/expense-upload-format.html"/>" class="text-success" target='_blank'>EXCEL</a>文件批量上传</span>
		</div>
        <div class="panel-body">
			<form class="form-inline" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/finance/expense_upload/do">

				<div class="form-group">
					<div class="upload-file" style="width: 330px; margin-left:15px;">
						<input type="file" id="inputUpload" class="upload-input" name="upload" onchange="change(this)"/> <label data-title="选择文件" for="inputUpload">
							<span data-title=""></span>
						</label>
					</div>
				</div>
				<div class="form-group">
					<input type="submit" class="btn btn-sm btn-success" value ="上传" style="width:90px; margin-top:7px; margin-left:40px;" id="btnLinkUpload"/>
					<button class="btn btn-sm btn-default" type="button" style="width:90px; margin-top:7px; margin-left:20px;" onclick="cls()">清除</button>
				</div>
			</form>
			<p style="margin: 30px 0px 0px 0px; padding:10px 0px; font-size:13px; border-top: 0.5px dashed #ccc; color: #aaa;">注：如原数据与批量导入数据冲突，以批量导入数据为准。</p>
        </div>

		<div class="panel-footer">
			<p id="pUploadMsg" style="margin-top: 5px;"></p>
		</div>
		<div class="panel-body form-horizontal"></div>
		<!-- /Panel body -->
	</div> <!-- /Panel -->
</div> <!-- ./padding -->
<script>
function change(input) {
	var filename = $(input).val().split('\\').pop();
    $(input).parent().find('span').attr('data-title',filename);
    $(input).parent().find('label').addClass('selected');
}
function cls() {
	$('#inputUpload').parent().find('span').attr('data-title','');
	$('#inputUpload').parent().find('label').removeClass('selected');
	return false;
}
</script>