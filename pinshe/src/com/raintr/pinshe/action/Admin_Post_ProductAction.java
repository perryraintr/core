package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.ProductService;
import com.raintr.pinshe.service.TagService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_Post_ProductAction extends BaseAction {
	@Autowired
	private PostService postService;
	@Autowired
	private ProductService productService;
	@Autowired
	private TagService tagService;
	
	@RequestMapping(value = "/admin_post_product")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String postId = request.getParameter("postid");
		String productId = request.getParameter("productid");
		
		if(StringGlobal.IsNull(postId)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"postid is null\"}}");
			return null;
		}
		
		if(StringGlobal.IsNull(productId)){
			response.getWriter().print("{\"head\":0,\"body\":{\"message\":\"productid is null\"}}");
			return null;
		}
		
		PostBean post = postService.ById(Integer.parseInt(postId));
			
		// post
		post.setProduct_id(Integer.parseInt(productId));
		post.setModify_time(new Date());
		postService.Modify(post);
		// product
		ProductBean product = productService.ById(post.getProduct_id());
		if(product != null){
			product.setFavorite(product.getFavorite() + post.getFavorite());
			productService.Modify(product, null);
		}
		// tag
		for(int i = 0; i < post.getTags().size(); i++){
			post.getTags().get(i).setProduct_id(post.getProduct_id());
			tagService.Modify(post.getTags().get(i));
		}
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done.\"}}"));
		return null;
	}
}