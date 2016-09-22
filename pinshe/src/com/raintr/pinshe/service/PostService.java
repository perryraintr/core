package com.raintr.pinshe.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.raintr.pinshe.bean.CommentBean;
import com.raintr.pinshe.bean.ImageBean;
import com.raintr.pinshe.bean.PostBean;
import com.raintr.pinshe.bean.TagBean;
import com.raintr.pinshe.dao.CommentDao;
import com.raintr.pinshe.dao.ImageDao;
import com.raintr.pinshe.dao.PostDao;
import com.raintr.pinshe.dao.TagDao;
import com.raintr.pinshe.dao.GroupDao;
import com.raintr.pinshe.dao.UserDao;
import com.raintr.pinshe.utils.FileGlobal;

public class PostService {
	private String remote;
	private String local;
	
	private UserDao userDao;
	private TagDao tagDao;
	private PostDao postDao;
	private CommentDao commentDao;
	private ImageDao imageDao;
	private GroupDao groupDao;
	
	public PostBean ById(int id) throws Exception{
		TagBean tag;
		PostBean post = postDao.ById(id);
		if(post != null){
			post.setUser(userDao.ById(post.getUser_id()));
			post.setTags(tagDao.ByPostId(post.getId()));
			post.setImages(imageDao.ByPostId(post.getId()));
			
			if(post.getTags() != null){
				for(int i = 0; i < 	post.getTags().size(); i++){
					tag = post.getTags().get(i);
					if(tag != null)
						tag.setGroup(groupDao.ById(tag.getT2()));
				}
			}
			
			return post;
		}
		
		return null;
	}
	
	public List<PostBean> By(int page){
		PostBean post;
		List<PostBean> posts = postDao.By(page);
		
		if(posts != null){
			for(int i = 0; i < posts.size(); i++){
				post = posts.get(i);
				post.setTags(tagDao.ByPostId(post.getId()));
				//post.setImages(imageDao.ByPostId(post.getId()));
			}
			return posts;
		}
		
		return null;
	}
	
	public List<PostBean> ByProductId(int productId){
		CommentBean comment;
		List<CommentBean> comments;
		PostBean post;
		List<PostBean> posts = postDao.ByProductId(productId);
		
		if(posts != null){
			for(int i = 0; i < posts.size(); i++){
				post = posts.get(i);
				post.setUser(userDao.ById(post.getUser_id()));
				
				comments = commentDao.ByPostId(post.getId(), 0);
				if(comments != null && comments.size() > 0){
					comment = comments.get(0);
					if(comment != null){
						comment.setUser_a(userDao.ById(comment.getUser_id_a()));
						post.setLastComment(comment);
					}
				}
				
				//post.setImages(imageDao.ByPostId(post.getId()));
			}
			return posts;
		}
		
		return null;
	}
	
	public List<PostBean> ByTagT1(int t1, int page){
		CommentBean comment;
		List<CommentBean> comments;
		
		PostBean post;
		List<PostBean> posts = postDao.ByTagT1(t1, page);
		
		if(posts != null){
			for(int i = 0; i < posts.size(); i++){
				post = posts.get(i);
				post.setUser(userDao.ById(post.getUser_id()));
				
				comments = commentDao.ByPostId(post.getId(), 0);
				if(comments != null && comments.size() > 0){
					comment = comments.get(0);
					if(comment != null){
						comment.setUser_a(userDao.ById(comment.getUser_id_a()));
						post.setLastComment(comment);
					}
				}
				
				//post.setImages(imageDao.ByPostId(post.getId()));
			}
			return posts;
		}
		
		return null;
	}
	
	public List<PostBean> ByTagT1T2(int t1, int t2, int page){
		CommentBean comment;
		List<CommentBean> comments;
		
		PostBean post;
		List<PostBean> posts = postDao.ByTagT1T2(t1, t2, page);
		
		if(posts != null){
			for(int i = 0; i < posts.size(); i++){
				post = posts.get(i);
				post.setUser(userDao.ById(post.getUser_id()));
				
				comments = commentDao.ByPostId(post.getId(), 0);
				if(comments != null && comments.size() > 0){
					comment = comments.get(0);
					if(comment != null){
						comment.setUser_a(userDao.ById(comment.getUser_id_a()));
						post.setLastComment(comment);
					}
				}
				
				//post.setImages(imageDao.ByPostId(post.getId()));
			}
			return posts;
		}
		
		return null;
	}
	
