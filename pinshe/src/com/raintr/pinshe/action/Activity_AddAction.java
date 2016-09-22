package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.ActivityBean;
import com.raintr.pinshe.bean.ActivityDetailBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.service.ActivityDetailService;
import com.raintr.pinshe.service.ActivityService;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Activity_AddAction extends BaseAction {
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ActivityDetailService activityDetailService;
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/activity_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String memberId = request.getParameter("mid");
		String sharerId = request.getParameter("sharer");
		String storeId = request.getParameter("sid");
		String orderId = request.getParameter("oid");
		
		if(StringGlobal.IsNull(memberId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "mid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(orderId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "oid is null"));
			return null;
		}
		
		ActivityBean activity = activityService.ByMemberId(Integer.parseInt(memberId));
		if(activity == null){
			activity = new ActivityBean();
			if(!StringGlobal.IsNull(memberId))
				activity.setMember_id(Integer.parseInt(memberId));
			if(!StringGlobal.IsNull(sharerId))
				activity.setSharer_id(Integer.parseInt(sharerId));
			activity.setCount(activity.getCount() + 1);
			activity.setIs3(0);
			activity.setAmount(activity.getAmount() + 30);
			activity.setCreate_time(new Date());
			activity.setModify_time(new Date());
			activity.setId(activityService.Add(activity));

			ActivityDetailBean activityDetail = new ActivityDetailBean();
			activityDetail.setActivity_id(activity.getId());
			activityDetail.setStore_id(Integer.parseInt(storeId));
			activityDetail.setOrder_id(Integer.parseInt(orderId));
			activityDetailService.Add(activityDetail);
			
			MemberBean member = memberService.ById(Integer.parseInt(memberId));
			if(member != null){
				member.setAmount(member.getAmount() + 30);
				member.setCurrent(member.getCurrent() + 30);
				memberService.Modify(member);
			}
			
			if(!StringGlobal.IsNull(sharerId)){
				member = memberService.ById(Integer.parseInt(sharerId));
				if(member != null){
					member.setAmount(member.getAmount() + 30);
					member.setCurrent(member.getCurrent() + 30);
					memberService.Modify(member);
				}
			}
			
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", activity.ToId("")));
			return null;
		}else{
			ActivityDetailBean activityDetail = activityDetailService.ByActivityIdStoreId(activity.getId(), Integer.parseInt(storeId));
			if(activityDetail == null){
				activityDetail = new ActivityDetailBean();
				activityDetail.setActivity_id(activity.getId());
				activityDetail.setStore_id(Integer.parseInt(storeId));
				activityDetail.setOrder_id(Integer.parseInt(orderId));
				activityDetailService.Add(activityDetail);
				
				MemberBean member = memberService.ById(Integer.parseInt(memberId));
				
				activity.setCount(activity.getCount() + 1);
				if(activity.getIs3() == 0 && activity.getCount() >= 3){
					activity.setIs3(1);
					activity.setAmount(activity.getAmount() + 150);
					if(member != null){
						member.setAmount(member.getAmount() + 150);
						member.setCurrent(member.getCurrent() + 150);
					}
				}
				
				if(member != null){
					member.setAmount(member.getAmount() + 30);
					member.setCurrent(member.getCurrent() + 30);				
					memberService.Modify(member);
				}

				activity.setAmount(activity.getAmount() + 30);
				activity.setModify_time(new Date());
				activityService.Modify(activity);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", activity.ToId("")));
				return null;
			}
		}
	
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}