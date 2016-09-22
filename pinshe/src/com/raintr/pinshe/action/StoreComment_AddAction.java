package com.raintr.pinshe.action;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreCommentBean;
import com.raintr.pinshe.service.StoreCommentService;
import com.raintr.pinshe.service.StoreService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreComment_AddAction extends BaseAction {
	@Autowired
	private StoreService storeService;
	@Autowired
	private StoreCommentService storeCommentService;
	
	@RequestMapping(value = "/store_comment_add")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String memberId = request.getParameter("mid");
		String star = request.getParameter("star");
		String message = request.getParameter("m");

		if(StringGlobal.IsNull(storeId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "sid is null"));
			return null;
		}
		
		if(StringGlobal.IsNull(memberId)){
			response.getWriter().print(String.format("{\"head\":0,\"body\":{\"error\":\"%s\"}}", "mid is null"));
			return null;
		}
		
		StoreCommentBean storeComment = new StoreCommentBean();
		if(!StringGlobal.IsNull(storeId))
			storeComment.setStore_id(Integer.parseInt(storeId));
		if(!StringGlobal.IsNull(memberId))
			storeComment.setMember_id(Integer.parseInt(memberId));
		if(!StringGlobal.IsNull(star))
			storeComment.setStar(Integer.parseInt(star));
		if(!StringGlobal.IsNull(message)){
			message = new String(message.getBytes("ISO-8859-1"), "utf-8");
			message = StringGlobal.SerializeJson(message);
			storeComment.setMessage(message);
		}
		storeComment.setCreate_time(new Date());
		storeComment.setModify_time(new Date());
		storeComment.setId(storeCommentService.Add(storeComment));
		
		StoreBean store = storeService.ById(Integer.parseInt(storeId));
		if(store != null){
			store.setComment(store.getComment() + 1);
			if(!StringGlobal.IsNull(star))
				store.setStar(store.getStar() + Integer.parseInt(star));
			storeService.Modify(store, null, null);
		}
		
		StringBuffer json = new StringBuffer();
		json.append(String.format("%s,%s,%s,%s,%s", storeComment.ToId(""),
													storeComment.ToStar(""),
													storeComment.ToMessage(""),
													storeComment.ToCreate_time(""),
													storeComment.ToModify_time("")));
		
		response.getWriter().print(String.format("{\"head\":1,\"body\":{%s}}", json.toString()));
		return null;
	}
}