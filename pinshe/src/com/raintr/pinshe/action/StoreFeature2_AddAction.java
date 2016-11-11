package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StoreFeature2Bean;
import com.raintr.pinshe.service.StoreFeature2Service;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreFeature2_AddAction extends BaseAction {
	@Autowired
	private StoreFeature2Service storeFeature2Service;
	
	@RequestMapping(value = "/store_feature2_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String feature2s = request.getParameter("feature2s");
		
		StoreFeature2Bean storeFeature2;
		
		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(feature2s)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "feature2s is null"));
			return null;
		}
		
		StringBuffer json = new StringBuffer();
		String[] ids = feature2s.split(",");
		if(ids != null && ids.length > 0){
			storeFeature2Service.RemoveStoreId(Integer.parseInt(storeId));
			
			for(int i = 0; i < ids.length; i++){
				json.append(ids[i] + ",");
				storeFeature2 = new StoreFeature2Bean();
				storeFeature2.setStore_id(Integer.parseInt(storeId));
				storeFeature2.setStore_feature2_image_id(Integer.parseInt(ids[i]));
				storeFeature2.setCreate_time(new Date());
				storeFeature2.setModify_time(new Date());
				storeFeature2.setId(storeFeature2Service.Add(storeFeature2));
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