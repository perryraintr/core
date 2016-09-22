package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.ConsigneeBean;
import com.raintr.pinshe.service.ConsigneeService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Consignee_AddAction extends BaseAction {
	@Autowired
	private ConsigneeService consigneeService;
	
	@RequestMapping(value = "/consignee_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");

		if(StringGlobal.IsNull(memberId)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"mid is null\"}}");
			return null;
		}
		
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String zip = request.getParameter("zip");
		String status = request.getParameter("status");
		
		ConsigneeBean consignee = new ConsigneeBean();
		if(!StringGlobal.IsNull(memberId))
			consignee.setMember_id(Integer.parseInt(memberId));
		if(!StringGlobal.IsNull(name))
			consignee.setName(name);
		if(!StringGlobal.IsNull(phone))
			consignee.setPhone(phone);
		if(!StringGlobal.IsNull(address))
			consignee.setAddress(address);
		if(!StringGlobal.IsNull(zip))
			consignee.setZip(zip);
		if(!StringGlobal.IsNull(status))
			consignee.setStatus(Integer.parseInt(status));
		consignee.setCreate_time(new Date());
		consignee.setModify_time(new Date());
		consignee.setId(consigneeService.Add(consignee));
			
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", consignee.ToId("")));
		return null;
	}
}