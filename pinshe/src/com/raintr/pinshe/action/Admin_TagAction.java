package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.service.GroupService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_TagAction extends BaseAction {
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/admin_tag")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");	
		String page = request.getParameter("page");

		GroupBean group;
		List<GroupBean> groups;
		
		if(!StringGlobal.IsNull(page)){
			groups = groupService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(groups != null && groups.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < groups.size(); i++){
					group = groups.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s", 	group.ToId(""),
																		group.ToType(""),
																		group.ToName(""),
																		group.ToDescription(""),
																		group.ToImage(""),
																		group.ToCreate_time(""),
																		group.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(id)){
			group = groupService.ById(Integer.parseInt(id));
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s", 	group.ToId(""),
																group.ToType(""),
																group.ToName(""),
																group.ToDescription(""),
																group.ToImage(""),
																group.ToCreate_time(""),
																group.ToModify_time("")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}