package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.dao.MemberDao;

public class MemberService {
	
	private MemberDao memberDao;
	
	public List<MemberBean> By(int page) throws Exception{		
		return memberDao.By(page);
	}
	
	public MemberBean ById(int id){
		return memberDao.ById(id);
	}
	
	public MemberBean ByWechatId(String wechatId){		
		return memberDao.ByWechatId(wechatId);
	}
	
	public MemberBean ByPhone(String phone){
		return memberDao.ByPhone(phone);
	}
	
	public MemberBean ByPhonePassword(String phone, String password){
		return memberDao.ByPhonePassword(phone, password);
	}
	
	public int Add(MemberBean member){
		return memberDao.Add(member);
		
	}
	
	public int Modify(MemberBean member){
		return memberDao.Modify(member);
	}
	
	public int Remove(int id){
		return memberDao.Remove(id);
	}
	
	

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}
