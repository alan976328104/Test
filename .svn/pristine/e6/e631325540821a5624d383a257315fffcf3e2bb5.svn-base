package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Security;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_SecurityService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_SecurityController {
	@Autowired
	SVDS_SecurityService securityService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 查询全部密级
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getSecurityAll", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getSecurityAll() {
		List<SVDS_Security> securitys = securityService.getSecurityAll();
		JSONArray data = JSONArray.fromObject(securitys);
		return data;
	}

	/**
	 * 分页查询带条件
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param securityName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageSecurity",method = RequestMethod.POST)
	@ResponseBody
	public String pageSecurity(@RequestParam int pageNumber, int pageSize,
			String securityName) throws Exception {
		List<SVDS_Security> securitys = null;
		List<SVDS_Security> pageSecurity = null;
		if (securityName == "") {
			securitys = securityService.getSecurityAll();
			pageSecurity = securityService.getSecurityAll(pageNumber, pageSize);
		} else {
			securitys = securityService.listSecurityByName(securityName);
			pageSecurity = securityService
					.listSecurityByName(pageNumber, pageSize, securityName);
		}
		int total = securitys.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageSecurity);
		result.put("total", total);
		return result.toString();
	}

	/**
	 * 添加密级
	 * 
	 * @param SecurityName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertSecurity", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertSecurity(String securityName,HttpServletRequest request) throws UnknownHostException{
		SVDS_Security security = new SVDS_Security();
		security.setSecurityName(securityName);
		Integer securityId = securityService.insertSecurity(security);
		if (securityId > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了添加密级操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return security.getSecurityId();
		} else {
			return null;
		}
	}

	/**
	 * 根据Id查找密级
	 * 
	 * @param SecurityId
	 * @return
	 */
	@RequestMapping(value = "/getSecurityById", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Security getSecurityById(String securityId) {
		return securityService.getSecurityById(Integer.parseInt(securityId));
	}

	/**
	 * 根据ID修改密级
	 * 
	 * @param SVDS_Security
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateSecurity", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateSecurity(SVDS_Security security,HttpServletRequest request) throws UnknownHostException {
		if (securityService.updateSecurity(security) > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改密级操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return securityService.updateSecurity(security);
		}
		return null;
	}

	/**
	 * 删除密级
	 * 
	 * @param SecurityIds
	 * @return
	 */
	@RequestMapping(value = "/deleteSecurity", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteSecurity(
			@RequestParam(value = "securityIds[]", required = false, defaultValue = "") Integer[] SecurityIds) {
		List<Integer> ids = Arrays.asList(SecurityIds);
		if (securityService.deleteSecurity(ids) > 0) {
			return securityService.deleteSecurity(ids);
		}
		return null;
	}
}
