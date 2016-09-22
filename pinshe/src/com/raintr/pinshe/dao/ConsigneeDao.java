package com.raintr.pinshe.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.ConsigneeBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class ConsigneeDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<ConsigneeBean> By(int page){
		String key = "consignee.by" + page;
		if(Cache.consignees.containsKey(key))
	    	return Cache.consignees.get(key);
		
		List<ConsigneeBean> consignees = (List<ConsigneeBean>)getSqlMapClientTemplate().queryForList("consignee.by", page);
		Cache.consignees.put(key, consignees);
		return consignees;
	}
	
	public ConsigneeBean ById(int id){
		String key = "consignee.byId" + id;
		if(Cache.consignee.containsKey(key))
	    	return Cache.consignee.get(key);
		
		ConsigneeBean consignee = (ConsigneeBean)getSqlMapClientTemplate().queryForObject("consignee.byId", id);
		Cache.consignee.put(key, consignee);
		return consignee;
	}
	
	@SuppressWarnings("unchecked")
	public List<ConsigneeBean> ByMemberId(int memberId){
		String key = "consignee.byMemberId" + memberId;
		if(Cache.consignees.containsKey(key))
	    	return Cache.consignees.get(key);
		
		List<ConsigneeBean> consignees = (List<ConsigneeBean>)getSqlMapClientTemplate().queryForList("consignee.byMemberId", memberId);
		Cache.consignees.put(key, consignees);
		return consignees;
	}
	
	public int Add(ConsigneeBean consignee){
		int id = (Integer)getSqlMapClientTemplate().insert("consignee.add", consignee);
		Cache.consignee.clear();
		Cache.consignees.clear();
		return id;
	}
	
	public int Modify(ConsigneeBean consignee){
		int id = (Integer)getSqlMapClientTemplate().update("consignee.modify", consignee);
		Cache.consignee.clear();
		Cache.consignees.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("consignee.remove", id);
		Cache.consignee.clear();
		Cache.consignees.clear();
		return id;
	}
}