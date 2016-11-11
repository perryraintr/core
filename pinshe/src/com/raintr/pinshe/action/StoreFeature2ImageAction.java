package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StoreFeature2ImageBean;
import com.raintr.pinshe.service.StoreFeature2ImageService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreFeature2ImageAction extends BaseAction {
	@Autowired
	private StoreFeature2ImageService storeFeature2ImageService;
	
	@RequestMapping(value = "/store_feature2_image")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String page = request.getParameter("page");
		
		StoreFeature2ImageBean storeFeature2Image;
		
		if(!StringGlobal.IsNull(page)){
			StringBuffer json = new StringBuffer();
			List<StoreFeature2ImageBean> storeFeature2Images = storeFeature2ImageService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(storeFeature2Images != null && storeFeature2Images.size() > 0){
				for(int i = 0; i < storeFeature2Images.size(); i++){
					storeFeature2Image = storeFeature2Images.get(i);
					json.append(String.format("{%s,%s,%s},", storeFeature2Image.ToId(""), storeFeature2Image.ToName(""), storeFeature2Image.ToUrl("")));
				}
			
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		
		if(!StringGlobal.IsNull(id)){
			storeFeature2Image = storeFeature2ImageService.ById(Integer.parseInt(id));
			if(storeFeature2Image != null){
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s,%s}}", storeFeature2Image.ToId(""), storeFeature2Image.ToName(""), storeFeature2Image.ToUrl("")));
				return null;
			}
		}

		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}