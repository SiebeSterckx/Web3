<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Overview WorkOrders</title>
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
        <div id="OverviewWorkOrderContainer">

            <c:if test="${not empty error}">
                <div class="alert-danger">
                    <p class="error">${error}</p>
                </div>
            </c:if>

            <div id="WOTop">
                <h2>Work orders</h2>
                <form method="POST" action="Controller?command=OverviewWorkOrder">
                    <label for="sort">Sort <i class="fa">&#xf0dc;</i></label>
                    <select onchange='if(this.value != 0) { this.form.submit(); }' name="sort" id="sort">
                        <option ${sortValueWorkOrder == "workorderid" ? 'selected="Selected"' : ''} value="workorderid">Workorder ID</option>
                        <option ${sortValueWorkOrder == "name" ? 'selected="Selected"' : ''} value="name">Name</option>
                        <option ${sortValueWorkOrder == "team" ? 'selected="Selected"' : ''} value="team">Team</option>
                        <option ${sortValueWorkOrder == "date" ? 'selected="Selected"' : ''} value="date">Date</option>
                        <option ${sortValueWorkOrder == "endtime-starttime" ? 'selected="Selected"' : ''} value="endtime-starttime">Duration</option>
                    </select>
                </form>
            </div>

            <div id="OverviewWorkOrderBox">
                <c:choose>
                    <c:when test="${!empty workOrders}">
                        <c:forEach items="${workOrders}" var="workOrder">
                            <table class="${workOrder.starttime}">
                                <tr>
                                    <th>Order</th>
                                    <td><c:out value='${workOrder.workorderid}'/></td>
                                </tr>
                                <tr>
                                    <th>Employee</th>
                                    <td><c:out value='${workOrder.name}'/> (<c:out value='${workOrder.team}'/>)</td>
                                </tr>
                                <tr>
                                    <th>Date</th>
                                    <td class="dateClass"><c:out value='${workOrder.date}'/></td>
                                    <%--dateClass nodig voor testing--%>
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
                                <tr>
                                    <c:if test="${(loggedin.role == 'TEAMLEADER' && workOrder.team == loggedin.team)
                                    || (loggedin.role == 'EMPLOYEE' && workOrder.name == loggedin.firstName && loggedin.team == workOrder.team)
                                    || (loggedin.role == 'DIRECTOR')}">
                                        <td class="edit">
                                            <a href="Controller?command=EditWorkOrderPage&id=${workOrder.workorderid}">
                                                <img src="images/updateIcon.png" alt="Wijzigen">
                                            </a>
                                        </td>
                                    </c:if>
                                    <c:if test="${loggedin.role == 'DIRECTOR'}">
                                    <td class="verwijder">
                                        <a href="Controller?command=DeleteWorkOrderPage&id=${workOrder.workorderid}">
                                            <img src="images/deleteIcon.png" alt="Verwijderen">
                                        </a>
                                    </td>
                                    </c:if>
                                </tr>
                            </table>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <ul>
                            <li>
                                <p>There aren't any work orders yet!</p>
                            </li>
                            <li>
                                <p><a href="Controller?command=AddWorkOrderPage">Click here</a> to add a work order</p>
                            </li>
                        </ul>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
