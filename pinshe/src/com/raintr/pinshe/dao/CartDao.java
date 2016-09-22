package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.CartBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class CartDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<CartBean> By(int page){
		String key = "cart.by" + page;
		if(Cache.carts.containsKey(key))
	    	return Cache.carts.get(key);
		
		List<CartBean> carts = (List<CartBean>)getSqlMapClientTemplate().queryForList("cart.by", page);
		Cache.carts.put(key, carts);
		return carts;
	}
	
	public CartBean ById(int id){
		String key = "cart.byId" + id;
		if(Cache.cart.containsKey(key))
			return Cache.cart.get(key);
		
		CartBean cart = (CartBean)getSqlMapClientTemplate().queryForObject("cart.byId", id);
		Cache.cart.put(key, cart);
		return cart;
	}
	
	@SuppressWarnings("unchecked")
	public List<CartBean> ByMemberId(int memberId){
		String key = "cart.byMemberId" + memberId;
		if(Cache.carts.containsKey(key))
	    	return Cache.carts.get(key);

		List<CartBean> carts = (List<CartBean>)getSqlMapClientTemplate().queryForList("cart.byMemberId", memberId);
		Cache.carts.put(key, carts);
		return carts;
	}
	
	public CartBean ByMemberIdCommodityId(int memberId, int commodityId){
		String key = "cart.byMemberIdCommodityId" + memberId + commodityId;
		if(Cache.cart.containsKey(key))
			return Cache.cart.get(key);
		
		CartBean cart = new CartBean();
		cart.setMember_id(memberId);
		cart.setCommodity_id(commodityId);
		
		cart = (CartBean)getSqlMapClientTemplate().queryForObject("cart.byMemberIdCommodityId", cart);
		Cache.cart.put(key, cart);
		return cart;
	}
	
	public int Add(CartBean cart){
		int id = (Integer)getSqlMapClientTemplate().insert("cart.add", cart);
		Cache.cart.clear();
		Cache.carts.clear();
		return id;
	}
	
	public int Modify(CartBean cart){
		int id = (Integer)getSqlMapClientTemplate().update("cart.modify", cart);
		Cache.cart.clear();
		Cache.carts.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("cart.remove", id);
		Cache.cart.clear();
		Cache.carts.clear();
		return id;
	}
	
	public int RemoveMemberIdCommodityId(int memberId, int commodityId){
		CartBean cart = new CartBean();
		cart.setMember_id(memberId);
		cart.setCommodity_id(commodityId);
		
		int id = (Integer)getSqlMapClientTemplate().delete("cart.removeMemberIdCommodityId", cart);
		Cache.cart.clear();
		Cache.carts.clear();
		return id;
	}
}