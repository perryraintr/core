package com.raintr.pinshe.utils;

import java.security.MessageDigest;

public class Md5Global {
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

	public static String Encode(String origin) throws Exception{
	    String resultString = origin;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(resultString.getBytes("UTF-8"));
        resultString = ByteArrayToHexString(md.digest());
	    return resultString;
	}
	
	private static String ByteArrayToHexString(byte[] b){
	    StringBuilder resultSb = new StringBuilder();
	    for(byte aB : b)
	        resultSb.append(ByteToHexString(aB));
	    return resultSb.toString();
	}

	private static String ByteToHexString(byte b){
	    int n = b;
	    if (n < 0)
	        n = 256 + n;
	    int d1 = n / 16;
	    int d2 = n % 16;
	    return hexDigits[d1] + hexDigits[d2];
	}
}