<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.js"></script>

<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link title="timeline-styles" rel="stylesheet" href="//cdn.knightlab.com/libs/timeline3/latest/css/timeline.css">
        <script src="//cdn.knightlab.com/libs/timeline3/latest/js/timeline.js">     </script>
        <link href="<c:url value='/resources/css/timeline_style.css' />" rel="stylesheet"></link>
</head>
<body>
<%@ include file="../navbar.jsp" %>
<div class="container">
<h3><c:out value="${figure.toString()}"></c:out></h3>
<p>
  <c:if test="${figure.url!=null}"><img class="pull-right img-responsive" src="<c:url value = "${figure.url}"/>"></c:if>
  <c:out value="${figure.biography}"></c:out>
</p>

<div id="Timeline"></div>
    <script>

    var bpmnJsonString = ${figureJSON};

        var options ={  
           width:'100%',
           timenav_position:'top',
           language:'fr',
           start_at_end:false,
           initial_zoom:1,
           zoom_sequence:[0.1,0.2,0.5,1]
        }; 

         window.timeline = new TL.Timeline('Timeline',bpmnJsonString,options);
    </script>

 
</div>


</body>
</html>
