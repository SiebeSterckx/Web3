<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Search WorkOrder Result</title>
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
        <div id="OverviewWorkOrderContainer">
            <div id="WOTop">
                <h2>Search workorder result</h2>
            </div>

        <div id="OverviewWorkOrderBox">
            <c:choose>
                <c:when test="${!empty workOrders}">
                    <c:forEach items="${workOrders}" var="workOrder">
                        <table>
                            <tr>
                                <th>Order</th>
                                <td><c:out value='${workOrder.workorderid}'/></td>
                            </tr>
                            <tr>
                                <th>Employee</th>
                                <td><c:out value='${workOrder.name} (${workOrder.team})'/></td>
                            </tr>
                            <tr>
                                <th>Date</th>
                                <td><c:out value='${workOrder.date}'/></td>
                            </tr>
                            <tr>
                                <th>Time</th>
                                <td>From <c:out value='${workOrder.starttime}'/> till <c:out value='${workOrder.endtime}'/></td>
                            </tr>
                            <tr>
                                <th>Duration</th>
                                <td><c:out value='${workOrder.getDuration()}'/></td>
                            </tr>
                            <tr>
                                <th>Description</th>
                                <td class="descriptionClass"><c:out value='${workOrder.description}'/></td>
                            </tr>
                        </table>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <ul>
                        <li>
                            <p>There aren't any work orders on this date!</p>
                        </li>
                        <li>
                            <p><a href="Controller?command=SearchWorkOrderPage">Click here</a> to try again and search by another date</p>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>

        </div>
        </div>
    </main>

    <jsp:include page="footer.jsp"/>

</body>
</html>


