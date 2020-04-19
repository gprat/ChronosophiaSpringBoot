<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
    <head>
        <link title="timeline-styles" rel="stylesheet" href="//cdn.knightlab.com/libs/timeline3/latest/css/timeline.css">
        <script src="//cdn.knightlab.com/libs/timeline3/latest/js/timeline.js">     </script>
        <link href="<c:url value='/resources/css/timeline_style.css' />" rel="stylesheet"></link>
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
    <div id="Timeline"></div>
    <script>

    var bpmnJsonString = ${chronologieJSON};

        var options ={  
           width:'100%',
           height:'500',
           timenav_position:'top',
           language:'fr',
           start_at_end:false,
           start_at_slide:0,
           initial_zoom:1,
           zoom_sequence:[0.1,0.2,0.5,1]
        }; 

         window.timeline = new TL.Timeline('Timeline',bpmnJsonString,options);
    </script>
</div>
</body>
</html>