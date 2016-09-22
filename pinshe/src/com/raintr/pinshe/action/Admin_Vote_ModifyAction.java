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
public class Admin_Vote_ModifyAction extends BaseAction {
	@Autowired
	private PostService postService;
	@Autowired
	private VoteService voteService;
	
	@RequestMapping(value = "/admin_vote_modify")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");

		if(StringGlobal.IsNull(id)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"id is null\"}}");
			return null;
		}
		
		String userId = request.getParameter("uid");
		String postIdA = request.getParameter("postida");
		String postIdB = request.getParameter("postidb");
		String productIdA = request.getParameter("productida");
		String productIdB = request.getParameter("productidb");
		String name = request.getParameter("name");
		String countA = request.getParameter("a");
		String countB = request.getParameter("b");
		String view = request.getParameter("view");
		String comment = request.getParameter("comment");
		String description = request.getParameter("description");
		
		VoteBean vote = voteService.ById(Integer.parseInt(id));
		
		name = StringGlobal.SerializeJson(name);
		description = StringGlobal.SerializeJson(description);
		
		if(!StringGlobal.IsNull(userId) && !"0".equals(userId)){
			MultipartFile postImageA = null;
			MultipartFile postImageB = null;
			List<MultipartFile> files = null;

			try{
				files = ((MultipartHttpServletRequest)request).getFiles("file");
			}catch(Exception ex){}
			
			if(files != null){
				if(files.size() > 0)
					postImageA = files.get(0);
				
				if(files.size() > 1)
					postImageB = files.get(1);
			}
			
			PostBean posta = vote.getPost_a();
			PostBean postb = vote.getPost_b();
			
			// posta
			posta.setUser_id(Integer.parseInt(userId));
			posta.setModify_time(new Date());
			postService.Modify(posta, postImageA);
			vote.setPost_id_a(posta.getId());
			
			// postb
			postb.setUser_id(Integer.parseInt(userId));
			postb.setModify_time(new Date());
			postService.Modify(postb, postImageB);
			vote.setPost_id_b(postb.getId());
			
			// vote
			vote.setUser_id(Integer.parseInt(userId));
			if(!StringGlobal.IsNull(name))
				vote.setName(name);
			vote.setPost_id_a(posta.getId());
			vote.setPost_id_b(postb.getId());
			if(!StringGlobal.IsNull(countA))
				vote.setCount_a(Integer.parseInt(countA));
			if(!StringGlobal.IsNull(countB))
				vote.setCount_b(Integer.parseInt(countB));
			if(!StringGlobal.IsNull(view))
				vote.setView(Integer.parseInt(view));
			if(!StringGlobal.IsNull(comment))
				vote.setComment(Integer.parseInt(comment));
			if(!StringGlobal.IsNull(description))
				vote.setDescription(description);
			vote.setModify_time(new Date());
			voteService.Modify(vote);
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done.\"}}"));
			return null;
		}else{
			if(!StringGlobal.IsNull(postIdA) && !StringGlobal.IsNull(postIdB) && !StringGlobal.IsNull(productIdA) && !StringGlobal.IsNull(productIdB)){
				// vote
				vote.setUser_id(0);
				if(!StringGlobal.IsNull(name))
					vote.setName(name);
				vote.setPost_id_a(Integer.parseInt(postIdA));
				vote.setPost_id_b(Integer.parseInt(postIdB));
				vote.setProduct_id_a(Integer.parseInt(productIdA));
				vote.setProduct_id_b(Integer.parseInt(productIdB));
				if(!StringGlobal.IsNull(countA))
					vote.setCount_a(Integer.parseInt(countA));
				if(!StringGlobal.IsNull(countB))
					vote.setCount_b(Integer.parseInt(countB));
				if(!StringGlobal.IsNull(view))
					vote.setView(Integer.parseInt(view));
				if(!StringGlobal.IsNull(comment))
					vote.setComment(Integer.parseInt(comment));
				if(!StringGlobal.IsNull(description))
					vote.setDescription(description);
				vote.setModify_time(new Date());
				voteService.Modify(vote);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done.\"}}"));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}