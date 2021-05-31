package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);
    private static String loginPage;

    public LogoutCommand() {
       LOGGER.debug("Initializing Logout command");

       MappingProperties properties = MappingProperties.getInstance();
       loginPage = properties.getProperty("redirect.login");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Logging out user " + request.getRemoteUser());
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("user", null);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//??????????
            session.invalidate();
            request.setAttribute("msgInfo", "You have been logged out");
        }

        return loginPage;    //"/cargo/login";
    }
}
