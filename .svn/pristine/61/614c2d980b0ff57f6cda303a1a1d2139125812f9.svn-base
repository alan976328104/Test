package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
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
import ac.drsi.nestor.dao.FolderUpDao;
import ac.drsi.nestor.dao.TabsDao;
import ac.drsi.nestor.entity.SVDS_Alias;
import ac.drsi.nestor.entity.SVDS_AliasFile;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_RoleMenu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.Tabs;
import ac.drsi.nestor.service.SVDS_AliasFileService;
import ac.drsi.nestor.service.SVDS_AliasService;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_RoleMenuService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;

@Controller
public class SVDS_AliasController {
	@Autowired
	SVDS_AliasService aliasService;
	@Autowired
	SVDS_AliasFileService aliasFileService;
	@Autowired
	SVDS_MenuService menuService;
	@Autowired
	SVDS_RoleMenuService roleMenuService;
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_UserService userService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	FolderUpDao dao;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	TabsDao tabsDao;

	/**
	 * 查询用户的标签
	 * 
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/aliasByUser", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_Alias> getAliasByUser(Integer userId,
			HttpServletRequest request) {
		List<SVDS_Alias> aliasUser = aliasService.listAliasByUser(userId);
		System.out.println(userId);
		System.out.println(aliasUser.size());
		return aliasUser;
	}

	/**
	 * 根据标签ID查询
	 * 
	 * @param aliasIds
	 * @return
	 */
	@RequestMapping(value = "/getByAliasById", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_Alias> listByAliasByIds(
			@RequestParam(value = "aliasIds[]", required = false, defaultValue = "") Integer[] aliasIds) {
		List<Integer> ids = Arrays.asList(aliasIds);
		List<SVDS_Alias> alias = aliasService.listAliasByIds(ids);
		System.out.println(alias.toString());
		return alias;
	}

	/**
	 * 根据标签名称查询
	 * 
	 * @param aliasIds
	 * @return
	 */
	@RequestMapping(value = "/searchAliasByName", method = RequestMethod.POST)
	@ResponseBody
	public String getAliasByName(Integer searchType, String searchVal,
			Integer userId) {
		SVDS_User roleUser = userService.getUserById(userId);
		List<Integer> menuIds = new ArrayList<Integer>();
		List<SVDS_Menu> menus = new ArrayList<SVDS_Menu>();
		List<SVDS_Menu> menuNew = new ArrayList<SVDS_Menu>();
		List<SVDS_RoleMenu> roleMenus = roleMenuService.getRoleMenuAll(roleUser
				.getRole().getRoleId());
		if (searchType == 0) {// 项目名
			// System.out.println(123);
			if (roleMenus.size() > 0) {
				for (int i = 0; i < roleMenus.size(); i++) {
					Integer menuId = roleMenus.get(i).getMenuId();
					menuIds.add(menuId);
				}
				menus = menuService.searchMenuById(menuIds, searchVal);
				System.out.println(menus.size());
			}
		} else if (searchType == 1) {// 标签
			List<SVDS_Alias> alias = aliasService.listAliasByUserName(
					searchVal, userId);
			System.out.println(searchVal);
			System.out.println("标签");
			List<Integer> aliasIds = new ArrayList<Integer>();
			if (alias != null) {
				for (SVDS_Alias svds_alias : alias) {
					aliasIds.add(svds_alias.getAliasId());
				}
			}
			if (aliasIds.size() > 0) {
				List<SVDS_AliasFile> aliasFile = aliasFileService
						.listByAliasIds(aliasIds);
				System.out.println(aliasIds.toString());
				List<Integer> fileIds = new ArrayList<Integer>();
				List<Integer> mIds = new ArrayList<Integer>();
				if (aliasFile != null && aliasFile.size() > 0) {
					for (int i = 0; i < aliasFile.size(); i++) {
						Integer fileId = aliasFile.get(i).getFiles()
								.getFileId();
						fileIds.add(fileId);
					}
					List<SVDS_Files> files = filesService
							.getFilesByIds(fileIds);
					if (files != null && files.size() > 0) {
						for (int i = 0; i < files.size(); i++) {
							Integer menuId = files.get(i).getMenu().getId();
							menuIds.add(menuId);
						}
					}
					if (roleMenus.size() > 0) {
						for (int i = 0; i < roleMenus.size(); i++) {
							for (int j = 0; j < menuIds.size(); j++) {
								if (roleMenus.get(i).getMenuId() == menuIds
										.get(j)) {
									mIds.add(menuIds.get(j));
								}
							}

						}
					}
					menus = menuService.listMenuByIds(mIds);
				}
			}
		} else {// 基本信息

			// System.out.println(123);
			if (roleMenus.size() > 0) {
				for (int i = 0; i < roleMenus.size(); i++) {
					Integer menuId = roleMenus.get(i).getMenuId();
					menuIds.add(menuId);
				}

				// System.out.println(456);
				menus = menuService.searchMenuByInfo(menuIds, searchVal);
			}
		}

		for (int i = 0; i < menus.size(); i++) {
			for (int j = 0; j < menus.size(); j++) {
				if (menus.get(i).getId() == menus.get(j).getParentId()) {
					menuNew.add(menus.get(i));
				}
			}
		}
		menus.removeAll(menuNew);

		// System.out.println(555);
		JSONArray array = JSONArray.fromObject(menus);
		System.out.println(array);
		if (array != null) {
			return array.toString();
		}
		return null;
	}

	/**
	 * 添加标签
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addAlias", method = RequestMethod.POST)
	@ResponseBody
	public Integer addAlias(String aliasName, Integer aliasType,
			Integer parentId, HttpServletRequest request) throws Exception {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_Alias alias = new SVDS_Alias();
		SVDS_Alias aliasParent = aliasService.getAliasById(parentId);
		SVDS_Alias aliasExist = aliasService.getAliasByUserName(aliasName,
				loginUser.getUserId(), parentId);
		if (aliasExist == null) {
			String date = DateUtils.getDate();
			alias.setAliasName(aliasName);
			alias.setAliasDate(date);
			alias.setAliasType(aliasType);
			alias.setParentId(parentId);
			alias.setLv(aliasParent.getLv() + 1);
			if (aliasType == 1) {
				alias.setIcon("../assets/images/alias.png");
			} else {
				alias.setIcon("../assets/images/alias.png");
				alias.setUser(userService.getUserById(loginUser.getUserId()));
			}
			Integer aliasId = aliasService.insertAlias(alias);
			if (aliasId > 0) {
				logService.insertLog(new SVDS_Log("执行了添加标签操作", DateUtils
						.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
				return alias.getAliasId();
			} else {
				return null;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 文件添加标签
	 * 
	 * @param fileName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertAlias", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertAlias(String aliasName, Integer aliasType,
			Integer fileId, Integer aliasId, HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_Alias alias = new SVDS_Alias();
		SVDS_AliasFile aliasFile = new SVDS_AliasFile();
		SVDS_Files file = filesService.getFilesById(fileId);
		if (aliasId == null) {// 新增
			System.out.println("新增的标签");
			String date = DateUtils.getDate();
			alias.setAliasName(aliasName);
			alias.setAliasDate(date);
			alias.setAliasType(aliasType);
			alias.setParentId(aliasType);
			alias.setLv(1);
			if (aliasType == 1) {
				alias.setIcon("../assets/images/alias.png");
			} else {
				alias.setIcon("../assets/images/alias.png");
				alias.setUser(userService.getUserById(loginUser.getUserId()));
			}
			Integer isOk = aliasService.insertAlias(alias);
			if (isOk > 0) {
				aliasFile.setFiles(filesService.getFilesById(fileId));
				aliasFile.setAlias(alias);
				aliasFile.setAliasDate(DateUtils.getDate());
				Integer isSuccess = aliasFileService.insertAliasFile(aliasFile);
				if (isSuccess > 0) {
					logService.insertLog(new SVDS_Log("执行了文件添加标签操作", DateUtils
							.getDate(), loginUser, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
				}
			}
		} else {// 选择
			System.out.println("选择的标签");
			SVDS_Alias aliasExist = aliasService.getAliasById(aliasId);
			SVDS_AliasFile aliasFileExist = aliasFileService
					.existAliasFileById(aliasId, fileId);
			if (aliasFileExist == null) {
				aliasFile.setFiles(filesService.getFilesById(fileId));
				aliasFile.setAlias(aliasExist);
				aliasFile.setAliasDate(DateUtils.getDate());
				Integer isSuccess = aliasFileService.insertAliasFile(aliasFile);
				if (isSuccess > 0) {
					logService.insertLog(new SVDS_Log("执行了文件添加标签操作", DateUtils
							.getDate(), loginUser, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
					return isSuccess;
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		}
		/*
		 * if (aliasExist == null) { String date = DateUtils.getDate();
		 * alias.setAliasName(aliasName); alias.setAliasDate(date);
		 * alias.setAliasType(aliasType); alias.setParentId(aliasType);
		 * alias.setLv(1); if(aliasType==1){
		 * alias.setIcon("../assets/images/alias.png"); }else{
		 * alias.setIcon("../assets/images/alias.png");
		 * alias.setUser(userService.getUserById(loginUser.getUserId())); }
		 * Integer aliasId = aliasService.insertAlias(alias);
		 * System.out.println(alias.getAliasId()); if (aliasId > 0) {
		 * aliasFile.setFiles(filesService.getFilesById(fileId));
		 * aliasFile.setAlias(alias);
		 * aliasFile.setAliasDate(DateUtils.getDate()); Integer isSuccess =
		 * aliasFileService.insertAliasFile(aliasFile); if (isSuccess > 0) {
		 * logService.insertLog(new SVDS_Log("执行了文件添加标签操作", DateUtils
		 * .getDate(), loginUser, file.getMenu(),file)); } } } else {
		 * System.out.println(aliasExist.getAliasId() + "---" + fileId);
		 * SVDS_AliasFile aliasFileExist = aliasFileService
		 * .existAliasFileById(aliasExist.getAliasId(), fileId); if
		 * (aliasFileExist == null) {
		 * aliasFile.setFiles(filesService.getFilesById(fileId));
		 * aliasFile.setAlias(aliasExist);
		 * aliasFile.setAliasDate(DateUtils.getDate()); Integer isSuccess =
		 * aliasFileService.insertAliasFile(aliasFile); if (isSuccess > 0) {
		 * logService.insertLog(new SVDS_Log("执行了文件添加标签操作", DateUtils
		 * .getDate(), loginUser, file.getMenu(),file)); return isSuccess; }
		 * else { return 0; } } else { return 0; } }
		 */

		return null;
	}

