package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StorePushBean;
import com.raintr.pinshe.service.StorePushService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StorePushAction extends BaseAction {
	@Autowired
	private StorePushService storePushService;

	@RequestMapping(value = "/store_push")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");

		StorePushBean storePush;
		List<StorePushBean> storePushs;
		
		if(!StringGlobal.IsNull(storeId)){
			storePushs = storePushService.ByStoreId(Integer.parseInt(storeId));
			if(storePushs != null && storePushs.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < storePushs.size(); i++){
					storePush = storePushs.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s", 	storePush.ToId(""),
																	storePush.ToName(""),
																	storePush.ToImage(""),
																	storePush.ToUrl(""),
																	storePush.ToCreate_time(""),
																	storePush.ToModify_time("")));
			
					json.append("},");
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}