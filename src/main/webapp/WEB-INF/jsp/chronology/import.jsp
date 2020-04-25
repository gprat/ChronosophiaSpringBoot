<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
<spring:url value="/resources/css/style.css" var="styleCss" />
<spring:url value="/resources/css/normalize.css" var="normalizeCss" />
<spring:url value="/resources/js/jquery.flexdatalist.js"
	var="flexdatalistJS" />
<spring:url value="/resources/css/jquery.flexdatalist.min.css"
	var="flexdatalistCss" />
<link href="${flexdatalistCss}" rel="stylesheet" type="text/css">
<script src="${flexdatalistJS}"></script>
<link href="${normalizeCss}" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Chronologies à importer</title>
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
	<h3>Chronologies à importer</h3>
	
	<spring:url value="/chronology/upload" var="uploadUrl" />
	<form:form method="post" action="${uploadUrl}">
	<table class="table table-bordered">
		<thead>
		<tr>
			<th>Nom</th>
			<th>Description</th>
			<th>Utilisateur</th>
			<th>Chronologies à importer</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${contents}" var="content">
			<tr>
				<td><c:out value="${content.filename}"></c:out></td>
				<td><c:out value="${content.description}"></c:out></td>
				<td><c:out value="${content.username}"></c:out></td>
				<td><input type="checkbox" name="selectedFilenames" value="${content.filename}">
			</tr>
		</c:forEach>
		</tbody>
	</table>
    <input type="submit" value="Submit" class="btn" />
</form:form>
    </div>
</body>
</html>