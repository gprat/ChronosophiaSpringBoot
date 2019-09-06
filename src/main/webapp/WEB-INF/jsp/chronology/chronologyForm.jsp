<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>

<spring:url value="/resources/css/jquery-ui.css" var="jqueryCss" />
<spring:url value="/resources/js/jquery-ui.js" var="jqueryJs" />
<spring:url value="/resources/js/fieldChooser.js" var="fieldChooserJs" />
<!-- Latest compiled JavaScript -->


<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.js"></script>
	<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<spring:url value="/resources/css/style.css" var="styleCss" />
<spring:url value="/resources/css/normalize.css" var="normalizeCss" />
<spring:url value="/resources/js/jquery.flexdatalist.js"
	var="flexdatalistJS" />
<spring:url value="/resources/css/jquery.flexdatalist.min.css"
	var="flexdatalistCss" />
<link href="${flexdatalistCss}" rel="stylesheet" type="text/css">
<script src="${flexdatalistJS}"></script>
<link href="${normalizeCss}" rel="stylesheet">

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${jqueryCss}" />
<link rel="stylesheet" type="text/css" href="${styleCss}" />

<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

<script src="${fieldChooserJs}"></script>

<title>Ajouter une chronologie</title>
</head>
<body>
	<div class="container">
		<div class="col-sm-offset-1 col-sm-9">
			<h1>Ajouter un évènement</h1>
		</div>

		<spring:url value="/chronology/filter" var="selectUrl" />
		<form:form method="post" modelAttribute="selectEventForm"
			action="${selectUrl}">

			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Catégories : </label>
				<div class="col-sm-7">
					<form:input path="categories" class="categories form-control"  data-min-length='0' multiple='multiple'/>
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Personnalités : </label>
				<div class="col-sm-7">
					<form:input path="figures" class="figures form-control"  data-min-length='0' multiple='multiple'/>
				</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Villes : </label>
				<div class="col-sm-7">
					<form:input path="cities" class="cities form-control"  data-min-length='0' multiple='multiple'/>
				</div>
			</div>
			
			<form:hidden path="eventsToExclude" id="eventsToExclude" />
		<div class="col-sm-offset-7 col-sm-5">	      
		<input type="submit" value="Filtrer" class="btn btn-default" />
		</div>
		</form:form>

		<spring:url value="/chronology/save" var="chronologyActionUrl" />
		
		<div class="col-xs-12" style="height:40px;"></div>
		
		<form:form method="post" modelAttribute="chronologyForm" class="form-horizontal"
			action="${chronologyActionUrl}">
			<form:hidden path="eventList" id="selectedEventList" />
			<form:hidden path="id" />
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Nom de la chronologie : </label>
					<div class="col-sm-7">
					<form:input path="name" class="form-control" placeholder="Nom" />
					</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Description de la chronologie : </label>
				<div class="col-sm-7">
					<form:textarea path="description" class="form-control" placeholder="Description" rows="8" />
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Url de l'image : </label>
				<div class="col-sm-7">
					<form:input path="url" class="form-control" placeholder="Url" />
					</div>
			</div>
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Catégorie : </label>
				<div class="col-sm-7">
					<form:input path="category" class="category form-control" placeholder="Catégories" data-min-length='0'/>
				</div>
			</div>
			<input type="submit" value="Sauver" class="btn btn-default" /> 
			<input type="reset" value="Reinitialiser" class="btn btn-default" />
			
			<div class="col-xs-12" style="height:30px;"></div>
			
			<div id="fieldChooser" class="form-control">
				<div id="sourceFields">
					<c:forEach items="${AvailableEventList}" var="event">
						<div id="${event.getIdEvent()}">${event.getName()}</div>
					</c:forEach>
				</div>
				<div id="destinationFields">
					<c:forEach items="${SelectedEventList}" var="event">
						<div id="${event.getIdEvent()}">${event.getName()}</div>
					</c:forEach>
				</div>
			</div>
			
		</form:form>

	</div>

	<script type="text/javascript">
		var $sourceFields = $("#sourceFields");
		var $destinationFields = $("#destinationFields");
		var $chooser = $("#fieldChooser").fieldChooser($sourceFields,
				$destinationFields);

		$(function() {
			$('#destinationFields').sortable({
				update : function(event, ui) {
					var productOrder = $destinationFields.sortable('toArray').toString();
					document.getElementById('selectedEventList').value= productOrder;
					document.getElementById('eventsToExclude').value= productOrder;
				}
			});
		});
		
		
	</script>
	
	<script type="text/javascript">
    var figuresSource = ${figuresJSON};
    $('.figures').flexdatalist({
    	minLength: 0,
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
    
    $('.category').flexdatalist({
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
    	minLength: 0,
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