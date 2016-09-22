package com.raintr.pinshe.utils;

public class StringGlobal {
	public static boolean IsNull(String value) {
		if(value != null)
			return value.isEmpty();
		
		return true;
	}
	
	public static String SerializeJson(String value){
		if(!IsNull(value))
			value = value.replace("\r", "");
		if(!IsNull(value))
			value = value.replace("\b", "");
		if(!IsNull(value))
			value = value.replace("\f", "");
		if(!IsNull(value))
			value = value.replace("\t", "");

		value = DeserializeJson(value);
		
		if(!IsNull(value))
			value = value.replace("\\", "\\\\");
		if(!IsNull(value))
			value = value.replace("\"", "\\\"");
		if(!IsNull(value))
			value = value.replace("\n", "\\n");
		
		return value;
	}
	
	public static String DeserializeJson(String value){
		if(!IsNull(value))
			value = value.replace("\\n", "\n");
		if(!IsNull(value))
			value = value.replace("\\\"", "\"");
		if(!IsNull(value))
			value = value.replace("\\\\", "\\");
		
		return value;
	}
}