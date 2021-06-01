package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class GetPaymentConfirmPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetUserListPageCommand.class);

    private static String paymentPage;
    private static String errorPage;

    public GetPaymentConfirmPageCommand() {
        LOGGER.debug("Initializing GetPaymentConfirmPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        paymentPage = properties.getProperty("paymentPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute sending transportation payment page command");

        if (Objects.isNull(request.getParameter("id"))) {
            return errorPage;
        }

        Integer id = Integer.parseInt(request.getParameter("id"));
        request.getSession().setAttribute("updateTranspId", id);

        return paymentPage;
    }
}
