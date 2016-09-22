package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.WishBean;
import com.raintr.pinshe.service.WishService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class AddWishAction extends BaseAction {
	@Autowired
	private WishService wishService;
	
	@RequestMapping(value = "/addwish")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String userId = request.getParameter("uid");
		String voteId = request.getParameter("vid");
		String postId = request.getParameter("pid");

		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(postId) && StringGlobal.IsNull(voteId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "pid and vid is null"));
			return null;
		}
		
		WishBean wish = null;
		
		if(!StringGlobal.IsNull(postId)){
			wish = wishService.ByUserIdPostId(Integer.parseInt(userId), Integer.parseInt(postId));
			if(wish == null){
				wish = new WishBean();
				wish.setUser_id(Integer.parseInt(userId));
				wish.setVote_id(0);
				wish.setPost_id(Integer.parseInt(postId));
				wish.setCreate_time(new Date());
				wish.setModify_time(new Date());
				wish.setId(wishService.Add(wish));
			}
		}

		if(!StringGlobal.IsNull(voteId)){
			wish = wishService.ByUserIdVoteId(Integer.parseInt(userId), Integer.parseInt(voteId));
			if(wish == null){
				wish = new WishBean();
				wish.setUser_id(Integer.parseInt(userId));
				wish.setVote_id(Integer.parseInt(voteId));
				wish.setPost_id(0);
				wish.setCreate_time(new Date());
				wish.setModify_time(new Date());
				wish.setId(wishService.Add(wish));
			}
		}
		
		if(wish != null){
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", wish.ToId("")));
			return null;
		}
		
		response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "wish is null"));
		return null;
	}
}