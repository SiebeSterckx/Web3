package ui.controller.actions;

import domain.model.Role;
import domain.model.User;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteUser extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Role[] roles = {Role.DIRECTOR};
        Utility.checkRole(request, roles);

        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        User user = service.getUser(id);

        if (session.getAttribute("loggedin") != null) {
            User u = (User) session.getAttribute("loggedin");
            if (user.getEmail().equals(u.getEmail())) {
                session.removeAttribute("loggedin");
            }
        }

        service.deleteUser(id);

        response.sendRedirect("Controller?command=OverviewUser");
        return "Controller?command=OverviewUser";
    }
}
