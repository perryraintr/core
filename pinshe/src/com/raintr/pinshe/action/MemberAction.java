package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.service.MemberService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class MemberAction extends BaseAction {
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/member")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");
		String page = request.getParameter("page");
		String wechatId = request.getParameter("wcid");
		String phone = request.getParameter("phone");

		MemberBean member;
		List<MemberBean> members;
		
		if(!StringGlobal.IsNull(page)){
			members = memberService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(members != null && members.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < members.size(); i++){
					member = members.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", member.ToId(""),
																			member.ToWechat_id(""),
																			member.ToName(""),
																			member.ToPhone(""),
																			member.ToAvatar(""),
																			member.ToCurrent(""),
																			member.ToAmount(""),
																			member.ToCreate_time(""),
																			member.ToModify_time("")));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(id)){
			member = memberService.ById(Integer.parseInt(id));
			StringBuffer json = new StringBuffer();
			json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", member.ToId(""),
																	member.ToWechat_id(""),
																	member.ToName(""),
																	member.ToPhone(""),
																	member.ToAvatar(""),
																	member.ToCurrent(""),
																	member.ToAmount(""),
																	member.ToCreate_time(""),
																	member.ToModify_time("")));

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}
		
		
		if(!StringGlobal.IsNull(wechatId)){
			member = memberService.ByWechatId(wechatId);
			if(member != null){
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", member.ToId(""),
																		member.ToWechat_id(""),
																		member.ToName(""),
																		member.ToPhone(""),
																		member.ToAvatar(""),
																		member.ToCurrent(""),
																		member.ToAmount(""),
																		member.ToCreate_time(""),
																		member.ToModify_time("")));
	
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(phone)){
			member = memberService.ByPhone(phone);
			if(member != null){
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", member.ToId(""),
																		member.ToWechat_id(""),
																		member.ToName(""),
																		member.ToPhone(""),
																		member.ToAvatar(""),
																		member.ToCurrent(""),
																		member.ToAmount(""),
																		member.ToCreate_time(""),
																		member.ToModify_time("")));
	
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}