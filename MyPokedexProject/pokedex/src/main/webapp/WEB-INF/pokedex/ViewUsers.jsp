<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<title>AllPokedex</title>
</head>
<body class="bg-light">
	<div id="header"
		class="opacity-4 pt-4 bg-danger bg-gradient text-black fw-bold">
		<h1 class="display-2 text-center mb-4">
			All <span class="fw-bold">Pokedex</span>
		</h1>
		<div id="navbar" class="opacity-50 ps-5 pb-1 d-flex bg-light flex-row">
			<a href="/show/mypokedex/${user.id}" class="ms-2 me-2">MyPokedex</a> | 
			<a href="/show/mypokemon/${user.id}" class="ms-2 me-2">View My Pokemon</a> | 
			<a href="/findpokemon" class="ms-2 me-2">Find a New Pokemon</a> | 
			<a href="/show/users" class="ms-2 me-2 flex-grow-1">View Other Pokedexes</a> 
			<a href="/logout" class="ms-2 pe-5 me-5">Logout</a>
		</div>
	</div>
	
	<div class="container mt-5">
	<table class="table">
		<thead>
			<tr>
				<th scope="col">Display Name</th>
				<th scope="col">Number of Pokemon</th>
				<th scope="col">Likes</th>
				<th scope="col">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="userX" items="${userList}">
				<tr>
					<th scope="row">
						<a href="/show/mypokedex/${userX.id}">
							<c:out value="${userX.displayName}"/>
						</a>							
					</th>					
					<td><c:out value="${userX.pokemonRoster.size()}"/></td>
					<td><c:out value="${userX.likers.size()}"/></td>
					<td>
						<c:if test="${!user.likes.contains(userX)}">
							<a href="/like/${userX.id}">Like</a>
						</c:if>
						<c:if test="${user.likes.contains(userX)}">
							<a href="/like/${userX.id}">Unlike</a>
						</c:if>
					</td>
				</tr>	
			</c:forEach>
		</tbody>
	</table>
	</div>



</body>
</html>