package com.raintr.pinshe.bean;

public class TokenBean {
	private String access_token;
	// 7 day
	private int expires_in;
	private String application;
	
	public TokenBean(){
		access_token = "YWMt1rw3BEmlEea1xhtFX1lkAQAAAVcdvVaGK2jW3Vo7A2W3aPk5kdotVK2MfDE";
		expires_in = 5183999;
		application = "ae243f20-1cdd-11e6-9f05-51ba9f45e18a";
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
}
