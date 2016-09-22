package com.raintr.pinshe.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.dao.ImageDao;
import com.raintr.pinshe.dao.ProductDao;
import com.raintr.pinshe.dao.TagDao;
import com.raintr.pinshe.dao.GroupDao;
import com.raintr.pinshe.dao.UserDao;
import com.raintr.pinshe.utils.FileGlobal;

public class ProductService {
	private String domain;
	private String remote;
	private String local;
	
	private UserDao userDao;
	private TagDao tagDao;
	private ProductDao productDao;
	private ImageDao imageDao;
	private GroupDao groupDao;
	
	public ProductBean ById(int id) throws Exception{	
		TagBean tag;
		ProductBean product = productDao.ById(id);
		if(product != null){
			product.setTags(tagDao.ByProductId(product.getId()));			
			product.setImages(imageDao.ByProductId(product.getId()));
			
			if(product.getTags() != null){
				for(int i = 0; i < 	product.getTags().size(); i++){
					tag = product.getTags().get(i);
					if(tag != null)
						tag.setGroup(groupDao.ById(tag.getT2()));
				}
			}
			
			return product;
		}
		
		return null;
	}
	
	public List<ProductBean> By(int page){
		return productDao.By(page);
	}
	
	public List<ProductBean> ByTag(int t1, int t2, int page){
		return productDao.ByTag(t1, t2, page);
	}
	
	public List<ProductBean> ByName(String name){
		return productDao.ByName(name);
	}
	
	public int Add(ProductBean product, List<MultipartFile> files) throws Exception{
		String url = null;
		ImageBean image;
		MultipartFile file;
		List<ImageBean> images = new ArrayList<ImageBean>();
		if(files != null && files.size() > 0){
			for(int i = 0; i < files.size(); i++){
				file = files.get(i);
				if(file != null && !file.isEmpty()){
					Thread.sleep(1);
					url = FileGlobal.AddFile(file, remote, local);					
					image = new ImageBean();
					image.setUrl(url);
					image.setCreate_time(new Date());
					image.setModify_time(new Date());
					images.add(image);
				}
			}
		}

		if(images != null && images.size() > 0)
			product.setImage(images.get(0).getUrl());
		
		int productId = productDao.Add(product);
		
		if(images != null && images.size() > 0){
			for(int i = 0; i < images.size(); i++){
				image = images.get(i);
				image.setProduct_id(productId);
				imageDao.Add(image);
			}
		}
		
		return productId;
	}
	
	public int Modify(ProductBean product, List<MultipartFile> files) throws Exception{
		String url;
		ImageBean image;
		MultipartFile file;
		List<ImageBean> images = product.getImages();

		if(images == null)
			images = new ArrayList<ImageBean>();
		
		if(files != null && files.size() > 0){
			// rid of waste others
			if(images.size() > files.size()){
				for(int i = images.size() - 1; i > files.size() - 1; i--){
					imageDao.Remove(images.get(i).getId());
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
						url = FileGlobal.AddFile(file, remote, local);

						image.setUrl(url);
						imageDao.Modify(image);
					}else{
						url = FileGlobal.AddFile(file, remote, local);
						
						image = new ImageBean();
						image.setProduct_id(product.getId());
						image.setUrl(url);
						image.setCreate_time(new Date());
						image.setModify_time(new Date());
						images.add(image);
						imageDao.Add(image);
					}
				}
			}
		}
		
		if(images != null && images.size() > 0)
			product.setImage(images.get(0).getUrl());

		
		return productDao.Modify(product);
	}
	
	public int Remove(ProductBean product) throws Exception{
		ImageBean image;
		
		String url = product.getImage().replaceAll(remote, local);
		FileGlobal.RemoveFile(url);
		
		// remove image url
		List<ImageBean> images = product.getImages();
		if(images != null && images.size() > 0){
			for(int i = 0; i < images.size(); i++){
				// remove
				image = images.get(i);
				url = image.getUrl().replaceAll(remote, local);
				if(url != null)
					FileGlobal.RemoveFile(url);
				
				imageDao.Remove(image.getId());
			}
		}
		
		return productDao.Remove(product.getId());
	}
	
	public String GetUrl(int id, String url) throws UnsupportedEncodingException{
		return String.format("%s/pv.a?pid=%d&url=%s", domain, id, URLEncoder.encode(url, "utf-8"));
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	public ProductDao getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public ImageDao getImageDao() {
		return imageDao;
	}

	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
