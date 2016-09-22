package com.raintr.pinshe.service;

import com.raintr.pinshe.bean.HistoryBean;
import com.raintr.pinshe.dao.HistoryDao;


public class HistoryService {
	private HistoryDao historyDao;

	public HistoryBean ById(int id){
		return historyDao.ById(id);
	}
	
	public HistoryBean ByUserIdVoteId(int userId, int voteId){
		return historyDao.ByUserIdVoteId(userId, voteId);
	}
	
	public int Add(HistoryBean history){
		return historyDao.Add(history);
	}
	
	public HistoryDao getHistoryDao() {
		return historyDao;
	}

	public void setHistoryDao(HistoryDao historyDao) {
		this.historyDao = historyDao;
	}
}
