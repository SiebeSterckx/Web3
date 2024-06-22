package ui.controller.actions;

import domain.model.Role;
import domain.model.Team;
import domain.model.User;
import domain.model.WorkOrder;
import domain.service.DbException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class AddWorkOrder extends RequestHandler {
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Role[] roles = {Role.EMPLOYEE, Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        ArrayList<String> errors = new ArrayList<>();

        WorkOrder workOrder = new WorkOrder();
        setDate(workOrder, request, errors);
        setStartTime(workOrder, request, errors);
        setEndTime(workOrder, request, errors);
        setDescription(workOrder, request, errors);
        setUserName(workOrder, request, errors);
        setTeam(workOrder, request, errors);

        if (errors.size() == 0) {
            try {
                getService().add(workOrder);
                response.sendRedirect("Controller?command=OverviewWorkOrder");
                return "Controller?command=OverviewWorkOrder";
            } catch (DbException exc) {
                errors.add(exc.getMessage());
            }
        }
        request.setAttribute("errors", errors);
        return "addWorkOrder.jsp";
    }

    private void setTeam(WorkOrder workOrder, HttpServletRequest request, ArrayList<String> errors) {
        HttpSession session = request.getSession();

        String team = request.getParameter("team");

        boolean teamHasErrors = false;

        try {
            if (session.getAttribute("loggedin") != null) {
                User user = (User) session.getAttribute("loggedin");
                workOrder.setTeam(user.getTeam());
            } else {
                workOrder.setTeam(Team.ALPHA);
            }
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            teamHasErrors = true;
        } finally {
            request.setAttribute("teamHasErrors", teamHasErrors);
        }
    }

    private void setUserName(WorkOrder workOrder, HttpServletRequest request, ArrayList<String> errors) {
        HttpSession session = request.getSession();

        String name = request.getParameter("name");

        boolean nameHasErrors = false;

        try {
            if (session.getAttribute("loggedin") != null) {
                User user = (User) session.getAttribute("loggedin");
                workOrder.setName(user.getFirstName());
            } else {
                workOrder.setName("Unknown");
            }
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            nameHasErrors = true;
        } finally {
            request.setAttribute("nameHasErrors", nameHasErrors);
        }
    }

    private void setDate(WorkOrder workOrder, HttpServletRequest request, ArrayList<String> errors) {
        String datum = request.getParameter("date");
        boolean dateHasErrors = false;

        try {
            if (!datum.isEmpty()) {
                request.setAttribute("datePreviousValue", datum);
                LocalDate date = LocalDate.parse(datum);
                workOrder.setDate(date);
            }
            else {
                errors.add("No date given");
            }
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            dateHasErrors = true;
        } finally {
            request.setAttribute("dateHasErrors", dateHasErrors);
        }
    }

    private void setStartTime(WorkOrder workOrder, HttpServletRequest request, ArrayList<String> errors) {
        String starttijd = request.getParameter("startTime");
        boolean startTimeHasErrors = false;

        try {
            if (!starttijd.isEmpty()) {
                request.setAttribute("startTimePreviousValue", starttijd);
                LocalTime starttime = LocalTime.parse(starttijd);
                workOrder.setStarttime(starttime);
            }
            else {
                errors.add("No starttime given");
            }
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            startTimeHasErrors = true;
        } finally {
            request.setAttribute("startTimeHasErrors", startTimeHasErrors);
        }
    }

    private void setEndTime(WorkOrder workOrder, HttpServletRequest request, ArrayList<String> errors) {
        String eindtijd = request.getParameter("endTime");
        boolean endTimeHasErrors = false;

        try {
            if (!eindtijd.isEmpty()) {
                request.setAttribute("endTimePreviousValue", eindtijd);
                LocalTime endtime = LocalTime.parse(eindtijd);
                workOrder.setEndtime(endtime);
            }
            else {
                errors.add("No endtime given");
            }
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            endTimeHasErrors = true;
        } finally {
            request.setAttribute("endTimeHasErrors", endTimeHasErrors);
        }
    }

    private void setDescription(WorkOrder workOrder, HttpServletRequest request, ArrayList<String> errors) {
        String description = request.getParameter("description");
        boolean descriptionHasErrors = false;

        try {
            request.setAttribute("descriptionPreviousValue", description);
            workOrder.setDescription(description);
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            descriptionHasErrors = true;
        } finally {
            request.setAttribute("descriptionHasErrors", descriptionHasErrors);
        }
    }

}
