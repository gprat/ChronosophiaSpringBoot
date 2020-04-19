<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>AccessDenied page</title>
</head>
<body>
	<div class="generic-container">
		<div class="authbar">
			<span>Cher <strong>${loggedinuser}</strong>, Vous n'êtes pas autorisé à accéder à cette page.</span> <span class="floatRight"><a href="<c:url value="/logout" />">Déconnexion</a></span>
		</div>
	</div>
</body>
</html>