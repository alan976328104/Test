package ac.drsi.nestor.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.dao.SVDS_AliasDao;
import ac.drsi.nestor.dao.SVDS_AliasFileDao;
import ac.drsi.nestor.dao.SVDS_CollectFileDao;
import ac.drsi.nestor.dao.SVDS_FolderDao;
import ac.drsi.nestor.dao.SVDS_LogDao;
import ac.drsi.nestor.dao.SVDS_MajorDao;
import ac.drsi.nestor.dao.SVDS_MessageDao;
import ac.drsi.nestor.dao.SVDS_UserAllDao;
import ac.drsi.nestor.dao.SVDS_UserDao;
import ac.drsi.nestor.dao.SVDS_VisitedDao;
import ac.drsi.nestor.entity.SVDS_Folder;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Role;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SVDS_UserAll;

import com.github.pagehelper.PageHelper;

@Service
public class SVDS_UserService {
	@Autowired
	SVDS_UserDao dao;
	@Autowired
	SVDS_LogDao logDao;
	@Autowired
	SVDS_MajorDao majorDao;
	@Autowired
	SVDS_VisitedDao visitedDao;
	@Autowired
	SVDS_FolderDao folderDao;
	@Autowired
	SVDS_UserAllDao userAllDao;
	@Autowired
	SVDS_AliasDao aliasDao;
	@Autowired
	SVDS_AliasFileDao aliasFileDao;
	@Autowired
	SVDS_CollectFileDao collectFileDao;
	@Autowired
	SVDS_MessageDao messageDao;

	/**
	 * 查询全部用户
	 * 
	 * @return
	 */
	public List<SVDS_User> getUserAll() {
		return dao.getUserAll();
	}

	/**
	 * 分页查询全部用户
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_User> getUserAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_User> users = dao.getUserAll();
		return users;
	}

	/**
	 * 根据用户姓名进行模糊查询
	 * 
	 * @param username
	 * @return
	 */
	public List<SVDS_User> getUserByName(String username) {
		return dao.getUserByName(username);
	}

