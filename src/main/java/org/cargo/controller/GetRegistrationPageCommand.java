package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.bean.user.UserBuilder;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static org.cargo.service.Validator.validateRegistration;

public class GetRegistrationPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetRegistrationPageCommand.class);

    private static UserService userService = UserService.getInstance();

    private static String registrationPage;
    private static String loginPage;
    private static String errorPage;

    public GetRegistrationPageCommand() {
        LOGGER.debug("Initializing GetRegistrationPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        registrationPage = properties.getProperty("registerPage");
        loginPage = properties.getProperty("redirect.login");
        errorPage = properties.getProperty("errorPage");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing registration page command");

        //String resultPage = registrationPage;

        if (!validateRegistration(request.getParameter("username"), request.getParameter("password"),
                request.getParameter("email"))) {

            LOGGER.debug("Bad credentials, returning registration form page");
            request.setAttribute("msg", " ");
            return registrationPage;
        }

        try {
            if (Objects.nonNull(userService.findUserByUsername(request.getParameter("username")))) {
                request.setAttribute("msgExists", " ");
                return registrationPage;
            }

            User user = new UserBuilder().setUsername(request.getParameter("username"))
                    .setPassword(request.getParameter("password"))
                    .setEmail(request.getParameter("email"))
                    .setUserRole(Role.USER)
                    .build();

            userService.registerUser(user);

        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }

        LOGGER.debug("User registered successfully");
        request.getSession().setAttribute("msgSuccess", "You have been registered successfully");

        return loginPage;
    }
}
