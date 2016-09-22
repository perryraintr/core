package com.raintr.pinshe.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.VoteService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_Vote_AddAction extends BaseAction {
	@Autowired
	private PostService postService;
	@Autowired
	private VoteService voteService;
	
	@RequestMapping(value = "/admin_vote_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String userId = request.getParameter("uid");
		String postIdA = request.getParameter("postida");
		String postIdB = request.getParameter("postidb");
		String productIdA = request.getParameter("productida");
		String productIdB = request.getParameter("productidb");
		String name = request.getParameter("name");

		name = StringGlobal.SerializeJson(name);
		
		if(!StringGlobal.IsNull(userId) && !"0".equals(userId)){
			List<MultipartFile> files = null;
			try{
				files = ((MultipartHttpServletRequest)request).getFiles("file");
			}catch(Exception ex){}
			
			if(files == null){
				response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"file is null\"}}");
				return null;
			}
			
			if(files.size() < 2){
				response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"file's just one, you can give one more\"}}");
				return null;
			}
			
			// posta
			PostBean posta = new PostBean();
			posta.setUser_id(Integer.parseInt(userId));
			posta.setName("");
			posta.setView(0);
			posta.setComment(0);
			posta.setFavorite(0);
			posta.setPrice(0);
			posta.setAddress("");
			posta.setBrand("");
			posta.setDescription("");
			posta.setCreate_time(new Date());
			posta.setModify_time(new Date());
			posta.setId(postService.Add(posta, files.get(0)));
			// postb
			PostBean postb = new PostBean();
			postb.setUser_id(Integer.parseInt(userId));
			postb.setName("");
			postb.setView(0);
			postb.setComment(0);
			postb.setFavorite(0);
			postb.setPrice(0);
			postb.setAddress("");
			postb.setBrand("");
			postb.setDescription("");
			postb.setCreate_time(new Date());
			postb.setModify_time(new Date());
			postb.setId(postService.Add(postb, files.get(1)));
			// vote
			VoteBean vote = new VoteBean();
			vote.setUser_id(Integer.parseInt(userId));
			if(!StringGlobal.IsNull(name))
				vote.setName(name);
			vote.setPost_id_a(posta.getId());
			vote.setPost_id_b(postb.getId());
			vote.setCount_a(0);
			vote.setCount_b(0);
			vote.setView(0);
			vote.setComment(0);
			vote.setDescription("");
			vote.setCreate_time(new Date());
			vote.setModify_time(new Date());
			vote.setId(voteService.Add(vote));
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done. id=%d\"}}", vote.getId()));
			return null;
		}else{
			if(!StringGlobal.IsNull(postIdA) && !StringGlobal.IsNull(postIdB) && !StringGlobal.IsNull(productIdA) && !StringGlobal.IsNull(productIdB)){
				// vote
				VoteBean vote = new VoteBean();
				vote.setUser_id(0);
				if(!StringGlobal.IsNull(name))
					vote.setName(name);
				vote.setPost_id_a(Integer.parseInt(postIdA));
				vote.setPost_id_b(Integer.parseInt(postIdB));
				vote.setProduct_id_a(Integer.parseInt(productIdA));
				vote.setProduct_id_b(Integer.parseInt(productIdB));
				vote.setCount_a(0);
				vote.setCount_b(0);
				vote.setView(0);
				vote.setComment(0);
				vote.setDescription("");
				vote.setCreate_time(new Date());
				vote.setModify_time(new Date());
				vote.setId(voteService.Add(vote));
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done. id=%d\"}}", vote.getId()));
				return null;
			}
		}
	
		response.getWriter().print("{\"head\":0,\"body\":{}}");
		return null;
	}
}