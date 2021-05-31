package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.bean.user.UserBuilder;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class handles POST requests to register user. Provides password encryption
 */
public class RegistrationCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    private static UserService userService = UserService.getInstance();

    private static String registerPage;
    private static String homePage;
    private static String loginPage;

    public RegistrationCommand() {
        LOGGER.debug("Initializing Registration");

        MappingProperties properties = MappingProperties.getInstance();
        registerPage = properties.getProperty("registerPage");
        homePage = properties.getProperty("homepage");
        loginPage = properties.getProperty("redirect.login");
    }

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing Registration command");

        String resultPage = registerPage;
//лучше сделать валидацию - добавить проверку на пустое поле
        if(request.getParameter("username") == null || request.getParameter("password") == null ||
                request.getParameter("email") == null){
            request.setAttribute("msg", "Please fill in all the fields of the form"); //TODO in18ze
            return resultPage;
        }
        if (userService.findUserByUsername(request.getParameter("username")) != null) {
            request.setAttribute("msg", "User already exists"); //TODO in18ze
            return resultPage;
        }
            //TODO can pass all data as String to Service do built there
            User user = new UserBuilder().setUsername(request.getParameter("username"))
                    .setPassword(request.getParameter("password"))
                    .setEmail(request.getParameter("email"))
                    .setUserRole(Role.USER)
                    .build();

            userService.registerUser(user);

            LOGGER.debug("User registered successfully");
            request.setAttribute("msgSuccess", "You have been registered successfully");
            resultPage = loginPage;

            return resultPage;
    }
}
