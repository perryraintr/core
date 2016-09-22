package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.FeedbackBean;
import com.raintr.pinshe.service.FeedbackService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class AddFeedbackAction extends BaseAction {
	@Autowired
	private FeedbackService feedbackService;
	
	@RequestMapping(value = "/addfeedback")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String name = request.getParameter("name");
		String email = request.getParameter("email");

		if(StringGlobal.IsNull(name)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "name is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(email)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "email is null"));
			return null;
		}
		
		name = StringGlobal.SerializeJson(name);
		email = StringGlobal.SerializeJson(email);
		
		FeedbackBean feedback = new FeedbackBean();
		feedback.setName(name);
		feedback.setE_mail(email);
		feedback.setCreate_time(new Date());
		feedback.setModify_time(new Date());
		feedback.setId(feedbackService.Add(feedback));
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", feedback.ToId("")));
		return null;
	}
}