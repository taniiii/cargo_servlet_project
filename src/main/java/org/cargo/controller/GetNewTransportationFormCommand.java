package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.Address;
import org.cargo.bean.transportation.Size;
import org.cargo.bean.transportation.Weight;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetNewTransportationFormCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetNewTransportationFormCommand.class);

    private static String neworderPage;
    private static String loginPage;

    public GetNewTransportationFormCommand() {
        LOGGER.debug("Initializing GetNewTransportationFormCommand");

        MappingProperties properties = MappingProperties.getInstance();
        neworderPage = properties.getProperty("neworderPage");
        loginPage = properties.getProperty("loginPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send new order's page command");

        String resultPage = neworderPage;

        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            request.getSession().setAttribute("addresses", Address.values());
            request.getSession().setAttribute("sizes", Size.values());
            request.getSession().setAttribute("weights", Weight.values());
            return resultPage;
        } else {
            LOGGER.debug("User is not logged in yet, returning login page");
            resultPage = loginPage;
            }

        return resultPage;
    }
}
