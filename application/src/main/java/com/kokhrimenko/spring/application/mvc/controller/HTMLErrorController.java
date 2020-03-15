package com.kokhrimenko.spring.application.mvc.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class HTMLErrorController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private static final String ERROR_PAGE_TEMPLATE = "..%s/%s";
    private static final String ERROR_PAGE_UNkNOWN = "unknown";

    private static List<Integer> PROCESSING_ERROR_CODES = Arrays.asList(HttpStatus.NOT_FOUND.value(), HttpStatus.INTERNAL_SERVER_ERROR.value());

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (PROCESSING_ERROR_CODES.contains(statusCode)) {
                return String.format(ERROR_PAGE_TEMPLATE, ERROR_PATH, statusCode);
            }
        }
        return String.format(ERROR_PAGE_TEMPLATE, ERROR_PATH, ERROR_PAGE_UNkNOWN);
    }

    @Override
    public String getErrorPath() {
        log.warn("Request error page. Something went wrong!");

        return ERROR_PATH;
    }

}
