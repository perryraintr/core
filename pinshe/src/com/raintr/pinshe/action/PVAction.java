package com.raintr.pinshe.action;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.PVBean;
import com.raintr.pinshe.service.PVService;
@Controller
@RequestMapping(value = "/")
public class PVAction extends BaseAction {
	@Autowired
	private PVService pvService;
	
	@RequestMapping(value = "/pv")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String productId = request.getParameter("pid");
		String url = request.getParameter("url");
		
		PVBean pv = pvService.ByProductId(Integer.parseInt(productId));
		if(pv != null){
			pv.setCount(pv.getCount() + 1);
			pv.setModify_time(new Date());
			pvService.Modify(pv);
		}else{
			pv = new PVBean();
			pv.setProduct_id(Integer.parseInt(productId));
			pv.setCount(1);
			pv.setCreate_time(new Date());
			pv.setModify_time(new Date());
			pvService.Add(pv);
		}

		return "redirect:" + URLDecoder.decode(url, "utf-8");
	}
}