<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Liste des utilisateurs</title>

    <link href="<c:url value='/resources/css/app.css' />" rel="stylesheet"></link>
    
  <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>  
</head>

<body>
	<div class="generic-container">
		
		<div class="panel panel-default">
			  <!-- Default panel contents -->
		  	<div class="panel-heading"><span class="lead">Liste des utilisateurs </span></div>
			<table class="table table-hover">
	    		<thead>
		      		<tr>
				        <th>Prénom</th>
				        <th>Nom</th>
				        <th>Email</th>
				        <th>Nom d'utilisateur</th>
				        <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
				        	<th width="100"></th>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
				        	<th width="100"></th>
				        </sec:authorize>
				        
					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.email}</td>
						<td>${user.username}</td>
					    <sec:authorize access="hasRole('ADMIN') or hasRole('DBA')">
					    	<spring:url value="/edit-user-${user.username}" var="newuserUrl" />
							
							<td><form:form method="get" action="${newuserUrl}" style="display: inline;"> 
								<input type="submit" value="Modifier" class="btn" />
							</form:form></td>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
				        	<spring:url value="/delete-user-${user.username}" var="newuserUrl" />
							
							<td><form:form method="get" action="${newuserUrl}" style="display: inline;"> 
								<input type="submit" value="Supprimer" class="btn" />
							</form:form></td>
        				</sec:authorize>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>
		</div>
		<sec:authorize access="hasRole('ADMIN')">
			<spring:url value="/newuser" var="newuserUrl" />
			<form:form method="get" action="${newuserUrl}" style="display: inline;"> 
				<input type="submit" value="Ajouter un nouvel utilisateur" class="btn" />
			</form:form>
	 	</sec:authorize>
	 	<spring:url value="/create-user" var="createuserUrl" />
		<form:form method="get" action="${createuserUrl}" style="display: inline;"> 
			<input type="submit" value="Ajouter un nouvel utilisateur" class="btn" />
		</form:form>
   	</div>
</body>
</html>