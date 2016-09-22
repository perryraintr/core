package com.raintr.pinshe.service;

import com.raintr.pinshe.bean.FeedbackBean;
import com.raintr.pinshe.dao.FeedbackDao;

public class FeedbackService {
	private FeedbackDao feedbackDao;

	public FeedbackBean ById(int id){
		return feedbackDao.ById(id); 
	}
	public int Add(FeedbackBean feedback){
		return feedbackDao.Add(feedback); 
	}
	
	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}

	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}
}
