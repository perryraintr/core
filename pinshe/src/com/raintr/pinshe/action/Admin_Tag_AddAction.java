package com.raintr.pinshe.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.service.GroupService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_Tag_AddAction extends BaseAction {
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/admin_tag_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		if(StringGlobal.IsNull(name)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"name is null\"}}");
			return null;
		}
		
		if(StringGlobal.IsNull(description)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"description is null\"}}");
			return null;
		}
		
		name = StringGlobal.SerializeJson(name);
		description = StringGlobal.SerializeJson(description);	
		
		List<MultipartFile> files = null;
		try{
			files = ((MultipartHttpServletRequest)request).getFiles("file");
		}catch(Exception ex){}
		
		GroupBean group = new GroupBean();
		if(!StringGlobal.IsNull(type))
			group.setType(Integer.parseInt(type));
		group.setName(name);
		group.setDescription(description);
		group.setStart_time(new Date());
		group.setFinish_time(new Date());
		group.setCreate_time(new Date());
		group.setModify_time(new Date());
		group.setId(groupService.Add(group, files));
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done. id=%d\"}}", group.getId()));
		return null;
	}
}