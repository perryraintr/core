package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreCashBean;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.service.OrderService;
import com.raintr.pinshe.service.StoreCashService;
import com.raintr.pinshe.service.StoreService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreCash_AddAction extends BaseAction {
	@Autowired
	private StoreCashService storeCashService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private StoreService storeService;
	
	@RequestMapping(value = "/store_cash_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String memberId = request.getParameter("memberid");
		String merchantId = request.getParameter("merchantid");
		String orderId = request.getParameter("oid");
		String amount = request.getParameter("amount");
		String type = request.getParameter("type");
		String status = request.getParameter("status");

		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(orderId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "oid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(amount)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "amount is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(type)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "type is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(status)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "status is null"));
			return null;
		}
		
		
		if("1".equals(type)){
			StoreBean store = storeService.ById(Integer.parseInt(storeId));
			if(store != null){
				store.setCurrent(store.getCurrent() + Double.parseDouble(amount));
				storeService.Modify(store, null, null);
				
				StoreCashBean storeCash = new StoreCashBean();
				if(!StringGlobal.IsNull(storeId))
					storeCash.setStore_id(Integer.parseInt(storeId));
				if(!StringGlobal.IsNull(memberId))
					storeCash.setMember_id(Integer.parseInt(memberId));
				storeCash.setMerchant_id(0);
				if(!StringGlobal.IsNull(orderId))
					storeCash.setOrder_id(Integer.parseInt(orderId));
				if(!StringGlobal.IsNull(amount))
					storeCash.setAmount(Double.parseDouble(amount));
				storeCash.setTotal(store.getCurrent());
				if(!StringGlobal.IsNull(type))
					storeCash.setType(Integer.parseInt(type));
				if(!StringGlobal.IsNull(status))
					storeCash.setStatus(Integer.parseInt(status));
				storeCash.setCreate_time(new Date());
				storeCash.setModify_time(new Date());
				storeCash.setId(storeCashService.Add(storeCash));
				
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s", 	storeCash.ToId(""),
																	storeCash.ToAmount(""),
																	storeCash.ToTotal(""),
																	storeCash.ToType(""),
																	storeCash.ToStatus(""),
																	storeCash.ToCreate_time(""),
																	storeCash.ToModify_time("")));
		
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if("-1".equals(type)){
			StoreBean store = storeService.ById(Integer.parseInt(storeId));
			
			if(store.getCurrent() >= Double.parseDouble(amount)){
				store.setCurrent(store.getCurrent() - Double.parseDouble(amount));
				storeService.Modify(store, null, null);
				
				StoreCashBean storeCash = new StoreCashBean();
				if(!StringGlobal.IsNull(storeId))
					storeCash.setStore_id(Integer.parseInt(storeId));
				storeCash.setMember_id(0);				
				if(!StringGlobal.IsNull(merchantId))
					storeCash.setMerchant_id(Integer.parseInt(merchantId));
				if(!StringGlobal.IsNull(orderId))
					storeCash.setOrder_id(Integer.parseInt(orderId));
				if(!StringGlobal.IsNull(amount))
					storeCash.setAmount(Double.parseDouble(amount));
				storeCash.setTotal(store.getCurrent());
				if(!StringGlobal.IsNull(type))
					storeCash.setType(Integer.parseInt(type));
				if(!StringGlobal.IsNull(status))
					storeCash.setStatus(Integer.parseInt(status));
				storeCash.setCreate_time(new Date());
				storeCash.setModify_time(new Date());
				storeCash.setId(storeCashService.Add(storeCash));
				
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s", 	storeCash.ToId(""),
																	storeCash.ToAmount(""),
																	storeCash.ToTotal(""),
																	storeCash.ToType(""),
																	storeCash.ToStatus(""),
																	storeCash.ToCreate_time(""),
																	storeCash.ToModify_time("")));
		
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