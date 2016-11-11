package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StorePaymentBean;
import com.raintr.pinshe.service.StorePaymentService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StorePayment_AddAction extends BaseAction {
	@Autowired
	private StorePaymentService storePaymentService;
	
	@RequestMapping(value = "/store_payment_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String type = request.getParameter("type");
		String company = request.getParameter("company");
		String account = request.getParameter("account");
		String holder = request.getParameter("holder");
		
		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(account)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "account is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(holder)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "holder is null"));
			return null;
		}
		
		StorePaymentBean storePayment = new StorePaymentBean();
		if(!StringGlobal.IsNull(storeId))
			storePayment.setStore_id(Integer.parseInt(storeId));
		if(!StringGlobal.IsNull(type))
			storePayment.setType(Integer.parseInt(type));
		if(!StringGlobal.IsNull(company))
			storePayment.setCompany(company);
		if(!StringGlobal.IsNull(account))
			storePayment.setAccount(account);
		if(!StringGlobal.IsNull(holder))
			storePayment.setHolder(holder);
		storePayment.setCreate_time(new Date());
		storePayment.setModify_time(new Date());
		storePayment.setId(storePaymentService.Add(storePayment));

		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", storePayment.ToId("")));
		return null;
	}
}