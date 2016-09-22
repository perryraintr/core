package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CartBean;
import com.raintr.pinshe.dao.CartDao;
import com.raintr.pinshe.dao.CommodityDao;
import com.raintr.pinshe.dao.CommodityImageDao;

public class CartService {
	private CartDao cartDao;
//	private MemberDao memberDao;
	private CommodityDao commodityDao;
	private CommodityImageDao commodityImageDao;
	
	public List<CartBean> By(int page){	
//		CartBean cart;
		List<CartBean> carts = cartDao.By(page);
//		for(int i = 0; i < carts.size(); i++){
//			cart = carts.get(i);
//			if(cart != null){
//				if(cart.getMember_id() > 0)
//					cart.setMemeber(memberDao.ById(cart.getMember_id()));
//			}
//		}
		return carts;
	}
	
	public CartBean ById(int id){
		return cartDao.ById(id);
	}
	
	public List<CartBean> ByMemberId(int memberId){
		CartBean cart;
		List<CartBean> carts = cartDao.ByMemberId(memberId);
		if(carts != null && carts.size() > 0){
			for(int i = 0; i < carts.size(); i++){
				cart = carts.get(i);
				if(cart != null){
					if(cart.getCommodity_id() > 0){
						cart.setCommodity(commodityDao.ById(cart.getCommodity_id()));
						cart.getCommodity().setImages(commodityImageDao.ByCommodityId(cart.getCommodity_id()));
					}
				}
			}
		}
		
		return carts;
	}
	
	public CartBean ByMemberIdCommodityId(int memberId, int commodityId){	
		return cartDao.ByMemberIdCommodityId(memberId, commodityId);
	}
	
	public int Add(CartBean cart){
		return cartDao.Add(cart);
	}
	
	public int Modify(CartBean cart){
		return cartDao.Modify(cart);
	}
	
	public int Remove(int id){
		return cartDao.Remove(id);
	}

	public int RemoveMemberIdCommodityId(int memberId, int commodityId){
		return cartDao.RemoveMemberIdCommodityId(memberId, commodityId);
	}
	
	public CartDao getCartDao() {
		return cartDao;
	}

	public void setCartDao(CartDao cartDao) {
		this.cartDao = cartDao;
	}

	public CommodityDao getCommodityDao() {
		return commodityDao;
	}

	public void setCommodityDao(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}

	public CommodityImageDao getCommodityImageDao() {
		return commodityImageDao;
	}

	public void setCommodityImageDao(CommodityImageDao commodityImageDao) {
		this.commodityImageDao = commodityImageDao;
	}
}
