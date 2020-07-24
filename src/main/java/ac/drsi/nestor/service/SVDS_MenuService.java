package ac.drsi.nestor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_MenuDao;
import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Menu;

import com.github.pagehelper.PageHelper;

@Service
public class SVDS_MenuService {
	@Autowired
	SVDS_MenuDao dao;

	/**
	 * 获取子级集合
	 * 
	 * @param parentId
	 * @param childList
	 * @return
	 */
	private static List<SVDS_Menu> getChild(Integer parentId,
			List<SVDS_Menu> childList) {
		List<SVDS_Menu> listParentOrgs = new ArrayList<SVDS_Menu>();
		List<SVDS_Menu> listNotParentOrgs = new ArrayList<SVDS_Menu>();
		// 遍历childList，找出所有的根节点和非根节点
		if (childList != null && childList.size() > 0) {
			for (SVDS_Menu record : childList) {
				// 对比找出父节点
				if (record.getParentId() == parentId) {
					listParentOrgs.add(record);
				} else {
					listNotParentOrgs.add(record);
				}

			}
		}
		// 查询子节点
		if (listParentOrgs.size() > 0) {
			for (SVDS_Menu menu : listParentOrgs) {
				// 递归查询子节点
				menu.setChildren(getChild(menu.getId(), listNotParentOrgs));
				menu.setMenus(getChild(menu.getId(), listNotParentOrgs));
			}
		}
		return listParentOrgs;
	}

	/**
	 * 查询全部菜单
	 * 
	 * @return
	 */
	public List<SVDS_Menu> getMenuAll() {
		List<SVDS_Menu> menus = dao.getMenuAll();// 全部菜单
		return getMenuOrg(menus);
		// return menus;
	}

	/**
	 * 查询回收站里的菜单
	 * 
	 * @return
	 */
	public List<SVDS_Menu> listMenuByState() {
		List<Integer> tabIds = dao.listTabsByState();
		List<Integer> fileIds = dao.listFilesByState();
		List<Integer> listAll = new ArrayList<Integer>();
		List<SVDS_Menu> delMenus = new ArrayList<SVDS_Menu>();
		List<SVDS_Menu> delMenu = new ArrayList<SVDS_Menu>();
		List<SVDS_Menu> delMenuAll = new ArrayList<SVDS_Menu>();
		List<SVDS_Menu> delMenuyState = dao.listMenusByState();
		listAll.addAll(tabIds);
		listAll.addAll(fileIds);
		listAll = new ArrayList<Integer>(new LinkedHashSet<>(listAll));
		// System.out.println(listAll.toString());
		if (listAll != null && listAll.size() > 0) {
			// System.out.println(123);
			List<Integer> menuIds = dao.listMenuByState(listAll);// 回收站的菜单
			// System.out.println(menuIds);
			if (menuIds != null && menuIds.size() > 0) {
				List<SVDS_Menu> menus = new ArrayList<SVDS_Menu>();
				for (int i = 0; i < menuIds.size(); i++) {
					menus.add(dao.getMenuById(menuIds.get(i)));
				}
				delMenus = re(menus);
				delMenus = getMenuOne(delMenus);
			}
		}
		if (delMenuyState != null && delMenuyState.size() > 0) {
			// System.out.println(456);
			// System.out.println(delMenuyState);
			delMenu = re(delMenuyState);
			delMenu = getMenuOne(delMenu);
		}
		// if(delMenus!=null&&delMenus.size()>0){
		delMenuAll.addAll(delMenu);
		delMenuAll.addAll(delMenus);
		// System.out.println(delMenu);
		// System.out.println(delMenus);
		delMenuAll = getMenuOne(delMenuAll);
		// }
		if (delMenuAll != null && delMenuAll.size() > 0) {
			return getMenuOrg(delMenuAll);
		} else {
			return null;
		}

	}

	/**
	 * 去重
	 * 
	 * @param menus
	 * @return
	 */
	public List<SVDS_Menu> getMenuOne(List<SVDS_Menu> menus) {
		for (int i = 0; i < menus.size(); i++) {
			for (int j = menus.size() - 1; j > i; j--) {
				if (menus.get(i).getId().equals(menus.get(j).getId())) {
					menus.remove(j);
				}
			}
		}
		return menus;
	}

