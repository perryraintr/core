package com.raintr.pinshe.service;

import java.util.List;
import com.raintr.pinshe.bean.StorePaymentBean;
import com.raintr.pinshe.dao.StorePaymentDao;

public class StorePaymentService {	
	private StorePaymentDao storePaymentDao;
	
	public StorePaymentBean ById(int id) throws Exception{		
		return storePaymentDao.ById(id);
	}
	
	public List<StorePaymentBean> ByStoreId(int storeId){
		return storePaymentDao.ByStoreId(storeId);
	}
	
	public int Add(StorePaymentBean storePayment) throws Exception{
		return storePaymentDao.Add(storePayment); 
	}
	
	public int Modify(StorePaymentBean storePayment) throws Exception{
		return storePaymentDao.Modify(storePayment);
	}
	
	public int Remove(int id) throws Exception{
		return storePaymentDao.Remove(id);
	}

	public StorePaymentDao getStorePaymentDao() {
		return storePaymentDao;
	}

	public void setStorePaymentDao(StorePaymentDao storePaymentDao) {
		this.storePaymentDao = storePaymentDao;
	}
}
