package ui.controller.actions;

import domain.model.Role;
import domain.model.Team;
import domain.model.User;
import ui.controller.NotAuthorizedException;
import ui.controller.RequestHandler;
import ui.controller.Utility;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OverviewProject extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws NotAuthorizedException {
        Role[] roles = {Role.EMPLOYEE, Role.TEAMLEADER, Role.DIRECTOR};
        Utility.checkRole(request, roles);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loggedin");

        String sort = request.getParameter("sort");
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            setNew(request, response, user);
            return "overviewProject.jsp";
        }
        for (Cookie c : cookies) {
            if (c.getName().equals("sortValueProject")) {
                setSort(sort, request, response, c, user);
                return "overviewProject.jsp";
            }
        }
        setNew(request, response, user);
        return "overviewProject.jsp";
    }


    private void setSort(String sort, HttpServletRequest request, HttpServletResponse response, Cookie c, User user) {
        if (sort != null) {
            if (c.getValue().equals(sort)) {
                request.setAttribute("sortValueProject", sort);
                if (!user.getRole().equals(Role.EMPLOYEE)) {
                    request.setAttribute("projects", service.getAllProjects(sort));
                } else {
                    request.setAttribute("projects", service.getAllProjects(sort, user.getTeam()));
                }
            }
            else {
                c.setValue(sort);
                response.addCookie(c);
                request.setAttribute("sortValueProject", sort);
                if (!user.getRole().equals(Role.EMPLOYEE)) {
                    request.setAttribute("projects", service.getAllProjects(sort));
                } else {
                    request.setAttribute("projects", service.getAllProjects(sort, user.getTeam()));
                }
            }
        } else {
            request.setAttribute("sortValueProject", c.getValue());
            if (!user.getRole().equals(Role.EMPLOYEE)) {
                request.setAttribute("projects", service.getAllProjects(c.getValue()));
            } else {
                request.setAttribute("projects", service.getAllProjects(c.getValue(), user.getTeam()));
            }
        }
    }

    private void setNew(HttpServletRequest request, HttpServletResponse response, User user) {
        Cookie cookie = new Cookie("sortValueProject", "projectid");
        cookie.setMaxAge(((60*60)*24)); // 1 dag geldig
        response.addCookie(cookie);
        request.setAttribute("sortValueProject", "projectid");
        if (!user.getRole().equals(Role.EMPLOYEE)) {
            request.setAttribute("projects", service.getAllProjects("projectid"));
        } else {
            request.setAttribute("projects", service.getAllProjects("projectid", user.getTeam()));
        }
    }
}
