package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.User;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateProfileCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetRegistrationPageCommand.class);

    private static String profilePage;
    private static String loginPage;
    private static UserService userService = UserService.getInstance();

    public UpdateProfileCommand() {
        LOGGER.debug("Initializing UpdateProfileCommand");

        MappingProperties properties = MappingProperties.getInstance();
        profilePage = properties.getProperty("redirect.profile");
        loginPage = properties.getProperty("loginPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing user profile update command");

        String resultPage = profilePage;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (request.getParameter("email") != null
                && request.getParameter("password") != null) {
            user.setEmail(request.getParameter("email"));
            user.setPassword(request.getParameter("password"));
            userService.updateProfile(user);
        } else {
                request.setAttribute("msg", "No user details to update"); //TODO in18ze
            }

        return resultPage;
    }
}