	/**
	 * 无限级分类，实现具有父子关系的数据分类
	 * 
	 * @param menus
	 * @return
	 */
	public List<SVDS_Menu> getMenuOrg(List<SVDS_Menu> menus) {
		List<SVDS_Menu> menuParentList = new ArrayList<SVDS_Menu>();
		List<SVDS_Menu> menuNotParentList = new ArrayList<SVDS_Menu>();
		for (SVDS_Menu menu : menus) {
			if (menu.getParentId() == 0) {
				menuParentList.add(menu);
			} else {
				menuNotParentList.add(menu);
			}
		}
		if (menuParentList.size() > 0) {
			for (SVDS_Menu menu : menuParentList) {
				menu.setChildren(getChild(menu.getId(), menuNotParentList));
				menu.setMenus(getChild(menu.getId(), menuNotParentList));
			}
		}
		return menuParentList;
	}
	/**
	 * 根据Id查询子级数据
	 * @param mId
	 * @return
	 */
	public List<SVDS_Menu> listChildrenMenu(Integer mId){
		return dao.listChildrenMenu(mId);
	}
	
	/**
	 * 根据菜单Id集合查询
	 * 
	 * @param menuIds
	 * @return
	 */
	public List<SVDS_Menu> getUserMenu(List<Integer> menuIds) {
		List<SVDS_Menu> menus = new ArrayList<SVDS_Menu>();
		for (int i = 0; i < menuIds.size(); i++) {
			SVDS_Menu menu = dao.getMenuById(menuIds.get(i));
			// if(menu.getParentId()==0){
			menus.add(menu);
			// }
		}
		return getMenuOrg(menus);
		// return menus;
	}

	/**
	 * 分页查询菜单列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Menu> listMenuAll() {
		// 使用分页插件,核心代码就这一行
		List<SVDS_Menu> menus = dao.getMenuAll();
		return getMenuOrg(menus);
		// return menus;
	}

	/**
	 * 根据菜单名称查询
	 * 
	 * @param menuName
	 * @return
	 */
	public SVDS_Menu getMenuByName(String menuName) {
		return dao.getMenuByName(menuName);
	}

	/**
	 * 根据菜单名称进行模糊查询
	 * 
	 * @param menuName
	 * @return
	 */
	public List<SVDS_Menu> listMenuByName(String menuName) {
		return dao.listMenuByName(menuName);
	}

