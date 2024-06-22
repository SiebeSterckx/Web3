<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Delete WorkOrder</title>
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
        <div id="deleteContainer">
            <h2>Confirmation</h2>
            <p>Are you sure you want to remove this work order?</p>

            <table>
                <thead>
                    <tr>
                        <th>Order</th>
                        <th>Employee</th>
                        <th>Date</th>
                        <th>Start time</th>
                        <th>End time</th>
                        <th>Duration</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><c:out value='${workOrder.workorderid}'/></td>
                        <td><c:out value='${workOrder.name} (${workOrder.team})'/></td>
                        <td><c:out value='${workOrder.date}'/></td>
                        <td><c:out value='${workOrder.starttime}'/></td>
                        <td><c:out value='${workOrder.endtime}'/></td>
                        <td><c:out value='${workOrder.getDuration()}'/></td>
                        <td><c:out value='${workOrder.description}'/></td>
                    </tr>
                </tbody>
            </table>

            <div id="verwijderen2">

                <form action="Controller?command=DeleteWorkOrder&id=${workOrder.workorderid}" method="post" novalidate>
                    <input id="verwijderen" type="submit" value="Yes" class="verwijderen">
                </form>
                <form action="Controller?command=OverviewWorkOrder" method="post" novalidate>
                    <input id="nietVerwijderen" type="submit" value="No" class="verwijderen">
                </form>

            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
