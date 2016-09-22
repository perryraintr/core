package com.raintr.pinshe.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.raintr.pinshe.bean.AdBean;
import com.raintr.pinshe.dao.AdDao;
import com.raintr.pinshe.utils.FileGlobal;

public class AdService {
	private String domain;
	private String domainLocal;
	private String imageRemote;
	private String imageLocal;
	private String articleRemote;
	private String articleLocal;
	
	private AdDao adDao;
	
	public AdBean ById(int id) throws Exception{		
		return adDao.ById(id);
	}
	
	public List<AdBean> ByType(int type, int page){
		return adDao.ByType(type, page);
	}
	
	public int Add(AdBean ad, MultipartFile file, String name, String content, String[] url) throws Exception{
		if(content != null && !content.isEmpty()){
			url[0] = FileGlobal.AddFile(GetHtml(name, content), articleRemote, articleLocal);
			if(url[0] != null)
				ad.setUrl(url[0]);
		}
		
		if(file != null && !file.isEmpty()){
			String image = FileGlobal.AddFile(file, imageRemote, imageLocal);
			if(image != null)
				ad.setImage(image);
		}
		
		return adDao.Add(ad);
	}
	
	private String GetHtml(String name, String content) throws IOException{
		String html = FileGlobal.ReadFile(new File(articleLocal + "/wechat.htm"), "utf-8");
		html = html.replace("#name#", name);
		html = html.replace("#content#", content);
		return html;
	}
	
//	public int Modify(AdBean user, MultipartFile file) throws Exception{
////		if(file != null && !file.isEmpty()){
////			String path = user.getAvatar().replaceAll(remote, local);
////			FileGlobal.RemoveFile(path);
////			
////			String image = FileGlobal.AddFile(file, remote, local);
////			if(image != null)
////				user.setAvatar(image);
////		}
//		return adDao.Modify(user);
//	}

	public String GetImageUrl(MultipartFile file) throws Exception{
		if(file != null && !file.isEmpty())
			return FileGlobal.AddFile(file, imageRemote, imageLocal);
		return null;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomainLocal() {
		return domainLocal;
	}

	public void setDomainLocal(String domainLocal) {
		this.domainLocal = domainLocal;
	}

	public String getImageRemote() {
		return imageRemote;
	}

	public void setImageRemote(String imageRemote) {
		this.imageRemote = imageRemote;
	}

	public String getImageLocal() {
		return imageLocal;
	}

	public void setImageLocal(String imageLocal) {
		this.imageLocal = imageLocal;
	}

	public String getArticleRemote() {
		return articleRemote;
	}

	public void setArticleRemote(String articleRemote) {
		this.articleRemote = articleRemote;
	}

	public String getArticleLocal() {
		return articleLocal;
	}

	public void setArticleLocal(String articleLocal) {
		this.articleLocal = articleLocal;
	}

	public AdDao getAdDao() {
		return adDao;
	}

	public void setAdDao(AdDao adDao) {
		this.adDao = adDao;
	}
}
