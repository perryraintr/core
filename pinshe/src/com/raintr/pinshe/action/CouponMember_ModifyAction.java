package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CouponMemberBean;
import com.raintr.pinshe.service.CouponMemberService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class CouponMember_ModifyAction extends BaseAction {
	@Autowired
	private CouponMemberService couponMemberService;
	
	@RequestMapping(value = "/coupon_member_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
//		String memberId = request.getParameter("mid");
//		String couponId = request.getParameter("couponid");
//
//		if(StringGlobal.IsNull(memberId)){
//			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"mid is null\"}}");
//			return null;
//		}
//		
//		if(StringGlobal.IsNull(couponId)){
//			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"couponid is null\"}}");
//			return null;
//		}
//		
//		String status = request.getParameter("status");
//		
//		CouponMemberBean couponMember = couponMemberService.ByMemberIdCouponId(Integer.parseInt(memberId), Integer.parseInt(couponId));
//		if(couponMember != null){
//			if(!StringGlobal.IsNull(status))
//				couponMember.setStatus(Integer.parseInt(status));
//			couponMember.setModify_time(new Date());
//			couponMemberService.Modify(couponMember);
//				
//			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", couponMember.ToId("")));
//			return null;
//		}
//		
//		response.getWriter().print("{\"head\":1,\"body\":{}}");
//		return null;
		
		
		
		String id = request.getParameter("id");

		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		String status = request.getParameter("status");
		
		CouponMemberBean couponMember = couponMemberService.ById(Integer.parseInt(id));
		if(couponMember != null){
			if(!StringGlobal.IsNull(status))
				couponMember.setStatus(Integer.parseInt(status));
			couponMember.setModify_time(new Date());
			couponMemberService.Modify(couponMember);
				
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", couponMember.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
		
		
		
	}
}