	/**
	 * 根据用户姓名进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param username
	 * @return
	 */
	public List<SVDS_User> getUserByName(int pageNum, int pageSize,
			String username) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_User> users = dao.getUserByName(username);
		return users;
	}
	
	/**
	 * 查询系统管理员创建的用户
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_User> listUserBySys(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_User> users = dao.listUserBySys();
		return users;
	}

	/**
	 * 根据部门名称模糊查询用户
	 * 
	 * @param deptname
	 * @return
	 */
	public List<SVDS_User> getUserByDeptName(String deptname) {
		return dao.getUserByDeptName(deptname);
	}

	/**
	 * 根据部门名称查询用户
	 * 
	 * @param deptname
	 * @return
	 */
	public List<SVDS_User> listUserByDeptName(int pageNum, int pageSize,
			String deptname, String username) {
		List<SVDS_User> users = null;
		if (deptname == "" || deptname == null) {
			users = dao.listUserByDeptName(deptname, username);
		} else {
			PageHelper.startPage(pageNum, pageSize);
			users = dao.listUserByDeptName(deptname, username);
		}
		return users;
	}

	/**
	 * 专业管理员获取用户
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param deptname
	 * @return
	 */
	public List<SVDS_UserAll> listUserByDeptName(String deptname) {
		return userAllDao.listUserByDeptName(deptname);
		// return dao.userByDeptName(deptname);
	}

	/**
	 * 根据密级查询用户
	 * 
	 * @param deptname
	 * @param ids
	 *            文件ids
	 * @param userId
	 * @return
	 */
	public List<SVDS_User> listUserBySecretlevel(String deptname, Integer id,
			Integer userId, List<Integer> roleIds) {
		List<String> secretlevels = new ArrayList<String>();
		/*
		 * if (ids.length > 0) { for (int i = 0; i < ids.length; i++) {// 123456
		 * if (ids[i].equals(1)) {//非密 secretlevels.add("D");
		 * secretlevels.add("B"); secretlevels.add("C"); } else if
		 * (ids[i].equals(2)) {//内部 secretlevels.add("D");
		 * secretlevels.add("B"); secretlevels.add("C"); } else if
		 * (ids[i].equals(3)) {//秘密 secretlevels.add("B");
		 * secretlevels.add("C"); } else if (ids[i].equals(4)) {//机密
		 * secretlevels.add("B"); } else if (ids[i].equals(5)) {//普通商密
		 * secretlevels.add("D"); secretlevels.add("B"); secretlevels.add("C");
		 * } else{//核心商密 secretlevels.add("D"); secretlevels.add("B");
		 * secretlevels.add("C"); } }
		 */
		if (id.equals(1)) {// 非密
			secretlevels.add("D");
			secretlevels.add("B");
			secretlevels.add("C");
			secretlevels.add("Z");
		} else if (id.equals(2)) {// 内部
			secretlevels.add("D");
			secretlevels.add("B");
			secretlevels.add("C");
			secretlevels.add("Z");
		} else if (id.equals(3)) {// 秘密
			secretlevels.add("B");
			secretlevels.add("C");
		} else if (id.equals(4)) {// 机密
			secretlevels.add("B");
		} else if (id.equals(5)) {// 普通商密
			secretlevels.add("D");
			secretlevels.add("B");
			secretlevels.add("C");
		} else {// 核心商密
			secretlevels.add("D");
			secretlevels.add("B");
			secretlevels.add("C");
		}
		return dao.listUserBySecretlevel(deptname, secretlevels, userId,
				roleIds);
		/*
		 * }else{ return null; }
		 */
	}

	/**
	 * 查询所有用户总数
	 * 
	 * @return
	 */
	public Integer getUserAllCount() {
		return dao.getUserAllCount();
	}

	/**
	 * 根据角色Id查找用户
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_User> listUserByRoleId(Integer roleId) {
		return dao.listUserByRoleId(roleId);
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @throws UnknownHostException
	 * @throws Exception
	 */
	public Integer insertUser(JSONArray data, SVDS_Role role,
			SVDS_User loginUser, HttpServletRequest request)
			throws UnknownHostException {
		int cont = 0;
		for (int i = 0; i < data.size(); i++) {
			JSONObject object = data.getJSONObject(i);
			SVDS_User user = new SVDS_User();
			if (existUserById(object.get("id").toString()) != null) {
				break;
			} else {
				user.setId(object.get("id").toString());
				user.setUsername(object.get("username").toString());
				user.setCode(object.get("code").toString());
				user.setPwd("12345678");
				user.setCardno(object.get("cardno").toString());
				user.setKeyno(object.get("keyno").toString());
				//SVDS_Major major = majorDao.getMajorById(1);
				// user.setMajor(major);
				user.setRole(role);
				if (object.get("secretlevel").toString().equals("A")) {
					user.setSecretlevel("B");
				} else {
					user.setSecretlevel(object.get("secretlevel").toString());
				}
				user.setDeptname(object.get("deptname").toString());
				Integer userId = dao.insertUser(user);
				System.out.println("新增用户Id：" + user.getUserId());
				if (userId > 0) {
					cont++;
					SVDS_Folder folder = new SVDS_Folder();
					folder.setFolderName("默认收藏夹");
					folder.setFolderDate(DateUtils.getDate());
					folder.setParentId(0);
					// folder.setIcon("../assets/images/folder.png");
					folder.setUser(dao.getUserById(user.getUserId()));
					// System.out.println(dao.getUserById(user.getUserId()));

					folderDao.insertFolder(folder);
					logDao.insertLog(new SVDS_Log("执行了添加 "
							+ object.get("username").toString() + " 用户操作",
							DateUtils.getDate(), loginUser, IpAddressUtils
									.getIpAddress(request), "成功"));
				}
			}
		}
		if (cont == data.size()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 根据用户Id查询用户
	 * 
	 * @param userId
	 * @return
	 */
	public SVDS_User getUserById(Integer userId) {
		return dao.getUserById(userId);
	}

	/**
	 * 根据Id查询用户是否存在
	 * 
	 * @param userId
	 * @return
	 */
	public SVDS_User existUserById(String id) {
		return dao.existUserById(id);
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	public Integer updateUser(SVDS_User user) {
		return dao.updateUser(user);
	}

	/**
	 * 分配用户
	 * 
	 * @param roleId
	 *            ,ids
	 * @return
	 */
	public Integer updateRoleUser(Integer roleId, List<Integer> ids) {
		return dao.updateRoleUser(roleId, ids);
	}

	/**
	 * 根据Id删除用户
	 * 
	 * @param ids
	 * @return
	 * @throws UnknownHostException
	 */
	public Integer deleteUser(List<Integer> ids, SVDS_User loginUser,
			HttpServletRequest request) throws UnknownHostException {
		visitedDao.deleteByUserId(ids);
		collectFileDao.deleteByUserId(ids);
		aliasFileDao.deleteByUserId(ids);
		folderDao.deleteFolderByUserIds(ids);
		aliasDao.deleteByUserId(ids);
		messageDao.deleteByUserId(ids);
		logDao.deleteByUserId(ids);
		Integer count = 0;
		for (int i = 0; i < ids.size(); i++) {
			String userName = dao.getUserById(ids.get(i)).getUsername();
			Integer isSuccess = dao.deleteUserById(ids.get(i));
			if (isSuccess > 0) {
				logDao.insertLog(new SVDS_Log("执行了删除 " + userName + " 用户操作",
						DateUtils.getDate(), loginUser, IpAddressUtils
								.getIpAddress(request), "成功"));
				count++;
			}
		}
		if (ids.size() == count) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 查询系统管理员创建的用户
	 * 
	 * @return
	 */
	public List<SVDS_User> listUserBySys() {
		return dao.listUserBySys();
	}
}
