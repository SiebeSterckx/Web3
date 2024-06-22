<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>

<body>

	<header>
		<h1>SportFit</h1>
		<jsp:include page="nav.jsp">
			<jsp:param name="actual" value="index"/>
		</jsp:include>
	</header>

	<main>
		<c:choose>
			<c:when test="${empty sessionScope.loggedin}">
				<div id="LoginContainer">
					<c:if test="${not empty error}">
						<div class="alert-danger">
							<p class="error">${error}</p>
						</div>
					</c:if>
					<div id="LoginSession">
						<div id="LoginLeft">
							<img src="images/Login.jpg" alt="">
						</div>
						<div id="LoginRight">
							<h3>We are <strong>SportFit</strong></h3>
							<p>Welcome back! </br> Log in to view the website:</p>

							<form action="Controller?command=Login" method="POST" novalidate>
								<p>
									<label for="email"><img src="images/emailIcon.png" alt="Email"></label>
									<input type="email" name="email" id="email" placeholder="Email" required>
								</p>
								<p>
									<label for="password"><img src="images/lockIcon.png" alt="Password"></label>
									<input type="password" name="password" id="password" placeholder="Password" required>
								</p>
								<p>
									<input type="submit" value="Log in" id="logIn">
								</p>
							</form>

						</div>
					</div>
				</div>

		</c:when>
			<c:otherwise>
				<div id="HomeWelkom">
					<div id="TekstWelkom">
						<h2>SportFit</h2>
						<h3>Welcome back <strong>${user.firstName}</strong>!</h3>
						<p>
							SportFit offers you the opportunity to get fit the way you want, by offering different memberships.
							<br>Choose the membership that suits you and Go For <strong>IT</strong>!
						</p>
						<a id="logoutButton" href="Controller?command=Logout">Uitloggen</a>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</main>

	<jsp:include page="footer.jsp"></jsp:include>

</body>
</html>

