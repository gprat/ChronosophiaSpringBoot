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
<title>Liste des évènements</title>
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
	<h3>Evènements</h3>
	
	<spring:url value="/event/list" var="selectUrl" />
	
	<form:form method="post" modelAttribute="selectEventForm" action="${selectUrl}" class="form-inline" >
		<div class="form-group">
		<form:label path="categories" >Catégorie : </form:label>
		<form:input path="categories" class="categories form-control"  data-min-length='0' multiple='multiple'/>
		</div>     
		&nbsp;&nbsp;&nbsp;&nbsp; 
		
		<input type="submit" value="Filtrer" class="btn" />
	</form:form>
	<br />

	<table class="table table-bordered">
		<thead>
		<tr>
			<th>Nom</th>
			<th>Date</th>
			<th>Ville</th>
			<th>Opérations</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${events}" var="event">
			<tr>
				<td><c:out value="${event.name}"></c:out></td>
				<td><c:if test="${event.date!=null}"> <c:out value="${event.date.toString()}"></c:out> </c:if></td>
				<td><c:if test="${event.city!=null}"> <c:out value="${event.city.toString()}"></c:out> </c:if></td>
				<td>
					<spring:url value="/event/${event.idEvent}" var="eventUrl" />
				  	<form:form method="post" action="${eventUrl}" style="display: inline;"> 
				  	<!--<input type="submit" value="Afficher" name="view" class="btn" />  -->
				  	<input type="submit" value="Mettre à jour" name="update" class="btn" /> 
				  	<input type="submit" value="Supprimer" name="delete" class="btn" />
				  	</form:form>
				 </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<spring:url value="/event/add" var="addUrl" />
	<form:form method="post" action="${addUrl}" style="display: inline;"> 
		<input type="submit" value="Créer un évènement" class="btn" />
	</form:form>
<script type="text/javascript">
    var categoriesSource = ${categoriesJSON};
    $('.categories').flexdatalist({
    	minLength: 0,
    	searchIn: ['name'],
    	selectionRequired: true,
    	valueProperty: 'idCategory',
        visibleProperties: ["name"],
        textProperty: '{name}',
        data: categoriesSource
   });
    
  
    </script>
    </div>
</body>
</html>