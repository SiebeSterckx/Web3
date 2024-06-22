<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Delete Project</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
        <h1>SportFit</h1>
        <jsp:include page="nav.jsp">
            <jsp:param name="actual" value="projectOverview"/>
        </jsp:include>
    </header>

    <main>
        <div id="deleteContainer">
            <h2>Confirmation</h2>
            <p>Are you sure you want to remove this user?</p>

            <table>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Team</th>
                        <th>Start</th>
                        <th>End</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><c:out value='${project.name}'/></td>
                        <td><c:out value='${project.team}'/></td>
                        <td><c:out value='${project.startdate}'/></td>
                        <td><c:out value='${project.enddate}'/></td>
                    </tr>
                </tbody>
              </table>

          <div id="verwijderen2">

            <form action="Controller?command=DeleteProject&id=${project.projectid}" method="post" novalidate>
              <input id="verwijderen" type="submit" value="Yes" class="verwijderen">
            </form>
            <form action="Controller?command=OverviewProject" method="post" novalidate>
              <input id="nietVerwijderen" type="submit" value="No" class="verwijderen">
            </form>

          </div>
        </div>
    </main>

  <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
