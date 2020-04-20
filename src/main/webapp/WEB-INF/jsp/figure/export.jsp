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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Liste des Chronologies</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
	<h3>Personnages</h3>
	<spring:url value="/figure/download" var="downloaddUrl" />
	<form:form method="post" action="${downloaddUrl}" style="display: inline;">
	<TABLE class="table table-bordered">
		<TR>
			<TH>Prénom</TH>
			<TH>Nom</TH>
			<TH>Date de Naissance</TH>
			<TH>Date de Mort</TH>
			<TH>Personnages à Exporter</TH>
		</TR>
		<c:forEach items="${figures}" var="figure">
			<TR>
				<TD><c:out value="${figure.firstName}"></c:out></TD>
				<TD><c:out value="${figure.lastName}"></c:out></TD>
				<TD><c:if test="${figure.birthDate!=null}"> <c:out value="${figure.birthDate.toString()}"></c:out> </c:if></TD>
				<TD><c:if test="${figure.deathDate!=null}"> <c:out value="${figure.deathDate.toString()}"></c:out> </c:if></TD>
					<td><input type="checkbox" name="selectedIds" value="${figure.idFigure}"></td>
				</TR>
			</c:forEach>
		</TABLE> 
		<input type="submit" value="Export" class="btn" />
	</form:form>
</div>
</body>
</html>