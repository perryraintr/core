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

import com.raintr.pinshe.bean.StorePushBean;
import com.raintr.pinshe.service.StorePushService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StorePush_AddAction extends BaseAction {
	@Autowired
	private StorePushService storePushService;
	
	@RequestMapping(value = "/store_push_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String name = request.getParameter("name");
		String url = request.getParameter("url");

		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		MultipartFile image = null;
		try{
			image = ((MultipartHttpServletRequest)request).getFile("file");
		}catch(Exception ex){}
		

		StorePushBean storePush = new StorePushBean();
		if(!StringGlobal.IsNull(storeId))
			storePush.setStore_id(Integer.parseInt(storeId));
		if(!StringGlobal.IsNull(name)){
			name = StringGlobal.SerializeJson(name);
			storePush.setName(name);
		}
		if(!StringGlobal.IsNull(url)){
			url = StringGlobal.SerializeJson(url);
			storePush.setUrl(url);
		}
		storePush.setCreate_time(new Date());
		storePush.setModify_time(new Date());
		storePush.setId(storePushService.Add(storePush, image));
		
		StringBuffer json = new StringBuffer();
		json.append(String.format("%s,%s,%s,%s,%s,%s", 	storePush.ToId(""),
														storePush.ToName(""),
														storePush.ToImage(""),
														storePush.ToUrl(""),
														storePush.ToCreate_time(""),
														storePush.ToModify_time("")));

		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
		return null;
	}
}