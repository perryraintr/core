package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.ActivityBean;
import com.raintr.pinshe.service.ActivityService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Activity_ModifyAction extends BaseAction {
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping(value = "/activity_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");

		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		String memberId = request.getParameter("mid");
		String sharerId = request.getParameter("sharer");
		String count = request.getParameter("count");
		String is3 = request.getParameter("is3");	
		
		ActivityBean activity = activityService.ById(Integer.parseInt(id));
		if(activity != null){
			if(!StringGlobal.IsNull(memberId))
				activity.setMember_id(Integer.parseInt(memberId));
			if(!StringGlobal.IsNull(sharerId))
				activity.setSharer_id(Integer.parseInt(sharerId));
			if(!StringGlobal.IsNull(count))
				activity.setCount(Integer.parseInt(count));
			if(!StringGlobal.IsNull(is3))
				activity.setIs3(Integer.parseInt(is3));
			activity.setModify_time(new Date());
			activityService.Modify(activity);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", activity.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}