package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.Address;
import org.cargo.bean.transportation.Transportation;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class GetAllTransportationsPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetAllTransportationsPageCommand.class);

    private static String ordersPage;
    private static String errorAccessPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public GetAllTransportationsPageCommand() {
        LOGGER.debug("Initializing GetAllTransportationsPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        ordersPage = properties.getProperty("ordersPage");
        errorAccessPage = properties.getProperty("errorAccessPage");
    }
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing send transportations list page command");

        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        String sortBy = request.getParameter("sortBy");
        String address = request.getParameter("destinationFilter");
        //validate
        String date = request.getParameter("order_date");

        Page<Transportation> page = transpService.getAllTransportationsPaginated(pageNo, pageSize, sortDirection, sortBy);

        if(address != null && !address.isEmpty()){
            //move to method validate
            Set<String> addresses = Arrays.stream(Address.values()).map(Address :: name).collect(Collectors.toSet());

            for(String e : addresses){
                if(e.contains(address.toUpperCase())){
                    page = transpService.findTransportationsByAddress(pageNo, pageSize, sortDirection, e);
                }
            }

        } else {
            if(date != null && date.matches("\\d{4}-\\d{2}-\\d{2}")){
            page = transpService.findTransportationByDate(pageNo, pageSize, sortBy, sortDirection, LocalDate.parse(date));}
//                else{
//                    page = Page.empty();
//                }
        }

        request.getSession().setAttribute("sortDirection", sortDirection);
        request.getSession().setAttribute("sortBy", sortBy);
        request.getSession().setAttribute("currentPage", pageNo);
        request.getSession().setAttribute("userOrders", page);
        request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

        request.getSession().setAttribute("orderList", page);


        return ordersPage;
    }
}
