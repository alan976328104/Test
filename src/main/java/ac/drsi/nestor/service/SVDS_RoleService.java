package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_RoleButtonDao;
import ac.drsi.nestor.dao.SVDS_RoleDao;
import ac.drsi.nestor.dao.SVDS_RoleMenuDao;
import ac.drsi.nestor.dao.SVDS_UserDao;
import ac.drsi.nestor.entity.SVDS_Role;

import com.github.pagehelper.PageHelper;

@Service
public class SVDS_RoleService {
	@Autowired
	SVDS_RoleDao dao;
	@Autowired
	SVDS_RoleMenuDao roleMenuDao;
	@Autowired
	SVDS_RoleButtonDao roleButtonDao;
	@Autowired
	SVDS_UserDao userDao;

	/**
	 * 查询全部角色
	 * 
	 * @return
	 */
	public List<SVDS_Role> getRoleAll() {
		return dao.getRoleAll();
	}

	/**
	 * 分页查询全部角色
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Role> getRoleAll(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Role> Roles = dao.getRoleAll();
		return Roles;
	}

	/**
	 * 根据角色名称进行模糊查询
	 * 
	 * @param roleName
	 * @return
	 */
	public List<SVDS_Role> getRoleByNames(String roleName) {
		return dao.getRoleByNames(roleName);
	}

	/**
	 * 根据角色名称查询
	 * 
	 * @param roleName
	 * @return
	 */
	public SVDS_Role getRoleByName(String roleName) {
		return dao.getRoleByName(roleName);
	}

	/**
	 * 根据专业管理员创建名称查询角色
	 * 
	 * @param roleName
	 * @param userId
	 * @return
	 */
	public SVDS_Role getRoleByMajorName(String roleName) {
		return dao.getRoleByMajorName(roleName);
	}

	/**
	 * 根据名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param roleName
	 * @return
	 */
	public List<SVDS_Role> getRoleByNames(int pageNum, int pageSize,
			String roleName) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Role> Roles = dao.getRoleByNames(roleName);
		return Roles;
	}

	/**
	 * 查询所有角色总数
	 * 
	 * @return
	 */
	public Integer getRoleAllCount() {
		return dao.getRoleAllCount();
	}

	/**
	 * 添加角色
	 * @param roleName
	 * @param describe
	 * @param userId
	 * @return
	 */
	public Integer insertRole(String roleName, String describe, Integer userId) {
		SVDS_Role role = new SVDS_Role();
		role.setRoleName(roleName);
		role.setDescribe(describe);
		role.setUserId(userId);
		Integer isSuccess = dao.insertRole(role);
		if (isSuccess > 0) {
			return role.getRoleId();
		} else {
			return 0;
		}
	}

	/**
	 * 根据Id查询角色
	 * 
	 * @param roleId
	 * @return
	 */
	public SVDS_Role getRoleById(Integer roleId) {
		return dao.getRoleById(roleId);
	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 */
	public Integer updateRole(SVDS_Role role) {
		return dao.updateRole(role);
	}

	/**
	 * 根据用户Id查询角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<SVDS_Role> listRoleByUserId(Integer userId) {
		return dao.listRoleByUserId(userId);
	}

	/**
	 * 根据用户Id修改角色
	 * 
	 * @param role
	 * @return
	 */
	public Integer updateRoleByUserId(SVDS_Role role,Integer userId) {
		return dao.updateRoleByUserId(role,userId);
	}

	/**
	 * 根据Id集合删除角色
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteRole(List<Integer> ids) {
		for (int i = 0; i < ids.size(); i++) {
			if (userDao.listUserByRoleId(ids.get(i)) != null
					&& userDao.listUserByRoleId(ids.get(i)).size() > 0) {
				return 0;
			} else {
				
			}
		}
		roleMenuDao.deleteRoleMenuByRoleIds(ids);
		dao.deleteRole(ids);
		roleButtonDao.deleteRoleButtonByRoleIds(ids);
		System.out.println(ids);
		return 1;
		//return dao.deleteRole(ids);
	}
}
