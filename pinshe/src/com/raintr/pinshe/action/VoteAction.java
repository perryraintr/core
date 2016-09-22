package com.raintr.pinshe.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.FavoriteBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.bean.WishBean;
import com.raintr.pinshe.service.FavoriteService;
import com.raintr.pinshe.service.VoteService;
import com.raintr.pinshe.service.WishService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class VoteAction extends BaseAction {
	@Autowired
	private VoteService voteService;
	@Autowired
	private FavoriteService favoriteService;
	@Autowired
	private WishService wishService;
	
	@RequestMapping(value = "/vote")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		VoteBean vote;
		UserBean user;
		UserBean user_a;
		UserBean user_b;
		PostBean post_a;
		PostBean post_b;
		ProductBean product_a;
		ProductBean product_b;
		WishBean wish = null;
		FavoriteBean favorite = null;
		Map<Integer, Integer> voteIds = new HashMap<Integer, Integer>();
		List<VoteBean> all = new ArrayList<VoteBean>();
		List<VoteBean> votes = new ArrayList<VoteBean>();
		
		String voteId = request.getParameter("vid");
		String userId = request.getParameter("uid");
		String page = request.getParameter("page");
		String vids = request.getParameter("vids");
		
		if(!StringGlobal.IsNull(voteId)){
			if(!StringGlobal.IsNull(userId)){
				favorite = favoriteService.ByUserIdVoteId(Integer.parseInt(userId), Integer.parseInt(voteId));
				wish = wishService.ByUserIdVoteId(Integer.parseInt(userId), Integer.parseInt(voteId));
			}
			
			if(wish == null)
				wish = new WishBean();
			if(favorite == null)
				favorite = new FavoriteBean();
			
			
			vote = voteService.ById(Integer.parseInt(voteId));
			if(vote != null){
				// add view + 1
				vote.setView(vote.getView() + 1);
				voteService.Modify(vote);
				
				StringBuffer json = new StringBuffer();
				user = vote.getUser();
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
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	vote.ToId("vote_"),
																				vote.ToUser_id("vote_"),
																				user.ToName("vote_user_"),
																				user.ToAvatar("vote_user_"),
																				vote.ToName("vote_"),
																				vote.ToCount_a("vote_"),
																				vote.ToCount_b("vote_"),
																				vote.ToView("vote_"),
																				vote.ToComment("vote_"),
																				vote.ToFavorite("vote_"),
																				vote.ToModify_time("vote_")));

				json.append(String.format("%s,%s,", wish.ToId("wish_"), favorite.ToId("favorite_")));
				
				json.append(String.format("%s,%s,", user_a.ToName("usera_"), user_a.ToAvatar("usera_")));
				json.append(String.format("%s,%s,", user_b.ToName("userb_"), user_b.ToAvatar("userb_")));
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"producta_guid\":%d,", post_a.ToId("posta_"),
																								 post_a.ToName("posta_"),
																								 post_a.ToView("posta_"),
																								 post_a.ToComment("posta_"),
																								 post_a.ToFavorite("posta_"),
																								 post_a.ToImage("posta_"),
																								 post_a.ToPrice("posta_"),
																								 post_a.ToAddress("posta_"),
																								 post_a.ToBrand("posta_"),
																								 post_a.ToDescription("posta_"),
																								 post_a.getProduct_id()));

				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"productb_guid\":%d,", post_b.ToId("postb_"),
																								 post_b.ToName("postb_"),
																								 post_b.ToView("postb_"),
																								 post_b.ToComment("postb_"),
																								 post_b.ToFavorite("postb_"),
																								 post_b.ToImage("postb_"),
																								 post_b.ToPrice("postb_"),
																								 post_b.ToAddress("postb_"),
																								 post_b.ToBrand("postb_"),
																								 post_b.ToDescription("postb_"),
																								 post_b.getProduct_id()));
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,", product_a.ToId("product_a_"), 
																		 product_a.ToName("product_a_"), 
																		 product_a.ToFavorite("product_a_"),
																		 product_a.ToImage("product_a_"), 
																		 product_a.ToPrice("product_a_"), 
																		 product_a.ToAddress("product_a_"),
																		 product_a.ToBrand("product_a_"),
																		 product_a.ToDescription("product_a_"),
																		 product_a.ToModify_time("product_a_")));
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", product_b.ToId("product_b_"), 
																		product_b.ToName("product_b_"), 
																		product_b.ToFavorite("product_b_"),
																		product_b.ToImage("product_b_"), 
																		product_b.ToPrice("product_b_"), 
																		product_b.ToAddress("product_b_"),
																		product_b.ToBrand("product_b_"),
																		product_b.ToDescription("product_b_"),
																		product_b.ToModify_time("product_b_")));
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		
		if(!StringGlobal.IsNull(userId)){
			if(!StringGlobal.IsNull(vids)){
				String[] rows = vids.split(",");
				for(int i = 0; i < rows.length; i++)
					voteIds.put(Integer.parseInt(rows[i]), 1);
				
				for(int i = 0; i < 20; i++){
					votes = voteService.By(Integer.parseInt(userId), i * 10);
					
					if(votes == null)
						break;
					
					all.clear();
					for(int j = 0; j < votes.size(); j++){
						all.add(votes.get(j).Clone());
					}

					votes = all;
					
					for(int j = votes.size() - 1; j >=0; j--){
						if(voteIds.containsKey(votes.get(j).getId()))
							votes.remove(j);
					}
					
					if(votes != null && votes.size() > 0)
						break;
				}
			}else{
				votes = voteService.By(Integer.parseInt(userId), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			}
			
			StringBuffer json = new StringBuffer();
			
			if(votes != null){
				for(int i = 0; i < votes.size(); i++){
					vote = votes.get(i);
					
					user = vote.getUser();
					
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
					
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	vote.ToId("vote_"),
																				vote.ToUser_id("vote_"),
																				user.ToName("vote_user_"),
																				user.ToAvatar("vote_user_"),
																				vote.ToName("vote_"),
																				vote.ToCount_a("vote_"),
																				vote.ToCount_b("vote_"),
																				vote.ToView("vote_"),
																				vote.ToComment("vote_")));
					
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
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", product_b.ToId("product_b_"), 
																			product_b.ToName("product_b_"), 
																			product_b.ToFavorite("product_b_"),
																			product_b.ToImage("product_b_"), 
																			product_b.ToPrice("product_b_"), 
																			product_b.ToAddress("product_b_"),
																			product_b.ToBrand("product_b_"),
																			product_b.ToDescription("product_b_"),
																			product_b.ToModify_time("product_b_")));
					
					json.append("},");
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