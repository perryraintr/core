package com.raintr.pinshe.action;

import java.util.List;

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
public class StorePaymentAction extends BaseAction {
	@Autowired
	private StorePaymentService storePaymentService;

	@RequestMapping(value = "/store_payment")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String storeId = request.getParameter("sid");

		StorePaymentBean storePayment;
		List<StorePaymentBean> storePayments;
		
		if(!StringGlobal.IsNull(storeId)){
			storePayments = storePaymentService.ByStoreId(Integer.parseInt(storeId));
			if(storePayments != null && storePayments.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < storePayments.size(); i++){
					storePayment = storePayments.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s", 	storePayment.ToId(""),
																		storePayment.ToType(""),
																		storePayment.ToCompany(""),
																		storePayment.ToAccount(""),
																		storePayment.ToHolder(""),
																		storePayment.ToCreate_time(""),
																		storePayment.ToModify_time("")));
			
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
		
		if(!StringGlobal.IsNull(id)){
			storePayment = storePaymentService.ById(Integer.parseInt(id));
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s", 	storePayment.ToId(""),
																storePayment.ToType(""),
																storePayment.ToCompany(""),
																storePayment.ToAccount(""),
																storePayment.ToHolder(""),
																storePayment.ToCreate_time(""),
																storePayment.ToModify_time("")));
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}