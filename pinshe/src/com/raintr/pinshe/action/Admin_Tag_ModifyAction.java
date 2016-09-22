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
public class Admin_Tag_ModifyAction extends BaseAction {
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/admin_tag_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
			
		if(StringGlobal.IsNull(id)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"id is null\"}}");
			return null;
		}

		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		
		name = StringGlobal.SerializeJson(name);
		description = StringGlobal.SerializeJson(description);
		
		List<MultipartFile> files = null;
		try{
			files = ((MultipartHttpServletRequest)request).getFiles("file");
		}catch(Exception ex){}
		
		GroupBean group = groupService.ById(Integer.parseInt(id));
		
		if(!StringGlobal.IsNull(type))
			group.setType(Integer.parseInt(type));
		if(!StringGlobal.IsNull(name))
			group.setName(name);
		if(!StringGlobal.IsNull(description))
			group.setDescription(description);
		group.setModify_time(new Date());
		groupService.Modify(group, files);
			
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done. id=%d\"}}", group.getId()));
		return null;
	}
}