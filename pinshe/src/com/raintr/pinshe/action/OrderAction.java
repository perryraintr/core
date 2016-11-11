package com.raintr.pinshe.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.bean.ConsigneeBean;
import com.raintr.pinshe.bean.CouponBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreCommentBean;
import com.raintr.pinshe.bean.StoreImageBean;
import com.raintr.pinshe.service.OrderService;
import com.raintr.pinshe.service.StoreCommentService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class OrderAction extends BaseAction {
	@Autowired
	private OrderService orderService;
	@Autowired
	private StoreCommentService storeCommentService;
	
	@RequestMapping(value = "/order")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String memberId = request.getParameter("mid");
		String page = request.getParameter("page");
		String status = request.getParameter("status");
		String commodityId = request.getParameter("cid");
		String vip = request.getParameter("vip");
		String storeId = request.getParameter("sid");
		String orderNo = request.getParameter("orderno");
		
		OrderBean order;
		List<OrderBean> orders;
		MemberBean member;
		CouponBean coupon;
		ConsigneeBean consignee;
		OrderDetailBean orderDetail;
		List<OrderDetailBean> orderDetails;
		CommodityBean commodity;
		List<CommodityImageBean> commodityImages;
		StoreBean store;
		List<StoreImageBean> storeImages;
		StoreCommentBean storeComment;
		
		if(!StringGlobal.IsNull(orderNo)){
			order = orderService.ByOrderNo(orderNo);
			if(order != null){
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", order.ToId(""),
																		order.ToOrder_no(""),
																		order.ToCurrent(""),
																		order.ToAmount(""),
																		order.ToSort(""),
																		order.ToType(""),
																		order.ToStatus(""),
																		order.ToCreate_time(""),
																		order.ToModify_time("")));
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(status) && !StringGlobal.IsNull(memberId)){
			orders = orderService.ByMemberId(Integer.parseInt(memberId), 0);
			if(orders != null && orders.size() > 0){
				for(int i = 0; i < orders.size(); i++){
					if(orders.get(i) != null && orders.get(i).getStatus() == 1){
						response.getWriter().print("{\"head\":1,\"body\":{\"status\":1}}");
						return null;
					}
				}
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{\"status\":0}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(page) && !StringGlobal.IsNull(memberId)){
			orders = orderService.ByMemberId(Integer.parseInt(memberId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(orders != null && orders.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < orders.size(); i++){
					order = orders.get(i);

					if(order == null)
						order = new OrderBean();
					
					coupon = order.getCoupon();
					if(coupon == null)
						coupon = new CouponBean();
					
					member = order.getMember();
					if(member == null)
						member = new MemberBean();
					
					consignee = order.getConsignee();
					if(consignee == null)
						consignee = new ConsigneeBean();
					
					orderDetails = order.getOrderDetails();

					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", order.ToId(""),
																							order.ToOrder_no(""),
																							order.ToCurrent(""),
																							order.ToAmount(""),
																							order.ToSort(""),
																							order.ToType(""),
																							order.ToStatus(""),
																							order.ToCreate_time(""),
																							order.ToModify_time(""),
																							coupon.ToId("coupon_"),
																							coupon.ToAmount("coupon_"),
																							consignee.ToName("consignee_"),
																							consignee.ToPhone("consignee_"),
																							consignee.ToAddress("consignee_")));
					
					json.append("\"details\":[");
					if(orderDetails != null && orderDetails.size() > 0){
						orderDetail = orderDetails.get(0);
						if(orderDetail == null)
							orderDetail = new OrderDetailBean();
						
						commodity = orderDetail.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();
						commodityImages = commodity.getImages();
						if(commodityImages == null){
							commodityImages = new ArrayList<CommodityImageBean>();
							commodityImages.add(new CommodityImageBean());
						}
						
						store = orderDetail.getStore();
						if(store == null)
							store = new StoreBean();
						storeImages = store.getImages();
						if(storeImages == null){
							storeImages = new ArrayList<StoreImageBean>();
							storeImages.add(new StoreImageBean());
						}
						
						storeComment = storeCommentService.ByStoreIdOrderIdMemberId(store.getId(), member.getId(), order.getId());
						if(storeComment == null)
							storeComment = new StoreCommentBean();
						
						
						json.append("{");
						json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,", 	orderDetail.ToId(""),
																				orderDetail.ToCount(""),
																				orderDetail.ToAmount(""),
																				commodity.ToId("commodity_"),
																				commodity.ToName("commodity_"),
																				commodity.ToPrice("commodity_"),
																				commodity.ToCount("commodity_"),
																				commodity.ToDescription("commodity_")));
						
						json.append(String.format("\"commodity_image\":\"%s\",", commodityImages.get(0).getUrl()));
//						json.append("\"commodity_images\":[");
//						if(commodityImages != null && commodityImages.size() > 0){
//							json.append(String.format("\"%s\"", commodityImages.get(0).getUrl()));
//							for(int k = 1; k < commodityImages.size(); k++){
//								json.append(String.format(",\"%s\"", commodityImages.get(k).getUrl()));
//							}
//						}
//						json.append("],");
						
						
						json.append(String.format("%s,%s,%s,", 	store.ToId("store_"),
																store.ToName("store_"),
																storeComment.ToId("store_comment_")));
						json.append(String.format("\"store_image\":\"%s\"", storeImages.get(0).getUrl()));
						//json.append("\"store_images\":[");
						//if(storeImages != null && storeImages.size() > 0){
						//json.append(String.format("\"%s\"", storeImages.get(0).getUrl()));
						//for(int k = 1; k < storeImages.size(); k++){
						//json.append(String.format(",\"%s\"", storeImages.get(k).getUrl()));
						//}
						//}
						//json.append("]");
						
						
						json.append("}");
						
						for(int j = 1; j < orderDetails.size(); j++){
							orderDetail = orderDetails.get(j);
							if(orderDetail == null)
								orderDetail = new OrderDetailBean();
								
							commodity = orderDetail.getCommodity();
							if(commodity == null)
								commodity = new CommodityBean();
							commodityImages = commodity.getImages();
							if(commodityImages == null){
								commodityImages = new ArrayList<CommodityImageBean>();
								commodityImages.add(new CommodityImageBean());
							}
							
							store = orderDetail.getStore();
							if(store == null)
								store = new StoreBean();
							storeImages = store.getImages();
							if(storeImages == null){
								storeImages = new ArrayList<StoreImageBean>();
								storeImages.add(new StoreImageBean());
							}
							
							storeComment = storeCommentService.ByStoreIdOrderIdMemberId(store.getId(), member.getId(), order.getId());
							if(storeComment == null)
								storeComment = new StoreCommentBean();
							
							json.append(",{");
							
							json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,", 	orderDetail.ToId(""),
																					orderDetail.ToCount(""),
																					orderDetail.ToAmount(""),
																					commodity.ToId("commodity_"),
																					commodity.ToName("commodity_"),
																					commodity.ToPrice("commodity_"),
																					commodity.ToCount("commodity_"),
																					commodity.ToDescription("commodity_")));

							json.append(String.format("\"commodity_image\":\"%s\",", commodityImages.get(0).getUrl()));
//							json.append("\"commodity_images\":[");
//							if(commodityImages != null && commodityImages.size() > 0){
//								json.append(String.format("\"%s\"", commodityImages.get(0).getUrl()));
//								for(int k = 1; k < commodityImages.size(); k++){
//									json.append(String.format(",\"%s\"", commodityImages.get(k).getUrl()));
//								}
//							}
//							json.append("],");
							
							
							json.append(String.format("%s,%s,%s,", 	store.ToId("store_"),
																	store.ToName("store_"),
																	storeComment.ToId("store_comment_")));
							json.append(String.format("\"store_image\":\"%s\"", storeImages.get(0).getUrl()));
//							json.append("\"store_images\":[");
//							if(storeImages != null && storeImages.size() > 0){
//								json.append(String.format("\"%s\"", storeImages.get(0).getUrl()));
//								for(int k = 1; k < storeImages.size(); k++){
//									json.append(String.format(",\"%s\"", storeImages.get(k).getUrl()));
//								}
//							}
//							json.append("]");
							
							json.append("}");
						}
					}
					json.append("]");
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
		
		if(!StringGlobal.IsNull(commodityId) && !StringGlobal.IsNull(page)){
			orders = orderService.ByCommodity(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);

			if(orders != null && orders.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < orders.size(); i++){
					order = orders.get(i);
					
					consignee = order.getConsignee();
					if(consignee == null)
						consignee = new ConsigneeBean();
					
					member = order.getMember();
					if(member == null)
						member = new MemberBean();
					
					orderDetails = order.getOrderDetails();

					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	order.ToId(""),
																											order.ToOrder_no(""),
																											order.ToCount(""),
																											order.ToCurrent(""),
																											order.ToAmount(""),
																											order.ToSort(""),
																											order.ToType(""),
																											order.ToStatus(""),
																											order.ToGrind(""),
																											order.ToCost(""),
																											order.ToMemo(""),
																											order.ToCreate_time(""),
																											order.ToModify_time(""),
																											consignee.ToName("consignee_"),
																											consignee.ToPhone("consignee_"),
																											consignee.ToAddress("consignee_"),
																											member.ToId("member_"),
																											member.ToName("member_"),
																											member.ToWechat_id("member_")));

					json.append("\"details\":[");
					if(orderDetails != null && orderDetails.size() > 0){
						orderDetail = orderDetails.get(0);
						if(orderDetail == null)
							orderDetail = new OrderDetailBean();
						
						commodity = orderDetail.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();

						store = orderDetail.getStore();
						if(store == null)
							store = new StoreBean();

						storeImages = store.getImages();
						if(storeImages == null){
							storeImages = new ArrayList<StoreImageBean>();
							storeImages.add(new StoreImageBean());
						}
						
						json.append("{");
						json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", orderDetail.ToId(""),
																					orderDetail.ToCount(""),
																					orderDetail.ToAmount(""),
																					commodity.ToId("commodity_"),
																					commodity.ToName("commodity_"),
																					commodity.ToPrice("commodity_"),
																					commodity.ToCount("commodity_"),
																					commodity.ToDescription("commodity_"),
																					store.ToId("store_"),
																					store.ToName("store_")));
						json.append(String.format("\"store_image\":\"%s\"", storeImages.get(0).getUrl()));
						
						json.append("}");
						
						for(int j = 1; j < orderDetails.size(); j++){
							orderDetail = orderDetails.get(j);
							if(orderDetail == null)
								orderDetail = new OrderDetailBean();
								
							commodity = orderDetail.getCommodity();
							if(commodity == null)
								commodity = new CommodityBean();
							
							store = orderDetail.getStore();
							if(store == null)
								store = new StoreBean();

							storeImages = store.getImages();
							if(storeImages == null){
								storeImages = new ArrayList<StoreImageBean>();
								storeImages.add(new StoreImageBean());
							}
							
							json.append(",{");
							
							json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", orderDetail.ToId(""),
																						orderDetail.ToCount(""),
																						orderDetail.ToAmount(""),
																						commodity.ToId("commodity_"),
																						commodity.ToName("commodity_"),
																						commodity.ToPrice("commodity_"),
																						commodity.ToCount("commodity_"),
																						commodity.ToDescription("commodity_"),
																						store.ToId("store_"),
																						store.ToName("store_")));
							
							json.append(String.format("\"store_image\":\"%s\"", storeImages.get(0).getUrl()));
							
							json.append("}");
						}
					}
					json.append("]");
					
					
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
		
		if(!StringGlobal.IsNull(vip) && !StringGlobal.IsNull(page)){
			orders = orderService.ByVip(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);

			if(orders != null && orders.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < orders.size(); i++){
					order = orders.get(i);
					
					consignee = order.getConsignee();
					if(consignee == null)
						consignee = new ConsigneeBean();
					
					member = order.getMember();
					if(member == null)
						member = new MemberBean();
					
					orderDetails = order.getOrderDetails();

					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	order.ToId(""),
																											order.ToOrder_no(""),
																											order.ToCount(""),
																											order.ToCurrent(""),
																											order.ToAmount(""),
																											order.ToSort(""),
																											order.ToType(""),
																											order.ToStatus(""),
																											order.ToGrind(""),
																											order.ToCost(""),
																											order.ToMemo(""),
																											order.ToCreate_time(""),
																											order.ToModify_time(""),
																											consignee.ToName("consignee_"),
																											consignee.ToPhone("consignee_"),
																											consignee.ToAddress("consignee_"),
																											member.ToId("member_"),
																											member.ToName("member_"),
																											member.ToWechat_id("member_")));

					json.append("\"details\":[");
					if(orderDetails != null && orderDetails.size() > 0){
						orderDetail = orderDetails.get(0);
						if(orderDetail == null)
							orderDetail = new OrderDetailBean();
						
						commodity = orderDetail.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();

						store = orderDetail.getStore();
						if(store == null)
							store = new StoreBean();

						storeImages = store.getImages();
						if(storeImages == null){
							storeImages = new ArrayList<StoreImageBean>();
							storeImages.add(new StoreImageBean());
						}
						
						json.append("{");
						json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", orderDetail.ToId(""),
																					orderDetail.ToCount(""),
																					orderDetail.ToAmount(""),
																					commodity.ToId("commodity_"),
																					commodity.ToName("commodity_"),
																					commodity.ToPrice("commodity_"),
																					commodity.ToCount("commodity_"),
																					commodity.ToDescription("commodity_"),
																					store.ToId("store_"),
																					store.ToName("store_")));
						json.append(String.format("\"store_image\":\"%s\"", storeImages.get(0).getUrl()));
						
						json.append("}");
						
						for(int j = 1; j < orderDetails.size(); j++){
							orderDetail = orderDetails.get(j);
							if(orderDetail == null)
								orderDetail = new OrderDetailBean();
								
							commodity = orderDetail.getCommodity();
							if(commodity == null)
								commodity = new CommodityBean();
							
							store = orderDetail.getStore();
							if(store == null)
								store = new StoreBean();

							storeImages = store.getImages();
							if(storeImages == null){
								storeImages = new ArrayList<StoreImageBean>();
								storeImages.add(new StoreImageBean());
							}
							
							json.append(",{");
							
							json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", orderDetail.ToId(""),
																						orderDetail.ToCount(""),
																						orderDetail.ToAmount(""),
																						commodity.ToId("commodity_"),
																						commodity.ToName("commodity_"),
																						commodity.ToPrice("commodity_"),
																						commodity.ToCount("commodity_"),
																						commodity.ToDescription("commodity_"),
																						store.ToId("store_"),
																						store.ToName("store_")));
							
							json.append(String.format("\"store_image\":\"%s\"", storeImages.get(0).getUrl()));
							
							json.append("}");
						}
					}
					json.append("]");
					
					
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
		
		if(!StringGlobal.IsNull(storeId) && !StringGlobal.IsNull(page)){
			if("all".equals(storeId))
				orders = orderService.ByStore(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			else
				orders = orderService.ByStoreId(Integer.parseInt(storeId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(orders != null && orders.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < orders.size(); i++){
					order = orders.get(i);
					
					consignee = order.getConsignee();
					if(consignee == null)
						consignee = new ConsigneeBean();
					
					member = order.getMember();
					if(member == null)
						member = new MemberBean();
					
					orderDetails = order.getOrderDetails();

					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	order.ToId(""),
																											order.ToOrder_no(""),
																											order.ToCount(""),
																											order.ToCurrent(""),
																											order.ToAmount(""),
																											order.ToSort(""),
																											order.ToType(""),
																											order.ToStatus(""),
																											order.ToGrind(""),
																											order.ToCost(""),
																											order.ToMemo(""),
																											order.ToCreate_time(""),
																											order.ToModify_time(""),
																											consignee.ToName("consignee_"),
																											consignee.ToPhone("consignee_"),
																											consignee.ToAddress("consignee_"),
																											member.ToId("member_"),
																											member.ToName("member_"),
																											member.ToWechat_id("member_")));
					
					
					json.append("\"details\":[");
					if(orderDetails != null && orderDetails.size() > 0){
						orderDetail = orderDetails.get(0);
						if(orderDetail == null)
							orderDetail = new OrderDetailBean();
						
						commodity = orderDetail.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();

						store = orderDetail.getStore();
						if(store == null)
							store = new StoreBean();

						storeImages = store.getImages();
						if(storeImages == null){
							storeImages = new ArrayList<StoreImageBean>();
							storeImages.add(new StoreImageBean());
						}
						
						json.append("{");
						json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", orderDetail.ToId(""),
																					orderDetail.ToCount(""),
																					orderDetail.ToAmount(""),
																					commodity.ToId("commodity_"),
																					commodity.ToName("commodity_"),
																					commodity.ToPrice("commodity_"),
																					commodity.ToCount("commodity_"),
																					commodity.ToDescription("commodity_"),
																					store.ToId("store_"),
																					store.ToName("store_")));
						
						if(storeImages != null && storeImages.size() > 0)
							json.append(String.format("\"store_image\":\"%s\"", storeImages.get(0).getUrl()));
						else
							json.append(String.format("\"store_image\":\"%s\"", ""));
						
						json.append("}");
						
						for(int j = 1; j < orderDetails.size(); j++){
							orderDetail = orderDetails.get(j);
							if(orderDetail == null)
								orderDetail = new OrderDetailBean();
								
							commodity = orderDetail.getCommodity();
							if(commodity == null)
								commodity = new CommodityBean();
							
							store = orderDetail.getStore();
							if(store == null)
								store = new StoreBean();

							storeImages = store.getImages();
							if(storeImages == null){
								storeImages = new ArrayList<StoreImageBean>();
								storeImages.add(new StoreImageBean());
							}
							
							json.append(",{");
							
							json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", orderDetail.ToId(""),
																						orderDetail.ToCount(""),
																						orderDetail.ToAmount(""),
																						commodity.ToId("commodity_"),
																						commodity.ToName("commodity_"),
																						commodity.ToPrice("commodity_"),
																						commodity.ToCount("commodity_"),
																						commodity.ToDescription("commodity_"),
																						store.ToId("store_"),
																						store.ToName("store_")));
							
							json.append(String.format("\"store_image\":\"%s\"", storeImages.get(0).getUrl()));
							
							json.append("}");
						}
					}
					json.append("]");
					
					
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
			orders = orderService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(orders != null && orders.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < orders.size(); i++){
					order = orders.get(i);
					member = order.getMember();
					
					if(member == null)
						member = new MemberBean();
					
					consignee = order.getConsignee();
					if(consignee == null)
						consignee = new ConsigneeBean();
					
					
					orderDetails = order.getOrderDetails();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	order.ToId(""),
																											order.ToOrder_no(""),
																											order.ToCount(""),
																											order.ToCurrent(""),
																											order.ToAmount(""),
																											order.ToSort(""),
																											order.ToType(""),
																											order.ToStatus(""),
																											order.ToGrind(""),
																											order.ToCost(""),
																											order.ToMemo(""),
																											order.ToCreate_time(""),
																											order.ToModify_time(""),
																											consignee.ToName("consignee_"),
																											consignee.ToPhone("consignee_"),
																											consignee.ToAddress("consignee_"),
																											member.ToId("member_"),
																											member.ToName("member_"),
																											member.ToWechat_id("member_")));
					
					
					json.append("\"details\":[");
					if(orderDetails != null && orderDetails.size() > 0){
						orderDetail = orderDetails.get(0);
						if(orderDetail == null)
							orderDetail = new OrderDetailBean();
						
						commodity = orderDetail.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();

						store = orderDetail.getStore();
						if(store == null)
							store = new StoreBean();

						json.append("{");
						json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	orderDetail.ToId(""),
																					orderDetail.ToCount(""),
																					orderDetail.ToAmount(""),
																					commodity.ToId("commodity_"),
																					commodity.ToName("commodity_"),
																					commodity.ToPrice("commodity_"),
																					commodity.ToCount("commodity_"),
																					commodity.ToDescription("commodity_"),
																					store.ToId("store_"),
																					store.ToName("store_")));
						
						
						json.append("}");
						
						for(int j = 1; j < orderDetails.size(); j++){
							orderDetail = orderDetails.get(j);
							if(orderDetail == null)
								orderDetail = new OrderDetailBean();
								
							commodity = orderDetail.getCommodity();
							if(commodity == null)
								commodity = new CommodityBean();
							
							store = orderDetail.getStore();
							if(store == null)
								store = new StoreBean();

							json.append(",{");
							
							json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	orderDetail.ToId(""),
																						orderDetail.ToCount(""),
																						orderDetail.ToAmount(""),
																						commodity.ToId("commodity_"),
																						commodity.ToName("commodity_"),
																						commodity.ToPrice("commodity_"),
																						commodity.ToCount("commodity_"),
																						commodity.ToDescription("commodity_"),
																						store.ToId("store_"),
																						store.ToName("store_")));

							json.append("}");
						}
					}
					json.append("]");
					
					
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
		
		if(!StringGlobal.IsNull(status)){
			orders = orderService.ByStatus(Integer.parseInt(status));
			if(orders != null && orders.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < orders.size(); i++){
					order = orders.get(i);
					member = order.getMember();
					
					if(member == null)
						member = new MemberBean();
					
					consignee = order.getConsignee();
					if(consignee == null)
						consignee = new ConsigneeBean();
					
					
					orderDetails = order.getOrderDetails();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	order.ToId(""),
																											order.ToOrder_no(""),
																											order.ToCount(""),
																											order.ToCurrent(""),
																											order.ToAmount(""),
																											order.ToSort(""),
																											order.ToType(""),
																											order.ToStatus(""),
																											order.ToGrind(""),
																											order.ToCost(""),
																											order.ToMemo(""),
																											order.ToCreate_time(""),
																											order.ToModify_time(""),
																											consignee.ToName("consignee_"),
																											consignee.ToPhone("consignee_"),
																											consignee.ToAddress("consignee_"),
																											member.ToId("member_"),
																											member.ToName("member_"),
																											member.ToWechat_id("member_")));
					
					
					json.append("\"details\":[");
					if(orderDetails != null && orderDetails.size() > 0){
						orderDetail = orderDetails.get(0);
						if(orderDetail == null)
							orderDetail = new OrderDetailBean();
						
						commodity = orderDetail.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();

						store = orderDetail.getStore();
						if(store == null)
							store = new StoreBean();

						json.append("{");
						json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	orderDetail.ToId(""),
																					orderDetail.ToCount(""),
																					orderDetail.ToAmount(""),
																					commodity.ToId("commodity_"),
																					commodity.ToName("commodity_"),
																					commodity.ToPrice("commodity_"),
																					commodity.ToCount("commodity_"),
																					commodity.ToDescription("commodity_"),
																					store.ToId("store_"),
																					store.ToName("store_")));
						
						
						json.append("}");
						
						for(int j = 1; j < orderDetails.size(); j++){
							orderDetail = orderDetails.get(j);
							if(orderDetail == null)
								orderDetail = new OrderDetailBean();
								
							commodity = orderDetail.getCommodity();
							if(commodity == null)
								commodity = new CommodityBean();
							
							store = orderDetail.getStore();
							if(store == null)
								store = new StoreBean();

							json.append(",{");
							
							json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	orderDetail.ToId(""),
																						orderDetail.ToCount(""),
																						orderDetail.ToAmount(""),
																						commodity.ToId("commodity_"),
																						commodity.ToName("commodity_"),
																						commodity.ToPrice("commodity_"),
																						commodity.ToCount("commodity_"),
																						commodity.ToDescription("commodity_"),
																						store.ToId("store_"),
																						store.ToName("store_")));

							json.append("}");
						}
					}
					json.append("]");
					
					
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
			order = orderService.ById(Integer.parseInt(id));
			if(order == null)
				order = new OrderBean();
			
			coupon = order.getCoupon();
			if(coupon == null)
				coupon = new CouponBean();
			
			member = order.getMember();
			if(member == null)
				member = new MemberBean();
					
			consignee = order.getConsignee();
			if(consignee == null)
				consignee = new ConsigneeBean();
			
			orderDetails = order.getOrderDetails();
			
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	order.ToId(""),
																								order.ToOrder_no(""),
																								order.ToCurrent(""),
																								order.ToAmount(""),
																								order.ToSort(""),
																								order.ToType(""),
																								order.ToStatus(""),
																								order.ToCreate_time(""),
																								order.ToModify_time(""),
																								coupon.ToId("coupon_"),
																								coupon.ToAmount("coupon_"),
																								consignee.ToName("consignee_"),
																								consignee.ToPhone("consignee_"),
																								consignee.ToAddress("consignee_"),
																								member.ToId("member_"),
																								member.ToName("member_"),
																								member.ToWechat_id("member_")));
			
			json.append("\"details\":[");
			if(orderDetails != null && orderDetails.size() > 0){
				orderDetail = orderDetails.get(0);
				if(orderDetail == null)
					orderDetail = new OrderDetailBean();
				
				commodity = orderDetail.getCommodity();
				if(commodity == null)
					commodity = new CommodityBean();
				
				store = orderDetail.getStore();
				if(store == null)
					store = new StoreBean();
				
				storeComment = storeCommentService.ByStoreIdOrderIdMemberId(store.getId(), member.getId(), order.getId());
				if(storeComment == null)
					storeComment = new StoreCommentBean();
				
				json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s}", 	orderDetail.ToId(""),
																					orderDetail.ToCount(""),
																					orderDetail.ToAmount(""),
																					commodity.ToId("commodity_"),
																					commodity.ToName("commodity_"),
																					commodity.ToPrice("commodity_"),
																					commodity.ToCount("commodity_"),
																					commodity.ToDescription("commodity_"),
																					store.ToId("store_"),
																					store.ToName("store_"),
																					"\"store_merchant_guid\":" + store.getMerchant_id(),
																					storeComment.ToId("store_comment_")));

				for(int i = 1; i < orderDetails.size(); i++){
					orderDetail = orderDetails.get(i);
					if(orderDetail == null)
						orderDetail = new OrderDetailBean();
						
					commodity = orderDetail.getCommodity();
					if(commodity == null)
						commodity = new CommodityBean();
					
					store = orderDetail.getStore();
					if(store == null)
						store = new StoreBean();
					
					storeComment = storeCommentService.ByStoreIdOrderIdMemberId(store.getId(), member.getId(), order.getId());
					if(storeComment == null)
						storeComment = new StoreCommentBean();
					
					json.append(String.format(",{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s}", orderDetail.ToId(""),
																						orderDetail.ToCount(""),
																						orderDetail.ToAmount(""),
																						commodity.ToId("commodity_"),
																						commodity.ToName("commodity_"),
																						commodity.ToPrice("commodity_"),
																						commodity.ToCount("commodity_"),
																						commodity.ToDescription("commodity_"),
																						store.ToId("store_"),
																						store.ToName("store_"),
																						"\"store_merchant_guid\":" + store.getMerchant_id(),
																						storeComment.ToId("store_comment_")));
				}
			}
			json.append("]");
				
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}