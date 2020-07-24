package ac.drsi.nestor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.common.DateUtils;
import ac.drsi.nestor.dao.SVDS_CollectFileDao;
import ac.drsi.nestor.dao.SVDS_FolderDao;
import ac.drsi.nestor.dao.SVDS_UserDao;
import ac.drsi.nestor.entity.SVDS_Folder;

@Service
public class SVDS_FolderService {
	@Autowired
	SVDS_FolderDao dao;
	@Autowired
	SVDS_UserDao userDao;
	@Autowired
	SVDS_CollectFileDao collectFileDao;
	/**
	 * 获取子级集合
	 * 
	 * @param parentId
	 * @param childList
	 * @return
	 */
	private static List<SVDS_Folder> getChild(Integer parentId,
			List<SVDS_Folder> childList) {
		List<SVDS_Folder> listParentOrgs = new ArrayList<SVDS_Folder>();
		List<SVDS_Folder> listNotParentOrgs = new ArrayList<SVDS_Folder>();
		// 遍历childList，找出所有的根节点和非根节点
		if (childList != null && childList.size() > 0) {
			for (SVDS_Folder record : childList) {
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
			for (SVDS_Folder folder : listParentOrgs) {
				// 递归查询子节点
				folder.setChildren(getChild(folder.getFolderId(),
						listNotParentOrgs));
			}
		}
		return listParentOrgs;
	}
	/**
	 * 无限级分类，实现具有父子关系的数据分类
	 * 
	 * @param folders
	 * @return
	 */
	public List<SVDS_Folder> getFolderOrg(List<SVDS_Folder> folders) {
		List<SVDS_Folder> folderParentList = new ArrayList<SVDS_Folder>();
		List<SVDS_Folder> folderNotParentList = new ArrayList<SVDS_Folder>();
		for (SVDS_Folder folder : folders) {
			if (folder.getParentId() == 0) {
				folderParentList.add(folder);
			} else {
				folderNotParentList.add(folder);
			}
		}
		if (folderParentList.size() > 0) {
			for (SVDS_Folder folder : folderParentList) {
				folder.setChildren(getChild(folder.getFolderId(),
						folderNotParentList));
			}
		}
		return folderParentList;
	}

	/**
	 * 查询全部收藏夹
	 * 
	 * @return
	 */
	public List<SVDS_Folder> getFolderAll() {
		return dao.getFolderAll();
	}

	/**
	 * 根据用户Id进行查询
	 * 
	 * @param FolderName
	 * @return
	 */
	public List<SVDS_Folder> getFolderByUser(Integer userId) {
		List<SVDS_Folder> folders = getFolderOrg(dao.getFolderByUser(userId));
		return folders;
	}

	/**
	 * 查询所有收藏夹总数
	 * 
	 * @return
	 */
	public Integer getFolderAllCount() {
		return dao.getFolderAllCount();
	}

	/**
	 * 根据Id查找
	 * 
	 * @param deptId
	 * @return
	 */
	public SVDS_Folder getFolderById(Integer folderId) {
		return dao.getFolderById(folderId);
	}
	
	/**
	 * 判断是否存在收藏夹
	 * @param folderName
	 * @param parentId
	 * @param userId
	 * @return
	 */
	public SVDS_Folder isExistFolder(String folderName,Integer parentId,Integer userId){
		return dao.isExistFolder(folderName, parentId, userId);
	}

	/**
	 * 添加收藏夹
	 * @param folderName
	 * @param userId
	 * @param parentId
	 * @return
	 */
	public int insertFolder(String folderName, Integer userId, Integer parentId) {
		SVDS_Folder folder = new SVDS_Folder();
		folder.setFolderName(folderName);
		folder.setFolderDate(DateUtils.getDate());
		folder.setParentId(parentId);
		// folder.setIcon("../assets/images/folder.png");
		folder.setUser(userDao.getUserById(userId));
		Integer folderId = dao.insertFolder(folder);
		return folderId;
	}

	/**
	 * 修改收藏夹
	 * 
	 * @param SVDS_Folder
	 * @return
	 */
	public Integer updateFolder(SVDS_Folder folder) {
		return dao.updateFolder(folder);
	}

	/**
	 * 根据Id删除收藏夹
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteFolder(List<Integer> ids, Integer userId) {
		Integer fileSuccess = collectFileDao.deleteByFolderIds(ids);
		Integer isSuccess = dao.deleteFolder(ids, userId);
		if (isSuccess > 0) {
			return isSuccess;
		} else {
			return null;
		}
	}

}
