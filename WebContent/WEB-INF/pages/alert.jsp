<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
