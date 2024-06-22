package ui.controller.actions;

import domain.model.Role;
import domain.model.WorkOrder;
import domain.service.DbException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EditWorkOrder extends RequestHandler {

    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Role[] roles = {Role.EMPLOYEE, Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        int id = Integer.parseInt(request.getParameter("id"));
        ArrayList<String> errors = new ArrayList<>();

        WorkOrder workOrder = service.getWorkOrder(id);
        setStartTime(workOrder, request, errors);
        setEndTime(workOrder, request, errors);
        setDescription(workOrder, request, errors);

        if (errors.size() == 0) {
            try {
                getService().update(workOrder);
                response.sendRedirect("Controller?command=OverviewWorkOrder");
                return "Controller?command=OverviewWorkOrder";
            } catch (DbException exc) {
                errors.add(exc.getMessage());
            }
        }
        request.setAttribute("errors", errors);
        return "Controller?command=EditWorkOrderPage&id=" + id;
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
                request.setAttribute("startTimePreviousValue", eindtijd);
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
