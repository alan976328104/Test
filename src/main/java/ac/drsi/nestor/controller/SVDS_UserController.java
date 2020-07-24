package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.common.StringUtil;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Role;
import ac.drsi.nestor.entity.SVDS_RoleMenu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SVDS_UserAll;
import ac.drsi.nestor.service.SVDS_DeptService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MajorService;
import ac.drsi.nestor.service.SVDS_RoleMenuService;
import ac.drsi.nestor.service.SVDS_RoleService;
import ac.drsi.nestor.service.SVDS_SecurityService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;

@Controller
public class SVDS_UserController {
	@Autowired
	SVDS_UserService userService;
	@Autowired
	SVDS_RoleService roleService;
	@Autowired
	SVDS_DeptService deptService;
	@Autowired
	SVDS_MajorService majorService;
	@Autowired
	SVDS_RoleMenuService roleMenuService;
	@Autowired
	SVDS_SecurityService securityService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 根据名称模糊查询
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/userByName", method = RequestMethod.GET)
	@ResponseBody
	public String getUserByName(String username) {
		List<SVDS_User> users = userService.getUserByName(username);
		JSONArray data = JSONArray.fromObject(users);
		JSONObject result = new JSONObject();
		result.put("rows", data);
		result.put("total", users.size());
		return result.toString();
	}

	/**
	 * 根据部门名称模糊查询
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/userByDeptName", method = RequestMethod.POST)
	@ResponseBody
	public String getUserByDeptName(String deptname) {
		if (deptname != "") {
			System.out.println(13);
			List<SVDS_UserAll> users = userService.listUserByDeptName(deptname);
			JSONArray data = JSONArray.fromObject(users);
			return data.toString();
		} else {
			return null;
		}

	}

	/**
	 * 根据部门名称模糊查询
	 * 
	 * @param username
	 *            ,deptname
	 * @return
	 */
	@RequestMapping(value = "/pageUserByDeptName", method = RequestMethod.POST)
	@ResponseBody
	public String pageUserByDeptName(@RequestParam int pageNumber,
			int pageSize, String deptname, String username) {
		System.out.println(deptname);
		List<SVDS_User> users = userService.listUserByDeptName(pageNumber,
				pageSize, deptname, username);
		int total = users.size();
		JSONObject result = new JSONObject();
		result.put("rows", users);
		result.put("total", total);
		return result.toString();
	}

