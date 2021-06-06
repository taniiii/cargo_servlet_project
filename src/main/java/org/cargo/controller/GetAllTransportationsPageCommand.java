package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.Address;
import org.cargo.bean.transportation.Transportation;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TransportationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class GetAllTransportationsPageCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(GetAllTransportationsPageCommand.class);

    private static String ordersPage;
    private static String errorPage;
    private static TransportationService transpService = TransportationService.getInstance();

    public GetAllTransportationsPageCommand() {
        LOGGER.debug("Initializing GetAllTransportationsPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        ordersPage = properties.getProperty("ordersPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute sending transportations list page command");

//        Map<String, String> pageParams = validatePageRequest(request.getParameter("p"),
//                request.getParameter("s"), request.getParameter("sortDirection"),
//                request.getParameter("sortBy"));
//
//        Integer pageNo = Integer.parseInt(pageParams.getOrDefault("p", "1"));
//        Integer pageSize = Integer.parseInt(pageParams.getOrDefault("s", "10"));
//        String sortDirection = pageParams.getOrDefault("sortDirection", "ASC");
//        String sortBy = pageParams.getOrDefault("sortBy", "id");
        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        String sortBy = request.getParameter("sortBy");

        String address = request.getParameter("destinationFilter");

//        Optional<String> d = Optional.ofNullable(request.getParameter("order_date"));
//        String date = d.orElse();
        String date = request.getParameter("order_date");

        try {

            Page<Transportation> page = transpService.getAllTransportationsPaginated(pageNo, pageSize, sortDirection, sortBy);

            if (Objects.nonNull(address) && !address.isEmpty()) {
                Set<String> addresses = Arrays.stream(Address.values()).map(Address::name).collect(Collectors.toSet());

                for (String e : addresses) { //TODO
                    if (e.contains(address.toUpperCase())) {
                        page = transpService.findTransportationsByAddress(pageNo, pageSize, sortDirection, e);
                    }
                }

            } else {
                if (Objects.nonNull(date) && date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    page = transpService.findTransportationByDate(pageNo, pageSize, sortBy, sortDirection, LocalDate.parse(date));
                }

            }

            request.getSession().setAttribute("sortDirection", sortDirection);
            request.getSession().setAttribute("sortBy", sortBy);
            request.getSession().setAttribute("currentPage", pageNo);
            request.getSession().setAttribute("userOrders", page);
            request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            request.getSession().setAttribute("orderList", page);

            return ordersPage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}
