package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.service.VoteService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_VoteAction extends BaseAction {
	@Autowired
	private VoteService voteService;
	
	@RequestMapping(value = "/admin_vote")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		VoteBean vote;
		PostBean post_a;
		PostBean post_b;
		ProductBean product_a;
		ProductBean product_b;
		
		String id = request.getParameter("id");	
		String page = request.getParameter("page");
		
		if(!StringGlobal.IsNull(page)){
			List<VoteBean> votes = voteService.By(1, page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(votes != null && votes.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < votes.size(); i++){
					vote = votes.get(i);
					if(vote.getPost_id_a() > 0){
						post_a = vote.getPost_a();
						post_b = vote.getPost_b();
					}else{
						post_a = new PostBean();
						post_b = new PostBean();
					}
					
					if(vote.getProduct_id_a() > 0){
						product_a = vote.getProduct_a();
						product_b = vote.getProduct_b();
					}else{
						product_a = new ProductBean();
						product_b = new ProductBean();
					}
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	vote.ToId("vote_"),
																											vote.ToUser_id("vote_"),
																											vote.ToName("vote_"),
																											vote.ToCount_a("vote_"),
																											vote.ToCount_b("vote_"),
																											vote.ToView("vote_"),
																											vote.ToComment("vote_"),
																											vote.ToDescription("vote_"),
																											"\"vote_create_time\":\"" + vote.getCreate_time() + "\"",
																											"\"vote_modify_time\":\"" + vote.getModify_time() + "\"",
																											post_a.ToId("posta_"),
																											post_a.ToImage("posta_"),
																											post_b.ToId("postb_"),
																											post_b.ToImage("postb_"),
																											product_a.ToId("producta_"), 
																											product_a.ToImage("producta_"),
																											product_b.ToId("productb_"), 
																											product_b.ToImage("productb_")));	
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		if(!StringGlobal.IsNull(id)){
			vote = voteService.ById(Integer.parseInt(id));
			if(vote != null){
				StringBuffer json = new StringBuffer();

				if(vote.getPost_id_a() > 0){
					post_a = vote.getPost_a();
					post_b = vote.getPost_b();
				}else{
					post_a = new PostBean();
					post_b = new PostBean();
				}
				
				if(vote.getProduct_id_a() > 0){
					product_a = vote.getProduct_a();
					product_b = vote.getProduct_b();
				}else{
					product_a = new ProductBean();
					product_b = new ProductBean();
				}
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	vote.ToId("vote_"),
																									vote.ToUser_id("vote_"),
																									vote.ToName("vote_"),
																									vote.ToCount_a("vote_"),
																									vote.ToCount_b("vote_"),
																									vote.ToView("vote_"),
																									vote.ToComment("vote_"),
																									vote.ToDescription("vote_"),
																									"\"vote_create_time\":\"" + vote.getCreate_time() + "\"",
																									"\"vote_modify_time\":\"" + vote.getModify_time() + "\"",
																									post_a.ToId("posta_"),
																									post_a.ToImage("posta_"),
																									post_b.ToId("postb_"),
																									post_b.ToImage("postb_"),
																									product_a.ToId("producta_"), 
																									product_a.ToImage("producta_"),
																									product_b.ToId("productb_"), 
																									product_b.ToImage("productb_")));	
			
				response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}