package ui.controller.actions;

import domain.model.Project;
import domain.model.Role;
import domain.model.Team;
import domain.model.User;
import domain.service.DbException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddProject extends RequestHandler  {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Role[] roles = {Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        ArrayList<String> errors = new ArrayList<>();

        Project project = new Project();
        setName(project, request, errors);
        setTeam(project, request, errors);
        setStartDate(project, request, errors);
        setEndDate(project, request, errors);

        if (errors.size() == 0) {
            try {
                getService().add(project);
                response.sendRedirect("Controller?command=OverviewProject");
                return "Controller?command=OverviewProject";
            } catch (DbException exc) {
                errors.add(exc.getMessage());
            }
        }
        request.setAttribute("errors", errors);
        return "addProject.jsp";
    }
    private void setName(Project project, HttpServletRequest request, ArrayList<String> errors) {
        String name = request.getParameter("name");
        boolean nameHasErrors = false;

        try {
            request.setAttribute("namePreviousValue", name);
            project.setName(name);
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            nameHasErrors = true;
        } finally {
            request.setAttribute("nameHasErrors", nameHasErrors);
        }
    }

    private void setTeam(Project project, HttpServletRequest request, ArrayList<String> errors) {
        HttpSession session = request.getSession();

        String team = request.getParameter("team");

        boolean teamHasErrors = false;

        try {
            if (session.getAttribute("loggedin") != null) {
                User user = (User) session.getAttribute("loggedin");
                project.setTeam(user.getTeam());
            } else {
                project.setTeam(Team.ALPHA);
            }
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            teamHasErrors = true;
        } finally {
            request.setAttribute("teamHasErrors", teamHasErrors);
        }
    }

    private void setStartDate(Project project, HttpServletRequest request, ArrayList<String> errors) {
        String startDate = request.getParameter("startDate");
        boolean startDateHasErrors = false;

        try {
            if (!startDate.isEmpty() || startDate == null) {
                LocalDate date = LocalDate.parse(startDate);
                request.setAttribute("startDatePreviousValue", date);
                project.setStartdate(date);
            } else {
                errors.add("Start date can't be empty");
            }
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            startDateHasErrors = true;
        } finally {
            request.setAttribute("startDateHasErrors", startDateHasErrors);
        }
    }

    private void setEndDate(Project project, HttpServletRequest request, ArrayList<String> errors) {
        String endDate = request.getParameter("endDate");
        boolean endDateHasErrors = false;

        try {
            if (!endDate.isEmpty()) {
                LocalDate date = LocalDate.parse(endDate);
                request.setAttribute("endDatePreviousValue", date);
                project.setEnddate(date);
            } else {
                project.setEnddate(null);
            }
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            endDateHasErrors = true;
        } finally {
            request.setAttribute("endDateHasErrors", endDateHasErrors);
        }
    }
}
