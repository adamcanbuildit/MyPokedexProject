<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<title>MyPokedex</title>
</head>
<body class="p-5">
	<div id="header">
		<form action="/logout" method="get" class="mt-2">
			<input type="submit" class="btn btn-primary float-end" value="Logout">
		</form>
		<a href="/findpokemon">Find a New Pokemon</a>
		<a href="/show/mypokedex/${user.id}">MyPokedex</a>
		<h1>MyPokedex</h1>
	</div>
	
	<div id="body">
		<c:forEach var="pokemon" items="${pokemonlist}">
			<img src="${pokemon.pictureUrl}" alt="" />
			<p><c:out value="${pokemon.name}" /></p>
			<p><c:out value="${pokemon.species}" /></p>
		</c:forEach>
	</div>
</body>
</html>