package ui.controller;

import domain.model.Role;
import domain.model.User;

import javax.servlet.http.HttpServletRequest;

public class Utility {
    /**
     * Checks if the role of the user stored in request.getSession() has one of the given roles
     * @param request The request object to get user in session attribute
     * @param roles Array of alowed roles for the given request
     * @throws NotAuthorizedException if the role of the user in session does not correspond with one of the given roles
     */
    public static void checkRole(HttpServletRequest request, Role[] roles) {
        // read user from session
        // if users role is different from given roles
        //      throw NotAuthorizedException
        boolean found = false;
        User user = (User) request.getSession().getAttribute("loggedin");
        if (user == null) {
            throw new NotAuthorizedException("You are not logged in");
        }
        if (user != null)
            for (Role role : roles) {
                if (user.getRole().equals(role)) {
                    found = true;
                    break;
                }
            }
        if (!found)
            throw new NotAuthorizedException();

    }

}
