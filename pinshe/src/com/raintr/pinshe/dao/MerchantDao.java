package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class MerchantDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<MerchantBean> By(int page){
		String key = "merchant.by" + page;
		if(Cache.merchants.containsKey(key))
	    	return Cache.merchants.get(key);
		
		List<MerchantBean> merchants = (List<MerchantBean>)getSqlMapClientTemplate().queryForList("merchant.by", page);
		Cache.merchants.put(key, merchants);
		return merchants;
	}
	
	public MerchantBean ById(int id){
		String key = "merchant.byId" + id;
		if(Cache.merchant.containsKey(key)){
			synchronized (Cache.merchantObject){
				return Cache.merchant.get(key);
			}
		}
		
		MerchantBean merchant = (MerchantBean)getSqlMapClientTemplate().queryForObject("merchant.byId", id);
		Cache.merchant.put(key, merchant);
		return merchant;
	}
	
	public MerchantBean ByWechatId(String wechatId){
		String key = "merchant.byWechatId" + wechatId;
		if(Cache.merchant.containsKey(key))
	    	return Cache.merchant.get(key);
		
		MerchantBean merchant = (MerchantBean)getSqlMapClientTemplate().queryForObject("merchant.byWechatId", wechatId);
		Cache.merchant.put(key, merchant);
		return merchant;
	}
	
	public MerchantBean ByPhone(String phone){
		String key = "merchant.byPhone" + phone;
		if(Cache.merchant.containsKey(key))
	    	return Cache.merchant.get(key);
		
		MerchantBean merchant = (MerchantBean)getSqlMapClientTemplate().queryForObject("merchant.byPhone", phone);
		Cache.merchant.put(key, merchant);
		return merchant;
	}
	
	public MerchantBean ByPhonePassword(String phone, String password){
		String key = "merchant.byPhonePassword" + phone + password;
		if(Cache.merchant.containsKey(key))
	    	return Cache.merchant.get(key);
		
		MerchantBean merchant = new MerchantBean();
		merchant.setPhone(phone);
		merchant.setPassword(password);
		
		merchant = (MerchantBean)getSqlMapClientTemplate().queryForObject("merchant.byPhonePassword", merchant);
		Cache.merchant.put(key, merchant);
		return merchant;
	}
	
	
	public int Add(MerchantBean merchant){
		int id = (Integer)getSqlMapClientTemplate().insert("merchant.add", merchant);
		synchronized (Cache.merchantObject){
			Cache.merchant.clear();
			Cache.merchants.clear();
		}
		return id;
	}
	
	public int Modify(MerchantBean merchant){
		int id = (Integer)getSqlMapClientTemplate().update("merchant.modify", merchant);
		synchronized (Cache.merchantObject){
			Cache.merchant.clear();
			Cache.merchants.clear();
		}
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("merchant.remove", id);
		synchronized (Cache.merchantObject){
			Cache.merchant.clear();
			Cache.merchants.clear();
		}
		return id;
	}
}