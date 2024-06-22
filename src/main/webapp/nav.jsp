<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<nav>

    <button onclick="location.href='Controller?command=Index'" type="button" class="dropbtn" ${param.actual eq 'index'?"id=actual":""}>
      <i class="fa fa-home"></i> Home
    </button>

  <div class="dropdown">
    <button class="dropbtn">Users <i class='fas'>&#xf0d7;</i></button>
    <div class="dropdown-content">
      <a ${param.actual eq 'userOverview'?"id=actual":""} href="Controller?command=OverviewUser">Overview</a>
      <a ${param.actual eq 'addUser'?"id=actual":""} href="Controller?command=AddUserPage">Register</a>
    </div>
  </div>

  <c:if test="${loggedin.role=='EMPLOYEE' || loggedin.role=='TEAMLEADER' || loggedin.role=='DIRECTOR'}">
    <div class="dropdown">
      <button class="dropbtn">Projects <i class='fas'>&#xf0d7;</i></button>
      <div class="dropdown-content">
        <a ${param.actual eq 'projectOverview'?"id=actual":""} href="Controller?command=OverviewProject">Overview</a>
        <c:if test="${loggedin.role != 'EMPLOYEE'}">
          <a ${param.actual eq 'addProject'?"id=actual":""} href="Controller?command=AddProjectPage">Add</a>
        </c:if>
        <a ${param.actual eq 'searchProject'?"id=actual":""} href="Controller?command=SearchProjectPage">Search</a>
      </div>
    </div>

    <div class="dropdown">
      <button class="dropbtn">Workorders <i class='fas'>&#xf0d7;</i></button>
      <div class="dropdown-content">
        <a ${param.actual eq 'workOrderOverview'?"id=actual":""} href="Controller?command=OverviewWorkOrder">Overview</a>
        <a ${param.actual eq 'addWorkOrder'?"id=actual":""} href="Controller?command=AddWorkOrderPage">Add</a>
        <a ${param.actual eq 'searchWorkOrder'?"id=actual":""} href="Controller?command=SearchWorkOrderPage">Search</a>
      </div>
    </div>
  </c:if>

</nav>


