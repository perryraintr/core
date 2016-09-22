package com.raintr.pinshe.service;

import com.raintr.pinshe.bean.AdminBean;
import com.raintr.pinshe.dao.AdminDao;

public class AdminService {
	private AdminDao adminDao;

	public AdminBean ById(int id){
		return adminDao.ById(id);
	}
	
	public AdminBean ByPhone(String phone){
		return adminDao.ByPhone(phone);
	}
	
	public AdminBean ByPhonePassword(String phone, String password){
		return adminDao.ByPhonePassword(phone, password);
	}
	
	public int Add(AdminBean admin){
		return adminDao.Add(admin);
		
	}
	
	public int Modify(AdminBean admin){
		return adminDao.Modify(admin);
	}
	
	public int Remove(int id){
		return adminDao.Remove(id);
	}
	
	

	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}
}
