package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class is used to handle GET requests to the login page. Sends login page.
 */
public class GetLoginPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetLoginPageCommand.class);

    private static String loginPage;
    private static String homePage;

    public GetLoginPageCommand() {
        LOGGER.debug("Initializing GetLoginPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        homePage = properties.getProperty("homepage");
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute sending login page command");

        String resultPage = loginPage;

        if (request.getSession().getAttribute("authenticated") != null && //заменить  на request.getRemoteUser() и session user!= null
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = homePage;
        } else if (request.getParameter("username") == null || request.getParameter("password") == null) { //or isEmpty()
            LOGGER.debug("Returning login page");
            return resultPage;
        }

        return resultPage;
    }

}
