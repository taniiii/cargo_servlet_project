package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.*;
import org.cargo.bean.user.User;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used to handle POST request to register new order.
 */
public class NewTransportationCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(NewTransportationCommand.class);

    private static TransportationService transpService = TransportationService.getInstance();
    private static String userTransportationsPage;
    private static String errorPage;

    public NewTransportationCommand() {
        LOGGER.debug("Initializing NewTransportationCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userTransportationsPage = properties.getProperty("redirect.user.orders");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute placing new transportation command");

        User user = (User) request.getSession().getAttribute("user");

        Tariff tariff = new TariffBuilder()
                .setAddress(Address.valueOf(request.getParameter("address")))
                .setSize(Size.valueOf(request.getParameter("size")))
                .setWeight(Weight.valueOf(request.getParameter("weight")))
                .build();
        try {
            transpService.createTransportation(user, tariff, request.getParameter("comment"));
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
        return userTransportationsPage;
    }
}
