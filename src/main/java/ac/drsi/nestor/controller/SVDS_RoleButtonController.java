package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
import ac.drsi.nestor.entity.SVDS_Button;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_RoleButton;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_ButtonService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_RoleButtonService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;


@Controller
public class SVDS_RoleButtonController {
	@Autowired
	SVDS_RoleButtonService roleButtonService;
	@Autowired
	SVDS_ButtonService buttonService;
	@Autowired
	SVDS_UserService userService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_MenuService menuService;

	/**
	 * 根据角色Id查找按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getRoleButtonById", method = RequestMethod.POST)
	@ResponseBody
	public String getRoleButtonAll(Integer roleId) {
//		SVDS_User roleUser = userService.getUserById(userId);
		List<SVDS_RoleButton> RoleButtons = roleButtonService.getRoleButtonAll(roleId);
		JSONArray data = JSONArray.fromObject(RoleButtons);
		return data.toString();
	}
	
	/**
	 * 根据角色Id查找按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getRoleButtonByRoleId", method = RequestMethod.POST)
	@ResponseBody
	public String getRoleButtonByRoleId(Integer roleId,Integer menuId) {
//		SVDS_User roleUser = userService.getUserById(userId);
		List<SVDS_RoleButton> roleButtons = roleButtonService.listRoleButtonAllById(roleId,menuId);
		List<Integer> btnIds=new ArrayList<Integer>();
		if(roleButtons!=null&&roleButtons.size()>0){
			for (SVDS_RoleButton svds_RoleButton : roleButtons) {
				btnIds.add(svds_RoleButton.getBtnId());
			}
			List<SVDS_Button> btns=buttonService.listByButtonIds(btnIds);
			JSONArray data = JSONArray.fromObject(btns);
			return data.toString();
		}else{
			return null;
		}
	}
	/**
	 * 根据角色Id菜单Id查找按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getRoleButtonByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public String getRoleButtonAll(Integer roleId,Integer menuId) {
//		SVDS_User roleUser = userService.getUserById(userId);
		List<SVDS_RoleButton> roleButtons = roleButtonService.listRoleButtonAllById(roleId,menuId);
		JSONArray data = JSONArray.fromObject(roleButtons);
		return data.toString();
	}
	/**
	 * 根据角色Id菜单Id查找按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getRoleButtonByMId", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_Button> getRoleButtonByMId(Integer roleId,Integer menuId) {
//		SVDS_User roleUser = userService.getUserById(userId);
		System.out.println("角色Id："+roleId);
		System.out.println("菜单Id："+menuId);
		List<SVDS_RoleButton> roleButtons = roleButtonService.listRoleButtonAllById(roleId,menuId);
		List<Integer> rbtnIds=new ArrayList<Integer>();
		if(roleButtons!=null&&roleButtons.size()>0){
			for (SVDS_RoleButton svds_RoleButton : roleButtons) {
				rbtnIds.add(svds_RoleButton.getBtnId());
			}
		}
		if(rbtnIds.size()>0){
			List<SVDS_Button> buttons= buttonService.listByButtonIds(rbtnIds);
			
			JSONArray data = JSONArray.fromObject(buttons);
			return buttons;
		}else{
			return null;
		}
		
	}
	/**
	 * 查询用户操作权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getUserButton", method = RequestMethod.POST)
	@ResponseBody
	public String getUserButton(Integer userId) {
		List<Integer> btnIds = roleButtonService.listRoleButtonByUserId(userId);
		if(btnIds!=null&&btnIds.size()>0){
			List<SVDS_Button> btns = buttonService.getUserButton(btnIds);
			JSONArray array = JSONArray.fromObject(btns);
			if (array != null) {
				return array.toString();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 * 根据用户Id和菜单Id查询操作权限
	 * @param userId
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/getUserButtonByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public String getUserButtonByMenuId(Integer userId,Integer menuId) {
		List<Integer> btnIds = roleButtonService.listRoleButtonByUserIdMenuId(userId,menuId);
		if(btnIds!=null&&btnIds.size()>0){
			List<SVDS_Button> btns = buttonService.getUserButton(btnIds);
			JSONArray array = JSONArray.fromObject(btns);
			if (array != null) {
				return array.toString();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 * 先删除所拥有的按钮权限，再新增按钮权限
	 * 
	 * @param roleId
	 * @param btnIds
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/editRoleButton", method = RequestMethod.POST)
	@ResponseBody
	public String insertRoleButton(
			Integer roleId,
			@RequestParam(value = "menuIds[]", required = false, defaultValue = "") Integer[] menuIds,
			@RequestParam(value = "btnIds[]", required = false, defaultValue = "") Integer[] btnIds,HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_RoleButton roleButton = new SVDS_RoleButton();
		List<Integer> results = new ArrayList<Integer>();
		roleButtonService.deleteRoleButton(roleId);
		for (Integer i = 0; i < btnIds.length; i++) {
			for (Integer j = 0; j < menuIds.length; j++) {
			roleButton.setRoleId(roleId);
			roleButton.setBtnId(btnIds[i]);
			roleButton.setMenuId(menuIds[j]);
			Integer resultId = roleButtonService.insertRoleButton(roleButton);
			results.add(resultId);
			}
		}
		if (results.size() == btnIds.length) {
			logService.insertLog(new SVDS_Log("执行了编辑用户按钮权限操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return "ok";
		} else {
			logService.insertLog(new SVDS_Log("执行了编辑用户按钮权限操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"失败"));
			return null;
		}
	}
	/**
	 * 角色编辑权限
	 * @param params
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/editRoleButtonById", method = RequestMethod.POST)
	@ResponseBody
	public String editRoleButtonById(String params,Integer rId,HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		JSONArray jsonArray = JSONArray.fromObject(params);
		List<Integer> results = new ArrayList<Integer>();
		List<Integer> btnIds = new ArrayList<Integer>();
		System.out.println("角色Id："+rId);
		//roleButtonService.deleteRoleButton(rId);
		for (Object object : jsonArray) {
			JSONObject jsonObject=(JSONObject) object;
			JSONArray btnIdsArray=JSONArray.fromObject(jsonObject.get("btnIds"));
			if(btnIdsArray.size()>0){
				Integer roleId=Integer.parseInt(jsonObject.get("roleId").toString());
				Integer menuId=Integer.parseInt(jsonObject.get("menuId").toString());
				roleButtonService.deleteRoleButtonByMenuId(roleId,menuId);
				SVDS_RoleButton roleButton = new SVDS_RoleButton();
				for (int i = 0; i < btnIdsArray.size(); i++) {
					if(loginUser.getUserId()==4){//安全管理员
					SVDS_Menu menu=menuService.getMenuById(menuId);
					if(menu.getParentId()!=0){
						List<SVDS_Menu> menus=menuService.listChildrenMenu(menuId);
						for (SVDS_Menu svds_Menu : menus) {
								roleButton.setMenuId(svds_Menu.getId());
								roleButton.setRoleId(roleId);
								roleButton.setBtnId(Integer.parseInt(btnIdsArray.get(i).toString()));
								btnIds.add(Integer.parseInt(btnIdsArray.get(i).toString()));
								Integer resultId = roleButtonService.insertRoleButton(roleButton);
								results.add(resultId);
							}
						}else{
							roleButton.setMenuId(menuId);
							roleButton.setRoleId(roleId);
							roleButton.setBtnId(Integer.parseInt(btnIdsArray.get(i).toString()));
							btnIds.add(Integer.parseInt(btnIdsArray.get(i).toString()));
							Integer resultId = roleButtonService.insertRoleButton(roleButton);
							results.add(resultId);
						}
					
					}else{
						roleButton.setMenuId(menuId);
						roleButton.setRoleId(roleId);
						roleButton.setBtnId(Integer.parseInt(btnIdsArray.get(i).toString()));
						btnIds.add(Integer.parseInt(btnIdsArray.get(i).toString()));
						Integer resultId = roleButtonService.insertRoleButton(roleButton);
						results.add(resultId);
					}
				}
			}
			
		}
		if (results.size() == btnIds.size()) {
			logService.insertLog(new SVDS_Log("执行了编辑用户按钮权限操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return "ok";
		} else {
			logService.insertLog(new SVDS_Log("执行了编辑用户按钮权限操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"失败"));
			return null;
		}
	}

}
