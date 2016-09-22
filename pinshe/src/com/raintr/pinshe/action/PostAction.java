package com.raintr.pinshe.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.FavoriteBean;
import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.bean.WishBean;
import com.raintr.pinshe.service.FavoriteService;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.WishService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class PostAction extends BaseAction {
	@Autowired
	private PostService postService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private WishService wishService;
	
	@RequestMapping(value = "/post")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		WishBean wish = null;
		FavoriteBean favorite = null;
		
		String postId = request.getParameter("pid");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		String userId = request.getParameter("uid");
		String page = request.getParameter("page");
		String productId = request.getParameter("productid");
		
		if(postId != null && postId.length() > 0){
			if(!StringGlobal.IsNull(userId)){
				favorite = favoriteService.ByUserIdPostId(Integer.parseInt(userId), Integer.parseInt(postId));
				wish = wishService.ByUserIdPostId(Integer.parseInt(userId), Integer.parseInt(postId));
			}
			
			if(wish == null)
				wish = new WishBean();
			if(favorite == null)
				favorite = new FavoriteBean();

			PostBean post = postService.ById(Integer.parseInt(postId));
			if(post != null){
				// add view + 1
				post.setView(post.getView() + 1);
				postService.Modify(post);
				
				StringBuffer json = new StringBuffer();
				List<TagBean> tags = post.getTags();
				UserBean user = post.getUser();
				List<ImageBean> images = post.getImages();
				
				if(tags == null || tags.size() == 0){
					tags = new ArrayList<TagBean>();
					tags.add(new TagBean());
				}
				
				// t1
				json.append(String.format("%s,", tags.get(0).ToT1Id("tag_")));
				json.append(String.format("%s,", tags.get(0).ToT1("tag_")));
				
				json.append("\"tag_t2\":[");
				if(tags.get(0).getGroup() != null)
					json.append(String.format("\"%s\"", tags.get(0).getGroup().getName()));
				for(int i = 1; i < tags.size(); i++){
					if(tags.get(i).getGroup() != null)
						json.append(String.format(",\"%s\"", tags.get(i).getGroup().getName()));
				}
				json.append("],");
				
				json.append(String.format("%s,%s,%s,", user.ToId("user_"), user.ToName("user_"), user.ToAvatar("user_")));
				
				json.append(String.format("%s,%s,", wish.ToId("wish_"), favorite.ToId("favorite_")));
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	post.ToId("post_"), 
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
																					"\"product_guid\":" + post.getProduct_id()));
					
				json.append("\"post_images\":[");
				if(images != null && images.size() > 0){
					json.append(String.format("\"%s\"", images.get(0).getUrl()));
					for(int i = 1; i < images.size(); i++){
						json.append(String.format(",\"%s\"", images.get(i).getUrl()));
					}
				}
				json.append("]");
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(t1 != null && t1.length() > 0){
			PostBean post;
			UserBean user;
			CommentBean comment;
			List<PostBean> posts;
			
			if(t2 != null && t2.length() > 0)
				posts = postService.ByTagT1T2(Integer.parseInt(t1), Integer.parseInt(t2), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			else
				posts = postService.ByTagT1(Integer.parseInt(t1), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			
			if(posts != null && posts.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < posts.size(); i++){
					post = posts.get(i);
					user = post.getUser();
					comment = post.getLastComment();
					
					wish = new WishBean();
					favorite = new FavoriteBean();
					
					if(user == null)
						user = new UserBean();
					
					if(comment == null){
						comment = new CommentBean();
						comment.setUser_a(new UserBean());
					}
					
					if(!StringGlobal.IsNull(userId)){
						favorite = favoriteService.ByUserIdPostId(Integer.parseInt(userId), post.getId());
						wish = wishService.ByUserIdPostId(Integer.parseInt(userId), post.getId());
						
						if(wish == null)
							wish = new WishBean();
						if(favorite == null)
							favorite = new FavoriteBean();
					}
					
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	user.ToName("user_"), 
																										user.ToAvatar("user_"),
																										post.ToId("post_"), 
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
																										comment.ToModify_time("reply_"),
																										wish.ToId("wish_"),
																										favorite.ToId("favorite_")));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
	
		if(productId != null && productId.length() > 0){
			PostBean post;
			UserBean user;
			CommentBean comment;
			List<PostBean> posts = postService.ByProductId(Integer.parseInt(productId));
			
			if(posts != null && posts.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < posts.size(); i++){
					post = posts.get(i);
					user = post.getUser();
					comment = post.getLastComment();
					
					wish = new WishBean();
					favorite = new FavoriteBean();
					
					if(user == null)
						user = new UserBean();
					
					if(comment == null){
						comment = new CommentBean();
						comment.setUser_a(new UserBean());
					}
					
					if(!StringGlobal.IsNull(userId)){
						favorite = favoriteService.ByUserIdPostId(Integer.parseInt(userId), post.getId());
						wish = wishService.ByUserIdPostId(Integer.parseInt(userId), post.getId());
						
						if(wish == null)
							wish = new WishBean();
						if(favorite == null)
							favorite = new FavoriteBean();
					}
					
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	user.ToName("user_"), 
																										user.ToAvatar("user_"),
																										post.ToId("post_"), 
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
																										comment.ToModify_time("reply_"),
																										wish.ToId("wish_"),
																										favorite.ToId("favorite_")));
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