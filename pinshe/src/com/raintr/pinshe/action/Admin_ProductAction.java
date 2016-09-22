package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.service.ProductService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_ProductAction extends BaseAction {
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/admin_product")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ProductBean product;
		
		String id = request.getParameter("id");	
		String page = request.getParameter("page");
		String t1 = request.getParameter("t1");
		String t2 = request.getParameter("t2");
		String name = request.getParameter("name");	
		
		if(!StringGlobal.IsNull(name)){
			name = new String(name.getBytes("ISO-8859-1"), "utf-8");
			List<ProductBean> products = productService.ByName(name);
			if(products != null && products.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < products.size(); i++){
					product = products.get(i);
						
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	product.ToId("product_"), 
																					product.ToBrand("product_"), 
																					product.ToImage("product_"), 
																					product.ToName("product_"), 
																					product.ToPrice("product_"), 
																					product.ToAddress("product_"),
																					product.ToFavorite("product_"),
																					product.ToDescription("product_"),
																					"\"product_create_time\":\"" + product.getCreate_time() + "\"",
																					"\"product_modify_time\":\"" + product.getModify_time() + "\""));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(t1) && !StringGlobal.IsNull(t2)){
			List<ProductBean> products = productService.ByTag(Integer.parseInt(t1), Integer.parseInt(t2), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(products != null && products.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < products.size(); i++){
					product = products.get(i);
						
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	product.ToId("product_"), 
																					product.ToBrand("product_"), 
																					product.ToImage("product_"), 
																					product.ToName("product_"), 
																					product.ToPrice("product_"), 
																					product.ToAddress("product_"),
																					product.ToFavorite("product_"),
																					product.ToDescription("product_"),
																					"\"product_create_time\":\"" + product.getCreate_time() + "\"",
																					"\"product_modify_time\":\"" + product.getModify_time() + "\""));
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
			
			response.getWriter().print("{\"head\":1,\"body\":{}}");
			return null;
		}
		
		if(!StringGlobal.IsNull(id)){
			product = productService.ById(Integer.parseInt(id));
			if(product != null){
				List<ImageBean> images = product.getImages();
				StringBuffer json = new StringBuffer();
				json.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,", 	product.ToId("product_"), 
																				product.ToBrand("product_"), 
																				product.ToImage("product_"), 
																				product.ToName("product_"), 
																				product.ToPrice("product_"), 
																				product.ToAddress("product_"),
																				product.ToFavorite("product_"),
																				product.ToDescription("product_"),
																				"\"product_create_time\":\"" + product.getCreate_time() + "\"",
																				"\"product_modify_time\":\"" + product.getModify_time() + "\""));

				
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

		if(!StringGlobal.IsNull(page)){
			List<ProductBean> products = productService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(products != null && products.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < products.size(); i++){
					product = products.get(i);
						
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s,%s,%s},", 	product.ToId("product_"), 
																					product.ToBrand("product_"), 
																					product.ToImage("product_"), 
																					product.ToName("product_"), 
																					product.ToPrice("product_"), 
																					product.ToAddress("product_"),
																					product.ToFavorite("product_"),
																					product.ToDescription("product_"),
																					"\"product_create_time\":\"" + product.getCreate_time() + "\"",
																					"\"product_modify_time\":\"" + product.getModify_time() + "\""));
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