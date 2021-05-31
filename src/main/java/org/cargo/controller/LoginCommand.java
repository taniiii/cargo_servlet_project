package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.User;
import org.cargo.dao.UserDao;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * This handles POST request to authenticate user. Checks login, password
 */
public class LoginCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private static UserService userService = UserService.getInstance();

    private static String loginPage;
    private static String homePage;

    public LoginCommand() {
       LOGGER.debug("Initializing LoginCommand");

        MappingProperties properties = MappingProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        homePage = properties.getProperty("redirect.home");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Executing command");

        String resultPage = loginPage;
//можно заменить валидатором
        if (request.getParameter("username") != null
                && request.getParameter("password") != null) {
            User user = userService.getUserByCredentials(request.getParameter("username"),
                    request.getParameter("password"));

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("authenticated", true);
                session.setAttribute("role", user.getUserRole().name());
                request.setAttribute("loginSuccess", true);

                resultPage = homePage;
            } else {
                request.setAttribute("msg", "No user found with such credentials"); //TODO in18ze
                request.setAttribute("loginSuccess", false);
            }
        }

        return  resultPage;              //"/cargo";
    }
}
