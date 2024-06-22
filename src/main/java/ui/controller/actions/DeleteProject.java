package ui.controller.actions;

import domain.model.Role;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProject extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Role[] roles = {Role.DIRECTOR};
        Utility.checkRole(request, roles);

        int id = Integer.parseInt(request.getParameter("id"));

        service.deleteProject(id);

        response.sendRedirect("Controller?command=OverviewProject");
        return "Controller?command=OverviewProject";
    }
}
