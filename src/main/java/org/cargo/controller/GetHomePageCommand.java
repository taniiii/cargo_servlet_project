package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * This class is used to handle GET requests to the home page.
 */
public class GetHomePageCommand implements Command{
    private static final Logger LOGGER = Logger.getLogger(GetHomePageCommand.class);
    private static String homepage;

    public GetHomePageCommand(){
        LOGGER.debug("Initializing GetHomePageCommand");
        MappingProperties properties = MappingProperties.getInstance();
        homepage = properties.getProperty("homepage");
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("Executing home page command");

        if (Objects.nonNull(request.getParameter("locale"))) {
            switch (request.getParameter("locale")) {
                case "en":
                    request.getSession().setAttribute("locale", "en");
                    break;
                case "ua":
                    request.getSession().setAttribute("locale", "ua");
                    break;
            }
        }

        return homepage;
    }
}
