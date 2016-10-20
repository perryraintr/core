package com.raintr.pinshe.service;

import java.util.Date;
import java.util.List;

import com.raintr.pinshe.bean.StoreCashBean;
import com.raintr.pinshe.dao.StoreCashDao;
import com.raintr.pinshe.dao.MemberDao;
import com.raintr.pinshe.dao.MerchantDao;
import com.raintr.pinshe.dao.OrderDao;
import com.raintr.pinshe.dao.StoreDao;

public class StoreCashService {
	private StoreCashDao storeCashDao;
	private StoreDao storeDao;
	private OrderDao orderDao;
	private MemberDao memberDao;
	private MerchantDao merchantDao;
	
	public List<StoreCashBean> By(int page){
		StoreCashBean storeCash;
		List<StoreCashBean> storeCashs = storeCashDao.By(page);
		if(storeCashs != null && storeCashs.size() > 0){
			for(int i = 0; i < storeCashs.size(); i++){
				storeCash = storeCashs.get(i);
				
				if(storeCash != null){
					storeCash.setMember(memberDao.ById(storeCash.getMember_id()));
					storeCash.setMerchant(merchantDao.ById(storeCash.getMerchant_id()));
					storeCash.setStore(storeDao.ById(storeCash.getStore_id()));
					storeCash.setOrder(orderDao.ById(storeCash.getOrder_id()));
				}
			}
		}
		return storeCashs;
	}
	
	public StoreCashBean ById(int id){
		StoreCashBean storeCash = storeCashDao.ById(id);
		if(storeCash != null){
			storeCash.setMember(memberDao.ById(storeCash.getMember_id()));
			storeCash.setMerchant(merchantDao.ById(storeCash.getMerchant_id()));
			storeCash.setStore(storeDao.ById(storeCash.getStore_id()));
			storeCash.setOrder(orderDao.ById(storeCash.getOrder_id()));
			
			return storeCash;
		}
		
		return null;
	}
	
	public StoreCashBean ByOrderId(int orderId){
		StoreCashBean storeCash = storeCashDao.ByOrderId(orderId);

		if(storeCash != null){
			storeCash.setMember(memberDao.ById(storeCash.getMember_id()));
			storeCash.setMerchant(merchantDao.ById(storeCash.getMerchant_id()));
			storeCash.setStore(storeDao.ById(storeCash.getStore_id()));
			storeCash.setOrder(orderDao.ById(storeCash.getOrder_id()));
			
			return storeCash;
		}
		
		return null;
	}
	
	public List<StoreCashBean> ByStoreId(int storeId, int page){
		StoreCashBean storeCash;
		List<StoreCashBean> storeCashs = storeCashDao.ByStoreId(storeId, page);
		if(storeCashs != null && storeCashs.size() > 0){
			for(int i = 0; i < storeCashs.size(); i++){
				storeCash = storeCashs.get(i);
				if(storeCash != null){
					storeCash.setMember(memberDao.ById(storeCash.getMember_id()));
					storeCash.setMerchant(merchantDao.ById(storeCash.getMerchant_id()));
					storeCash.setStore(storeDao.ById(storeCash.getStore_id()));
					storeCash.setOrder(orderDao.ById(storeCash.getOrder_id()));
				}
			}
		}
		return storeCashs;
	}
	
	public List<StoreCashBean> ByStoreIdCreateTime(int storeId, Date date){
		StoreCashBean storeCash;
		List<StoreCashBean> storeCashs = storeCashDao.ByStoreIdCreateTime(storeId, date);
		if(storeCashs != null && storeCashs.size() > 0){
			for(int i = 0; i < storeCashs.size(); i++){
				storeCash = storeCashs.get(i);
				if(storeCash != null){
					storeCash.setMember(memberDao.ById(storeCash.getMember_id()));
					storeCash.setMerchant(merchantDao.ById(storeCash.getMerchant_id()));
					storeCash.setStore(storeDao.ById(storeCash.getStore_id()));
					storeCash.setOrder(orderDao.ById(storeCash.getOrder_id()));
				}
			}
		}
		return storeCashs;
	}
	
	public List<StoreCashBean> ByType(int type, int page){
		StoreCashBean storeCash;
		List<StoreCashBean> storeCashs = storeCashDao.ByType(type, page);
		if(storeCashs != null && storeCashs.size() > 0){
			for(int i = 0; i < storeCashs.size(); i++){
				storeCash = storeCashs.get(i);
				if(storeCash != null){
					storeCash.setMember(memberDao.ById(storeCash.getMember_id()));
					storeCash.setMerchant(merchantDao.ById(storeCash.getMerchant_id()));
					storeCash.setStore(storeDao.ById(storeCash.getStore_id()));
					storeCash.setOrder(orderDao.ById(storeCash.getOrder_id()));
				}
			}
		}
		return storeCashs;
		
	}
	
	public int Add(StoreCashBean storeCash){
		return storeCashDao.Add(storeCash);
	}
	
	public int Modify(StoreCashBean storeCash){
		return storeCashDao.Modify(storeCash);
	}
	
	public int Remove(int id){
		return storeCashDao.Remove(id);
	}

	
	
	
	public StoreCashDao getStoreCashDao() {
		return storeCashDao;
	}

	public void setStoreCashDao(StoreCashDao storeCashDao) {
		this.storeCashDao = storeCashDao;
	}

	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
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
