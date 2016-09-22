package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.PVBean;
import com.raintr.pinshe.dao.PVDao;
import com.raintr.pinshe.dao.ProductDao;

public class PVService {	
	private PVDao pvDao;
    private ProductDao productDao;
    
	public List<PVBean> By(int page){
		List<PVBean> pvs = pvDao.By(page);
		for(int i = 0; i < pvs.size(); i++){
			pvs.get(i).setProduct(productDao.ById(pvs.get(i).getProduct_id()));
		}
		return pvs;
	}
	
	public PVBean ById(int id){		
		return pvDao.ById(id);
	}
	
	public PVBean ByProductId(int productId){		
		return pvDao.ByProductId(productId);
	}
	
	public int Add(PVBean pv){
		return pvDao.Add(pv);
	}
	
	public int Modify(PVBean pv){
		return pvDao.Modify(pv);
	}

	public PVDao getPvDao() {
		return pvDao;
	}

	public void setPvDao(PVDao pvDao) {
		this.pvDao = pvDao;
	}
	public ProductDao getProductDao() {
		return productDao;
	}
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
}
