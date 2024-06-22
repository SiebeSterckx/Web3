<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Edit User</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
        <h1>SportFit</h1>
        <jsp:include page="nav.jsp">
            <jsp:param name="actual" value="userOverview"/>
        </jsp:include>
    </header>

    <main>
        <div id="addContainer">
            <c:if test="${not empty errors2}">
                <div class="alert-danger">
                    <ul>
                        <li>! Original User is restored !</li>
                        <c:forEach items="${errors2}" var="error2">
                            <li class="error2">${error2}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form method="POST" action="Controller?command=EditUser&id=${user.userid}" novalidate>
                <p>
                    <label for="firstName">First Name</label>
                    <input type="text" id="firstName" name="firstName" required
                           value="<c:out value='${user.firstName}'/>" >
                </p>

                <p>
                    <label for="name">Last Name</label>
                    <input type="text" id="name" name="name" required
                           value="<c:out value='${user.lastName}'/>">
                </p>

                <p>
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required
                           value="<c:out value='${user.email}'/>">
                </p>
                <c:if test="${loggedin.role == 'DIRECTOR' && loggedin.email != user.email}">
                    <p>
                        <label for="team">Team</label>
                        <select name="team" id="team">
                            <option ${user.team == "ALPHA" ? 'selected="Selected"' : ''} value="ALPHA">Alpha</option>
                            <option ${user.team == "BETA" ? 'selected="Selected"' : ''} value="BETA">Bèta</option>
                            <option ${user.team == "GAMMA" ? 'selected="Selected"' : ''} value="GAMMA">Gamma</option>
                            <option ${user.team == "DELTA" ? 'selected="Selected"' : ''} value="DELTA">Delta</option>
                            <option ${user.team == "EPSILON" ? 'selected="Selected"' : ''} value="EPSILON">Epsilon</option>
                        </select>
                    </p>
                </c:if>
                <c:if test="${loggedin.role != 'EMPLOYEE' && loggedin.email != user.email}">
                    <p>
                        <label for="role">Role</label>
                        <select name="role" id="role">
                            <c:if test="${loggedin.role == 'DIRECTOR' }">
                                <option ${user.role == "DIRECTOR" ? 'selected="Selected"' : ''} value="DIRECTOR">Director</option>
                            </c:if>
                            <option ${user.role == "TEAMLEADER" ? 'selected="Selected"' : ''} value="TEAMLEADER">Teamleader</option>
                            <option ${user.role == "EMPLOYEE" ? 'selected="Selected"' : ''} value="EMPLOYEE">Employee</option>
                        </select>
                    </p>
                </c:if>

                <p>
                    <input type="submit" id="signUp" value="Edit">
                </p>
            </form>

        </div>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
