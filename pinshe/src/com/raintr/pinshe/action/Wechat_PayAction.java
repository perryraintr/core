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
public class Wechat_PayAction extends BaseAction {
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(value = "/pay/wechat_pay")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String wechatId = request.getParameter("wcid");
		String orderNo = request.getParameter("order_no");
		String amount = request.getParameter("amount");
		
		String[] rows = orderNo.split("-");

		if(rows != null && rows.length > 2){
			model.put("orderno", rows[0]);
			model.put("from", rows[1]);
			model.put("id", rows[2]);
		}
		
//		wechatId = "o1D_JwGKMNWZmBYLxghYYw0GIlUg";
//		orderNo = "E201608081615020085728562-1-111";
//		amount = "1";
		
		model.put("data", wechatService.GetPrepay(wechatId, orderNo, amount));
		return "/wechat_pay";
		
	} 
}