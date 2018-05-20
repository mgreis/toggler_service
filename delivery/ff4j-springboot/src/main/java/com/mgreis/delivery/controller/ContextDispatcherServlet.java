package com.mgreis.delivery.controller;

import org.ff4j.web.FF4jDispatcherServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContextDispatcherServlet extends FF4jDispatcherServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(ContextDispatcherServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        LOGGER.info(req.getUserPrincipal().getName());
        getFf4j().getCurrentContext().addValue("clientHostName", req.getUserPrincipal().getName());
        super.doGet(req, res);

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        LOGGER.info(req.getUserPrincipal().getName());
        getFf4j().getCurrentContext().addValue("clientHostName", req.getUserPrincipal().getName());
        super.doPost(req, res);
    }

}
