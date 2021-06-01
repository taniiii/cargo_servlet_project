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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing get request");

        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("Processing post request");

        processRequest(request, response);
    }

    /**
     * This method is used to get a command instance mapped to http get/post method, based on a request.
     * and sets redirect or forward action according to result of execute() method
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/cargo", "");
        Command command = commandManager.getCommand(path);

        String page = command.execute(request, response);

        if (page.contains("redirect:")) {
            response.sendRedirect(page.replace("redirect:", "/cargo"));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
