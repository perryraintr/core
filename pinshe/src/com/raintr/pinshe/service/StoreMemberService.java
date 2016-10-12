package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.StoreMemberBean;
import com.raintr.pinshe.dao.MerchantDao;
import com.raintr.pinshe.dao.StoreDao;
import com.raintr.pinshe.dao.StoreMemberDao;

public class StoreMemberService {
	private MerchantDao merchantDao;
	private StoreDao storeDao;
	private StoreMemberDao storeMemberDao;
	
	public StoreMemberBean ById(int id){
		return storeMemberDao.ById(id); 
	}
	
	public List<StoreMemberBean> ByStoreId(int storeId) throws Exception{
		StoreMemberBean storeMember;
		List<StoreMemberBean> storeMembers = storeMemberDao.ByStoreId(storeId);
		if(storeMembers != null && storeMembers.size() > 0){
			if(storeMembers != null && storeMembers.size() > 0){
				for(int i = 0; i < storeMembers.size(); i++){
					storeMember = storeMembers.get(i);
					storeMember.setStore(storeDao.ById(storeMember.getStore_id()));
					storeMember.setMerchant(merchantDao.ById(storeMember.getMerchant_id()));
				}
			}
			
			return storeMembers;
		}
		
		return null;
	}
	
	public List<StoreMemberBean> ByMerchantId(int memberId){
		StoreMemberBean storeMember;
		List<StoreMemberBean> storeMembers = storeMemberDao.ByMerchantId(memberId);
		if(storeMembers != null && storeMembers.size() > 0){
			if(storeMembers != null && storeMembers.size() > 0){
				for(int i = 0; i < storeMembers.size(); i++){
					storeMember = storeMembers.get(i);
					storeMember.setStore(storeDao.ById(storeMember.getStore_id()));
					storeMember.setMerchant(merchantDao.ById(storeMember.getMerchant_id()));
				}
			}
			
			return storeMembers;
		}
		
		return null;
	}
	
	public StoreMemberBean ByStoreIdMerchantId(int storeId, int memberId){
		return storeMemberDao.ByStoreIdMerchantId(storeId, memberId);
	}
	
	public int Add(StoreMemberBean storeMember){
		return storeMemberDao.Add(storeMember); 
	}
	
	public int Modify(StoreMemberBean storeMember){
		return storeMemberDao.Modify(storeMember); 
	}
	
	public int Remove(int id){
		return storeMemberDao.Remove(id); 
	}

	
	


	public MerchantDao getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(MerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}

	public StoreMemberDao getStoreMemberDao() {
		return storeMemberDao;
	}

	public void setStoreMemberDao(StoreMemberDao storeMemberDao) {
		this.storeMemberDao = storeMemberDao;
	}

	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}
}
