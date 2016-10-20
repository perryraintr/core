package com.raintr.pinshe.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.raintr.pinshe.bean.StorePushBean;
import com.raintr.pinshe.dao.StorePushDao;
import com.raintr.pinshe.utils.FileGlobal;

public class StorePushService {
	private String remote;
	private String local;
	private StorePushDao storePushDao;
	
	public List<StorePushBean> By(int page){
		return storePushDao.By(page);
	}
	
	public List<StorePushBean> ByStoreId(int storeId){
		return storePushDao.ByStoreId(storeId);
	}
	
	public int Add(StorePushBean storePush, MultipartFile image) throws Exception{
		if(image != null && !image.isEmpty()){
			Thread.sleep(1);
			String url = FileGlobal.AddFile(image, remote, local);					
			storePush.setImage(url);
		}
		
		return storePushDao.Add(storePush);
	}
	
	public int Modify(StorePushBean storePush){
		return storePushDao.Modify(storePush);
	}
	
	public int Remove(int id) throws Exception{
		StorePushBean storePush = storePushDao.ById(id);
		if(storePush != null){
			String url = storePush.getImage().replaceAll(remote, local);
			FileGlobal.RemoveFile(url);
		}
		
		return storePushDao.Remove(id);
	}

	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public StorePushDao getStorePushDao() {
		return storePushDao;
	}

	public void setStorePushDao(StorePushDao storePushDao) {
		this.storePushDao = storePushDao;
	}	
}
