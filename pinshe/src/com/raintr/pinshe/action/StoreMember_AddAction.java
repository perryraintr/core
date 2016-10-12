package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StoreMemberBean;
import com.raintr.pinshe.service.StoreMemberService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreMember_AddAction extends BaseAction {
	@Autowired
	private StoreMemberService storeMemberService;
	
	@RequestMapping(value = "/store_member_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String merchantId = request.getParameter("mid");

		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(merchantId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "mid is null"));
			return null;
		}
		
		StoreMemberBean storeMember = storeMemberService.ByStoreIdMerchantId(Integer.parseInt(storeId), Integer.parseInt(merchantId));
		if(storeMember == null){
			storeMember = new StoreMemberBean();
			if(!StringGlobal.IsNull(storeId))
				storeMember.setStore_id(Integer.parseInt(storeId));
			if(!StringGlobal.IsNull(merchantId))
				storeMember.setMerchant_id(Integer.parseInt(merchantId));
			storeMember.setCreate_time(new Date());
			storeMember.setModify_time(new Date());
			storeMember.setId(storeMemberService.Add(storeMember));
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", storeMember.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}