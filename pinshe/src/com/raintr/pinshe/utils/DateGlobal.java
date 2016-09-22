package com.raintr.pinshe.utils;

import java.util.Date;

public class DateGlobal {
	public static String GetDistanceTime(Date one){
		return GetDistanceTime(one, new Date());
	}
	
	public static String GetDistanceTime(Date one, Date two) {		
		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		
		if(time2 > time1){
			diff = time2 - time1;
		
			long day = diff / (24 * 60 * 60 * 1000);
			long hour = (diff / (60 * 60 * 1000) - day * 24);
			long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
	
			if(day == 0){
				if(hour == 0){
					if(min == 0){
						if(sec == 0)
							return "同步";
						else
							return sec + "秒前";
					}else{
						return min + "分钟前";
					}
				}else{
					return hour + "小时前";
				}
			}else{
				return day + "天前";
			}
		}
		
		return "";
	}
}
