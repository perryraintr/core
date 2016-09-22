package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.AdBean;
import com.raintr.pinshe.service.AdService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class AdAction extends BaseAction {
	@Autowired
	private AdService adService;
	
	@RequestMapping(value = "/ad")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		AdBean ad;
		
		String type = request.getParameter("t2");
		String page = request.getParameter("page");
		
		if(StringGlobal.IsNull(type)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "t2 is null"));
			return null;
		}
		
		List<AdBean> ads = adService.ByType(Integer.parseInt(type), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
		if(ads != null){
			StringBuffer json = new StringBuffer();
			for(int i = 0; i < ads.size(); i++){
				ad = ads.get(i);
				json.append(String.format("{%s,%s,%s},", ad.ToName(""), ad.ToImage(""), ad.ToUrl("")));
			}
			
			if(json.length() > 0)
				json.setLength(json.length() - 1);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
			return null;
		}
	
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}