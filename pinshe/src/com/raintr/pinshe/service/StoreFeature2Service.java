package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.StoreFeature2Bean;
import com.raintr.pinshe.dao.StoreFeature2Dao;
import com.raintr.pinshe.dao.StoreFeature2ImageDao;

public class StoreFeature2Service {	
	private StoreFeature2Dao storeFeature2Dao;
	private StoreFeature2ImageDao storeFeature2ImageDao;
	
	public StoreFeature2Bean ById(int storeId, int storeFeature2ImageId) throws Exception{		
		return storeFeature2Dao.byStoreFeature2(storeId, storeFeature2ImageId);
	}
	
	public List<StoreFeature2Bean> ByStoreId(int storeId) throws Exception{
		StoreFeature2Bean storeFeature2;
		List<StoreFeature2Bean> storeFeature2s = storeFeature2Dao.ByStoreId(storeId);
		if(storeFeature2s != null && storeFeature2s.size() > 0){
			for(int i = 0; i < storeFeature2s.size(); i++){
				storeFeature2 = storeFeature2s.get(i);
				if(storeFeature2 != null)
					storeFeature2.setStoreFeature2Image(storeFeature2ImageDao.ById(storeFeature2.getStore_feature2_image_id()));
			}
		}
		
		return storeFeature2s;
	}
	
	public int Add(StoreFeature2Bean storeFeature2) throws Exception{
		return storeFeature2Dao.Add(storeFeature2); 
	}
	
	public int Remove(int id) throws Exception{
		return storeFeature2Dao.Remove(id);
	}
	
	public int RemoveStoreId(int storeId) throws Exception{
		return storeFeature2Dao.RemoveStoreId(storeId);
	}

	public StoreFeature2Dao getStoreFeature2Dao() {
		return storeFeature2Dao;
	}

	public void setStoreFeature2Dao(StoreFeature2Dao storeFeature2Dao) {
		this.storeFeature2Dao = storeFeature2Dao;
	}

	public StoreFeature2ImageDao getStoreFeature2ImageDao() {
		return storeFeature2ImageDao;
	}

	public void setStoreFeature2ImageDao(StoreFeature2ImageDao storeFeature2ImageDao) {
		this.storeFeature2ImageDao = storeFeature2ImageDao;
	}
}
