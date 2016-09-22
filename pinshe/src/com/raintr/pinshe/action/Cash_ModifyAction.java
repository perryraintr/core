package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CashBean;
import com.raintr.pinshe.service.CashService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Cash_ModifyAction extends BaseAction {
	@Autowired
	private CashService cashService;
	
	@RequestMapping(value = "/cash_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");

		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		String storeId = request.getParameter("sid");
		String memberId = request.getParameter("mid");
		String orderId = request.getParameter("oid");
		String amount = request.getParameter("amount");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		
		CashBean cash = cashService.ById(Integer.parseInt(id));
		if(cash != null){
			if(!StringGlobal.IsNull(storeId))
				cash.setStore_id(Integer.parseInt(storeId));
			if(!StringGlobal.IsNull(memberId))
				cash.setMember_id(Integer.parseInt(memberId));
			if(!StringGlobal.IsNull(orderId))
				cash.setOrder_id(Integer.parseInt(orderId));
			if(!StringGlobal.IsNull(amount))
				cash.setAmount(Double.parseDouble(amount));
			if(!StringGlobal.IsNull(type))
				cash.setType(Integer.parseInt(type));
			if(!StringGlobal.IsNull(status))
				cash.setStatus(Integer.parseInt(status));
			cash.setModify_time(new Date());
			cashService.Modify(cash);

			
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s", 	cash.ToId(""),
															cash.ToAmount(""),
															cash.ToType(""),
															cash.ToStatus(""),
															cash.ToCreate_time(""),
															cash.ToModify_time("")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}