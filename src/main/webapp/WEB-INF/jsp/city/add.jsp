<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1">
<title>Ajouter une ville</title>
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
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
<div class="col-sm-offset-1 col-sm-9">
 <h1>Ajouter une ville</h1>
</div>
 <spring:url value="/city/save" var="cityActionUrl" />
	<form:form method="post" modelAttribute="cityForm" action="${cityActionUrl}" class="form-horizontal">
		<div class="form-group">
				<div class="col-sm-offset-3 col-sm-7">
			<div id="locationField">
			<input id="autocomplete" placeholder="Enter your address"
				onFocus="geolocate()" type="text"  class="form-control"></input>
		</div>
		</div>
		</div>
		<form:hidden path="idCity" />
		<div class="form-group">
		<label class="control-label col-md-3 col-sm-4" >Nom de la ville : </label>
		<div class="col-sm-7">
		<form:input path="cityname" id="locality" class="form-control"/>
		</div>
		</div>
		<div class="form-group">
		<label  class="control-label col-md-3 col-sm-4">Nom du pays : </label>
		<div class="col-sm-7">
		<form:input path="countryname" id="country"  class="form-control"/>
		</div>
		</div>
		<div class="form-group">
		<label  class="control-label col-md-3 col-sm-4">Longitude : </label>
		<div class="col-sm-7">
		<form:input path="longitude" id="longitude"  class="form-control"/>
		</div>
		</div>
		<div class="form-group">
		<label class="control-label col-md-3 col-sm-4">Latitude : </label>
		<div class="col-sm-7">
		<form:input path="latitude" id="latitude"  class="form-control"/>
		</div>
		<div class="form-group">
				<label class="control-label col-md-3 col-sm-4">Description : </label>
				<div class="col-sm-7">
					<form:textarea path="description" class="form-control" placeholder="Description" rows="8" />
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-3 col-sm-8">
				<input type="submit" value="Sauver" />
				<input type="reset" value="Réinitialiser" />
			</div>
		</div>
	</form:form>

	<script>
		// This example displays an address form, using the autocomplete feature
		// of the Google Places API to help users fill in the information.

		var placeSearch, autocomplete;
		var componentForm = {
			locality : 'long_name',
			country : 'long_name',
		};

		function initAutocomplete() {
			// Create the autocomplete object, restricting the search to geographical
			// location types.
			autocomplete = new google.maps.places.Autocomplete(
			/** @type {!HTMLInputElement} */
			(document.getElementById('autocomplete')), {
				types : [ 'geocode' ]
			});

			// When the user selects an address from the dropdown, populate the address
			// fields in the form.
			autocomplete.addListener('place_changed', fillInAddress);
		}

		// [START region_fillform]
		function fillInAddress() {
			// Get the place details from the autocomplete object.
			var place = autocomplete.getPlace();

			for ( var component in componentForm) {
				document.getElementById(component).value = '';
				document.getElementById(component).disabled = false;
			}

			// Get each component of the address from the place details
			// and fill the corresponding field on the form.
			for (var i = 0; i < place.address_components.length; i++) {
				var addressType = place.address_components[i].types[0];
				if (componentForm[addressType]) {
					var val = place.address_components[i][componentForm[addressType]];
					document.getElementById(addressType).value = val;
				}
			}
			
			document.getElementById("latitude").value = place.geometry.location.lat();
			document.getElementById("longitude").value = place.geometry.location.lng();
		}
		// [END region_fillform]

		// [START region_geolocation]
		// Bias the autocomplete object to the user's geographical location,
		// as supplied by the browser's 'navigator.geolocation' object.
		function geolocate() {
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {
					var geolocation = {
						lat : position.coords.latitude,
						lng : position.coords.longitude
					};
					var circle = new google.maps.Circle({
						center : geolocation,
						radius : position.coords.accuracy
					});
					autocomplete.setBounds(circle.getBounds());
				});
			}
		}
		// [END region_geolocation]
	</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=myKey&signed_in=true&libraries=places&language=fr&callback=initAutocomplete"
        async defer></script>
</div>
</body>
</html>