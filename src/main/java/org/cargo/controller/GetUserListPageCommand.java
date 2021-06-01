package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.user.User;
import org.cargo.properties.MappingProperties;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static org.cargo.service.Validator.validatePageRequest;

public class GetUserListPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetUserListPageCommand.class);

    private static String userlistPage;
    private static String errorPage;
    private static UserService userService = UserService.getInstance();

    public GetUserListPageCommand() {
        LOGGER.debug("Initializing GetUserListPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userlistPage = properties.getProperty("userlistPage");
        errorPage = properties.getProperty("errorPage");
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send user list page command");

        Map<String, String> pageParams = validatePageRequest(request.getParameter("p"),
                request.getParameter("s"), request.getParameter("sortDirection"),
                request.getParameter("sortBy"));

        Integer pageNo = Integer.parseInt(pageParams.getOrDefault("p", "1"));
        Integer pageSize = Integer.parseInt(pageParams.getOrDefault("s", "10"));
        String sortDirection = pageParams.getOrDefault("sortDirection", "ASC");
        String sortBy = pageParams.getOrDefault("sortBy", "id");
//        Integer pageSize = Integer.parseInt(request.getParameter("s"));
//        String sortDirection = request.getParameter("sortDirection");
//        String sortBy = request.getParameter("sortBy");

        Page<User> page = userService.getAllUsersPaginated(pageNo, pageSize, sortDirection, sortBy);

        request.getSession().setAttribute("sortDirection", sortDirection);
        request.getSession().setAttribute("sortBy", sortBy);
        request.getSession().setAttribute("currentPage", pageNo);
        request.getSession().setAttribute("userOrders", page);
        request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        request.getSession().setAttribute("userList", page);

        return userlistPage;
    }
}
