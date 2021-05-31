package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserProfileCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetUserProfileCommand.class);

    private static String profilePage;
    private static String loginPage;

    public GetUserProfileCommand() {
        LOGGER.debug("Initializing GetUserProfileCommand");

        MappingProperties properties = MappingProperties.getInstance();
        profilePage = properties.getProperty("profilePage");
        loginPage = properties.getProperty("loginPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send user profile page command");

        String resultPage = profilePage;

        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            return resultPage;
        } else {
            LOGGER.debug("User is not logged in, returning login form page");
            resultPage = loginPage;
            }
        return resultPage;
    }
}
