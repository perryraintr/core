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
public class Recommend_ModifyAction extends BaseAction {
	@Autowired
	private RecommendService RecommendService;
	
	@RequestMapping(value = "/recommend_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		
		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		String storeId = request.getParameter("sid");
		String commodityId = request.getParameter("cid");
		String message = request.getParameter("m");
		
		message = new String(message.getBytes("ISO-8859-1"), "utf-8");
		message = StringGlobal.SerializeJson(message);
		
		
//		RecommendBean recommend = RecommendService.ByStoreIdCommodityId(Integer.parseInt(storeId), Integer.parseInt(commodityId));
//		if(recommend != null){
		
		RecommendBean recommend = RecommendService.ById(Integer.parseInt(id));

			if(recommend != null){
				if(!StringGlobal.IsNull(storeId))
					recommend.setStore_id(Integer.parseInt(storeId));
				if(!StringGlobal.IsNull(commodityId))
					recommend.setCommodity_id(Integer.parseInt(commodityId));
				if(!StringGlobal.IsNull(message))
					recommend.setMessage(message);
				recommend.setModify_time(new Date());
				RecommendService.Modify(recommend);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", recommend.ToId("")));
				return null;
			}
//		}

		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}