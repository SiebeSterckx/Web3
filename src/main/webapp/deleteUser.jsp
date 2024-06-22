<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Delete User</title>
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
        <div id="deleteContainer">
            <h2>Confirmation</h2>
            <p>Are you sure you want to remove this user?</p>

            <table>
                <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>E-mail</th>
                        <th>Team</th>
                        <th>Role</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><c:out value='${user.firstName}'/></td>
                        <td><c:out value='${user.lastName}'/></td>
                        <td><c:out value='${user.email}'/></td>
                        <td><c:out value='${user.team}'/></td>
                        <td><c:out value='${user.role}'/></td>
                    </tr>
                </tbody>
            </table>

            <div id="verwijderen2">

                <form action="Controller?command=DeleteUser&id=${user.userid}" method="post" novalidate>
                    <input id="verwijderen" type="submit" value="Yes" class="verwijderen">
                </form>
                <form action="Controller?command=OverviewUser" method="post" novalidate>
                    <input id="nietVerwijderen" type="submit" value="No" class="verwijderen">
                </form>

            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
