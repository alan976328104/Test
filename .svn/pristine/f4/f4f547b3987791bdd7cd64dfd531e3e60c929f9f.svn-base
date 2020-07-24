package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.dao.FolderUpDao;
import ac.drsi.nestor.dao.SVDS_FilesDao;
import ac.drsi.nestor.entity.RelationData;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.Tabs;
import ac.drsi.nestor.service.FolderUpService2;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;
/**
 * 2019年3月20日 曹泽凯
 * 用于关联文件上传
 * @author CZK
 *
 */
@RestController
public class FolderUpController {
	@Autowired
	FolderUpService2 folderUpService;
	@Autowired
	FolderUpDao dao;
	@Autowired
	SVDS_FilesDao dao2;
	@Autowired
	SVDS_UserService userService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_MenuService menuService;
/**
 * 2019年3月20日 曹泽凯
 * 关联文件上传
 * @param folder 文件夹
 * @param menuid 菜单id
 * @param miji 密级
 * @param alisname 标签名称
 * @param alistype 标签类型
 * @param request http请求协议
 * @return
 * @throws UnknownHostException 
 * @throws NumberFormatException 
 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFolder(MultipartFile[] folder, Integer menuid,
			String miji, String alisname, Integer alistype,
			HttpServletRequest request) throws NumberFormatException, UnknownHostException {
		// FilesUtils file = new FilesUtils();
		SVDS_User loginUser = sessionService.getSessionByIp(request);//获取登陆用户
		folderUpService.saveMultiFile("f:\\files", folder, menuid,
				Integer.parseInt(miji), alisname, alistype, loginUser,request);//调用添加方法
		return "ok";
	}
	/**
	 * 2019年3月20日 曹泽凯
	 * 获取所有选项卡
	 * @param id 菜单id
	 * @return
	 */
	@RequestMapping(value = "/getTabsData")
	public List<Tabs> getTabsData(Integer id) {
		List<Tabs> list = dao.getTabsByMenuid(id);
		for (int i = 0; i < list.size(); i++) {//通过5层循环获选项卡列表
			for (int j = 0; j < list.get(i).getChildren().size(); j++) {
				for (int j2 = 0; j2 < list.get(i).getChildren().get(j)
						.getChildren().size(); j2++) {
						for (int k = 0; k <list.get(i).getChildren().get(j)
								.getChildren().get(j2).getChildren().size(); k++) {
							for (int k2 = 0; k2 < list.get(i).getChildren().get(j)
								.getChildren().get(j2).getChildren().get(k).getChildren().size(); k2++) {
								if (list.get(i).getChildren().get(j).getChildren().get(j2).getChildren().get(k).getChildren().get(k2)
										.getName().equals("其他数据")) {
									Tabs t = list.get(i).getChildren().get(j).getChildren()
											.get(j2).getChildren().get(k).getChildren().get(k2);
									list.get(i).getChildren().get(j).getChildren().get(j2).getChildren().get(k).getChildren()
											.remove(k2);
									list.get(i).getChildren().get(j).getChildren().get(j2).getChildren().get(k).getChildren().add(t);
								}
								
								
							}
							
							
							
							
							if (list.get(i).getChildren().get(j).getChildren().get(j2).getChildren().get(k)
									.getName().equals("其他数据")) {
								Tabs t = list.get(i).getChildren().get(j).getChildren()
										.get(j2).getChildren().get(k);
								list.get(i).getChildren().get(j).getChildren().get(j2).getChildren()
										.remove(k);
								list.get(i).getChildren().get(j).getChildren().get(j2).getChildren().add(t);
							}
							
							
						}
					
					
					
					
					
					if (list.get(i).getChildren().get(j).getChildren().get(j2)
							.getName().equals("其他数据")) {
						Tabs t = list.get(i).getChildren().get(j).getChildren()
								.get(j2);
						list.get(i).getChildren().get(j).getChildren()
								.remove(j2);
						list.get(i).getChildren().get(j).getChildren().add(t);
					}
				}
				if (list.get(i).getChildren().get(j).getName().equals("其他数据")) {
					Tabs t = list.get(i).getChildren().get(j);
					list.get(i).getChildren().remove(j);
					list.get(i).getChildren().add(t);
				}
			}
			if (list.get(i).getName().equals("其他数据")) {
				Tabs t = list.get(i);
				list.remove(i);
				list.add(t);
			}

		}
		return list;
	}
/**
 * 2019年3月20日 曹泽凯
 * 设置选项卡id再缓存中
 * @param tabsId  选项卡id
 * @param menuId 菜单id
 * @param request http请求
 */
	@RequestMapping(value = "/getDataBySession", method = RequestMethod.POST)
	public void getDataBySession(Integer tabsId, Integer menuId,
			HttpServletRequest request) {
		request.getSession().removeAttribute("tabsId");
		request.getSession().setAttribute("tabsId", tabsId);
		request.getSession().setAttribute("menuId", menuId);
	}
/**
 * 2019年3月20日 曹泽凯
 * @param request http请求协议
 * @param fileName 文件名称
 * @return
 */
	@RequestMapping(value = "/getFilesByTabsid", method = RequestMethod.POST)
	public String getFilesByTabsid(HttpServletRequest request, String fileName) {
		Integer tabsid = (Integer) request.getSession().getAttribute("tabsId");
		Integer menuId = (Integer) request.getSession().getAttribute("menuId");
		System.out.println("选项卡"+tabsid);
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		// String lang = request.getSession().getAttribute("lang").toString();
		List<SVDS_Files> files = new ArrayList<SVDS_Files>();
		if (fileName == null) {
			fileName = "";
		}
		if (loginUser.getSecretlevel().equals("B")) {
			System.out.println(fileName);
			System.out.println("tabsid"+tabsid);
			files = dao2.getFilesByTabsid(tabsid, 6, fileName,
					loginUser.getUserId());
		} else if (loginUser.getSecretlevel().equals("C")) {
			files = dao2.getFilesByTabsid(tabsid, 5, fileName,
					loginUser.getUserId());
		} else if (loginUser.getSecretlevel().equals("D")) {
			files = dao2.getFilesByTabsid(tabsid, 1, fileName,
					loginUser.getUserId());
		} else if(loginUser.getSecretlevel().equals("Z")){
			files = dao2.getFilesByTabsid(tabsid, 2, fileName,
					loginUser.getUserId());
		}else if(loginUser.getSecretlevel().equals("F")){
			files = dao2.getFilesByTabsid(tabsid, 1, fileName,
					loginUser.getUserId());
		}
		JSONObject result = new JSONObject();
		result.put("menuId", menuId);
		result.put("tabsId", tabsid);
		// result.put("lang", lang);
		result.put("files", files);
		return result.toString();
	}
	/**
	 *  2019年3月20日 曹泽凯
	 *  用于获取选项卡下的数据并排序
	 * @param request http请求
	 * @param fileName 文件名称
	 * @param sortName 排序名称
	 * @return
	 */
	@RequestMapping(value = "/listFilesByTabsidSort", method = RequestMethod.POST)
	public String listFilesByTabsidSort(HttpServletRequest request, String fileName,String sortName) {
		Integer tabsid = (Integer) request.getSession().getAttribute("tabsId");
		Integer menuId = (Integer) request.getSession().getAttribute("menuId");
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		// String lang = request.getSession().getAttribute("lang").toString();
		System.out.println(sortName);
		List<SVDS_Files> files = new ArrayList<SVDS_Files>();
		if (fileName == null) {
			fileName = "";
		}
		if (loginUser.getSecretlevel().equals("B")) {
			files = dao2.listFilesByTabsidSort(tabsid, 6, fileName,
					loginUser.getUserId(),sortName);
		} else if (loginUser.getSecretlevel().equals("C")) {
			files = dao2.listFilesByTabsidSort(tabsid, 5, fileName,
					loginUser.getUserId(),sortName);
		} else if (loginUser.getSecretlevel().equals("D")) {
			files = dao2.listFilesByTabsidSort(tabsid, 1, fileName,
					loginUser.getUserId(),sortName);
		}
		JSONObject result = new JSONObject();
		result.put("menuId", menuId);
		result.put("tabsId", tabsid);
		// result.put("lang", lang);
		result.put("files", files);
		return result.toString();
	}
	/**
	 * 2019年3月20日 曹泽凯
	 * 添加选项卡
	 * @param name 选项卡名称
	 * @param parentId 父节点id
	 * @param lv 节点属于几级节点
	 * @param menuId 菜单id
	 * @param request http请求协议
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/insertTabs", method = RequestMethod.POST)
	public String insertTabs(String name, Integer parentId, Integer lv,
			Integer menuId, HttpServletRequest request) throws Exception {
		Tabs isExist = dao.getTabsByLv(name, lv, menuId);
		System.out.println(isExist);
		if (isExist == null) {
			Tabs tabs = new Tabs();
			tabs.setName(name);
			tabs.setMenuid(menuId);
			tabs.setParentid(parentId);
			tabs.setLv(lv);
			Integer isSuccess = dao.insertTabs(tabs);
			SVDS_Menu menu = menuService.getMenuById(menuId);
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			if(getTabsName(parentId, "")!=null){
				logService.insertLog(new SVDS_Log("执行了添加" + menu.getName()
						+ getTabsName(parentId, "") + "→" + name + "选项卡操作",
						DateUtils.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			}else{
				logService.insertLog(new SVDS_Log("执行了添加" + menu.getName() + "→" + name + "选项卡操作",
						DateUtils.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			}
			if (isSuccess > 0) {
				return "ok";
			} else {
				return "error";
			}
		} else {
			return "exist";
		}
	}
/**
 * 2019年3月20日 曹泽凯
 * 查看是否存在父节点
 * @param id 选项卡id
 * @param tabName 选项卡名称
 * @return
 */
	public String getTabsName(Integer id, String tabName) {
		Tabs tabs = dao.getTabsBytabsId(id);
		tabName += "→";
		if (tabs != null) {
			if (tabs.getParentid() != 0) {
				tabName += tabs.getName();
				return getTabsName(tabs.getParentid(), tabName);
			} else {
				tabName += tabs.getName();
				return tabName;
			}
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/ttttt", method = RequestMethod.GET)
	public String ttttt() {
		return dao.getTbsByName("[非密]基准卡", 22, 1) + "";
	}
/**
 * 2019年3月20日 曹泽凯
 * 是否存在选项卡
 * @param menuid 菜单id
 * @return
 */
	@RequestMapping(value = "/getishavetabs", method = RequestMethod.GET)
	public List<String> getishavetabs(Integer menuid) {
		return dao.getMenuTabs(menuid);
	}
/**
 * 2019年3月20日 曹泽凯
 * 获取选项卡下所有文件的id 路径 名称
 * @param id 选项卡id
 * @return
 */
	@RequestMapping(value = "/getfileBytabsIdforRelation", method = RequestMethod.GET)
	public List<RelationData> getfileBytabsIdforRelation(Integer id) {
		return dao.getfileBytabsIdforRelation(id);
	}
	
}
