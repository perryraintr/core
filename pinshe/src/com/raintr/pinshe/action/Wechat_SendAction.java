package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.service.WechatService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Wechat_SendAction extends BaseAction {
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(value = "/wechat_send")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String wechatId = request.getParameter("wcid");
		String message = request.getParameter("m");
		String express = request.getParameter("express");
		String title = request.getParameter("title");
		String deliver = request.getParameter("deliver");
		String order = request.getParameter("order");
		String content = request.getParameter("content");
		String storeId = request.getParameter("sid");
		String orderId = request.getParameter("oid");
		String amount = request.getParameter("amount");
		String url = request.getParameter("url");
		String name = request.getParameter("name");
		String time = request.getParameter("time");
		String way = request.getParameter("way");
		
		
		if(!StringGlobal.IsNull(wechatId) && !StringGlobal.IsNull(message)){
			response.getWriter().print(String.format("%s", wechatService.Send(wechatId, message)));
			return null;
		}
	
		if(!StringGlobal.IsNull(wechatId) && !StringGlobal.IsNull(express) && !StringGlobal.IsNull(deliver) && !StringGlobal.IsNull(order)){
			if(StringGlobal.IsNull(content))
				content = "";
			if(StringGlobal.IsNull(title))
				title = "";
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":%s}", wechatService.SendTemplate(wechatId, express, title, deliver, order, content)));
			return null;
		}
		
		if(!StringGlobal.IsNull(wechatId) && !StringGlobal.IsNull(amount) && !StringGlobal.IsNull(order)){
			if(StringGlobal.IsNull(content))
				content = "";
			if(StringGlobal.IsNull(title))
				title = "";
			if(StringGlobal.IsNull(url))
				url = "";
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":%s}", wechatService.SendTemplatePaid(wechatId, url, title, amount, order, content)));
			return null;
		}
		
		if(!StringGlobal.IsNull(wechatId) && !StringGlobal.IsNull(amount) && !StringGlobal.IsNull(name) && !StringGlobal.IsNull(way) && !StringGlobal.IsNull(time)){
			if(StringGlobal.IsNull(title))
				title = "";
			if(StringGlobal.IsNull(url))
				url = "";
			if(StringGlobal.IsNull(name))
				name = "";
			if(StringGlobal.IsNull(time))
				time = "";
			if(StringGlobal.IsNull(amount))
				amount = "";
			if(StringGlobal.IsNull(way))
				way = "";
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":%s}", wechatService.SendTemplateWithdraw(wechatId, url, title, name, time, amount, way)));
			return null;
		}
		

		
		if(!StringGlobal.IsNull(wechatId) && !StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("%s", wechatService.SendStore(wechatId, storeId, orderId)));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}