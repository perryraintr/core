package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CouponBean;
import com.raintr.pinshe.dao.CouponDao;
import com.raintr.pinshe.dao.MemberDao;

public class CouponService {
	private CouponDao couponDao;
	private MemberDao memberDao;
	
	public List<CouponBean> By(int page) throws Exception{		
		return couponDao.By(page);
	}
	
	public CouponBean ById(int id){
		CouponBean coupon = couponDao.ById(id);
		if(coupon != null)
			coupon.setMember(memberDao.ById(coupon.getMember_id()));
		
		return coupon;
	}
	
	public List<CouponBean> ByMemberId(int memberId){
		return couponDao.ByMemberId(memberId);
	}
	
	public List<CouponBean> ByCouponMember(int memberId){
		return couponDao.ByCouponMember(memberId);
	}
	
	public int Add(CouponBean coupon){
		return couponDao.Add(coupon);
		
	}
	
	public int Modify(CouponBean coupon){
		return couponDao.Modify(coupon);
	}

	public int Remove(int id){
		return couponDao.Remove(id);
	}
	
	
	
	public CouponDao getCouponDao() {
		return couponDao;
	}

	public void setCouponDao(CouponDao couponDao) {
		this.couponDao = couponDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}
