package com.raintr.pinshe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.raintr.pinshe.bean.GroupBean;
import com.raintr.pinshe.dao.GroupDao;
import com.raintr.pinshe.utils.FileGlobal;
import com.raintr.pinshe.utils.NetGlobal;

public class GroupService {
	private String remote;
	private String local;
	
	private String org_name;
	private String app_name;
	private TokenService tokenService;
	
	private GroupDao groupDao;
	
	public GroupService(){}

	public List<GroupBean> By(int page){
		return groupDao.By(page);
	}
	
	public List<GroupBean> ByType(int type, int page){
		return groupDao.ByType(type, page);
	}
	
	public GroupBean ById(int id){	
		return groupDao.ById(id);
	}
	
	public GroupBean ByEasemobId(String id){
		return groupDao.ByEasemobId(id);
	}
	
	public int Add(GroupBean group, List<MultipartFile> files) throws Exception{
		String url = null;
		MultipartFile file;
		if(files != null && files.size() > 0){
			for(int i = 0; i < files.size(); i++){
				file = files.get(i);
				if(file != null && !file.isEmpty()){
					Thread.sleep(1);
					url = FileGlobal.AddFile(file, remote, local);					
					group.setImage(url);
				}
			}
		}
		
		return groupDao.Add(group);
	}
	
	public int Modify(GroupBean group, List<MultipartFile> files) throws Exception{
		String url;
		MultipartFile file;
		if(files != null && files.size() > 0){
			for(int i = 0; i < files.size(); i++){
				file = files.get(i);
				if(file != null && !file.isEmpty()){
					// remove
					url = group.getImage().replaceAll(remote, local);
					FileGlobal.RemoveFile(url);
					// add
					Thread.sleep(1);
					url = FileGlobal.AddFile(file, remote, local);
					group.setImage(url);
				}
			}
		}
	
		return groupDao.Modify(group);
	}
	
	public int Remove(int id){
		return groupDao.Remove(id);
	}
	
	
	public GroupBean Add(String groupname, String description, boolean isPublic, int count, boolean isApproval, int userId) throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/chatgroups", org_name, app_name);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + tokenService.Instance().getAccess_token());
		
		StringBuffer body = new StringBuffer();
		body.append("{");
		body.append(String.format("\"groupname\":\"%s\",", 	groupname));	// 群组名称，此属性为必须的
		body.append(String.format("\"desc\":\"%s\",", 		description));	// 群组描述，此属性为必须的
		body.append(String.format("\"public\":%b,", 		isPublic));		// 是否是公开群，此属性为必须的
		body.append(String.format("\"maxusers\":%d,", 		count));		// 群组成员最大数(包括群主)，值为数值类型，默认值200，此属性为可选的
		body.append(String.format("\"approval\":%b,", 		isApproval));	// 加入公开群是否需要批准，默认值是false（加入公开群不需要群主批准），此属性为必选的，私有群必须为true
		body.append(String.format("\"owner\":\"%d\"", 		userId));		// 群组的管理员，此属性为必须的
		body.append("}");
		
		String json = NetGlobal.HttpPost(url, headers, body.toString());
		
		if(json != null && json.length() > 0)
			return new GroupBean(JSON.parseObject(json).getJSONObject("data"));
		
		return null;
	}

	public GroupBean Remove(String groupId) throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/chatgroups/%s", org_name, app_name, groupId);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + tokenService.Instance().getAccess_token());
		
		String json = NetGlobal.HttpDelete(url, headers);
		
		if(json != null && json.length() > 0)
			return new GroupBean(JSON.parseObject(json).getJSONObject("data"));
		
		return null;
	}

	public GroupBean AddUser(String groupId, int userId) throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/chatgroups/%s/users/%d", org_name, app_name, groupId, userId);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + tokenService.Instance().getAccess_token());
		
		String json = NetGlobal.HttpPost(url, headers, null);
		
		if(json != null && json.length() > 0)
			return new GroupBean(JSON.parseObject(json).getJSONObject("data"));
		
		return null;
	}
	
	public GroupBean RemoveUser(String groupId, int userId) throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/chatgroups/%s/users/%d", org_name, app_name, groupId, userId);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "Bearer " + tokenService.Instance().getAccess_token());
		
		String json = NetGlobal.HttpDelete(url, headers);
		
		if(json != null && json.length() > 0)
			return new GroupBean(JSON.parseObject(json).getJSONObject("data"));
		
		return null;
	}
	
	public GroupBean SendTxt(String groupId, String message, int userId) throws Exception{
		String url = String.format("https://a1.easemob.com/%s/%s/messages", org_name, app_name);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer " + tokenService.Instance().getAccess_token());
		
		StringBuffer body = new StringBuffer();
		body.append("{");
		body.append("\"target_type\":\"chatgroups\","); 									// users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
		body.append(String.format("\"target\":[\"%s\"],", 						groupId));	// 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时 数组元素是groupid
		body.append(String.format("\"msg\":{\"type\":\"txt\",\"msg\":\"%s\"},", message)); 	// 消息内容，参考[[start:100serverintegration:30chatlog|聊天记录]]里的bodies内容
		body.append(String.format("\"from\":\"%d\"", 							userId));		// 表示消息发送者。无此字段Server会默认设置为"from":"admin"，有from字段但值为空串("")时请求失败
		body.append("}");

		String json = NetGlobal.HttpPost(url, headers, body.toString());
		
		if(json != null && json.length() > 0)
			return new GroupBean(JSON.parseObject(json).getJSONObject("data"));
		
		return null;
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
	
	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

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
}
