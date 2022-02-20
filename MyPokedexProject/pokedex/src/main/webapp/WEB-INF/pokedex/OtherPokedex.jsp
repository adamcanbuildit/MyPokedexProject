<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<title><c:out value="${pokedexOwner}'s Pokedex" /></title>
</head>
<body class="bg-light">
	<div id="header" class="opacity-4 pt-4 bg-danger bg-gradient text-black fw-bold">
		<h1 class="display-2 text-center mb-4">
			<c:out value="${pokedexOwner}'s "/>
			<span class="fw-bold">Pokedex</span>
		</h1>
		<div id="navbar" class="opacity-50 ps-5 pb-1 d-flex bg-light flex-row">
			<a href="/show/mypokedex/${user.id}" class="ms-2 me-2">MyPokedex</a> |
			<a href="/show/mypokemon/${user.id}" class="ms-2 me-2">View My Pokemon</a> |
			<a href="/findpokemon" class="ms-2 me-2">Find a New Pokemon</a> |
			<a href="/show/users" class="ms-2 me-2 flex-grow-1">View Other Pokedexes</a>
			<a href="/logout" class="ms-2 pe-5 me-5">Logout</a>
		</div>
	</div>
	
	
	
	<div id="body" class="row row-cols-4 p-5">
		<c:forEach var="pokemon" items="${pokemonlist}">
			<div class="col">
			<div class="m-3 p-3 ">
					<!-- display pokemon image -->
						<h2 class="mb-1 text-uppercase fs-3 d-flex flex-row justify-content-evenly flex-wrap"><c:out value="${pokemon.name}" /></h2>
					<img class="img-thumbnail position-relative top-0 start-50 translate-middle-x rounded border border-dark border-3" 
					width="200" height="200" src="${pokemon.pictureUrl}" alt="" />
					<!-- display pokemon name & species -->
					<div class="d-flex flex-column pt-3 mb-2">
						<h3 class="mb-1 text-uppercase fs-5 d-flex flex-row justify-content-evenly flex-wrap"><c:out value="(${pokemon.species})" /></h3>
					</div>
			</div>			
			</div>
			
		</c:forEach>
	</div>
</body>
</html>