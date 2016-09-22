package com.raintr.pinshe.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CouponBean;
import com.raintr.pinshe.service.CouponService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Coupon_AddAction extends BaseAction {
	@Autowired
	private CouponService couponService;
	
	@RequestMapping(value = "/coupon_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String memberId = request.getParameter("mid");
		String current = request.getParameter("current");
		String count = request.getParameter("count");
		String amount = request.getParameter("amount");
		String expire = request.getParameter("expire");
		String status = request.getParameter("status");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		CouponBean coupon = new CouponBean();
		if(!StringGlobal.IsNull(memberId))
			coupon.setMember_id(Integer.parseInt(memberId));
		if(!StringGlobal.IsNull(current))
			coupon.setCurrent(Integer.parseInt(current));
		if(!StringGlobal.IsNull(count))
			coupon.setCount(Integer.parseInt(count));
		if(!StringGlobal.IsNull(amount))
			coupon.setAmount(Double.parseDouble(amount));
		if(!StringGlobal.IsNull(expire))
			coupon.setExpire_time(format.parse(expire));
		if(!StringGlobal.IsNull(status))
			coupon.setStatus(Integer.parseInt(status));
		coupon.setCreate_time(new Date());
		coupon.setModify_time(new Date());
		coupon.setId(couponService.Add(coupon));
			
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", coupon.ToId("")));
		return null;
	}
}