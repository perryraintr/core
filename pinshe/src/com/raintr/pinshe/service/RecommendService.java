package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.RecommendBean;
import com.raintr.pinshe.dao.CommodityDao;
import com.raintr.pinshe.dao.CommodityImageDao;
import com.raintr.pinshe.dao.RecommendDao;
import com.raintr.pinshe.dao.StoreDao;

public class RecommendService {
	private RecommendDao recommendDao;
	private StoreDao storeDao;
	private CommodityDao commodityDao;
	private CommodityImageDao commodityImageDao;
	
	public RecommendBean ById(int id){
		return recommendDao.ById(id); 
	}
	
	public RecommendBean ByStoreIdCommodityId(int storeId, int commodityId){
		return recommendDao.ByStoreIdCommodityId(storeId, commodityId);
	}
	
	public List<RecommendBean> ByStoreId(int storeId, int page){
		RecommendBean recommend;
		CommodityBean commodity;
		List<RecommendBean> recommends = recommendDao.ByStoreId(storeId, page);
		if(recommends != null && recommends.size() > 0){
			for(int i = 0; i < recommends.size(); i++){
				recommend = recommends.get(i);
				recommend.setCommodity(commodityDao.ById(recommend.getCommodity_id()));
				commodity = recommend.getCommodity();
				if(commodity != null)
					commodity.setImages(commodityImageDao.ByCommodityId(commodity.getId()));
			}
		}
		
		return recommends;
	}
	
	public List<RecommendBean> ByCommodityId(int commodityId, int page){
		RecommendBean recommend;
		List<RecommendBean> recommends = recommendDao.ByCommodityId(commodityId, page);
		if(recommends != null && recommends.size() > 0){
			for(int i = 0; i < recommends.size(); i++){
				recommend = recommends.get(i);
				recommend.setStore(storeDao.ById(recommend.getStore_id()));
			}
		}
		
		return recommends;
	}
	
	public int Add(RecommendBean recommend){
		return recommendDao.Add(recommend); 
	}
	
	public int Modify(RecommendBean recommend){
		return recommendDao.Modify(recommend);
	}
	
	public int Remove(int id){
		return recommendDao.Remove(id);
	}

	
	
	
	public RecommendDao getRecommendDao() {
		return recommendDao;
	}

	public void setRecommendDao(RecommendDao recommendDao) {
		this.recommendDao = recommendDao;
	}

	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public CommodityDao getCommodityDao() {
		return commodityDao;
	}

	public void setCommodityDao(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}

	public CommodityImageDao getCommodityImageDao() {
		return commodityImageDao;
	}

	public void setCommodityImageDao(CommodityImageDao commodityImageDao) {
		this.commodityImageDao = commodityImageDao;
	}
}
