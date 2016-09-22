package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.dao.CommodityDao;
import com.raintr.pinshe.dao.CommodityImageDao;

public class CommodityService {
	private CommodityDao commodityDao;
	private CommodityImageDao commodityImageDao;
	
	public List<CommodityBean> By(int page){
		CommodityBean commodity;
		List<CommodityBean> commoditys = commodityDao.By(page);
		if(commoditys != null && commoditys.size() > 0){
			for(int i = 0; i < commoditys.size(); i++){
				commodity = commoditys.get(i);
				commodity.setImages(commodityImageDao.ByCommodityId(commodity.getId()));
			}
		}
		
		return commoditys;
	}
	
	public CommodityBean ById(int id){
		CommodityBean commodity = commodityDao.ById(id);
		commodity.setImages(commodityImageDao.ByCommodityId(commodity.getId()));
		return commodity;
	}
	
	public List<CommodityBean> ByT0(int t0){
		CommodityBean commodity;
		List<CommodityBean> commoditys = commodityDao.ByT0(t0);
		if(commoditys != null && commoditys.size() > 0){
			for(int i = 0; i < commoditys.size(); i++){
				commodity = commoditys.get(i);
				commodity.setImages(commodityImageDao.ByCommodityId(commodity.getId()));
			}
		}
		
		return commoditys;
	}
	
	public List<CommodityBean> ByT1(int t1){
		CommodityBean commodity;
		List<CommodityBean> commoditys = commodityDao.ByT1(t1);
		if(commoditys != null && commoditys.size() > 0){
			for(int i = 0; i < commoditys.size(); i++){
				commodity = commoditys.get(i);
				commodity.setImages(commodityImageDao.ByCommodityId(commodity.getId()));
			}
		}
		
		return commoditys;
	}
	
	public CommodityBean ById(int num_iid, int outer_id){
		return commodityDao.ByYZ(num_iid, outer_id);
	}
	
	public int Add(CommodityBean commodity){
		return commodityDao.Add(commodity);
	}
	
	public int Modify(CommodityBean commodity){
		return commodityDao.Modify(commodity);
	}
	
	public int Modify(int status){
		return commodityDao.Modify(status);
	}
	
	public int Remove(int id){
		return commodityDao.Remove(id);
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