package com.athensoft.prototype.error.config;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

public class MyErrorViewResolver implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        // Use the request or status to optionally return a ModelAndView
    	ModelAndView modelAndView = new ModelAndView();
    	
        if (status == HttpStatus.INTERNAL_SERVER_ERROR) {
        	modelAndView.setViewName("error/500"); 
        }else {
        	modelAndView.setViewName("error/404");
        }
        
        modelAndView.addObject("timestamp", model.get("timestamp"));
    	modelAndView.addObject("status", model.get("status"));
    	modelAndView.addObject("error", model.get("error"));
    	modelAndView.addObject("path", model.get("path"));
    	modelAndView.addObject("message", model.get("message"));
        return modelAndView;
    }

}
