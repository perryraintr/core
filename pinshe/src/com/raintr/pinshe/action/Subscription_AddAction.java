package com.raintr.pinshe.action;

import java.util.Calendar;
import java.util.Date;

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
public class Subscription_AddAction extends BaseAction {
	@Autowired
	private SubscriptionService subscriptionService;
	
	@RequestMapping(value = "/subscription_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");
		String day = request.getParameter("day");
		String commodity_id1 = request.getParameter("cid1");
		String commodity_id2 = request.getParameter("cid2");
		String commodity_id3 = request.getParameter("cid3");
		String commodity_id4 = request.getParameter("cid4");
		String commodity_id5 = request.getParameter("cid5");
		String commodity_id6 = request.getParameter("cid6");

		if(StringGlobal.IsNull(memberId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "mid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(day)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "day is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(commodity_id1)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "cid1 is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(commodity_id2)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "cid2 is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(commodity_id3)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "cid3 is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(commodity_id4)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "cid4 is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(commodity_id5)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "cid5 is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(commodity_id6)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "cid6 is null"));
			return null;
		}
		
		SubscriptionBean subscription = new SubscriptionBean();
		if(!StringGlobal.IsNull(memberId))
			subscription.setMember_id(Integer.parseInt(memberId));
		if(!StringGlobal.IsNull(day))
			subscription.setDay(Integer.parseInt(day));
		if(!StringGlobal.IsNull(commodity_id1))
			subscription.setCommodity_id1(Integer.parseInt(commodity_id1));
		if(!StringGlobal.IsNull(commodity_id2))
			subscription.setCommodity_id2(Integer.parseInt(commodity_id2));
		if(!StringGlobal.IsNull(commodity_id3))
			subscription.setCommodity_id3(Integer.parseInt(commodity_id3));
		if(!StringGlobal.IsNull(commodity_id4))
			subscription.setCommodity_id4(Integer.parseInt(commodity_id4));
		if(!StringGlobal.IsNull(commodity_id5))
			subscription.setCommodity_id5(Integer.parseInt(commodity_id5));
		if(!StringGlobal.IsNull(commodity_id6))
			subscription.setCommodity_id6(Integer.parseInt(commodity_id6));
		subscription.setStatus1(0);
		subscription.setStatus2(0);
		subscription.setStatus3(0);
		subscription.setStatus4(0);
		subscription.setStatus5(0);
		subscription.setStatus6(0);
		
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, Integer.parseInt(day));
		subscription.setNext_time(now.getTime());
		
		subscription.setCreate_time(new Date());
		subscription.setModify_time(new Date());
		subscription.setId(subscriptionService.Add(subscription));

		StringBuffer json = new StringBuffer();
		json.append(String.format("%s,%s,%s", 	subscription.ToId(""),
												subscription.ToCreate_time(""),
												subscription.ToModify_time("")));
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
		return null;
	}
}