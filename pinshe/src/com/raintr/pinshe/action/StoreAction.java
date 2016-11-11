package com.raintr.pinshe.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.bean.RecommendBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreFeature1Bean;
import com.raintr.pinshe.bean.StoreFeature1ImageBean;
import com.raintr.pinshe.bean.StoreFeature2Bean;
import com.raintr.pinshe.bean.StoreFeature2ImageBean;
import com.raintr.pinshe.bean.StoreImageBean;
import com.raintr.pinshe.bean.StoreMemberBean;
import com.raintr.pinshe.bean.StorePaymentBean;
import com.raintr.pinshe.service.RecommendService;
import com.raintr.pinshe.service.StoreFeature1Service;
import com.raintr.pinshe.service.StoreFeature2Service;
import com.raintr.pinshe.service.StorePaymentService;
import com.raintr.pinshe.service.StoreService;
import com.raintr.pinshe.utils.MathGlobal;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreAction extends BaseAction {
	@Autowired
	private StoreService storeService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private StoreFeature1Service storeFeature1Service;
	@Autowired
	private StoreFeature2Service storeFeature2Service;
	@Autowired
	private StorePaymentService storePaymentService;
	
	@RequestMapping(value = "/store")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");	
		String distance = request.getParameter("distance");
		String page = request.getParameter("page");
		String merchantId = request.getParameter("mid");
		String by = request.getParameter("by");
		String all = request.getParameter("all");
		
		String realLongitude = request.getParameter("real_longitude");
		String realLatitude = request.getParameter("real_latitude");
		
		StoreBean store;
		List<StoreBean> stores;
		List<StoreImageBean> images;
		CommodityBean commodity;
		RecommendBean recommend;
		List<RecommendBean> recommends;
		CommodityImageBean commodityImage = null;
		List<CommodityImageBean> commodityImages = null;
		MerchantBean merchant = null;
		StoreMemberBean storeMember;
		List<StoreMemberBean> storeMembers = null;
		StoreFeature1Bean storeFeature1;
		List<StoreFeature1Bean> storeFeature1s;
		StoreFeature1ImageBean storeFeature1Image;
		StoreFeature2Bean storeFeature2;
		List<StoreFeature2Bean> storeFeature2s;
		StoreFeature2ImageBean storeFeature2Image;
		
		StorePaymentBean storePayment;
		List<StorePaymentBean> storePayments;
		
		if(!StringGlobal.IsNull(merchantId)){
			String row;
			String json0 = null;
			StringBuffer json = new StringBuffer();
			stores = storeService.ByMerchantId(Integer.parseInt(merchantId));
			if(stores != null && stores.size() > 0){
				for(int i = 0; i < stores.size(); i++){
					store = stores.get(i);
			
					images = store.getImages();
					if(images == null)
						images = new ArrayList<StoreImageBean>();

					
					row = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	store.ToId(""),
																													store.ToCurrent(""),
																													store.ToLongitude(""),
																													store.ToLatitude(""),
																													store.ToName(""),
																													store.ToStar(""),
																													store.ToAddress(""),
																													store.ToPhone(""),
																													store.ToDate(""),
																													store.ToSlogan(""),
																													store.ToOwner(""),
																													store.ToAvatar(""),
																													store.ToRecommend(""),
																													store.ToFeature1(""),
																													store.ToFeature2(""),
																													store.ToFeature3(""),
																													store.ToImage(""),
																													store.ToVideo(""),
																													store.ToActivity(""),
																													store.ToComment(""),
																													store.ToIs_delete(""),
																													store.ToInvaild(""),
																													store.ToCreate_time(""),
																													store.ToModify_time(""));
					
					json.append(String.format("{%s},", row));
					
					if(json0 == null)
						json0 = row;
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,\"array\":[%s]}}", json0, json.toString()));
				return null;
			}

			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		
		if(!StringGlobal.IsNull(latitude) && !StringGlobal.IsNull(longitude)){
			int index = (Integer.parseInt(page) - 1) * 5;
			int count = index + 5;

			if(!StringGlobal.IsNull(all))
				count = index + 10000;
			
			List<StoreBean> collection = new ArrayList<StoreBean>();
			for(int i = 0; i < 20; i++){
				stores = storeService.ByIsDelete(i * 100);
				if(stores == null || stores.size() == 0)
					break;
				
				for(int j = 0; j < stores.size(); j++){
					store = stores.get(j);
					store.setDistance(MathGlobal.GetDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), store.getLongitude(), store.getLatitude()));
					if(store.getDistance() <= Double.parseDouble(distance)){
						collection.add(store);
					}
				}
			}
			
			if(collection.size() > 0){
				StoreBean storeTemp = new StoreBean();
				if(!StringGlobal.IsNull(by) && "1".equals(by))
					storeTemp.setBy(1);
				Collections.sort(collection, storeTemp);
				
				StringBuffer json = new StringBuffer();
				
				for(int i = index; i < collection.size() && i < count; i++){
					store = collection.get(i);
					if(store == null)
						break;
					
					if(!StringGlobal.IsNull(realLongitude) && !StringGlobal.IsNull(realLatitude))
						store.setDistance(MathGlobal.GetDistance(Double.parseDouble(realLongitude), Double.parseDouble(realLatitude), store.getLongitude(), store.getLatitude()));

					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", store.ToId(""),
																															store.ToCurrent(""),
																															store.ToLongitude(""),
																															store.ToLatitude(""),
																															store.ToName(""),
																															store.ToStar(""),
																															store.ToAddress(""),
																															store.ToPhone(""),
																															store.ToDate(""),
																															store.ToSlogan(""),
																															store.ToOwner(""),
																															store.ToAvatar(""),
																															store.ToRecommend(""),
																															store.ToFeature1(""),
																															store.ToFeature2(""),
																															store.ToFeature3(""),
																															store.ToImage(""),
																															store.ToVideo(""),
																															store.ToActivity(""),
																															store.ToComment(""),
																															store.ToInvaild(""),
																															store.ToDescription(""),
																															store.ToCreate_time(""),
																															store.ToModify_time(""),
																															store.ToDistance("")));
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
			StringBuffer json = new StringBuffer();
			store = storeService.ById(Integer.parseInt(id));
			if(store != null){
				images = store.getImages();
				if(images == null)
					images = new ArrayList<StoreImageBean>();
			
				merchant = store.getMerchant();
				if(merchant == null)
					merchant = new MerchantBean();
				
				storeMembers = store.getStoreMembers();
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	store.ToId(""),
																																				store.ToCurrent(""),
																																				store.ToLongitude(""),
																																				store.ToLatitude(""),
																																				store.ToName(""),
																																				store.ToStar(""),
																																				store.ToAddress(""),
																																				store.ToPhone(""),
																																				store.ToDate(""),
																																				store.ToSlogan(""),
																																				store.ToOwner(""),
																																				store.ToAvatar(""),
																																				store.ToRecommend(""),
																																				store.ToFeature1(""),
																																				store.ToFeature2(""),
																																				store.ToFeature3(""),
																																				store.ToImage(""),
																																				store.ToVideo(""),
																																				store.ToActivity(""),
																																				store.ToComment(""),
																																				store.ToWifi(""),
																																				store.ToWifi_password(""),
																																				store.ToInvaild(""),
																																				store.ToDescription(""),
																																				store.ToCreate_time(""),
																																				store.ToModify_time(""),
																																				store.ToDistance(""),
																																				merchant.ToId("merchant_"),
																																				merchant.ToWechat_id("merchant_"),
																																				merchant.ToPhone("merchant_"),
																																				merchant.ToDevice("merchant_"),
																																				merchant.ToGetui_id("merchant_")));
				json.append("\"store_member\":[");
				if(storeMembers != null && storeMembers.size() > 0){
					storeMember = storeMembers.get(0);
					
					if(storeMember != null && storeMember.getMerchant() != null){
						json.append(String.format("{%s,%s,%s,%s,%s}", 	storeMember.getMerchant().ToId(""), 
																		storeMember.getMerchant().ToWechat_id(""),
																		storeMember.getMerchant().ToPhone(""),
																		storeMember.getMerchant().ToDevice(""),
																		storeMember.getMerchant().ToGetui_id("")));
					}
					
					for(int i = 1; i < storeMembers.size(); i++){
						storeMember = storeMembers.get(i);
						if(storeMember != null && storeMember.getMerchant() != null){
							json.append(String.format(",{%s,%s,%s,%s,%s}", storeMember.getMerchant().ToId(""), 
																			storeMember.getMerchant().ToWechat_id(""),
																			storeMember.getMerchant().ToPhone(""),
																			storeMember.getMerchant().ToDevice(""),
																			storeMember.getMerchant().ToGetui_id("")));
						}
					}
				}
				json.append("],");
				
				
				recommends = recommendService.ByStoreId(store.getId(), 0);
				json.append("\"recommends\":[");
				if(recommends != null && recommends.size() > 0){
					recommend = recommends.get(0);
					commodity = recommend.getCommodity();
					if(commodity == null)
						commodity = new CommodityBean();
					
					commodityImages = commodity.getImages();
					if(commodityImages != null && commodityImages.size() > 0)
						commodityImage = commodityImages.get(0);
					
					if(commodityImage == null)
						commodityImage = new CommodityImageBean();
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s}", 	recommend.ToId(""),
																		recommend.ToMessage(""),
																		commodity.ToId("commodity_"),
																		commodity.ToName("commodity_"),
																		commodity.ToDescription("commodity_"),
																		commodityImage.ToUrl("commodity_image_")));
					
					for(int i = 1; i < recommends.size(); i++){
						recommend = recommends.get(i);
						commodity = recommend.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();
						
						commodityImages = commodity.getImages();
						if(commodityImages != null && commodityImages.size() > 0)
							commodityImage = commodityImages.get(0);
						
						if(commodityImage == null)
							commodityImage = new CommodityImageBean();
						
						json.append(String.format(",{%s,%s,%s,%s,%s,%s}", 	recommend.ToId(""),
																			recommend.ToMessage(""),
																			commodity.ToId("commodity_"),
																			commodity.ToName("commodity_"),
																			commodity.ToDescription("commodity_"),
																			commodityImage.ToUrl("commodity_image_")));
					}
				}
				json.append("],");
				
				json.append("\"images\":[");
				if(images != null && images.size() > 0){
					json.append(String.format("\"%s\"", images.get(0).getUrl()));
					for(int i = 1; i < images.size(); i++){
						json.append(String.format(",\"%s\"", images.get(i).getUrl()));
					}
				}
				json.append("],");
				
				storeFeature1s = storeFeature1Service.ByStoreId(store.getId());
				json.append("\"feature1s\":[");
				if(storeFeature1s != null && storeFeature1s.size() > 0){
					storeFeature1 = storeFeature1s.get(0);
					
					if(storeFeature1 == null)
						storeFeature1 = new StoreFeature1Bean();
					
					storeFeature1Image = storeFeature1.getStoreFeature1Image();
					if(storeFeature1Image == null)
						storeFeature1Image = new StoreFeature1ImageBean();

					json.append(String.format("{%s,%s,%s}", storeFeature1Image.ToId(""), storeFeature1Image.ToName(""), storeFeature1Image.ToUrl("")));
					
					for(int j = 1; j < storeFeature1s.size(); j++){
						storeFeature1 = storeFeature1s.get(j);
						
						if(storeFeature1 == null)
							storeFeature1 = new StoreFeature1Bean();
						
						storeFeature1Image = storeFeature1.getStoreFeature1Image();
						if(storeFeature1Image == null)
							storeFeature1Image = new StoreFeature1ImageBean();
						
						json.append(String.format(",{%s,%s,%s}", storeFeature1Image.ToId(""), storeFeature1Image.ToName(""), storeFeature1Image.ToUrl("")));
					}
				}
				json.append("],");
				
				storeFeature2s = storeFeature2Service.ByStoreId(store.getId());
				json.append("\"feature2s\":[");
				if(storeFeature2s != null && storeFeature2s.size() > 0){
					storeFeature2 = storeFeature2s.get(0);
					if(storeFeature2 == null)
						storeFeature2 = new StoreFeature2Bean();
					
					storeFeature2Image = storeFeature2.getStoreFeature2Image();
					if(storeFeature2Image == null)
						storeFeature2Image = new StoreFeature2ImageBean();
					
					json.append(String.format("{%s,%s,%s}", storeFeature2Image.ToId(""), storeFeature2Image.ToName(""), storeFeature2Image.ToUrl("")));
					
					for(int j = 1; j < storeFeature2s.size(); j++){
						storeFeature2 = storeFeature2s.get(j);
						if(storeFeature2 == null)
							storeFeature2 = new StoreFeature2Bean();
						
						storeFeature2Image = storeFeature2.getStoreFeature2Image();
						if(storeFeature2Image == null)
							storeFeature2Image = new StoreFeature2ImageBean();
						
						json.append(String.format(",{%s,%s,%s}", storeFeature2Image.ToId(""), storeFeature2Image.ToName(""), storeFeature2Image.ToUrl("")));
					}
				}
				json.append("],");
				
				storePayments = storePaymentService.ByStoreId(store.getId());
				json.append("\"payments\":[");
				if(storePayments != null && storePayments.size() > 0){
					storePayment = storePayments.get(0);
					if(storePayment == null)
						storePayment = new StorePaymentBean();
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s}", 	storePayment.ToId(""),
																			storePayment.ToType(""),
																			storePayment.ToCompany(""),
																			storePayment.ToAccount(""),
																			storePayment.ToHolder(""),
																			storePayment.ToCreate_time(""),
																			storePayment.ToModify_time("")));
					
					for(int j = 1; j < storePayments.size(); j++){
						storePayment = storePayments.get(j);
						if(storePayment == null)
							storePayment = new StorePaymentBean();
						
						json.append(String.format(",{%s,%s,%s,%s,%s,%s,%s}",	storePayment.ToId(""),
																				storePayment.ToType(""),
																				storePayment.ToCompany(""),
																				storePayment.ToAccount(""),
																				storePayment.ToHolder(""),
																				storePayment.ToCreate_time(""),
																				storePayment.ToModify_time("")));
							
					}
				}
				json.append("]");
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(page)){
			StringBuffer json = new StringBuffer();
			stores = storeService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(stores != null && stores.size() > 0){
				for(int i = 0; i < stores.size(); i++){
					store = stores.get(i);
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", store.ToId(""),
																																			store.ToCurrent(""),
																																			"\"merchant_guid\":" + store.getMerchant_id(),
																																			store.ToLongitude(""),
																																			store.ToLatitude(""),
																																			store.ToName(""),
																																			store.ToStar(""),
																																			store.ToAddress(""),
																																			store.ToPhone(""),
																																			store.ToDate(""),
																																			store.ToSlogan(""),
																																			store.ToOwner(""),
																																			store.ToAvatar(""),
																																			store.ToRecommend(""),
																																			store.ToFeature1(""),
																																			store.ToFeature2(""),
																																			store.ToFeature3(""),
																																			store.ToImage(""),
																																			store.ToVideo(""),
																																			store.ToActivity(""),
																																			store.ToComment(""),
																																			store.ToPayment(""),
																																			store.ToWifi(""),
																																			store.ToWifi_password(""),
																																			store.ToIs_delete(""),
																																			store.ToInvaild(""),
																																			store.ToDescription(""),
																																			store.ToCreate_time(""),
																																			store.ToModify_time(""),
																																			store.ToDistance("")));

					recommends = recommendService.ByStoreId(store.getId(), 0);
					json.append("\"recommends\":[");
					if(recommends != null && recommends.size() > 0){
						recommend = recommends.get(0);
						commodity = recommend.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();
						json.append(String.format("{%s,%s,%s}", recommend.ToId(""), commodity.ToId("commodity_"), recommend.ToMessage("")));
						
						for(int j = 1; j < recommends.size(); j++){
							recommend = recommends.get(j);
							commodity = recommend.getCommodity();
							if(commodity == null)
								commodity = new CommodityBean();
							json.append(String.format(",{%s,%s,%s}", recommend.ToId(""), commodity.ToId("commodity_"), recommend.ToMessage("")));
						}
					}
					json.append("],");
					
					storeFeature1s = storeFeature1Service.ByStoreId(store.getId());
					json.append("\"feature1s\":[");
					if(storeFeature1s != null && storeFeature1s.size() > 0){
						storeFeature1 = storeFeature1s.get(0);
						
						if(storeFeature1 == null)
							storeFeature1 = new StoreFeature1Bean();
						
						storeFeature1Image = storeFeature1.getStoreFeature1Image();
						if(storeFeature1Image == null)
							storeFeature1Image = new StoreFeature1ImageBean();

						json.append(String.format("{%s,%s,%s}", storeFeature1Image.ToId(""), storeFeature1Image.ToName(""), storeFeature1Image.ToUrl("")));
						
						for(int j = 1; j < storeFeature1s.size(); j++){
							storeFeature1 = storeFeature1s.get(j);
							
							if(storeFeature1 == null)
								storeFeature1 = new StoreFeature1Bean();
							
							storeFeature1Image = storeFeature1.getStoreFeature1Image();
							if(storeFeature1Image == null)
								storeFeature1Image = new StoreFeature1ImageBean();
							
							json.append(String.format(",{%s,%s,%s}", storeFeature1Image.ToId(""), storeFeature1Image.ToName(""), storeFeature1Image.ToUrl("")));
						}
					}
					json.append("],");
					
					storeFeature2s = storeFeature2Service.ByStoreId(store.getId());
					json.append("\"feature2s\":[");
					if(storeFeature2s != null && storeFeature2s.size() > 0){
						storeFeature2 = storeFeature2s.get(0);
						if(storeFeature2 == null)
							storeFeature2 = new StoreFeature2Bean();
						
						storeFeature2Image = storeFeature2.getStoreFeature2Image();
						if(storeFeature2Image == null)
							storeFeature2Image = new StoreFeature2ImageBean();
						
						json.append(String.format("{%s,%s,%s}", storeFeature2Image.ToId(""), storeFeature2Image.ToName(""), storeFeature2Image.ToUrl("")));
						
						for(int j = 1; j < storeFeature2s.size(); j++){
							storeFeature2 = storeFeature2s.get(j);
							if(storeFeature2 == null)
								storeFeature2 = new StoreFeature2Bean();
							
							storeFeature2Image = storeFeature2.getStoreFeature2Image();
							if(storeFeature2Image == null)
								storeFeature2Image = new StoreFeature2ImageBean();
							
							json.append(String.format(",{%s,%s,%s}", storeFeature2Image.ToId(""), storeFeature2Image.ToName(""), storeFeature2Image.ToUrl("")));
						}
					}
					json.append("],");
					
					storePayments = storePaymentService.ByStoreId(store.getId());
					json.append("\"payments\":[");
					if(storePayments != null && storePayments.size() > 0){
						storePayment = storePayments.get(0);
						if(storePayment == null)
							storePayment = new StorePaymentBean();
						
						json.append(String.format("{%s,%s,%s,%s,%s,%s,%s}", 	storePayment.ToId(""),
																				storePayment.ToType(""),
																				storePayment.ToCompany(""),
																				storePayment.ToAccount(""),
																				storePayment.ToHolder(""),
																				storePayment.ToCreate_time(""),
																				storePayment.ToModify_time("")));
						
						for(int j = 1; j < storePayments.size(); j++){
							storePayment = storePayments.get(j);
							if(storePayment == null)
								storePayment = new StorePaymentBean();
							
							json.append(String.format(",{%s,%s,%s,%s,%s,%s,%s}",	storePayment.ToId(""),
																					storePayment.ToType(""),
																					storePayment.ToCompany(""),
																					storePayment.ToAccount(""),
																					storePayment.ToHolder(""),
																					storePayment.ToCreate_time(""),
																					storePayment.ToModify_time("")));
								
						}
					}
					json.append("]");

					json.append("},");
				}
			}
			
			if(json.length() > 0)
				json.setLength(json.length() - 1);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}