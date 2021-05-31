package org.cargo;

import org.apache.log4j.Logger;
import org.cargo.controller.Command;
import org.cargo.controller.CommandManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class Servlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(Servlet.class);

    private CommandManager commandManager;

    public void init(ServletConfig config) throws ServletException {
        LOGGER.info("Initializing Servlet");
        commandManager = new CommandManager();

//        config.getServletContext()
//                .setAttribute("loggedUsers", new HashSet<String>());
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing get request");

        Command command = commandManager.getGetCommand(request);
        String page = command.execute(request, response);

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher(page).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing post request");

        Command command = commandManager.getPostCommand(request);
        String page = command.execute(request, response);
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        response.sendRedirect(page);
    }
}
