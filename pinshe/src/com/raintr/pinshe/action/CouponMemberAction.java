package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CouponBean;
import com.raintr.pinshe.bean.CouponMemberBean;
import com.raintr.pinshe.service.CouponMemberService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class CouponMemberAction extends BaseAction {
	@Autowired
	private CouponMemberService couponMemberService;
	
	@RequestMapping(value = "/coupon_member")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");

		CouponBean coupon;
		CouponMemberBean couponMember;
		List<CouponMemberBean> couponMembers;

		if(!StringGlobal.IsNull(memberId)){
			couponMembers = couponMemberService.ByMemberId(Integer.parseInt(memberId));
			if(couponMembers != null && couponMembers.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < couponMembers.size(); i++){
					couponMember = couponMembers.get(i);
					if(couponMember == null)
						couponMember = new CouponMemberBean();
					
					coupon = couponMember.getCoupon();
					if(coupon == null)
						coupon = new CouponBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	couponMember.ToId(""),
																				couponMember.ToStatus(""),
																				couponMember.ToCreate_time(""),
																				couponMember.ToModify_time(""),
																				coupon.ToId("coupon_"),
																				coupon.ToCurrent("coupon_"),
																				coupon.ToCount("coupon_"),
																				coupon.ToAmount("coupon_"),
																				coupon.ToExpire_time("coupon_"),
																				coupon.ToStatus("coupon_")));
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