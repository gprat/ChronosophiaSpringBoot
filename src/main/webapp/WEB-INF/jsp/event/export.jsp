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
	<h3>Evenements</h3>
	<spring:url value="/event/download" var="downloaddUrl" />
	<form:form method="post" action="${downloaddUrl}" style="display: inline;">
	<TABLE class="table table-bordered">
		<tr>
			<th>Nom</th>
			<th>Date</th>
			<th>Ville</th>
			<th>Evenements à exporter</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${events}" var="event">
			<tr>
				<td><c:out value="${event.name}"></c:out></td>
				<td><c:if test="${event.date!=null}"> <c:out value="${event.date.toString()}"></c:out> </c:if></td>
				<td><c:if test="${event.city!=null}"> <c:out value="${event.city.toString()}"></c:out> </c:if></td>
				<td><input type="checkbox" name="selectedIds" value="${event.idEvent}">
				 </td>
			</tr>
		</c:forEach>
		</TABLE> 
		<input type="submit" value="Export" class="btn" />
	</form:form>
</div>
</body>
</html>