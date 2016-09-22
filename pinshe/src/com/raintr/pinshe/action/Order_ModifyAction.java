package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.service.CashService;
import com.raintr.pinshe.service.CommodityService;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.service.OrderService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Order_ModifyAction extends BaseAction {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CashService cashService;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/order_modify")
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
		String consigneeId = request.getParameter("cneeid");
		String couponId = request.getParameter("couponid");
		String orderNo = request.getParameter("orderno");
		String count = request.getParameter("count");
		String current = request.getParameter("current");
		String amount = request.getParameter("amount");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		String grind = request.getParameter("grind");
		String cost = request.getParameter("cost");
		String memo = request.getParameter("memo");
		
		OrderBean order = orderService.ById(Integer.parseInt(id));
		if(order != null){
			memo = StringGlobal.SerializeJson(memo);

			if(!StringGlobal.IsNull(memberId))
				order.setMember_id(Integer.parseInt(memberId));
			if(!StringGlobal.IsNull(consigneeId))
				order.setConsignee_id(Integer.parseInt(consigneeId));
			if(!StringGlobal.IsNull(couponId))
				order.setCoupon_id(Integer.parseInt(couponId));
			if(!StringGlobal.IsNull(orderNo))
				order.setOrder_no(orderNo);
			if(!StringGlobal.IsNull(count))
				order.setCount(Integer.parseInt(count));
			if(!StringGlobal.IsNull(current))
				order.setCurrent(Double.parseDouble(current));
			if(!StringGlobal.IsNull(amount))
				order.setAmount(Double.parseDouble(amount));
			if(!StringGlobal.IsNull(type))
				order.setType(Integer.parseInt(type));
			if(!StringGlobal.IsNull(status))
				order.setStatus(Integer.parseInt(status));
			if(!StringGlobal.IsNull(grind))
				order.setGrind(Integer.parseInt(grind));
			if(!StringGlobal.IsNull(cost))
				order.setCost(Double.parseDouble(cost));
			if(!StringGlobal.IsNull(memo))
				order.setMemo(memo);
			order.setModify_time(new Date());
			orderService.Modify(order);

			String cids = request.getParameter("cids");
			String counts = request.getParameter("counts");
			// sku
			if(!StringGlobal.IsNull(cids)){
				String[] detail_cids = cids.split(",");
				String[] detail_counts = counts.split(",");
				
				CommodityBean commodity;
				
				for(int i = 0; i < detail_cids.length; i++){
					commodity = commodityService.ById(Integer.parseInt(detail_cids[i]));
					commodity.setCount(commodity.getCount() + Integer.parseInt(detail_counts[i]));
					commodityService.Modify(commodity);
				}
			}
			
//			//cash
//			CashBean cash = cashService.ByOrderId(order.getId());
//			if(cash != null){
//				if(!StringGlobal.IsNull(amount))
//					cash.setAmount(Double.parseDouble(amount));
//				cash.setStatus(1);
//				cashService.Modify(cash);
//				
//				MemberBean member = memberService.ById(order.getMember_id());
//				if(member != null){
//					member.setCurrent(member.getCurrent() + cash.getAmount());
//					member.setAmount(member.getAmount() + cash.getAmount());
//					memberService.Modify(member);
//				}
//			}
			
			
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s", order.ToId(""),
														order.ToOrder_no(""),
														order.ToAmount(""),
														order.ToStatus(""),
														order.ToCreate_time(""),
														order.ToModify_time("")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}