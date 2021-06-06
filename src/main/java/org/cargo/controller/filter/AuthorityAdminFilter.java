package org.cargo.controller.filter;

import org.apache.log4j.Logger;
import org.cargo.properties.MappingProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorityAdminFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthorityAdminFilter.class);
    private static String accessErrorPage;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.trace("Filter initialization starts");
        MappingProperties properties = MappingProperties.getInstance();
        accessErrorPage = properties.getProperty("errorAccessPage");
        LOGGER.trace("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.trace("Filter starts");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        if(session != null && session.getAttribute("role").equals("ADMIN")){
            LOGGER.trace("Filter finished");
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(accessErrorPage).forward(request, response);
        }
    }

    @Override
    public void destroy() {
        LOGGER.trace("Filter destruction starts");
        // do nothing
        LOGGER.trace("Filter destruction finished");
    }
}
