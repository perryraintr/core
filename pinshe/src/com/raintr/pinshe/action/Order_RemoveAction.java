package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.service.OrderDetailService;
import com.raintr.pinshe.service.OrderService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class Order_RemoveAction extends BaseAction {
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderDetailService orderDetailService;
	
	@RequestMapping(value = "/order_remove")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	
		String id = request.getParameter("id");

		if(!StringGlobal.IsNull(id)){
			orderService.Remove(Integer.parseInt(id));
			
			List<OrderDetailBean> orderDetails = orderDetailService.ByOrderId(Integer.parseInt(id));
			for(int i = 0; i < orderDetails.size(); i++){
				orderDetailService.Remove(orderDetails.get(i).getId());
			}
		}
		response.getWriter().print("{\"head\":1,\"body\":{}}");
		return null;
	}
}