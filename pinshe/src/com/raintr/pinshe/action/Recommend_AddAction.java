package com.raintr.pinshe.action;

import java.util.Date;

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
public class Recommend_AddAction extends BaseAction {
	@Autowired
	private RecommendService RecommendService;
	
	@RequestMapping(value = "/recommend_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String commodityId = request.getParameter("cid");
		String message = request.getParameter("m");
		
		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(commodityId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "cid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(message)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "m is null"));
			return null;
		}
		
		message = new String(message.getBytes("ISO-8859-1"), "utf-8");
		
		message = StringGlobal.SerializeJson(message);
		
		RecommendBean recommend = RecommendService.ByStoreIdCommodityId(Integer.parseInt(storeId), Integer.parseInt(commodityId));
		
		if(recommend == null){
			recommend = new RecommendBean();
			recommend.setStore_id(Integer.parseInt(storeId));
			recommend.setCommodity_id(Integer.parseInt(commodityId));
			recommend.setMessage(message);
			recommend.setCreate_time(new Date());
			recommend.setModify_time(new Date());
			recommend.setId(RecommendService.Add(recommend));
			
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s", recommend.ToId("")));
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}