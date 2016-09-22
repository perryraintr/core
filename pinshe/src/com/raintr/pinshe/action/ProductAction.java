package com.raintr.pinshe.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.FavoriteBean;
import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.service.FavoriteService;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.ProductService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class ProductAction extends BaseAction {
	@Autowired
	private ProductService productService;
	@Autowired
	private PostService postService;
	@Autowired
	private FavoriteService favoriteService;
	
	@RequestMapping(value = "/product")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String productId = request.getParameter("pid");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		String userId = request.getParameter("uid");
		String page = request.getParameter("page");
		
		FavoriteBean favorite = null;
		
		StringBuffer json = new StringBuffer();
		
		if(productId != null && productId.length() > 0){
			if(!StringGlobal.IsNull(userId))
				favorite = favoriteService.ByUserIdProductId(Integer.parseInt(userId), Integer.parseInt(productId));

			if(favorite == null)
				favorite = new FavoriteBean();
			
			ProductBean product = productService.ById(Integer.parseInt(productId));
			if(product != null){
				List<TagBean> tags = product.getTags();
				
				Map<Integer, TagBean> maps = new HashMap<Integer, TagBean>();
				if(tags == null || tags.size() == 0){
					tags = new ArrayList<TagBean>();
					tags.add(new TagBean());
				}else{
					for(int i = 0; i < tags.size(); i++){
						if(!maps.containsKey(tags.get(i).getT2()))
							maps.put(tags.get(i).getT2(), tags.get(i));
					}
				}
				
				List<ImageBean> images = product.getImages();
				
				// t1
				json.append(String.format("%s,", tags.get(0).ToT1("tag_")));
				json.append("\"tag_t2\":[");
				
				int index = 0;
				for (Entry<Integer, TagBean> entry : maps.entrySet()){
					if(index == 0){
						json.append(String.format("\"%s\"", entry.getValue().getGroup().getName()));
						index++;
					}else{
						json.append(String.format(",\"%s\"", entry.getValue().getGroup().getName()));
					}
				}
				json.append("],");
				
				json.append(String.format("%s,",favorite.ToId("favorite_")));
				
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,", product.ToId("product_"), 
																		 product.ToName("product_"), 
																		 product.ToFavorite("product_"),
																		 product.ToImage("product_"), 
																		 product.ToPrice("product_"), 
																		 product.ToAddress("product_"),
																		 product.ToBrand("product_"),
																		 product.ToDescription("product_"),
																		 product.ToModify_time("product_")));

				json.append("\"product_images\":[");
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
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}

		if(t1 != null && t1.length() > 0 && t2 != null && t2.length() > 0){
			int users = 0;
			UserBean user1 = null;
			UserBean user2 = null;
			UserBean user3 = null;
			ProductBean product;
			List<PostBean> posts;
			List<ProductBean> products = productService.ByTag(Integer.parseInt(t1), Integer.parseInt(t2), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(products != null){
				for(int i = 0; i < products.size(); i++){
					product = products.get(i);
					if(product != null){
						users = 0;
						user1 = new UserBean();
						user2 = new UserBean();
						user3 = new UserBean();
						
						posts = postService.ByProductId(product.getId());
						if(posts != null){
							users = posts.size();
							
							if(posts.size() > 0)
								user1 = posts.get(0).getUser();
							if(posts.size() > 1)
								user2 = posts.get(1).getUser();
							if(posts.size() > 2)
								user3 = posts.get(2).getUser();
						}
						
						json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,\"user_count\":%d,%s,%s,%s},", 	product.ToId("product_"), 
																												product.ToBrand("product_"), 
																												product.ToImage("product_"), 
																												product.ToName("product_"), 
																												product.ToPrice("product_"), 
																												product.ToAddress("product_"),
																												product.ToFavorite("product_"),
																												product.ToDescription("product_"),
																												product.ToModify_time("product_"),
																												users,
																												user1.ToAvatar("user1_"),
																												user2.ToAvatar("user2_"),
																												user3.ToAvatar("user3_")));
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
	
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}