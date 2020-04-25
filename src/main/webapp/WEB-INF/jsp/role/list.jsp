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
<meta name="viewport" content="initial-scale=1">
<title>Liste des positions</title>

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
 	
	<h3>Positions</h3>
	
	<TABLE class="table table-bordered">
		<TR>
			<TH>Nom</TH>
			<th>Opérations</th>
		</TR>
		<c:forEach items="${roles}" var="role">
			<TR>
				<TD><c:out value="${role.name}"></c:out></TD>
				<td>
					<spring:url value="/role/${role.idRole}/delete" var="deleteUrl" />
					<form:form method="post" action="${deleteUrl}" style="display: inline;"> <input type="submit" value="Supprimer" class="btn" /> </form:form>
				</td>
			</TR>
		</c:forEach>
	</TABLE>
	<spring:url value="/role/add" var="addUrl" />
	<form:form method="post" action="${addUrl}" style="display: inline;"> 
		<input type="submit" value="Créer une position" class="btn" />
	</form:form>
</div>
</body>
</html>