package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CouponBean;
import com.raintr.pinshe.bean.CouponMemberBean;
import com.raintr.pinshe.service.CouponMemberService;
import com.raintr.pinshe.service.CouponService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class CouponMember_AddAction extends BaseAction {
	@Autowired
	private CouponService couponService;
	@Autowired
	private CouponMemberService couponMemberService;
	
	@RequestMapping(value = "/coupon_member_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");
		String couponId = request.getParameter("couponid");
		String status = request.getParameter("status");
		
		CouponBean coupon = couponService.ById(Integer.parseInt(couponId));
		
		if(coupon != null && coupon.getCurrent() > 0 && (new Date()).getTime() <= coupon.getExpire_time().getTime()){
			CouponMemberBean couponMember = couponMemberService.ByMemberIdCouponId(Integer.parseInt(memberId), Integer.parseInt(couponId));
			if(couponMember == null){
				couponMember = new CouponMemberBean();
				if(!StringGlobal.IsNull(couponId))
					couponMember.setCoupon_id(Integer.parseInt(couponId));
				if(!StringGlobal.IsNull(memberId))
					couponMember.setMember_id(Integer.parseInt(memberId));
				if(!StringGlobal.IsNull(status))
					couponMember.setStatus(Integer.parseInt(status));
				couponMember.setCreate_time(new Date());
				couponMember.setModify_time(new Date());
				couponMember.setId(couponMemberService.Add(couponMember));
					
				coupon.setCurrent(coupon.getCurrent() - 1);
				couponService.Modify(coupon);
				
				// coupon owner
				if(coupon.getAmount() == 20 && coupon.getMember_id() > 0){
					couponMember = new CouponMemberBean();
					if(!StringGlobal.IsNull(couponId))
						couponMember.setCoupon_id(Integer.parseInt(couponId));
					couponMember.setMember_id(coupon.getMember_id());
					if(!StringGlobal.IsNull(status))
						couponMember.setStatus(Integer.parseInt(status));
					couponMember.setCreate_time(new Date());
					couponMember.setModify_time(new Date());
					couponMemberService.Add(couponMember);
				}
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", couponMember.ToId("")));
				return null;
			}
		}

		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}