package ac.drsi.nestor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.common.DateUtils;
import ac.drsi.nestor.dao.SVDS_AliasFileDao;
import ac.drsi.nestor.dao.SVDS_CollectFileDao;
import ac.drsi.nestor.dao.SVDS_FilesDao;
import ac.drsi.nestor.dao.SVDS_MessageDao;
import ac.drsi.nestor.dao.SVDS_RecycleDao;
import ac.drsi.nestor.dao.SVDS_VisitedDao;
import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_Recycle;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SearchBean;

import com.github.pagehelper.PageHelper;

@Service
public class SVDS_FilesService {
	@Autowired
	SVDS_FilesDao dao;
	@Autowired
	SVDS_CollectFileDao collectFileDao;
	@Autowired
	SVDS_AliasFileDao aliasFileDao;
	@Autowired
	SVDS_VisitedDao visitedDao;
	@Autowired
	SVDS_MessageDao messageDao;
	@Autowired
	SVDS_RecycleDao recycleDao;

	/**
	 * 查询全部文件
	 * 
	 * @return
	 */
	public List<SVDS_Files> getFilesAll() {
		return dao.getFilesAll();
	}

	/**
	 * 分页查询全部文件
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Files> getFilesAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Files> files = dao.getFilesAll();
		return files;
	}

	/**
	 * 根据文件名称进行模糊查询
	 * 
	 * @param filesName
	 * @return
	 */
	public List<SVDS_Files> listFilesByName(String filesName) {
		return dao.listFilesByName(filesName);
	}

	/**
	 * 根据文件名称和选项卡Id判断文件是否存在(未删除)
	 * 
	 * @param fileName
	 *            文件名称
	 * @param tabsId
	 *            选项卡Id
	 * @return
	 */
	public List<SVDS_Files> getFilesByName(String filesName, Integer tabsId) {
		return dao.getFilesByName(filesName, tabsId);
	}

	/**
	 * 根据文件名称和选项卡Id判断文件是否存在(全部文件)
	 * 
	 * @param fileName
	 *            文件名称
	 * @param tabsId
	 *            选项卡Id
	 * @return
	 */
	public List<SVDS_Files> getFilesByNameAll(String filesName, Integer tabsId) {
		return dao.getFilesByNameAll(filesName, tabsId);
	}

	/**
	 * 根据文件名称和选项卡Id查询最大版本
	 * 
	 * @param fileName
	 *            文件名称
	 * @param tabsId
	 *            选项卡Id
	 * @return
	 */
	public Integer getFilesByFileVersion(String fileName, Integer tabsId) {
		return dao.getFilesByFileVersion(fileName, tabsId);
	}

