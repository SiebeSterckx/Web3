package ui.controller.actions;

import domain.model.Project;
import domain.model.Role;
import domain.service.DbException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EditProject extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Role[] roles = {Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        ArrayList<String> errors2 = new ArrayList<>();
        int id= Integer.parseInt(request.getParameter("id"));
        Project project = service.getProject(id);

        setStartDate(project, request, errors2);
        setEndDate(project, request, errors2);

        if (errors2.size() == 0) {
            try {
                service.update(project);
                response.sendRedirect("Controller?command=OverviewProject");
                return "Controller?command=OverviewProject";
            } catch (DbException exc) {
                errors2.add(exc.getMessage());
            }
        }
        request.setAttribute("errors2", errors2);
        return "Controller?command=EditProjectPage&id=" + id;

    }

    private void setStartDate(Project project, HttpServletRequest request, ArrayList<String> errors2) {
        String startDate = request.getParameter("startDate");
        boolean startDateHasErrors = false;

        try {
            if (!startDate.isEmpty()) {
                LocalDate date = LocalDate.parse(startDate);
                request.setAttribute("startDatePreviousValue", date);
                project.setStartdate(date);
            } else {
                errors2.add("Start date can't be empty");
            }
        } catch (DbException exc) {
            errors2.add(exc.getMessage());
            startDateHasErrors = true;
        } finally {
            request.setAttribute("startDateHasErrors", startDateHasErrors);
        }
    }

    private void setEndDate(Project project, HttpServletRequest request, ArrayList<String> errors2) {
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
            errors2.add(exc.getMessage());
            endDateHasErrors = true;
        } finally {
            request.setAttribute("endDateHasErrors", endDateHasErrors);
        }
    }



}
