package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.RecommendBean;
import com.raintr.pinshe.service.RecommendService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class RecommendAction extends BaseAction {
	@Autowired
	private RecommendService RecommendService;
	
	@RequestMapping(value = "/recommend")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String commodityId = request.getParameter("cid");
		String page = request.getParameter("page");
		
		RecommendBean recommend;
		List<RecommendBean> recommends;
		
		if(!StringGlobal.IsNull(storeId)){
			StringBuffer json = new StringBuffer();
			recommends = RecommendService.ByStoreId(Integer.parseInt(storeId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(recommends != null){
				for(int i = 0; i < recommends.size(); i++){
					recommend = recommends.get(i);
					json.append(String.format("{%s,%s,%s,%s},", recommend.ToId(""), 
																recommend.ToStore_id(""), 
																recommend.ToCommodity_id(""),
																recommend.ToMessage("")));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(commodityId)){
			StringBuffer json = new StringBuffer();
			recommends = RecommendService.ByCommodityId(Integer.parseInt(commodityId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(recommends != null){
				for(int i = 0; i < recommends.size(); i++){
					recommend = recommends.get(i);
					json.append(String.format("{%s,%s,%s,%s},", recommend.ToId(""), 
																recommend.ToStore_id(""), 
																recommend.ToCommodity_id(""),
																recommend.ToMessage("")));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}