package ui.controller.actions;

import domain.model.Role;
import domain.model.WorkOrder;
import domain.service.DbException;
import ui.controller.NotAuthorizedException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteWorkOrderPage extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException {
        Role[] roles = {Role.DIRECTOR};
        Utility.checkRole(request, roles);

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            WorkOrder workOrder = service.getWorkOrder(id);
            request.setAttribute("workOrder", workOrder);
            return "deleteWorkOrder.jsp";
        } catch (DbException exc) {
            String error = exc.getMessage();
            request.setAttribute("error", error);
            return "Controller?command=OverviewWorkOrder";
        }

    }
}
