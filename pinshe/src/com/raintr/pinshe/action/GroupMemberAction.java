package com.raintr.pinshe.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raintr.pinshe.bean.GroupMemberBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.service.GroupMemberService;
import com.raintr.pinshe.utils.StringGlobal;

@Controller
@RequestMapping(value = "/")
public class GroupMemberAction extends BaseAction {
	@Autowired
	private GroupMemberService groupMemberService;

	@RequestMapping(value = "/group_member")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		String groupId = request.getParameter("gid");
		String page = request.getParameter("page");

		UserBean user;
		GroupMemberBean groupMember;
		List<GroupMemberBean> groupMembers;
		
		if(!StringGlobal.IsNull(groupId)){
			groupMembers = groupMemberService.ByGroupId(Integer.parseInt(groupId), page == null ? 0 : (Integer.parseInt(page) - 1) * 10);
			if(groupMembers != null && groupMembers.size() > 0){
				StringBuffer json = new StringBuffer();
				for(int i = 0; i < groupMembers.size(); i++){
					groupMember = groupMembers.get(i);
					user = groupMember.getUser();
					
					if(user == null)
						user = new UserBean();
					
					json.append("{");
					json.append(String.format("%s,%s,%s,%s", 	groupMember.ToId(""),
																user.ToAvatar(""),
																groupMember.ToCreate_time(""),
																groupMember.ToModify_time("")));
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
}