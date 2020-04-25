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
<title>Liste des Villes</title>

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
	<h3>Villes</h3>
	<TABLE class="table table-bordered">
		<TR>
			<TH>Nom</TH>
			<TH>Pays</TH>
			<th>Opérations</th>
		</TR>
		<c:forEach items="${cities}" var="city">
			<TR>
				<TD><c:out value="${city.name }"></c:out></TD>
				<TD><c:out value="${city.country.name }"></c:out></TD>
				<TD>
					<spring:url value="/city/${city.idCity}" var="cityUrl" />
				  	<form:form method="post" action="${cityUrl}" style="display: inline;"> 
				  	<input type="submit" value="Afficher" name="view" class="btn" />
				  	<input type="submit" value="Mettre à jour" name="update" class="btn" /> 
				  	<input type="submit" value="Supprimer" name="delete" class="btn" />
				  	</form:form>
				 </TD>
			</TR>
		</c:forEach>
	</TABLE>
	<spring:url value="/city/add" var="addUrl" />
	<form:form method="post" action="${addUrl}" style="display: inline;"> 
		<input type="submit" value="Créer une ville" class="btn" />
	</form:form>
	<br>
	<spring:url value="/city/view" var="viewUrl" />
	<form:form method="post"  class="form-inline"  action="${viewUrl}" modelAttribute="eventsSpan">
  <div class="form-group">
    <label>De :</label>
    <form:input type="text" class="form-control" path="startYear"/>
  </div>
  <div class="form-group">
    <label>à :</label>
    <form:input type="text" class="form-control" path="endYear"/>
  </div>
  <input type="submit" value="Voir les villes" class="btn" />
</form:form>
</div>
</body>
</html>
