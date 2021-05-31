package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.bean.Page;
import org.cargo.bean.transportation.Tariff;
import org.cargo.properties.MappingProperties;
import org.cargo.service.TariffService;
import org.cargo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetTariffPageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetTariffPageCommand.class);

    private static TariffService tariffService = TariffService.getInstance();
    private static String loginPage;
    private static String tariffPage;

    public GetTariffPageCommand(){
        LOGGER.debug("Initializing GetTariffPageCommand");

        MappingProperties properties = MappingProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        tariffPage = properties.getProperty("tariffPage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Execute sending tariff page command");
//можно обернуть в try с catch NumberFormatException
        Integer pageNo = Integer.parseInt(request.getParameter("p"));
        Integer pageSize = Integer.parseInt(request.getParameter("s"));
        String sortDirection = request.getParameter("sortDirection");

        //List<Tariff> tariffs = tariffService.getAllTariffs();

        Page<Tariff> page = tariffService.getAllTariffsPaginated(pageNo, pageSize, sortDirection);

        //request.getSession().setAttribute("tariffs", tariffs);
        request.getSession().setAttribute("tariffs", page);
        request.getSession().setAttribute("currentPage", pageNo);
       // request.getSession().setAttribute("totalPages", page.getCurrentSize()); //TODO заменить на счётчик страниц
        request.getSession().setAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");                                   //TODO вытаскивать сразу все
        return tariffPage;
    }
}
