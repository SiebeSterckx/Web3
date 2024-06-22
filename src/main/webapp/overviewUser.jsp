<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Overview Users</title>
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
        <div id="OverviewContainer">

            <c:if test="${!empty error}">
                <div class="alert-danger">
                        <p class="error">${error}</p>
                </div>
            </c:if>

            <h2>User overview</h2>

            <c:choose>
                <c:when test="${!empty users}">
                    <table>
                        <thead>
                            <tr>
                                <th>UserId</th>
                                <th>First Name</th>
                                <th>Last Name</th>
                                <th>E-mail</th>
                                <th>Team</th>
                                <th>Role</th>
                                <c:if test="${!empty loggedin}">
                                <div>
                                    <th>Edit</th>
                                    <c:if test="${loggedin.role == 'DIRECTOR'}">
                                        <th>Delete</th>
                                    </c:if>
                                </div>
                                </c:if>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr id="${user.userid}" class="${user.email}">
                                    <td><c:out value='${user.userid}'/></td>
                                    <td><c:out value='${user.firstName}'/></td>
                                    <td><c:out value='${user.lastName}'/></td>
                                    <td><c:out value='${user.email}'/></td>
                                    <td><c:out value='${user.team eq "ALPHA" ? "Alpha" :
                                        user.team eq "BETA" ? "Bèta" :
                                        user.team eq "GAMMA" ? "Gamma" :
                                        user.team eq "DELTA" ? "Delta" :
                                        user.team eq "EPSILON" ? "Epsilon" : user.team}'/></td>
                                    <td><c:out value='${user.role eq "DIRECTOR" ? "Director" :
                                        user.role eq "TEAMLEADER" ? "Teamleader" :
                                        user.role eq "EMPLOYEE" ? "Employee" : user.role}'/></td>
                                    <c:if test="${!empty loggedin}">
                                        <c:if test="${(loggedin.role == 'DIRECTOR')
                                        || (loggedin.role == 'EMPLOYEE' && loggedin.email == user.email)
                                        || (loggedin.role == 'TEAMLEADER' &&
                                                (user.team == loggedin.team || user.email == loggedin.email) && user.role != 'DIRECTOR')}">
                                            <div>
                                                <td class="edit">
                                                    <a href="Controller?command=EditUserPage&id=${user.userid}">
                                                        <img src="images/updateIcon.png" alt="Wijzigen">
                                                    </a>
                                                </td>
                                                <c:if test="${loggedin.role == 'DIRECTOR' && loggedin.email != user.email}">
                                                    <td class="verwijder">
                                                        <a href="Controller?command=DeleteUserPage&id=${user.userid}">
                                                            <img src="images/deleteIcon.png" alt="Verwijderen">
                                                        </a>
                                                    </td>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </c:when>

                    <c:otherwise>
                        <ul>
                            <li>
                                <p>There aren't any users yet!</p>
                            </li>
                            <li>
                                <p><a href="Controller?command=AddUserPage">Click here</a> to add an user</p>
                            </li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </table>

        </div>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>