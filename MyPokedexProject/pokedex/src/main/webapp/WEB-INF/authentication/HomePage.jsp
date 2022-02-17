<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css" />
<title>Login or Register</title>
</head>
<body class="d-flex flex-row justify-content-start p-5">
	<div class="col m-5">
		<h1>Register</h1>
		<p><c:out value="${register_error}" /></p>
		<form:form method="POST" action="/registration" modelAttribute="user" id="registration-form">
			<p>
				<form:label path="displayName" class="form-label">Name:</form:label>
				<form:input type="text" path="displayName" class="form-control" />
				<form:errors class="alert-danger" path="displayName" />
			</p>
			<p>
				<form:label path="email" class="form-label">Email:</form:label>
				<form:input type="email" path="email" class="form-control" />
				<form:errors class="alert-danger" path="email" />
			</p>
			<p>
				<form:label path="password" class="form-label">Password:</form:label>
				<form:password path="password" class="form-control" />
				<form:errors class="alert-danger" path="password" />
			</p>
			<p>
				<form:label path="passwordConfirmation" class="form-label">Password Confirmation:</form:label>
				<form:password path="passwordConfirmation" class="form-control" />
				<form:errors class="alert-danger" path="passwordConfirmation" />
			</p>
			<input type="submit" form="registration-form" value="Register!" />
		</form:form>
	</div>
	<div class="col m-5">
		<h1>Login</h1>
		<p>
			<c:out value="${login_error}" />
		</p>
		<form method="post" action="/login" id="login-form">
			<p>
				<label for="email" class="form-label">Email:</label>
				<input type="email" id="email" name="email" class="form-control" />
			</p>
			<p>
				<label for="password" class="form-label">Password:</label>
				<input type="password" id="password" name="password" class="form-control" />
			</p>
			<input type="submit" form="login-form" value="Login!" />	
		</form>
	</div>
</body>
</html>
