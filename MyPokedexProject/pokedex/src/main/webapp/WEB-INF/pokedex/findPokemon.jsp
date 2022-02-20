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
<body class="bg-light">
	<div id="header" class="opacity-4 pt-4 bg-danger bg-gradient text-black fw-bold">
		<h1 class="display-2 text-center mb-4">Find a <span class="fw-bold">Pokemon</span></h1>
		<div id="navbar" class="opacity-50 ps-5 pb-1 d-flex bg-light flex-row">
			<a href="/show/mypokedex/${user.id}" class="ms-2 me-2">MyPokedex</a> |
			<a href="/show/mypokemon/${user.id}" class="ms-2 me-2">View My Pokemon</a> |
			<a href="/findpokemon" class="ms-2 me-2">Find a New Pokemon</a> |
			<a href="/show/users" class="ms-2 me-2 flex-grow-1">View Other Pokedexes</a>
			<a href="/logout" class="ms-2 pe-5 me-5">Logout</a>
		</div>
	</div>

	<div id="search-form-container" class="pt-5 mb-5 container">
		<form class="mt-5" action="/findpokemon" method="post" id="search-form">
			<p>
				<label for="query" class="form-label fs-3">Search for a pokemon! </label>
				<p class="text-danger"><c:out value="${error}" /></p>
				<input type="text" class="form-control" id="query" name="query" placeholder="Enter pokemon name or number (i.e. 'bulbasaur' or '1')"/>
				<form:errors path="query" class="alert-danger" />
			</p>
			<input type="submit" form="search-form" class="btn btn-outline-success btn-lg" value="Search for Pokemon" />
		</form>
	</div>
	
	<c:if test="${pokemonId!=null}">
	<div id="body" class="d-flex flex-column p-5">
			<div class="row mb-4 p-4 bg-white rounded border border-dark border-3">
				<!-- display pokemon image -->
				<div class="col-md-2">
					<img class="img-thumbnail position-relative top-50 start-50 translate-middle" 
					width="200" height="200" src="${pokemonUrl}" alt="" />
				</div>
				<!-- display pokemon species & types -->
				<div class="col-md-4 d-flex flex-column align-content-center pt-3 mb-2">
					<div class="d-inline mt-4 align-content-center">
						<h3 class="mb-4 text-uppercase fs-1 fw-bold fst-italic d-flex flex-row align-items-center justify-content-center">
							<c:out value="${pokemonSpecies}" />
							<span class="ms-2 fs-4 align-middle"><c:out value="(${pokemonId})" /></span>
						</h3>
						<div class="d-flex flex-row justify-content-evenly flex-wrap">
							<c:forEach var="type" items="${pokemonType}">
								<p class="fst-italic fs-3 text-uppercase"><c:out value="${type}" /></p>
							</c:forEach>
						</div>
					</div>
				</div>
				<!-- display pokemon stats -->
				<div class="col-md-4 fw-bold d-flex fs-3 flex-column pt-3">
					<div class="d-flex flex-row justify-content-evenly">
						<p><c:out value="HP - ${pokemonStats.get('hp')}" /></p>
						<p><c:out value="SPD - ${pokemonStats.get('spd')}" /></p>
					</div>
					<div class="d-flex flex-row justify-content-evenly">
						<p><c:out value="ATK - ${pokemonStats.get('atk')}" /></p>
						<p><c:out value="DEF - ${pokemonStats.get('def')}" /></p>
					</div>
					<div class="d-flex flex-row justify-content-evenly">
						<p><c:out value="SP. ATK - ${pokemonStats.get('sp_atk')}" /></p>
						<p><c:out value="SP. DEF - ${pokemonStats.get('sp_def')}" /></p>
					</div>
				</div>
				<!-- display pokemon links (Release & Edit) -->
				<div class="col-md-2 d-flex align-content-center justify-content-center flex-wrap">
					<form action="/addpokemon" id="add" >
						<input type="submit" form="add" class="btn btn-success btn-lg" value="Add to your Pokedex!" />
					</form>
				</div>
			</div>
	</div>
	</c:if>
	
</body>
</html>