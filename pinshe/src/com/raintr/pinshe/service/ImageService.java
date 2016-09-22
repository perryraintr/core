package com.raintr.pinshe.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.dao.ImageDao;
import com.raintr.pinshe.utils.FileGlobal;

public class ImageService {
	private String remote;
	private String local;
	
	private ImageDao imageDao;
	
	public ImageBean ById(int id) throws Exception{		
		return imageDao.ById(id);
	}
	
	public List<ImageBean> ByPostId(int postId){
		return imageDao.ByPostId(postId);
	}
	
	public List<ImageBean> ByProductId(int productId){
		return imageDao.ByProductId(productId);
	}
	
	public int Add(ImageBean image, MultipartFile file) throws Exception{
		String url = FileGlobal.AddFile(file, remote, local);
		if(url != null)
			image.setUrl(url);
		
		return imageDao.Add(image); 
	}
	
	public int Modify(ImageBean image, MultipartFile file) throws Exception{
		if(file != null && !file.isEmpty()){
			// remove
			String path = image.getUrl().replaceAll(remote, local);
			FileGlobal.RemoveFile(path);
			// add
			String url = FileGlobal.AddFile(file, remote, local);
			if(url != null)
				image.setUrl(url);
		}
		return imageDao.Modify(image);
	}
	
	public int Remove(ImageBean image) throws Exception{
		String path = image.getUrl().replaceAll(remote, local);
		FileGlobal.RemoveFile(path);
		return imageDao.Remove(image.getId());
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

	public ImageDao getImageDao() {
		return imageDao;
	}

	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}
}
