package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

public abstract class BaseAction {
	protected abstract String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception;  
	
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
		return Action(request, response, model);
	}
}