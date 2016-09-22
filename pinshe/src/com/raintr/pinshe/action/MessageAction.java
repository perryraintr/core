package com.raintr.pinshe.action;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MessageBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.service.MessageService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class MessageAction extends BaseAction {
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value = "/message")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		UserBean user;
		PostBean post;
		VoteBean vote;
		MessageBean message;
		
		String userId = request.getParameter("uid");
		String modifyTime = request.getParameter("time");
		String page = request.getParameter("page");
		
		if(!StringGlobal.IsNull(userId) && !StringGlobal.IsNull(modifyTime)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			List<MessageBean> messages = messageService.ByUserIdAndModifyTime(Integer.parseInt(userId), sdf.parse(modifyTime));
			if(messages != null){
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"count\":%d}}", messages.size()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(userId)){
			List<MessageBean> messages = messageService.ByUserId(Integer.parseInt(userId), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(messages != null){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < messages.size(); i++){
					message = messages.get(i);
					
					if(message.getVote_id() > 0){
						vote = message.getVote();
						post = vote.getPost_a();
						user = message.getUser_a();
						
						json.append(String.format("{%s,%s,%s,%s,%s,%s,\"type\":%d,%s,%s,%s},",   message.ToId(""),
																								 message.ToMessage(""),
																								 message.ToModify_time(""),
																								 message.ToModify_time1(""),
																								 vote.ToId("vote_"),
																								 post.ToId("post_"), 
																								 3,
																								 post.ToImage("post_"), 
																								 user.ToName("user_"),
																								 user.ToAvatar("user_")));
					}else{
						vote = new VoteBean();
						post = message.getPost();
						user = message.getUser_a();
						
						json.append(String.format("{%s,%s,%s,%s,%s,%s,\"type\":%d,%s,%s,%s},",   message.ToId(""),
																								 message.ToMessage(""),
																								 message.ToModify_time(""),
																								 message.ToModify_time1(""),
																								 vote.ToId("vote_"),
																								 post.ToId("post_"), 
																								 post.getTags().get(0).getT1(),
																								 post.ToImage("post_"), 
																								 user.ToName("user_"),
																								 user.ToAvatar("user_")));
					}
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