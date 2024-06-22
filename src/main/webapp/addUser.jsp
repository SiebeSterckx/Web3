<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Register User</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
        <h1>SportFit</h1>
        <jsp:include page="nav.jsp">
            <jsp:param name="actual" value="addUser"/>
        </jsp:include>
    </header>

    <main>
        <div id="addContainer">
            <c:if test="${not empty errors}">
                <div class="alert-danger">
                    <ul>
                        <c:forEach items="${errors}" var="error">
                            <li class="error">${error}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <h2>Add a user</h2>

            <form method="POST" action="Controller?command=AddUser" novalidate>
                <p>
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" required
                           value="<c:out value='${firstNamePreviousValue}'/>">
                </p>
                <p>
                    <label for="name">Last Name</label>
                    <input type="text" id="name" name="name" required
                           value="<c:out value='${namePreviousValue}'/>">
                </p>
                <p>
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required
                           value="<c:out value='${emailPreviousValue}'/>">
                </p>
                <p>
                    <label for="team">Team</label>
                    <select name="team" id="team" >
                        <option ${teamPreviousValue == "APHA" ? 'selected="Selected"' : ''} value="ALPHA">Alpha</option>
                        <option ${teamPreviousValue == "BETA" ? 'selected="Selected"' : ''} value="BETA">Beta</option>
                        <option ${teamPreviousValue == "GEMMA" ? 'selected="Selected"' : ''} value="GAMMA">Gamma</option>
                        <option ${teamPreviousValue == "DELTA" ? 'selected="Selected"' : ''} value="DELTA">Delta</option>
                        <option ${teamPreviousValue == "EPSILON" ? 'selected="Selected"' : ''} value="EPSILON">Epsilon</option>
                    </select>
                </p>
                <p>
                    <label for="password">Password</label>
                    <input type="password" id="password"  name="password" required
                           value="<c:out value='${passwordPreviousValue}'/>">
                </p>
                <p>
                    <input type="submit" id="signUp" value="Sign Up">
                </p>
            </form>

        </div>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
