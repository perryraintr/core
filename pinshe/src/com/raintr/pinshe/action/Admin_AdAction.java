package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.raintr.pinshe.bean.AdBean;
import com.raintr.pinshe.service.AdService;
import com.raintr.pinshe.utils.StringGlobal;
@Controller
@RequestMapping(value = "/")
public class Admin_AdAction extends BaseAction {
	@Autowired
	private AdService adService;
	
	@RequestMapping(value = "/admin_ad")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String[] url = new String[1];
		
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		String content = request.getParameter("content");

		name = StringGlobal.SerializeJson(name);
		
		MultipartFile file = null;
		try{
			file = ((MultipartHttpServletRequest)request).getFile("file");
		}catch(Exception ex){}
		
		AdBean ad = new AdBean();
		ad.setType(Integer.parseInt(type));
		ad.setName(name);
		adService.Add(ad, file, name, content, url);

		response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"%s\"}}", url[0]));
		return null;
	}
}