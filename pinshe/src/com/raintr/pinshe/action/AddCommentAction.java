package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.MessageBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.service.CommentService;
import com.raintr.pinshe.service.MessageService;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.UserService;
import com.raintr.pinshe.service.VoteService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class AddCommentAction extends BaseAction {
	@Autowired
	private CommentService commentService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private VoteService voteService;
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/addcomment")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		UserBean user_a;
		UserBean user_b;
		
		String voteId = request.getParameter("vid");
		String postId = request.getParameter("pid");
		String userId = request.getParameter("uid");
		String userIdA = request.getParameter("uaid");
		String userIdB = request.getParameter("ubid");
		String message = request.getParameter("m");
		
		if(StringGlobal.IsNull(voteId) && StringGlobal.IsNull(postId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "vid pid both are null"));
			return null;
		}
		
		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(userIdA)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uaid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(userIdB)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "ubid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(message)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "m is null"));
			return null;
		}
		
		message = new String(message.getBytes("ISO-8859-1"), "utf-8");
		
		message = StringGlobal.SerializeJson(message);
		
		// comment
		CommentBean comment = new CommentBean();
		if(voteId != null && voteId.length() > 0)
			comment.setVote_id(Integer.parseInt(voteId));
		if(postId != null && postId.length() > 0)
			comment.setPost_id(Integer.parseInt(postId));
		comment.setUser_id_a(Integer.parseInt(userIdA));
		comment.setUser_id_b(Integer.parseInt(userIdB));
		comment.setMessage(message);
		comment.setCreate_time(new Date());
		comment.setModify_time(new Date());
		commentService.Add(comment);
		// message
		MessageBean instant = new MessageBean();
		if(voteId != null && voteId.length() > 0)
			instant.setVote_id(Integer.parseInt(voteId));
		if(postId != null && postId.length() > 0)
			instant.setPost_id(Integer.parseInt(postId));
		instant.setUser_id_a(Integer.parseInt(userIdA));
		instant.setUser_id_b(Integer.parseInt(userIdB));
		instant.setMessage(message);
		instant.setCreate_time(new Date());
		instant.setModify_time(new Date());
		messageService.Add(instant);
		// owner
		if(!userIdB.equals(userId)){
			instant = new MessageBean();
			if(voteId != null && voteId.length() > 0)
				instant.setVote_id(Integer.parseInt(voteId));
			if(postId != null && postId.length() > 0)
				instant.setPost_id(Integer.parseInt(postId));
			instant.setUser_id_a(Integer.parseInt(userIdA));
			instant.setUser_id_b(Integer.parseInt(userId));
			instant.setMessage(message);
			instant.setCreate_time(new Date());
			instant.setModify_time(new Date());
			messageService.Add(instant);
		}
		// vote comment
		if(voteId != null && voteId.length() > 0){
			VoteBean vote = voteService.ById(Integer.parseInt(voteId));
			vote.setComment(vote.getComment() + 1);
			voteService.Modify(vote);
		}
		// post comment
		if(postId != null && postId.length() > 0){
			PostBean post = postService.ById(Integer.parseInt(postId));
			post.setComment(post.getComment() + 1);
			postService.Modify(post);
		}
		
		comment.setUser_a(userService.ById(comment.getUser_id_a()));
		comment.setUser_b(userService.ById(comment.getUser_id_b()));
		
		user_a = comment.getUser_a();
		user_b = comment.getUser_b();
		StringBuffer json = new StringBuffer();
		json.append(String.format("%s,%s,%s,%s,%s", comment.ToId(""), 
													user_a.ToId("user_"), 
													user_a.ToName("user_"), 
													comment.ToMessage(""), 
													user_b.ToName("reply_")));
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
		return null;
	}
}