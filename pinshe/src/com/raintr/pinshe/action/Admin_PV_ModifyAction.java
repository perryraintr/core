package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.PVBean;
import com.raintr.pinshe.service.PVService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_PV_ModifyAction extends BaseAction {
	@Autowired
	private PVService pVService;
	
	@RequestMapping(value = "/admin_pv_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String count = request.getParameter("count");
		
		if(StringGlobal.IsNull(id)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"id is null\"}}");
			return null;
		}
		
		if(StringGlobal.IsNull(count)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"count is null\"}}");
			return null;
		}
		
		PVBean pv = pVService.ById(Integer.parseInt(id));
		pv.setCount(Integer.parseInt(count));
		pVService.Modify(pv);
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done.\"}}"));
		return null;
	}
}