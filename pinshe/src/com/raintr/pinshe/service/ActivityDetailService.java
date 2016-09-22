package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.ActivityDetailBean;
import com.raintr.pinshe.dao.ActivityDetailDao;
import com.raintr.pinshe.dao.OrderDao;

public class ActivityDetailService {
	private ActivityDetailDao activityDetailDao;
	private OrderDao orderDao;

	public List<ActivityDetailBean> ByActivityId(int activityId){
		ActivityDetailBean activityDetail;
		List<ActivityDetailBean> activityDetails = activityDetailDao.ByActivityId(activityId);
		if(activityDetails != null && activityDetails.size() > 0){
			for(int i = 0; i < activityDetails.size(); i++){
				activityDetail = activityDetails.get(i);
				if(activityDetail != null){
					activityDetail.setOrder(orderDao.ById(activityDetail.getOrder_id()));
				}
			}
		}
		
		return activityDetails;
	}
	
	public ActivityDetailBean ByActivityIdStoreId(int activityId, int storeId){
		ActivityDetailBean activityDetail = activityDetailDao.ByActivityIdStoreId(activityId, storeId);
		if(activityDetail != null)
			activityDetail.setOrder(orderDao.ById(activityDetail.getOrder_id()));
		
		return activityDetail;
	}
	
	public int Add(ActivityDetailBean activityDetail){
		return activityDetailDao.Add(activityDetail);
	}
	
	public int Modify(ActivityDetailBean activityDetail){
		return activityDetailDao.Modify(activityDetail);
	}
	

	public ActivityDetailDao getActivityDetailDao() {
		return activityDetailDao;
	}

	public void setActivityDetailDao(ActivityDetailDao activityDetailDao) {
		this.activityDetailDao = activityDetailDao;
	}

	public OrderDao getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
}
