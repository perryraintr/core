package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.service.GetuiService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class GetuiAction extends BaseAction {
	@Autowired
	private GetuiService getuiService;
	
	@RequestMapping(value = "/getui")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	// cbd07c9d3b792dc6715f75b5a3afcd32
	// a7911cd0ac46428f8609c80911c45a52
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		String device = request.getParameter("device");
		String cid = request.getParameter("cid");
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		if(!StringGlobal.IsNull(cid)){
			if(!StringGlobal.IsNull(title))
				title = new String(title.getBytes("ISO-8859-1"), "utf-8");
			if(!StringGlobal.IsNull(body))
				body = new String(body.getBytes("ISO-8859-1"), "utf-8");
			
			String result = "";
			if("0".equals(device))
				result = getuiService.SendIOS(cid, title, body);
			else if("1".equals(device))
				result = getuiService.SendAndroid(cid, title, body);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":\"%s\"}", result));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}