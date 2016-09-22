package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.CouponMemberBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class CouponMemberDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<CouponMemberBean> By(int page){
		String key = "couponMember.by" + page;
		if(Cache.couponMembers.containsKey(key))
	    	return Cache.couponMembers.get(key);
		
		List<CouponMemberBean> couponMembers = (List<CouponMemberBean>)getSqlMapClientTemplate().queryForList("couponMember.by", page);
		Cache.couponMembers.put(key, couponMembers);
		return couponMembers;
	}
	
	public CouponMemberBean ById(int id){
		String key = "couponMember.byId" + id;
		if(Cache.couponMember.containsKey(key))
	    	return Cache.couponMember.get(key);
		
		CouponMemberBean couponMember = (CouponMemberBean)getSqlMapClientTemplate().queryForObject("couponMember.byId", id);
		Cache.couponMember.put(key, couponMember);
		return couponMember;
	}
	
	@SuppressWarnings("unchecked")
	public List<CouponMemberBean> ByMemberId(int memberId){
		String key = "couponMember.byMemberId" + memberId;
		if(Cache.couponMembers.containsKey(key))
	    	return Cache.couponMembers.get(key);
		
		List<CouponMemberBean> couponMembers = (List<CouponMemberBean>)getSqlMapClientTemplate().queryForList("couponMember.byMemberId", memberId);
		Cache.couponMembers.put(key, couponMembers);
		return couponMembers;
	}
	
	public CouponMemberBean ByMemberIdCouponId(int memberId, int couponId){
		String key = "couponMember.byMemberIdCouponId" + memberId + couponId;
		if(Cache.couponMembers.containsKey(key))
	    	return Cache.couponMember.get(key);
		
		CouponMemberBean couponMember = new CouponMemberBean();
		couponMember.setCoupon_id(couponId);
		couponMember.setMember_id(memberId);
		couponMember = (CouponMemberBean)getSqlMapClientTemplate().queryForObject("couponMember.byMemberIdCouponId", couponMember);
		Cache.couponMember.put(key, couponMember);
		return couponMember;
	}
	
	
	public int Add(CouponMemberBean couponMember){
		int id = (Integer)getSqlMapClientTemplate().insert("couponMember.add", couponMember);
		Cache.couponMember.clear();
		Cache.couponMembers.clear();
		return id;
	}
	
	public int Modify(CouponMemberBean couponMember){
		int id = (Integer)getSqlMapClientTemplate().update("couponMember.modify", couponMember);
		Cache.couponMember.clear();
		Cache.couponMembers.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("couponMember.remove", id);
		Cache.couponMember.clear();
		Cache.couponMembers.clear();
		return id;
	}
}