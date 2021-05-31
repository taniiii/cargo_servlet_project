package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.OrderStatus;
import org.cargo.bean.transportation.Transportation;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PaymentCompleteCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(PaymentCompleteCommand.class);

    private static String userTransportationsPage;
    private static String errorAccessPage;

    private static TransportationService transpService = TransportationService.getInstance();

    public PaymentCompleteCommand() {
        LOGGER.debug("Initializing UpdateTransportationCommand");

        MappingProperties properties = MappingProperties.getInstance();
        userTransportationsPage = properties.getProperty("redirect.user.orders");
        errorAccessPage = properties.getProperty("errorAccessPage");
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing tarnsportation update command");

        String resultPage = userTransportationsPage;
        HttpSession session = request.getSession();

        Integer id = (Integer) session.getAttribute("updateTranspId");

        transpService.saveTransportation(id, OrderStatus.PAID);
        return resultPage;
    }
}
