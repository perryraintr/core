package com.raintr.pinshe.service;

import java.util.List;

import com.raintr.pinshe.bean.ConsigneeBean;
import com.raintr.pinshe.bean.SubscriptionBean;
import com.raintr.pinshe.dao.CommodityDao;
import com.raintr.pinshe.dao.ConsigneeDao;
import com.raintr.pinshe.dao.MemberDao;
import com.raintr.pinshe.dao.SubscriptionDao;

public class SubscriptionService {
	private SubscriptionDao subscriptionDao;
	private ConsigneeDao consigneeDao;
	private CommodityDao commodityDao;
	private MemberDao memberDao;

	public List<SubscriptionBean> By(int page) {
		SubscriptionBean subscription;
		ConsigneeBean consignee;
		List<ConsigneeBean> consignees;
		List<SubscriptionBean> subscriptions = subscriptionDao.By(page);
		if(subscriptions != null && subscriptions.size() > 0){
			for(int i = 0; i < subscriptions.size(); i++){
				subscription = subscriptions.get(i);
				
				consignees = consigneeDao.ByMemberId(subscription.getMember_id());
				if(consignees != null)
				for(int j = 0; j < consignees.size(); j++){
					consignee = consignees.get(j);
					if(consignee.getStatus() == 1){
						subscription.setConsignee(consignee);
						break;
					}
				}
				
				subscription.setCommodity1(commodityDao.ById(subscription.getCommodity_id1()));
				subscription.setCommodity2(commodityDao.ById(subscription.getCommodity_id2()));
				subscription.setCommodity3(commodityDao.ById(subscription.getCommodity_id3()));
				subscription.setCommodity4(commodityDao.ById(subscription.getCommodity_id4()));
				subscription.setCommodity5(commodityDao.ById(subscription.getCommodity_id5()));
				subscription.setCommodity6(commodityDao.ById(subscription.getCommodity_id6()));
				
				subscription.setMember(memberDao.ById(subscription.getMember_id()));
			}
		}
		
		return subscriptions;
	}

	public SubscriptionBean ById(int id) {
		return subscriptionDao.ById(id);
	}
	
	public SubscriptionBean ByMemberId(int id){
		return subscriptionDao.ByMemberId(id);
	}

	public int Add(SubscriptionBean subscription) {
		return subscriptionDao.Add(subscription);
	}

	public int Modify(SubscriptionBean subscription) {
		return subscriptionDao.Modify(subscription);
	}

	public int Remove(int id) throws Exception {
		return subscriptionDao.Remove(id);
	}

	
	
	
	public SubscriptionDao getSubscriptionDao() {
		return subscriptionDao;
	}

	public void setSubscriptionDao(SubscriptionDao subscriptionDao) {
		this.subscriptionDao = subscriptionDao;
	}

	public ConsigneeDao getConsigneeDao() {
		return consigneeDao;
	}

	public void setConsigneeDao(ConsigneeDao consigneeDao) {
		this.consigneeDao = consigneeDao;
	}

	public CommodityDao getCommodityDao() {
		return commodityDao;
	}

	public void setCommodityDao(CommodityDao commodityDao) {
		this.commodityDao = commodityDao;
	}

	public MemberDao getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}