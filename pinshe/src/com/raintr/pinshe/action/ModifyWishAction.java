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
public class ModifyWishAction extends BaseAction {
	@Autowired
	private WishService wishService;
	
	@RequestMapping(value = "/modifywish")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String id = request.getParameter("id");
		String userId = request.getParameter("uid");
		String voteId = request.getParameter("vid");
		String postId = request.getParameter("pid");

		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		WishBean wish = wishService.ById(Integer.parseInt(id));
		if(wish != null){
			if(!StringGlobal.IsNull(userId))
				wish.setUser_id(Integer.parseInt(userId));
			if(!StringGlobal.IsNull(voteId))
				wish.setVote_id(Integer.parseInt(voteId));
			if(!StringGlobal.IsNull(postId))
				wish.setPost_id(Integer.parseInt(postId));
			wish.setModify_time(new Date());
			wishService.Modify(wish);
	
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", wish.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}