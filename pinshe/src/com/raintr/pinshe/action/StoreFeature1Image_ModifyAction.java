package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.raintr.pinshe.bean.StoreFeature1ImageBean;
import com.raintr.pinshe.service.StoreFeature1ImageService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreFeature1Image_ModifyAction extends BaseAction {
	@Autowired
	private StoreFeature1ImageService storeFeature1ImageService;
	
	@RequestMapping(value = "/store_feature1_image_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		
		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		StoreFeature1ImageBean storeFeature1Image = storeFeature1ImageService.ById(Integer.parseInt(id));
		if(storeFeature1Image != null){
			MultipartFile file = null;
			try{
				file = ((MultipartHttpServletRequest)request).getFile("file");
			}catch(Exception ex){}
			
			if(!StringGlobal.IsNull(name))
				storeFeature1Image.setName(name);
			storeFeature1Image.setModify_time(new Date());
			storeFeature1ImageService.Modify(storeFeature1Image, file);

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s,%s}}", storeFeature1Image.ToId(""), storeFeature1Image.ToUrl("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}