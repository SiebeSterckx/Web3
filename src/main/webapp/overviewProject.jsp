<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>Overview Projects</title>
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
        <div id="OverviewContainer">

            <c:if test="${not empty error}">
                <div class="alert-danger">
                    <p class="error">${error}</p>
                </div>
            </c:if>

            <div id="WOTop">
                <h2>Projects</h2>
                <form method="POST" action="Controller?command=OverviewProject">
                    <label for="sort">Sort <i class="fa">&#xf0dc;</i></label>
                    <select onchange='if(this.value != 0) { this.form.submit(); }' name="sort" id="sort">
                        <option ${sortValueProject == "projectid" ? 'selected="Selected"' : ''} value="projectid">Project ID</option>
                        <option ${sortValueProject == "name" ? 'selected="Selected"' : ''} value="name">Name</option>
                        <option ${sortValueProject == "team" ? 'selected="Selected"' : ''} value="team">Team</option>
                        <option ${sortValueProject == "startdate" ? 'selected="Selected"' : ''} value="startdate">Start date</option>
                        <option ${sortValueProject == "enddate" ? 'selected="Selected"' : ''} value="enddate">End date</option>
                    </select>
                </form>
            </div>

            <c:choose>
                <c:when test="${!empty projects}">
                    <table>
                        <thead>
                            <tr>
                                <th>ProjectId</th>
                                <th>Name</th>
                                <th>Team</th>
                                <th>Start</th>
                                <th>End</th>
                                <c:if test="${loggedin.role == 'DIRECTOR'}">
                                    <div>
                                        <th>Edit</th>
                                        <th>Delete</th>
                                    </div>
                                </c:if>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${projects}" var="project">
                            <tr class="${project.name}">
                                <td><c:out value='${project.projectid}'/></td>
                                <td><c:out value='${project.name}'/></td>
                                <td><c:out value='${project.team}'/></td>
                                <td><c:out value='${project.startdate}'/></td>
                                <td><c:out value='${project.enddate}'/></td>
                                <c:if test="${loggedin.role == 'DIRECTOR'}">
                                    <td class="edit">
                                        <a href="Controller?command=EditProjectPage&id=${project.projectid}">
                                            <img src="images/updateIcon.png" alt="Wijzigen">
                                        </a>
                                    </td>
                                    <td class="verwijder">
                                        <a href="Controller?command=DeleteProjectPage&id=${project.projectid}">
                                            <img src="images/deleteIcon.png" alt="Verwijderen">
                                        </a>
                                    </td>
                                </c:if>
                                </div>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>

                <c:otherwise>
                    <ul>
                        <li>
                            <p>There aren't any projects yet!</p>
                        </li>
                        <li>
                            <p><a href="Controller?command=AddProjectPage">Click here</a> to add a project</p>
                        </li>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
