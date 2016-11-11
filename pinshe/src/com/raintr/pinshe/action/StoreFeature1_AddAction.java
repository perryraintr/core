package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StoreFeature1Bean;
import com.raintr.pinshe.service.StoreFeature1Service;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreFeature1_AddAction extends BaseAction {
	@Autowired
	private StoreFeature1Service storeFeature1Service;
	
	@RequestMapping(value = "/store_feature1_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String feature1s = request.getParameter("feature1s");
		
		StoreFeature1Bean storeFeature1;
		
		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(feature1s)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "feature1s is null"));
			return null;
		}
		
		StringBuffer json = new StringBuffer();
		String[] ids = feature1s.split(",");
		if(ids != null && ids.length > 0){
			storeFeature1Service.RemoveStoreId(Integer.parseInt(storeId));
			
			for(int i = 0; i < ids.length; i++){
				json.append(ids[i] + ",");
				storeFeature1 = new StoreFeature1Bean();
				storeFeature1.setStore_id(Integer.parseInt(storeId));
				storeFeature1.setStore_feature1_image_id(Integer.parseInt(ids[i]));
				storeFeature1.setCreate_time(new Date());
				storeFeature1.setModify_time(new Date());
				storeFeature1.setId(storeFeature1Service.Add(storeFeature1));
			}
			
			if(json.length() > 0)
				json.setLength(json.length() - 1);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":\"%s\"}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}");
		return null;
	}
}