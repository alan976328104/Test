package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.common.SolrUtils;
import ac.drsi.nestor.dao.MenuDao;
import ac.drsi.nestor.dao.PhenomenonDao;
import ac.drsi.nestor.entity.Menu;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_SessionService;
/**
 * 用于操作系统中菜单
 * @author CZK
 *
 */
@RestController
public class MenuController {
	@Autowired
	SVDS_LogService logService;
	@Autowired
		MenuDao dao;
	@Autowired
	PhenomenonDao phdDao;
	@Autowired
	SVDS_MenuService menuService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_FilesService filesService;
	
	@Autowired
	SVDS_MenuController svds_MenuController;
	/**
	 * 查询所有菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findallmenuforManager", method = RequestMethod.POST)
	public String findallmenuforManager(HttpServletRequest request){
		SVDS_User user = sessionService.getSessionByIp(request);//获取登陆用户
		result = new ArrayList<>();  
		List<SVDS_Menu> menus = menuService.getMenuAll(); //通多service获得所有菜单
		List<SVDS_Menu> iconnewmenus = addiconMenu(menus.get(0).getChildren());
		List<SVDS_Menu> newmenus= distinctrole(menus.get(0).getChildren(),dao.getidByrole(user.getUserId()),result);//为菜单去重
		JSONArray data = JSONArray.fromObject(newmenus); //将实体类转换为JSON
		if (menus != null) {
			System.out.println(newmenus);
			return data.toString();
		}
		return null;
		
	}
	
	/**
	 * 查询所有菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findallmenuforManager2", method = RequestMethod.POST)
	public String findallmenuforManager2(HttpServletRequest request){
		SVDS_User user = sessionService.getSessionByIp(request);//获取登陆用户
		result = new ArrayList<>();  
		List<SVDS_Menu> menus = menuService.getMenuAll(); //通多service获得所有菜单
		List<SVDS_Menu> iconnewmenus = addiconMenu(menus.get(0).getChildren());
		List<SVDS_Menu> newmenus= distinctrole(menus.get(0).getChildren(),dao.getidByrole(user.getUserId()),result);//为菜单去重
		if(user.getRole().getUserId()!=null&&user.getRole().getUserId()==4){
			JSONArray data = JSONArray.fromObject(newmenus); //将实体类转换为JSON
			if (menus != null) {
				System.out.println(data);
				return data.toString();
			}
			
			
		}else{
			
		
		List<SVDS_Menu> listmenu=getquanxian(user.getRole().getRoleId(),newmenus);
		
		JSONArray data = JSONArray.fromObject(listmenu); //将实体类转换为JSON
		if (menus != null) {
			System.out.println(listmenu);
			return data.toString();
		}
		}
		return null;
		
	}
	
	public List<SVDS_Menu> getquanxian(Integer roleid,List<SVDS_Menu> menus){
		List<SVDS_Menu> list =new ArrayList<>();
		for (int i = 0; i < menus.size(); i++) {
			if(isquanxian(menus.get(i),roleid)){
				list.add(menus.get(i));
			}
		}
		return list;
	}
	
	
	public boolean isquanxian(SVDS_Menu menu,Integer roleid){
		if(menu.getChildren()==null||menu.getChildren().size()==0){
			return false;
		}else{
			
			for (int i = 0; i < menu.getChildren().size(); i++) {
				System.out.println("menuid:"+menu.getChildren().get(i).getId()+"roleid:"+roleid);
				if(dao.getquanxian(roleid, menu.getChildren().get(i).getId())>0){
					System.out.println("menuid:"+menu.getChildren().get(i).getId()+"roleid:"+roleid);
					return true;
				}else{
					
					if(isquanxian(menu.getChildren().get(i),roleid)){
					return true;	
					}
						
				}
				
				
			}
		}
		
		
		return false;
		
		
	}
	
	/**
	 * 获得权限内所有菜单
	 * @param name用户名
	 * @param password 密码
	 * @return
	 */
	@RequestMapping(value = "/getAllMenuData")
	public String getAllMenuData(String name,String password){
		if(name==""||name==null||password==""||password==null){
				return "请输入账号或者密码参数";
		}
		return svds_MenuController.getUserMenu(dao.getUserid(name, password));
	}
	
