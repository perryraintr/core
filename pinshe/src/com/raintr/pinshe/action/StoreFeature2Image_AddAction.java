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

import com.raintr.pinshe.bean.StoreFeature2ImageBean;
import com.raintr.pinshe.service.StoreFeature2ImageService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreFeature2Image_AddAction extends BaseAction {
	@Autowired
	private StoreFeature2ImageService storeFeature2ImageService;
	
	@RequestMapping(value = "/store_feature2_image_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String name = request.getParameter("name");
		MultipartFile file = null;
		try{
			file = ((MultipartHttpServletRequest)request).getFile("file");
		}catch(Exception ex){}
		
		if(file == null){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "file is null"));
			return null;
		}
		
		StoreFeature2ImageBean storeFeature2Image = new StoreFeature2ImageBean();
		if(!StringGlobal.IsNull(name))
			storeFeature2Image.setName(name);
		storeFeature2Image.setCreate_time(new Date());
		storeFeature2Image.setModify_time(new Date());
		storeFeature2Image.setId(storeFeature2ImageService.Add(storeFeature2Image, file));

		response.getWriter().print(String.format("{\"head\":2,\"body\":{%s,%s}}", storeFeature2Image.ToId(""), storeFeature2Image.ToUrl("")));
		return null;
	}
}