package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.ConsigneeBean;
import com.raintr.pinshe.dao.ConsigneeDao;

public class ConsigneeService {
	
	private ConsigneeDao consigneeDao;
	
	public List<ConsigneeBean> By(int page){		
		return consigneeDao.By(page);
	}
	
	public ConsigneeBean ById(int id){
		return consigneeDao.ById(id);
	}
	
	public List<ConsigneeBean> ByMemberId(int memberId){		
		return consigneeDao.ByMemberId(memberId);
	}
	
	public int Add(ConsigneeBean consignee){
		return consigneeDao.Add(consignee);
		
	}
	
	public int Modify(ConsigneeBean consignee){
		return consigneeDao.Modify(consignee);
	}

	public int Remove(int id){
		return consigneeDao.Remove(id);
	}
	
	
	public ConsigneeDao getConsigneeDao() {
		return consigneeDao;
	}

	public void setConsigneeDao(ConsigneeDao consigneeDao) {
		this.consigneeDao = consigneeDao;
	}
}
