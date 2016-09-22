package com.raintr.pinshe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class NetGlobal {
	public static String HttpPost(String url, Map<String, String> headers, String body) throws Exception{
		return HttpPost(url, headers, body, "utf-8");
	}
	
	public static String HttpPost(String url, Map<String, String> headers, String body, String charset) throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		try{
			HttpPost post = new HttpPost(url);
			post.setConfig(RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build());
			
			if(headers != null){
				for(Entry<String, String> header : headers.entrySet()){
					post.addHeader(header.getKey(), header.getValue());
				}
			}
			
			if(body != null){
				if(charset != null && charset.length() > 0)
					post.setEntity(new StringEntity(body, charset));
				else
					post.setEntity(new StringEntity(body));
			}
			
			CloseableHttpResponse response = client.execute(post);
			try{
				if (response.getStatusLine().getStatusCode() == 200)
					return EntityUtils.toString(response.getEntity());
			}finally{
				response.close();
			}
		}finally{
			client.close();
		}
		
		return null;
	}
	
	public static String HttpPost(String url, Map<String, String> headers, Map<String, String> bodys, String charset) throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		try{
			HttpPost post = new HttpPost(url);
			post.setConfig(RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build());
			
			if(headers != null){
				for(Entry<String, String> header : headers.entrySet()){
					post.addHeader(header.getKey(), header.getValue());
				}
			}

			if(bodys != null){
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				for(Entry<String, String> body : bodys.entrySet()){
					formparams.add(new BasicNameValuePair(body.getKey(), body.getValue()));
				}
				
				if(charset != null && charset.length() > 0)
					post.setEntity(new UrlEncodedFormEntity(formparams, charset));
				else
					post.setEntity(new UrlEncodedFormEntity(formparams));
			}
			
			CloseableHttpResponse response = client.execute(post);
			try{
				if (response.getStatusLine().getStatusCode() == 200)
					return EntityUtils.toString(response.getEntity());
			}finally{
				response.close();
			}
		}finally{
			client.close();
		}
		
		return null;
	}
	
	
	public static String HttpGet(String url, Map<String, String> headers) throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		try{
			HttpGet get = new HttpGet(url);
			get.setConfig(RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build());
			
			if(headers != null){
				for(Entry<String, String> header : headers.entrySet()){
					get.addHeader(header.getKey(), header.getValue());
				}
			}
	
			CloseableHttpResponse response = client.execute(get);
			try{
				if(response.getStatusLine().getStatusCode() == 200)
					return EntityUtils.toString(response.getEntity());
			}finally{
				response.close();
			}
		}finally{
			client.close();
		}
		
		return null;
	}
	
	public static String HttpGet(String url, Map<String, String> headers, String charset) throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		try{
			HttpGet get = new HttpGet(url);
			get.setConfig(RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build());
			
			if(headers != null){
				for(Entry<String, String> header : headers.entrySet()){
					get.addHeader(header.getKey(), header.getValue());
				}
			}
	
			CloseableHttpResponse response = client.execute(get);
			try{
				if(response.getStatusLine().getStatusCode() == 200)
					return EntityUtils.toString(response.getEntity(), charset);
			}finally{
				response.close();
			}
		}finally{
			client.close();
		}
		
		return null;
	}
	
	public static String HttpDelete(String url, Map<String, String> headers) throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		try{
			HttpDelete delete = new HttpDelete(url);
			delete.setConfig(RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build());
			
			if(headers != null){
				for(Entry<String, String> header : headers.entrySet()){
					delete.addHeader(header.getKey(), header.getValue());
				}
			}
	
			CloseableHttpResponse response = client.execute(delete);
			try{
				if(response.getStatusLine().getStatusCode() == 200)
					return EntityUtils.toString(response.getEntity());
			}finally{
				response.close();
			}
		}finally{
			client.close();
		}
		
		return null;
	}
}