	/**
	 * 根据部门名称模糊查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listUserBySys", method = RequestMethod.POST)
	@ResponseBody
	public String listUserBySys(@RequestParam int pageNumber, int pageSize) {
		List<SVDS_User> users = userService.listUserBySys(pageNumber, pageSize);
		int total = users.size();
		JSONObject result = new JSONObject();
		result.put("rows", users);
		result.put("total", total);
		return result.toString();
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pageUser", method = RequestMethod.POST)
	@ResponseBody
	public String pageInfo(@RequestParam int pageNumber, int pageSize,
			String username) {
		List<SVDS_User> users = null;
		List<SVDS_User> pageInfo = null;
		if (username == "" || username == null) {
			users = userService.getUserAll();
			pageInfo = userService.getUserAll(pageNumber, pageSize);
		} else {
			users = userService.getUserByName(username);
			pageInfo = userService
					.getUserByName(pageNumber, pageSize, username);
		}
		int total = users.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		// System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * 添加用户
	 * 
	 * @param username
	 * @param phone
	 * @param sex
	 * @param roleId
	 * @param deptId
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertUser(String jsonstr, Integer roleId,
			HttpServletRequest request) throws UnknownHostException {
		JSONArray data = JSONArray.fromObject(jsonstr);
		SVDS_Role role = roleService.getRoleById(roleId);
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		return userService.insertUser(data, role, loginUser, request);
	}

	/**
	 * 根据Id查找用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getUserById", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_User getUserById(String userId, HttpSession session) {
		session.setMaxInactiveInterval(18000);
		SVDS_User user = userService.getUserById(Integer.parseInt(userId));
		session.setAttribute("user", user);
		return user;
	}

	/**
	 * 根据查找session里的用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_User getUser(HttpSession session, HttpServletRequest request) {
		SVDS_User user = (SVDS_User) session.getAttribute("user");
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		System.out.println(loginUser.toString());
		return user;
	}

	/**
	 * 根据角色Id查找用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/listUserByRoleId", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_User> listUserByRoleId(String roleId) {
		List<SVDS_User> user = userService.listUserByRoleId(Integer
				.parseInt(roleId));
		return user;
	}

	/**
	 * 根据密级查找用户
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/listUserBySecretlevel", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_User> listUserBySecretlevel(
			String deptname,
			Integer id,
			@RequestParam(value = "menuIds[]", required = false, defaultValue = "") Integer[] menuIds,
			HttpServletRequest request) {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		List<Integer> roleIds = new ArrayList<Integer>();
		List<Integer> ids = Arrays.asList(menuIds);
		System.out.println(ids);
		List<SVDS_RoleMenu> roleMenus = roleMenuService.listByMenuIds(ids);
		if (roleMenus != null) {
			for (SVDS_RoleMenu svds_RoleMenu : roleMenus) {
				roleIds.add(svds_RoleMenu.getRoleId());
			}
		}
		List<SVDS_User> users = userService.listUserBySecretlevel(deptname, id,
				loginUser.getUserId(), roleIds);
		if (users.size() > 0) {
			System.out.println(users.toString());
			return users;
		} else {
			System.out.println("无数据");
			return users;
		}
	}

	/**
	 * 根据ID修改用户
	 * 
	 * @param user
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateUser(String editUserId, String editUserName,
			String roleId, HttpServletRequest request)
			throws UnknownHostException {
		System.out.println(editUserId);
		System.out.println(editUserName);
		System.out.println(roleId);
		SVDS_User user = new SVDS_User();
		if (!StringUtils.isEmpty(editUserId)) {
			user.setUserId(StringUtil.stringToUInt(editUserId));
		}
		if (!StringUtils.isEmpty(editUserName)) {
			user.setUsername(editUserName);
		}
		if (!StringUtils.isEmpty(roleId)) {
			SVDS_Role role = roleService.getRoleById(StringUtil
					.stringToUInt(roleId));
			user.setRole(role);
		}
		if (userService.updateUser(user) > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改" + editUserName + "用户操作",
					DateUtils.getDate(), loginUser, IpAddressUtils
							.getIpAddress(request), "成功"));
			return userService.updateUser(user);
		}
		return null;
	}

	/**
	 * 角色分配用户
	 * 
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	@RequestMapping(value = "/updateRoleUser", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateRoleUser(
			Integer roleId,
			@RequestParam(value = "userIds[]", required = false, defaultValue = "") Integer[] userIds) {
		System.out.println(roleId);
		List<Integer> ids = Arrays.asList(userIds);
		System.out.println(ids.toString());
		Integer isSuccess = userService.updateRoleUser(roleId, ids);
		if (isSuccess > 0) {
			return 1;
		}
		return null;
	}

	/**
	 * 删除用户
	 * 
	 * @param userIds
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteUser(
			@RequestParam(value = "userIds[]", required = false, defaultValue = "") Integer[] userIds,
			HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		List<Integer> ids = Arrays.asList(userIds);
		if (userService.deleteUser(ids, loginUser, request) > 0) {
			return 1;
		}
		return null;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param pwd
	 * @param request
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/updateUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateUserPwd(String pwd, HttpServletRequest request)
			throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		loginUser.setPwd(pwd);
		Integer isSuccess = userService.updateUser(loginUser);
		if (isSuccess > 0) {
			logService.insertLog(new SVDS_Log("执行了修改密码操作", DateUtils.getDate(),
					loginUser, IpAddressUtils.getIpAddress(request), "成功"));
			return isSuccess;
		} else {
			return 0;
		}
	}
}
