package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.service.FavoriteService;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.ProductService;
import com.raintr.pinshe.service.VoteService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class RemoveFavoriteAction extends BaseAction {
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private PostService postService;
	@Autowired
	private ProductService productService;
	@Autowired
	private VoteService voteService;
	
	@RequestMapping(value = "/removefavorite")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String userId = request.getParameter("uid");
		String voteId = request.getParameter("vid");
		String postId = request.getParameter("pid");
		String productId = request.getParameter("prodid");
		
		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(postId) && StringGlobal.IsNull(voteId) && StringGlobal.IsNull(productId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "pid vid and prodid is null"));
			return null;
		}
		
		if(!StringGlobal.IsNull(postId)){
			favoriteService.RemoveUserIdPostId(Integer.parseInt(userId), Integer.parseInt(postId));
			
			PostBean post = postService.ById(Integer.parseInt(postId));
			if(post != null && post.getFavorite() > 0){
				post.setFavorite(post.getFavorite() - 1);
				postService.Modify(post);
				
				if(post.getProduct_id() > 0){
					ProductBean product = productService.ById(post.getProduct_id());
					if(product != null){
						product.setFavorite(product.getFavorite() - 1);
						productService.Modify(product, null);
					}
				}
			}
		}
		
		if(!StringGlobal.IsNull(voteId)){
			favoriteService.RemoveUserIdVoteId(Integer.parseInt(userId), Integer.parseInt(voteId));
			VoteBean vote = voteService.ById(Integer.parseInt(voteId));
			if(vote != null && vote.getFavorite() > 0){
				vote.setFavorite(vote.getFavorite() - 1);
				voteService.Modify(vote);
			}
		}
		
		if(!StringGlobal.IsNull(productId)){
			favoriteService.RemoveUserIdProductId(Integer.parseInt(userId), Integer.parseInt(productId));
			ProductBean product = productService.ById(Integer.parseInt(productId));
			if(product != null){
				product.setFavorite(product.getFavorite() - 1);
				productService.Modify(product, null);
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}