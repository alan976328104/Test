package ac.drsi.nestor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_RoleButtonDao;
import ac.drsi.nestor.dao.SVDS_UserDao;
import ac.drsi.nestor.entity.SVDS_RoleButton;
import ac.drsi.nestor.entity.SVDS_User;

@Service
public class SVDS_RoleButtonService {
	@Autowired
	SVDS_RoleButtonDao dao;
	@Autowired
	SVDS_UserDao userDao;

	/**
	 * 根据角色Id查询所拥有的按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_RoleButton> getRoleButtonAll(Integer roleId) {
		return dao.getRoleButtonAll(roleId);
	}

	/**
	 * 根据角色Id菜单Id查询所拥有的菜单按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_RoleButton> listRoleButtonAllById(Integer roleId,
			Integer menuId) {
		return dao.listRoleButtonAllById(roleId, menuId);
	}

	/**
	 * 根据用户Id查询按钮权限
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> listRoleButtonByUserId(Integer userId) {
		SVDS_User roleUser = userDao.getUserById(userId);
		List<SVDS_RoleButton> RoleButtons = dao.getRoleButtonAll(roleUser
				.getRole().getRoleId());
		List<Integer> btnIds = new ArrayList<Integer>();
		for (int i = 0; i < RoleButtons.size(); i++) {
			Integer btnId = RoleButtons.get(i).getBtnId();
			btnIds.add(btnId);
		}
		if (btnIds.size() > 0) {
			return btnIds;
		} else {
			return null;
		}
	}

	/**
	 * 根据用户Id和菜单Id查询操作权限
	 * 
	 * @param userId
	 * @param menuId
	 * @return
	 */
	public List<Integer> listRoleButtonByUserIdMenuId(Integer userId,
			Integer menuId) {
		SVDS_User roleUser = userDao.getUserById(userId);
		List<SVDS_RoleButton> RoleButtons = dao.listRoleButtonAllById(roleUser
				.getRole().getRoleId(), menuId);
		List<Integer> btnIds = new ArrayList<Integer>();
		for (int i = 0; i < RoleButtons.size(); i++) {
			Integer btnId = RoleButtons.get(i).getBtnId();
			btnIds.add(btnId);
		}
		if (btnIds.size() > 0) {
			return btnIds;
		} else {
			return null;
		}
	}

	/**
	 * 给角色添加按钮权限
	 * 
	 * @param roleButton
	 * @return
	 */
	public int insertRoleButton(SVDS_RoleButton roleButton) {
		return dao.insertRoleButton(roleButton);
	}

	/**
	 * 删除角色所拥有的按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	public Integer deleteRoleButton(Integer roleId) {
		return dao.deleteRoleButton(roleId);
	}

	/**
	 * 根据角色Id菜单Id删除操作权限
	 * 
	 * @param roleId
	 * @param menuId
	 * @return
	 */
	public Integer deleteRoleButtonByMenuId(Integer roleId, Integer menuId) {
		return dao.deleteRoleButtonByMenuId(roleId, menuId);
	}
}
