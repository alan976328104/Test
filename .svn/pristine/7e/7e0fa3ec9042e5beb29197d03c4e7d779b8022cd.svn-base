package ac.drsi.nestor.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.common.StringUtil;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_LoginService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_LoginController {
	@Autowired
	SVDS_LoginService loginService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 登录
	 * 
	 * @param username
	 *            账号
	 * @param password
	 *            密码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/oldlogin", method = RequestMethod.POST)
	@ResponseBody
	public String loginVerify(String username, String password,String lang,
			HttpSession session,HttpServletRequest request) throws Exception {
		SVDS_User user = loginService.Login(username, password);
		session.setMaxInactiveInterval(180000);
		if(lang!=null){
			System.out.println("语言："+lang);
			session.setAttribute("lang", lang);
		}else{
			session.setAttribute("lang", "zh_CN");
		}
		JSONArray array;
		if (user != null) {
			session.setAttribute("user", user);
			// 获取本地IP地址
	       // String ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println("用户Id:"+user.getUserId());
	        sessionService.inserSession(IpAddressUtils.getIpAddress(request), "userId", user.getUserId()+"");
			array = JSONArray.fromObject(user);
			System.out.print("HttpSession 默认的过期时间为："+session.getMaxInactiveInterval());
			logService.insertLog(new SVDS_Log("登录系统", DateUtils.getDate(),
					user,IpAddressUtils.getIpAddress(request),"成功"));
			return array.toString();
		} else {
			array = JSONArray.fromObject("[{'result':'error'}]");
			return array.toString();
		}
	}
	
	@RequestMapping(value = "/login")
	public String KeyLogin(HttpSession session,HttpServletRequest request) throws UnknownHostException{
		//System.out.println(IpAddressUtils.getIpAddress(request));
		String dnname= null;
		Enumeration<String> heardNames = request.getHeaderNames();
		while (heardNames.hasMoreElements()) {
			String heardnames = (String) heardNames.nextElement();
			if(heardnames.equals("dnname")){
				dnname=request.getHeader(heardnames);
			}
			System.out.println(heardnames+":" +request.getHeader(heardnames));
		}
		
			//dnname = "CN=1111,OU=0786fdsfdsfs,OU=USERS,O=NPIC,C=CN";
		System.out.println("dnname:"+dnname);
		if(dnname==null){
			return "error";
		}else{
			System.out.println(dnname);
		//	dnname = "CN=NPIC000001,OU=0786fdsfdsfs,OU=USERS,O=NPIC,C=CN";
			String name[]=dnname.split(",");
			String card[] = name[0].split("=");
			session.setMaxInactiveInterval(180000);
			SVDS_User user = loginService.keyLogin(card[1]);
			if (user != null) {
				session.setAttribute("user", user);
				// 获取本地IP地址
		       // String ip = InetAddress.getLocalHost().getHostAddress();
				System.out.println("用户Id:"+user.getUserId());
				return "redirect:/temp.html?card="+card[1];
			} else {
				return "error";
			}
			
		}
		
		
		
		
		
	}
	
	@RequestMapping(value = "/templogin")
	@ResponseBody
	public Integer templogin(HttpServletRequest request,String card,HttpSession session) throws UnknownHostException{
		System.out.println("---------card"+card);
		if(card==null||card==""){
			
			return 0;
		}else{
			System.out.println(IpAddressUtils.getIpAddress(request));
			SVDS_User user = loginService.keyLogin(card);
			logService.insertLog(new SVDS_Log("登录系统", DateUtils.getDate(),
			user,InetAddress.getLocalHost().getHostAddress(),"成功"));
			  sessionService.inserSession(IpAddressUtils.getIpAddress(request), "userId", user.getUserId()+"");
			System.out.print("HttpSession 默认的过期时间为："+session.getMaxInactiveInterval());
			return 1;
		}
		
	
		
		
	}
	
	
	
	
	public String getIpAddress(HttpServletRequest request) throws UnknownHostException{
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
	       System.out.println(InetAddress.getLocalHost().getHostAddress());
	       String clientip = ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip.split(",")[0];
	        return clientip;
	   // }
	}
	
	public static void main(String[] args) {
		
	}
}
