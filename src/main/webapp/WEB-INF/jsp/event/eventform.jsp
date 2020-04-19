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
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


 <spring:url value="/resources/css/style.css" var="styleCss" />
<spring:url value="/resources/css/normalize.css" var="normalizeCss" />
<spring:url value="/resources/js/jquery.flexdatalist.js" var="flexdatalistJS" />
<spring:url value="/resources/css/jquery.flexdatalist.min.css" var="flexdatalistCss" />
<link href="${flexdatalistCss}" rel="stylesheet" type="text/css">
<script src="${flexdatalistJS}"></script>
	<link href="${styleCss}" rel="stylesheet" /> 
<link href="${normalizeCss}" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
.nopadding {
	padding: 0 !important;
	margin: 0 !important;
}
</style>
<title>Ajouter un évènement</title>
</head>
<body>
<%@ include file="../navbar.jsp" %>
	<div class="container">
		<div class="col-sm-offset-1 col-sm-9">
			<h1>Ajouter un évènement</h1>
		</div>
		<spring:url value="/event/save" var="eventActionUrl" />
		<form:form method="post" modelAttribute="eventForm"
			action="${eventActionUrl}" class="form-horizontal">
			<form:hidden path="id" />
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Nom de l'évènement : </label>
				<div class="col-sm-7">
					<form:input path="name" class="form-control" placeholder="Nom"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Description de
					l'évènement : </label>
				<div class="col-sm-7">
					<form:textarea path="description" class="form-control" rows="8" placeholder="Description"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Personnalités : </label>
				<div class="col-sm-7">
					<form:input path="figures" class="figures form-control" placeholder="Personnalités" data-min-length='1' multiple='multiple'/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Date de l'évènement :
				</label>
				<div class="col-md-3 col-sm-4">
					<div class="row nopadding">
						<div class="col-lg-5 col-sm-6 nopadding">
							<div class="col-sm-6 nopadding">
								<form:input path="day" class="form-control" placeholder="jj" maxlength="2" />
							</div>
							<div class="col-sm-6 nopadding">
								<form:input path="month" class="form-control" placeholder="mm" maxlength="2" />
							</div>
						</div>
						<div class="col-sm-3 nopadding">
							<form:input path="year" class="form-control" placeholder="aaaa" maxlength="5"/>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Catégories : </label>
				<div class="col-sm-7">
					<form:input path="categories" class="categories form-control" placeholder="Catégories" data-min-length='0' multiple='multiple'/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Ville : </label>
				<div class="col-sm-7">
					<form:input path="idCity" class="cities form-control" placeholder="Villes" data-min-length='1'/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Url de
					l'image : </label>
				<div class="col-sm-7">
					<form:input path="url" class="form-control" rows="8" placeholder="Url"/>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-8">
					<input type="submit" value="Sauver" /> <input type="reset"
						value="Reinitialiser" />
				</div>
			</div>
		</form:form>
	</div>
    <script type="text/javascript">
    var figuresSource = ${figuresJSON};
    $('.figures').flexdatalist({
    	minLength: 1,
    	searchIn: ['firstName','lastName'],
    	selectionRequired: true,
    	valueProperty: 'idFigure',
        visibleProperties: ["firstName","lastName","figureDates"],
        textProperty: '{firstName} {lastName}',
        data: figuresSource
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
    
    var citiesSource = ${citiesJSON};
    $('.cities').flexdatalist({
    	minLength: 1,
    	searchIn: ['name'],
    	selectionRequired: true,
    	valueProperty: 'idCity',
        visibleProperties: ["sCity"],
        textProperty: '{name}',
        data: citiesSource
   });
    </script>
  
</body>
</html>