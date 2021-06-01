package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.OrderStatus;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateTransportationCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(UpdateTransportationCommand.class);

    private static String ordersPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public UpdateTransportationCommand() {
        LOGGER.debug("Initializing UpdateTransportationCommand");

        MappingProperties properties = MappingProperties.getInstance();
        ordersPage = properties.getProperty("redirect.admin.orders");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing tarnsportation update command");

        Integer id = (Integer) request.getSession().getAttribute("transportationId");
        transpService.saveTransportation(id, OrderStatus.valueOf(request.getParameter("status")));

        return ordersPage;
    }
}
