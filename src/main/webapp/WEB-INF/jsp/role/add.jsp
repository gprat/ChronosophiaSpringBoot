<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajouter une position</title>
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
	<link href="${styleCss}" rel="stylesheet" /> 
<link href="${normalizeCss}" rel="stylesheet">
</head>
<body>
<div class="container">
	<div class="col-sm-offset-1 col-sm-9">
 		<h1>Ajouter une position</h1>
 	</div>
 	<spring:url value="/role/save" var="roleActionUrl" />
	<form:form method="post" modelAttribute="role" class="form-horizontal" action="${roleActionUrl}">
	<div class="form-group">
		<label path="name" class="control-label col-md-3 col-sm-4">Nom de la position : </label>
		<div class="col-sm-7">
		<form:input class="form-control" path="name"/>
		</div>
	</div>
	<div class="form-group">
		<div class="col-sm-offset-4 col-sm-8">
			<input type="submit" value="Sauver" />
			<input type="reset" value="Reinitialiser" />
		</div>
	</div>
	</form:form>
</div>
</body>
</html>