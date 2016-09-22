package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.CartBean;
import com.raintr.pinshe.service.CartService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class CartAction extends BaseAction {
	@Autowired
	private CartService cartService;
	
	@RequestMapping(value = "/cart")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");
		String page = request.getParameter("page");

		CartBean cart;
		List<CartBean> carts;
		MemberBean member;
		CommodityBean commodity;
		List<CommodityImageBean> images;
		
		if(!StringGlobal.IsNull(memberId)){
			carts = cartService.ByMemberId(Integer.parseInt(memberId));
			if(carts != null && carts.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < carts.size(); i++){
					cart = carts.get(i);
					commodity = cart.getCommodity();
					
					if(commodity == null)
						commodity = new CommodityBean();
					
					images = commodity.getImages();
					
					json.append("{");
					
					if(images != null && images.size() > 0)
						json.append(String.format("\"image\":\"%s\",", images.get(0).getUrl()));
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s", 	cart.ToId("cart_"),
																			commodity.ToId("commodity_"),
																			commodity.ToName("commodity_"),
																			commodity.ToDescription("commodity_"),
																			commodity.ToPrice("commodity_"),
																			commodity.ToCount("commodity_"),
																			cart.ToCount("cart_"),
																			cart.ToCreate_time("cart_"),
																			cart.ToModify_time("cart_")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
				
			}
		}
		
		
		if(!StringGlobal.IsNull(page)){
			carts = cartService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(carts != null && carts.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < carts.size(); i++){
					cart = carts.get(i);
					member = cart.getMemeber();
					
					if(member == null)
						member = new MemberBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s", 	cart.ToId(""),
																cart.ToCount(""),
																cart.ToCreate_time(""),
																cart.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}