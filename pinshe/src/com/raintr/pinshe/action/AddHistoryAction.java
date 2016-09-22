package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.HistoryBean;
import com.raintr.pinshe.service.HistoryService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class AddHistoryAction extends BaseAction {
	@Autowired
	private HistoryService historyService;
	
	@RequestMapping(value = "/addhistory")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String userId = request.getParameter("uid");
		String voteId = request.getParameter("vid");
		
		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(voteId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "vid is null"));
			return null;
		}
		
		HistoryBean history = historyService.ByUserIdVoteId(Integer.parseInt(userId), Integer.parseInt(voteId));
		if(history == null){
			history = new HistoryBean();
			history.setUser_id(Integer.parseInt(userId));
			history.setVote_id(Integer.parseInt(voteId));
			history.setCreate_time(new Date());
			history.setModify_time(new Date());
			history.setId(historyService.Add(history));
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", history.ToId("")));
			return null;
		}
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", history.ToId("")));
		return null;
	}
}