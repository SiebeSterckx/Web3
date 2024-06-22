package ui.controller.actions;

import domain.model.Role;
import domain.model.User;
import domain.service.DbException;
import ui.controller.NotAuthorizedException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserPage extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException {
        Role[] roles = {Role.EMPLOYEE, Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        int id = Integer.parseInt(request.getParameter("id"));
        try {
            User user = service.getUser(id);
            request.setAttribute("user", user);
            return "editUser.jsp";
        } catch (DbException exc) {
            String error = exc.getMessage();
            request.setAttribute("error", error);
            return "Controller?command=OverviewUser";
        }
    }
}
