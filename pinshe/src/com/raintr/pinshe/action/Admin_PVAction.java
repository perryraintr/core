package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.PVBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.service.PVService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_PVAction extends BaseAction {
	@Autowired
	private PVService pVService;
	
	@RequestMapping(value = "/admin_pv")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String page = request.getParameter("page");
		
		if(StringGlobal.IsNull(page)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"page is null\"}}");
			return null;
		}
		
		PVBean pv;
		ProductBean product;
		List<PVBean> pvs = pVService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
		if(pvs != null && pvs.size() > 0){
			StringBuffer json = new StringBuffer();
			for(int i = 0; i < pvs.size(); i++){
				pv = pvs.get(i);
				
				product = pv.getProduct();
				if(product == null)
					product = new ProductBean();
				
				json.append(String.format("{%s,%s,%s,%s,%s,%s,%s},", pv.ToId("pv_"), 
																	 pv.ToCount("pv_"), 
																	 product.ToId("product_"), 
																	 product.ToName("product_"), 
																	 product.ToImage("product_"),
																	 pv.ToCreate_time("pv_"),
																	 pv.ToModify_time("pv_")));
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