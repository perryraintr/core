package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.StoreMemberBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class StoreMemberDao extends SqlMapClientDaoSupport {
	public StoreMemberBean ById(int id){
		String key = "storeMember.byId" + id;
		if(Cache.storeMember.containsKey(key))
	    	return Cache.storeMember.get(key);
		
		StoreMemberBean storeMember = (StoreMemberBean)getSqlMapClientTemplate().queryForObject("storeMember.byId", id);
		Cache.storeMember.put(key, storeMember);
		return storeMember;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreMemberBean> ByStoreId(int storeId){
		String key = "storeMember.byStoreId" + storeId;
		if(Cache.storeMembers.containsKey(key))
	    	return Cache.storeMembers.get(key);

		List<StoreMemberBean> storeMembers = (List<StoreMemberBean>)getSqlMapClientTemplate().queryForList("storeMember.byStoreId", storeId);
		Cache.storeMembers.put(key, storeMembers);
		return storeMembers;
	}
	
	@SuppressWarnings("unchecked")
	public List<StoreMemberBean> ByMerchantId(int memberId){
		String key = "storeMember.byMerchantId" + memberId;
		if(Cache.storeMembers.containsKey(key))
	    	return Cache.storeMembers.get(key);

		List<StoreMemberBean> storeMembers = (List<StoreMemberBean>)getSqlMapClientTemplate().queryForList("storeMember.byMerchantId", memberId);
		Cache.storeMembers.put(key, storeMembers);
		return storeMembers;
	}
	
	public StoreMemberBean ByStoreIdMerchantId(int storeId, int merchantId){
		String key = "storeMember.byStoreIdMerchantId" + storeId + merchantId;
		if(Cache.storeMember.containsKey(key))
	    	return Cache.storeMember.get(key);
		
		StoreMemberBean storeMember = new StoreMemberBean();
		storeMember.setStore_id(storeId);
		storeMember.setMerchant_id(merchantId);
		storeMember = (StoreMemberBean)getSqlMapClientTemplate().queryForObject("storeMember.byStoreIdMerchantId", storeMember);
		Cache.storeMember.put(key, storeMember);
		return storeMember;
	}
	
	public int Add(StoreMemberBean storeMember){
		int id = (Integer)getSqlMapClientTemplate().insert("storeMember.add", storeMember);
		Cache.storeMember.clear();
		Cache.storeMembers.clear();
		return id;
	}
	
	public int Modify(StoreMemberBean storeMember){
		int id = (Integer)getSqlMapClientTemplate().update("storeMember.modify", storeMember);
		Cache.storeMember.clear();
		Cache.storeMembers.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("storeMember.remove", id);
		Cache.storeMember.clear();
		Cache.storeMembers.clear();
		return id;
	}
}