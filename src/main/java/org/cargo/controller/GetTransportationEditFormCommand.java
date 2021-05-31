package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.OrderStatus;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetTransportationEditFormCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetTransportationEditFormCommand.class);

    private static String transportationEdit;
    private static String errorAccessPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public GetTransportationEditFormCommand() {
        LOGGER.debug("Initializing GetTransportationEditFormCommand");

        MappingProperties properties = MappingProperties.getInstance();
        transportationEdit = properties.getProperty("transportationEditPage");
        errorAccessPage = properties.getProperty("errorAccessPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send user edit page command");

        String resultPage = transportationEdit;

//        if (request.getSession().getAttribute("authenticated") != null &&
//                request.getSession().getAttribute("role").equals("ADMIN")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
//            request.getSession().setAttribute("transportation", transpService.findTransportationById(id));
            request.getSession().setAttribute("transportationId", id);
            request.getSession().setAttribute("statuses", OrderStatus.values());
            return resultPage;
//        } else {
//            LOGGER.debug("User`s authority is not 'ADMIN', returning error page");
//            resultPage = errorAccessPage;
//        }
//        return resultPage;
    }
}
