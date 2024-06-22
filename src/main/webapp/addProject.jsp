<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Add Project</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
        <h1>SportFit</h1>
        <jsp:include page="nav.jsp">
            <jsp:param name="actual" value="addProject"/>
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

            <h2>Add a project</h2>

            <form method="POST" action="Controller?command=AddProject" novalidate>
                <p>
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" required
                           value="<c:out value='${namePreviousValue}'/>" >
                </p>

                <p>
                    <label for="startDate">start Date</label>
                    <input type="date" name="startDate" id="startDate"
                           value="<c:out value='${startDatePreviousValue}'/>">
                </p>
                <p>
                    <label for="endDate">end Date</label>
                    <input type="date" name="endDate" id="endDate"
                           value="<c:out value='${endDatePreviousValue}'/>">
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



