package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreMemberBean;
import com.raintr.pinshe.service.StoreMemberService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreMemberAction extends BaseAction {
	@Autowired
	private StoreMemberService storeMemberService;
	
	@RequestMapping(value = "/store_member")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String merchantId = request.getParameter("mid");
		
		MerchantBean merchant;
		StoreBean store;
		StoreMemberBean storeMember;
		
		if(!StringGlobal.IsNull(merchantId)){
			List<StoreMemberBean> storeMembers = storeMemberService.ByMerchantId(Integer.parseInt(merchantId));
			if(storeMembers != null && storeMembers.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < storeMembers.size(); i++){
					storeMember = storeMembers.get(i);
					
					merchant = storeMember.getMerchant();
					if(merchant == null)
						merchant = new MerchantBean();
					
					store = storeMember.getStore();
					if(store == null)
						store = new StoreBean();
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	storeMember.ToId(""),
																				store.ToId("store_"),
																				store.ToCurrent("store_"),
																				store.ToName("store_"),
																				store.ToImage("store_"),
																				merchant.ToId("member_"),
																				merchant.ToName("member_"),
																				merchant.ToAvatar("member_"),
																				merchant.ToPhone("member_")));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}

		if(!StringGlobal.IsNull(storeId)){
			List<StoreMemberBean> storeMembers = storeMemberService.ByStoreId(Integer.parseInt(storeId));
			if(storeMembers != null && storeMembers.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < storeMembers.size(); i++){
					storeMember = storeMembers.get(i);
					
					merchant = storeMember.getMerchant();
					if(merchant == null)
						merchant = new MerchantBean();
					
					store = storeMember.getStore();
					if(store == null)
						store = new StoreBean();
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	storeMember.ToId(""),
																				store.ToId("store_"),
																				store.ToCurrent("store_"),
																				store.ToName("store_"),
																				store.ToImage("store_"),
																				merchant.ToId("member_"),
																				merchant.ToName("member_"),
																				merchant.ToAvatar("member_"),
																				merchant.ToPhone("member_")));
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