package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.bean.GroupMemberBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.service.GroupMemberService;
import com.raintr.pinshe.service.GroupService;
import com.raintr.pinshe.service.UserService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Chat_User_AddAction extends BaseAction {
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupMemberService groupMemberService;
	
	@RequestMapping(value = "/chat_user_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String code = request.getParameter("code");
		String groupId = request.getParameter("state");
		
		UserBean user = userService.ByWechat(code);
		
		if(user != null){
			// add user
			String name = request.getParameter("name");
			String wechat_guid = request.getParameter("wcid");
			String wechat = request.getParameter("wechat");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String avatar = request.getParameter("avatar");
			
			name = user.getName();
			wechat_guid = user.getWechat_guid();
			avatar = user.getAvatar();

			if(StringGlobal.IsNull(wechat_guid))
				return "redirect:" + userService.getUrl();
			
			if(StringGlobal.IsNull(name))
				return "redirect:" + userService.getUrl();
			
			user = userService.ByWechatGuid(wechat_guid);
			
			if(user == null){
				name = StringGlobal.SerializeJson(name);
				wechat = StringGlobal.SerializeJson(wechat);
				phone = StringGlobal.SerializeJson(phone);
				address = StringGlobal.SerializeJson(address);
				
				MultipartFile file = null;
				try{
					file = ((MultipartHttpServletRequest)request).getFile("file");
				}catch(Exception ex){}
				
				user = new UserBean();
				if(!StringGlobal.IsNull(name))
					user.setName(name);
				if(!StringGlobal.IsNull(wechat_guid))
					user.setWechat_guid(wechat_guid);
				if(!StringGlobal.IsNull(wechat))
					user.setWechat(wechat);
				if(!StringGlobal.IsNull(phone))
					user.setPhone(phone);
				if(!StringGlobal.IsNull(address))
					user.setAddress(address);
				if(!StringGlobal.IsNull(avatar))
					user.setAvatar(avatar);
				
				user.setCreate_time(new Date());
				user.setModify_time(new Date());
				user.setId(userService.Add(user, file));
			}

			userService.Add(user.getId(), wechat_guid, name);
			
			// add member
			GroupBean group = groupService.ById(Integer.parseInt(groupId));
			if(group != null){
				GroupMemberBean groupMember = new GroupMemberBean();
				groupMember.setGroup_id(group.getId());
				groupMember.setUser_id(user.getId());
				groupMember = groupMemberService.ByGroupIdUserId(groupMember);
				if(groupMember == null){
					groupMember = new GroupMemberBean();
					groupMember.setGroup_id(group.getId());
					groupMember.setUser_id(user.getId());
					groupMember.setKick(0);
					groupMember.setCreate_time(new Date());
					groupMember.setModify_time(new Date());
					groupMemberService.Add(groupMember);
					
					group.setMember_count(group.getMember_count() + 1);
					groupService.Modify(group, null);
					
					groupService.AddUser(group.getEasemob_id(), groupMember.getUser_id());
				}
			
				if(groupMember.getKick() == 0)
					return "redirect:" + userService.getUrl() + "?wcid=" + wechat_guid + "&gid=" + groupId + "&emid=" + group.getEasemob_id();
			}
		}
		
		return "redirect:" + userService.getUrl() + "?gid=" + groupId;
	}
}