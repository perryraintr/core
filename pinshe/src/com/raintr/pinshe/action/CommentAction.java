package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.service.CommentService;

@Controller
@RequestMapping(value = "/")
public class CommentAction extends BaseAction {
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value = "/comment")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		UserBean user;
		UserBean user_a;
		UserBean user_b;
		PostBean post;
		CommentBean comment;
		
		String postId = request.getParameter("pid");
		String voteId = request.getParameter("vid");
		String t1 = request.getParameter("t1");
		String page = request.getParameter("page");
		
		if(postId != null && postId.length() > 0){
			List<CommentBean> comments = commentService.ByPostId(Integer.parseInt(postId), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(comments != null && comments.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < comments.size(); i++){
					comment = comments.get(i);
					user_a = comment.getUser_a();
					user_b = comment.getUser_b();
					
					json.append(String.format("{%s,%s,%s,%s,%s},", 	comment.ToId(""), 
																	user_a.ToId("user_"), 
																	user_a.ToName("user_"), 
																	comment.ToMessage(""), 
																	user_b.ToName("reply_")));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}

		if(voteId != null && voteId.length() > 0){
			List<CommentBean> comments = commentService.ByVoteId(Integer.parseInt(voteId), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(comments != null && comments.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < comments.size(); i++){
					comment = comments.get(i);
					user_a = comment.getUser_a();
					user_b = comment.getUser_b();
					
					json.append(String.format("{%s,%s,%s,%s,%s},", 	comment.ToId(""), 
																	user_a.ToId("user_"), 
																	user_a.ToName("user_"), 
																	comment.ToMessage(""), 
																	user_b.ToName("reply_")));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(t1 != null && t1.length() > 0){
			List<CommentBean> comments = commentService.ByModifyTime(Integer.parseInt(t1), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(comments != null && comments.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < comments.size(); i++){
					comment = comments.get(i);
					if(comment != null){
						post = comment.getPost();
						user = post.getUser();
						
						json.append(String.format("{%s,%s,", user.ToName("user_"), user.ToAvatar("user_")));
						
						json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	post.ToId("post_"), 
																						post.ToBrand("post_"), 
																						post.ToImage("post_"), 
																						post.ToName("post_"), 
																						post.ToPrice("post_"), 
																						post.ToAddress("post_"),
																						post.ToView("post_"),
																						post.ToComment("post_"),
																						post.ToFavorite("post_"),
																						post.ToDescription("post_"),
																						post.ToModify_time("post_")));
						
						json.append(String.format("%s},", 	comment.ToModify_time("reply_")));
					}
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