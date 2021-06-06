package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.OrderStatus;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateTransportationCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(UpdateTransportationCommand.class);

    private static String ordersPage;
    private static String errorPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public UpdateTransportationCommand() {
        LOGGER.debug("Initializing UpdateTransportationCommand");

        MappingProperties properties = MappingProperties.getInstance();
        ordersPage = properties.getProperty("redirect.admin.orders");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing tarnsportation update command");

        Integer id = (Integer) request.getSession().getAttribute("transportationId");
        try {
            transpService.saveTransportation(id, OrderStatus.valueOf(request.getParameter("status")));

            return ordersPage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}