	/**
	 * 选项卡添加标签
	 * 
	 * @param aliasName
	 * @param fileId
	 * @param state
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertTabAlias", method = RequestMethod.POST)
	@ResponseBody
	public String insertTabAlias(String aliasName, Integer aliasType,
			Integer tabsId, Integer aliasId, HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_Alias alias = new SVDS_Alias();
		List<Integer> tabsIds = new ArrayList<Integer>();
		List<Tabs> tabs = dao.getTabsBytabsid(tabsId);
		if (tabs.size() == 0) {
			tabsIds.add(tabsId);
		} else {
			tabsIds = getTabsIds(tabs, tabsIds);
		}
		System.out.println(tabsIds);
		System.out.println(aliasName);
		System.out.println(aliasId);
		System.out.println(aliasType);
		if (tabsIds != null) {
			List<SVDS_Files> files = filesService.listFilesByTabsIds(tabsIds);
			SVDS_AliasFile aliasFile = new SVDS_AliasFile();
			if (aliasId == null) {// 新增
				System.out.println("***标签不存在***");
				String date = DateUtils.getDate();
				alias.setAliasName(aliasName);
				alias.setAliasDate(date);
				alias.setAliasType(aliasType);
				alias.setParentId(aliasType);
				alias.setLv(1);
				if (aliasType == 1) {
					alias.setIcon("../assets/images/alias.png");
				} else {
					alias.setIcon("../assets/images/alias.png");
					alias.setUser(userService.getUserById(loginUser.getUserId()));
				}
				Integer isSuccess = aliasService.insertAlias(alias);
				if (isSuccess > 0) {
					if (files.size() != 0) {
						for (SVDS_Files svds_Files : files) {
							aliasFile.setFiles(svds_Files);
							aliasFile.setAlias(alias);
							aliasFile.setAliasDate(DateUtils.getDate());
							aliasFileService.insertAliasFile(aliasFile);
							logService.insertLog(new SVDS_Log("执行了选项卡添加标签操作",
									DateUtils.getDate(), loginUser, svds_Files
											.getMenu(), svds_Files,IpAddressUtils.getIpAddress(request),"成功"));
						}
						return "ok";
					} else {
						return "null";
					}
				}
			} else {// 选择
				System.out.println("***标签存在***");
				if (files.size() != 0) {
					SVDS_Alias aliasExist = aliasService.getAliasById(aliasId);
					for (SVDS_Files svds_Files : files) {
						SVDS_AliasFile aliasFileExist = aliasFileService
								.existAliasFileById(aliasId,
										svds_Files.getFileId());
						if (aliasFileExist == null) {
							System.out.println("***文件未添加过标签***");
							aliasFile = new SVDS_AliasFile();
							aliasFile.setFiles(svds_Files);
							aliasFile.setAlias(aliasExist);
							aliasFile.setAliasDate(DateUtils.getDate());
							aliasFileService.insertAliasFile(aliasFile);
							logService.insertLog(new SVDS_Log("执行了选项卡添加标签操作",
									DateUtils.getDate(), loginUser, svds_Files
											.getMenu(), svds_Files,IpAddressUtils.getIpAddress(request),"成功"));
						}
					}
					return "ok";
				} else {
					return "null";
				}
			}
		}
		/*
		 * if (tabsIds != null) { List<SVDS_Files> files =
		 * filesService.listFilesByTabsIds(tabsIds); SVDS_AliasFile aliasFile =
		 * new SVDS_AliasFile(); SVDS_Alias aliasExist =
		 * aliasService.getAliasByUserName
		 * (aliasName,loginUser.getUserId(),aliasType);
		 * System.out.println("*****标签是否存在*******");
		 * System.out.println(aliasExist); if (aliasExist == null) { String date
		 * = DateUtils.getDate(); alias.setAliasName(aliasName);
		 * alias.setAliasDate(date); alias.setAliasType(aliasType);
		 * alias.setParentId(aliasType); alias.setLv(1); if(aliasType==1){
		 * alias.setIcon("../assets/images/alias.png"); }else{
		 * alias.setIcon("../assets/images/alias.png"); }
		 * alias.setUser(userService.getUserById(loginUser.getUserId()));
		 * Integer isSuccess = aliasService.insertAlias(alias);
		 * System.out.println(alias.getAliasId()); if (isSuccess > 0) { if
		 * (files.size() != 0) { for (SVDS_Files svds_Files : files) {
		 * aliasFile.setFiles(svds_Files); aliasFile.setAlias(alias);
		 * aliasFile.setAliasDate(DateUtils.getDate());
		 * aliasFileService.insertAliasFile(aliasFile); logService.insertLog(new
		 * SVDS_Log("执行了选项卡添加标签操作", DateUtils.getDate(), loginUser,
		 * svds_Files.getMenu(),svds_Files)); } return "ok"; } else { return
		 * "null"; } } } else { System.out.println("*****标签存在*******");
		 * System.out.println(files.size()); if (files.size() != 0) { for
		 * (SVDS_Files svds_Files : files) { SVDS_AliasFile aliasFileExist =
		 * aliasFileService .existAliasFileById(aliasExist.getAliasId(),
		 * svds_Files.getFileId()); System.out.println(aliasFileExist); if
		 * (aliasFileExist == null) { System.out.println("***文件未添加过标签***");
		 * System.out.println(svds_Files.getFileId());
		 * System.out.println(aliasExist.getAliasId()); aliasFile = new
		 * SVDS_AliasFile(); aliasFile.setFiles(svds_Files);
		 * aliasFile.setAlias(aliasExist);
		 * aliasFile.setAliasDate(DateUtils.getDate());
		 * aliasFileService.insertAliasFile(aliasFile); logService.insertLog(new
		 * SVDS_Log("执行了选项卡添加标签操作", DateUtils .getDate(), loginUser,
		 * svds_Files.getMenu(),svds_Files)); } } return "ok"; } else { return
		 * "null"; } } }
		 */

