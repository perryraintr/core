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
public class GroupAction extends BaseAction {
	@Autowired
	private GroupService groupService;

	@RequestMapping(value = "/group")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String gid = request.getParameter("gid");
		String type = request.getParameter("type");
		String page = request.getParameter("page");
		if(!StringGlobal.IsNull(page)){
			GroupBean group;
			List<GroupBean> groups;
			
			if(!StringGlobal.IsNull(page)){
				if(!StringGlobal.IsNull(type))
					groups = groupService.ByType(Integer.parseInt(type), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
				else
					groups = groupService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
				if(groups != null && groups.size() > 0){
					StringBuffer json = new StringBuffer();
					for(int i = 0; i < groups.size(); i++){
						group = groups.get(i);
						
						json.append("{");
						json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", group.ToId(""),
																				group.ToType(""),
																				group.ToName(""),
																				group.ToDescription(""),
																				group.ToMember_count(""),
																				group.ToPost_count(""),
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
		}
		
		if(!StringGlobal.IsNull(gid)){
			GroupBean group = groupService.ById(Integer.parseInt(gid));
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", group.ToId(""),
																	group.ToType(""),
																	group.ToName(""),
																	group.ToDescription(""),
																	group.ToMember_count(""),
																	group.ToPost_count(""),
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