package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class ImageDao extends SqlMapClientDaoSupport {
	public ImageBean ById(int id){
		String key = "image.byId" + id;
		if(Cache.image.containsKey(key))
			return Cache.image.get(key);
		
		ImageBean image = (ImageBean)getSqlMapClientTemplate().queryForObject("image.byId", id);
		Cache.image.put(key, image);
		return image;
	}
	
	@SuppressWarnings("unchecked")
	public List<ImageBean> ByPostId(int postId){
		String key = "image.byPostId" + postId;
		if(Cache.images.containsKey(key))
	    	return Cache.images.get(key);
		
		List<ImageBean> images = (List<ImageBean>)getSqlMapClientTemplate().queryForList("image.byPostId", postId);
		Cache.images.put(key, images);
		return images;
	}
	
	@SuppressWarnings("unchecked")
	public List<ImageBean> ByProductId(int productId){
		String key = "image.byProductId" + productId;
		if(Cache.images.containsKey(key))
	    	return Cache.images.get(key);
		
		List<ImageBean> images = (List<ImageBean>)getSqlMapClientTemplate().queryForList("image.byProductId", productId);
		Cache.images.put(key, images);
		return images;
	}
	
	public int Add(ImageBean image){
		int id = (Integer)getSqlMapClientTemplate().insert("image.add", image);
		Cache.image.clear();
		Cache.images.clear();
		return id;
	}
	
	public int Modify(ImageBean image){
		int id = (Integer)getSqlMapClientTemplate().update("image.modify", image);
		Cache.image.clear();
		Cache.images.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("image.remove", id);
		Cache.image.clear();
		Cache.images.clear();
		return id;
	}
}