	/**
	 * 根据名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param MenuName
	 * @return
	 */
	public List<SVDS_Menu> listMenuByName(int pageNum, int pageSize,
			String menuName) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Menu> Menus = dao.listMenuByName(menuName);
		return Menus;
	}

	/**
	 * 查询所有菜单总数
	 * 
	 * @return
	 */
	public Integer getMenuAllCount() {
		return dao.getMenuAllCount();
	}

	public Integer getMenu() {

		return null;
	}

	/**
	 * 搜索菜单
	 * 
	 * @param ids
	 * @param text
	 * @return
	 */
	public List<SVDS_Menu> searchMenuById(List<Integer> ids, String text) {
		return dao.searchMenuById(ids, text);
	}

	public List<SVDS_Menu> searchMenuByInfo(List<Integer> ids, String text) {
		return dao.searchMenuByinfo(ids, text);
	}

	/**
	 * 根据id集合查询
	 * 
	 * @param ids
	 * @return
	 */
	public List<SVDS_Menu> listMenuByIds(List<Integer> ids) {
		return dao.listMenuByIds(ids);
	}

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	public int insertMenu(SVDS_Menu menu) {
		return dao.insertMenu(menu);
	}

	/**
	 * 根据Id查询菜单
	 * 
	 * @param menuId
	 * @return
	 */
	public SVDS_Menu getMenuById(Integer menuId) {
		return dao.getMenuById(menuId);
	}

	/**
	 * 根据菜单Id更新文件路径
	 * 
	 * @param menuId
	 *            菜单Id
	 * @param oldName
	 *            旧名称
	 * @param newName
	 *            新名称
	 * @return
	 */
	public Integer updateFilesLocationByMenuId(Integer menuId, String oldName,
			String newName) {
		return dao.updateFilesLocationByMenuId(menuId, oldName, newName);
	}

	/**
	 * 根据Id查询子菜单
	 * 
	 * @param menuId
	 * @return
	 */
	public List<SVDS_Menu> listMenuByParentId(Integer parentId) {
		return dao.listMenuByParentId(parentId);
	}

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 * @return
	 */
	public Integer updateMenu(SVDS_Menu menu) {
		return dao.updateMenu(menu);
	}

	/**
	 * 根据Id删除菜单
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteMenu(List<Integer> ids) {
		return dao.deleteMenu(ids);
	}

	List<SVDS_Menu> newlist = new ArrayList<>();

	/**
	 * 获取子节点的集合
	 * 
	 * @param list
	 * @return
	 */
	public List<SVDS_Menu> re(List<SVDS_Menu> list) {
		newlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getParentId() != 0) {
				dguie(list.get(i));

			}

		}

		return newlist;
	}

	/**
	 * 判断是否为根节点
	 * 
	 * @param menu
	 */
	public void dguie(SVDS_Menu menu) {
		newlist.add(menu);
		if (menu.getParentId() != 0) {
			menu = dao.getMenuById(menu.getParentId());
			dguie(menu);
		}
	}

	/**
	 * 查询各专业的数据占比图
	 * 
	 * @return
	 */
	public List<ChartEntity> listMenu() {
		List<ChartEntity> entitys = dao.listMenu();
		List<String> menuIds = new ArrayList<String>();
		for (ChartEntity chartEntity : entitys) {
			menuIds = getParentId(Integer.parseInt(chartEntity.getName()),
					menuIds);
		}
		List<ChartEntity> entityAll = new ArrayList<ChartEntity>();
		entityAll=same(menuIds, entitys);
		if(entityAll.size()>0){
			return entityAll;
		}else{
			return null;
		}
		 
	}

	public List<String> getParentId(Integer menuId, List<String> menuIds) {
		SVDS_Menu menu = dao.getMenuById(menuId);
		if (menu.getParentId() != 1) {
			getParentId(menu.getParentId(), menuIds);
		} else {
			menuIds.add(menu.getId() + "");
		}
		return menuIds;
	}

	public List<ChartEntity> same(List<String> list, List<ChartEntity> entitys) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			String key = list.get(i);
			String old = map.get(key);
			if (old != null) {
				map.put(key, old + "," + (i));
			} else {
				map.put(key, "" + (i));
			}
		}
		List<ChartEntity> entityAll = new ArrayList<ChartEntity>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = map.get(key);
			if (value.indexOf(",") != -1) {
//				System.out.println(key + " 重复,行： " + value);
				String[] ss = value.split(",");
				ChartEntity entity = new ChartEntity();
				entity.setName(dao.getMenuById(Integer.parseInt(key)).getName());
				int count = 0;
				for (int i = 0; i < ss.length; i++) {
					count += Integer.parseInt(entitys.get(
							Integer.parseInt(ss[i])).getValue());
				}
				entity.setValue(count + "");
				entity.setId(dao.getMenuById(Integer.parseInt(key)).getId().toString());
				entityAll.add(entity);
			} else {
				ChartEntity entity = new ChartEntity();
				entity.setName(dao.getMenuById(Integer.parseInt(key)).getName());
				entity.setValue(entitys.get(Integer.parseInt(value)).getValue());
				entity.setId(dao.getMenuById(Integer.parseInt(key)).getId().toString());
				entityAll.add(entity);
			}
		}
		return entityAll;
	}
	/**
	 * 查询各专业例题数目占比图
	 * 
	 * @return
	 */
	public List<ChartEntity> listMenuCount(){
		List<SVDS_Menu> menus=dao.menuParent();
		List<ChartEntity> entityAll=new ArrayList<ChartEntity>(); 
		for (SVDS_Menu svds_Menu : menus) {
				ChartEntity entity = new ChartEntity();
				entity.setName(svds_Menu.getName());
				entity.setValue(dao.menuChildren(svds_Menu.getId())+"");
				entity.setId(svds_Menu.getId().toString());
				entityAll.add(entity);
		}
		return entityAll;
	}
	/**
	 * 系统标签数目各专业占比及数目
	 * @return
	 */
	public List<ChartEntity> listBySysAlias(){
//		List<ChartEntity> entityAll=dao.listBySysAlias();
//		for (ChartEntity chartEntity : entityAll) {
//			SVDS_Menu menu=dao.getMenuById(Integer.parseInt(chartEntity.getName()));
//			rootNode(menu,chartEntity.getValue());
////			System.out.println("*********start***************");
//		//	System.out.println(rootNode(menu));
////			System.out.println(chartEntity.getValue());
////			System.out.println("***********end*************");
//		}
//		return null;
		
		
		List<ChartEntity> entitys = dao.listBySysAlias();
		List<String> menuIds = new ArrayList<String>();
		for (ChartEntity chartEntity : entitys) {
			menuIds = getParentId(Integer.parseInt(chartEntity.getName()),
					menuIds);
		}
		List<ChartEntity> entity = new ArrayList<ChartEntity>();
		entity=same(menuIds, entitys);
		if(entity.size()>0){
			return entity;
		}else{
			return null;
		}
	}
	public void rootNode(SVDS_Menu menu,String vlaue) {
		if (menu.getParentId() != 1&&menu.getParentId() != 0) {
			menu = dao.getMenuById(menu.getParentId());
			 rootNode(menu,vlaue);
		}else{
			System.out.println(menu);
			System.out.println(vlaue);
		}
	}
}
