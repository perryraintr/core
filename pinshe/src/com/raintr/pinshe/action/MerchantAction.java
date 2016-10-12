package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.service.MerchantService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class MerchantAction extends BaseAction {
	@Autowired
	private MerchantService merchantService;
	
	@RequestMapping(value = "/merchant")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String page = request.getParameter("page");
		String wechatId = request.getParameter("wcid");
		String phone = request.getParameter("phone");

		MerchantBean merchant;
		List<MerchantBean> merchants;
		
		if(!StringGlobal.IsNull(page)){
			merchants = merchantService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(merchants != null && merchants.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < merchants.size(); i++){
					merchant = merchants.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", merchant.ToId(""),
																			merchant.ToWechat_id(""),
																			merchant.ToName(""),
																			merchant.ToPhone(""),
																			merchant.ToAvatar(""),
																			merchant.ToCurrent(""),
																			merchant.ToAmount(""),
																			merchant.ToCreate_time(""),
																			merchant.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(id)){
			merchant = merchantService.ById(Integer.parseInt(id));
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", merchant.ToId(""),
																	merchant.ToWechat_id(""),
																	merchant.ToName(""),
																	merchant.ToPhone(""),
																	merchant.ToAvatar(""),
																	merchant.ToCurrent(""),
																	merchant.ToAmount(""),
																	merchant.ToCreate_time(""),
																	merchant.ToModify_time("")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		
		if(!StringGlobal.IsNull(wechatId)){
			merchant = merchantService.ByWechatId(wechatId);
			if(merchant != null){
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", merchant.ToId(""),
																		merchant.ToWechat_id(""),
																		merchant.ToName(""),
																		merchant.ToPhone(""),
																		merchant.ToAvatar(""),
																		merchant.ToCurrent(""),
																		merchant.ToAmount(""),
																		merchant.ToCreate_time(""),
																		merchant.ToModify_time("")));
	
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(phone)){
			merchant = merchantService.ByPhone(phone);
			if(merchant != null){
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", merchant.ToId(""),
																		merchant.ToWechat_id(""),
																		merchant.ToName(""),
																		merchant.ToPhone(""),
																		merchant.ToAvatar(""),
																		merchant.ToCurrent(""),
																		merchant.ToAmount(""),
																		merchant.ToCreate_time(""),
																		merchant.ToModify_time("")));
	
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}