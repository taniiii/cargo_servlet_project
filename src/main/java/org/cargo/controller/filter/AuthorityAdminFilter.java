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
        LOGGER.debug("Filter initialization starts");
        MappingProperties properties = MappingProperties.getInstance();
        accessErrorPage = properties.getProperty("errorAccessPage");
        LOGGER.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("Filter starts");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        if(session != null && session.getAttribute("role").equals("ADMIN")){
            LOGGER.debug("Filter finished");
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher(accessErrorPage).forward(request, response);
        }
    }

    @Override
    public void destroy() {
        LOGGER.debug("Filter destruction starts");
        // do nothing
        LOGGER.debug("Filter destruction finished");
    }
}
