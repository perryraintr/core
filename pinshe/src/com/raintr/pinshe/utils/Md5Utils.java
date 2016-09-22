package com.raintr.pinshe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

	public static String Md5(String source) throws NoSuchAlgorithmException {
		if(source != null && source.length() > 0){
			String md5;
			StringBuffer md5s = new StringBuffer();
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			byte digest[] = messagedigest.digest(source.getBytes());
			for (int i = 0; i < digest.length; i++) {
				md5 = Integer.toHexString(digest[i] & 0x000000ff);
				if (md5.length() < 2)
					md5 = "0" + md5;
				md5s.append(md5);
			}
			return md5s.toString();
		}
		
		return null;
	}

	public static String[] Md5(String[] sources) throws NoSuchAlgorithmException {
		String[] md5s = new String[sources.length];
		for (int i = 0; i < md5s.length; i++)
			md5s[i] = Md5(sources[i]);
		return md5s;
	}
}