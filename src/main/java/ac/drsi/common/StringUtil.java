package ac.drsi.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * 2019年3月25日 宁春
 * 用于操作一些常用字符类型数据
 * @author NC
 *
 */
public class StringUtil {
	/**
	 * 2019年3月25日 宁春
	 * 字符转换int
	 * @param str 字符
	 * @return
	 */
	public static Integer stringToUInt(String str) {
		{
			return Integer.parseInt(str);
		}
	}
	
	/**
	 * 2019年3月25日 宁春
	 * 获得字符串中所有的数字并转换成Int类型
	 * @param str 字符
	 * @return
	 */
	public static Integer getInteger(String str){
		str=str.trim();
		String str2="";
		if(str != null && !"".equals(str)){
			for(int i=0;i<str.length();i++){
			if(str.charAt(i)>=48 && str.charAt(i)<=57){
			str2+=str.charAt(i);
			}
			}
			 
			}
			return Integer.parseInt(str2);
	}
	

	/**
	 * 2019年3月25日 宁春
	 * 获得字符编码格式
	 * @param str 字符
	 * @return
	 */
	public static String getEncoding(String str) { 
		String encode = "GB2312"; 
		try { 
		if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GB2312
		String s = encode; 
		return s; //是的话，返回“GB2312“，以下代码同理
		} 
		} catch (Exception exception) { 
		} 
		encode = "ISO-8859-1"; 
		try { 
		if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是ISO-8859-1
		String s1 = encode; 
		return s1; 
		} 
		} catch (Exception exception1) { 
		} 
		encode = "UTF-8"; 
		try { 
		if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是UTF-8
		String s2 = encode; 
		return s2; 
		} 
		} catch (Exception exception2) { 
		} 
		encode = "GBK"; 
		try { 
		if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GBK
		String s3 = encode; 
		return s3; 
		} 
		} catch (Exception exception3) { 
		} 
		return "";
	}
	/**
	 * 2019年3月25日 宁春
	 * 将字符编码格式转换为UTF-8
	 * @param str
	 * @return
	 */
	public static String encodeUTF8(String str){
		
		try {
			return new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//获取客户端ip

	public static String getLocalIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }
}
