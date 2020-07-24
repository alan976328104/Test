package ac.drsi.nestor.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_RoleMenuDao;
import ac.drsi.nestor.entity.SVDS_RoleMenu;

@Service
public class SVDS_RoleMenuService {
	@Autowired
	SVDS_RoleMenuDao dao;

	/**
	 * 根据角色Id查询所拥有的菜单菜单权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_RoleMenu> getRoleMenuAll(Integer roleId) {
		return dao.getRoleMenuAll(roleId);
	}

	/**
	 * 给角色添加菜单权限
	 * 
	 * @param roleMenu
	 * @return
	 */
	public int insertRoleMenu(SVDS_RoleMenu roleMenu) {
		return dao.insertRoleMenu(roleMenu);
	}

	/**
	 * 删除角色所拥有的菜单权限
	 * 
	 * @param roleId
	 * @return
	 */
	public Integer deleteRoleMenu(Integer roleId) {
		return dao.deleteRoleMenu(roleId);
	}

	/**
	 * 根据菜单Id查询角色Id
	 * @param menuId
	 * @return
	 */
	public List<SVDS_RoleMenu>listByMenuId(Integer menuId){
		return dao.listByMenuId(menuId);
	}

	/**
	 * 根据菜单Id集合查询角色Id
	 * @param menuIds
	 * @return
	 */
	public List<SVDS_RoleMenu> listByMenuIds(List<Integer>  menuIds){
		return dao.listByMenuIds(menuIds);
	}
}