	public int Add(PostBean post) throws Exception{
		List<MultipartFile> files = null;
		return Add(post, files);
	}
	
	public int Add(PostBean post, MultipartFile file) throws Exception{
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(file);
		return Add(post, files);
	}
	
	public int Add(PostBean post, List<MultipartFile> files) throws Exception{
		String url = null;
		ImageBean image;
		MultipartFile file;
		List<ImageBean> images = new ArrayList<ImageBean>();
		if(files != null && files.size() > 0){
			for(int i = 0; i < files.size(); i++){
				file = files.get(i);
				if(file != null && !file.isEmpty()){
					Thread.sleep(1);
					url = FileGlobal.AddFile(file, remote, local);					
					image = new ImageBean();
					image.setUrl(url);
					image.setCreate_time(new Date());
					image.setModify_time(new Date());
					images.add(image);
				}
			}
		}

		if(images != null && images.size() > 0)
			post.setImage(images.get(0).getUrl());
		
		int postId = postDao.Add(post); 
		
		if(images != null && images.size() > 0){
			for(int i = 0; i < images.size(); i++){
				image = images.get(i);
				image.setPost_id(postId);
				imageDao.Add(image);
			}
		}
		
		return postId;
	}
	
	public int Modify(PostBean post) throws Exception{
		List<MultipartFile> files = null;
		return Modify(post, files);
	}
	
	public int Modify(PostBean post, MultipartFile file) throws Exception{
		List<MultipartFile> files = new ArrayList<MultipartFile>();
		files.add(file);
		return Modify(post, files);
	}
	
	public int Modify(PostBean post, List<MultipartFile> files) throws Exception{
		String url;
		ImageBean image;
		MultipartFile file;
		List<ImageBean> images = post.getImages();

		if(images == null)
			images = new ArrayList<ImageBean>(); 
		
		if(files != null && files.size() > 0){
			// rid of waste others
			if(images.size() > files.size()){
				for(int i = images.size() - 1; i > files.size() - 1; i--){
					imageDao.Remove(images.get(i).getId());
					images.remove(i);
				}
			}
			
			for(int i = 0; i < files.size(); i++){
				file = files.get(i);
				if(file != null && !file.isEmpty()){
					if(images != null && images.size() > i){
						image = images.get(i);
						// remove
						url = image.getUrl().replaceAll(remote, local);
						FileGlobal.RemoveFile(url);
						// add
						Thread.sleep(1);
						url = FileGlobal.AddFile(file, remote, local);

						image.setUrl(url);
						imageDao.Modify(image);
					}else{
						Thread.sleep(1);
						url = FileGlobal.AddFile(file, remote, local);
						
						image = new ImageBean();
						image.setPost_id(post.getId());
						image.setUrl(url);
						image.setCreate_time(new Date());
						image.setModify_time(new Date());
						images.add(image);
						imageDao.Add(image);
					}
				}
			}
		}
		
		if(images != null && images.size() > 0)
			post.setImage(images.get(0).getUrl());
		
		return postDao.Modify(post);
	}
	
	public int Remove(PostBean post) throws Exception{
		ImageBean image;
		
		String url = post.getImage().replaceAll(remote, local);
		FileGlobal.RemoveFile(url);
		
		// remove image url
		List<ImageBean> images = post.getImages();
		if(images != null && images.size() > 0){
			for(int i = 0; i < images.size(); i++){
				// remove
				image = images.get(i);
				url = image.getUrl().replaceAll(remote, local);
				if(url != null)
					FileGlobal.RemoveFile(url);
				
				imageDao.Remove(image.getId());
			}
		}
		
		return postDao.Remove(post.getId());
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

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public TagDao getTagDao() {
		return tagDao;
	}

	public void setTagDao(TagDao tagDao) {
		this.tagDao = tagDao;
	}

	public PostDao getPostDao() {
		return postDao;
	}

	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public ImageDao getImageDao() {
		return imageDao;
	}

	public void setImageDao(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
