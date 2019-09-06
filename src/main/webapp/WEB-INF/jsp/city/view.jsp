<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAl6p1I1Vw7gnyXbPK71HpnFBuuNKAeEAM"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Cities</title>
</head>
<body>
    <style>
        #map-canvas {
          height: 800px;
          width: 1200px;
          margin: 0px;
          padding: 0px
        }
    </style>
<div id="map-canvas"></div>
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
	                infoWindow.setContent(json[i].infocontent);
	                infoWindow.open(map, marker);
	            }
	        })(marker, i));

		} // end loop

		
	}

	//Initialize the map
	google.maps.event.addDomListener(window, 'load', initialize);
</script>
</body>
</html>