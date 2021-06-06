package org.cargo.controller.filter;


import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(EncodingFilter.class);


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        LOGGER.trace("Filter starts");

        servletResponse.setContentType("text/html");
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");


        LOGGER.trace("Filter finished");
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {
        LOGGER.trace("Filter destruction starts");
        // do nothing
        LOGGER.trace("Filter destruction finished");
    }
}
