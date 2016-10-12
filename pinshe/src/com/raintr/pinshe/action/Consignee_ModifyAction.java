package com.raintr.pinshe.action;

import java.util.Date;
import java.util.List;

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
public class Consignee_ModifyAction extends BaseAction {
	@Autowired
	private ConsigneeService consigneeService;
	
	@RequestMapping(value = "/consignee_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");
		String id = request.getParameter("id");

		if(StringGlobal.IsNull(id)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"id is null\"}}");
			return null;
		}
		
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String zip = request.getParameter("zip");
		String status = request.getParameter("status");
		String isDelete = request.getParameter("is_delete");
		
		
		ConsigneeBean consignee;
		List<ConsigneeBean> consignees = consigneeService.ByMemberId(Integer.parseInt(memberId));
		if(consignees != null && consignees.size() > 0){
			for(int i = 0; i < consignees.size(); i++){
				consignee = consignees.get(i);
				consignee.setStatus(0);
				consigneeService.Modify(consignee);
			}
		}
		
		consignee = consigneeService.ById(Integer.parseInt(id));
		if(consignee != null){
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
			if(!StringGlobal.IsNull(isDelete))
				consignee.setIs_delete(Integer.parseInt(isDelete));
			consignee.setModify_time(new Date());
			consigneeService.Modify(consignee);
				
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", consignee.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}