package com.raintr.pinshe.action;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreCashBean;
import com.raintr.pinshe.service.CartService;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.service.OrderDetailService;
import com.raintr.pinshe.service.OrderService;
import com.raintr.pinshe.service.StoreCashService;
import com.raintr.pinshe.service.StoreService;
import com.raintr.pinshe.utils.FileGlobal;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Wechat_NotifyAction extends BaseAction {
	@Autowired
	private MemberService memberService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	@Autowired
	private CartService cartService;
	@Autowired
	private StoreCashService storeCashService;
	@Autowired
	private StoreService storeService;
	
	@RequestMapping(value = "/wechat_notify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	// orderno - 1 - 0
	// orderno - 2 - cid
	// orderno - 3 - sid
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		response.setContentType("application/xml");
		
		String xml = IOUtils.toString(request.getInputStream());
		
		FileGlobal.AddFile(xml, "", "/opt/log/notify");
		
//		String wechatId = null;
		String orderNo = null;
		Element element;
		
		if(!StringGlobal.IsNull(xml)){
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(new ByteArrayInputStream(xml.getBytes("utf-8")));
			Element root = doc.getRootElement();
			List<Element> elements = root.getChildren();
			if (elements != null && elements.size() != 0) {
				for(int i = 0; i < elements.size(); i++){
					element = elements.get(i);
//					if("openid".equals(element.getName()))
//						wechatId = element.getText();
					if("out_trade_no".equals(element.getName())){
						orderNo = element.getText();
						break;
					}
				}
			}
		}
		
		//orderNo = "1476204768412-3-83";
		
		if(!StringGlobal.IsNull(orderNo)){
			String[] rows = orderNo.split("-");
			if(rows.length == 3){
				OrderBean order = orderService.ByOrderNo(rows[0]);
				if(order != null && order.getStatus() == 0){
					if("1".equals(rows[1])){
						order.setType(2);
						order.setStatus(1);
						orderService.Modify(order);
						
						MemberBean member = order.getMember();
						if(member != null){
							OrderDetailBean orderDetail;
							List<OrderDetailBean> orderDetails = order.getOrderDetails();
							for(int i = 0; i < orderDetails.size(); i++){
								orderDetail = orderDetails.get(i);
								if(orderDetail != null)
									cartService.RemoveMemberIdCommodityId(member.getId(), orderDetail.getCommodity_id());
							}
						}
	
						response.getWriter().print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
						return null;
					}
					
					if("2".equals(rows[1])){
						order.setType(2);
						order.setStatus(3);
						orderService.Modify(order);
						
						MemberBean member = order.getMember();
						if(member != null){
							if("114".equals(rows[2])){
								member.setCurrent(member.getCurrent() + 220);
								member.setAmount(member.getAmount() + 220);
								memberService.Modify(member);
							}else{
								if("113".equals(rows[2])){
									member.setCurrent(member.getCurrent() + 600);
									member.setAmount(member.getAmount() + 600);
									memberService.Modify(member);
								}else{
									if("112".equals(rows[2])){
										member.setCurrent(member.getCurrent() + 1000);
										member.setAmount(member.getAmount() + 1000);
										memberService.Modify(member);
									}else{
										if("111".equals(rows[2])){
											member.setCurrent(member.getCurrent() + 2000);
											member.setAmount(member.getAmount() + 2000);
											memberService.Modify(member);
										}else{
											if("126".equals(rows[2])){
												if(order.getAmount() >= 800){// 25%
													member.setCurrent(member.getCurrent() + order.getAmount() + order.getAmount() * 0.25);
													member.setAmount(member.getAmount() + order.getAmount() + order.getAmount() * 0.25);
												}else{
													if(order.getAmount() >= 500){// 20%
														member.setCurrent(member.getCurrent() + order.getAmount() + order.getAmount() * 0.2);
														member.setAmount(member.getAmount() + order.getAmount() + order.getAmount() * 0.2);
													}else{
														if(order.getAmount() >= 200){// 10%
															member.setCurrent(member.getCurrent() + order.getAmount() + order.getAmount() * 0.1);
															member.setAmount(member.getAmount() + order.getAmount() + order.getAmount() * 0.1);
														}else{
															member.setCurrent(member.getCurrent() + order.getAmount());
															member.setAmount(member.getAmount() + order.getAmount());
														}
													}
												}

												memberService.Modify(member);
											}
										}
									}
								}
							}
						}
	
						response.getWriter().print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
						return null;
					}
					
					if("3".equals(rows[1])){
						order.setType(2);
						order.setStatus(3);
						orderService.Modify(order);
						
						List<OrderDetailBean> orderDetails = order.getOrderDetails();
						if(orderDetails != null && orderDetails.size() > 0){
							OrderDetailBean orderDetail = orderDetails.get(0);
							if(orderDetail != null){
								StoreBean store = orderDetail.getStore();
								if(store != null){
									store.setCurrent(store.getCurrent() + order.getAmount());
									storeService.Modify(store, null, null);
									
									StoreCashBean storeCash = storeCashService.ByOrderId(order.getId());
									if(storeCash == null){
										storeCash = new StoreCashBean();
										storeCash.setStore_id(store.getId());
										storeCash.setMember_id(order.getMember_id());
										storeCash.setMerchant_id(0);
										storeCash.setOrder_id(order.getId());
										storeCash.setAmount(order.getAmount());
										storeCash.setTotal(store.getCurrent());
										storeCash.setType(1);
										storeCash.setStatus(1);
										storeCash.setCreate_time(new Date());
										storeCash.setModify_time(new Date());
										storeCashService.Add(storeCash);
									}
								}
							}
						}
						
						response.getWriter().print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
						return null;
					}
				}
			}
		}

		response.getWriter().print("<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");
		return null;
	} 
}