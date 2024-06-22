<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Search WorkOrder</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
        <h1>SportFit</h1>
        <jsp:include page="nav.jsp">
            <jsp:param name="actual" value="searchWorkOrder"/>
        </jsp:include>
    </header>

    <main>
        <div id="addContainer">

            <form method="POST" action="Controller?command=SearchWorkOrder">
                <p>
                    <label for="date">Search workorder by date</label>
                    <input type="date" id="date" name="date" required
                           value="<c:out value='${datePreviousValue}'/>" >
                </p>
                <p>
                    <input type="submit" id="search" value="Search">
                </p>
            </form>

        </div>
    </main>

    <jsp:include page="footer.jsp"/>

</body>
</html>
