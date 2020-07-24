package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import ac.drsi.nestor.dao.SVDS_DeptDao;
import ac.drsi.nestor.entity.SVDS_Dept;

@Service
public class SVDS_DeptService {
	@Autowired
	SVDS_DeptDao dao;

	/**
	 * 查询全部部门
	 * 
	 * @return
	 */
	public List<SVDS_Dept> getDeptAll() {
		return dao.getDeptAll();
	}

	/**
	 * 分页查询全部部门
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Dept> getDeptAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Dept> Depts = dao.getDeptAll();
		return Depts;
	}

	/**
	 * 根据部门名称进行模糊查询
	 * 
	 * @param Deptname
	 * @return
	 */
	public List<SVDS_Dept> getDeptByName(String deptName) {
		return dao.getDeptByName(deptName);
	}

	/**
	 * 根据部门名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param Deptname
	 * @return
	 */
	public List<SVDS_Dept> getDeptByName(int pageNum, int pageSize, String Deptname) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Dept> Depts = dao.getDeptByName(Deptname);
		return Depts;
	}

	/**
	 * 查询所有部门总数
	 * @return
	 */
	public Integer getDeptAllCount() {
		return dao.getDeptAllCount();
	}

	/**
	 * 添加部门
	 * @param dept
	 * @return
	 */
	public int insertDept(SVDS_Dept dept) {
		return dao.insertDept(dept);
	}

	/**
	 * 根据Id查询部门
	 * @param deptId
	 * @return
	 */
	public SVDS_Dept getDeptById(Integer deptId) {
		return dao.getDeptById(deptId);
	}

	/**
	 * 修改部门
	 * @param dept
	 * @return
	 */
	public Integer updateDept(SVDS_Dept dept) {
		return dao.updateDept(dept);
	}

	/**
	 * 根据Id删除部门
	 * @param ids
	 * @return
	 */
	public Integer deleteDept(List<Integer> ids) {
		return dao.deleteDept(ids);
	}

}
