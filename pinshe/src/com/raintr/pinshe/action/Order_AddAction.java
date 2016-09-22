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
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.service.CommodityService;
import com.raintr.pinshe.service.OrderDetailService;
import com.raintr.pinshe.service.OrderService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Order_AddAction extends BaseAction {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private CommodityService commodityService;
	
	@RequestMapping(value = "/order_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");
		String consigneeId = request.getParameter("cneeid");
		String couponId = request.getParameter("couponid");
		String count = request.getParameter("count");
		String current = request.getParameter("current");
		String amount = request.getParameter("amount");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		String grind = request.getParameter("grind");
		String cost = request.getParameter("cost");
		String memo = request.getParameter("memo");
		
		memo = StringGlobal.SerializeJson(memo);
		
		String cids = request.getParameter("cids");
		String sids = request.getParameter("sids");
		String counts = request.getParameter("counts");
		String amounts = request.getParameter("amounts");
		
		// sku
		if(!StringGlobal.IsNull(cids)){
			String[] detail_cids = cids.split(",");
			String[] detail_counts = counts.split(",");
			
			CommodityBean commodity;
			
			for(int i = 0; i < detail_cids.length; i++){
				commodity = commodityService.ById(Integer.parseInt(detail_cids[i]));				
				if(commodity.getCount() >= Integer.parseInt(detail_counts[i])){
					commodity.setCount(commodity.getCount() - Integer.parseInt(detail_counts[i]));
					
					commodityService.Modify(commodity);
				}
				
				
				commodity = commodityService.ById(Integer.parseInt(detail_cids[i]));				
				if(commodity.getCount() < 0)
					throw new RuntimeException();
				
				if(commodity.getCount() == 0){
					response.getWriter().print("{\"head\":1,\"body\":{}}");
					return null;
				}
			}
		}
		
		
		
		OrderBean order = new OrderBean();
		if(!StringGlobal.IsNull(memberId))
			order.setMember_id(Integer.parseInt(memberId));
		if(!StringGlobal.IsNull(consigneeId))
			order.setConsignee_id(Integer.parseInt(consigneeId));
		if(!StringGlobal.IsNull(couponId))
			order.setCoupon_id(Integer.parseInt(couponId));
		order.setOrder_no(order.MadeOrderNo());
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
		order.setCreate_time(new Date());
		order.setModify_time(new Date());
		order.setId(orderService.Add(order));

		
		if(!StringGlobal.IsNull(cids)){
			String[] detail_cids = cids.split(",");
			String[] detail_counts = counts.split(",");
			String[] detail_amounts = amounts.split(",");
			
			OrderDetailBean orderDetail;
			for(int i = 0; i < detail_cids.length; i++){
				if(!StringGlobal.IsNull(detail_cids[i])){
					orderDetail = new OrderDetailBean();
					orderDetail.setOrder_id(order.getId());
					orderDetail.setCommodity_id(Integer.parseInt(detail_cids[i]));
					orderDetail.setStore_id(0);
					orderDetail.setCount(Integer.parseInt(detail_counts[i]));
					orderDetail.setAmount(Double.parseDouble(detail_amounts[i]));
					orderDetail.setCreate_time(new Date());
					orderDetail.setModify_time(new Date());
					orderDetailService.Add(orderDetail);
				}
			}
		}else{
			if(!StringGlobal.IsNull(sids)){
				String[] detail_sids = sids.split(",");
				String[] detail_counts = counts.split(",");
				String[] detail_amounts = amounts.split(",");
				
				OrderDetailBean orderDetail;
				for(int i = 0; i < detail_sids.length; i++){
					if(!StringGlobal.IsNull(detail_sids[i])){
						orderDetail = new OrderDetailBean();
						orderDetail.setOrder_id(order.getId());
						orderDetail.setCommodity_id(0);
						orderDetail.setStore_id(Integer.parseInt(detail_sids[i]));
						orderDetail.setCount(Integer.parseInt(detail_counts[i]));
						orderDetail.setAmount(Double.parseDouble(detail_amounts[i]));
						orderDetail.setCreate_time(new Date());
						orderDetail.setModify_time(new Date());
						orderDetailService.Add(orderDetail);
					}
				}
			}
		}

		
		StringBuffer json = new StringBuffer();
		json.append(String.format("%s,%s,%s,%s,%s", order.ToId(""),
													order.ToOrder_no(""),
													order.ToAmount(""),
													order.ToCreate_time(""),
													order.ToModify_time("")));

		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
		return null;
	}
}