<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<title>My Pokemon</title>
</head>
<body class="bg-light">
	<div id="header" class="opacity-4 pt-4 bg-danger bg-gradient text-black fw-bold">
		<h1 class="display-2 text-center mb-4">My <span class="fw-bold">Pokemon</span></h1>
		<div id="navbar" class="opacity-50 ps-5 pb-1 d-flex bg-light flex-row rounded-bottom">
			<a href="/show/mypokedex/${user.id}" class="ms-2 me-2">MyPokedex</a> |
			<a href="/show/mypokemon/${user.id}" class="ms-2 me-2">View My Pokemon</a> |
			<a href="/findpokemon" class="ms-2 me-2">Find a New Pokemon</a> |
			<a href="/show/users" class="ms-2 me-2 flex-grow-1">View Other Pokedexes</a>
			<a href="/logout" class="ms-2 pe-5 me-5">Logout</a>
		</div>
	</div>
	
	<div id="edit-form-container" class="pt-5 mb-5 container">
		<form class="mt-5" action="/rename/${editPokemon.id}" method="post" id="name-form">
			<input type="hidden" name="_method" value="put">
			<label for="name" class="form-label fs-3">What would you like to rename your Pokemon? </label>
			<p class="text-danger"><c:out value="${error}" /></p>
			<input type="text" id="name" name="name" class="form-control" placeholder="Current name: ${editPokemon.name}" />
			<div class="d-flex justify-content-evenly mt-3">
				<input type="submit" form="name-form" class="btn btn-outline-success btn-lg" value="Change my Pokemon's name!" />
			</div>
		</form>
	</div>
	
	<div id="add-pokemon-body" class="d-flex flex-column p-5">
			<div class="row mb-4 p-4 bg-white rounded border border-dark border-3">
				<!-- display pokemon image -->
				<div class="col-md-2">
					<img class="img-thumbnail position-relative top-50 start-50 translate-middle" 
					width="200" height="200" src="${editPokemon.pictureUrl}" alt="" />
				</div>
				<!-- display pokemon species & types -->
				<div class="col-md-4 d-flex flex-column align-content-center pt-3 mb-2">
					<div class="d-inline mt-4 align-content-center">
						<h3 class="mb-4 text-uppercase fs-1 fw-bold fst-italic d-flex flex-row align-items-center justify-content-center">
							<c:out value="${editPokemon.species}" />
							<span class="ms-2 fs-4 align-middle"><c:out value="(${editPokemon.pokeNumber})" /></span>
						</h3>
						<div class="d-flex flex-row justify-content-evenly flex-wrap">
							<c:forEach var="type" items="${editPokemon.types}">
								<p class="fst-italic fs-3 text-uppercase"><c:out value="${type}" /></p>
							</c:forEach>
						</div>
					</div>
				</div>
				<!-- display pokemon stats -->
				<div class="col-md-4 fw-bold d-flex fs-3 flex-column pt-3">
					<div class="d-flex flex-row justify-content-evenly">
						<p><c:out value="HP - ${editPokemon.hp}" /></p>
						<p><c:out value="SPD - ${editPokemon.spd}" /></p>
					</div>
					<div class="d-flex flex-row justify-content-evenly">
						<p><c:out value="ATK - ${editPokemon.atk}" /></p>
						<p><c:out value="DEF - ${editPokemon.def}" /></p>
					</div>
					<div class="d-flex flex-row justify-content-evenly">
						<p><c:out value="SP. ATK - ${editPokemon.spAtk}" /></p>
						<p><c:out value="SP. DEF - ${editPokemon.spDef}" /></p>
					</div>
				</div>
				<!-- display pokemon links (Release & Edit) -->
				<div class="col-md-2 d-flex flex-column justify-content-evenly flex-wrap">
					<form action="/release/${editPokemon.id}" method="post">
						<input type="hidden" name="_method" value="delete">
						<input type="submit" class="btn btn-danger" value="Release Pokemon">
					</form>
				</div>
			</div>
	</div>
</body>
</html>