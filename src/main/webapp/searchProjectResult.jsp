<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Search Project Result</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
      <h1>SportFit</h1>
      <jsp:include page="nav.jsp">
        <jsp:param name="actual" value="searchProject"/>
      </jsp:include>
    </header>

    <main>
        <div id="OverviewContainer">
          <h2>Search Project Result</h2>

          <c:choose>
            <c:when test="${empty project}">
              <ul>
                <li>There is no project with this name</li>
                <li><a href="Controller?command=SearchProjectPage">Click here</a> to try again and search by another name</li>
              </ul>
            </c:when>

            <c:otherwise>
              <table>
                  <thead>
                      <tr>
                          <th>ProjectId</th>
                          <th>Name</th>
                          <th>Team</th>
                          <th>Start</th>
                          <th>End</th>
                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td><c:out value='${project.projectid}'/></td>
                          <td><c:out value='${project.name}'/></td>
                          <td><c:out value='${project.team}'/></td>
                          <td><c:out value='${project.startdate}'/></td>
                          <td><c:out value='${project.enddate}'/></td>
                      </tr>
                  </tbody>
                </table>
            </c:otherwise>
          </c:choose>

        </div>
    </main>

  <jsp:include page="footer.jsp"/>

</body>
</html>
