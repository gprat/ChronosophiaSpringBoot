<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>

<spring:url value="/event/list" var="eventUrl" />
<spring:url value="/chronology/list" var="chronologyUrl" />
<spring:url value="/figure/list" var="figureUrl" />
<spring:url value="/city/list" var="cityUrl" />
<spring:url value="/category/list" var="categoryUrl" />
<spring:url value="/role/list" var="roleUrl" />
<spring:url value="/logout" var="logoutUrl" />
<spring:url value="/userlist" var="userListUrl" />
<nav class="navbar navbar-inverse">  
  <div class="container-fluid">  
    <div class="navbar-header">  
      <a class="navbar-brand">Chronosophia</a>  
    </div>  
    <ul class="nav navbar-nav"> 
      <li><a href="${figureUrl}">Personnages</a></li>  
      <li><a href="${eventUrl}">Evènements</a></li>  
      <li><a href="${chronologyUrl}">Chronologies</a></li>
      <li><a href="${cityUrl}">Villes</a></li>  
      <li><a href="${categoryUrl}">Catégories</a></li>  
      <li><a href="${roleUrl}">Positions</a></li>
      <li><a href="${userListUrl}" >Utilisateurs</a></li>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li><a href="${logoutUrl}"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
    </ul>  
  </div>  
</nav>  