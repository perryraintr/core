package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CartBean;
import com.raintr.pinshe.service.CartService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Cart_ModifyAction extends BaseAction {
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value = "/cart_modify")
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
		String commodityId = request.getParameter("cid");
		String count = request.getParameter("count");
		
		CartBean cart = cartService.ById(Integer.parseInt(id));
		if(cart != null){
			if(!StringGlobal.IsNull(memberId)) 
				cart.setMember_id(Integer.parseInt(memberId));
			if(!StringGlobal.IsNull(commodityId)) 
				cart.setCommodity_id(Integer.parseInt(commodityId));
			if(!StringGlobal.IsNull(count)) 
				cart.setCount(Integer.parseInt(count));
			
			cart.setModify_time(new Date());
			
			cartService.Modify(cart);

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s,%s,%s}}", 	cart.ToId(""),
																								cart.ToCount(""),
																								cart.ToCreate_time(""),
																								cart.ToModify_time("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}