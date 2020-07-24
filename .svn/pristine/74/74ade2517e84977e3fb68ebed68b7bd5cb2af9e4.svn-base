package ac.drsi.nestor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_AliasDao;
import ac.drsi.nestor.dao.SVDS_AliasFileDao;
import ac.drsi.nestor.entity.SVDS_Alias;

@Service
public class SVDS_AliasService {
	@Autowired
	SVDS_AliasDao dao;
	@Autowired
	SVDS_AliasFileDao aliasFiledao;

	/**
	 * 无限级分类，实现具有父子关系的数据分类
	 * 
	 * @param aliass
	 * @return
	 */
	public List<SVDS_Alias> getAliasOrg(List<SVDS_Alias> aliass) {
		List<SVDS_Alias> aliasParentList = new ArrayList<SVDS_Alias>();
		List<SVDS_Alias> aliasNotParentList = new ArrayList<SVDS_Alias>();
		for (SVDS_Alias alias : aliass) {
			if (alias.getParentId() == 0) {
				aliasParentList.add(alias);
			} else {
				aliasNotParentList.add(alias);
			}
		}
		if (aliasParentList.size() > 0) {
			for (SVDS_Alias alias : aliasParentList) {
				alias.setChildren(getChild(alias.getAliasId(),
						aliasNotParentList));
			}
		}
		return aliasParentList;
	}

	/**
	 * 获取子级集合
	 * 
	 * @param parentId
	 * @param childList
	 * @return
	 */
	private static List<SVDS_Alias> getChild(Integer parentId,
			List<SVDS_Alias> childList) {
		List<SVDS_Alias> listParentOrgs = new ArrayList<SVDS_Alias>();
		List<SVDS_Alias> listNotParentOrgs = new ArrayList<SVDS_Alias>();
		// 遍历childList，找出所有的根节点和非根节点
		if (childList != null && childList.size() > 0) {
			for (SVDS_Alias record : childList) {
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
			for (SVDS_Alias alias : listParentOrgs) {
				// 递归查询子节点
				alias.setChildren(getChild(alias.getAliasId(),
						listNotParentOrgs));
			}
		}
		return listParentOrgs;
	}

	/**
	 * 查询全部的标签
	 * 
	 * @return
	 */
	public List<SVDS_Alias> listAliasAll() {
		List<SVDS_Alias> alias = dao.listAliasAll();
		return getAliasOrg(alias);

	}

	/**
	 * 查询所有标签总数
	 * 
	 * @return
	 */
	public Integer countAliasAll() {
		return dao.countAliasAll();
	}

	/**
	 * 根据日期查询
	 * 
	 * @param aliasDate
	 * @return
	 */
	public SVDS_Alias getAliasByDate(String aliasDate) {
		return dao.getAliasByDate(aliasDate);
	}

	/**
	 * 根据Id查找
	 * 
	 * @param deptId
	 * @return
	 */
	public SVDS_Alias getAliasById(Integer aliasId) {
		return dao.getAliasById(aliasId);
	}

	/**
	 * 根据名称查找
	 * 
	 * @param aliasName
	 * @return
	 */
	public SVDS_Alias getByName(String aliasName) {
		return dao.getByName(aliasName);
	}

	/**
	 * 根据标签名称，用户Id和父级Id判断同济是否存在
	 * 
	 * @param aliasName
	 * @param userId
	 * @param parentId
	 * @return
	 */
	public SVDS_Alias getAliasByUserName(String aliasName, Integer userId,
			Integer parentId) {
		return dao.getAliasByUserName(aliasName, userId, parentId);
	}

	/**
	 * 根据标签名称和用户Id查询
	 * 
	 * @param aliasName
	 * @param userId
	 * @return
	 */
	public SVDS_Alias getAliasByUserId(String aliasName, Integer userId) {
		return dao.getAliasByUserId(aliasName, userId);
	}

	/**
	 * 根据标签名称查询个人标签
	 * 
	 * @param aliasName
	 * @return
	 */
	public SVDS_Alias getAliasByType(String aliasName) {
		return dao.getAliasByType(aliasName);
	}

	/**
	 * 根据名称和用户Id查找
	 * 
	 * @param deptId
	 * @return
	 */
	public List<SVDS_Alias> listAliasByUserName(String aliasName, Integer userId) {
		return dao.listAliasByUserName(aliasName, userId);
	}

	/**
	 * 根据用户Id
	 * 
	 * @param userId
	 * @return
	 */
	public List<SVDS_Alias> listAliasByUser(Integer userId) {
		List<SVDS_Alias> alias = dao.listAliasByUser(userId);
		return getAliasOrg(alias);
	}

	/**
	 * 根据登录用户和标签id查询
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Alias> listAliasById(Map<String, Object> param) {
		List<SVDS_Alias> alias = dao.listAliasById(param);
		return alias;
	}

	/**
	 * 根据Id集合查找
	 * 
	 * @param deptId
	 * @return
	 */
	public List<SVDS_Alias> listAliasByIds(List<Integer> aliasIds) {
		List<SVDS_Alias> alias = dao.listAliasByIds(aliasIds);
		return getAliasOrg(alias);
	}

	/**
	 * 添加标签
	 * 
	 * @param SVDS_Alias
	 * @return
	 */
	public int insertAlias(SVDS_Alias alias) {
		return dao.insertAlias(alias);
	}

	/**
	 * 修改标签
	 * 
	 * @param SVDS_Alias
	 * @return
	 */
	public Integer updateAlias(SVDS_Alias alias) {
		return dao.updateAlias(alias);
	}

	/**
	 * 根据Id删除标签
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteAlias(List<Integer> ids) {
		Integer fileSuccess = aliasFiledao.deleteByaliasIds(ids);
		System.out.println(fileSuccess);
		Integer isSuccess = dao.deleteAlias(ids);
		if (isSuccess > 0) {
			return isSuccess;
		} else {
			return null;
		}
	}

	/**
	 * 系统标签的总数
	 * 
	 * @return
	 */
	public Integer sysAliasCount() {
		return dao.sysAliasCount();
	}

	/**
	 * 个人标签的总数
	 * 
	 * @return
	 */
	public Integer perAliasCount() {
		return dao.perAliasCount();
	}

	/**
	 * 创建个人标签最多的用户的个人标签数
	 * 
	 * @return
	 */
	public Integer perAliasBigCount() {
		return dao.perAliasBigCount();
	}

}
