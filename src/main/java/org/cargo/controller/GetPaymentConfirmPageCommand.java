package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.user.Role;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetPaymentConfirmPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetUserListPageCommand.class);

    private static String paymentPage;
    private static String errorAccessPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public GetPaymentConfirmPageCommand() {
        LOGGER.debug("Initializing GetPaymentConfirmPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        paymentPage = properties.getProperty("paymentPage");
        errorAccessPage = properties.getProperty("errorAccessPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send transportation payment page command");

        String resultPage = paymentPage;

        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("role").equals("USER")) {
            Integer id = Integer.parseInt(request.getParameter("id"));
            request.getSession().setAttribute("updateTranspId", id);
//            request.getSession().setAttribute("transportation", transpService.findTransportationById(id));
            //TODO нужно ли проверять запрашивает ли юзер свой заказ или нет
            return resultPage;
        } else {
            LOGGER.debug("User has no authority to access payment page, returning error page");
            resultPage = errorAccessPage;
        }
        return resultPage;
    }
}
