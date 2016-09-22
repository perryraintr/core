package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_PostAction extends BaseAction {
	@Autowired
	private PostService postService;
	
	@RequestMapping(value = "/admin_post")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String id = request.getParameter("id");	
		String page = request.getParameter("page");
		
		TagBean tag;
		PostBean post;
		List<PostBean> posts = null;
		
		if(!StringGlobal.IsNull(page)){
			posts = postService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			
			if(posts != null && posts.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < posts.size(); i++){
					post = posts.get(i);
					List<TagBean> tags = post.getTags();
					if(tags == null || tags.size() == 0){
						tag = new TagBean();
						tags.add(tag);
					}
					
					json.append("{");
					// t1
					json.append(String.format("%s,", tags.get(0).ToT1Id("tag_")));
					json.append("\"tag_t2\":[");
					json.append(String.format("%s", tags.get(0).getT2()));
					for(int j = 1; j < tags.size(); j++){
						json.append(String.format(",%s", tags.get(j).getT2()));
					}
					json.append("],");
					
					json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 	post.ToId("post_"), 
																							post.ToBrand("post_"), 
																							post.ToImage("post_"), 
																							post.ToName("post_"), 
																							post.ToPrice("post_"), 
																							post.ToAddress("post_"),
																							post.ToView("post_"),
																							post.ToComment("post_"),
																							post.ToFavorite("post_"),
																							post.ToDescription("post_"),
																							"\"user_guid\":" + post.getUser_id() + "",
																							"\"product_guid\":" + post.getProduct_id() + "",
																							"\"post_create_time\":\"" + post.getCreate_time() + "\"",
																							"\"post_modify_time\":\"" + post.getModify_time() + "\""));
					json.append("},");
					
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}

		if(!StringGlobal.IsNull(id)){
			post = postService.ById(Integer.parseInt(id));
			if(post != null){
				StringBuffer json = new StringBuffer();
				List<ImageBean> images = post.getImages();
				List<TagBean> tags = post.getTags();
				if(tags != null && tags.size() > 0){
					// t1
					json.append(String.format("%s,", tags.get(0).ToT1Id("tag_")));
					json.append("\"tag_t2\":[");
					json.append(String.format("%s", tags.get(0).getT2()));
					for(int j = 1; j < tags.size(); j++){
						json.append(String.format(",%s", tags.get(j).getT2()));
					}
					json.append("],");
				}
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", post.ToId("post_"), 
																						post.ToBrand("post_"), 
																						post.ToImage("post_"), 
																						post.ToName("post_"), 
																						post.ToPrice("post_"), 
																						post.ToAddress("post_"),
																						post.ToView("post_"),
																						post.ToComment("post_"),
																						post.ToFavorite("post_"),
																						post.ToDescription("post_"),
																						"\"user_guid\":" + post.getUser_id() + "",
																						"\"product_guid\":" + post.getProduct_id() + "",
																						"\"post_create_time\":\"" + post.getCreate_time() + "\"",
																						"\"post_modify_time\":\"" + post.getModify_time() + "\""));
				
				
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
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}