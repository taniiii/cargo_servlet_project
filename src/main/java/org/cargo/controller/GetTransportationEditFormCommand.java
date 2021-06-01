package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.OrderStatus;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class GetTransportationEditFormCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetTransportationEditFormCommand.class);

    private static String transportationEdit;
    private static String errorPage;

    public GetTransportationEditFormCommand() {
        LOGGER.debug("Initializing GetTransportationEditFormCommand");

        MappingProperties properties = MappingProperties.getInstance();
        transportationEdit = properties.getProperty("transportationEditPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send user edit page command");

        if (Objects.nonNull(request.getParameter("id"))) {

            Integer id = Integer.parseInt(request.getParameter("id"));
            request.getSession().setAttribute("transportationId", id);
            request.getSession().setAttribute("statuses", OrderStatus.values());

            return transportationEdit;
        } else return errorPage;

    }
}
