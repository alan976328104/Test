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
import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_RoleMenu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_RoleMenuService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;

@Controller
public class SVDS_MenuController {
	@Autowired
	SVDS_MenuService menuService;
	@Autowired
	SVDS_UserService userService;
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_RoleMenuService roleMenuService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 查询用户数据权限
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getUserMenu", method = RequestMethod.POST)
	@ResponseBody
	public String getUserMenu(Integer userId) {
		SVDS_User roleUser = userService.getUserById(userId);
		List<SVDS_RoleMenu> roleMenus = roleMenuService.getRoleMenuAll(roleUser
				.getRole().getRoleId());
		List<Integer> menuIds = new ArrayList<Integer>();
		for (int i = 0; i < roleMenus.size(); i++) {
			Integer menuId = roleMenus.get(i).getMenuId();
			SVDS_Menu menu=menuService.getMenuById(menuId);
			if(menu.getState()==0){
				System.out.println(menu.getState());
				menuIds.add(menuId);
			}
		}
		System.out.println(menuIds.size());
		System.out.println(menuIds.toString());
		List<SVDS_Menu> menus = menuService.getUserMenu(menuIds);
		JSONArray array = JSONArray.fromObject(menus);
		System.out.println(array);
		if (array != null) {
			return array.toString();
		}
		return null;
	}
	/**
	 * 回收站里的菜单
	 * @return
	 */
	@RequestMapping(value = "/getDelMenu", method = RequestMethod.POST)
	@ResponseBody
public String getDelMenu(){
		List<SVDS_Menu> menus=menuService.listMenuByState();
		if(menus!=null&&menus.size()>0){
			JSONArray array = JSONArray.fromObject(menus);
			return array.toString();
		}
		return null;
}
	/**
	 * 获取等级为1的子菜单
	 * @param userId
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/getMenu", method = RequestMethod.POST)
	@ResponseBody
	public String getMenu(Integer userId, Integer menuId) {
		System.out.println("菜单Id"+menuId);
		SVDS_User roleUser = userService.getUserById(userId);
		List<SVDS_RoleMenu> roleMenus = roleMenuService.getRoleMenuAll(roleUser
				.getRole().getRoleId());
		List<Integer> menuIds = new ArrayList<Integer>();
		//List<SVDS_Menu> menuChildrens = new ArrayList<SVDS_Menu>();
		List<SVDS_Menu> menuList = new ArrayList<SVDS_Menu>();
		SVDS_Menu menu = new SVDS_Menu();
		SVDS_Menu menuParent = new SVDS_Menu();
		for (int i = 0; i < roleMenus.size(); i++) {
			Integer id = roleMenus.get(i).getMenuId();
			menuIds.add(id);
		}
		System.out.println(menuIds.toString());
	//	List<SVDS_Menu> menus = menuService.getUserMenu(menuIds);
		menu = menuService.getMenuById(menuId);
		if (menu != null) {
			menuParent = getLv(menu);
		}
		//System.out.println(menuService.getMenuById(menuParent.getId()));
		menuList = menuService.getMenuById(menuParent.getId()).getChildren();
		/*
		 * if(menu.getLv()!=1){ menu =
		 * menuService.getMenuById(menu.getParentId()); }else{
		 * 
		 * }
		 */
		/*
		 * List<SVDS_Menu> menuList=new ArrayList<SVDS_Menu>(); for (int i = 0;
		 * i < menuChildrens.size(); i++) { if (menuChildrens.get(i).getId() ==
		 * menu.getParentId()) { menuList.add(menuChildrens.get(i)); } }
		 */
		List<Integer> mIdAll=new ArrayList<Integer>();
		for (SVDS_Menu svds_Menu : menuList) {
			for (Integer mId : menuIds) {
				if(svds_Menu.getId().equals(mId)){
					mIdAll.add(mId);
				}
			}
		}
		List<SVDS_Menu> menuAll=new ArrayList<SVDS_Menu>();
		menuAll=menuService.listMenuByIds(mIdAll);
		System.out.println(menuAll.toString());
		if (menuAll.size()>0) {
			JSONArray array = JSONArray.fromObject(menuAll);
			return array.toString();
		}else{
			return null;
		}
	}
