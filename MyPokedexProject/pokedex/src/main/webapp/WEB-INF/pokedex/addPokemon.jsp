<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<title>Add Pokemon</title>
</head>
<body class="p-5">
	
	<div id="header">
		<form action="/logout" method="get" class="mt-2">
			<input type="submit" class="btn btn-primary float-end" value="Logout">
		</form>
		<a href="/findpokemon">Find a New Pokemon</a>
		<a href="/show/mypokedex/0">MyPokedex</a>
		<h1>Add Pokemon</h1>
	</div>

	<div id="edit-form-container">
		<form class="mt-5" action="/addpokemonwithname" method="post" id="name-form">
			<label for="name">Would you like to name your new pokemon? </label>
			<p><c:out value="${error}" /></p>
			<input type="text" id="name" name="name" />
			<form:errors path="name" class="alert-danger" />
			<input type="submit" form="name-form" value="Add to my Pokedex!" />
		</form>
		
		<form action="/addpokemonwithoutname" method="post" id="noname" >
			<input type="submit" form="noname" value="Name your pokemon ${pokemonSpecies}" />
		</form>
	</div>
	
	<c:if test="${pokemonId!=null}">
	<div id="search-result-container">
		<p><c:out value="Pokemon ID - ${pokemonId}" /></p>
		<p><c:out value="Pokemon Species -${pokemonSpecies}" /></p>
		<img src="<c:out value="${pokemonUrl}" />" alt="image of a ${pokemonName}" />
		<c:forEach var="stat" items="${pokemonStats}">
			<p><c:out value="${stat.key} - ${stat.value}" /></p>
		</c:forEach>
		<c:forEach var="data" items="${pokemonType}">
			<p><c:out value="${data}" /></p>
		</c:forEach>
	</div>
	</c:if>



</body>
</html>