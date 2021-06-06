package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.Transportation;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserTransportationsPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetUserTransportationsPageCommand.class);

    private static String userTransportationsPage;
    private static String errorPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public GetUserTransportationsPageCommand() {
        LOGGER.debug("Initializing GetUserTransportationsPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userTransportationsPage = properties.getProperty("userTransportationsPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send user transportations list page command");

//        Map<String, String> pageParams = validatePageRequest(request.getParameter("p"),
//                request.getParameter("s"), request.getParameter("sortDirection"),
//                request.getParameter("sortBy"));
//
//        Integer pageNo = Integer.parseInt(pageParams.getOrDefault("p", "1"));
//        Integer pageSize = Integer.parseInt(pageParams.getOrDefault("s", "10"));
//        String sortDirection = pageParams.getOrDefault("sortDirection", "ASC");
//        String sortBy = pageParams.getOrDefault("sortBy", "id");

        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        String sortBy = request.getParameter("sortBy");

        try {
            Page<Transportation> page = transpService.findTransportationsByUser(
                    request.getSession().getAttribute("user"), pageNo, pageSize, sortDirection, sortBy);

            request.getSession().setAttribute("sortDirection", sortDirection);
            request.getSession().setAttribute("sortBy", sortBy);
            request.getSession().setAttribute("currentPage", pageNo);
            request.getSession().setAttribute("userOrders", page);
            request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

            return userTransportationsPage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}
