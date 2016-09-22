package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CouponMemberBean;
import com.raintr.pinshe.dao.CouponDao;
import com.raintr.pinshe.dao.CouponMemberDao;
import com.raintr.pinshe.dao.MemberDao;

public class CouponMemberService {
	private MemberDao memberDao;
	private CouponDao couponDao;
	private CouponMemberDao couponMemberDao;
	
	public List<CouponMemberBean> By(int page) throws Exception{		
		return couponMemberDao.By(page);
	}
	
	public CouponMemberBean ById(int id){
		CouponMemberBean couponMember = couponMemberDao.ById(id);
		if(couponMember != null){
			couponMember.setCoupon(couponDao.ById(couponMember.getCoupon_id()));
			couponMember.setMember(memberDao.ById(couponMember.getMember_id()));
		}
		
		return couponMember;
	}
	
	public List<CouponMemberBean> ByMemberId(int memberId){
		CouponMemberBean couponMember;
		List<CouponMemberBean> couponMembers = couponMemberDao.ByMemberId(memberId);
		if(couponMembers != null && couponMembers.size() > 0){
			for(int i = 0; i < couponMembers.size(); i++){
				couponMember = couponMembers.get(i);
				if(couponMember != null){
					couponMember.setCoupon(couponDao.ById(couponMember.getCoupon_id()));
//					couponMember.setMember(memberDao.ById(couponMember.getMember_id()));
				}
			}
		}
		
		return couponMembers;
	}
	
	public CouponMemberBean ByMemberIdCouponId(int memberId, int couponId){
		return couponMemberDao.ByMemberIdCouponId(memberId, couponId);
	}
	
	public int Add(CouponMemberBean couponMember){
		return couponMemberDao.Add(couponMember);
		
	}
	
	public int Modify(CouponMemberBean couponMember){
		return couponMemberDao.Modify(couponMember);
	}

	public int Remove(int id){
		return couponMemberDao.Remove(id);
	}

	
	
	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public CouponDao getCouponDao() {
		return couponDao;
	}

	public void setCouponDao(CouponDao couponDao) {
		this.couponDao = couponDao;
	}

	public CouponMemberDao getCouponMemberDao() {
		return couponMemberDao;
	}

	public void setCouponMemberDao(CouponMemberDao couponMemberDao) {
		this.couponMemberDao = couponMemberDao;
	}
}
