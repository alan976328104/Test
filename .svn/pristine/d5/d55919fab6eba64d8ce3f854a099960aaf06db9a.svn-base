package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.common.SolrUtils;
import ac.drsi.nestor.dao.FolderUpDao;
import ac.drsi.nestor.dao.SVDS_MenuDao;
import ac.drsi.nestor.dao.SVDS_RecycleDao;
import ac.drsi.nestor.dao.TabsDao;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.Tabs;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_SessionService;
/**
 * 用于选项卡操作
 * @author CZK
 *
 */
@RestController
public class TabsController {
	@Autowired
	TabsDao dao;
	@Autowired
	SVDS_MenuDao menuDao;
	@Autowired
	FolderUpDao folderDao;
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_RecycleDao recycleDao;
	@Autowired
	SVDS_MenuService menuService;
	/**
	 * 修改选项卡名称
	 * @param tabsname
	 * @param tabsId
	 * @param menuId
	 * @param lv
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateTabsforindex", method = RequestMethod.POST)
	public Integer updateTabsforindex(String tabsname,Integer tabsId,Integer menuId,Integer lv,HttpServletRequest request) throws UnknownHostException{
		Tabs isExist = folderDao.getTabsByLv(tabsname, lv, menuId);
		if (isExist == null) {
			Tabs tab=dao.getTabsByIdAll(tabsId);
			SVDS_Menu menu=menuService.getMenuById(menuId);
			Integer sussecc=dao.updateTabs(tabsname, tabsId);
			if(sussecc>0){
				dao.updateFilesLocationByTabsId(tabsId,tab.getName(),tabsname);
			}
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			if(getTabsName(tabsId, "")!=null){
				logService.insertLog(new SVDS_Log("执行了修改"+menu.getName()+getTabsName(tabsId, "")+"→"+tabsname+"选项卡名称操作", DateUtils.getDate(),
						loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			}else{
				logService.insertLog(new SVDS_Log("执行了修改"+menu.getName()+"→"+tabsname+"选项卡名称操作", DateUtils.getDate(),
						loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			}
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 置最前
	 * @param tabsId
	 * @param menuId
	 * @param parentId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getTabsOrderById", method = RequestMethod.POST)
	public Integer getTabsOrderById(Integer tabsId,Integer menuId,Integer parentId,HttpServletRequest request){
		System.out.println(tabsId);
		System.out.println(menuId);
		System.out.println(parentId);
		Tabs tabs=dao.getTabsOrderById(tabsId, menuId, parentId);
		System.out.println(tabs);
		if(tabs!=null){
			Integer orderVal=dao.getTabsOrderMax(menuId,parentId,tabs.getLv());
			orderVal=orderVal+1;
			System.out.println(orderVal);
			return dao.updateTabsOrder(tabsId,orderVal);
		}else{
			return 0;
		}
	}
	/**
	 * 删除选项卡
	 * @param tabsId
	 * @param menuId
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/delTabsforindex", method = RequestMethod.POST)
	public Integer delTabsforindex(Integer tabsId,Integer menuId,HttpServletRequest request) throws UnknownHostException{
		SVDS_Menu menu=menuService.getMenuById(menuId);
		Tabs tabs=dao.getTabsById(tabsId);
//		System.out.println(getTabsName(tabsId, ""));
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		List<SVDS_Files> files=new ArrayList<SVDS_Files>();
		List<Integer> ids=new ArrayList<Integer>();
		List<Tabs> tabsList=dao.listTabsResByParentId(tabsId);
		List<Tabs> tabsChildrenList=new ArrayList<Tabs>();
		//System.out.println("删除的父级选项卡的子级项"+tabsList.toString());
		if(tabsList!=null){
			tabsList=getChildrenTabs(tabsList,tabsChildrenList);
			System.out.println("子级选项卡Id数量："+tabsList.size());
			for (Tabs tab : tabsList) {
				System.out.println("选项卡Id："+tab.getId());
				files=filesService.listFilesByDelTabsId(tab.getId());
				ids=new ArrayList<Integer>();
				for (SVDS_Files svds_Files : files) {
					ids.add(svds_Files.getFileId());
				}
				if(ids.size()>0){
					filesService.recycleFiles(ids, 1,loginUser,tab.getId());
					dao.updateTabsState(tab.getId(),1);
				}
			}
		}
		
		
		if(getTabsName(tabsId, "")!=null){
			logService.insertLog(new SVDS_Log("执行了删除"+menu.getName()+getTabsName(tabsId, "")+"→"+tabs.getName()+"选项卡操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
		}else{
			logService.insertLog(new SVDS_Log("执行了删除"+menu.getName()+"→"+tabs.getName()+"选项卡操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"失败"));
		}
		files=filesService.listFilesByDelTabsId(tabsId);
		ids=new ArrayList<Integer>();
		for (SVDS_Files svds_Files : files) {
			ids.add(svds_Files.getFileId());
		}
		if(ids.size()>0){
			filesService.recycleFiles(ids, 1,loginUser,tabsId);
		}
		return dao.updateTabsState(tabsId,1);
	}
	

	/**
	 * 根据等级和父级Id查询同一层的选项卡
	 * @param menuId
	 * @param lv
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value = "/listTabByLv")
	public List<Tabs> listTabByLv(Integer menuId,Integer lv,Integer parentId){
		System.out.println(System.currentTimeMillis());
		List<Tabs> tabs = dao.listTabByLv(menuId,lv,parentId);
		if(tabs!=null&&tabs.size()>0){
			for (int i = 0; i < tabs.size(); i++) {
				if (tabs.get(i).getName().equals("其他数据")) {
					Tabs t = tabs.get(i);
					tabs.remove(i);
					tabs.add(t);
				}
			}
		}
		return tabs;
	}
	
	/**
	 * 回收站删除选项卡
	 * @param tabsId
	 * @param menuId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delTabs", method = RequestMethod.POST)
	public Integer delTabs(Integer id,HttpServletRequest request){
//		Tabs tabs=dao.getDelTabsById(id);
		List<Tabs> tabsList=dao.listTabsResByParentId(id);
		List<Tabs> tabsChildrenList=new ArrayList<Tabs>();
		//SVDS_User loginUser = sessionService.getSessionByIp(request);
		//List<SVDS_Files> files=filesService.listFilesByDelTabsId(id);
		//List<Integer> ids=new ArrayList<Integer>();
		//for (SVDS_Files svds_Files : files) {
		//	ids.add(svds_Files.getFileId());
		//}
		//if(ids.size()>0){
		//	filesService.deleteFiles(ids);
		//	logService.deleteByFileId(ids);
		//	recycleDao.deleteRecycleByFileIds(ids);
		//}
		if(tabsList!=null){
			tabsList=getChildrenTabs(tabsList,tabsChildrenList);
			for (Tabs tabs : tabsList) {
				recycleDao.deleteRecycleByTabsId(tabs.getId());//删除回收站信息
				logService.deleteLogByTabsId(tabs.getId());//删除日志信息
				filesService.delFileByTabId(tabs.getId());//删除文件
				 dao.delTabs(tabs.getId());//删除选项卡
			}
		}
		
		return dao.delTabs(id);//删除选项卡
	}
	/**
	 * 回收站还原选项卡
	 * @param tabsId
	 * @param id
	 * @param request
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateTabsState", method = RequestMethod.POST)
	public Integer updateTabsState(Integer id,HttpServletRequest request) throws UnknownHostException{
		Tabs tabs=dao.getDelTabsById(id);
//		System.out.println(tabs.getMenuid());
		SVDS_Menu menu=menuService.getMenuById(tabs.getMenuid());
//		System.out.println(getTabsName(id, ""));
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		
		List<SVDS_Files> files=new ArrayList<SVDS_Files>();
		List<Integer> ids=new ArrayList<Integer>();
		List<Tabs> tabsList=dao.listTabsResByParentId(id);
		List<Tabs> tabsChildrenList=new ArrayList<Tabs>();
		if(tabsList!=null){
			tabsList=getChildrenTabs(tabsList,tabsChildrenList);
			System.out.println("子级选项卡Id数量："+tabsList.size());
			for (Tabs tab : tabsList) {
				files=filesService.listFilesByDelTabsId(tab.getId());
				ids=new ArrayList<Integer>();
				for (SVDS_Files svds_Files : files) {
					ids.add(svds_Files.getFileId());
				}
				if(ids.size()>0){
					filesService.updateFiles(ids, 0);
					recycleDao.deleteRecycleByFileIds(ids);
				}
				 dao.updateTabsState(tab.getId(),0);
			}
		}
		
		if(getTabsName(id, "")!=null){
			logService.insertLog(new SVDS_Log("执行了还原"+menu.getName()+getTabsName(id, "")+"→"+tabs.getName()+"选项卡操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
		}else{
			logService.insertLog(new SVDS_Log("执行了还原"+menu.getName()+"→"+tabs.getName()+"选项卡操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"失败"));
		}
		files=filesService.listFilesByDelTabsId(id);
		ids=new ArrayList<Integer>();
		for (SVDS_Files svds_Files : files) {
			ids.add(svds_Files.getFileId());
		}
		if(ids.size()>0){
			filesService.updateFiles(ids, 0);
			recycleDao.deleteRecycleByFileIds(ids);
		}
		return dao.updateTabsState(id,0);
	}
	
	public List<Tabs> getChildrenTabs(List<Tabs> tabsList,List<Tabs> tabsChildrenList){
		for (Tabs tabs : tabsList) {
			tabsChildrenList.add(tabs);
			if(tabs.getChildren()!=null&&tabs.getChildren().size()>0){
				getChildrenTabs(dao.listTabsResByParentId(tabs.getId()),tabsChildrenList);
			}
		}
		return tabsChildrenList;
	}
	/**
	 * 获得文件路径名称
	 * @param id 选项卡id
	 * @param tabName 选项卡名称
	 * @return
	 */
	public String getTabsName(Integer id, String tabName) {
		Tabs tabs = dao.getDelTabsById(id);
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
	/**
	 * 删除选项卡
	 * @param tabsid 选项卡id
	 * @return
	 */
	private Integer deltab(Integer tabsid){
		Integer childrenid  =dao.ishavachildren(tabsid);
		if(childrenid>0){
			List<Integer> ids = dao.getchildernids(tabsid);
			for (int i = 0; i < ids.size(); i++) {
				deltab(ids.get(i));
			}
		}
		List<String> ids = dao.getFileidBytabsid(tabsid);
		System.out.println("---"+ids.size());
		removeSolrById(ids);
		dao.deltabsforfile(tabsid); //删除选项卡下数据
		dao.delTabs(tabsid); //删除选选项卡
		dao.delsvds_collectfile(tabsid);//删除收藏夹
		dao.delAliasBytabsid(tabsid); //删除标签
		dao.delsvds_log(tabsid); //删除日志
		dao.delsvds_visited(tabsid);//删除首页推送
		dao.delMessageBytabsid(tabsid); //删除消息
		return 1;
	}
	
	/**
	 * 删除选下卡下索引索引
	 * @param ids
	 */
	public void removeSolrById(List<String> ids){
		System.out.println(ids.size());
		for (int i = 0; i < ids.size(); i++) {
			System.out.println("删除"+ids.get(i));
			SolrUtils.removeById(ids.get(i));
		}
	}
	/**
	 * 查询选显卡
	 * @param tabsId 选项卡id
	 * @param menuId 菜单id
	 * @return
	 */
	@RequestMapping(value = "/listTabsById", method = RequestMethod.POST)
	public List<Tabs> listTabsById(Integer tabsId,Integer menuId){
		List<Tabs> list = dao.listByTabsId(tabsId,menuId);
		 for (int i = 0; i <list.size(); i++) {
			 for (int j = 0; j <list.get(i).getChildren().size(); j++) {
				for (int j2 = 0; j2 <list.get(i).getChildren().get(j).getChildren().size(); j2++) {
					if( list.get(i).getChildren().get(j).getChildren().get(j2).getName().equals("其他数据")){
						Tabs t = list.get(i).getChildren().get(j).getChildren().get(j2);
						list.get(i).getChildren().get(j).getChildren().remove(j2);
						list.get(i).getChildren().get(j).getChildren().add(t);
					}
				}
				if( list.get(i).getChildren().get(j).getName().equals("其他数据")){
					Tabs t = list.get(i).getChildren().get(j);
					list.get(i).getChildren().remove(j);
					list.get(i).getChildren().add(t);
				}
			}
			 if( list.get(i).getName().equals("其他数据")){
					Tabs t = list.get(i);
					list.remove(i);
					list.add(t);
				}
			
		}
		return list;
	}
	/**
	 * 获得删除后的选项卡
	 * @param menuId菜单id
	 * @return
	 */
	@RequestMapping(value = "/getDelTabs", method = RequestMethod.POST)
	public List<Tabs> getDelTabs(Integer menuId) {
		System.out.println("菜单id:"+menuId);
		List<Tabs> list = dao.listTabsByState(menuId);
		List<Tabs> fileTabs=new ArrayList<Tabs>();
		List<Tabs> delTabs=new ArrayList<Tabs>();
		List<Tabs> tabsAll=new ArrayList<Tabs>();
		List<Integer> tabsIds = menuDao.listFilesByMenuState(menuId);
		System.out.println("选项卡");
		//System.out.println(tabsIds);
		//System.out.println(list);
		if(tabsIds!=null&&tabsIds.size()>0){
			fileTabs=dao.getTabsByIds(tabsIds);
			fileTabs=re(fileTabs);
		}
		if(list!=null&&list.size()>0){
			delTabs=re(list);
		//	System.out.println(delTabs.toString());
			delTabs = getTabsOne(delTabs);
		}
		//System.out.println(fileTabs);
		//System.out.println(delTabs);
		tabsAll.addAll(fileTabs);
		tabsAll.addAll(delTabs);
		tabsAll = getTabsOne(tabsAll);
	//	System.out.println(tabsAll.toString());
		//System.out.println(getTabsOrg(tabsAll));
		if(tabsAll!=null&&tabsAll.size()>0){
			return getTabsOrg(tabsAll);
		}else{
			return null;
		}
	}

	public List<Tabs> getTabsOne(List<Tabs> tabs){
		for (int i = 0; i < tabs.size(); i++) {
			for (int j = tabs.size() - 1; j > i; j--) {
				if (tabs.get(i).getId().equals(tabs.get(j).getId())) {
					tabs.remove(j);
				}
			}
		}
		return tabs;
}
	/**
	 * 获得选项卡并排序
	 * @param tabs 选项卡
	 * @return
	 */
	public List<Tabs> getTabsOrg(List<Tabs> tabs) {
		List<Tabs> tabsParentList = new ArrayList<Tabs>();
		List<Tabs> tabsNotParentList = new ArrayList<Tabs>();
		for (Tabs tab : tabs) {
			if (tab.getParentid() == 0) {
				tabsParentList.add(tab);
			} else {
				tabsNotParentList.add(tab);
			}
		}
		//System.out.println(tabsNotParentList);
		//System.out.println(tabsParentList);
		if (tabsParentList.size() > 0) {
			for (Tabs tab : tabsParentList) {
				tab.setChildren(getChild(tab.getId(), tabsNotParentList));
			}
		}
		return tabsParentList;
	}
	/**
	 * 获得选项卡所有子选项卡
	 * @param parentId 父选项卡id
	 * @param childList 子选项卡集合
	 * @return
	 */
	private static List<Tabs> getChild(Integer parentId,
			List<Tabs> childList) {
		List<Tabs> listParentOrgs = new ArrayList<Tabs>();
		List<Tabs> listNotParentOrgs = new ArrayList<Tabs>();
		// 遍历childList，找出所有的根节点和非根节点
		if (childList != null && childList.size() > 0) {
			for (Tabs record : childList) {
				// 对比找出父节点
				//System.out.println(record.getParentid().equals(parentId));
				if (record.getParentid().equals(parentId)) {
					listParentOrgs.add(record);
				} else {
					listNotParentOrgs.add(record);
				}
			}
		}
	//	System.out.println(listParentOrgs);
	//	System.out.println(listNotParentOrgs);
		// 查询子节点
		if (listParentOrgs.size() > 0) {
			for (Tabs tab : listParentOrgs) {
				// 递归查询子节点
				tab.setChildren(getChild(tab.getId(), listNotParentOrgs));
			}
		}
		return listParentOrgs;
	}
	List<Tabs> newlist = new ArrayList<>();
/**
 * 刷新选项卡
 * @param list 选项卡集合
 * @return
 */
	public List<Tabs> re(List<Tabs> list) {
		newlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			//System.out.println(list.get(i));
			//if (list.get(i).getParentid() != 0) {
				dguie(list.get(i));
			//}
		}
		return newlist;
	}

	public void dguie(Tabs tabs) {
		newlist.add(tabs);
			if (tabs.getParentid() != 0) {
				tabs = dao.getTabById(tabs.getParentid());
				if(tabs!=null){
					dguie(tabs);
				}
		}
		
	}
}
