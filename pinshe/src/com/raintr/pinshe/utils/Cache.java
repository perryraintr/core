package com.raintr.pinshe.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.raintr.pinshe.bean.ActivityBean;
import com.raintr.pinshe.bean.ActivityDetailBean;
import com.raintr.pinshe.bean.AdBean;
import com.raintr.pinshe.bean.AdminBean;
import com.raintr.pinshe.bean.CartBean;
import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.CommodityBean;
import com.raintr.pinshe.bean.CommodityImageBean;
import com.raintr.pinshe.bean.ConsigneeBean;
import com.raintr.pinshe.bean.CouponBean;
import com.raintr.pinshe.bean.CouponMemberBean;
import com.raintr.pinshe.bean.FavoriteBean;
import com.raintr.pinshe.bean.FeedbackBean;
import com.raintr.pinshe.bean.GroupMemberBean;
import com.raintr.pinshe.bean.GroupMessageBean;
import com.raintr.pinshe.bean.HistoryBean;
import com.raintr.pinshe.bean.HitBean;
import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.bean.MemberBean;
import com.raintr.pinshe.bean.MerchantBean;
import com.raintr.pinshe.bean.MessageBean;
import com.raintr.pinshe.bean.OrderBean;
import com.raintr.pinshe.bean.OrderDetailBean;
import com.raintr.pinshe.bean.PVBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.ProductBean;
import com.raintr.pinshe.bean.PublishBean;
import com.raintr.pinshe.bean.RecommendBean;
import com.raintr.pinshe.bean.StoreBean;
import com.raintr.pinshe.bean.StoreCashBean;
import com.raintr.pinshe.bean.StoreCommentBean;
import com.raintr.pinshe.bean.StoreImageBean;
import com.raintr.pinshe.bean.StoreMemberBean;
import com.raintr.pinshe.bean.SubscriptionBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.bean.VoteBean;
import com.raintr.pinshe.bean.WishBean;

public class Cache {
	public static Map<String, ActivityBean> activity = new HashMap<String, ActivityBean>();
	public static Map<String, List<ActivityBean>> activitys = new HashMap<String, List<ActivityBean>>();
	
	public static Map<String, ActivityDetailBean> activityDetail = new HashMap<String, ActivityDetailBean>();
	public static Map<String, List<ActivityDetailBean>> activityDetails = new HashMap<String, List<ActivityDetailBean>>();
	
	public static Map<String, AdBean> ad = new HashMap<String, AdBean>();
	public static Map<String, List<AdBean>> ads = new HashMap<String, List<AdBean>>();
	
	public static Map<String, AdminBean> admin = new HashMap<String, AdminBean>();
	
	public static Map<String, CartBean> cart = new HashMap<String, CartBean>();
	public static Map<String, List<CartBean>> carts = new HashMap<String, List<CartBean>>();
	
	public static Map<String, CommentBean> comment = new HashMap<String, CommentBean>();
	public static Map<String, List<CommentBean>> comments = new HashMap<String, List<CommentBean>>();
	
	
	public static Object commodityObject = new Object();
	public static Map<String, CommodityBean> commodity = new HashMap<String, CommodityBean>();
	public static Map<String, List<CommodityBean>> commoditys = new HashMap<String, List<CommodityBean>>();
	
	public static Map<String, CommodityImageBean> commodityImage = new HashMap<String, CommodityImageBean>();
	public static Map<String, List<CommodityImageBean>> commodityImages = new HashMap<String, List<CommodityImageBean>>();
	
	public static Map<String, ConsigneeBean> consignee = new HashMap<String, ConsigneeBean>();
	public static Map<String, List<ConsigneeBean>> consignees = new HashMap<String, List<ConsigneeBean>>();
	
	public static Map<String, CouponBean> coupon = new HashMap<String, CouponBean>();
	public static Map<String, List<CouponBean>> coupons = new HashMap<String, List<CouponBean>>();
	
	public static Map<String, CouponMemberBean> couponMember = new HashMap<String, CouponMemberBean>();
	public static Map<String, List<CouponMemberBean>> couponMembers = new HashMap<String, List<CouponMemberBean>>();
	
	public static Map<String, FavoriteBean> favorite = new HashMap<String, FavoriteBean>();
	
	public static Map<String, FeedbackBean> feedback = new HashMap<String, FeedbackBean>();
	
	public static Map<String, GroupBean> group = new HashMap<String, GroupBean>();
	public static Map<String, List<GroupBean>> groups = new HashMap<String, List<GroupBean>>();
	
	public static Map<String, GroupMemberBean> groupMember = new HashMap<String, GroupMemberBean>();
	public static Map<String, List<GroupMemberBean>> groupMembers = new HashMap<String, List<GroupMemberBean>>();
	
