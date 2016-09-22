package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.dao.CommodityImageDao;

public class CommodityImageService {
	private CommodityImageDao commodityImageDao;

	public CommodityImageBean ById(int id){
		return commodityImageDao.ById(id);
	}
	
	public List<CommodityImageBean> ByCommodityId(int commodityId){
		return commodityImageDao.ByCommodityId(commodityId);
	}
	
	public int Add(CommodityImageBean commodityImage){
		return commodityImageDao.Add(commodityImage);
	}
	
	public int Modify(CommodityImageBean commodityImage){
		return commodityImageDao.Modify(commodityImage);
	}
	
	public int RemoveCommodityId(int commodityId){
		return commodityImageDao.RemoveCommodityId(commodityId);
	}

	public CommodityImageDao getCommodityImageDao() {
		return commodityImageDao;
	}

	public void setCommodityImageDao(CommodityImageDao commodityImageDao) {
		this.commodityImageDao = commodityImageDao;
	}
}