/**
 * 获取等级为1的菜单
 * @param menu
 * @return
 */
	public SVDS_Menu getLv(SVDS_Menu menu) {
		if (menu.getLv() != 1 && menu.getLv() != 0) {
			return getLv(menuService.getMenuById(menu.getParentId()));
		} else {
			return menu;
		}
	}

	/**
	 * 搜索菜单
	 * 
	 * @param userId
	 * @param text
	 * @return
	 */
	@RequestMapping(value = "/searchMenu", method = RequestMethod.POST)
	@ResponseBody
	public String searchMenu(Integer userId, String text) {
		SVDS_User roleUser = userService.getUserById(userId);
		List<SVDS_RoleMenu> roleMenus = roleMenuService.getRoleMenuAll(roleUser
				.getRole().getRoleId());
		List<Integer> menuIds = new ArrayList<Integer>();
		for (int i = 0; i < roleMenus.size(); i++) {
			Integer menuId = roleMenus.get(i).getMenuId();
			menuIds.add(menuId);
		}
		List<SVDS_Menu> menus = menuService.searchMenuById(menuIds, text);
		JSONArray array = JSONArray.fromObject(menus);
		if (array != null) {
			return array.toString();
		}
		return null;
	}

	/**
	 * 查询全部菜单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getMenuAll", method = RequestMethod.POST)
	@ResponseBody
	public String getMenuAll() {
		List<SVDS_Menu> menus = menuService.getMenuAll();
		JSONArray data = JSONArray.fromObject(menus);
		System.out.println(data.toString());
		if (menus != null) {
			return data.toString();
		}
		return null;
	}

	/**
	 * 分页查询 带条件
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param menuName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageMenu", method = RequestMethod.POST)
	@ResponseBody
	public String pageMenu() throws Exception {
		List<SVDS_Menu> menus = null;
		// List<SVDS_Menu> pageMenu = null;
		menus = menuService.listMenuAll();
		// if (menuName == "") {
		// menus = menuService.listMenuAll();
		// pageMenu = menuService.listMenuAll(pageNumber, pageSize);
		// } else {
		// menus = menuService.getMenuByName(menuName);
		// pageMenu = menuService
		// .getMenuByName(pageNumber, pageSize, menuName);
		// }
		int total = menus.size();
		JSONObject result = new JSONObject();
		result.put("rows", menus);
		result.put("total", total);
		return result.toString();
	}

	/**
	 * 添加菜单
	 * 
	 * @param menuName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertMenu", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertmenu(String menuName, Integer state,
			HttpServletRequest request) throws UnknownHostException {
		SVDS_Menu menu = new SVDS_Menu();
		menu.setText(menuName);
		menu.setLv(state);
		Integer menuId = menuService.insertMenu(menu);
		if (menuId > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了添加菜单操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return menu.getId();
		} else {
			return null;
		}
	}

	/**
	 * 根据Id查找菜单
	 * 
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/getmenuById", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Menu getMenuById(String menuId) {
		return menuService.getMenuById(Integer.parseInt(menuId));
	}
/**
 * 获取点击的菜单的父级名称
 * @param menuId
 * @return
 */
	@RequestMapping(value = "/getMenuTitle", method = RequestMethod.POST)
	@ResponseBody
	public String getMenuTitle(Integer menuId) {
		SVDS_Menu menu = menuService.getMenuById(menuId);
		System.out.println(menu);
		StringBuffer title= new StringBuffer();
		String parentTitle=getMenuParent(menu,title);
		return parentTitle;
	}
/**
 * 菜单名称追加
 * @param menu
 * @param title
 * @return
 */
	public String getMenuParent(SVDS_Menu menu,StringBuffer title){
		if(menu.getParentId()!=0){
			title=title.insert(0, menu.getName()+">");
			return getMenuParent(menuService.getMenuById(menu.getParentId()),title);
		}else{
			title=title.insert(0, menu.getName()+">");
			title=title.deleteCharAt(title.length()-1);
			return title.toString();
		}
	}
	/**
	 * 根据ID修改菜单
	 * 
	 * @param menu
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateMenu(Integer menuId, String name,
			HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		//String lang = request.getSession().getAttribute("lang").toString();
		SVDS_Menu menu = menuService.getMenuById(menuId);
		//if (lang.equals("en_US")) {
		//	if (menu.getEname() != "") {
		//		menu.setEname(name);
		//	}
		//} else {
			menu.setText(name);
		//}
		System.out.println(name);
		System.out.println(loginUser);
		Integer sussecc = menuService.updateMenu(menu);
		if (sussecc > 0) {
			menuService.updateFilesLocationByMenuId(menuId,menu.getName(),name);
			logService.insertLog(new SVDS_Log("执行了修改菜单名称操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return sussecc;
		}
		return null;
	}

	/**
	 * 根据ID删除菜单
	 * 
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
	@ResponseBody
	public Integer deletemenu(
			@RequestParam(value = "menuIds[]", required = false, defaultValue = "") Integer[] menuIds) {
		List<Integer> ids = Arrays.asList(menuIds);
		if (menuService.deleteMenu(ids) > 0) {
			return menuService.deleteMenu(ids);
		}
		return null;
	}
	/**
	 * 查询各专业的数据占比图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listMenu", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listMenu(){
		 
		return menuService.listMenu();
	}
	/**
	 * 查询各专业例题数目占比图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listMenuCount", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listMenuCount(){
		 
		return menuService.listMenuCount();
	}
	
	/**
	 * 查询某专业下例题个数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listMenuCountByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public Integer listMenuCountByMenuId(@RequestParam Integer menuId){
		Integer count=menuService.listMenuByParentId(menuId).size();
		return count;
	}
	
	/**
	 * 系统标签数目各专业占比及数目
	 * @return
	 */
	@RequestMapping(value = "/listBySysAlias", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listBySysAlias(){
		 
		return menuService.listBySysAlias();
	}
	
	
}
