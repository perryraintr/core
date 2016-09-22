package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.bean.RecommendBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.service.CommodityService;
import com.raintr.pinshe.service.RecommendService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class CommodityAction extends BaseAction {
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping(value = "/commodity")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");	
		String page = request.getParameter("page");
		String t0 = request.getParameter("t0");
		String t1 = request.getParameter("t1");
		
		
		CommodityBean commodity;
		List<CommodityBean> commoditys;
		List<CommodityImageBean> images;
		StoreBean store;
		RecommendBean recommend;
		List<RecommendBean> recommends;
		
		if(!StringGlobal.IsNull(page)){
			commoditys = commodityService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(commoditys != null && commoditys.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < commoditys.size(); i++){
					commodity = commoditys.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	commodity.ToId(""),
																																	commodity.ToName(""),
																																	commodity.ToT0(""),
																																	commodity.ToT1(""),
																																	commodity.ToT2(""),
																																	commodity.ToT3(""),
																																	commodity.ToT4(""),
																																	commodity.ToT5(""),
																																	commodity.ToT0_str(""),
																																	commodity.ToT1_str(""),
																																	commodity.ToT2_str(""),
																																	commodity.ToT3_str(""),
																																	commodity.ToT4_str(""),
																																	commodity.ToT5_str(""),
																																	commodity.ToPrice(""),
																																	commodity.ToCount(""),
																																	commodity.ToDescription(""),
																																	commodity.ToOther_name(""),
																																	commodity.ToOther_description(""),
																																	commodity.ToCost(""),
																																	commodity.ToUrl(""),
																																	commodity.ToMemo(""),
																																	commodity.ToStatus(""),
																																	commodity.ToRank(""),
																																	commodity.ToCreate_time(""),
																																	commodity.ToModify_time("")));
					
					
					
					
					recommends = recommendService.ByCommodityId(commodity.getId(), 0);
					json.append("\"recommends\":[");
					if(recommends != null && recommends.size() > 0){
						recommend = recommends.get(0);
						store = recommend.getStore();
						if(store == null)
							store = new StoreBean();
						json.append(String.format("{%s,%s,%s,%s,%s}", 	recommend.ToId(""),
																		recommend.ToMessage(""),
																		store.ToId("store_"),
																		store.ToOwner("store_"),
																		store.ToName("store_")));
						
						for(int j = 1; j < recommends.size(); j++){
							recommend = recommends.get(j);
							store = recommend.getStore();
							if(store == null)
								store = new StoreBean();
							
							json.append(String.format(",{%s,%s,%s,%s,%s}", 	recommend.ToId(""),
																			recommend.ToMessage(""),
																			store.ToId("store_"),
																			store.ToOwner("store_"),
																			store.ToName("store_")));
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
		}
		
		if(!StringGlobal.IsNull(id)){
			StringBuffer json = new StringBuffer();
			
			commodity = commodityService.ById(Integer.parseInt(id));
			images = commodity.getImages();

			json.append("\"images\":[");
			if(images != null && images.size() > 0){
				json.append(String.format("\"%s\"", images.get(0).getUrl()));
				for(int i = 1; i < images.size(); i++){
					json.append(String.format(",\"%s\"", images.get(i).getUrl()));
				}
			}
			json.append("],");
			
			recommends = recommendService.ByCommodityId(commodity.getId(), 0);
			json.append("\"recommends\":[");
			if(recommends != null && recommends.size() > 0){
				recommend = recommends.get(0);
				store = recommend.getStore();
				if(store == null)
					store = new StoreBean();
				json.append(String.format("{%s,%s,%s,%s,%s}", 	recommend.ToId(""),
																recommend.ToMessage(""),
																store.ToId("store_"),
																store.ToOwner("store_"),
																store.ToName("store_")));
				
				for(int i = 1; i < recommends.size(); i++){
					recommend = recommends.get(i);
					store = recommend.getStore();
					if(store == null)
						store = new StoreBean();
					json.append(String.format(",{%s,%s,%s,%s,%s}", 	recommend.ToId(""),
																	recommend.ToMessage(""),
																	store.ToId("store_"),
																	store.ToOwner("store_"),
																	store.ToName("store_")));
				}
			}
			json.append("],");
			
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	commodity.ToId(""),
																															commodity.ToName(""),
																															commodity.ToT0(""),
																															commodity.ToT1(""),
																															commodity.ToT2(""),
																															commodity.ToT3(""),
																															commodity.ToT4(""),
																															commodity.ToT5(""),
																															commodity.ToT0_str(""),
																															commodity.ToT1_str(""),
																															commodity.ToT2_str(""),
																															commodity.ToT3_str(""),
																															commodity.ToT4_str(""),
																															commodity.ToT5_str(""),
																															commodity.ToPrice(""),
																															commodity.ToCount(""),
																															commodity.ToDescription(""),
																															commodity.ToDetail(""),
																															commodity.ToOther_name(""),
																															commodity.ToOther_description(""),
																															commodity.ToCost(""),
																															commodity.ToUrl(""),
																															commodity.ToMemo(""),
																															commodity.ToStatus(""),
																															commodity.ToRank(""),
																															commodity.ToCreate_time(""),
																															commodity.ToModify_time("")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		if(!StringGlobal.IsNull(t0)){
			commoditys = commodityService.ByT0(Integer.parseInt(t0));
			if(commoditys != null && commoditys.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < commoditys.size(); i++){
					commodity = commoditys.get(i);
					images = commodity.getImages();
					
					json.append("{");
					
					if(images != null && images.size() > 0)
						json.append(String.format("\"image\":\"%s\",", images.get(0).getUrl()));
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	commodity.ToId(""),
																											commodity.ToName(""),
																											commodity.ToT0(""),
																											commodity.ToT1(""),
																											commodity.ToT2(""),
																											commodity.ToT3(""),
																											commodity.ToT4(""),
																											commodity.ToT5(""),
																											commodity.ToT0_str(""),
																											commodity.ToT1_str(""),
																											commodity.ToT2_str(""),
																											commodity.ToT3_str(""),
																											commodity.ToT4_str(""),
																											commodity.ToT5_str(""),
																											commodity.ToPrice(""),
																											commodity.ToCount(""),
																											commodity.ToDescription(""),
																											commodity.ToCreate_time(""),
																											commodity.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(t1)){
			commoditys = commodityService.ByT1(Integer.parseInt(t1));
			if(commoditys != null && commoditys.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < commoditys.size(); i++){
					commodity = commoditys.get(i);
					
					images = commodity.getImages();
					
					json.append("{");
					
					if(images != null && images.size() > 0)
						json.append(String.format("\"image\":\"%s\",", images.get(0).getUrl()));
					
//					json.append("\"images\":[");
//					if(images != null && images.size() > 0){
//						json.append(String.format("\"%s\"", images.get(0).getUrl()));
//						for(int j = 1; j < images.size(); j++){
//							json.append(String.format(",\"%s\"", images.get(j).getUrl()));
//						}
//					}
//					json.append("],");
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	commodity.ToId(""),
																											commodity.ToName(""),
																											commodity.ToT0(""),
																											commodity.ToT1(""),
																											commodity.ToT2(""),
																											commodity.ToT3(""),
																											commodity.ToT4(""),
																											commodity.ToT5(""),
																											commodity.ToT0_str(""),
																											commodity.ToT1_str(""),
																											commodity.ToT2_str(""),
																											commodity.ToT3_str(""),
																											commodity.ToT4_str(""),
																											commodity.ToT5_str(""),
																											commodity.ToPrice(""),
																											commodity.ToCount(""),
																											commodity.ToDescription(""),
																											commodity.ToCreate_time(""),
																											commodity.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}