	/**
	 * 根据文件名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param FilesName
	 * @return
	 */
	public List<SVDS_Files> listFilesByName(int pageNum, int pageSize,
			String filesName) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Files> files = dao.listFilesByName(filesName);
		return files;
	}

	/**
	 * 多条件查询
	 * 
	 * @param files
	 * @return
	 */
	public List<SVDS_Files> selectWhitFilesSql(SVDS_Files file,
			Map<String, Object> param) {
		return dao.selectWhitFilesSql(file, param);
	}

	public List<SVDS_Files> selectFilesSql(List<SearchBean> bean) {
		return dao.selectFilesSql(bean);
	}

	/**
	 * 多条件查询分页
	 * 
	 * @param files
	 * @return
	 */
	public List<SVDS_Files> selectWhitFilesSql(int pageNum, int pageSize,
			String sortOrder, List<Integer> fileIds, Integer userId) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Files> files = dao.listFilesByIdsSort(fileIds, sortOrder,
				userId);
		return files;
	}

	/**
	 * 根据文件路径集合查询
	 * 
	 * @param fileUrls
	 * @return
	 */
	public List<SVDS_Files> getFilesByUrl(List<String> fileUrls) {
		return dao.getFilesByUrl(fileUrls);
	}

	/**
	 * 根据文件路径集合分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param fileUrls
	 * @return
	 */
	public List<SVDS_Files> getFilesByUrl(int pageNum, int pageSize,
			List<String> fileUrls) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Files> files = dao.getFilesByUrl(fileUrls);
		return files;
	}

	/**
	 * 查询所有文件总数
	 * 
	 * @return
	 */
	public Integer getFilesAllCount() {
		return dao.getFilesAllCount();
	}

	/**
	 * 获取文件格式
	 * 
	 * @return
	 */
	public List<String> listFormat() {
		return dao.listFormat();
	}

	/**
	 * 添加文件
	 * 
	 * @param Files
	 * @return
	 */
	public int insertFiles(SVDS_Files Files) {
		return dao.insertFiles(Files);
	}

	/**
	 * 根据Id查询文件
	 * 
	 * @param FilesId
	 * @return
	 */
	public SVDS_Files getFilesById(Integer fileId) {
		return dao.getFilesById(fileId);
	}

	/**
	 * 根据密级Id查询文件
	 * 
	 * @param FilesId
	 * @return
	 */
	public List<SVDS_Files> listBySecurityId(Integer securityId, Integer tabsId) {
		return dao.listBySecurityId(securityId, tabsId);
	}

	/**
	 * 根据Id批量查询文件
	 * 
	 * @param FilesId
	 * @return
	 */
	public List<SVDS_Files> getFilesByIds(List<Integer> fileIds) {
		return dao.getFilesByIds(fileIds);
	}

	/**
	 * 根据文件Id集合跟文件名称批量查询
	 * 
	 * @param fileIds
	 * @param fileName
	 * @return
	 */
	public List<SVDS_Files> getFilesByIdsAndFileName(List<Integer> fileIds,
			String fileName) {
		return dao.getFilesByIdsAndFileName(fileIds, fileName);
	}

	/**
	 * 根据Id批量分页排序查询文件
	 * 
	 * @param FilesId
	 * @return
	 */
	public List<SVDS_Files> listFilesByIdsSort(int pageNum, int pageSize,
			String sortOrder, List<Integer> fileIds, Integer userId) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Files> files = dao.listFilesByIdsSort(fileIds, sortOrder,
				userId);
		return files;
	}

	/**
	 * 根据选项卡的Id的查询
	 * 
	 * @param tabsId
	 * @return
	 */
	public List<SVDS_Files> getFilesByTabsid(Integer tabsId,
			Integer securityId, String fileName, Integer userId) {
		return dao.getFilesByTabsid(tabsId, securityId, fileName, userId);
	}

	/**
	 * 根据选项卡的Id的查询名称排序
	 * 
	 * @param tabsId
	 * @param securityId
	 * @param fileName
	 * @param userId
	 * @return
	 */
	public List<SVDS_Files> listFilesByTabsidSort(Integer tabsId,
			Integer securityId, String fileName, Integer userId, String sortName) {
		return dao.listFilesByTabsidSort(tabsId, securityId, fileName, userId,
				sortName);
	}

	/**
	 * 根据选项卡的Id的查询未删除的文件
	 * 
	 * @param tabsId
	 * @return
	 */
	public List<SVDS_Files> listFilesByTabsId(Integer tabsId) {
		return dao.listFilesByTabsId(tabsId);
	}

	/**
	 * 根据选项卡Id和菜单Id 查询未删除的文件
	 * 
	 * @param tabsId
	 * @param menuId
	 * @return
	 */
	public List<SVDS_Files> listFilesByTabIdMenuId(Integer tabsId,
			Integer menuId) {
		return dao.listFilesByTabIdMenuId(tabsId, menuId);
	}

	/**
	 * 根据已删除的选项卡Id查询文件
	 * 
	 * @param tabsId
	 * @return
	 */
	public List<SVDS_Files> listFilesByDelTabsId(Integer tabsId) {
		return dao.listFilesByDelTabsId(tabsId);
	}

	
	/**
	 * 根据已删除的选项卡Id删除文件
	 * 
	 * @param tabsId
	 * @return
	 */
	public Integer delFileByTabId(@Param("tabsId") Integer tabsId){
		return dao.delFileByTabId(tabsId);
	}
	
	/**
	 * 根据选项卡的Id集合查询
	 * 
	 * @param tabsId
	 * @return
	 */
	public List<SVDS_Files> listFilesByTabsIds(List<Integer> tabsId) {
		return dao.listFilesByTabsIds(tabsId);
	}

	/**
	 * 根据菜单的Id的查询
	 * 
	 * @param tabsId
	 * @return
	 */
	public List<SVDS_Files> listFilesByMenuId(Integer menuId) {
		return dao.listFilesByMenuId(menuId);
	}

	/**
	 * 根据菜单的Id的查询文件
	 * 
	 * @param tabsId
	 * @return
	 */
	public List<SVDS_Files> listFilesByMenuIdAll(Integer menuId) {
		return dao.listFilesByMenuIdAll(menuId);
	}

	/**
	 * 根据选项卡的Id的查询文件
	 * 
	 * @param tabsId
	 * @return
	 */
	public List<SVDS_Files> listFilesByTabIdAll(Integer tabsId) {
		return dao.listFilesByTabIdAll(tabsId);
	}

	/**
	 * 修改备注字段
	 * 
	 * @param Remark
	 * @return
	 */
	public Integer updateRemark(String remark, Integer fileId) {
		return dao.updateRemark(remark, fileId);
	}

	/**
	 * 清空摘要字段
	 * 
	 * @return
	 */
	public Integer emptyAbstracts() {
		return dao.emptyAbstracts();
	}

	/**
	 * 修改文件
	 * 
	 * @param SVDS_Files
	 * @return
	 */
	public Integer updateFiles(SVDS_Files files) {
		return dao.updateFiles(files);
	}

	/**
	 * 加入回收站
	 * 
	 * @param ids
	 * @return
	 */
	public Integer recycleFiles(List<Integer> ids, Integer state, SVDS_User user,Integer tabsId) {
		//aliasFileDao.deleteByFileId(ids);
		//collectFileDao.deleteByFileId(ids);
		//messageDao.deleteByFileId(ids);
		//visitedDao.deleteByFileId(ids);
		aliasFileDao.deleteByTabsId(tabsId);
		collectFileDao.deleteByTabsId(tabsId);
		messageDao.deleteByTabsId(tabsId);
		visitedDao.deleteByTabsId(tabsId);
		if (state != 0) {
			for (int i = 0; i < ids.size(); i++) {
				SVDS_Recycle recycle = new SVDS_Recycle();
				SVDS_Files files = dao.getFilesById(ids.get(i));
				recycle.setFiles(files);
				recycle.setRecDate(DateUtils.getDate());
				recycle.setUser(user);
				recycleDao.insertRecycle(recycle);
				SVDS_Files parentFile = dao.getFilesById(ids.get(i));
//				System.out.println(parentFile.getpId());
//				System.out.println(parentFile.getpId() == null
//						|| parentFile.getpId() == 0);
//				System.out.println(parentFile.getpId());
				if (parentFile.getpId() != null && parentFile.getpId() == 0) {
					SVDS_Files file = dao.getFilesByParentId(parentFile
							.getFileId());
					if (file != null) {
						file.setpId(0);
						dao.updateFiles(file);
						dao.updateFilesByPId(parentFile.getFileId(),
								file.getFileId());
					}
				}
			}
		}
		return dao.recycleFiles(ids, state);
	}
	public Integer recycleFiles(List<Integer> ids, Integer state, SVDS_User user) {
		aliasFileDao.deleteByFileId(ids);
		collectFileDao.deleteByFileId(ids);
		messageDao.deleteByFileId(ids);
		visitedDao.deleteByFileId(ids);
		if (state != 0) {
			for (int i = 0; i < ids.size(); i++) {
				SVDS_Recycle recycle = new SVDS_Recycle();
				SVDS_Files files = dao.getFilesById(ids.get(i));
				recycle.setFiles(files);
				recycle.setRecDate(DateUtils.getDate());
				recycle.setUser(user);
				recycleDao.insertRecycle(recycle);
				SVDS_Files parentFile = dao.getFilesById(ids.get(i));
				System.out.println(parentFile.getpId());
				System.out.println(parentFile.getpId() == null
						|| parentFile.getpId() == 0);
				System.out.println(parentFile.getpId());
				if (parentFile.getpId() != null && parentFile.getpId() == 0) {
					SVDS_Files file = dao.getFilesByParentId(parentFile
							.getFileId());
					if (file != null) {
						file.setpId(0);
						dao.updateFiles(file);
						dao.updateFilesByPId(parentFile.getFileId(),
								file.getFileId());
					}
				}
			}
		}
		return dao.recycleFiles(ids, state);
	}
	/**
	 * 还原文件
	 * 
	 * @param ids
	 * @param state
	 * @return
	 */
	public Integer updateFiles(List<Integer> ids, Integer state) {
		return dao.recycleFiles(ids, state);
	}

	/**
	 * 根据Id删除文件
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteFiles(List<Integer> ids) {
		return dao.deleteFiles(ids);
	}

	/**
	 * 查询某一年的数据上传量
	 * 
	 * @return
	 */
	public List<ChartEntity> listMonth(String yearValue) {
		List<ChartEntity> entityAll = new ArrayList<ChartEntity>();
		for (int i = 1; i <= 12; i++) {
			ChartEntity entity = new ChartEntity();
			entity.setName(i + "");
			entity.setValue(0 + "");
			entityAll.add(entity);
		}
		List<ChartEntity> entitys = dao.listMonth(yearValue);
		if (entitys.size() > 0) {
			for (ChartEntity chartEntity : entityAll) {
				for (ChartEntity ce : entitys) {
					if (ce.getName().equals(chartEntity.getName())) {
						chartEntity.setValue(ce.getValue());
					}
				}
			}
			return entityAll;
		} else {
			return null;
		}

	}
	/**
	 * 查询某专业一年的数据上传量
	 * @param menuIds
	 * @return
	 */
	public List<ChartEntity> listMonthByMenuId(List<Integer> menuIds, String yearValue) {
		List<ChartEntity> entityAll = new ArrayList<ChartEntity>();
		for (int i = 1; i <= 12; i++) {
			ChartEntity entity = new ChartEntity();
			entity.setName(i + "");
			entity.setValue(0 + "");
			entityAll.add(entity);
		}
		List<ChartEntity> entitys = dao.listMonthByMenuId(menuIds,yearValue);
		if (entitys.size() > 0) {
			for (ChartEntity chartEntity : entityAll) {
				for (ChartEntity ce : entitys) {
					if (ce.getName().equals(chartEntity.getName())) {
						chartEntity.setValue(ce.getValue());
					}
				}
			}
			return entityAll;
		} else {
			return null;
		}

	}
	/**
	 * 查询各类文档占比图
	 * 
	 * @return
	 */
	public List<ChartEntity> listformat() {
		return dao.listformat();
	}

	/**
	 * 查询某专业下各类文档占比图
	 * @param menuIds
	 * @return
	 */
	public List<ChartEntity> listformatByMenuId(List<Integer> menuIds) {
		
		return dao.listformatByMenuId( menuIds);
	}
	/**
	 * 查询文件密级进行文档占比图
	 * 
	 * @return
	 */
	public List<ChartEntity> listSecurity(){
		return dao.listSecurity();
	}
	/**
	 * 查询某专业下文件密级进行文档占比图
	 * @param menuIds
	 * @return
	 */
	public List<ChartEntity> listSecurityByMenuId(List<Integer> menuIds){
		return dao.listSecurityByMenuId(menuIds);
	}
	/**
	 * 系统标签数目最多的例题
	 * @return
	 */
	public SVDS_Menu aliasManyByFile(){
		SVDS_Menu menu=dao.aliasManyByFile();
		if(menu!=null){
			return menu;
		}else{
			return null;
		}
	}
	
	/**
	 * TOP5最常访问文件的点击次数
	 * @return
	 */
	public List<ChartEntity> listVisited(){
		return dao.listVisited();
	}
	/**
	 * 某专业下TOP5最常访问文件的点击次数
	 * @param menuIds
	 * @return
	 */
	public List<ChartEntity> listVisitedByMenuId(List<Integer> menuIds){
		return dao.listVisitedByMenuId(menuIds);
	}
	
	/**
	 * 某专业下的文档总数
	 * @param menuIds
	 * @return
	 */
	public Integer fileCountByMenuId(List<Integer> menuIds){
		return dao.fileCountByMenuId(menuIds);
	}
	
	/**
	 * 某专业下各例题文档数占比
	 * @param menuIds
	 * @return
	 */
	public List<ChartEntity> listfileCountByMenuId(List<Integer> menuIds){
		return dao.listfileCountByMenuId(menuIds);
	}
	/**
	 * 某专业下各例题数据量占比
	 * @param menuIds
	 * @return
	 */
	public List<ChartEntity> listfileSizeByMenuId(List<Integer> menuIds){
		return dao.listfileSizeByMenuId(menuIds);
	}
	/**
	 * 系统年新装载数据量统计
	 * @return
	 */
	public List<ChartEntity> listFileCountAll(){
		return dao.listFileCountAll();
	}
	/**
	 * 某专业的年新装载数据量统计
	 * @param menuIds
	 * @return
	 */
	public List<ChartEntity> listFileCountAllByMenuId( List<Integer> menuIds){
		return dao.listFileCountAllByMenuId(menuIds);
	}
	
	/**
	 * 某专业的系统标签总数
	 * @param menuIds
	 * @return
	 */
	public Integer sysAliasCountByMenuId( List<Integer> menuIds){
		return dao.sysAliasCountByMenuId(menuIds);
	}
}
