package com.raintr.pinshe.utils;

public class MathGlobal {
	private static final double EARTH_RADIUS = 6378137; // m
	
	/** 
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下 
     * @param lon1 第一点的精度 
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的精度 
     * @param lat3 第二点的纬度 
     * @return 返回的距离，单位km
     * */
	public static double GetDistance(double lon1, double lat1, double lon2, double lat2){
       double radLat1 = Rad(lat1);
       double radLat2 = Rad(lat2);  
       double a = radLat1 - radLat2;  
       double b = Rad(lon1) - Rad(lon2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       //s = Math.round(s * 10000) / 10000;
       return s;
    }
    
    private static double Rad(double d){  
    	return d * Math.PI / 180.0;  
	}
}