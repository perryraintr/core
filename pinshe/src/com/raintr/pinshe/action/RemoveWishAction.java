package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.service.WishService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class RemoveWishAction extends BaseAction {
	@Autowired
	private WishService wishService;
	
	@RequestMapping(value = "/removewish")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String wishId = request.getParameter("wid");
		String userId = request.getParameter("uid");
		String voteId = request.getParameter("vid");
		String postId = request.getParameter("pid");

		if(!StringGlobal.IsNull(wishId)){
			wishService.Remove(Integer.parseInt(wishId));
			response.getWriter().print(String.format("{\"head\":1,\"body\":{}}"));
			return null;
		}
		
		if(!StringGlobal.IsNull(userId) && !StringGlobal.IsNull(postId)){
			wishService.RemoveByUserIdPostId(Integer.parseInt(userId), Integer.parseInt(postId));
			response.getWriter().print(String.format("{\"head\":1,\"body\":{}}"));
			return null;
		}

		if(!StringGlobal.IsNull(userId) && !StringGlobal.IsNull(voteId)){
			wishService.RemoveByUserIdVoteId(Integer.parseInt(userId), Integer.parseInt(voteId));
			response.getWriter().print(String.format("{\"head\":1,\"body\":{}}"));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}