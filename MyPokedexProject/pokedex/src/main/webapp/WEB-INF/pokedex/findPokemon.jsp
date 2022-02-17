<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<title>Find a Pokemon</title>
</head>
<body class="p-5">
	<div id="header">
		<form action="/logout" method="get" class="mt-2">
			<input type="submit" class="btn btn-primary float-end" value="Logout">
		</form>
		<a href="/findpokemon">Find a New Pokemon</a>
		<a href="/show/mypokedex/${user.id}">MyPokedex</a>
		<h1>Find Pokemon</h1>
	</div>

	<div id="search-form-container">
		<form class="mt-5" action="/findpokemon" method="post" id="search-form">
			<p>
				<label for="query">Search: </label>
				<input type="text" id="query" name="query" />
				<form:errors path="query" class="alert-danger" />
			</p>
			<input type="submit" form="search-form" value="Search for Pokemon" />
		</form>
		<p><c:out value="${error}" /></p>
	</div>
	
	<c:if test="${pokemonId!=null}">
	<div id="search-result-container">
		<p><c:out value="Pokemon ID - ${pokemonId}" /></p>
		<p><c:out value="Pokemon Species -${pokemonSpecies}" /></p>
		<img src="<c:out value="${pokemonUrl}" />" alt="image of a ${pokemonSpecies}" />
		<c:forEach var="stat" items="${pokemonStats}">
			<p><c:out value="${stat.key} - ${stat.value}" /></p>
		</c:forEach>
		<c:forEach var="data" items="${pokemonType}">
			<p><c:out value="${data}" /></p>
		</c:forEach>
	</div>
	<form action="/addpokemon" id="add" >
			<input type="submit" form="add" value="Add to your Pokedex" />
	</form>
	</c:if>
	
</body>
</html>