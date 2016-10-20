package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StoreCashBean;
import com.raintr.pinshe.service.StoreCashService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreCash_ModifyAction extends BaseAction {
	@Autowired
	private StoreCashService storeCashService;
	
	@RequestMapping(value = "/store_cash_modify")
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
		String total = request.getParameter("total");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		
		StoreCashBean storeCash = storeCashService.ById(Integer.parseInt(id));
		if(storeCash != null){
			if(!StringGlobal.IsNull(storeId))
				storeCash.setStore_id(Integer.parseInt(storeId));
			if(!StringGlobal.IsNull(memberId))
				storeCash.setMember_id(Integer.parseInt(memberId));
			if(!StringGlobal.IsNull(orderId))
				storeCash.setOrder_id(Integer.parseInt(orderId));
			if(!StringGlobal.IsNull(amount))
				storeCash.setAmount(Double.parseDouble(amount));
			if(!StringGlobal.IsNull(total))
				storeCash.setTotal(Double.parseDouble(total));
			if(!StringGlobal.IsNull(type))
				storeCash.setType(Integer.parseInt(type));
			if(!StringGlobal.IsNull(status))
				storeCash.setStatus(Integer.parseInt(status));
			storeCash.setModify_time(new Date());
			storeCashService.Modify(storeCash);

			
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s", 	storeCash.ToId(""),
																storeCash.ToAmount(""),
																storeCash.ToTotal(""),
																storeCash.ToType(""),
																storeCash.ToStatus(""),
																storeCash.ToCreate_time(""),
																storeCash.ToModify_time("")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}