package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.service.WechatService;

@Controller
@RequestMapping(value = "/")
public class Wechat_AutoreplyAction extends BaseAction {	
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(value = "/wechat_autoreply")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}

	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.getWriter().print(String.format("%s", wechatService.GetAutoreply()));
		return null;
	} 
}