package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.FavoriteBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.bean.WishBean;
import com.raintr.pinshe.service.FavoriteService;
import com.raintr.pinshe.service.WishService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class WishAction extends BaseAction {
	@Autowired
	private WishService wishService;
	@Autowired
	private FavoriteService favoriteService;
	
	@RequestMapping(value = "/wish")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		WishBean wish;
		UserBean user;
		VoteBean vote;
		PostBean post;
		UserBean owner;
		UserBean user_a;
		UserBean user_b;
		PostBean post_a;
		PostBean post_b;
		ProductBean product_a;
		ProductBean product_b;
		CommentBean comment;
		FavoriteBean favorite;
		
		String userId = request.getParameter("uid");
		String page = request.getParameter("page");
		
		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "userId is null"));
			return null;
		}
		
		List<WishBean> wishs = wishService.ByUserId(Integer.parseInt(userId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
		if(wishs != null){
			StringBuffer json = new StringBuffer();
			for(int i = 0; i < wishs.size(); i++){
				wish = wishs.get(i);
				if(wish.getPost_id() > 0){
					post = wish.getPost();
					user = post.getUser();
					
					favorite = new FavoriteBean();
					
					if(!StringGlobal.IsNull(userId)){
						favorite = favoriteService.ByUserIdPostId(Integer.parseInt(userId), post.getId());
						if(favorite == null)
							favorite = new FavoriteBean();
					}
					
					json.append(String.format("{%s,%s,\"type\":%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	wish.ToId("wish_"),
																												post.ToId("post_"), 
																												post.getTags().get(0).getT1(),
																												post.ToBrand("post_"), 
																												post.ToImage("post_"), 
																												post.ToName("post_"), 
																												post.ToPrice("post_"), 
																												post.ToAddress("post_"),
																												post.ToView("post_"),
																												post.ToComment("post_"),
																												post.ToFavorite("post_"),
																												post.ToDescription("post_"),
																												post.ToModify_time("post_"),
																												user.ToName("user_"),
																												user.ToAvatar("user_"),
																												favorite.ToId("favorite_")));
				}else{
					vote = wish.getVote();
					owner = vote.getUser();
					
					if(vote.getPost_id_a() > 0){
						post_a = vote.getPost_a();
						user_a = post_a.getUser();
						post_b = vote.getPost_b();
						user_b = post_b.getUser();
					}else{
						post_a = new PostBean();
						user_a = new UserBean();
						post_b = new PostBean();
						user_b = new UserBean();
					}
					
					if(vote.getProduct_id_a() > 0){
						product_a = vote.getProduct_a();
						product_b = vote.getProduct_b();
					}else{
						product_a = new ProductBean();
						product_b = new ProductBean();
					}
					
					comment = vote.getLastComment();
					if(comment == null)
						user = new UserBean();
					else
						user = comment.getUser_a();
					
					favorite = new FavoriteBean();
					
					if(!StringGlobal.IsNull(userId)){
						favorite = favoriteService.ByUserIdVoteId(Integer.parseInt(userId), vote.getId());
						if(favorite == null)
							favorite = new FavoriteBean();
					}
					
					json.append(String.format("{%s,\"type\":%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	wish.ToId("wish_"),
																												3,
																												vote.ToId("vote_"),
																												vote.ToUser_id("vote_"),
																												owner.ToName("vote_user_"),
																												owner.ToAvatar("vote_user_"),
																												vote.ToName("vote_"), 
																												vote.ToCount_a("vote_"), 
																												vote.ToCount_b("vote_"),
																												vote.ToView("vote_"),
																												vote.ToComment("vote_"),
																												vote.ToFavorite("vote_"),
																												vote.ToDescription("vote_"),
																												vote.ToModify_time("vote_"),
																												user.ToName("reply_"),
																												user.ToModify_time("reply_"),
																												favorite.ToId("favorite_")));

					json.append(String.format("%s,%s,", user_a.ToName("usera_"), user_a.ToAvatar("usera_")));
					json.append(String.format("%s,%s,", user_b.ToName("userb_"), user_b.ToAvatar("userb_")));
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	post_a.ToId("posta_"),
																					post_a.ToName("posta_"),
																					post_a.ToView("posta_"),
																					post_a.ToComment("posta_"),
																					post_a.ToFavorite("posta_"),
																					post_a.ToImage("posta_"),
																					post_a.ToPrice("posta_"),
																					post_a.ToAddress("posta_"),
																					post_a.ToBrand("posta_"),
																					post_a.ToDescription("posta_")));
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	post_b.ToId("postb_"),
																					post_b.ToName("postb_"),
																					post_b.ToView("postb_"),
																					post_b.ToComment("postb_"),
																					post_b.ToFavorite("postb_"),
																					post_b.ToImage("postb_"),
																					post_b.ToPrice("postb_"),
																					post_b.ToAddress("postb_"),
																					post_b.ToBrand("postb_"),
																					post_b.ToDescription("postb_")));
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,", product_a.ToId("product_a_"), 
																			 product_a.ToName("product_a_"), 
																			 product_a.ToFavorite("product_a_"),
																			 product_a.ToImage("product_a_"), 
																			 product_a.ToPrice("product_a_"), 
																			 product_a.ToAddress("product_a_"),
																			 product_a.ToBrand("product_a_"),
																			 product_a.ToDescription("product_a_"),
																			 product_a.ToModify_time("product_a_")));

					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s},", product_b.ToId("product_b_"), 
																			  product_b.ToName("product_b_"), 
																			  product_b.ToFavorite("product_b_"),
																			  product_b.ToImage("product_b_"), 
																			  product_b.ToPrice("product_b_"), 
																			  product_b.ToAddress("product_b_"),
																			  product_b.ToBrand("product_b_"),
																			  product_b.ToDescription("product_b_"),
																			  product_b.ToModify_time("product_b_")));
					
					
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
}