	public static Map<String, List<GroupMessageBean>> groupMessages = new HashMap<String, List<GroupMessageBean>>();
	
	public static Map<String, HistoryBean> history = new HashMap<String, HistoryBean>();
	
	public static Map<String, HitBean> hit = new HashMap<String, HitBean>();
	public static Map<String, List<HitBean>> hits = new HashMap<String, List<HitBean>>();
	
	public static Map<String, ImageBean> image = new HashMap<String, ImageBean>();
	public static Map<String, List<ImageBean>> images = new HashMap<String, List<ImageBean>>();
	
	public static Object memberObject = new Object();
	public static Map<String, MemberBean> member = new HashMap<String, MemberBean>();
	public static Map<String, List<MemberBean>> members = new HashMap<String, List<MemberBean>>();

	public static Object merchantObject = new Object();
	public static Map<String, MerchantBean> merchant = new HashMap<String, MerchantBean>();
	public static Map<String, List<MerchantBean>> merchants = new HashMap<String, List<MerchantBean>>();
	
	public static Map<String, MessageBean> message = new HashMap<String, MessageBean>();
	public static Map<String, List<MessageBean>> messages = new HashMap<String, List<MessageBean>>();
	
	public static Map<String, OrderBean> order = new HashMap<String, OrderBean>();
	public static Map<String, List<OrderBean>> orders = new HashMap<String, List<OrderBean>>();
	
	public static Map<String, OrderDetailBean> orderDetail = new HashMap<String, OrderDetailBean>();
	public static Map<String, List<OrderDetailBean>> orderDetails = new HashMap<String, List<OrderDetailBean>>();
	
	public static Map<String, PostBean> post = new HashMap<String, PostBean>();
	public static Map<String, List<PostBean>> posts = new HashMap<String, List<PostBean>>();
	
	public static Map<String, ProductBean> product = new HashMap<String, ProductBean>();
	public static Map<String, List<ProductBean>> products = new HashMap<String, List<ProductBean>>();
	
	public static Map<String, PublishBean> publish = new HashMap<String, PublishBean>();
	public static Map<String, List<PublishBean>> publishs = new HashMap<String, List<PublishBean>>();
	
	public static Map<String, PVBean> pv = new HashMap<String, PVBean>();
	public static Map<String, List<PVBean>> pvs = new HashMap<String, List<PVBean>>();
	
	public static Map<String, RecommendBean> recommend = new HashMap<String, RecommendBean>();
	public static Map<String, List<RecommendBean>> recommends = new HashMap<String, List<RecommendBean>>();
	
	public static Map<String, StoreCommentBean> storeComment = new HashMap<String, StoreCommentBean>();
	public static Map<String, List<StoreCommentBean>> storeComments = new HashMap<String, List<StoreCommentBean>>();
	
	public static Object storeObject = new Object();
	public static Map<String, StoreBean> store = new HashMap<String, StoreBean>();
	public static Map<String, List<StoreBean>> stores = new HashMap<String, List<StoreBean>>();
	
	public static Map<String, StoreCashBean> storeCash = new HashMap<String, StoreCashBean>();
	public static Map<String, List<StoreCashBean>> storeCashs = new HashMap<String, List<StoreCashBean>>();
	
	public static Map<String, StoreImageBean> storeImage = new HashMap<String, StoreImageBean>();
	public static Map<String, List<StoreImageBean>> storeImages = new HashMap<String, List<StoreImageBean>>();
	
	public static Map<String, StoreMemberBean> storeMember = new HashMap<String, StoreMemberBean>();
	public static Map<String, List<StoreMemberBean>> storeMembers = new HashMap<String, List<StoreMemberBean>>();
	
	public static Map<String, SubscriptionBean> subscription = new HashMap<String, SubscriptionBean>();
	public static Map<String, List<SubscriptionBean>> subscriptions = new HashMap<String, List<SubscriptionBean>>();
	
	public static Map<String, TagBean> tag = new HashMap<String, TagBean>();
	public static Map<String, List<TagBean>> tags = new HashMap<String, List<TagBean>>();

	public static Map<String, UserBean> user = new HashMap<String, UserBean>();
	
	public static Map<String, VoteBean> vote = new HashMap<String, VoteBean>();
	public static Map<String, List<VoteBean>> votes = new HashMap<String, List<VoteBean>>();
	
	public static Map<String, WishBean> wish = new HashMap<String, WishBean>();
	public static Map<String, List<WishBean>> wishs = new HashMap<String, List<WishBean>>();
	
	
	public static Map<String, String> phones = new HashMap<String, String>();
	
	
	
	public static Date date;
	public static Object objectToken = new Object();
	public static String token = null;	
}
