package ui.controller.actions;

import domain.model.Role;
import ui.controller.NotAuthorizedException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchProjectPage extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException {
        Role[] roles = {Role.EMPLOYEE, Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        return "searchProject.jsp";
    }

}
