package com.raintr.pinshe.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.GroupMessageBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.service.GroupMessageService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class GroupMessageAction extends BaseAction {
	@Autowired
	private GroupMessageService groupMessageService;

	@RequestMapping(value = "/group_message")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String groupId = request.getParameter("gid");
		String createTime = request.getParameter("time");

		if(StringGlobal.IsNull(groupId)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"gid is null\"}}");
			return null;
		}
		
		if(StringGlobal.IsNull(createTime)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"time is null\"}}");
			return null;
		}
		
		UserBean user;
		PostBean post;
		GroupMessageBean groupMessage;
		List<GroupMessageBean> groupMessages;
		
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
		
		if(!StringGlobal.IsNull(groupId)){
			groupMessages = groupMessageService.ByCreateTime(Integer.parseInt(groupId), sdf.parse(createTime));
			if(groupMessages != null && groupMessages.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < groupMessages.size(); i++){
					groupMessage = groupMessages.get(i);
					user = groupMessage.getUser();
					post = groupMessage.getPost();
					
					if(user == null)
						user = new UserBean();
					
					if(post == null)
						post = new PostBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s", user.ToId("user_"),
																user.ToAvatar("user_"),
																post.ToImage("post_"),
																post.ToFavorite("post_"),
																groupMessage.ToText("")));
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