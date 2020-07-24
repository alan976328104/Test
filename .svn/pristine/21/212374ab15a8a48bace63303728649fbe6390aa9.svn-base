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

import ac.drsi.nestor.entity.SVDS_Dept;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_DeptService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;

@Controller
public class SVDS_DeptController {
	@Autowired
	SVDS_DeptService deptService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 查询全部部门
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getDeptAll", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray getDeptAll() {
		List<SVDS_Dept> depts = deptService.getDeptAll();
		JSONArray data = JSONArray.fromObject(depts);
		//System.out.println(data.toString());
		return data;
	}

	/**
	 * 分页查询带条件
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param deptName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageDept", method = RequestMethod.POST)
	@ResponseBody
	public String pageDept(@RequestParam int pageNumber, int pageSize,
			String deptName) throws Exception {
		List<SVDS_Dept> depts = null;
		List<SVDS_Dept> pageDept = null;
		if (deptName == "") {
			depts = deptService.getDeptAll();
			pageDept = deptService.getDeptAll(pageNumber, pageSize);
		} else {
			depts = deptService.getDeptByName(deptName);
			pageDept = deptService
					.getDeptByName(pageNumber, pageSize, deptName);
		}
		int total = depts.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageDept);
		result.put("total", total);
		return result.toString();
	}

	/**
	 * 添加部门
	 * 
	 * @param deptName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertDept", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertDept(String deptName,HttpServletRequest request) throws UnknownHostException {
		SVDS_Dept dept = new SVDS_Dept();
		dept.setDeptName(deptName);
		Integer deptId = deptService.insertDept(dept);
		if (deptId > 0) {
			SVDS_User loginUser=sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了添加部门操作",DateUtils.getDate(),loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return dept.getDeptId();
		} else {
			return null;
		}
	}

	/**
	 * 根据Id查找部门
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/getDeptById", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Dept getDeptById(String deptId) {
		return deptService.getDeptById(Integer.parseInt(deptId));
	}

	/**
	 * 根据ID修改部门
	 * 
	 * @param dept
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateDept", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateDept(SVDS_Dept dept,HttpServletRequest request) throws UnknownHostException {
		if (deptService.updateDept(dept) > 0) {
			SVDS_User loginUser=sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了更新部门操作",DateUtils.getDate(),loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return deptService.updateDept(dept);
		}
		return null;
	}

	/**
	 * 删除部门
	 * 
	 * @param deptIds
	 * @return
	 */
	@RequestMapping(value = "/deleteDept", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteDept(
			@RequestParam(value = "deptIds[]", required = false, defaultValue = "") Integer[] deptIds) {
		List<Integer> ids = Arrays.asList(deptIds);
		if (deptService.deleteDept(ids) > 0) {
			return deptService.deleteDept(ids);
		}
		return null;
	}
}
