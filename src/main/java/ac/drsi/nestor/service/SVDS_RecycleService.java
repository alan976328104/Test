package ac.drsi.nestor.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.common.SolrUtils;
import ac.drsi.nestor.dao.SVDS_AliasFileDao;
import ac.drsi.nestor.dao.SVDS_CollectFileDao;
import ac.drsi.nestor.dao.SVDS_FilesDao;
import ac.drsi.nestor.dao.SVDS_LogDao;
import ac.drsi.nestor.dao.SVDS_MessageDao;
import ac.drsi.nestor.dao.SVDS_RecycleDao;
import ac.drsi.nestor.dao.SVDS_VisitedDao;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Recycle;

import com.github.pagehelper.PageHelper;

@Service
public class SVDS_RecycleService {
	@Autowired
	SVDS_RecycleDao dao;
	@Autowired
	SVDS_FilesDao filesDao;
	@Autowired
	SVDS_LogDao logDao;
	@Autowired
	SVDS_CollectFileDao collectFileDao;
	@Autowired
	SVDS_AliasFileDao aliasFileDao;
	@Autowired
	SVDS_VisitedDao visitedDao;
	@Autowired
	SVDS_MessageDao messageDao;

	/**
	 * 查询全部回收站
	 * 
	 * @return
	 */
	public List<SVDS_Recycle> getRecycleAll() {
		return dao.getRecycleAll();
	}

