package com.raintr.pinshe.action;

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
public class ConsigneeAction extends BaseAction {
	@Autowired
	private ConsigneeService consigneeService;
	
	@RequestMapping(value = "/consignee")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String page = request.getParameter("page");
		String memberId = request.getParameter("mid");
		
		ConsigneeBean consignee;
		List<ConsigneeBean> consignees;
		
		if(!StringGlobal.IsNull(page)){
			consignees = consigneeService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(consignees != null && consignees.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < consignees.size(); i++){
					consignee = consignees.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	consignee.ToId(""),
																				consignee.ToMember_id(""),
																				consignee.ToName(""),
																				consignee.ToPhone(""),
																				consignee.ToAddress(""),
																				consignee.ToZip(""),
																				consignee.ToStatus(""),
																				consignee.ToIsDelete(""),
																				consignee.ToCreate_time(""),
																				consignee.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(id)){
			consignee = consigneeService.ById(Integer.parseInt(id));
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	consignee.ToId(""),
																		consignee.ToMember_id(""),
																		consignee.ToName(""),
																		consignee.ToPhone(""),
																		consignee.ToAddress(""),
																		consignee.ToZip(""),
																		consignee.ToStatus(""),
																		consignee.ToIsDelete(""),
																		consignee.ToCreate_time(""),
																		consignee.ToModify_time("")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		if(!StringGlobal.IsNull(memberId)){
			consignees = consigneeService.ByMemberId(Integer.parseInt(memberId));
			if(consignees != null && consignees.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < consignees.size(); i++){
					consignee = consignees.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	consignee.ToId(""),
																				consignee.ToMember_id(""),
																				consignee.ToName(""),
																				consignee.ToPhone(""),
																				consignee.ToAddress(""),
																				consignee.ToZip(""),
																				consignee.ToStatus(""),
																				consignee.ToIsDelete(""),
																				consignee.ToCreate_time(""),
																				consignee.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}