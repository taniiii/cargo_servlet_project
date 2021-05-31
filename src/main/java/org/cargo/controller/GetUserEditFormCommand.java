package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.Role;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserEditFormCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetUserListPageCommand.class);

    private static String userEditPage;
    private static String errorAccessPage;
    private static UserService userService = UserService.getInstance();

    public GetUserEditFormCommand() {
        LOGGER.debug("Initializing GetUserEditFormCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userEditPage = properties.getProperty("userEditPage");
        errorAccessPage = properties.getProperty("errorAccessPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send user edit page command");

        String resultPage = userEditPage;

//        if (request.getSession().getAttribute("authenticated") != null &&
//                request.getSession().getAttribute("role").equals("ADMIN")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            request.getSession().setAttribute("updatedUser", userService.findUserById(id));
            request.getSession().setAttribute("roles", Role.values());
            return resultPage;
//        } else {
//            LOGGER.debug("User`s authority is not 'ADMIN', returning error page");
//            resultPage = errorAccessPage;
//        }
//        return resultPage;
    }
}
