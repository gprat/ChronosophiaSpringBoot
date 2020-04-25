<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1">
<spring:url value="/resources/css/style.css" var="styleCss" />
<spring:url value="/resources/css/normalize.css" var="normalizeCss" />
<spring:url value="/resources/js/jquery.flexdatalist.js" var="flexdatalistJS" />
<spring:url value="/resources/css/jquery.flexdatalist.min.css" var="flexdatalistCss" />
<link href="${flexdatalistCss}" rel="stylesheet" type="text/css">
<script src="${flexdatalistJS}"></script>
	<link href="${styleCss}" rel="stylesheet" />
<style type="text/css">
.nopadding {
	padding: 0 !important;
	margin: 0 !important;
}
</style>
<title>Ajouter une personnalité</title>
</head>
<body>
<%@ include file="../navbar.jsp" %>
	<div class="container">
		<div class="col-sm-offset-1 col-sm-9">
			<h1>Ajouter une personnalité</h1>
		</div>
		<spring:url value="/figure/save" var="figureActionUrl" />
		<form:form method="post" modelAttribute="figureForm"
			action="${figureActionUrl}" class="form-horizontal">
			<form:hidden path="id" />
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Nom de la personnalité
					: </label>
				<div class="col-sm-7 col-xs-8">
					<form:input path="lastName" class="form-control"
						placeholder="Nom" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Prénom de la
					personnalité : </label>
				<div class="col-sm-7 col-xs-8">
					<form:input path="firstName" class="form-control"
						placeholder="Prénom" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Date de naissance : </label>
				<div class="col-md-3 col-sm-4 col-xs-8">
					<div class="row nopadding">
						<div class="col-lg-5 col-sm-6  nopadding">
							<div class="col-sm-6 col-xs-3 nopadding">
								<form:input path="dayOfBirth" class="form-control"
									placeholder="jj" maxlength="2"/>
							</div>
							<div class="col-sm-6 col-xs-3 nopadding">
								<form:input path="monthOfBirth" class="form-control"
									placeholder="mm" maxlength="2"/>
							</div>
						</div>
						<div class="col-sm-3 col-xs-3 nopadding">
							<form:input path="yearOfBirth" class="form-control"
								placeholder="aaaa" maxlength="5"/>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Date de mort : </label>
				<div class="col-md-3 col-sm-4 col-xs-8">
					<div class="row nopadding">
						<div class="col-lg-5 col-sm-6 nopadding">
							<div class="col-sm-6 col-xs-3 nopadding">
								<form:input path="dayOfDeath" class="form-control"
									placeholder="jj" maxlength="2"/>
							</div>
							<div class="col-sm-6 col-xs-3 nopadding">
								<form:input path="monthOfDeath" class="form-control"
									placeholder="mm" maxlength="2"/>
							</div>
						</div>
						<div class="col-sm-3  col-xs-3 nopadding">
							<form:input path="yearOfDeath" class="form-control"
								placeholder="aaaa" maxlength="5"/>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Evènements : </label>
				<div class="col-sm-7 col-xs-8">
					<form:input path="events" class="events form-control" placeholder="Evènements" data-min-length='1' multiple='multiple'/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Catégories : </label>
				<div class="col-sm-7 col-xs-8">
					<form:input path="categories" class="categories form-control" placeholder="Catégories" data-min-length='0' multiple='multiple'/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Rôles : </label>
				<div class="col-sm-7 col-xs-8">
					<form:input path="roles" class="roles form-control" placeholder="Rôles" data-min-length='0' multiple='multiple'/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Biographie: </label>
				<div class="col-sm-7 col-xs-8">
					<form:textarea path="biography" class="form-control" placeholder="Biographie" rows="8" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4 col-xs-4">Url de l'image : </label>
				<div class="col-sm-7 col-xs-8">
					<form:input path="url" class="form-control" placeholder="Url" />
					</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-3 col-sm-9">
					<input type="submit" value="Sauver" /> <input type="reset"
						value="Reinitialiser" />
				</div>
			</div>
		</form:form>
	</div>
	
	<script type="text/javascript">
	var eventsSource = ${eventsJSON};
    $('.events').flexdatalist({
    	minLength: 1,
    	searchIn: ['name'],
    	selectionRequired: true,
    	valueProperty: 'idEvent',
        visibleProperties: ["name", "eventyear"],
        textProperty: '{name}, {eventyear}',
        data: eventsSource
   });
	
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
	    
	    var rolesSource = ${rolesJSON};
	    $('.roles').flexdatalist({
	    	minLength: 0,
	    	searchIn: ['name'],
	    	selectionRequired: true,
	    	valueProperty: 'idRole',
	        visibleProperties: ["name"],
	        textProperty: '{name}',
	        data: rolesSource
	   });
	</script>
</body>
</html>