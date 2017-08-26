package com.loozb.core.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * 
 * Base64加/解密工具类
 * <p>
 * </p>
 * @author SHC
 * @since jdk1.6
 * 2015年10月12日
 *  
 */

@SuppressWarnings("restriction")
public class Base64Util {

	
	// 加密  
	public static String getBase64(String str) {  
		byte[] b = null;  
		String s = null;  
		try {
			b = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		if (b != null) {  
			s = new BASE64Encoder().encode(b);  
		}  
		return s;  
	}
	
	// 解密  
	public static String getFromBase64(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();
            try {  
                b = decoder.decodeBuffer(s);  
                result = new String(b, "UTF-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }
	
	public static void main(String[] args) {
		String ird = "eyJkYXRhVGltZXN0YW1wIjpudWxsLCJkYXRhc2V0Q29kZSI6ImNvZGUxOTk5OTQiLCJpbmZvQ29u\r\ndGVudCI6bnVsbCwiaW5mb1RpbWUiOm51bGwsImluZm9UeXBlIjoidHlwZTE5OTk5NCIsImluc3Rh\r\nbmNlSWQiOm51bGwsInBpZCI6IjE5OTk5NCJ9";
		String result = Base64Util.getFromBase64(ird);
		System.out.println(result);
	}
    
}
