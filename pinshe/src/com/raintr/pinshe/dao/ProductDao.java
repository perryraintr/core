package com.raintr.pinshe.dao;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class ProductDao extends SqlMapClientDaoSupport {
	public ProductBean ById(int id){
		String key = "product.byId" + id;
		if(Cache.product.containsKey(key))
	    	return Cache.product.get(key);
		
		ProductBean product = (ProductBean)getSqlMapClientTemplate().queryForObject("product.byId", id);
		Cache.product.put(key, product);
		return product;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductBean> By(int page){
		String key = "product.by" + page;
		if(Cache.products.containsKey(key))
	    	return Cache.products.get(key);
		
		List<ProductBean> products = (List<ProductBean>)getSqlMapClientTemplate().queryForList("product.by", page);
		Cache.products.put(key, products);
		return products;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductBean> ByTag(int t1, int t2, int page){
		String key = "product.byTag" + t1 + t2 + page;
		if(Cache.products.containsKey(key))
	    	return Cache.products.get(key);
		
		TagBean tag = new TagBean();
		tag.setT1(t1);
		tag.setT2(t2);
		tag.setId(page);
		List<ProductBean> products = (List<ProductBean>)getSqlMapClientTemplate().queryForList("product.byTag", tag);
		Cache.products.put(key, products);
		return products;
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductBean> ByName(String name){
		String key = "product.byName" + name;
		if(Cache.products.containsKey(key))
	    	return Cache.products.get(key);
		
		List<ProductBean> products = (List<ProductBean>)getSqlMapClientTemplate().queryForList("product.byName", name);
		Cache.products.put(key, products);
		return products;
	}
	
	public int Add(ProductBean product){
		int id = (Integer)getSqlMapClientTemplate().insert("product.add", product);
		Cache.product.clear();
		Cache.products.clear();
		return id;
	}
	
	public int Modify(ProductBean product){
		int id = (Integer)getSqlMapClientTemplate().update("product.modify", product);
		Cache.product.clear();
		Cache.products.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("product.remove", id);
		Cache.product.clear();
		Cache.products.clear();
		return id;
	}
}