package org.cargo.controller.filter;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "errorFilter")
public class ErrorFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(ErrorFilter.class);
    private static String errorPage;

    @Override
    public void init(FilterConfig filterConfig) {
        MappingProperties properties = MappingProperties.getInstance();
        errorPage = properties.getProperty("errorPage");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) request;

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            LOGGER.debug("An exception occured");
            request.getRequestDispatcher(errorPage).forward(request, response);
        }

    }
}

