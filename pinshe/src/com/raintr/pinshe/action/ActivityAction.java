package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.ActivityBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.service.ActivityService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class ActivityAction extends BaseAction {
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping(value = "/activity")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String memberId = request.getParameter("mid");
		String sharerId = request.getParameter("sharerid");
		String page = request.getParameter("page");
		
		if(!StringGlobal.IsNull(memberId) && !StringGlobal.IsNull(sharerId)){
			ActivityBean activity = activityService.ByMemberId(Integer.parseInt(memberId));
			if(activity != null){
				if(activity.getSharer_id() == Integer.parseInt(sharerId)){
					response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", activity.ToId("")));
					return null;
				}
			}

			String tmp = memberId;
			memberId = sharerId;
			sharerId = tmp;
			
			activity = activityService.ByMemberId(Integer.parseInt(memberId));
			if(activity != null){
				if(activity.getSharer_id() == Integer.parseInt(sharerId)){
					response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", activity.ToId("")));
					return null;
				}
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(memberId)){
			ActivityBean activity = activityService.ByMemberId(Integer.parseInt(memberId));
			if(activity != null){
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", activity.ToId("")));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(page)){
			ActivityBean activity;
			MemberBean member;
			MemberBean sharer;
			List<ActivityBean> activitys = activityService.By((Integer.parseInt(page) - 1) * 100);
			if(activitys != null && activitys.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < activitys.size(); i++){
					activity = activitys.get(i);
					if(activity == null)
						activity = new ActivityBean();
					
					member = activity.getMember();
					if(member == null)
						member = new MemberBean();
					
					sharer = activity.getSharer();
					if(sharer == null)
						sharer = new MemberBean();
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	activity.ToId(""),
																					activity.ToCount(""),
																					activity.ToIs3(""),
																					activity.ToAmount(""),
																					activity.ToCreate_time(""),
																					activity.ToModify_time(""),
																					member.ToId("member_"),
																					member.ToName("member_"),
																					sharer.ToId("sharer_"),
																					sharer.ToName("sharer_")));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}