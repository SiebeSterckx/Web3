package ui.controller.actions;

import domain.model.Role;
import domain.model.User;
import domain.service.DbException;
import ui.controller.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AddUser extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<String> errors = new ArrayList<>();

        User user = new User();
        setName(user, request, errors);
        setFirstName(user, request, errors);
        setEmail(user, request, errors);
        setPassword(user, request, errors);
        setTeam(user, request, errors);
        user.setRole(Role.EMPLOYEE);

        if (errors.size() == 0) {
            try {
                getService().add(user);
                response.sendRedirect("Controller?command=OverviewUser");
                return "Controller?command=OverviewUser";
            } catch (DbException exc) {
                errors.add(exc.getMessage());
            }
        }
        request.setAttribute("errors", errors);
        return "addUser.jsp";
    }

    private void setName(User user, HttpServletRequest request, ArrayList<String> errors) {
        String name = request.getParameter("name");
        boolean nameHasErrors = false;

        try {
            request.setAttribute("namePreviousValue", name);
            user.setLastName(name);
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            nameHasErrors = true;
        } finally {
            request.setAttribute("nameHasErrors", nameHasErrors);
        }
    }

    private void setFirstName(User user, HttpServletRequest request, ArrayList<String> errors) {
        String firstName = request.getParameter("firstName");
        boolean firstNameHasErrors = false;

        try {
            request.setAttribute("firstNamePreviousValue", firstName);
            user.setFirstName(firstName);
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            firstNameHasErrors = true;
        } finally {
            request.setAttribute("firstNameHasErrors", firstNameHasErrors);
        }
    }

    private void setEmail(User user, HttpServletRequest request, ArrayList<String> errors) {
        String email = request.getParameter("email");
        email = email.toLowerCase();
        boolean emailHasErrors = false;

        try {
            request.setAttribute("emailPreviousValue", email);
            user.setEmail(email);
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            emailHasErrors = true;
        } finally {
            request.setAttribute("emailHasErrors", emailHasErrors);
        }
    }

    private void setPassword(User user, HttpServletRequest request, ArrayList<String> errors) {
        String password = request.getParameter("password");
        boolean passwordHasErrors = false;

        try {
            request.setAttribute("passwordPreviousValue", password);
            user.setPassword(password);
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            passwordHasErrors = true;
        } finally {
            request.setAttribute("passwordHasErrors", passwordHasErrors);
        }
    }

    private void setTeam(User user, HttpServletRequest request, ArrayList<String> errors) {
        String team = request.getParameter("team");
        boolean teamHasErrors = false;

        try {
            request.setAttribute("teamPreviousValue", team);
            user.setTeam(team);
        } catch (DbException exc) {
            errors.add(exc.getMessage());
            teamHasErrors = true;
        } finally {
            request.setAttribute("teamHasErrors", teamHasErrors);
        }
    }
}
