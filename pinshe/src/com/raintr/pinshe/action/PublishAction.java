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
import com.raintr.pinshe.bean.PublishBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.bean.WishBean;
import com.raintr.pinshe.service.FavoriteService;
import com.raintr.pinshe.service.PublishService;
import com.raintr.pinshe.service.WishService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class PublishAction extends BaseAction {
	@Autowired
	private PublishService publishService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private WishService wishService;
	
	@RequestMapping(value = "/publish")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {		
		PublishBean publish;
		UserBean user;
		VoteBean vote;
		PostBean post;
		UserBean user_a;
		UserBean user_b;
		PostBean post_a;
		PostBean post_b;
		CommentBean comment;
		WishBean wish;
		FavoriteBean favorite;
		
		
		String userId = request.getParameter("uid");
		String page = request.getParameter("page");
		
		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uid is null"));
			return null;
		}
		
		StringBuffer json = new StringBuffer();
		List<PublishBean> publishs = publishService.ByUserId(Integer.parseInt(userId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
		if(publishs != null){
			for(int i = 0; i < publishs.size(); i++){
				publish = publishs.get(i);
				if(publish.getPost_id() > 0){
					post = publish.getPost();
					comment = post.getLastComment();
					if(comment == null)
						user = new UserBean();
					else
						user = comment.getUser_a();
					
					wish = new WishBean();
					favorite = new FavoriteBean();
					
					if(!StringGlobal.IsNull(userId)){
						favorite = favoriteService.ByUserIdPostId(Integer.parseInt(userId), post.getId());
						wish = wishService.ByUserIdPostId(Integer.parseInt(userId), post.getId());
						
						if(wish == null)
							wish = new WishBean();
						if(favorite == null)
							favorite = new FavoriteBean();
					}
					
					json.append("{");
					// t1
					List<TagBean> tags = post.getTags();
					json.append(String.format("%s,", tags.get(0).ToT1Id("tag_")));
					json.append("\"tag_t2\":[");
					json.append(String.format("%d", tags.get(0).getT2()));
					for(int j = 1; j < tags.size(); j++){
						json.append(String.format(",%d", tags.get(j).getT2()));
					}
					json.append("],");
					
					json.append(String.format("%s,\"type\":%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 		publish.ToId(""),
																													post.getTags().get(0).getT1(),
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
																													user.ToName("reply_"),
																													user.ToModify_time("reply_"),
																													wish.ToId("wish_"),
																													favorite.ToId("favorite_")));
				}else{
					vote = publish.getVote();
					post_a = vote.getPost_a();
					user_a = post_a.getUser();
					post_b = vote.getPost_b();
					user_b = post_b.getUser();
					
					comment = vote.getLastComment();
					if(comment == null)
						user = new UserBean();
					else
						user = comment.getUser_a();
					
					json.append(String.format("{%s,\"type\":%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	publish.ToId(""),
																									3,
																									vote.ToId("vote_"),
																									vote.ToUser_id("vote_"),
																									vote.ToName("vote_"), 
																									vote.ToCount_a("vote_"), 
																									vote.ToCount_b("vote_"),
																									vote.ToView("vote_"),
																									vote.ToComment("vote_"),
																									vote.ToDescription("vote_"),
																									vote.ToModify_time("vote_"),
																									user.ToName("reply_"),
																									user.ToModify_time("reply_")));

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
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	post_b.ToId("postb_"),
																					post_b.ToName("postb_"),
																					post_b.ToView("postb_"),
																					post_b.ToComment("postb_"),
																					post_b.ToFavorite("postb_"),
																					post_b.ToImage("postb_"),
																					post_b.ToPrice("postb_"),
																					post_b.ToAddress("postb_"),
																					post_b.ToBrand("postb_"),
																					post_b.ToDescription("postb_")));
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