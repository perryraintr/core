package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.SubscriptionBean;
import com.raintr.pinshe.service.SubscriptionService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class SubscriptionAction extends BaseAction {
	@Autowired
	private SubscriptionService subscriptionService;
	
	@RequestMapping(value = "/subscription")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");
		
		SubscriptionBean subscription;
		
		if(!StringGlobal.IsNull(memberId)){
			StringBuffer json = new StringBuffer();
			subscription = subscriptionService.ByMemberId(Integer.parseInt(memberId));
			if(subscription != null){
				json.append(String.format("%s", 	subscription.ToId("")));
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}