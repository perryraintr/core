package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.StoreCommentBean;
import com.raintr.pinshe.service.StoreCommentService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class StoreCommentAction extends BaseAction {
	@Autowired
	private StoreCommentService storeCommentService;
	
	@RequestMapping(value = "/store_comment")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String storeId = request.getParameter("sid");
		String page = request.getParameter("page");
		
		MemberBean member;
		StoreCommentBean storeComment;
		
		if(!StringGlobal.IsNull(storeId)){
			List<StoreCommentBean> storeComments = storeCommentService.ByStoreId(Integer.parseInt(storeId), page == null ? 0 : (Integer.parseInt(page) - 1) * 100);
			if(storeComments != null && storeComments.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < storeComments.size(); i++){
					storeComment = storeComments.get(i);
					
					member = storeComment.getMember();
					if(member == null)
						member = new MemberBean();
					
					json.append(String.format("{%s,%s,%s,%s,%s,%s,%s,%s},", storeComment.ToId(""), 
																			storeComment.ToStar(""), 
																			storeComment.ToMessage(""),
																			storeComment.ToCreate_time1(""),
																			storeComment.ToCreate_time(""),
																			storeComment.ToModify_time(""),
																			member.ToName(""),
																			member.ToAvatar("")));
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