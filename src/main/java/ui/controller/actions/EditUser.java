package ui.controller.actions;

import domain.model.Role;
import domain.model.User;
import domain.service.DbException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class EditUser extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Role[] roles = {Role.EMPLOYEE, Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        ArrayList<String> errors2 = new ArrayList<>();
        int id= Integer.parseInt(request.getParameter("id"));
        User user = service.getUser(id);

        checkUser(user, request, errors2);

        if (errors2.size() == 0) {
            try {
                service.update(user);
                response.sendRedirect("Controller?command=OverviewUser");
                return "Controller?command=OverviewUser";
            } catch (DbException exc) {
                errors2.add(exc.getMessage());
            }
        }
        request.setAttribute("errors2", errors2);
        return "Controller?command=EditUserPage&id=" + id;

    }

    public void checkUser(User user, HttpServletRequest request, ArrayList<String> errors2) {
        int id = Integer.parseInt(request.getParameter("id"));
        String email = request.getParameter("email");

        for (User u : service.getAllUsers()) {
            if (u.getUserid() != id && u.getEmail().equals(email)) {
                errors2.add("Email already exists");
            }
        }
        if (errors2.size() == 0) {
            setName(user, request, errors2);
            setFirstName(user, request, errors2);
            setEmail(user, request, errors2);
            setTeam(user, request, errors2);
            setRole(user, request, errors2);
        }
    }

    private void setName(User user, HttpServletRequest request, ArrayList<String> errors2) {
        String name = request.getParameter("name");
        boolean nameHasErrors = false;

        try {
            request.setAttribute("namePreviousValue", name);
            user.setLastName(name);
        } catch (DbException exc) {
            errors2.add(exc.getMessage());
            nameHasErrors = true;
        } finally {
            request.setAttribute("nameHasErrors", nameHasErrors);
        }
    }

    private void setFirstName(User user, HttpServletRequest request, ArrayList<String> errors2) {
        String firstName = request.getParameter("firstName");
        boolean firstNameHasErrors = false;

        try {
            request.setAttribute("firstNamePreviousValue", firstName);
            user.setFirstName(firstName);
        } catch (DbException exc) {
            errors2.add(exc.getMessage());
            firstNameHasErrors = true;
        } finally {
            request.setAttribute("firstNameHasErrors", firstNameHasErrors);
        }
    }

    private void setEmail(User user, HttpServletRequest request, ArrayList<String> errors2) {
        String email = request.getParameter("email");
        email = email.toLowerCase();
        boolean emailHasErrors = false;

        try {
            request.setAttribute("emailPreviousValue", email);
            user.setEmail(email);
        } catch (DbException exc) {
            errors2.add(exc.getMessage());
            emailHasErrors = true;
        } finally {
            request.setAttribute("emailHasErrors", emailHasErrors);
        }
    }

    private void setTeam(User user, HttpServletRequest request, ArrayList<String> errors2) {
        String team = request.getParameter("team");
        boolean teamHasErrors = false;

        try {
            if (team != null) {
                request.setAttribute("teamPreviousValue", team);
                user.setTeam(team);
            }
        } catch (DbException exc) {
            errors2.add(exc.getMessage());
            teamHasErrors = true;
        } finally {
            request.setAttribute("teamHasErrors", teamHasErrors);
        }
    }

    private void setRole(User user, HttpServletRequest request, ArrayList<String> errors2) {
            String role = request.getParameter("role");
            boolean roleHasErrors = false;

            try {
                if (role != null) {
                    request.setAttribute("rolePreviousValue", role);
                    if (role.equals("DIRECTOR")) {
                        user.setRole(Role.DIRECTOR);
                    }
                    if (role.equals("TEAMLEADER")) {
                        user.setRole(Role.TEAMLEADER);
                    }
                    if (role.equals("EMPLOYEE")) {
                        user.setRole(Role.EMPLOYEE);
                    }
                }
            } catch (DbException exc) {
                errors2.add(exc.getMessage());
                roleHasErrors = true;
            } finally {
                request.setAttribute("roleHasErrors", roleHasErrors);
            }
    }
}
