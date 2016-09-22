package com.raintr.pinshe.dao;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.raintr.pinshe.bean.GroupMessageBean;
import com.raintr.pinshe.utils.Cache;

@SuppressWarnings("deprecation")
public class GroupMessageDao extends SqlMapClientDaoSupport {
	@SuppressWarnings("unchecked")
	public List<GroupMessageBean> ByCreateTime(int groupId, Date createTime){
		String key = "groupMessage.byCreateTime" + createTime;
		if(Cache.groupMessages.containsKey(key))
	    	return Cache.groupMessages.get(key);

		GroupMessageBean groupMessage = new GroupMessageBean();
		groupMessage.setGroup_id(groupId);
		groupMessage.setCreate_time(createTime);
		List<GroupMessageBean> groupMessages = (List<GroupMessageBean>)getSqlMapClientTemplate().queryForList("groupMessage.byCreateTime", groupMessage);
		Cache.groupMessages.put(key, groupMessages);
		return groupMessages;
	}
	
	public int Add(GroupMessageBean groupMessage){
		int id = (Integer)getSqlMapClientTemplate().insert("groupMessage.add", groupMessage);
		Cache.groupMessages.clear();
		return id;
	}
	
	public int Remove(int id){
		id = (Integer)getSqlMapClientTemplate().delete("groupMessage.remove", id);
		Cache.groupMessages.clear();
		return id;
	}
}