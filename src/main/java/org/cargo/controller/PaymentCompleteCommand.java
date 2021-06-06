package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.OrderStatus;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PaymentCompleteCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(PaymentCompleteCommand.class);

    private static String userTransportationsPage;
    private static String errorPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public PaymentCompleteCommand() {
        LOGGER.debug("Initializing UpdateTransportationCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userTransportationsPage = properties.getProperty("redirect.user.orders");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute transportation payment complete command");

        Integer id = (Integer) request.getSession().getAttribute("updateTranspId");
        try {
            transpService.saveTransportation(id, OrderStatus.PAID);

            return userTransportationsPage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}
