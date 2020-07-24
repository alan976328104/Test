package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.entity.SVDS_Button;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_ButtonService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_ButtonController {
	@Autowired
	SVDS_ButtonService ButtonService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 查询全部按钮
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getButtonAll", method = RequestMethod.POST)
	@ResponseBody
	public String getButtonAll() {
		List<SVDS_Button> Buttons = ButtonService.getButtonAll();
		JSONArray data = JSONArray.fromObject(Buttons);
		return data.toString();
	}
	/**
	 * 根据名称查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getButtonByName", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Button getbuttonByName(String btnName) {
		SVDS_Button button=ButtonService.getButtonByName(btnName);
		return button;
	}
	/**
	 * 添加按钮
	 * 
	 * @param btnName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertButton", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertButton(String btnName,HttpServletRequest request) throws UnknownHostException {
		SVDS_Button button = new SVDS_Button();
		button.setBtnName(btnName);
		Integer btnId = ButtonService.insertButton(button);
		if (btnId > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了添加权限按钮操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return button.getBtnId();
		} else {
			return null;
		}
	}

	/**
	 * 根据ID修改按钮
	 * 
	 * @param SVDS_Button
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateButton", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateButton(SVDS_Button button,HttpServletRequest request) throws UnknownHostException {
		if (ButtonService.updateButton(button) > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改权限按钮操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return ButtonService.updateButton(button);
		}
		return null;
	}

	/**
	 * 根据ID删除按钮
	 * 
	 * @param buttonIds
	 * @return
	 */
	@RequestMapping(value = "/deleteButton", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteButton(
			@RequestParam(value = "btnIds[]", required = false, defaultValue = "") Integer[] btnIds) {
		List<Integer> ids = Arrays.asList(btnIds);
		if (ButtonService.deleteButton(ids) > 0) {
			return ButtonService.deleteButton(ids);
		}
		return null;
	}
}
