package com.raintr.pinshe.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.raintr.pinshe.bean.StoreFeature2ImageBean;
import com.raintr.pinshe.dao.StoreFeature2ImageDao;
import com.raintr.pinshe.utils.FileGlobal;

public class StoreFeature2ImageService {
	private String remote;
	private String local;
	
	private StoreFeature2ImageDao storeFeature2ImageDao;
	
	public List<StoreFeature2ImageBean> By(int page){
		return storeFeature2ImageDao.By(page);
	}
	
	public StoreFeature2ImageBean ById(int id) throws Exception{		
		return storeFeature2ImageDao.ById(id);
	}
	
	public int Add(StoreFeature2ImageBean storeFeature2Image, MultipartFile file) throws Exception{
		String url = FileGlobal.AddFile(file, remote, local);
		if(url != null)
			storeFeature2Image.setUrl(url);
		
		return storeFeature2ImageDao.Add(storeFeature2Image); 
	}
	
	public int Modify(StoreFeature2ImageBean storeFeature2Image, MultipartFile file) throws Exception{
		if(file != null && !file.isEmpty()){
			// remove
			String path = storeFeature2Image.getUrl().replaceAll(remote, local);
			FileGlobal.RemoveFile(path);
			// add
			String url = FileGlobal.AddFile(file, remote, local);
			if(url != null)
				storeFeature2Image.setUrl(url);
		}
		return storeFeature2ImageDao.Modify(storeFeature2Image);
	}
	
	public int Remove(StoreFeature2ImageBean storeFeature2Image) throws Exception{
		String path = storeFeature2Image.getUrl().replaceAll(remote, local);
		FileGlobal.RemoveFile(path);
		return storeFeature2ImageDao.Remove(storeFeature2Image.getId());
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

	public StoreFeature2ImageDao getStoreFeature2ImageDao() {
		return storeFeature2ImageDao;
	}

	public void setStoreFeature2ImageDao(StoreFeature2ImageDao storeFeature2ImageDao) {
		this.storeFeature2ImageDao = storeFeature2ImageDao;
	}
}
