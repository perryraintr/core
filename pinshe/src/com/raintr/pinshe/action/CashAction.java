package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CashBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.service.CashService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class CashAction extends BaseAction {
	@Autowired
	private CashService cashService;
	
	@RequestMapping(value = "/cash")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String storeId = request.getParameter("sid");
		String page = request.getParameter("page");

		CashBean cash;
		List<CashBean> cashs;
		StoreBean store;
		OrderBean order;
		MemberBean member;
		
		if(!StringGlobal.IsNull(storeId)){
			cashs = cashService.ByStoreId(Integer.parseInt(storeId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(cashs != null && cashs.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < cashs.size(); i++){
					cash = cashs.get(i);
					
					store = cash.getStore();
					if(store == null)
						store = new StoreBean();
					
					order = cash.getOrder();
					if(order == null)
						order = new OrderBean();
					
					member = cash.getMember();
					if(member == null)
						member = new MemberBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", cash.ToId(""),
																						cash.ToAmount(""),
																						cash.ToType(""),
																						cash.ToStatus(""),
																						cash.ToCreate_time(""),
																						cash.ToModify_time(""),
																						store.ToId("store_"),
																						store.ToName("store_"),
																						member.ToId("member_"),
																						member.ToName("member_"),
																						member.ToWechat_id("member_"),
																						member.ToPhone("member_"),
																						order.ToOrder_no("order_")));
				
					json.append("},");
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(page)){
			cashs = cashService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(cashs != null && cashs.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < cashs.size(); i++){
					cash = cashs.get(i);
					
					store = cash.getStore();
					if(store == null)
						store = new StoreBean();
					
					order = cash.getOrder();
					if(order == null)
						order = new OrderBean();
					
					member = cash.getMember();
					if(member == null)
						member = new MemberBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", cash.ToId(""),
																						cash.ToAmount(""),
																						cash.ToType(""),
																						cash.ToStatus(""),
																						cash.ToCreate_time(""),
																						cash.ToModify_time(""),
																						store.ToId("store_"),
																						store.ToName("store_"),
																						member.ToId("member_"),
																						member.ToName("member_"),
																						member.ToWechat_id("member_"),
																						member.ToPhone("member_"),
																						order.ToOrder_no("order_")));
				
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(id)){
			cash = cashService.ById(Integer.parseInt(id));
			
			store = cash.getStore();
			if(store == null)
				store = new StoreBean();
			
			order = cash.getOrder();
			if(order == null)
				order = new OrderBean();
			
			member = cash.getMember();
			if(member == null)
				member = new MemberBean();
			
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", cash.ToId(""),
																				cash.ToAmount(""),
																				cash.ToType(""),
																				cash.ToStatus(""),
																				cash.ToCreate_time(""),
																				cash.ToModify_time(""),
																				store.ToId("store_"),
																				store.ToName("store_"),
																				member.ToId("member_"),
																				member.ToName("member_"),
																				member.ToWechat_id("member_"),
																				member.ToPhone("member_"),
																				order.ToOrder_no("order_")));
		
				
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}