<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Edit WorkOrder</title>
  <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
      <h1>SportFit</h1>
      <jsp:include page="nav.jsp">
        <jsp:param name="actual" value="workOrderOverview"/>
      </jsp:include>
    </header>

    <main>
      <div id="addContainer">
        <c:if test="${not empty errors}">
          <div class="alert-danger">
            <ul>
              <li>! Original Work order is restored !</li>
              <c:forEach items="${errors}" var="error">
                <li class="error">${error}</li>
              </c:forEach>
            </ul>
          </div>
        </c:if>

        <form method="POST" action="Controller?command=EditWorkOrder&id=${workorder.workorderid}" novalidate>
          <h2>Work order <c:out value='${workorder.workorderid}'/>, by <c:out value='${workorder.name}'/> (<c:out value='${workorder.team}'/>)</h2>
          <p>
            <label for="startTime">Start Time</label>
            <input type="time" name="startTime" id="startTime"
                   value="<c:out value='${workorder.starttime}'/>">
          </p>
          <p>
            <label for="endTime">End Time</label>
            <input type="time" name="endTime" id="endTime"
                   value="<c:out value='${workorder.endtime}'/>">
          </p>
          <p>
            <label for="description">Description</label>
            <input type="text" name="description" id="description"
                   value="<c:out value='${workorder.description}'/>" maxlength="100">
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
