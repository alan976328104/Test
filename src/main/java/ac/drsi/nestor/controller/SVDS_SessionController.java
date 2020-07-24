package ac.drsi.nestor.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;

@Controller
public class SVDS_SessionController {
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_UserService userService;

	/**
	 * 添加ip用户
	 * 
	 * @param ip
	 * @param userId
	 * @param value
	 * @return
	 */
	@RequestMapping(value = "/inserSession", method = RequestMethod.POST)
	@ResponseBody
	public Integer inserSession(String ip, String key, String value) {
		return sessionService.inserSession(ip, key, value);
	}

	/**
	 * 获取登录用户
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSessionByIp", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_User getSessionByIp(HttpServletRequest request) {
		SVDS_User loginUser=sessionService.getSessionByIp(request);
		if(loginUser!=null){
			return loginUser;
		}else{
			return null;
		}
	}
}
