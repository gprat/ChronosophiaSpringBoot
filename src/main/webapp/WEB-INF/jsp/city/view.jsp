<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<spring:eval expression="@environment.getProperty('MY_API_KEY')" var="myKey"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=${myKey}"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1">
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.js"></script>


<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>View Cities</title>
</head>
<body>
<%@ include file="../navbar.jsp" %>
    <style>
    	body, html {
  			height: 100%;
  			width: 100%;
		}
    
        #map-canvas {
          height: 60%;
          width: 100%;
          max-width: 1200px;
          margin: auto;
          padding: 0px
        }
        #city-canvas {
          width: 100%;
          max-width: 1200px;
          margin: auto;
        }
        
    </style>
<div id="map-canvas"></div>
<div id="city-canvas"></div>
<form:form method="post" id="form_id">
<div class="hidden" id="input_div">
					<input type="submit" value="Afficher" name="view" class="btn" />
</div> 
</form:form>


<script type="text/javascript">
var json = ${citiesJSON}

var map;


	function initialize() {

		// Giving the map som options
		var mapOptions = {
			zoom : 6,
			center : new google.maps.LatLng(50.11, 8.68)
		};

		// Creating the map
		map = new google.maps.Map(document.getElementById('map-canvas'),
				mapOptions);
		
		 // Add multiple markers to map
	    var infoWindow = new google.maps.InfoWindow(), marker, i;

		// Looping through all the entries from the JSON data
		for (var i = 0; i < json.length; i++) {

			// Current object
			var obj = json[i];

			// Adding a new marker for the object
			var marker = new google.maps.Marker({
				position : new google.maps.LatLng(obj.latitude, obj.longitude),
				label : obj.label,
				map : map
			});
			
			google.maps.event.addListener(marker, 'click', (function(marker, i) {
	            return function() {
	                infoWindow.setContent(json[i].titleCity);
	                infoWindow.open(map, marker);
	                $('#city-canvas').html(json[i].infocontent);
	                
	                $("#input_div").removeClass('hidden').addClass('text-center'); 
	                $("#form_id").attr("action", "/chronosophia/city/"+json[i].idCity); 
	            }
	        })(marker, i));

		} // end loop

		
	}

	//Initialize the map
	google.maps.event.addDomListener(window, 'load', initialize);
	
	
</script>
</body>
</html>