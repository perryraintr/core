package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.CouponBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class CouponDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<CouponBean> By(int page){
		String key = "coupon.by" + page;
		if(Cache.coupons.containsKey(key))
	    	return Cache.coupons.get(key);
		
		List<CouponBean> coupons = (List<CouponBean>)getSqlMapClientTemplate().queryForList("coupon.by", page);
		Cache.coupons.put(key, coupons);
		return coupons;
	}
	
	public CouponBean ById(int id){
		String key = "coupon.byId" + id;
		if(Cache.coupon.containsKey(key))
	    	return Cache.coupon.get(key);
		
		CouponBean coupon = (CouponBean)getSqlMapClientTemplate().queryForObject("coupon.byId", id);
		Cache.coupon.put(key, coupon);
		return coupon;
	}
	
	@SuppressWarnings("unchecked")
	public List<CouponBean> ByMemberId(int memberId){
		String key = "coupon.byMemberId" + memberId;
		if(Cache.coupons.containsKey(key))
	    	return Cache.coupons.get(key);
		
		List<CouponBean> coupons = (List<CouponBean>)getSqlMapClientTemplate().queryForList("coupon.byMemberId", memberId);
		Cache.coupons.put(key, coupons);
		return coupons;
	}
	
	@SuppressWarnings("unchecked")
	public List<CouponBean> ByCouponMember(int memberId){
		String key = "coupon.byCouponMember" + memberId;
		if(Cache.coupons.containsKey(key))
	    	return Cache.coupons.get(key);
		
		List<CouponBean> coupons = (List<CouponBean>)getSqlMapClientTemplate().queryForList("coupon.byCouponMember", memberId);
		Cache.coupons.put(key, coupons);
		return coupons;
	}
	
	public int Add(CouponBean coupon){
		int id = (Integer)getSqlMapClientTemplate().insert("coupon.add", coupon);
		Cache.coupon.clear();
		Cache.coupons.clear();
		return id;
	}
	
	public int Modify(CouponBean coupon){
		int id = (Integer)getSqlMapClientTemplate().update("coupon.modify", coupon);
		Cache.coupon.clear();
		Cache.coupons.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("coupon.remove", id);
		Cache.coupon.clear();
		Cache.coupons.clear();
		return id;
	}
}