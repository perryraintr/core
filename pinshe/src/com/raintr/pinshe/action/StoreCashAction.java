package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.bean.StoreCashBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.service.StoreCashService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreCashAction extends BaseAction {
	@Autowired
	private StoreCashService storeCashService;
	
	@RequestMapping(value = "/store_cash")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String storeId = request.getParameter("sid");
		String type = request.getParameter("type");
		String page = request.getParameter("page");

		StoreCashBean storeCash;
		List<StoreCashBean> storeCashs;
		StoreBean store;
		OrderBean order;
		MemberBean member;
		MerchantBean merchant;

		if(!StringGlobal.IsNull(storeId)){
			storeCashs = storeCashService.ByStoreId(Integer.parseInt(storeId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(storeCashs != null && storeCashs.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < storeCashs.size(); i++){
					storeCash = storeCashs.get(i);
					
					store = storeCash.getStore();
					if(store == null)
						store = new StoreBean();
					
					order = storeCash.getOrder();
					if(order == null)
						order = new OrderBean();
					
					member = storeCash.getMember();
					if(member == null)
						member = new MemberBean();
					
					merchant = storeCash.getMerchant();
					if(merchant == null)
						merchant = new MerchantBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	storeCash.ToId(""),
																											storeCash.ToAmount(""),
																											storeCash.ToTotal(""),
																											storeCash.ToType(""),
																											storeCash.ToStatus(""),
																											storeCash.ToCreate_time(""),
																											storeCash.ToModify_time(""),
																											store.ToId("store_"),
																											store.ToName("store_"),
																											store.ToPayment(""),
																											member.ToId("member_"),
																											member.ToName("member_"),
																											member.ToWechat_id("member_"),
																											member.ToPhone("member_"),
																											merchant.ToId("merchant_"),
																											merchant.ToName("merchant_"),
																											merchant.ToWechat_id("merchant_"),
																											merchant.ToPhone("merchant_"),
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
		
		if(!StringGlobal.IsNull(type)){
			storeCashs = storeCashService.ByType(Integer.parseInt(type), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(storeCashs != null && storeCashs.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < storeCashs.size(); i++){
					storeCash = storeCashs.get(i);
					
					store = storeCash.getStore();
					if(store == null)
						store = new StoreBean();
					
					order = storeCash.getOrder();
					if(order == null)
						order = new OrderBean();
					
					member = storeCash.getMember();
					if(member == null)
						member = new MemberBean();
					
					merchant = storeCash.getMerchant();
					if(merchant == null)
						merchant = new MerchantBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	storeCash.ToId(""),
																											storeCash.ToAmount(""),
																											storeCash.ToTotal(""),
																											storeCash.ToType(""),
																											storeCash.ToStatus(""),
																											storeCash.ToCreate_time(""),
																											storeCash.ToModify_time(""),
																											store.ToId("store_"),
																											store.ToName("store_"),
																											store.ToPayment(""),
																											member.ToId("member_"),
																											member.ToName("member_"),
																											member.ToWechat_id("member_"),
																											member.ToPhone("member_"),
																											merchant.ToId("merchant_"),
																											merchant.ToName("merchant_"),
																											merchant.ToWechat_id("merchant_"),
																											merchant.ToPhone("merchant_"),
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
			storeCashs = storeCashService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(storeCashs != null && storeCashs.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < storeCashs.size(); i++){
					storeCash = storeCashs.get(i);
					
					store = storeCash.getStore();
					if(store == null)
						store = new StoreBean();
					
					order = storeCash.getOrder();
					if(order == null)
						order = new OrderBean();
					
					member = storeCash.getMember();
					if(member == null)
						member = new MemberBean();
					
					merchant = storeCash.getMerchant();
					if(merchant == null)
						merchant = new MerchantBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	storeCash.ToId(""),
																											storeCash.ToAmount(""),
																											storeCash.ToTotal(""),
																											storeCash.ToType(""),
																											storeCash.ToStatus(""),
																											storeCash.ToCreate_time(""),
																											storeCash.ToModify_time(""),
																											store.ToId("store_"),
																											store.ToName("store_"),
																											store.ToPayment(""),
																											member.ToId("member_"),
																											member.ToName("member_"),
																											member.ToWechat_id("member_"),
																											member.ToPhone("member_"),
																											merchant.ToId("merchant_"),
																											merchant.ToName("merchant_"),
																											merchant.ToWechat_id("merchant_"),
																											merchant.ToPhone("merchant_"),
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
			storeCash = storeCashService.ById(Integer.parseInt(id));
			
			store = storeCash.getStore();
			if(store == null)
				store = new StoreBean();
			
			order = storeCash.getOrder();
			if(order == null)
				order = new OrderBean();
			
			member = storeCash.getMember();
			if(member == null)
				member = new MemberBean();
			
			merchant = storeCash.getMerchant();
			if(merchant == null)
				merchant = new MerchantBean();
			
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	storeCash.ToId(""),
																									storeCash.ToAmount(""),
																									storeCash.ToTotal(""),
																									storeCash.ToType(""),
																									storeCash.ToStatus(""),
																									storeCash.ToCreate_time(""),
																									storeCash.ToModify_time(""),
																									store.ToId("store_"),
																									store.ToName("store_"),
																									store.ToPayment(""),
																									member.ToId("member_"),
																									member.ToName("member_"),
																									member.ToWechat_id("member_"),
																									member.ToPhone("member_"),
																									merchant.ToId("merchant_"),
																									merchant.ToName("merchant_"),
																									merchant.ToWechat_id("merchant_"),
																									merchant.ToPhone("merchant_"),
																									order.ToOrder_no("order_")));
		
				
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}