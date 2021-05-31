package org.cargo.controller;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

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
    public String execute(HttpServletRequest request, HttpServletResponse response){
        LOGGER.debug("Executing command");
//        Optional<String> locale = Optional.of(request.getParameter("locale"));
//
//        request.setAttribute("locale", locale.orElse("en"));
        if (request.getParameter("locale") != null) {
            String locale = request.getParameter("locale");
            switch (locale) {
                case "en":
                    request.getSession().setAttribute("locale", "en");
                    break;
                case "ua":
                    request.getSession().setAttribute("locale", "ua");
                    break;
            }
        }

//        HttpSession session = request.getSession();
//        String locale = request.getParameter("locale");
//        String previousRequest = (String) session.getAttribute("previous_request");
//        session.setAttribute("locale", locale);
//        try {
//            response.sendRedirect(previousRequest);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return homepage;
    }
}
