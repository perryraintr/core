package com.raintr.pinshe.action;

import java.util.Date;
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
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.service.CashService;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.service.OrderService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Cash_AddAction extends BaseAction {
	@Autowired
	private CashService cashService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/cash_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String memberId = request.getParameter("mid");
		String orderId = request.getParameter("oid");
		String amount = request.getParameter("amount");
		String type = request.getParameter("type");
		String status = request.getParameter("status");

		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(memberId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "mid is null"));
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
			OrderBean order = orderService.ById(Integer.parseInt(orderId));
			if(order != null){
				List<OrderDetailBean> orderDetails = order.getOrderDetails();
				if(orderDetails != null && orderDetails.size() > 0){
					OrderDetailBean orderDetail = orderDetails.get(0);
					if(orderDetail != null){
						CashBean cash = cashService.ByOrderId(order.getId());
						if(cash == null){
							cash = new CashBean();
							cash.setStore_id(orderDetail.getStore_id());
							cash.setMember_id(order.getMember_id());
							cash.setOrder_id(order.getId());
							cash.setAmount(order.getAmount());
							cash.setType(1);
							cash.setStatus(1);
							cash.setCreate_time(new Date());
							cash.setModify_time(new Date());
							cashService.Add(cash);
							
							StoreBean store = orderDetail.getStore();
							if(store != null){
								MemberBean member = store.getMember();
								if(member != null){
									member.setCurrent(member.getCurrent() + cash.getAmount());
									member.setAmount(member.getAmount() + cash.getAmount());
									memberService.Modify(member);
								}
							}
							
							StringBuffer json = new StringBuffer();
							json.append(String.format("%s,%s,%s,%s,%s,%s", 	cash.ToId(""),
																			cash.ToAmount(""),
																			cash.ToType(""),
																			cash.ToStatus(""),
																			cash.ToCreate_time(""),
																			cash.ToModify_time("")));
					
							response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
							return null;
							
						}
					}
				}
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if("-1".equals(type)){
			MemberBean member = memberService.ById(Integer.parseInt(memberId));
			
			if(member.getCurrent() >= Double.parseDouble(amount)){
				CashBean cash = new CashBean();
				if(!StringGlobal.IsNull(storeId))
					cash.setStore_id(Integer.parseInt(storeId));
				if(!StringGlobal.IsNull(memberId))
					cash.setMember_id(Integer.parseInt(memberId));
				if(!StringGlobal.IsNull(orderId))
					cash.setOrder_id(Integer.parseInt(orderId));
				if(!StringGlobal.IsNull(amount))
					cash.setAmount(Double.parseDouble(amount));
				if(!StringGlobal.IsNull(type))
					cash.setType(Integer.parseInt(type));
				if(!StringGlobal.IsNull(status))
					cash.setStatus(Integer.parseInt(status));
				cash.setCreate_time(new Date());
				cash.setModify_time(new Date());
				cash.setId(cashService.Add(cash));
				
				member.setCurrent(member.getCurrent() - cash.getAmount());
				memberService.Modify(member);
				
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s", 	cash.ToId(""),
																cash.ToAmount(""),
																cash.ToType(""),
																cash.ToStatus(""),
																cash.ToCreate_time(""),
																cash.ToModify_time("")));
		
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