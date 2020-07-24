package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_RoleMenu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_RoleMenuService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_RoleMenuController {
	@Autowired
	SVDS_RoleMenuService roleMenuService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_MenuService menuService;

	/**
	 * 根据角色Id查找权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "/getRoleMenuById", method = RequestMethod.POST)
	@ResponseBody
	public String getRoleMenuAll(Integer roleId) {
		List<SVDS_RoleMenu> roleMenus = roleMenuService.getRoleMenuAll(roleId);
		JSONArray data = JSONArray.fromObject(roleMenus);
		return data.toString();
	}

	/**
	 * 先删除所拥有的权限，再编辑权限
	 * 
	 * @param roleId
	 * @param menuIds
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/editRoleMenu", method = RequestMethod.POST)
	@ResponseBody
	public String insertRoleMenu(
			Integer roleId,
			@RequestParam(value = "menuIds[]", required = false, defaultValue = "") Integer[] menuIds,HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_RoleMenu roleMenu = new SVDS_RoleMenu();
		List<Integer> results = new ArrayList<Integer>();
		roleMenuService.deleteRoleMenu(roleId);
		for (Integer i = 0; i < menuIds.length; i++) {
			
			if(loginUser.getUserId()==4){//安全管理员
				SVDS_Menu menu=menuService.getMenuById(menuIds[i]);
				if(menu.getParentId()!=0){
					List<SVDS_Menu> menus=menuService.listChildrenMenu(menuIds[i]);
					for (SVDS_Menu svds_Menu : menus) {
						roleMenu.setRoleId(roleId);
						roleMenu.setMenuId(svds_Menu.getId());
						Integer resultId = roleMenuService.insertRoleMenu(roleMenu);
						results.add(resultId);
					}
				}else{
					roleMenu.setRoleId(roleId);
					roleMenu.setMenuId(menuIds[i]);
					Integer resultId = roleMenuService.insertRoleMenu(roleMenu);
					results.add(resultId);
				}
			}else{
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuIds[i]);
				Integer resultId = roleMenuService.insertRoleMenu(roleMenu);
				results.add(resultId);
			}
		}
		System.out.println("修改角色权限");
		if (results.size() == menuIds.length) {
			
			logService.insertLog(new SVDS_Log("执行了编辑用户权限操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return "ok";
		} else {
			return null;
		}
	}

}
