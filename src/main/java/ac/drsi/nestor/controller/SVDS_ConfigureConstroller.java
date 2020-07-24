package ac.drsi.nestor.controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.entity.SVDS_Configure;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_ConfigureService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_ConfigureConstroller {
	@Autowired
	SVDS_ConfigureService configureService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;
	/**
	 * 获取系统名称
	 * @return
	 */
	@RequestMapping(value = "/getDataName", method = RequestMethod.POST)
	@ResponseBody
	public String getDataName(){
		SVDS_Configure configure= configureService.getConfigure();
		JSONObject json = JSONObject.fromObject(configure);//将java对象转换为json对象
		return json.toString();
	}
	/**
	 * 更新系统消息
	 * @param dataName
	 * @param infoError
	 * @param info
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateDataName", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateDataName(String dataName,String infoError,String info,HttpServletRequest request) throws UnknownHostException{
		SVDS_Configure configure= new SVDS_Configure();
		configure.setDataName(dataName);
		configure.setInfoError(infoError);
		configure.setInfo(info);
		System.out.println(infoError);
		System.out.println(info);
		Integer isSuccess=configureService.updateConfigure(configure);
		if(isSuccess>0){
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("修改系统配置操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
		}
		return isSuccess;
	}
}
