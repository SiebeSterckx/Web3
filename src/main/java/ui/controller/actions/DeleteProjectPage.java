package ui.controller.actions;

import domain.model.Project;
import domain.model.Role;
import domain.service.DbException;
import ui.controller.NotAuthorizedException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProjectPage extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException {
        Role[] roles = {Role.DIRECTOR};
        Utility.checkRole(request, roles);
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Project project = service.getProject(id);
            request.setAttribute("project", project);
            return "deleteProject.jsp";
        } catch (DbException exc) {
            String error = exc.getMessage();
            request.setAttribute("error", error);
            return "Controller?command=OverviewProject";
        }

    }
}
