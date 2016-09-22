package com.raintr.pinshe.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FileGlobal {
	public static void CreateDirectiory(File file) {
		if (file != null && !file.exists()) {
			file.mkdirs();
		}
	}
	
	public static String ReadFile(File file, String charsetName) throws IOException {
		if (file != null) {
			byte[] buffer;
			FileInputStream in = null;
			
			try {
				in = new FileInputStream(file);
				buffer = new byte[in.available()];
				in.read(buffer);
			} finally {
				if (in != null)
					in.close();
			}

			return new String(buffer, charsetName);
		}

		return null;
	}
	
	public static String AddFile(MultipartFile file, String remote, String local) throws Exception {
        if(file != null && !file.isEmpty()){
    		Date date = new Date();
    		
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    		
    		SimpleDateFormat year = new SimpleDateFormat("yyyy");
        	SimpleDateFormat month = new SimpleDateFormat("MM");
        	SimpleDateFormat day = new SimpleDateFormat("dd");
        	
        	String path = year.format(date) + "/" + month.format(date) + "/" + day.format(date);
        	CreateDirectiory(new File(local + "/" + path));
        	
        	String fileName = file.getOriginalFilename();
            String prefix = fileName.substring(fileName.lastIndexOf("."));
            
            File localFile = new File(local + "/" + path + "/" + sdf.format(date) + prefix);
            
            file.transferTo(localFile);
            
            return remote + "/" + path + "/" + sdf.format(date) + prefix;
        }

        return null;
	}
	
//	public static String AddFile(MultipartFile file, String remote, String local, String name) throws Exception {
//        if(file != null && !file.isEmpty()){
//        	CreateDirectiory(new File(local + "/" + name.charAt(0)));
//        	
//            File localFile = new File(local + "/" + name.charAt(0) + "/" + name);
//            
//            file.transferTo(localFile);
//            
//            return remote + "/" + name.charAt(0) + "/" + name;
//        }
//
//        return null;
//	}
	
//	public static String AddFile(MultipartFile file, String remote, String local) throws Exception {
//        if(file != null && !file.isEmpty()){
//        	byte[] buffer;
//    		FileOutputStream out = null;
//    		InputStream in = null;
//    		Date date = new Date();
//    		
//    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//    		
//    		SimpleDateFormat year = new SimpleDateFormat("yyyy");
//        	SimpleDateFormat month = new SimpleDateFormat("MM");
//        	SimpleDateFormat day = new SimpleDateFormat("dd");
//        	
//        	String path = year.format(date) + "/" + month.format(date) + "/" + day.format(date);
//        	CreateDirectiory(new File(local + "/" + path));
//        	
//        	String fileName = file.getOriginalFilename();
//            String prefix = fileName.substring(fileName.lastIndexOf("."));
//        	
//            try {
//                out = new FileOutputStream(local + "/" + path + "/" + sdf.format(date) + prefix);
//                in = file.getInputStream();
//                buffer = new byte[in.available()];
//                in.read(buffer);
//                out.write(buffer);
//            }finally{
//            	if(out != null){
//	            	out.flush();  
//	            	out.close();
//            	}
//            	
//            	if(in != null)
//            		in.close();
//            }
//            
//            return remote + "/" + path + "/" + sdf.format(date) + prefix;
//        }
//
//        return null;
//	}
	
	public static String AddFile(String context, String remote, String local) throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
    	SimpleDateFormat month = new SimpleDateFormat("MM");
    	SimpleDateFormat day = new SimpleDateFormat("dd");

    	String name = year.format(date) + "/" + month.format(date) + "/" + day.format(date) + "/" + sdf.format(date) + ".html";
    	File file = new File(local + "/" + name);
    	
    	CreateDirectiory(file.getParentFile());
		
        BufferedWriter bw = null;
        try{
        	bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        	bw.write(context);
        	bw.close();
        }finally{
            if (bw != null){
            	bw.close();
            	bw = null;
            }
        }
        
        return remote + "/" + name;
	}
	
	public static boolean RemoveFile(String path) throws Exception {
		File file = new File(path);
		if(file.exists())
			return file.delete();
		
		return false;
	}
}