	/**
	 * 分页查询全部
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Recycle> getRecycleAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Recycle> recycles = dao.getRecycleAll();
		return recycles;
	}
/**
 * 根据菜单Id和文件名称查询
 * @param menuId
 * @param fileName
 * @return
 */
	public List<SVDS_Recycle> listRecycleByMenuId(Integer menuId,
			String fileName) {
		return dao.listRecycleByMenuId(menuId, fileName);
	}
	/**
	 * 根据菜单Id和文件名称分页查询
	 * @param menuId
	 * @param fileName
	 * @return
	 */
	public List<SVDS_Recycle> listRecycleByMenuId(int pageNum, int pageSize,
			Integer menuId, String fileName) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Recycle> recycles = dao.listRecycleByMenuId(menuId, fileName);
		return recycles;
	}
	/**
	 * 根据选项卡Id和文件名称查询
	 * @param menuId
	 * @param fileName
	 * @return
	 */
	public List<SVDS_Recycle> listRecycleByTabsId(Integer tabsId,
			String fileName) {
		return dao.listRecycleByTabsId(tabsId, fileName);
	}
	/**
	 * 根据选项卡Id和文件名称分页查询
	 * @param menuId
	 * @param fileName
	 * @return
	 */
	public List<SVDS_Recycle> listRecycleByTabsId(int pageNum, int pageSize,
			Integer tabsId, String fileName) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Recycle> recycles = dao.listRecycleByTabsId(tabsId, fileName);
		return recycles;
	}

	/**
	 * 根据回收站名称进行模糊查询
	 * 
	 * @param RecycleName
	 * @return
	 */
	public List<SVDS_Recycle> getRecycleByName(String RecycleName) {
		return dao.getRecycleByName(RecycleName);
	}

	/**
	 * 根据名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param RecycleName
	 * @return
	 */
	public List<SVDS_Recycle> getRecycleByName(int pageNum, int pageSize,
			String RecycleName) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Recycle> Recycles = dao.getRecycleByName(RecycleName);
		return Recycles;
	}

	/***
	 * 根据Id查询回收站
	 * 
	 * @param colId
	 * @return
	 */
	public SVDS_Recycle getRecycleById(Integer colId) {
		return dao.getRecycleById(colId);
	}

	/**
	 * 根据收藏夹ID查询文件
	 * 
	 * @param folderId
	 * @return
	 */
	public List<SVDS_Recycle> getFolderById(Integer folderId) {
		return dao.getFolderById(folderId);
	}

	/**
	 * 根据收藏夹ID查询文件
	 * 
	 * @param folderId
	 * @return
	 */
	public List<SVDS_Recycle> getFolderById(int pageNum, int pageSize,
			Integer folderId) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Recycle> cons = dao.getFolderById(folderId);
		return cons;
	}

	/***
	 * 是否已添加过
	 * 
	 * @param colId
	 * @return
	 */
	public SVDS_Recycle getRecycle(SVDS_Files files) {
		return dao.getRecycle(files);
	}

	/***
	 * 根据批量查询回收站
	 * 
	 * @param colId
	 * @return
	 */
	public List<SVDS_Recycle> getRecycleByIds(List<Integer> colIds) {
		return dao.getRecycleByIds(colIds);
	}

	/**
	 * 查询所有回收站总数
	 * 
	 * @return
	 */
	public Integer getRecycleAllCount() {
		return dao.getRecycleAllCount();
	}

	/**
	 * 添加回收站
	 * 
	 * @param SVDS_Recycle
	 * @return
	 */
	public int insertRecycle(SVDS_Recycle recycle) {
		return dao.insertRecycle(recycle);
	}

	/**
	 * 修改回收站
	 * 
	 * @param SVDS_Recycle
	 * @return
	 */
	public Integer updateRecycle(SVDS_Recycle recycle) {
		return dao.updateRecycle(recycle);
	}

	/**
	 * 根据Id删除回收站
	 * 
	 * @param ids
	 * @return
	 */
	public List<SVDS_Files> deleteRecycle(List<Integer> ids) {
		List<Integer> fileIds = new ArrayList<Integer>();
		for (int i = 0; i < ids.size(); i++) {
			SVDS_Recycle recycle = dao.getRecycleById(ids.get(i));
			if(recycle!=null){
				SVDS_Files files = recycle.getFiles();
				fileIds.add(files.getFileId());
			}
			
		}
		System.out.println("删除的数量"+ids.size());
		Integer isSuccess = filesDao.recycleFiles(fileIds, 0);
		Integer recSuccess = dao.deleteRecycle(ids);
		List<Integer> fileId=new ArrayList<Integer>();
		if (isSuccess > 0 && recSuccess > 0) {
			System.out.println("lllll");
			List<SVDS_Files> files = filesDao.getFilesByIds(fileIds);
			List<SVDS_Files> fileList=new ArrayList<SVDS_Files>();
			for (SVDS_Files svds_Files : files) {
				String fileName=svds_Files.getFileName();
				Integer tabId=svds_Files.getTabsId(); 
				if(svds_Files.getpId()!=null&&svds_Files.getpId()==0){
					fileList=filesDao.getFilesByNameVersion(fileName,tabId,svds_Files.getFileVersion());
					if(fileList.size()>1){
						for(int i=0;i<fileList.size();i++){
						//	if(fileList.get(i).getpId()==0){
							if(fileList.get(0).getFileVersion().equals(svds_Files.getFileVersion())){
								System.out.println("删除的Id："+fileList.get(i).getFileId());
								filesDao.updateFilesByPId(fileList.get(i).getFileId(), svds_Files.getFileId());
								fileId.add(fileList.get(0).getFileId());
								filesDao.deleteFiles(fileId);
								//continue;
							}else{
								System.out.println("哈哈哈哈");
								fileList.get(i).setpId(svds_Files.getFileId());
								filesDao.updateFiles(fileList.get(i));
							}
						    //}
						}
						//continue;
					}
					fileList=filesDao.getFilesByName(fileName,tabId);
					System.out.println("wenjianshuliang"+fileList.size());
					if(fileList.size()>0){
						for(int i=1;i<fileList.size();i++){
							if(fileList.get(i).getpId()!=null&&fileList.get(i).getpId()==0){
								System.out.println(123456789);
								System.out.println(fileList.get(i).getFileId());
								System.out.println(svds_Files.getFileId());
								filesDao.updateFilesByPId(fileList.get(i).getFileId(), svds_Files.getFileId());
								fileList.get(i).setpId(svds_Files.getFileId());
								filesDao.updateFiles(fileList.get(i));
								System.out.println(fileList.get(i).getFileVersion());
								System.out.println(svds_Files.getFileVersion());
							}
						}
					}
				}else{
					fileList=filesDao.getFilesByName(fileName,tabId);
					System.out.println("wenjianshuliang:"+fileList.size());
					System.out.println(svds_Files.getpId());
					System.out.println(fileList.get(0).getFileId());
					if(svds_Files.getpId()!=null&&fileList.get(0).getFileId()!=null){
						System.out.println(svds_Files.getpId().equals(fileList.get(0).getFileId()));
						if(svds_Files.getpId().equals(fileList.get(0).getFileId())){
							for(Integer i=1;i<fileList.size();i++){
								fileList.get(i).setpId(fileList.get(0).getFileId());
								filesDao.updateFiles(fileList.get(i));
							}
						}else if(svds_Files.getpId()!=null&&svds_Files.getpId()!=0){
							for(Integer i=1;i<fileList.size();i++){
								fileList.get(i).setpId(fileList.get(0).getFileId());
								filesDao.updateFiles(fileList.get(i));
							}
						}
					}
				}
			}
				
			return files;
		} else {
			return null;
		}
	}
	
	public Integer isExistVersion( JSONArray array){
		Integer isExist=0;
		for (Object object : array) {
			JSONObject jsonObject = JSONObject.fromObject(object);
			String fileName=jsonObject.get("fileName").toString();
			String tabsId=jsonObject.get("tabsId").toString();
			String fileVersion=jsonObject.get("fileVersion").toString();
			List<SVDS_Files> files=filesDao.getFilesByNameVersion(fileName, Integer.parseInt(tabsId), fileVersion);
			if(files!=null&&files.size()>0){
				isExist++;
			}
		}
		return isExist;
	}

	/**
	 * 根据Id删除回收站彻底删除文件
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteRecycleFile(List<Integer> ids) {
		List<Integer> fileIds = new ArrayList<Integer>();
		for (int i = 0; i < ids.size(); i++) {
			SVDS_Recycle recycle = dao.getRecycleById(ids.get(i));
			SVDS_Files files = recycle.getFiles();
			fileIds.add(files.getFileId());
			SolrUtils.removeById(files.getFileId() + "");
		}
		System.out.println(fileIds);
		filesDao.deleteFiles(fileIds);
		aliasFileDao.deleteByFileId(fileIds);
		collectFileDao.deleteByFileId(fileIds);
		visitedDao.deleteByFileId(fileIds);
		logDao.deleteByFileId(fileIds);
		messageDao.deleteByFileId(fileIds);
		Integer recSuccess = dao.deleteRecycle(ids);
		if (recSuccess > 0) {
			return recSuccess;
		} else {
			return null;
		}
	}
}
