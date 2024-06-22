package ui.controller.actions;

import domain.model.Project;
import domain.model.Role;
import domain.model.User;
import domain.model.WorkOrder;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SearchWorkOrder extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Role[] roles = {Role.EMPLOYEE, Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedin");
        String date = request.getParameter("date");
        List<WorkOrder> workOrders = service.getWorkOrderWithDate(date);
        if (user.getRole() == Role.EMPLOYEE) {
            for (WorkOrder w : workOrders) {
                if (w.getTeam() != user.getTeam()) {
                    workOrders.remove(w);
                }
            }
        }
        request.setAttribute("workOrders", workOrders);
        return "searchWorkOrderResult.jsp";
    }
}