	/**
	 * 添加菜单图标
	 * @param list
	 * @return
	 */
	public List<SVDS_Menu> addiconMenu(List<SVDS_Menu> list){
		
		if(list!=null){
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getUrl()==null){
					list.get(i).setIconCls("icon-open");//如果是目录节点添加该图标
				}else{
					if(list.get(i).getUrl().equals("html/tab3.html")){
						list.get(i).setIconCls("icon-shujuku");//如果是数据节点添加该图标
						
					}else if(list.get(i).getUrl().equals("html/yanzhengjuzhen.html")){
						list.get(i).setIconCls("icon-renwu");//如果是验证矩阵节点添加该图标
					}
					
				}
				
				addiconMenu(list.get(i).getChildren());
			}
			
		}
		
		return list;
	}
	public List<SVDS_Menu> result ;
	/**
	 * 用于全部菜单剔除没有权限的菜单
	 * @param list所有菜单
	 * @param listi 所有权限
	 * @param result 返回值
	 * @return
	 */
	public List<SVDS_Menu> distinctrole(List<SVDS_Menu> list,List<Integer> listi,List<SVDS_Menu> result){
		if(list!=null){
		boolean b= true;
		for (int i = 0; i < list.size(); i++) {
			b = false;
			for (int j = 0; j < listi.size(); j++) {
				if(list.get(i).getId()==listi.get(j)){
					b = true;
					break;
				}
			}
			if(b){
				List<SVDS_Menu> children = new ArrayList<>();
				result.add(new SVDS_Menu(list.get(i).getId(), list.get(i).getText(), list.get(i).getName(), list.get(i).getIcon(), list.get(i).getUrl(), list.get(i).getParentId(), list.get(i).getIconCls(), list.get(i).getLv(), list.get(i).getState(), list.get(i).getEname(), list.get(i).getMenus(), children,list.get(i).getUserid()));
			}
			List<SVDS_Menu> children1 = new ArrayList<>();
			//递归本方法
			distinctrole(list.get(i).getChildren(),listi,result.size()==0?children1:result.get(result.size()-1).getChildren());
		}
	
		}
		return result;
		
	}
	/**
	 * 添加菜单
	 * @param menu 菜单
	 * @param value
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertMenuforManager", method = RequestMethod.GET)
	public Integer insertMenuforManager(Menu menu,HttpSession value,HttpServletRequest request){
		SVDS_User svds_User =sessionService.getSessionByIp(request);
		
		try {
			//System.out.println("role:"+svds_User.getRole().getRoleId());
			//System.out.println("menu:"+menu.getId());
			dao.insertMenuforManager(menu);
			dao.insertMenuOrRole(svds_User.getRole().getRoleId(),menu.getId());//添加菜单的同时添加访问权限
			List<Integer> list = dao.getallButtonids();
			for (int i = 0; i <list.size(); i++) {
				dao.insertbuttonOrRole(svds_User.getRole().getRoleId(), list.get(i), menu.getId());//添加菜单的同时添加操作权限
			}
		
			if(svds_User.getRole().getRoleId()!=1){ //如果添加菜单的用户不是超级管理员则同时为超级管理员添加权限
				dao.insertMenuOrRole(1, menu.getId());
				for (int i = 0; i <list.size(); i++) {
					dao.insertbuttonOrRole(1, list.get(i), menu.getId());
				}
			}
			return 1;
		} catch (Exception e) {
			
			e.printStackTrace();
			return 0;
		}
		
	}
	/**
	 * 删除菜单
	 * @param id菜单id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delMenuforManager", method = RequestMethod.POST)
	public Integer delMenuforManager(Integer id,HttpServletRequest request){
		SVDS_User loginUser = sessionService.getSessionByIp(request); //获得用户id
		System.out.println(id);
		List<String> ids = dao.getFileidBymenuid(id);//获得该菜单下所有文件
		System.out.println("---"+ids.size());
		//filesService.recycleFiles(ids, 1,loginUser);
		removeSolrById(ids); //删除菜单下文件索引
	/*	List<Integer> fileIds=dao.getFileIdBymenuId(id);
		logService.deleteByFileId(fileIds);*/
		dao.delsvdsrecycleByid(id);//删除回收站下所有数据
		dao.delInfodatabyid(id);//删除基本信息
		dao.delMenuRole(id);//删除菜单权限
		dao.delbtnrole(id); //删除操作权限
		dao.delsvds_collectfile(id);//删除收藏夹
		dao.delAliasBymenuid(id);//删除标签
		dao.delsvds_log(id);//删除日志
		dao.delsvds_visited(id);//删除首页推送
		dao.delFileBymenuid(id);//删除文件
		dao.delinfoByMenuid(id);//基本信息文件
		dao.deltabsByMenuid(id);//删除选项卡
		dao.delruleByMenuid(id);//删除文件关联信息
		dao.delMessageBymenuid(id);//删除消息分享
		phdDao.delph(id); //删除验证矩阵
		return removemenu(id);//删除菜单
	}
	/**
	 * 删除菜单到回收站中
	 * @param id  菜单id
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/updateMenuforManager", method = RequestMethod.GET)
	public Integer updateMenuforManager(Integer id,HttpServletRequest request) throws Exception{
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		
		logService.insertLog(new SVDS_Log("执行了删除节点操作", DateUtils.getDate(),
				loginUser,IpAddressUtils.getIpAddress(request),"成功"));
		List<Integer> ids = dao.getFileIdBymenuId(id);
		System.out.println("---"+ids.size());
		if(ids.size()>0){
			filesService.recycleFiles(ids, 1,loginUser);
		}
		dao.delInfodatabyid(id); //删除基本信息
		//dao.delMenuRole(id);
		dao.delsvds_collectfile(id); //删除收藏夹
		dao.delAliasBymenuid(id);
		//dao.delsvds_log(id);
		dao.delsvds_visited(id);//删除首页推送
		dao.updateFileByMenuid(id,1);//修改文件为回收站状态
		dao.delinfoByMenuid(id); //删除基本信息
		dao.updatetabsByMenuid(id,1);//修改选显卡为回收站状态
		dao.delruleByMenuid(id); //删除关联规则
		dao.delMessageBymenuid(id);//删除日志
		phdDao.delph(id);//删除验证矩阵
		return updatemenu(id,loginUser,1);
	}
	/**
	 * 回收站还原菜单
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/updateMenuState", method = RequestMethod.POST)
	public Integer updateMenuState(Integer id,HttpServletRequest request) throws Exception{
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		System.out.println("还原节点");
		logService.insertLog(new SVDS_Log("执行了还原节点操作", DateUtils.getDate(),
				loginUser,IpAddressUtils.getIpAddress(request),"成功"));
		System.out.println(id);
		List<Integer> ids = dao.getFileIdBymenuId(id);
		System.out.println("---"+ids.size());
		if(ids.size()>0){
			filesService.recycleFiles(ids, 0,loginUser);
		}
		dao.delInfodatabyid(id);
		//dao.delMenuRole(id);
		dao.delsvds_collectfile(id);
		dao.delAliasBymenuid(id);
		//dao.delsvds_log(id);
		dao.delsvds_visited(id);
		dao.updateFileByMenuid(id,0);
		dao.delinfoByMenuid(id);
		dao.updatetabsByMenuid(id,0);
		dao.delruleByMenuid(id);
		dao.delMessageBymenuid(id);
		phdDao.delph(id);
		return updatemenu(id,loginUser,0);
	}
	/**
	 * 删除该节点下子节点
	 * @param id
	 * @return
	 */
	public Integer removemenu(Integer id){
		//Integer thisid = id;
		try {
			if(dao.ishavechildren(id)==0){
				
				dao.delMenuforManager(id);
			}else{
				
				removemenuChildren(dao.getChildrenids(id));
				dao.delMenuforManager(id);
				
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 删除该节点子节点到回收站中
	 * @param id
	 * @param loginUser
	 * @param state
	 * @return
	 */
	public Integer updatemenu(Integer id,SVDS_User loginUser,Integer state){
		//Integer thisid = id;
		try {
			if(dao.ishavechildren(id)==0){
				
				dao.updateMenuforManager(id,state);
			}else{
				
				updatemenuChildren(dao.getChildrenids(id),loginUser,state);
				dao.updateMenuforManager(id,state);
				
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 递归删除子节点
	 * @param ids
	 */
	public void removemenuChildren(List<Integer> ids){
		for (int i = 0; i < ids.size(); i++) {
			if(dao.ishavechildren(ids.get(i))==0){
				dao.delMenuforManager(ids.get(i));
				dao.delMenuforManager(ids.get(i));
				List<String> idsS = dao.getFileidBymenuid(ids.get(i));
				System.out.println("---"+idsS.size());
				removeSolrById(idsS);
				dao.delsvdsrecycleByid(ids.get(i));
				dao.delInfodatabyid(ids.get(i));
				dao.delMenuRole(ids.get(i));
				dao.delbtnrole(ids.get(i));
				dao.delsvds_collectfile(ids.get(i));
				dao.delAliasBymenuid(ids.get(i));
				dao.delsvds_log(ids.get(i));
				dao.delsvds_visited(ids.get(i));
				dao.delFileBymenuid(ids.get(i));
				dao.delinfoByMenuid(ids.get(i));
				dao.deltabsByMenuid(ids.get(i));
				dao.delruleByMenuid(ids.get(i));
				dao.delMessageBymenuid(ids.get(i));
				phdDao.delph(ids.get(i));
				
			}else{
				removemenuChildren(dao.getChildrenids(ids.get(i)));
				dao.delMenuforManager(ids.get(i));
				List<String> idsS = dao.getFileidBymenuid(ids.get(i));
				System.out.println("---"+idsS.size());
				removeSolrById(idsS);
				dao.delsvdsrecycleByid(ids.get(i));
				dao.delInfodatabyid(ids.get(i));
				dao.delMenuRole(ids.get(i));
				dao.delbtnrole(ids.get(i));
				dao.delsvds_collectfile(ids.get(i));
				dao.delAliasBymenuid(ids.get(i));
				dao.delsvds_log(ids.get(i));
				dao.delsvds_visited(ids.get(i));
				dao.delFileBymenuid(ids.get(i));
				dao.delinfoByMenuid(ids.get(i));
				dao.deltabsByMenuid(ids.get(i));
				dao.delruleByMenuid(ids.get(i));
				dao.delMessageBymenuid(ids.get(i));
				phdDao.delph(ids.get(i));
			}
		}
	}
	/**
	 * 递归删除子节点到回收站中
	 * @param ids
	 * @param loginUser
	 * @param state
	 */
	public void updatemenuChildren(List<Integer> ids,SVDS_User loginUser,Integer state){
		for (int i = 0; i < ids.size(); i++) {
			if(dao.ishavechildren(ids.get(i))==0){
				
				dao.updateMenuforManager(ids.get(i),state);
				dao.updateMenuforManager(ids.get(i),state);
			//	List<String> idsS = dao.getFileidBymenuid(ids.get(i));
				List<Integer> idS = dao.getFileIdBymenuId(ids.get(i));
				if(idS.size()>0){
					filesService.recycleFiles(idS, state,loginUser);
				}
				
				//System.out.println("---"+ids.size());
				//removeSolrById(idsS);
				dao.delInfodatabyid(ids.get(i));
			//	dao.delMenuRole(ids.get(i));
				dao.delsvds_collectfile(ids.get(i));
				dao.delAliasBymenuid(ids.get(i));
			//	dao.delsvds_log(ids.get(i));
				dao.delsvds_visited(ids.get(i));
				dao.delFileBymenuid(ids.get(i));
				dao.delinfoByMenuid(ids.get(i));
				dao.deltabsByMenuid(ids.get(i));
				dao.delruleByMenuid(ids.get(i));
				dao.delMessageBymenuid(ids.get(i));
				phdDao.delph(ids.get(i));
				
			}else{
				updatemenuChildren(dao.getChildrenids(ids.get(i)),loginUser,state);
				dao.updateMenuforManager(ids.get(i),1);
				List<Integer> idS = dao.getFileIdBymenuId(ids.get(i));
				if(idS.size()>0){
					filesService.recycleFiles(idS, state,loginUser);
				}
				//System.out.println("---"+ids.size());
				//removeSolrById(idsS);
				dao.delInfodatabyid(ids.get(i));
			//	dao.delMenuRole(ids.get(i));
				dao.delsvds_collectfile(ids.get(i));
				dao.delAliasBymenuid(ids.get(i));
			//	dao.delsvds_log(ids.get(i));
				dao.delsvds_visited(ids.get(i));
				dao.delFileBymenuid(ids.get(i));
				dao.delinfoByMenuid(ids.get(i));
				dao.deltabsByMenuid(ids.get(i));
				dao.delruleByMenuid(ids.get(i));
				dao.delMessageBymenuid(ids.get(i));
				phdDao.delph(ids.get(i));
			}
		}
	}
	/**
	 * 删除文件索引
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
	 * 是否已经存在菜单节点
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/ishavemenuname", method = RequestMethod.GET)
	public Integer ishavemenuname(String name){
		
		return dao.ishavemenuname(name);
	}
	/**
	 * 移动拖拽菜单节点
	 * @param id
	 * @param targetid
	 * @param lv
	 * @return
	 */
	@RequestMapping(value = "/yidongmenu", method = RequestMethod.GET)
public Integer yidongmenu(Integer id,Integer targetid,Integer lv){
		
		return dao.yidongMenud(id, targetid, lv+1);
	}
}
