package org.cargo.controller.filter;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorityUserFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthorityUserFilter.class);
    private static String loginPage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.trace("Filter initialization starts");
        MappingProperties properties = MappingProperties.getInstance();
        loginPage = properties.getProperty("loginPage");
        LOGGER.trace("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.trace("Filter starts");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("role") == null){
            request.getRequestDispatcher(loginPage).forward(request, response);
        } else {
            LOGGER.trace("Filter finished");
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        LOGGER.trace("Filter destruction starts");
        // do nothing
        LOGGER.trace("Filter destruction finished");
    }
}
