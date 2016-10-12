package com.raintr.pinshe.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.service.StoreService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Store_ModifyAction extends BaseAction {
	@Autowired
	private StoreService storeService;
	
	@RequestMapping(value = "/store_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");

		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		String merchantId = request.getParameter("mid");
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		String name = request.getParameter("name");
		String star = request.getParameter("star");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		String date = request.getParameter("date");
		String slogan = request.getParameter("slogan");
		String owner = request.getParameter("owner");
		String recommend = request.getParameter("recommend");
		String feature1 = request.getParameter("feature1");
		String feature2 = request.getParameter("feature2");
		String feature3 = request.getParameter("feature3");
		String video = request.getParameter("video");
		String activity = request.getParameter("activity");
		String comment = request.getParameter("comment");
		String payment = request.getParameter("payment");
		String invaild = request.getParameter("invaild");
		String description = request.getParameter("description");

		StoreBean store = storeService.ById(Integer.parseInt(id));
		if(store != null){
			name = StringGlobal.SerializeJson(name);
			address = StringGlobal.SerializeJson(address);
			phone = StringGlobal.SerializeJson(phone);
			date = StringGlobal.SerializeJson(date);
			slogan = StringGlobal.SerializeJson(slogan);
			owner = StringGlobal.SerializeJson(owner);
			recommend = StringGlobal.SerializeJson(recommend);
			feature1 = StringGlobal.SerializeJson(feature1);
			feature2 = StringGlobal.SerializeJson(feature2);
			feature3 = StringGlobal.SerializeJson(feature3);
			activity = StringGlobal.SerializeJson(activity);
			payment = StringGlobal.SerializeJson(payment);
			description = StringGlobal.SerializeJson(description);
			
			MultipartFile avatar = null;
			try{
				avatar = ((MultipartHttpServletRequest)request).getFile("avatar");
			}catch(Exception ex){}
			
			List<MultipartFile> files = null;
			try{
				files = ((MultipartHttpServletRequest)request).getFiles("file");
			}catch(Exception ex){}
			
			if(!StringGlobal.IsNull(merchantId)) 
				store.setMerchant_id(Integer.parseInt(merchantId));
			if(!StringGlobal.IsNull(longitude)) 
				store.setLongitude(Double.parseDouble(longitude));
			if(!StringGlobal.IsNull(latitude)) 
				store.setLatitude(Double.parseDouble(latitude));
			if(!StringGlobal.IsNull(name)) 
				store.setName(name);
			if(!StringGlobal.IsNull(star)) 
				store.setStar(Integer.parseInt(star));
			if(!StringGlobal.IsNull(address)) 
				store.setAddress(address);
			if(!StringGlobal.IsNull(phone)) 
				store.setPhone(phone);
			if(!StringGlobal.IsNull(date)) 
				store.setDate(date);
			if(!StringGlobal.IsNull(slogan))
				store.setSlogan(slogan);
			if(!StringGlobal.IsNull(owner)) 
				store.setOwner(owner);
			if(!StringGlobal.IsNull(recommend)) 
				store.setRecommend(recommend);
			if(!StringGlobal.IsNull(feature1)) 
				store.setFeature1(feature1);
			if(!StringGlobal.IsNull(feature2)) 
				store.setFeature2(feature2);
			if(!StringGlobal.IsNull(feature3)) 
				store.setFeature3(feature3);
			if(!StringGlobal.IsNull(video)) 
				store.setVideo(video);
			if(!StringGlobal.IsNull(activity)) 
				store.setActivity(activity);
			if(!StringGlobal.IsNull(comment)) 
				store.setComment(Integer.parseInt(comment));
			if(!StringGlobal.IsNull(payment)) 
				store.setPayment(payment);
			if(!StringGlobal.IsNull(invaild)) 
				store.setInvaild(Integer.parseInt(invaild));
			if(!StringGlobal.IsNull(description)) 
				store.setDescription(description);
			store.setModify_time(new Date());
			storeService.Modify(store, avatar, files);
	
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", store.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}