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
import com.raintr.pinshe.bean.GroupMessageBean;
import com.raintr.pinshe.service.GroupMemberService;
import com.raintr.pinshe.service.GroupMessageService;
import com.raintr.pinshe.service.GroupService;
import com.raintr.pinshe.service.UserService;
import com.raintr.pinshe.utils.StringGlobal;


@Controller
@RequestMapping(value = "/")
public class Chat_Group_SendAction extends BaseAction {
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupMessageService groupMessageService;
	@Autowired
	private UserService userService;
	@Autowired
	private GroupMemberService groupMemberService;
	
	@RequestMapping(value = "/chat_group_send")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String groupId = request.getParameter("gid");
		String userId = request.getParameter("uid");
		
		String postId = request.getParameter("pid");
		String text = request.getParameter("text");
		String image = request.getParameter("image");
		String audio = request.getParameter("audio");

		
		if(StringGlobal.IsNull(groupId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "gid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(text) && StringGlobal.IsNull(image) && StringGlobal.IsNull(audio)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "text image audio is null"));
			return null;
		}
		
		GroupBean group = groupService.ById(Integer.parseInt(groupId));
		if(group != null){
			GroupMemberBean groupMember = new GroupMemberBean();
			groupMember.setGroup_id(group.getId());
			groupMember.setUser_id(Integer.parseInt(userId));
			groupMember = groupMemberService.ByGroupIdUserId(groupMember);
			
			if(groupMember != null && groupMember.getBan() == 0){
				GroupMessageBean groupMessage = new GroupMessageBean();
				groupMessage.setGroup_id(group.getId());
				groupMessage.setUser_id(Integer.parseInt(userId));
				
				if(!StringGlobal.IsNull(postId))
					groupMessage.setPost_id(Integer.parseInt(postId));
				if(!StringGlobal.IsNull(text))
					groupMessage.setText(text);
				if(!StringGlobal.IsNull(image))
					groupMessage.setImage(image);
				if(!StringGlobal.IsNull(audio))
					groupMessage.setAudio(audio);
				groupMessage.setCreate_time(new Date());
				groupMessage.setModify_time(new Date());
				groupMessageService.Add(groupMessage);
				
				groupService.SendTxt(group.getEasemob_id(), text, Integer.parseInt(userId));
			}
		}
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}