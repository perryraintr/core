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
public class Wechat_QrcodeAction extends BaseAction {	
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(value = "/wechat_qrcode")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}

	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String commodityId = request.getParameter("cid");
		String storeId = request.getParameter("sid");

		if(!StringGlobal.IsNull(commodityId)){
			response.getWriter().print(String.format("%s", wechatService.GetQrcode("0_" + commodityId)));
			return null;
		}
		
		if(!StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("%s", wechatService.GetQrcode("1_" + storeId)));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	} 
}