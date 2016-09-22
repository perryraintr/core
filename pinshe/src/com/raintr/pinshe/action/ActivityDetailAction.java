package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.ActivityBean;
import com.raintr.pinshe.bean.ActivityDetailBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.service.ActivityDetailService;
import com.raintr.pinshe.service.ActivityService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class ActivityDetailAction extends BaseAction {
	@Autowired
	private ActivityDetailService activityDetailService;
	@Autowired
	private ActivityService activityService;
	
	@RequestMapping(value = "/activity_detail")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String activityId = request.getParameter("aid");
		
		String memberId = request.getParameter("mid");
		String storeId = request.getParameter("sid");
		
		if(!StringGlobal.IsNull(memberId) && !StringGlobal.IsNull(storeId)){
			OrderBean order;
			ActivityBean activity = activityService.ByMemberId(Integer.parseInt(memberId));
			if(activity != null){
				ActivityDetailBean activityDetail = activityDetailService.ByActivityIdStoreId(activity.getId(), Integer.parseInt(storeId));
				if(activityDetail != null){
					StringBuffer json = new StringBuffer();
						
					order = activityDetail.getOrder();
					if(order == null)
						order = new OrderBean();
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s", 	order.ToId("order_"),
																			order.ToOrder_no("order_"),
																			order.ToCurrent("order_"),
																			order.ToAmount("order_"),
																			order.ToType("order_"),
																			order.ToStatus("order_"),
																			order.ToCreate_time("order_"),
																			order.ToModify_time("order_")));
					
					response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
					return null;
				}
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(activityId)){
			OrderBean order;
			ActivityDetailBean activityDetail;
			List<ActivityDetailBean> activityDetails = activityDetailService.ByActivityId(Integer.parseInt(activityId));
			if(activityDetails != null && activityDetails.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < activityDetails.size(); i++){
					activityDetail = activityDetails.get(i);
					if(activityDetail == null)
						activityDetail = new ActivityDetailBean();
					
					order = activityDetail.getOrder();
					if(order == null)
						order = new OrderBean();
					
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s},", order.ToId("order_"),
																			order.ToOrder_no("order_"),
																			order.ToCurrent("order_"),
																			order.ToAmount("order_"),
																			order.ToType("order_"),
																			order.ToStatus("order_"),
																			order.ToCreate_time("order_"),
																			order.ToModify_time("order_")));
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