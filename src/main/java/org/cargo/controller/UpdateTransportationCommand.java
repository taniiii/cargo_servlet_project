package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.OrderStatus;
import org.cargo.bean.transportation.Transportation;
import org.cargo.bean.user.User;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateTransportationCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(UpdateTransportationCommand.class);

    private static String ordersPage;
    private static String errorAccessPage;

    private static TransportationService transpService = TransportationService.getInstance();

    public UpdateTransportationCommand() {
        LOGGER.debug("Initializing UpdateTransportationCommand");

        MappingProperties properties = MappingProperties.getInstance();
        ordersPage = properties.getProperty("redirect.admin.orders");
        errorAccessPage = properties.getProperty("errorAccessPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing tarnsportation update command");

        String resultPage = ordersPage;
        HttpSession session = request.getSession();
        Integer id = (Integer) session.getAttribute("transportationId");

        transpService.saveTransportation(id, OrderStatus.valueOf(request.getParameter("status")));

        return resultPage;
    }
}
