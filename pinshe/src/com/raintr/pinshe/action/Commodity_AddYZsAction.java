package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.service.CommodityImageService;
import com.raintr.pinshe.service.CommodityService;
import com.raintr.pinshe.service.YouzanService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Commodity_AddYZsAction extends BaseAction {
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private YouzanService youzanService;
	@Autowired
	private CommodityImageService commodityImageService;
	
	@RequestMapping(value = "/commodity_add_yzs")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		commodityService.Modify(1);
		
		String json = youzanService.ByProduct();
		
		JSONObject row;
		JSONArray rows = JSON.parseObject(json).getJSONObject("response").getJSONArray("items");
		for(int j = 0; j < rows.size(); j++){
			row = rows.getJSONObject(j);
			
			String price = row.getString("price");
			String name = row.getString("title");
			String desc = row.getString("desc");
			String num_iid = row.getString("num_iid");
			String outer_id = row.getString("outer_id");
			JSONArray tags = row.getJSONArray("item_tags");
			
			name = StringGlobal.SerializeJson(name);
			desc = StringGlobal.SerializeJson(desc);
			
			String title = null;
			String description = null;
			String[] names = name.split("\\|");
			
			if(names.length > 0)
				title = names[0].trim();
			if(names.length > 1){
				description = names[1].trim();
				description = description.replace("&#039;", "'");
			}
			
			CommodityBean commodity = commodityService.ById(Integer.parseInt(num_iid), Integer.parseInt(outer_id));
			if(commodity == null){
				commodity = new CommodityBean();
	
				if(!StringGlobal.IsNull(num_iid)) 
					commodity.setNum_iid(Integer.parseInt(num_iid));
				if(!StringGlobal.IsNull(outer_id)) 
					commodity.setOuter_id(Integer.parseInt(outer_id));
				if(!StringGlobal.IsNull(title)) 
					commodity.setName(title);
				if(!StringGlobal.IsNull(description)) 
					commodity.setDescription(description);
				
				
				if(tags != null){
					for(int i = 0; i < tags.size(); i++){
						name = tags.getJSONObject(i).getString("name");
						names = name.split(":");
						
						if("0".equals(names[0]))
							commodity.setT0(commodity.T0(names[1]));
						else if("1".equals(names[0]))
							commodity.setT1(commodity.T1(names[1]));
						else if("2".equals(names[0]))
							commodity.setT2(commodity.T2(names[1]));
						else if("3".equals(names[0]))
							commodity.setT3(commodity.T3(names[1]));
						else if("4".equals(names[0]))
							commodity.setT4(commodity.T4(names[1]));
						else if("5".equals(names[0]))
							commodity.setT5(commodity.T5(names[1]));
					}
				}
				
				if(!StringGlobal.IsNull(price)) 
					commodity.setPrice(Double.parseDouble(price));
				commodity.setCount(9999);
				if(!StringGlobal.IsNull(desc)) 
					commodity.setDetail(desc);
				commodity.setStatus(0);
				commodity.setCreate_time(new Date());
				commodity.setModify_time(new Date());
				commodity.setId(commodityService.Add(commodity));
			}else{
				if(!StringGlobal.IsNull(num_iid)) 
					commodity.setNum_iid(Integer.parseInt(num_iid));
				if(!StringGlobal.IsNull(outer_id)) 
					commodity.setOuter_id(Integer.parseInt(outer_id));
				if(!StringGlobal.IsNull(title)) 
					commodity.setName(title);
				if(!StringGlobal.IsNull(description)) 
					commodity.setDescription(description);
	
				if(tags != null){
					for(int i = 0; i < tags.size(); i++){
						name = tags.getJSONObject(i).getString("name");
						names = name.split(":");
						
						if("0".equals(names[0]))
							commodity.setT0(commodity.T0(names[1]));
						else if("1".equals(names[0]))
							commodity.setT1(commodity.T1(names[1]));
						else if("2".equals(names[0]))
							commodity.setT2(commodity.T2(names[1]));
						else if("3".equals(names[0]))
							commodity.setT3(commodity.T3(names[1]));
						else if("4".equals(names[0]))
							commodity.setT4(commodity.T4(names[1]));
						else if("5".equals(names[0]))
							commodity.setT5(commodity.T5(names[1]));
					}
				}
				
				if(!StringGlobal.IsNull(price)) 
					commodity.setPrice(Double.parseDouble(price));

				if(!StringGlobal.IsNull(desc))
					commodity.setDetail(desc);
				commodity.setStatus(0);
//				commodity.setModify_time(new Date());
				commodityService.Modify(commodity);
			}
			
			commodityImageService.RemoveCommodityId(commodity.getId());
			
			CommodityImageBean commodityImage;
			JSONArray images = row.getJSONArray("item_imgs");
			for(int i = 0; i < images.size(); i++){
				commodityImage = new CommodityImageBean();
				commodityImage.setCommodity_id(commodity.getId());
				commodityImage.setUrl(images.getJSONObject(i).getString("url"));
				commodityImageService.Add(commodityImage);
			}
		}
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{}}"));
		return null;
	}
}