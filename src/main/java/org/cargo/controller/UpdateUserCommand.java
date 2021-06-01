package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.User;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class UpdateUserCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetUserListPageCommand.class);

    private static String userEditPage;
    private static String errorAccessPage;
    private static String userlistPage;
    private static UserService userService = UserService.getInstance();

    public UpdateUserCommand() {
        LOGGER.debug("Initializing UpdateUserCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userEditPage = properties.getProperty("userEditPage");
        errorAccessPage = properties.getProperty("errorAccessPage");
        userlistPage = properties.getProperty("redirect.admin.userList");
    }


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing user update command");

        HttpSession session = request.getSession();
        User userToUpdate = (User) session.getAttribute("updatedUser");

        if (Objects.nonNull(request.getParameter("updateName")) &&
                Objects.nonNull(request.getParameter("updateRole"))) {

            userToUpdate.setUsername(request.getParameter("updateName"));
            userService.saveUser(userToUpdate, request.getParameter("updateRole"));
        } else {
            request.setAttribute("msgUpdate", " ");
            return userEditPage;
        }
        return userlistPage;
    }
}