		return null;
	}

	/**
	 * 获取所有选项卡父级子级的Id
	 * 
	 * @param list
	 * @param tabsIds
	 * @return
	 */
	public List<Integer> getTabsIds(List<Tabs> list, List<Integer> tabsIds) {
		for (int i = 0; i < list.size(); i++) {
			// System.out.println(list.get(i));
			tabsIds.add(list.get(i).getId());
			if (list.get(i).getChildren().size() > 0) {
				getTabsIds(list.get(i).getChildren(), tabsIds);
			}
		}
		return tabsIds;
	}

	/**
	 * 根据ID修改标签
	 * 
	 * @param Alias
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateAlias", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateAlias(Integer aliasId, String aliasName,
			HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_Alias alias = aliasService.getAliasById(aliasId);
		SVDS_Alias aliasExit = aliasService.getAliasByUserName(aliasName,
				loginUser.getUserId(), alias.getParentId());
		if (aliasExit == null) {
			alias.setAliasName(aliasName);
			Integer isSuccess = aliasService.updateAlias(alias);
			if (isSuccess > 0) {
				logService.insertLog(new SVDS_Log("修改标签操作",
						DateUtils.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
				return isSuccess;
			} else {
				return null;
			}
		} else {
			return 0;
		}

	}

	/**
	 * 根据ID修改标签的父id
	 * 
	 * @param Alias
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateAliasByParent", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateAliasByParent(Integer aliasId, Integer parentId,
			HttpServletRequest request) throws UnknownHostException {
		SVDS_Alias alias = aliasService.getAliasById(aliasId);
		alias.setParentId(parentId);
		Integer isSuccess = aliasService.updateAlias(alias);
		if (isSuccess > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("拖拽标签操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return isSuccess;
		}
		return null;
	}

	/**
	 * 根据ID删除标签
	 * 
	 * @param AliasIds
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/deleteAlias", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteAlias(
			@RequestParam(value = "aliasIds[]", required = false, defaultValue = "") Integer[] aliasIds,
			HttpServletRequest request) throws UnknownHostException {
		List<Integer> ids = Arrays.asList(aliasIds);
		System.out.println(aliasService.deleteAlias(ids));
		if (aliasService.deleteAlias(ids) != null) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("删除标签操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return aliasService.deleteAlias(ids);
		}
		return null;
	}

	/**
	 * 系统标签的总数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sysAliasCount", method = RequestMethod.POST)
	@ResponseBody
	public Integer sysAliasCount() {
		return aliasService.sysAliasCount();
	}

	/**
	 * 个人标签的总数
	 * 
	 * @return
	 */
	@RequestMapping(value = "perAliasCount", method = RequestMethod.POST)
	@ResponseBody
	public Integer perAliasCount() {
		return aliasService.perAliasCount();
	}

	/**
	 * 创建个人标签最多的用户的个人标签数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/perAliasBigCount", method = RequestMethod.POST)
	@ResponseBody
	public Integer perAliasBigCount() {
		return aliasService.perAliasBigCount();
	}

}
