package com.raintr.pinshe.service;

import java.util.List;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.dao.TagDao;

public class TagService {
	private TagDao tagDao;
	
	public TagBean ById(int id){	
		return tagDao.ById(id);
	}
	
	public List<TagBean> ByPostId(int postId){
		return tagDao.ByPostId(postId);
	}
	
	public List<TagBean> ByT1T2(int t1, int t2){
		return tagDao.ByT1T2(t1, t2);
	}
	
	public int Add(TagBean tag){
		return tagDao.Add(tag);
	}
	
	public int Modify(TagBean tag){
		return tagDao.Modify(tag);
	}
	
	public int Remove(int id){
		return tagDao.Remove(id);
	}
	

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}
}
