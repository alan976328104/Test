package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import ac.drsi.nestor.dao.SVDS_SecurityDao;
import ac.drsi.nestor.entity.SVDS_Security;

@Service
public class SVDS_SecurityService {
	@Autowired
	SVDS_SecurityDao dao;

	/**
	 * 查询全部密级
	 * 
	 * @return
	 */
	public List<SVDS_Security> getSecurityAll() {
		return dao.getSecurityAll();
	}

	/**
	 * 分页查询全部密级
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Security> getSecurityAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Security> Securitys = dao.getSecurityAll();
		return Securitys;
	}

	/**
	 * 根据密级名称进行模糊查询
	 * 
	 * @param Securityname
	 * @return
	 */
	public List<SVDS_Security> listSecurityByName(String securityName) {
		return dao.listSecurityByName(securityName);
	}

	/**
	 * 根据密级名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param Securityname
	 * @return
	 */
	public List<SVDS_Security> listSecurityByName(int pageNum, int pageSize, String securityName) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Security> Securitys = dao.listSecurityByName(securityName);
		return Securitys;
	}

	/**
	 * 查询所有密级总数
	 * @return
	 */
	public Integer getSecurityAllCount() {
		return dao.getSecurityAllCount();
	}

	/**
	 * 添加密级
	 * @param security
	 * @return
	 */
	public int insertSecurity(SVDS_Security security) {
		return dao.insertSecurity(security);
	}

	/**
	 * 根据Id查询密级
	 * @param SecurityId
	 * @return
	 */
	public SVDS_Security getSecurityById(Integer securityId) {
		return dao.getSecurityById(securityId);
	}
	
	/**
	 * 根据名称查询密级
	 * @param SecurityId
	 * @return
	 */
	public SVDS_Security getSecurityByName(String securityByName) {
		return dao.getSecurityByName(securityByName);
	}

	/**
	 * 修改密级
	 * @param SVDS_Security
	 * @return
	 */
	public Integer updateSecurity(SVDS_Security security) {
		return dao.updateSecurity(security);
	}

	/**
	 * 根据Id删除密级
	 * @param ids
	 * @return
	 */
	public Integer deleteSecurity(List<Integer> ids) {
		return dao.deleteSecurity(ids);
	}

}
