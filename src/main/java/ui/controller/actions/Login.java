package ui.controller.actions;

import domain.model.User;
import domain.service.DbException;
import ui.controller.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response)  {

        String error = "No valid email/password";

        String email = request.getParameter("email");
        email = email.toLowerCase();
        String password = request.getParameter("password");

        try {
            User u = getService().getUserWithEmail(email);
            if (u.getEmail().equals(email) && u.isCorrectPassword(password)) {
                request.getSession().setAttribute("loggedin", u);
                return "Controller?command=Index";
            }
        } catch (DbException e) {
            request.setAttribute("error", error);
            return "Controller?command=Index";
        }
        request.setAttribute("error", error);
        return "Controller?command=Index";
    }
}
