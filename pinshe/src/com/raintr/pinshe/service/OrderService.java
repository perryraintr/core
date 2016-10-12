package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.dao.CommodityDao;
import com.raintr.pinshe.dao.CommodityImageDao;
import com.raintr.pinshe.dao.ConsigneeDao;
import com.raintr.pinshe.dao.CouponDao;
import com.raintr.pinshe.dao.MemberDao;
import com.raintr.pinshe.dao.MerchantDao;
import com.raintr.pinshe.dao.OrderDao;
import com.raintr.pinshe.dao.OrderDetailDao;
import com.raintr.pinshe.dao.StoreDao;
import com.raintr.pinshe.dao.StoreImageDao;

public class OrderService {
	private OrderDao orderDao;
	private OrderDetailDao orderDetailDao;
	private CommodityDao commodityDao;
	private CouponDao couponDao;
	private ConsigneeDao consigneeDao;
	private CommodityImageDao commodityImageDao;
	private StoreDao storeDao;
	private StoreImageDao storeImageDao;
	private MemberDao memberDao;
	private MerchantDao merchantDao;
	
	public List<OrderBean> By(int page){
		OrderBean order;
		OrderDetailBean orderDetail;
		List<OrderBean> orders = orderDao.By(page);
		if(orders != null && orders.size() > 0){
			for(int i = 0; i < orders.size(); i++){
				order = orders.get(i);
				order.setMember(memberDao.ById(order.getMember_id()));
				order.setConsignee(consigneeDao.ById(order.getConsignee_id()));
				order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));
				
				List<OrderDetailBean> orderDetails = order.getOrderDetails();
				if(orderDetails != null && orderDetails.size() > 0){
					for(int j = 0; j < orderDetails.size(); j++){
						orderDetail = orderDetails.get(j);
						if(orderDetail.getCommodity_id() > 0)
							orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
						if(orderDetail.getStore_id() > 0)
							orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
					}
				}
			}
		}
		
		return orders;
	}
	
	public OrderBean ById(int id){
		StoreBean store;
		OrderDetailBean orderDetail;
		OrderBean order = orderDao.ById(id);
		if(order != null){
			order.setCoupon(couponDao.ById(order.getCoupon_id()));
			order.setConsignee(consigneeDao.ById(order.getConsignee_id()));	
			order.setMember(memberDao.ById(order.getMember_id()));
			
			order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));
			List<OrderDetailBean> orderDetails = order.getOrderDetails();
			if(orderDetails != null && orderDetails.size() > 0){
				for(int i = 0; i < orderDetails.size(); i++){
					orderDetail = orderDetails.get(i);
					if(orderDetail.getCommodity_id() > 0)
						orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
					if(orderDetail.getStore_id() > 0){
						orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
						store = orderDetail.getStore();
						if(store != null)
							store.setMerchant(merchantDao.ById(store.getMerchant_id()));
					}
				}
			}
		}
		
		return order;
	}
	
	public List<OrderBean> ByMemberId(int memberId, int page){
		OrderBean order;
		OrderDetailBean orderDetail;
		CommodityBean commodity;
		StoreBean store;
		List<OrderBean> orders = orderDao.ByMemberId(memberId, page);
		if(orders != null && orders.size() > 0){
			for(int i = 0; i < orders.size(); i++){
				order = orders.get(i);
				order.setCoupon(couponDao.ById(order.getCoupon_id()));
				order.setConsignee(consigneeDao.ById(order.getConsignee_id()));
				order.setMember(memberDao.ById(order.getMember_id()));
				
				order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));
				List<OrderDetailBean> orderDetails = order.getOrderDetails();
				if(orderDetails != null && orderDetails.size() > 0){
					for(int j = 0; j < orderDetails.size(); j++){
						orderDetail = orderDetails.get(j);
						
						if(orderDetail.getCommodity_id() > 0){
							orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
							commodity = orderDetail.getCommodity();
							if(commodity != null)
								commodity.setImages(commodityImageDao.ByCommodityId(orderDetail.getCommodity_id()));
						}
						
						if(orderDetail.getStore_id() > 0){
							orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
							store = orderDetail.getStore();
							if(store != null)
								store.setImages(storeImageDao.ByStoreId(orderDetail.getStore_id()));
						}
					}
				}
			}
		}
		
		return orders;
	}
	
	public OrderBean ByOrderNo(String orderNo){
		StoreBean store;
		OrderDetailBean orderDetail;
		OrderBean order = orderDao.ByOrderNo(orderNo);
		order.setMember(memberDao.ById(order.getMember_id()));
		order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));

		List<OrderDetailBean> orderDetails = order.getOrderDetails();
		if(orderDetails != null && orderDetails.size() > 0){
			for(int i = 0; i < orderDetails.size(); i++){
				orderDetail = orderDetails.get(i);
				if(orderDetail.getCommodity_id() > 0)
					orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
				if(orderDetail.getStore_id() > 0){
					orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
					store = orderDetail.getStore();
					if(store != null)
						store.setMerchant(merchantDao.ById(store.getMerchant_id()));
				}
			}
		}
		
		return order;
	}
	
	public List<OrderBean> ByStoreId(int storeId, int page){
		StoreBean store;
		OrderBean order;
		OrderDetailBean orderDetail;
		List<OrderBean> orders = orderDao.ByStoreId(storeId, page);
		if(orders != null && orders.size() > 0){
			for(int i = 0; i < orders.size(); i++){
				order = orders.get(i);
				order.setMember(memberDao.ById(order.getMember_id()));
				order.setConsignee(consigneeDao.ById(order.getConsignee_id()));
				order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));
				
				List<OrderDetailBean> orderDetails = order.getOrderDetails();
				if(orderDetails != null && orderDetails.size() > 0){
					for(int j = 0; j < orderDetails.size(); j++){
						orderDetail = orderDetails.get(j);
						if(orderDetail.getCommodity_id() > 0)
							orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
						
						if(orderDetail.getStore_id() > 0){
							orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
							store = orderDetail.getStore();
							if(store != null)
								store.setImages(storeImageDao.ByStoreId(orderDetail.getStore_id()));
						}
						
					}
				}
			}
		}
		
		return orders;
	}
	
	
	public List<OrderBean> ByCommodity(int page){
		StoreBean store;
		OrderBean order;
		OrderDetailBean orderDetail;
		List<OrderBean> orders = orderDao.ByCommodity(page);
		if(orders != null && orders.size() > 0){
			for(int i = 0; i < orders.size(); i++){
				order = orders.get(i);
				order.setMember(memberDao.ById(order.getMember_id()));
				order.setConsignee(consigneeDao.ById(order.getConsignee_id()));
				order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));
				
				List<OrderDetailBean> orderDetails = order.getOrderDetails();
				if(orderDetails != null && orderDetails.size() > 0){
					for(int j = 0; j < orderDetails.size(); j++){
						orderDetail = orderDetails.get(j);
						if(orderDetail.getCommodity_id() > 0)
							orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
						if(orderDetail.getStore_id() > 0){
							orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
							store = orderDetail.getStore();
							if(store != null)
								store.setImages(storeImageDao.ByStoreId(orderDetail.getStore_id()));
						}
					}
				}
			}
		}
		
		return orders;
	}
	
	public List<OrderBean> ByVip(int page){
		StoreBean store;
		OrderBean order;
		OrderDetailBean orderDetail;
		List<OrderBean> orders = orderDao.ByVip(page);
		if(orders != null && orders.size() > 0){
			for(int i = 0; i < orders.size(); i++){
				order = orders.get(i);
				order.setMember(memberDao.ById(order.getMember_id()));
				order.setConsignee(consigneeDao.ById(order.getConsignee_id()));
				order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));
				
				List<OrderDetailBean> orderDetails = order.getOrderDetails();
				if(orderDetails != null && orderDetails.size() > 0){
					for(int j = 0; j < orderDetails.size(); j++){
						orderDetail = orderDetails.get(j);
						if(orderDetail.getCommodity_id() > 0)
							orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
						if(orderDetail.getStore_id() > 0){
							orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
							store = orderDetail.getStore();
							if(store != null)
								store.setImages(storeImageDao.ByStoreId(orderDetail.getStore_id()));
						}
					}
				}
			}
		}
		
		return orders;
	}
	
	
	public List<OrderBean> ByStore(int page){
		StoreBean store;
		OrderBean order;
		OrderDetailBean orderDetail;
		List<OrderBean> orders = orderDao.ByStore(page);
		if(orders != null && orders.size() > 0){
			for(int i = 0; i < orders.size(); i++){
				order = orders.get(i);
				order.setMember(memberDao.ById(order.getMember_id()));
				order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));
				
				List<OrderDetailBean> orderDetails = order.getOrderDetails();
				if(orderDetails != null && orderDetails.size() > 0){
					for(int j = 0; j < orderDetails.size(); j++){
						orderDetail = orderDetails.get(j);
						if(orderDetail.getCommodity_id() > 0)
							orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
						if(orderDetail.getStore_id() > 0){
							orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
							store = orderDetail.getStore();
							if(store != null)
								store.setImages(storeImageDao.ByStoreId(orderDetail.getStore_id()));
						}
					}
				}
			}
		}
		
		return orders;
	}
	
	public List<OrderBean> ByStatus(int stauts){
		OrderBean order;
		OrderDetailBean orderDetail;
		List<OrderBean> orders = orderDao.ByStatus(stauts);
		if(orders != null && orders.size() > 0){
			for(int i = 0; i < orders.size(); i++){
				order = orders.get(i);
				order.setMember(memberDao.ById(order.getMember_id()));
				order.setConsignee(consigneeDao.ById(order.getConsignee_id()));
				order.setOrderDetails(orderDetailDao.ByOrderId(order.getId()));
				
				List<OrderDetailBean> orderDetails = order.getOrderDetails();
				if(orderDetails != null && orderDetails.size() > 0){
					for(int j = 0; j < orderDetails.size(); j++){
						orderDetail = orderDetails.get(j);
						if(orderDetail.getCommodity_id() > 0)
							orderDetail.setCommodity(commodityDao.ById(orderDetail.getCommodity_id()));
						if(orderDetail.getStore_id() > 0)
							orderDetail.setStore(storeDao.ById(orderDetail.getStore_id()));
					}
				}
			}
		}
		
		return orders;
	}
	
	public int Add(OrderBean order){
		return orderDao.Add(order);
	}
	
	public int Modify(OrderBean order){
		return orderDao.Modify(order);
	}
	
	public int Remove(int id){
		return orderDao.Remove(id);
	}

	
	
	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public OrderDetailDao getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public CommodityDao getCommodityDao() {
		return commodityDao;
	}

	public void setCommodityDao(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}

	public CouponDao getCouponDao() {
		return couponDao;
	}

	public void setCouponDao(CouponDao couponDao) {
		this.couponDao = couponDao;
	}

	public ConsigneeDao getConsigneeDao() {
		return consigneeDao;
	}

	public void setConsigneeDao(ConsigneeDao consigneeDao) {
		this.consigneeDao = consigneeDao;
	}

	public CommodityImageDao getCommodityImageDao() {
		return commodityImageDao;
	}

	public void setCommodityImageDao(CommodityImageDao commodityImageDao) {
		this.commodityImageDao = commodityImageDao;
	}

	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public StoreImageDao getStoreImageDao() {
		return storeImageDao;
	}

	public void setStoreImageDao(StoreImageDao storeImageDao) {
		this.storeImageDao = storeImageDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public MerchantDao getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(MerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}
}
