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

import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.PublishBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.service.GroupService;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.PublishService;
import com.raintr.pinshe.service.TagService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class AddPostAction extends BaseAction {
	@Autowired
	private PostService postService;
	@Autowired
	private TagService tagService;
	@Autowired
	private PublishService publishService;
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/addpost")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String userId = request.getParameter("uid");
		String name = request.getParameter("name");
		String price = request.getParameter("price");
		String address = request.getParameter("address");
		String brand = request.getParameter("brand");
		String description = request.getParameter("description");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		
		if(StringGlobal.IsNull(userId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "uid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(t1)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "t1 is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(t2)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "t2 is null"));
			return null;
		}
		
		List<MultipartFile> files = null;
		try{
			files = ((MultipartHttpServletRequest)request).getFiles("file");
		}catch(Exception ex){}
		
		if(files == null){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "file is null"));
			return null;
		}
		
		name = StringGlobal.SerializeJson(name);
		address = StringGlobal.SerializeJson(address);
		brand = StringGlobal.SerializeJson(brand);
		description = StringGlobal.SerializeJson(description);
		
		// post
		PostBean post = new PostBean();
		post.setUser_id(Integer.parseInt(userId));
		if(!StringGlobal.IsNull(name))
			post.setName(name);
		post.setView(0);
		post.setComment(0);
		post.setFavorite(0);
		try{post.setPrice(Double.parseDouble(price));}catch(Exception e){}
		if(!StringGlobal.IsNull(address))
			post.setAddress(address);
		if(!StringGlobal.IsNull(brand))
			post.setBrand(brand);
		if(!StringGlobal.IsNull(description))
			post.setDescription(description);
		post.setCreate_time(new Date());
		post.setModify_time(new Date());
		post.setId(postService.Add(post, files));
		
		// tag
		TagBean tag;
		String[] t2s = t2.split(",");
		if(t2s != null){
			for(int i = 0; i < t2s.length; i++){
				if(t2s[i] != null){
					tag = new TagBean();
					tag.setT1(Integer.parseInt(t1));
					tag.setT2(Integer.parseInt(t2s[i]));
					tag.setPost_id(post.getId());
					tag.setCreate_time(new Date());
					tag.setModify_time(new Date());
					tagService.Add(tag);
				}
			}
		}

		// publish
		PublishBean publish = new PublishBean();
		publish.setUser_id(Integer.parseInt(userId));
		publish.setVote_id(0);
		publish.setPost_id(post.getId());
		publish.setCreate_time(new Date());
		publish.setModify_time(new Date());
		publishService.Add(publish);

		
		GroupBean group = groupService.ById(Integer.parseInt(t2));
		if(group != null){
			group.setPost_count(group.getPost_count() + 1);
			groupService.Modify(group, null);
		}
		
		StringBuffer json = new StringBuffer();
		json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",	post.ToId("post_"), 
																		post.ToBrand("post_"), 
																		post.ToImage("post_"), 
																		post.ToName("post_"), 
																		post.ToPrice("post_"), 
																		post.ToAddress("post_"),
																		post.ToView("post_"),
																		post.ToComment("post_"),
																		post.ToFavorite("post_"),
																		post.ToDescription("post_"),
																		post.ToModify_time("post_")));
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
		return null;
	}
}