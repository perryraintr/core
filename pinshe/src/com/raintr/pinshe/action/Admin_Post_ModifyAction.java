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
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.ProductService;
import com.raintr.pinshe.service.TagService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_Post_ModifyAction extends BaseAction {
	@Autowired
	private PostService postService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/admin_post_modify")
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
		String name = request.getParameter("name");
		String view = request.getParameter("view");
		String comment = request.getParameter("comment");
		String favorite = request.getParameter("favorite");
		String price = request.getParameter("price");
		String address = request.getParameter("address");
		String brand = request.getParameter("brand");
		String description = request.getParameter("description");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		String productId = request.getParameter("pid");
		
		name = StringGlobal.SerializeJson(name);
		address = StringGlobal.SerializeJson(address);
		brand = StringGlobal.SerializeJson(brand);
		description = StringGlobal.SerializeJson(description);
		
		List<MultipartFile> files = null;
		try{
			files = ((MultipartHttpServletRequest)request).getFiles("file");
		}catch(Exception ex){}
		
		// post
		PostBean post = postService.ById(Integer.parseInt(id));
		
		if(!StringGlobal.IsNull(userId))
			post.setUser_id(Integer.parseInt(userId));
		if(!StringGlobal.IsNull(productId))
			post.setProduct_id(Integer.parseInt(productId));
		if(!StringGlobal.IsNull(name))
			post.setName(name);
		if(!StringGlobal.IsNull(view))
			post.setView(Integer.parseInt(view));
		if(!StringGlobal.IsNull(comment))
			post.setComment(Integer.parseInt(comment));
		if(!StringGlobal.IsNull(favorite))
			post.setFavorite(Integer.parseInt(favorite));
		if(!StringGlobal.IsNull(price))
			try{post.setPrice(Double.parseDouble(price));}catch(Exception e){}
		if(!StringGlobal.IsNull(address))
			post.setAddress(address);
		if(!StringGlobal.IsNull(brand))
			post.setBrand(brand);
		if(!StringGlobal.IsNull(description))
			post.setDescription(description);
		post.setModify_time(new Date());
		postService.Modify(post, files);
		
		// tag
		TagBean tag;
		if(!StringGlobal.IsNull(t2)){
			String[] t2s = t2.split(",");
			if(t2s != null){
				for(int i = 0; i < post.getTags().size(); i++){
					tagService.Remove(post.getTags().get(i).getId());
				}
				
				for(int i = 0; i < t2s.length; i++){
					if(t2s[i] != null){
						tag = new TagBean();
						tag.setT1(Integer.parseInt(t1));
						tag.setT2(Integer.parseInt(t2s[i]));
						tag.setPost_id(post.getId());
						tag.setProduct_id(Integer.parseInt(productId));
						tag.setCreate_time(new Date());
						tag.setModify_time(new Date());
						tagService.Add(tag);
					}
				}
			}
		}
		
//			// product
//			ProductBean product = productService.ById(Integer.parseInt(productId));
//			if(product != null){
//				product.setFavorite(product.getFavorite() + post.getFavorite());
//				productService.Modify(product, null);
//			}
			
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done. id=%d\"}}", post.getId()));
		return null;
	}
}