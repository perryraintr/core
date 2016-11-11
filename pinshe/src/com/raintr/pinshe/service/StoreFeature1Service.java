package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.StoreFeature1Bean;
import com.raintr.pinshe.dao.StoreFeature1Dao;
import com.raintr.pinshe.dao.StoreFeature1ImageDao;

public class StoreFeature1Service {	
	private StoreFeature1Dao storeFeature1Dao;
	private StoreFeature1ImageDao storeFeature1ImageDao;
	
	public StoreFeature1Bean ById(int storeId, int storeFeature1ImageId) throws Exception{		
		return storeFeature1Dao.byStoreFeature1(storeId, storeFeature1ImageId);
	}
	
	public List<StoreFeature1Bean> ByStoreId(int storeId) throws Exception{
		StoreFeature1Bean storeFeature1;
		List<StoreFeature1Bean> storeFeature1s = storeFeature1Dao.ByStoreId(storeId);
		if(storeFeature1s != null && storeFeature1s.size() > 0){
			for(int i = 0; i < storeFeature1s.size(); i++){
				storeFeature1 = storeFeature1s.get(i);
				if(storeFeature1 != null)
					storeFeature1.setStoreFeature1Image(storeFeature1ImageDao.ById(storeFeature1.getStore_feature1_image_id()));
			}
		}
		
		return storeFeature1s;
	}
	
	public int Add(StoreFeature1Bean storeFeature1) throws Exception{
		return storeFeature1Dao.Add(storeFeature1); 
	}
	
	public int Remove(int id) throws Exception{
		return storeFeature1Dao.Remove(id);
	}
	
	public int RemoveStoreId(int storeId) throws Exception{
		return storeFeature1Dao.RemoveStoreId(storeId);
	}

	public StoreFeature1Dao getStoreFeature1Dao() {
		return storeFeature1Dao;
	}

	public void setStoreFeature1Dao(StoreFeature1Dao storeFeature1Dao) {
		this.storeFeature1Dao = storeFeature1Dao;
	}

	public StoreFeature1ImageDao getStoreFeature1ImageDao() {
		return storeFeature1ImageDao;
	}

	public void setStoreFeature1ImageDao(StoreFeature1ImageDao storeFeature1ImageDao) {
		this.storeFeature1ImageDao = storeFeature1ImageDao;
	}
}
