package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StoreFeature1ImageBean;
import com.raintr.pinshe.service.StoreFeature1ImageService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreFeature1ImageAction extends BaseAction {
	@Autowired
	private StoreFeature1ImageService storeFeature1ImageService;
	
	@RequestMapping(value = "/store_feature1_image")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String page = request.getParameter("page");
		
		StoreFeature1ImageBean storeFeature1Image;
		
		if(!StringGlobal.IsNull(page)){
			StringBuffer json = new StringBuffer();
			List<StoreFeature1ImageBean> storeFeature1Images = storeFeature1ImageService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(storeFeature1Images != null && storeFeature1Images.size() > 0){
				for(int i = 0; i < storeFeature1Images.size(); i++){
					storeFeature1Image = storeFeature1Images.get(i);
					json.append(String.format("{%s,%s,%s},", storeFeature1Image.ToId(""), storeFeature1Image.ToName(""), storeFeature1Image.ToUrl("")));
				}
			
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(id)){
			storeFeature1Image = storeFeature1ImageService.ById(Integer.parseInt(id));
			if(storeFeature1Image != null){
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s,%s}}", storeFeature1Image.ToId(""), storeFeature1Image.ToName(""), storeFeature1Image.ToUrl("")));
				return null;
			}
		}

		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}