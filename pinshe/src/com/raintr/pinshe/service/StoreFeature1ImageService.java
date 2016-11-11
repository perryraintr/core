package com.raintr.pinshe.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.raintr.pinshe.bean.StoreFeature1ImageBean;
import com.raintr.pinshe.dao.StoreFeature1ImageDao;
import com.raintr.pinshe.utils.FileGlobal;

public class StoreFeature1ImageService {
	private String remote;
	private String local;
	
	private StoreFeature1ImageDao storeFeature1ImageDao;
	
	public List<StoreFeature1ImageBean> By(int page){
		return storeFeature1ImageDao.By(page);
	}
	
	public StoreFeature1ImageBean ById(int id) throws Exception{		
		return storeFeature1ImageDao.ById(id);
	}
	
	public int Add(StoreFeature1ImageBean storeFeature1Image, MultipartFile file) throws Exception{
		String url = FileGlobal.AddFile(file, remote, local);
		if(url != null)
			storeFeature1Image.setUrl(url);
		
		return storeFeature1ImageDao.Add(storeFeature1Image); 
	}
	
	public int Modify(StoreFeature1ImageBean storeFeature1Image, MultipartFile file) throws Exception{
		if(file != null && !file.isEmpty()){
			// remove
			String path = storeFeature1Image.getUrl().replaceAll(remote, local);
			FileGlobal.RemoveFile(path);
			// add
			String url = FileGlobal.AddFile(file, remote, local);
			if(url != null)
				storeFeature1Image.setUrl(url);
		}
		return storeFeature1ImageDao.Modify(storeFeature1Image);
	}
	
	public int Remove(StoreFeature1ImageBean storeFeature1Image) throws Exception{
		String path = storeFeature1Image.getUrl().replaceAll(remote, local);
		FileGlobal.RemoveFile(path);
		return storeFeature1ImageDao.Remove(storeFeature1Image.getId());
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

	public StoreFeature1ImageDao getStoreFeature1ImageDao() {
		return storeFeature1ImageDao;
	}

	public void setStoreFeature1ImageDao(StoreFeature1ImageDao storeFeature1ImageDao) {
		this.storeFeature1ImageDao = storeFeature1ImageDao;
	}
}
