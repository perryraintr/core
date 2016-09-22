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

import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.service.ProductService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Admin_Product_AddAction extends BaseAction {
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/admin_product_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String name = request.getParameter("name");
		String favorite = request.getParameter("favorite");
		String price = request.getParameter("price");
		String address = request.getParameter("address");
		String brand = request.getParameter("brand");
		String description = request.getParameter("description");

		name = StringGlobal.SerializeJson(name);
		address = StringGlobal.SerializeJson(address);
		brand = StringGlobal.SerializeJson(brand);
		description = StringGlobal.SerializeJson(description);
		
		List<MultipartFile> files = null;
		try{
			files = ((MultipartHttpServletRequest)request).getFiles("file");
		}catch(Exception ex){}
		
		// post
		ProductBean product = new ProductBean();
		if(!StringGlobal.IsNull(name))
			product.setName(name);
		if(!StringGlobal.IsNull(favorite))
			product.setFavorite(Integer.parseInt(favorite));
		try{product.setPrice(Double.parseDouble(price));}catch(Exception e){}
		if(!StringGlobal.IsNull(address))
			product.setAddress(address);
		if(!StringGlobal.IsNull(brand))
			product.setBrand(brand);
		if(!StringGlobal.IsNull(description))
			product.setDescription(description);
		product.setCreate_time(new Date());
		product.setModify_time(new Date());
		product.setId(productService.Add(product, files));

		response.getWriter().print(String.format("{\"head\":1,\"body\":{\"message\":\"done. id=%d\",\"url\":\"%s\"}}", product.getId(), productService.GetUrl(product.getId(), product.getAddress())));
		return null;
	}
}