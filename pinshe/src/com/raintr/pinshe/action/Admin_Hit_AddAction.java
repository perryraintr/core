package com.raintr.pinshe.action;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.HitBean;
import com.raintr.pinshe.service.HitService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_Hit_AddAction extends BaseAction {
	@Autowired
	private HitService hitService;
	
	@RequestMapping(value = "/admin_hit_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String name = request.getParameter("name");
		
		if(StringGlobal.IsNull(name)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"name is null\"}}");
			return null;
		}
		
		name = new String(name.getBytes("ISO-8859-1"), "utf-8");
		
		name = StringGlobal.SerializeJson(name);
		
		HitBean hit = hitService.ByName(name);
		if(hit == null){
			hit = new HitBean();
			hit.setName(name);
			hit.setCount(1);
			hit.setCreate_time(new Date());
			hit.setModify_time(new Date());
			hit.setId(hitService.Add(hit));
		}else{
			hit.setCount(hit.getCount() + 1);
			hit.setModify_time(new Date());
			hitService.Modify(hit);
		}
		
		return null;
	}
}