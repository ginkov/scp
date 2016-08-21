<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	request.setCharacterEncoding("utf-8"); 
	request.setAttribute("pageTitle", "佳耘SCP");
	request.setAttribute("pageContent", "indexContent");	
%>
<jsp:include page="/WEB-INF/pages/mainpage.jsp"/>
<%-- <jsp:include page="/WEB-INF/pages/mainpage.jsp">
	<jsp:param name="title" value="佳耘SCP"/>
	<jsp:param name="content" value="indexContent"/>
</jsp:include>
 --%>