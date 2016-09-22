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
import com.raintr.pinshe.bean.RecommendBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreImageBean;
import com.raintr.pinshe.service.RecommendService;
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
		String memberId = request.getParameter("mid");
		
		StoreBean store;
		List<StoreBean> stores;
		List<StoreImageBean> images;
		CommodityBean commodity;
		RecommendBean recommend;
		List<RecommendBean> recommends;
		
		if(!StringGlobal.IsNull(memberId)){
			store = storeService.ByMemberId(Integer.parseInt(memberId));
			if(store != null){
				images = store.getImages();
				if(images == null)
					images = new ArrayList<StoreImageBean>();
			
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", store.ToId(""),
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
																											store.ToCreate_time(""),
																											store.ToModify_time("")));

				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}

			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		
		if(!StringGlobal.IsNull(latitude) && !StringGlobal.IsNull(longitude)){
			int index = (Integer.parseInt(page) - 1) * 3;
			int count = index + 3;

			List<StoreBean> collection = new ArrayList<StoreBean>();
			for(int i = 0; i < 20; i++){
				stores = storeService.By(i * 100);
				if(stores == null || stores.size() == 0)
					break;
				
				for(int j = 0; j < stores.size(); j++){
					store = stores.get(j);
					store.setDistance(MathGlobal.GetDistance(Double.parseDouble(longitude), Double.parseDouble(latitude), store.getLongitude(), store.getLatitude()));
					if(store.getDistance() <= Double.parseDouble(distance))
						collection.add(store);
				}
			}
			
			if(collection.size() > 0){
				Collections.sort(collection, new StoreBean());
				
				StringBuffer json = new StringBuffer();
				
				for(int i = index; i < collection.size() && i < count; i++){
					store = collection.get(i);
					if(store == null)
						break;
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	store.ToId(""),
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
			
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	store.ToId(""),
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
					json.append(String.format("{%s,%s,%s,%s,%s}", 	recommend.ToId(""),
																	recommend.ToMessage(""),
																	commodity.ToId("commodity_"),
																	commodity.ToName("commodity_"),
																	commodity.ToDescription("commodity_")));
					
					for(int i = 1; i < recommends.size(); i++){
						recommend = recommends.get(i);
						commodity = recommend.getCommodity();
						if(commodity == null)
							commodity = new CommodityBean();
						
						json.append(String.format(",{%s,%s,%s,%s,%s}", 	recommend.ToId(""),
																		recommend.ToMessage(""),
																		commodity.ToId("commodity_"),
																		commodity.ToName("commodity_"),
																		commodity.ToDescription("commodity_")));
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
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	store.ToId(""),
																															"\"member_guid\":" + store.getMember_id(),
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