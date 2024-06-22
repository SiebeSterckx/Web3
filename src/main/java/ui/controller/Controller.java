package ui.controller;

import domain.service.AppService;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AppService service = new AppService();
    private HandlerFactory handlerFactory = new HandlerFactory();

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destination;
        String command = request.getParameter("command");

        if (command == null) {
            command = "Index";
        }
        RequestHandler handler = handlerFactory.getHandler(command, service);

        try {
            destination = handler.handleRequest(request, response);
        } catch (NotAuthorizedException e) {
            // alle handlers gooien een NotAuthorizedException als gebruiker niet de juiste rechten heeft
            // zodat authorization altijd op dezelfde manier afgehandeld wordt
            request.setAttribute("error", "You have insufficient rights to have a look at the requested page.");
            destination = "Controller?command=Index";
        }
        // server kan maar één http response sturen
        // als er al een sendRedirect geweest is, mag er geen forward meer zijn
        if (!response.isCommitted()) {
            RequestDispatcher view = request.getRequestDispatcher(destination);
            view.forward(request, response);
        }
    }

}