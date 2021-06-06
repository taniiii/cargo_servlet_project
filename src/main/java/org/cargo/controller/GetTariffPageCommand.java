package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.Tariff;
import org.cargo.exception.DaoException;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TariffService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetTariffPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetTariffPageCommand.class);

    private static TariffService tariffService = TariffService.getInstance();
    private static String tariffPage;
    private static String errorPage;

    public GetTariffPageCommand(){
        LOGGER.debug("Initializing GetTariffPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        tariffPage = properties.getProperty("tariffPage");
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute sending tariff page command");

        //Map<String, Object> pageParam = Map.ofEntries(entry("", ))
        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");
        try {
            Page<Tariff> page = tariffService.getAllTariffsPaginated(pageNo, pageSize, sortDirection);

            request.getSession().setAttribute("tariffs", page);
            request.getSession().setAttribute("currentPage", pageNo);
            request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
            return tariffPage;
        } catch (DaoException e) {
            request.getSession().setAttribute("errorMessage", e.getMessage());
            return errorPage;
        }
    }
}
