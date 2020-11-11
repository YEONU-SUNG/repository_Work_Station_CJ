package com.neo.visitor.util;

import java.util.Calendar;
import java.util.UUID;

public class ExtensionTypeCheck {
	public static boolean checkExtensionTypeIMG(String extention, String type) {
		boolean result = false;
		switch (type) {
		case "img" :
			if(extention.equals("jpg") || extention.equals("png") || extention.equals("gif") ||  extention.equals("jpeg") ) {
				result = true;
			}
			break;
		case "doc" :
			if(!extention.equals("exe") && !extention.equals("com") && !extention.equals("dll") && !extention.equals("bat")
					&& !extention.equals("sh") && !extention.equals("java") && !extention.equals("jsp") && !extention.equals("php")
					&& !extention.equals("asp") && !extention.equals("js") && !extention.equals("jar")) {
				result = true;
			}
			break;
		}
		return result;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}
	
	public static String getMillisecondsName(String extention, String device, String header) {
		Calendar calendar = Calendar.getInstance();
		String transFileName = "";
		switch (header) {
		case "banner" :
			transFileName = header+"_"+device+"_"+calendar.getTimeInMillis()+"."+extention;
			break;
		case "board" :
			transFileName = header+"_"+calendar.getTimeInMillis()+"."+extention;
			break;
		case "research" :
			transFileName = header+"_"+calendar.getTimeInMillis()+"."+extention;
			break;
		case "popup" :
			transFileName = header+"_"+calendar.getTimeInMillis()+"."+extention;
			break;
		}
		return transFileName;
	}
}
