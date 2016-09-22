package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CouponBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.service.CouponService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class CouponAction extends BaseAction {
	@Autowired
	private CouponService couponService;
	
	@RequestMapping(value = "/coupon")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String page = request.getParameter("page");
		String memberId = request.getParameter("mid");
		
		MemberBean member;
		CouponBean coupon;
		List<CouponBean> coupons;
		
		if(!StringGlobal.IsNull(page)){
			coupons = couponService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(coupons != null && coupons.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < coupons.size(); i++){
					coupon = coupons.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s", 	coupon.ToId(""),
																			coupon.ToCurrent(""),
																			coupon.ToCount(""),
																			coupon.ToAmount(""),
																			coupon.ToExpire_time(""),
																			coupon.ToStatus(""),
																			coupon.ToCreate_time(""),
																			coupon.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(id)){
			coupon = couponService.ById(Integer.parseInt(id));
			member = coupon.getMember();
			if(member == null)
				member = new MemberBean();
			
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	coupon.ToId(""),
																			coupon.ToCurrent(""),
																			coupon.ToCount(""),
																			coupon.ToAmount(""),
																			coupon.ToExpire_time(""),
																			coupon.ToStatus(""),
																			coupon.ToCreate_time(""),
																			coupon.ToModify_time(""),
																			member.ToId("member_"),
																			member.ToName("member_"),
																			member.ToAvatar("member_")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		if(!StringGlobal.IsNull(memberId)){
			coupons = couponService.ByCouponMember(Integer.parseInt(memberId));
			if(coupons != null && coupons.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < coupons.size(); i++){
					coupon = coupons.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s", 	coupon.ToId(""),
																			coupon.ToCurrent(""),
																			coupon.ToCount(""),
																			coupon.ToAmount(""),
																			coupon.ToExpire_time(""),
																			coupon.ToStatus(""),
																			coupon.ToCreate_time(""),
																			coupon.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}