package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.User;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * This class is used to handle GET and POST requests to the login page. Sends login page.
 */
public class GetLoginPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetLoginPageCommand.class);

    private static UserService userService = UserService.getInstance();

    private static String loginPage;
    private static String homePage;
    private static String accessErrorPage;
    private static String errorPage;

    public GetLoginPageCommand() {
        LOGGER.debug("Initializing GetLoginPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        homePage = properties.getProperty("redirect.home");
        accessErrorPage = properties.getProperty("errorAccessPage");
        errorPage = properties.getProperty("errorPage");
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute login page command");

        if (Objects.isNull(request.getParameter("username")) || Objects.isNull(request.getParameter("password"))) {
            LOGGER.debug("Returning login page");
            request.setAttribute("msgInfo", "Please fill in all the fields of the form");
            return loginPage;
        }

        if (CommandUtility.checkUserIsLogged(request, request.getParameter("username"))) {
            return accessErrorPage;
        }
        try {
            User user = userService.getUserByCredentials(request.getParameter("username"),
                    request.getParameter("password"));

            if (Objects.nonNull(user)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userName", user.getUsername());
                session.setAttribute("authenticated", true);
                session.setAttribute("role", user.getUserRole().name());
                request.setAttribute("loginSuccess", true);
                return homePage;
            } else {
                request.setAttribute("loginSuccess", false);
                return loginPage;
            }
        } catch (DaoException e) {
            return errorPage;
        }
    }
}
