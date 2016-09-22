package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.bean.GroupMemberBean;
import com.raintr.pinshe.service.GroupMemberService;
import com.raintr.pinshe.service.GroupService;
import com.raintr.pinshe.service.UserService;
import com.raintr.pinshe.utils.StringGlobal;


@Controller
@RequestMapping(value = "/")
public class Chat_Group_User_AddAction extends BaseAction {
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupMemberService groupMemberService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/chat_group_user_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String groupId = request.getParameter("gid");
		String userId = request.getParameter("uid");

		if(StringGlobal.IsNull(groupId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "gid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uid is null"));
			return null;
		}
		
		GroupBean group = groupService.ById(Integer.parseInt(groupId));
		if(group != null){
			GroupMemberBean groupMember = new GroupMemberBean();
			groupMember.setGroup_id(group.getId());
			groupMember.setUser_id(Integer.parseInt(userId));
			groupMember = groupMemberService.ByGroupIdUserId(groupMember);
			if(groupMember == null){
				groupMember = new GroupMemberBean();
				groupMember.setGroup_id(group.getId());
				groupMember.setUser_id(Integer.parseInt(userId));
				groupMember.setKick(0);
				groupMember.setCreate_time(new Date());
				groupMember.setModify_time(new Date());
				groupMemberService.Add(groupMember);
				
				group.setMember_count(group.getMember_count() + 1);
				groupService.Modify(group, null);
			}
		
			groupService.AddUser(group.getEasemob_id(), groupMember.getUser_id());
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}