package com.raintr.pinshe.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class GeoAction extends BaseAction {
	private static final double EARTH_RADIUS = 6378137; // 赤道半径(单位m)
	
	@RequestMapping(value = "/geo")
    public String Init(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
		return super.Init(request, response, model);
	}
	
	protected String Action(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {	

		response.getWriter().print(GetDistance(116.454037,39.957905,-77.041138,38.89597));
		return null;
	}
	
    /** 
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下 
     * @param lon1 第一点的精度 
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的精度 
     * @param lat3 第二点的纬度 
     * @return 返回的距离，单位km
     * */
	private double GetDistance(double lon1, double lat1, double lon2, double lat2){
       double radLat1 = Rad(lat1);
       double radLat2 = Rad(lat2);  
       double a = radLat1 - radLat2;  
       double b = Rad(lon1) - Rad(lon2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       //s = Math.round(s * 10000) / 10000;
       return s;
    }
    
    private double Rad(double d){  
    	return d * Math.PI / 180.0;  
	}
}

//public class MapDistance {
//    
//    private static double EARTH_RADIUS = 6378.137;  
//  
//    private static double rad(double d) {  
//        return d * Math.PI / 180.0;  
//    }
//    
//    /**
//     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
//     * 参数为String类型
//     * @param lat1 用户经度
//     * @param lng1 用户纬度
//     * @param lat2 商家经度
//     * @param lng2 商家纬度
//     * @return
//     */
//    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
//        Double lat1 = Double.parseDouble(lat1Str);
//        Double lng1 = Double.parseDouble(lng1Str);
//        Double lat2 = Double.parseDouble(lat2Str);
//        Double lng2 = Double.parseDouble(lng2Str);
//        double patm = 2;
//        double radLat1 = rad(lat1);
//        double radLat2 = rad(lat2);
//        double difference = radLat1 - radLat2;
//        double mdifference = rad(lng1) - rad(lng2);
//        double distance = patm * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / patm), patm)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(mdifference / patm), patm)));
//        distance = distance * EARTH_RADIUS;
//        String distanceStr = String.valueOf(distance);
//        return distanceStr;
//    }
//    
//    /**
//     * 获取当前用户一定距离以内的经纬度值
//     * 单位米 return minLat
//     * 最小经度 minLng
//     * 最小纬度 maxLat
//     * 最大经度 maxLng
//     * 最大纬度 minLat
//     */
//    public static Map getAround(String latStr, String lngStr, String raidus) {
//        Map map = new HashMap();
//        
//        Double latitude = Double.parseDouble(latStr);// 传值给经度
//        Double longitude = Double.parseDouble(lngStr);// 传值给纬度
//
//        Double degree = (24901 * 1609) / 360.0; // 获取每度
//        double raidusMile = Double.parseDouble(raidus);
//        
//        Double mpdLng = Double.parseDouble((degree * Math.cos(latitude * (Math.PI / 180))+"").replace("-", ""));
//        Double dpmLng = 1 / mpdLng;
//        Double radiusLng = dpmLng * raidusMile;
//        //获取最小经度
//        Double minLat = longitude - radiusLng;
//        // 获取最大经度
//        Double maxLat = longitude + radiusLng;
//        
//        Double dpmLat = 1 / degree;
//        Double radiusLat = dpmLat * raidusMile;
//        // 获取最小纬度
//        Double minLng = latitude - radiusLat;
//        // 获取最大纬度
//        Double maxLng = latitude + radiusLat;
//        
//        map.put("minLat", minLat+"");
//        map.put("maxLat", maxLat+"");
//        map.put("minLng", minLng+"");
//        map.put("maxLng", maxLng+"");
//        
//        return map;
//    }
//    
//    public static void main(String[] args) {
//        //济南国际会展中心经纬度：117.11811  36.68484
//        //趵突泉：117.00999000000002  36.66123
//        System.out.println(getDistance("116.97265","36.694514","116.597805","36.738024"));
//        
//        System.out.println(getAround("117.11811", "36.68484", "13000"));
//        //117.01028712333508(Double), 117.22593287666493(Double),
//        //36.44829619896034(Double), 36.92138380103966(Double)
//        
//    }
//    
//}