package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.service.ConsigneeService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Consignee_RemoveAction extends BaseAction {
	@Autowired
	private ConsigneeService consigneeService;
	
	@RequestMapping(value = "/consignee_remove")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String id = request.getParameter("id");

		if(!StringGlobal.IsNull(id))
			consigneeService.Remove(Integer.parseInt(id));
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}