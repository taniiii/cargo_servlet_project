package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.Tariff;
import org.cargo.bean.transportation.Transportation;
import org.cargo.bean.user.User;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUserTransportationsPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetUserTransportationsPageCommand.class);

    private static String userTransportationsPage;
    private static String errorAccessPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public GetUserTransportationsPageCommand() {
        LOGGER.debug("Initializing GetUserTransportationsPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userTransportationsPage = properties.getProperty("userTransportationsPage");
        errorAccessPage = properties.getProperty("errorAccessPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send user transportations list page command");

        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        String sortBy = request.getParameter("sortBy");

        Page<Transportation> page = transpService.findTransportationsByUser(
                request.getSession().getAttribute("user"), pageNo, pageSize, sortDirection, sortBy);

        request.getSession().setAttribute("sortDirection", sortDirection);
        request.getSession().setAttribute("sortBy", sortBy);
        request.getSession().setAttribute("currentPage", pageNo);
        request.getSession().setAttribute("userOrders", page);
        request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        return userTransportationsPage;
    }
}
