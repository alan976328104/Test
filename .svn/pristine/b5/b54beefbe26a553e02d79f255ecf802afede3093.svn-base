package ac.drsi.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
/**
 * 用于获取用户IP地址
 * @author CZK
 *
 */
public class IpAddressUtils {
	//获取用户iP地址
	public static String getIpAddress(HttpServletRequest request) throws UnknownHostException{
		//	public static String getIpAddress(HttpServletRequest request) {
		        String ip = request.getHeader("x-forwarded-for");
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		            ip = request.getHeader("Proxy-Client-IP");
		        }
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		            ip = request.getHeader("WL-Proxy-Client-IP");
		        }
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		            ip = request.getHeader("HTTP_CLIENT_IP");
		        }
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		        }
		        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		            ip = request.getRemoteAddr();
		        }
		      // System.out.println(InetAddress.getLocalHost().getHostAddress());
		       String clientip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip.split(",")[0];
		        return clientip;
		   // }
		}
}
