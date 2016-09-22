package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.raintr.pinshe.service.AdService;

@Controller
@RequestMapping(value = "/")
public class Admin_ImageAction extends BaseAction {
	@Autowired
	private AdService adService;
	
	@RequestMapping(value = "/admin_image")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		MultipartFile file = null;
		try{
			file = ((MultipartHttpServletRequest)request).getFile("file");
		}catch(Exception ex){}
		
		String image = adService.GetImageUrl(file);
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"%s\"}}", image));
		return null;
	}
}