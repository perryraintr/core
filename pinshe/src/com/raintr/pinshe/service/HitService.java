package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.HitBean;
import com.raintr.pinshe.dao.HitDao;

public class HitService {	
	private HitDao hitDao;
    
	public List<HitBean> By(int page){
		return  hitDao.By(page);
	}

	public HitBean ByName(String name){		
		return hitDao.ByName(name);
	}
	
	public int Add(HitBean hit){
		return hitDao.Add(hit);
	}
	
	public int Modify(HitBean hit){
		return hitDao.Modify(hit);
	}
	
	public int Remove(int id){
		return hitDao.Remove(id);
	}

	public HitDao getHitDao() {
		return hitDao;
	}

	public void setHitDao(HitDao hitDao) {
		this.hitDao = hitDao;
	}
}
