package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.service.CommodityService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Commodity_AddAction extends BaseAction {
	@Autowired
	private CommodityService commodityService;
	
	@RequestMapping(value = "/commodity_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String t0 = request.getParameter("t0");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		String t3 = request.getParameter("t3");
		String t4 = request.getParameter("t4");
		String t5 = request.getParameter("t5");
		String price = request.getParameter("price");
		String count = request.getParameter("count");
		String cost = request.getParameter("cost");
		String url = request.getParameter("url");
		String memo = request.getParameter("memo");
		String status = request.getParameter("status");
		String rank = request.getParameter("rank");
		
		CommodityBean commodity = commodityService.ById(Integer.parseInt(id));

		if(commodity == null){
			commodity = new CommodityBean();

			if(!StringGlobal.IsNull(id)) 
				commodity.setId(Integer.parseInt(id));
			if(!StringGlobal.IsNull(name)) 
				commodity.setName(name);
			if(!StringGlobal.IsNull(description)) 
				commodity.setDescription(description);
			if(!StringGlobal.IsNull(t0)) 
				commodity.setT0(Integer.parseInt(t0));
			if(!StringGlobal.IsNull(t1)) 
				commodity.setT1(Integer.parseInt(t1));
			if(!StringGlobal.IsNull(t2)) 
				commodity.setT2(Integer.parseInt(t2));
			if(!StringGlobal.IsNull(t3)) 
				commodity.setT3(Integer.parseInt(t3));
			if(!StringGlobal.IsNull(t4)) 
				commodity.setT4(Integer.parseInt(t4));
			if(!StringGlobal.IsNull(t5)) 
				commodity.setT5(Integer.parseInt(t5));
			if(!StringGlobal.IsNull(price))
				commodity.setPrice(Double.parseDouble(price));
			if(!StringGlobal.IsNull(count)) 
				commodity.setCount(Integer.parseInt(count));
			if(!StringGlobal.IsNull(cost)) 
				commodity.setCost(Double.parseDouble(cost));
			if(!StringGlobal.IsNull(url)) 
				commodity.setUrl(url);
			if(!StringGlobal.IsNull(memo)) 
				commodity.setMemo(memo);
			if(!StringGlobal.IsNull(status)) 
				commodity.setStatus(Integer.parseInt(status));
			if(!StringGlobal.IsNull(rank)) 
				commodity.setRank(Integer.parseInt(rank));
			commodity.setCreate_time(new Date());
			commodity.setModify_time(new Date());
			commodity.setId(commodityService.Add(commodity));
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", commodity.ToId("")));
			return null;
		}else{
			if(!StringGlobal.IsNull(name)) 
				commodity.setName(name);
			if(!StringGlobal.IsNull(description)) 
				commodity.setDescription(description);
			if(!StringGlobal.IsNull(t0)) 
				commodity.setT0(Integer.parseInt(t0));
			if(!StringGlobal.IsNull(t1)) 
				commodity.setT1(Integer.parseInt(t1));
			if(!StringGlobal.IsNull(t2)) 
				commodity.setT2(Integer.parseInt(t2));
			if(!StringGlobal.IsNull(t3)) 
				commodity.setT3(Integer.parseInt(t3));
			if(!StringGlobal.IsNull(t4)) 
				commodity.setT4(Integer.parseInt(t4));
			if(!StringGlobal.IsNull(t5)) 
				commodity.setT5(Integer.parseInt(t5));
			if(!StringGlobal.IsNull(price)) 
				commodity.setPrice(Double.parseDouble(price));
			if(!StringGlobal.IsNull(count)) 
				commodity.setCount(Integer.parseInt(count));
			if(!StringGlobal.IsNull(cost)) 
				commodity.setCost(Double.parseDouble(cost));
			if(!StringGlobal.IsNull(url))
				commodity.setUrl(url);
			if(!StringGlobal.IsNull(memo)) 
				commodity.setMemo(memo);
			if(!StringGlobal.IsNull(status)) 
				commodity.setStatus(Integer.parseInt(status));
			commodity.setModify_time(new Date());
			commodityService.Modify(commodity);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", commodity.ToId("")));
			return null;
		}
	}
}