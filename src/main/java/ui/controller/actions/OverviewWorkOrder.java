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

public class OverviewWorkOrder extends RequestHandler {

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
            request.setAttribute("loggedin", session.getAttribute("loggedin"));
            return "overviewWorkOrder.jsp";
        }
        for (Cookie c : cookies) {
            if (c.getName().equals("sortValueWorkOrder")) {
                setSort(sort, request, response, c, user);
                request.setAttribute("loggedin", session.getAttribute("loggedin"));
                return "overviewWorkOrder.jsp";
            }
        }
        setNew(request, response, user);
        request.setAttribute("loggedin", session.getAttribute("loggedin"));
        return "overviewWorkOrder.jsp";
    }

    private void setSort(String sort, HttpServletRequest request, HttpServletResponse response, Cookie c, User user) {
        if (sort != null) {
            if (c.getValue().equals(sort)) {
                request.setAttribute("sortValueWorkOrder", sort);
                if (!user.getRole().equals(Role.DIRECTOR)) {
                    request.setAttribute("workOrders", service.getAllWorkOrders(sort, user.getTeam()));
                } else {
                    request.setAttribute("workOrders", service.getAllWorkOrders(sort));
                }
            }
            else {
                c.setValue(sort);
                response.addCookie(c);
                request.setAttribute("sortValueWorkOrder", sort);
                if (!user.getRole().equals(Role.DIRECTOR)) {
                    request.setAttribute("workOrders", service.getAllWorkOrders(sort, user.getTeam()));
                } else {
                    request.setAttribute("workOrders", service.getAllWorkOrders(sort));
                }
            }
        } else {
            request.setAttribute("sortValueWorkOrder", c.getValue());
            if (!user.getRole().equals(Role.DIRECTOR)) {
                request.setAttribute("workOrders", service.getAllWorkOrders(c.getValue(), user.getTeam()));
            } else {
                request.setAttribute("workOrders", service.getAllWorkOrders(c.getValue()));
            }
        }
    }

    private void setNew(HttpServletRequest request, HttpServletResponse response, User user) {
        Cookie cookie = new Cookie("sortValueWorkOrder", "workorderid");
        cookie.setMaxAge(((60*60)*24)); // 1 dag geldig
        response.addCookie(cookie);
        request.setAttribute("sortValueWorkOrder", "workorderid");
        if (!user.getRole().equals(Role.DIRECTOR)) {
            request.setAttribute("workOrders", service.getAllWorkOrders("workorderid", user.getTeam()));
        } else {
            request.setAttribute("workOrders", service.getAllWorkOrders("workorderid"));
        }
    }
}


