package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRegistrationPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetRegistrationPageCommand.class);

    private static String registrationPage;
    private static String homePage;

    public GetRegistrationPageCommand() {
      LOGGER.debug("Initializing GetRegistrationPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        registrationPage = properties.getProperty("registerPage");
        homePage = properties.getProperty("homepage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send registration page command");

        String resultPage = registrationPage;

        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = homePage;
        } else if (request.getParameter("username") == null || request.getParameter("password") == null ||
                request.getParameter("email") == null) {
            //request.setAttribute("msg", "Please fill in all the fields");  можно сделать error message
            LOGGER.debug("User is not registered yet, returning registration form page");
            return resultPage;
        }
        return resultPage;
    }
}
