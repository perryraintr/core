package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.dao.MerchantDao;

public class MerchantService {
	
	private MerchantDao merchantDao;
	
	public List<MerchantBean> By(int page) throws Exception{		
		return merchantDao.By(page);
	}
	
	public MerchantBean ById(int id){
		return merchantDao.ById(id);
	}
	
	public MerchantBean ByWechatId(String wechatId){		
		return merchantDao.ByWechatId(wechatId);
	}
	
	public MerchantBean ByPhone(String phone){
		return merchantDao.ByPhone(phone);
	}
	
	public MerchantBean ByPhonePassword(String phone, String password){
		return merchantDao.ByPhonePassword(phone, password);
	}
	
	public int Add(MerchantBean merchant){
		return merchantDao.Add(merchant);
		
	}
	
	public int Modify(MerchantBean merchant){
		return merchantDao.Modify(merchant);
	}
	
	public int Remove(int id){
		return merchantDao.Remove(id);
	}
	
	

	public MerchantDao getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(MerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}
}
