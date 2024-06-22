package ui.controller.actions;


import domain.model.Project;
import domain.model.Role;
import domain.model.User;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchProject extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Role[] roles = {Role.EMPLOYEE, Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        String name = request.getParameter("name");
        Project project = service.getProjectWithName(name);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedin");

        if (user.getRole() == Role.EMPLOYEE) {
            if (project != null) {
                if (project.getTeam() == user.getTeam()) {
                    request.setAttribute("project", project);
                }
            } else {
                request.setAttribute("project", null);
            }
        } else {
            request.setAttribute("project", project);
        }
        return "searchProjectResult.jsp";
    }

}
