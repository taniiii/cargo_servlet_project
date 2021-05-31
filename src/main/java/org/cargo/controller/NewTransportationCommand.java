package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.transportation.Address;
import org.cargo.bean.transportation.Size;
import org.cargo.bean.transportation.Tariff;
import org.cargo.bean.transportation.Weight;
import org.cargo.bean.user.Role;
import org.cargo.bean.user.User;
import org.cargo.bean.user.UserBuilder;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TariffService;
import org.cargo.service.TransportationService;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewTransportationCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(NewTransportationCommand.class);

    private static TransportationService transpService = TransportationService.getInstance();

    private static String neworderPage;
    private static String userTransportationsPage;
    private static String loginPage;

    public NewTransportationCommand() {
        LOGGER.debug("Initializing NewTransportationCommand");

        MappingProperties properties = MappingProperties.getInstance();
        neworderPage = properties.getProperty("neworderPage");
        userTransportationsPage = properties.getProperty("redirect.user.orders");
        loginPage = properties.getProperty("loginPage");
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing placing new transportation command");

        String resultPage = neworderPage;
//лучше сделать валидацию - добавить проверку на пустое поле
        if(request.getParameter("address") == null || request.getParameter("size") == null ||
                request.getParameter("weight") == null){
            request.setAttribute("msg", "Please fill in all the fields of the form"); //TODO in18ze
            return resultPage;
        } else {
            User user = (User) request.getSession().getAttribute("user");

            Tariff tariff = new Tariff();
            tariff.setAddress(Address.valueOf(request.getParameter("address")));
            tariff.setSize(Size.valueOf(request.getParameter("size")));
            tariff.setWeight(Weight.valueOf(request.getParameter("weight")));

            transpService.createTransportation(user, tariff, request.getParameter("comment"));

        }

        return userTransportationsPage;     //"/cargo/user/orders";
    }
}
