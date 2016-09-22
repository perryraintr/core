package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.service.PostService;
import com.raintr.pinshe.service.ProductService;
import com.raintr.pinshe.service.GroupService;
import com.raintr.pinshe.service.TagService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class TagAction extends BaseAction {
	@Autowired
	private ProductService productService;
	@Autowired
	private PostService postService;
	@Autowired
	private TagService tagService;
	@Autowired
	private GroupService groupService;

	@RequestMapping(value = "/tag")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String t1 = request.getParameter("t1");
		String type = request.getParameter("type");
		String page = request.getParameter("page");
		
		if(StringGlobal.IsNull(t1)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "t1 is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(page)){
			StringBuffer json = new StringBuffer();
			
			json.append(Tag(Integer.parseInt(t1), 1, "tag1_") + ",");
			json.append(Tag(Integer.parseInt(t1), 2, "tag2_") + ",");
			json.append(Tag(Integer.parseInt(t1), 3, "tag3_") + ",");
			json.append(Tag(Integer.parseInt(t1), 4, "tag4_"));
			
			response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
			return null;
		}else{
			GroupBean group;
			StringBuffer json = new StringBuffer();
			List<GroupBean> groups = groupService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			
			if(!StringGlobal.IsNull(type))
				groups = groupService.ByType(Integer.parseInt(type), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			else
				groups = groupService.By(page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			
			if(groups != null && groups.size() > 0){
				for(int i = 0; i < groups.size(); i++){
					group = groups.get(i);
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s,%s,", group.ToId("tag_"), "\"tag_t1\":" + t1, "\"tag_t2\":" + group.getId(), group.ToName("tag_"), group.ToDescription("tag_")));
					json.append(Tag(Integer.parseInt(t1), group.getId(), "tag_"));
					json.append("},");
				}
				
				if(json.length() > 0)
					json.setLength(json.length() - 1);
				
				response.getWriter().print(String.format("{\"head\":1,\"body\":{\"array\":[%s]}}", json.toString()));
				return null;
			}
		}
		
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
	
	private String Tag(int t1, int t2, String table){
		StringBuffer json = new StringBuffer();
		List<ProductBean> products = productService.ByTag(t1, t2, 0);
		if (products != null) {
			if (products.size() > 0)
				json.append(Json(products.get(0), table + "product1_"));
			if (products.size() > 1)
				json.append(Json(products.get(1), table + "product2_"));
			if (products.size() > 2)
				json.append(Json(products.get(2), table + "product3_"));
		}

		List<TagBean> tags = tagService.ByT1T2(t1, t2);
		json.append(String.format("\"%scount\":%d", table, tags == null ? 0 : tags.size()));
		
		return json.toString();
	}
	
	private String Json(ProductBean product, String table){
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,", product.ToId(table), 
															product.ToBrand(table), 
															product.ToImage(table), 
															product.ToName(table), 
															product.ToPrice(table), 
															product.ToAddress(table),
															product.ToFavorite(table),
															product.ToDescription(table),
															product.ToModify_time(table));
	}
}