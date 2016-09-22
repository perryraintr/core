package com.raintr.pinshe.action;


import java.util.List;

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
public class Admin_HitAction extends BaseAction {
	@Autowired
	private HitService hitService;
	
	@RequestMapping(value = "/admin_hit")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String page = request.getParameter("page");
		
		HitBean hit;
		List<HitBean> hits;
		if(!StringGlobal.IsNull(page)){
			hits = hitService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(hits != null && hits.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < hits.size(); i++){
					hit = hits.get(i);
					json.append(String.format("{%s,%s,%s,%s,%s},", 	hit.ToId(""), 
																	hit.ToName(""), 
																	hit.ToCount(""), 
																	hit.ToCreate_time(""), 
																	hit.ToModify_time("")));

				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}