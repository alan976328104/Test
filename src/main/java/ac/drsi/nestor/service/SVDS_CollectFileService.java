package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_CollectFileDao;
import ac.drsi.nestor.dao.SVDS_FilesDao;
import ac.drsi.nestor.entity.SVDS_CollectFile;
import ac.drsi.nestor.entity.SVDS_Files;

import com.github.pagehelper.PageHelper;

@Service
public class SVDS_CollectFileService {
	@Autowired
	SVDS_CollectFileDao dao;
	@Autowired
	SVDS_FilesDao fileDao;

	/**
	 * 查询全部收藏文件
	 * 
	 * @return
	 */
	public List<SVDS_CollectFile> getCollectFileAll() {
		return dao.getCollectFileAll();
	}

	/**
	 * 根据收藏文件名称进行模糊查询
	 * 
	 * @param CollectFileName
	 * @return
	 */
	public List<SVDS_CollectFile> getCollectFileByName(String CollectFileName) {
		return dao.getCollectFileByName(CollectFileName);
	}

	/**
	 * 根据名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param CollectFileName
	 * @return
	 */
	public List<SVDS_CollectFile> getCollectFileByName(int pageNum,
			int pageSize, String CollectFileName) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_CollectFile> CollectFiles = dao
				.getCollectFileByName(CollectFileName);
		return CollectFiles;
	}

	/***
	 * 根据Id查询收藏文件
	 * 
	 * @param colId
	 * @return
	 */
	public SVDS_CollectFile getCollectFileById(Integer colId) {
		return dao.getCollectFileById(colId);
	}

	/**
	 * 根据收藏夹ID查询文件
	 * 
	 * @param folderId
	 * @return
	 */
	public List<SVDS_CollectFile> getFolderById(Integer folderId) {
		return dao.getFolderById(folderId);
	}

	/**
	 * 根据收藏夹ID查询文件
	 * 
	 * @param folderId
	 * @return
	 */
	public List<SVDS_CollectFile> listFolderById(int pageNum, int pageSize,String sortOrder, Integer folderId) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_CollectFile> folders = null;
		folders = dao.getFolderById(folderId);
		if (folders.size() != 0) {
			return folders;
		} else {
			return null;
		}
	}

	/**
	 * 收藏文件查询
	 * @param file
	 * @param folderId
	 * @return
	 */
	public List<SVDS_CollectFile> selectCollectSql(SVDS_Files file,
			Integer folderId,SVDS_CollectFile collectFile) {
		return dao.selectCollectSql(file, folderId,collectFile);
	}
	/***
	 * 是否已收藏过
	 * 
	 * @param colId
	 * @return
	 */
	public SVDS_CollectFile getCollectFile(Integer fileId,Integer folderId) {
		return dao.getCollectFile(fileId,folderId);
	}

	/***
	 * 根据批量查询收藏文件
	 * 
	 * @param colId
	 * @return
	 */
	public List<SVDS_CollectFile> getCollectFileByIds(List<Integer> colIds) {
		return dao.getCollectFileByIds(colIds);
	}

	/**
	 * 查询所有收藏文件总数
	 * 
	 * @return
	 */
	public Integer getCollectFileAllCount() {
		return dao.getCollectFileAllCount();
	}

	/**
	 * 添加收藏文件
	 * 
	 * @param SVDS_CollectFile
	 * @return
	 */
	public int insertCollectFile(SVDS_CollectFile collectFile) {
		return dao.insertCollectFile(collectFile);
	}

	/**
	 * 修改收藏文件
	 * 
	 * @param SVDS_CollectFile
	 * @return
	 */
	public Integer updateCollectFile(SVDS_CollectFile collectFile) {
		return dao.updateCollectFile(collectFile);
	}

	/**
	 * 根据Id删除收藏文件
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteCollectFile(List<Integer> ids, Integer folderId) {
		return dao.deleteCollectFile(ids, folderId);
	}

}
