<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Search Project</title>
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
        <div id="addContainer">

            <form method="POST" action="Controller?command=SearchProject">
                <p>
                    <label for="name">Search project by name</label>
                    <input type="text" id="name" name="name" required
                           value="<c:out value='${namePreviousValue}'/>" >
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
