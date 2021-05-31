package org.cargo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This interface marks classes that are used as commands.
 */
public interface Command {
    /**
     *
     *
     * @param request  Http request from servlet.
     * @param response Http response from servlet.
     * @return A string that represents a view to forward to.
     */
    String execute(HttpServletRequest request, HttpServletResponse response);
}
