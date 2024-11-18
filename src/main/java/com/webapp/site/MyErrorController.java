package com.webapp.site;

import java.security.Principal;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;


@Controller

public class MyErrorController implements ErrorController {

	@Override
    public String getErrorPath() {
        return "/error";
    }
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	            return "error-404";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	            return "error-500";
	        }
	        else if(statusCode == HttpStatus.FORBIDDEN.value()) {
	            return "error-403";
	        }
	    }
	    return "error";
	}
}
