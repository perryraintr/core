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

import com.raintr.pinshe.bean.HistoryBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.service.HistoryService;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.VoteService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class ModifyVoteAction extends BaseAction {
	@Autowired
	private PostService postService;
	@Autowired
	private VoteService voteService;
	@Autowired
	private HistoryService historyService;
	
	@RequestMapping(value = "/modifyvote")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String countA = request.getParameter("a");
		String countB = request.getParameter("b");
		String view = request.getParameter("view");
		String comment = request.getParameter("comment");
		String description = request.getParameter("description");

		String userId = request.getParameter("uid");
		
		if(StringGlobal.IsNull(id)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "id is null"));
			return null;
		}
		
		name = StringGlobal.SerializeJson(name);
		description = StringGlobal.SerializeJson(description);
		
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
		
		VoteBean vote = voteService.ById(Integer.parseInt(id));
		if(vote != null){
			if(!StringGlobal.IsNull(userId)){
				HistoryBean history = historyService.ByUserIdVoteId(Integer.parseInt(userId), vote.getId());
				if(history == null){
					history = new HistoryBean();
					history.setUser_id(Integer.parseInt(userId));
					history.setVote_id(vote.getId());
					history.setCreate_time(new Date());
					history.setModify_time(new Date());
					history.setId(historyService.Add(history));
				}
			}
			
			PostBean posta = vote.getPost_a();
			PostBean postb = vote.getPost_b();
			// posta
			if(postImageA != null){
				posta.setModify_time(new Date());
				postService.Modify(posta, postImageA);
				vote.setPost_id_a(posta.getId());
			}
			// postb
			if(postImageB != null){
				postb.setModify_time(new Date());
				postService.Modify(postb, postImageB);
				vote.setPost_id_b(postb.getId());
			}
			// vote
			if(!StringGlobal.IsNull(name))
				vote.setName(name);
			if(!StringGlobal.IsNull(countA))
				vote.setCount_a(vote.getCount_a() + 1);
			if(!StringGlobal.IsNull(countB))
				vote.setCount_b(vote.getCount_b() + 1);
			if(!StringGlobal.IsNull(view))
				vote.setView(vote.getView() + 1);
			if(!StringGlobal.IsNull(comment))
				vote.setComment(vote.getComment() + 1);
			if(!StringGlobal.IsNull(description))
				vote.setDescription(description);
			//vote.setModify_time(new Date());
			voteService.Modify(vote);

			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", vote.ToId("")));
			return null;
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}