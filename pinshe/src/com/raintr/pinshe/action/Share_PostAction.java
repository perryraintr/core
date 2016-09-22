package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Share_PostAction extends BaseAction {
	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "/share_post")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String postId = request.getParameter("pid");

		PostBean post = null;
		
		if(!StringGlobal.IsNull(postId))
			post = postService.ById(Integer.parseInt(postId));

		if(post != null && post.getTags().get(0).getT1() == 1)
			return "redirect:http://www.pinshe.org/html/v1/list_good_detail_share.html?pid=" + postId;
		else
			return "redirect:http://www.pinshe.org/html/v1/list_bad_detail_share.html?pid=" + postId;
	}
}