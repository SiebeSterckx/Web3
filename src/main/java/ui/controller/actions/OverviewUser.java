package ui.controller.actions;

import ui.controller.RequestHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OverviewUser extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", service.getAllUsers());
        HttpSession session = request.getSession();
        if (session.getAttribute("loggedin") != null) {
            request.setAttribute("loggedin", session.getAttribute("loggedin"));
        }
        return "overviewUser.jsp";
    }
}
