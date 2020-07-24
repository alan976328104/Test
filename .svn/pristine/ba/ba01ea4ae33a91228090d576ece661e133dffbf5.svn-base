package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import ac.drsi.nestor.dao.SVDS_MajorDao;
import ac.drsi.nestor.entity.SVDS_Major;

@Service
public class SVDS_MajorService {
	@Autowired
	SVDS_MajorDao dao;

	/**
	 * 查询全部专业
	 * 
	 * @return
	 */
	public List<SVDS_Major> getMajorAll() {
		return dao.getMajorAll();
	}

	/**
	 * 分页查询全部专业
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Major> getMajorAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Major> majors = dao.getMajorAll();
		return majors;
	}

	/**
	 * 根据专业名称进行模糊查询
	 * 
	 * @param Majorname
	 * @return
	 */
	public List<SVDS_Major> getMajorByName(String majorname) {
		return dao.getMajorByName(majorname);
	}

	/**
	 * 根据专业名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param Majorname
	 * @return
	 */
	public List<SVDS_Major> getMajorByName(int pageNum, int pageSize, String majorname) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Major> majors = dao.getMajorByName(majorname);
		return majors;
	}

	/**
	 * 查询所有专业总数
	 * @return
	 */
	public Integer getMajorAllCount() {
		return dao.getMajorAllCount();
	}

	/**
	 * 添加专业
	 * @param SVDS_Major
	 * @return
	 */
	public int insertMajor(SVDS_Major major) {
		return dao.insertMajor(major);
	}

	/**
	 * 根据Id查询专业
	 * @param MajorId
	 * @return
	 */
	public SVDS_Major getMajorById(Integer majorId) {
		return dao.getMajorById(majorId);
	}

	/**
	 * 修改专业
	 * @param SVDS_Major
	 * @return
	 */
	public Integer updateMajor(SVDS_Major major) {
		return dao.updateMajor(major);
	}

	/**
	 * 根据Id删除专业
	 * @param ids
	 * @return
	 */
	public Integer deleteMajor(List<Integer> ids) {
		return dao.deleteMajor(ids);
	}

}
