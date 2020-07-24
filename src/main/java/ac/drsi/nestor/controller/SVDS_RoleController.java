package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
import ac.drsi.nestor.entity.SVDS_Role;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_RoleService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;

@Controller
public class SVDS_RoleController {
	@Autowired
	SVDS_RoleService roleService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_UserService userService;

	/**
	 * 查询全部角色
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getRoleAll", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_Role> getRoleAll() {
		List<SVDS_Role> roles = roleService.getRoleAll();
		if(roles!=null){
			return roles;
		}else{
			return null;
		}
	}
	/**
	 * 根据用户Id查询角色
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listRoleByUserId", method = RequestMethod.POST)
	@ResponseBody
	public String listRoleByUserId(Integer userId) {
		List<SVDS_Role> roles = roleService.listRoleByUserId(userId);
		if(roles!=null&&roles.size()>0){
			JSONArray data = JSONArray.fromObject(roles);
			System.out.println(data.toString());
			return data.toString();
		}else{
			return "";
		}
	}
	/**
	 * 分页查询 带条件
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param roleName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageRole", produces = "html/text;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String pageRole(@RequestParam int pageNumber, int pageSize,
			String roleName) throws Exception {
		List<SVDS_Role> roles = new ArrayList<SVDS_Role>();
		List<SVDS_Role> pageRole = new ArrayList<SVDS_Role>();
		if (roleName == "" || roleName == null) {
			roles = roleService.getRoleAll();
			pageRole = roleService.getRoleAll(pageNumber, pageSize);
		} else {
			roles = roleService.getRoleByNames(roleName);
			pageRole = roleService.getRoleByNames(pageNumber, pageSize, roleName);
		}
		int total = roles.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageRole);
		result.put("total", total);
		System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * 添加角色
	 * 
	 * @param roleName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertRole", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertRole(String roleName, String describe, Integer userId,HttpServletRequest request) throws UnknownHostException {
		System.out.println(roleName);
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_Role role=roleService.getRoleByName(roleName);
		if(role==null){
			Integer roleId = roleService.insertRole(roleName, describe, loginUser.getUserId());
			if (roleId > 0) {
				logService.insertLog(new SVDS_Log("执行了添加角色操作", DateUtils.getDate(),
						loginUser,IpAddressUtils.getIpAddress(request),"成功"));
				return roleId;
			} else {
				return null;
			}
		}else{
			return 0;
		}
	}
	/**
	 * 添加专业角色
	 * 
	 * @param roleName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/inserMajortRole", method = RequestMethod.POST)
	@ResponseBody
	public Integer inserMajortRole(String roleName, String describe, Integer userId,HttpServletRequest request) throws UnknownHostException {
		System.out.println(roleName);
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_Role role=roleService.getRoleByMajorName(roleName);
		if(role==null){
			Integer roleId = roleService.insertRole(roleName, describe, loginUser.getUserId());
			if (roleId > 0) {
				logService.insertLog(new SVDS_Log("执行了添加专业角色操作", DateUtils.getDate(),
						loginUser,IpAddressUtils.getIpAddress(request),"成功"));
				return roleId;
			} else {
				return null;
			}
		}else{
			return 0;
		}
	}
	/**
	 * 根据Id查找角色
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getRoleById", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Role getRoleById(Integer roleId) {
		return roleService.getRoleById(roleId);
	}

	/**
	 * 根据名称查找角色
	 * 
	 * @param roleName
	 * @return
	 */
	@RequestMapping(value = "/getRoleByName", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Role getRoleByName(String roleName) {
		return roleService.getRoleByName(roleName);
	}

	/**
	 * 根据ID修改角色
	 * 
	 * @param role
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateRole(SVDS_Role role,Integer isChange,HttpServletRequest request) throws UnknownHostException {
		System.out.println(isChange);
		Integer isSuccess;
		if(isChange!=0){
			SVDS_Role existRole=roleService.getRoleByName(role.getRoleName());
			if(existRole!=null){
				return 0;
			}else{
				isSuccess=roleService.updateRole(role);
			}
		}else{
			isSuccess=roleService.updateRole(role);
		}
		//SVDS_Role existRole=roleService.getRoleByName(role.getRoleName());
		//if(existRole==null){
			if (isSuccess > 0) {
				SVDS_User loginUser = sessionService.getSessionByIp(request);
				logService.insertLog(new SVDS_Log("执行了修改角色操作", DateUtils.getDate(),
						loginUser,IpAddressUtils.getIpAddress(request),""));
				return roleService.updateRole(role);
			}else{
				return null;
			}
		//}else{
		//	return 0;
		//}
		
	}

	/**
	 * 根据ID删除角色
	 * 
	 * @param roleIds
	 * @return
	 */
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteRole(
			@RequestParam(value = "roleIds[]", required = false, defaultValue = "") Integer[] roleIds) {
		System.out.println(roleIds);
		List<Integer> ids = Arrays.asList(roleIds);
			return roleService.deleteRole(ids);
	}
}
