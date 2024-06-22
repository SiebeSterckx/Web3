<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Add WorkOrder</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
        <h1>SportFit</h1>
        <jsp:include page="nav.jsp">
            <jsp:param name="actual" value="addWorkOrder"/>
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

            <h2>Add a work order</h2>

            <form method="POST" action="Controller?command=AddWorkOrder" novalidate>
                <p>
                    <label for="date">Date</label>
                    <input type="date" name="date" id="date"
                           value="<c:out value='${datePreviousValue}'/>">
                </p>
                <p>
                    <label for="startTime">Start Time</label>
                    <input type="time" name="startTime" id="startTime"
                           value="<c:out value='${startTimePreviousValue}'/>">
                </p>
                <p>
                    <label for="endTime">End Time</label>
                    <input type="time" name="endTime" id="endTime"
                           value="<c:out value='${endTimePreviousValue}'/>">
                </p>
                <p>
                    <label for="description">Description</label>
                    <input type="text" name="description" id="description"
                           value="<c:out value='${descriptionPreviousValue}'/>" maxlength="100">
                </p>
                <p>
                    <input type="submit" id="signUp" value="Add">
                </p>
            </form>

        </div>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
