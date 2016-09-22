package com.raintr.pinshe.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.raintr.pinshe.bean.UserBean;
import com.raintr.pinshe.dao.UserDao;
import com.raintr.pinshe.utils.FileGlobal;
import com.raintr.pinshe.utils.NetGlobal;
import com.raintr.pinshe.utils.StringGlobal;

public class UserService {
	private String remote;
	private String local;
	
	private UserDao userDao;
	
	private String org_name;
	private String app_name;
	private TokenService tokenService;
	
	private String appid;
	private String appsecret;
	
	private String url;
	
	public UserBean ById(int id) throws Exception{		
		return userDao.ById(id);
	}
	
	public UserBean ByWechatGuid(String wechat_guid) throws Exception{		
		return userDao.ByWechatGuid(wechat_guid);
	}
	
	public int Add(UserBean user, MultipartFile file) throws Exception{
		if(file != null && !file.isEmpty()){
			String image = FileGlobal.AddFile(file, remote, local);
			if(image != null)
				user.setAvatar(image);
		}
		
		return userDao.Add(user);
		
	}
	
	public int Modify(UserBean user, MultipartFile file) throws Exception{
		if(file != null && !file.isEmpty()){
			String path = user.getAvatar().replaceAll(remote, local);
			FileGlobal.RemoveFile(path);
			
			String image = FileGlobal.AddFile(file, remote, local);
			if(image != null)
				user.setAvatar(image);
		}
		return userDao.Modify(user);
	}

	// chat start
	
	public UserBean ByWechat(String code) throws Exception{
		String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code", appid, appsecret, code);

		String json = NetGlobal.HttpGet(url, null);
		
		if(json != null && json.length() > 0){
			JSONObject row = JSON.parseObject(json);
			
			url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN", row.getString("access_token"), row.getString("openid"));
			json = NetGlobal.HttpGet(url, null);
			
			row = JSON.parseObject(json);
			
			if(!StringGlobal.IsNull(row.getString("openid"))){
				UserBean user = new UserBean();
				user.setName(row.getString("nickname"));
				user.setWechat_guid(row.getString("openid"));
				user.setAvatar(row.getString("headimgurl"));
				return user;
			}
		}
		
		return null;
	}
	
	
	public UserBean Add(int userId, String password, String nickname) throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/users", org_name, app_name);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer " + tokenService.Instance().getAccess_token());
		
		String body = String.format("{\"username\":\"%d\",\"password\":\"%s\",\"nickname\":\"%s\"}", userId, password, nickname);
		
		String json = NetGlobal.HttpPost(url, headers, body);
		
		if(json != null && json.length() > 0)
			return new UserBean(JSON.parseObject(json).getJSONArray("entities").getJSONObject(0));
		
		return null;
	}
	
	public UserBean By(int userId) throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/users/%d", org_name, app_name, userId);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + tokenService.Instance().getAccess_token());

		String json = NetGlobal.HttpGet(url, headers);

		if(json != null && json.length() > 0)
			return new UserBean(JSON.parseObject(json).getJSONArray("entities").getJSONObject(0));
		
		return null;
	}
	
	public UserBean Remove(int userId) throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/users/%d", org_name, app_name, userId);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + tokenService.Instance().getAccess_token());

		String json = NetGlobal.HttpDelete(url, headers);

		if(json != null && json.length() > 0)
			return new UserBean(JSON.parseObject(json).getJSONArray("entities").getJSONObject(0));
		
		return null;
	}
	
	// chat ended
	
	public String getRemote() {
		return remote;
	}

	public void setRemote(String remote) {
		this.remote = remote;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getApp_name() {
		return app_name;
	}

	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
