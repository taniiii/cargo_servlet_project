package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.Role;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class GetUserEditFormCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetUserListPageCommand.class);

    private static String userEditPage;
    private static String errorPage;

    private static UserService userService = UserService.getInstance();

    public GetUserEditFormCommand() {
        LOGGER.debug("Initializing GetUserEditFormCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userEditPage = properties.getProperty("userEditPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute sending user edit page command");

        if (Objects.isNull(request.getParameter("id"))) {
            return errorPage;
        }
        Integer id = Integer.parseInt(request.getParameter("id"));
        try {
            request.getSession().setAttribute("updatedUser", userService.findUserById(id));
            request.getSession().setAttribute("roles", Role.values());
        } catch (DaoException e) {
            return errorPage;
        }

        return userEditPage;
    }
}
