package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.ActivityBean;
import com.raintr.pinshe.dao.ActivityDao;
import com.raintr.pinshe.dao.MemberDao;

public class ActivityService {
	private ActivityDao activityDao;
	private MemberDao memberDao;
	
	public List<ActivityBean> By(int page){
		ActivityBean activity;
		List<ActivityBean> activitys = activityDao.By(page);
		if(activitys != null && activitys.size() > 0){
			for(int i = 0; i < activitys.size(); i++){
				activity = activitys.get(i);
				activity.setMember(memberDao.ById(activity.getMember_id()));
				activity.setSharer(memberDao.ById(activity.getSharer_id()));
			}
		}
		
		return activitys;
	}
	
	public ActivityBean ById(int id){
		return activityDao.ById(id);
	}
	
	public ActivityBean ByMemberId(int memberId){
		return activityDao.ByMemberId(memberId);
	}
	
	public int Add(ActivityBean activity){
		return activityDao.Add(activity);
	}
	
	public int Modify(ActivityBean activity){
		return activityDao.Modify(activity);
	}
	

	public ActivityDao getActivityDao() {
		return activityDao;
	}

	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}
