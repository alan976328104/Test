package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_AliasFileDao;
import ac.drsi.nestor.entity.SVDS_AliasFile;

@Service
public class SVDS_AliasFileService {
	@Autowired
	SVDS_AliasFileDao dao;

	/**
	 * 根据全部查询标签
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_AliasFile> getAliasFileAll() {
		return dao.getAliasFileAll();
	}

	/**
	 * 根据标签Id查询
	 * 
	 * @param aliasId
	 * @return
	 */
	public List<SVDS_AliasFile> listAliasFileById(Integer aliasId) {
		return dao.listAliasFileById(aliasId);
	}

	/**
	 * 根据标签Id集合查询
	 * 
	 * @param aliasId
	 * @return
	 */
	public List<SVDS_AliasFile> listByAliasIds(List<Integer> aliasId) {
		return dao.listByAliasIds(aliasId);
	}
	/**
	 * 根据标签Id和文件Id查询
	 * 
	 * @param aliasId
	 * @return
	 */
	public List<SVDS_AliasFile> listByFileIdAndAliasId(List<Integer> ids,Integer aliasId,String sortOrder) {
		return dao.listByFileIdAndAliasId(ids,aliasId,sortOrder);
	}

	/**
	 * 根据文件Id查询
	 * 
	 * @param fileId
	 * @return
	 */
	public List<SVDS_AliasFile> listByFileId(Integer fileId) {
		return dao.listByFileId(fileId);
	}

	/**
	 * 判断文件是否添加标签
	 * 
	 * @param aliasId
	 * @param fileId
	 * @return
	 */
	public SVDS_AliasFile existAliasFileById(Integer aliasId, Integer fileId) {
		return dao.existAliasFileById(aliasId, fileId);
	}

	/**
	 * 文件添加标签
	 * 
	 * @param AliasFile
	 * @return
	 */
	public int insertAliasFile(SVDS_AliasFile AliasFile) {
		return dao.insertAliasFile(AliasFile);
	}

	/**
	 * 根据标签Id删除添加过的文件
	 * 
	 * @param ids
	 * @param aliasId
	 * @return
	 */
	public Integer deleteAliasFile(List<Integer> ids, Integer aliasId) {
		return dao.deleteAliasFile(ids, aliasId);
	}
}
