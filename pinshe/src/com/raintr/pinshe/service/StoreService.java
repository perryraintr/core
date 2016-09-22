package com.raintr.pinshe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreImageBean;
import com.raintr.pinshe.dao.StoreDao;
import com.raintr.pinshe.dao.StoreImageDao;
import com.raintr.pinshe.utils.FileGlobal;

public class StoreService {
	private String remote;
	private String local;
	
	private StoreDao storeDao;
	private StoreImageDao storeImageDao;
	
	public List<StoreBean> By(int page){
		return storeDao.By(page);
	}
	
	public StoreBean ById(int id){
		StoreBean store = storeDao.ById(id);
		store.setImages(storeImageDao.ByStoreId(store.getId()));
		return store;
	}
	
	public StoreBean ByMemberId(int memberId){
		return storeDao.ByMemberId(memberId);
	}
	
	public int Add(StoreBean store, MultipartFile avatar, List<MultipartFile> files) throws Exception{
		String url = null;
		StoreImageBean image;
		MultipartFile file;
		
		if(avatar != null && !avatar.isEmpty()){
			Thread.sleep(1);
			url = FileGlobal.AddFile(avatar, remote, local);					
			store.setAvatar(url);
		}
		
		List<StoreImageBean> images = new ArrayList<StoreImageBean>();
		if(files != null && files.size() > 0){
			for(int i = 0; i < files.size(); i++){
				file = files.get(i);
				if(file != null && !file.isEmpty()){
					Thread.sleep(1);
					url = FileGlobal.AddFile(file, remote, local);					
					image = new StoreImageBean();
					image.setUrl(url);
					image.setCreate_time(new Date());
					image.setModify_time(new Date());
					images.add(image);
				}
			}
		}

		if(images != null && images.size() > 0)
			store.setImage(images.get(0).getUrl());
		
		int storeId = storeDao.Add(store); 
		
		if(images != null && images.size() > 0){
			for(int i = 0; i < images.size(); i++){
				image = images.get(i);
				image.setStore_id(storeId);
				storeImageDao.Add(image);
			}
		}
		
		return storeId;
	}
	
	public int Modify(StoreBean store, MultipartFile avatar, List<MultipartFile> files) throws Exception{
		String url;
		StoreImageBean image;
		MultipartFile file;
		
		if(avatar != null && !avatar.isEmpty()){
			url = store.getAvatar().replaceAll(remote, local);
			FileGlobal.RemoveFile(url);
			Thread.sleep(1);
			url = FileGlobal.AddFile(avatar, remote, local);					
			store.setAvatar(url);
		}
		
		List<StoreImageBean> images = store.getImages();
		if(images == null)
			images = new ArrayList<StoreImageBean>(); 
		
		if(files != null && files.size() > 0){
			// rid of waste others
			if(images.size() > files.size()){
				for(int i = images.size() - 1; i > files.size() - 1; i--){
					storeImageDao.Remove(images.get(i).getId());
					images.remove(i);
				}
			}
			
			for(int i = 0; i < files.size(); i++){
				file = files.get(i);
				if(file != null && !file.isEmpty()){
					if(images != null && images.size() > i){
						image = images.get(i);
						// remove
						url = image.getUrl().replaceAll(remote, local);
						FileGlobal.RemoveFile(url);
						// add
						Thread.sleep(1);
						url = FileGlobal.AddFile(file, remote, local);

						image.setUrl(url);
						storeImageDao.Modify(image);
					}else{
						Thread.sleep(1);
						url = FileGlobal.AddFile(file, remote, local);
						
						image = new StoreImageBean();
						image.setStore_id(store.getId());
						image.setUrl(url);
						image.setCreate_time(new Date());
						image.setModify_time(new Date());
						images.add(image);
						storeImageDao.Add(image);
					}
				}
			}
		}
		
		if(images != null && images.size() > 0)
			store.setImage(images.get(0).getUrl());
		
		return storeDao.Modify(store);
	}
	
	public int Remove(StoreBean store) throws Exception{
		StoreImageBean image;
		
		String url = store.getAvatar().replaceAll(remote, local);
		FileGlobal.RemoveFile(url);
		
		url = store.getImage().replaceAll(remote, local);
		FileGlobal.RemoveFile(url);
		
		// remove image url
		List<StoreImageBean> images = store.getImages();
		if(images != null && images.size() > 0){
			for(int i = 0; i < images.size(); i++){
				// remove
				image = images.get(i);
				url = image.getUrl().replaceAll(remote, local);
				if(url != null)
					FileGlobal.RemoveFile(url);
				
				storeImageDao.Remove(image.getId());
			}
		}
		
		return storeDao.Remove(store.getId());
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

	public StoreDao getStoreDao() {
		return storeDao;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public StoreImageDao getStoreImageDao() {
		return storeImageDao;
	}

	public void setStoreImageDao(StoreImageDao storeImageDao) {
		this.storeImageDao = storeImageDao;
	}
}