<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Edit Project</title>
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
        <div id="addContainer">
          <c:if test="${not empty errors2}">
            <div class="alert-danger">
              <ul>
                <li>! Original Project is restored !</li>
                <c:forEach items="${errors2}" var="error2">
                  <li class="error2">${error2}</li>
                </c:forEach>
              </ul>
            </div>
          </c:if>

        <form method="POST" action="Controller?command=EditProject&id=${project.projectid}" novalidate>
          <h2>Project <c:out value='${project.projectid}'/>, <c:out value='${project.name}'/></h2>
          <p>
            <label for="startDate">start Date</label>
            <input type="date" name="startDate" id="startDate" required
                   value="<c:out value='${project.startdate}'/>">
          </p>
          <p>
            <label for="endDate">end Date</label>
            <input type="date" name="endDate" id="endDate"
                   value="<c:out value='${project.enddate}'/>">
          </p>
          <p>
            <input type="submit" id="signUp" value="Edit">
          </p>
        </form>

      </div>
    </main>

  <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
