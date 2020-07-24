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

import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Major;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MajorService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;

@Controller
public class SVDS_MajorController {
	@Autowired
	SVDS_MajorService majorService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 查询全部角色
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMajorAll", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getMajorAll() {
		List<SVDS_Major> majors = majorService.getMajorAll();
		JSONArray data = JSONArray.fromObject(majors);
		return data;
	}

	/**
	 * 分页查询带条件
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param MajorName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageMajor",method = RequestMethod.POST)
	@ResponseBody
	public String pageMajor(@RequestParam int pageNumber, int pageSize,
			String majorName) throws Exception {
		List<SVDS_Major> majors = null;
		List<SVDS_Major> pageMajor = null;
		if (majorName == "") {
			majors = majorService.getMajorAll();
			pageMajor = majorService.getMajorAll(pageNumber, pageSize);
		} else {
			majors = majorService.getMajorByName(majorName);
			pageMajor = majorService
					.getMajorByName(pageNumber, pageSize, majorName);
		}
		int total = majors.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageMajor);
		result.put("total", total);
		return result.toString();
	}

	/**
	 * 添加专业
	 * 
	 * @param majorName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertMajor", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertMajor(String majorName,HttpServletRequest request) throws UnknownHostException {
		SVDS_Major major = new SVDS_Major();
		major.setMajorName(majorName);
		Integer majorId = majorService.insertMajor(major);
		if (majorId > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了添加专业操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return major.getMajorId();
		} else {
			return null;
		}
	}

	/**
	 * 根据Id查找专业
	 * 
	 * @param MajorId
	 * @return
	 */
	@RequestMapping(value = "/getMajorById", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Major getMajorById(String majorId) {
		return majorService.getMajorById(Integer.parseInt(majorId));
	}

	/**
	 * 根据ID修改专业
	 * 
	 * @param SVDS_Major
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateMajor", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateMajor(SVDS_Major major,HttpServletRequest request) throws UnknownHostException {
		if (majorService.updateMajor(major) > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改专业操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return majorService.updateMajor(major);
		}
		return null;
	}

	/**
	 * 删除专业
	 * 
	 * @param MajorIds
	 * @return
	 */
	@RequestMapping(value = "/deleteMajor", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteMajor(
			@RequestParam(value = "majorIds[]", required = false, defaultValue = "") Integer[] MajorIds) {
		List<Integer> ids = Arrays.asList(MajorIds);
		if (majorService.deleteMajor(ids) > 0) {
			return majorService.deleteMajor(ids);
		}
		return null;
	}
}
