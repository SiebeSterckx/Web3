package ui.controller.actions;

import domain.model.User;
import ui.controller.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Index extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if (session.getAttribute("loggedin") != null) {
            User user = (User) session.getAttribute("loggedin");
            request.setAttribute("user", user);
        }
        return "index.jsp";

    }
}
