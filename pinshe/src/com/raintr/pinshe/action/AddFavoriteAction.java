package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.FavoriteBean;
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
public class AddFavoriteAction extends BaseAction {
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private PostService postService;
	@Autowired
	private ProductService productService;
	@Autowired
	private VoteService voteService;
	
	@RequestMapping(value = "/addfavorite")
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
		
		FavoriteBean favorite = null;
		
		if(!StringGlobal.IsNull(postId)){
			favorite = favoriteService.ByUserIdPostId(Integer.parseInt(userId), Integer.parseInt(postId));
			if(favorite == null){
				favorite = new FavoriteBean();
				favorite.setUser_id(Integer.parseInt(userId));
				favorite.setVote_id(0);
				favorite.setPost_id(Integer.parseInt(postId));
				favorite.setProduct_id(0);
				favorite.setCreate_time(new Date());
				favorite.setModify_time(new Date());
				favorite.setId(favoriteService.Add(favorite));
				
				PostBean post = postService.ById(favorite.getPost_id());
				post.setFavorite(post.getFavorite() + 1);
				postService.Modify(post);
				
				if(post.getProduct_id() > 0){
					ProductBean product = productService.ById(post.getProduct_id());
					product.setFavorite(product.getFavorite() + 1);
					productService.Modify(product, null);
				}
			}
		}

		if(!StringGlobal.IsNull(voteId)){
			favorite = favoriteService.ByUserIdVoteId(Integer.parseInt(userId), Integer.parseInt(voteId));
			if(favorite == null){
				favorite = new FavoriteBean();
				favorite.setUser_id(Integer.parseInt(userId));
				favorite.setVote_id(Integer.parseInt(voteId));
				favorite.setPost_id(0);
				favorite.setProduct_id(0);
				favorite.setCreate_time(new Date());
				favorite.setModify_time(new Date());
				favorite.setId(favoriteService.Add(favorite));
				
				VoteBean vote = voteService.ById(favorite.getVote_id());
				vote.setFavorite(vote.getFavorite() + 1);
				voteService.Modify(vote);
			}
		}
		
		if(!StringGlobal.IsNull(productId)){
			favorite = favoriteService.ByUserIdProductId(Integer.parseInt(userId), Integer.parseInt(productId));
			if(favorite == null){
				favorite = new FavoriteBean();
				favorite.setUser_id(Integer.parseInt(userId));
				favorite.setVote_id(0);
				favorite.setPost_id(0);
				favorite.setProduct_id(Integer.parseInt(productId));
				favorite.setCreate_time(new Date());
				favorite.setModify_time(new Date());
				favorite.setId(favoriteService.Add(favorite));
				
				ProductBean product = productService.ById(favorite.getProduct_id());
				product.setFavorite(product.getFavorite() + 1);
				productService.Modify(product, null);
			}
		}
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", favorite.ToId("")));
		return null;
	}
}