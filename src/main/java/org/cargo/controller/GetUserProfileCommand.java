package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.User;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.Encoder;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.cargo.service.Validator.validateProfileUpdate;

public class GetUserProfileCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetUserProfileCommand.class);

    private static UserService userService = UserService.getInstance();

    private static String profilePage;
    private static String homePage;
    private static String errorPage;

    public GetUserProfileCommand() {
        LOGGER.debug("Initializing GetUserProfileCommand");

        MappingProperties properties = MappingProperties.getInstance();
        profilePage = properties.getProperty("profilePage");
        homePage = properties.getProperty("redirect.home");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing user profile update page command");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (validateProfileUpdate(request.getParameter("password"),
                request.getParameter("email"))) {
            user.setEmail(request.getParameter("email"));
            user.setPassword(Encoder.encrypt(request.getParameter("password")));
            try {
                userService.updateProfile(user);
                return homePage;
            } catch (DaoException e) {
                return errorPage;
            }

        } else {
            request.setAttribute("msgBadUpdate", " ");
        }
        return profilePage;
    }
}
