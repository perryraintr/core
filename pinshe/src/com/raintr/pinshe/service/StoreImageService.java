package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.StoreImageBean;
import com.raintr.pinshe.dao.StoreImageDao;

public class StoreImageService {
	private StoreImageDao storeImageDao;

	public StoreImageBean ById(int id){
		return storeImageDao.ById(id);
	}
	
	public List<StoreImageBean> ByStoreId(int storeId){
		return storeImageDao.ByStoreId(storeId);
	}
	
	public int Add(StoreImageBean storeImage){
		return storeImageDao.Add(storeImage);
	}
	
	public int Modify(StoreImageBean storeImage){
		return storeImageDao.Modify(storeImage);
	}
	
	public int Remove(int id){
		return storeImageDao.Remove(id);
	}

	public StoreImageDao getStoreImageDao() {
		return storeImageDao;
	}

	public void setStoreImageDao(StoreImageDao storeImageDao) {
		this.storeImageDao = storeImageDao;
	}
}