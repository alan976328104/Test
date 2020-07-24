package ac.drsi.nestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_ConfigureDao;
import ac.drsi.nestor.entity.SVDS_Configure;

@Service
public class SVDS_ConfigureService {
	@Autowired
	SVDS_ConfigureDao dao;

	/**
	 * 查询名称
	 * 
	 * @return
	 */
	public SVDS_Configure getConfigure() {
		return dao.getConfigure();
	}



	/**
	 * 添加名称
	 * @param Configure
	 * @return
	 */
	public int insertConfigure(SVDS_Configure configure) {
		return dao.insertConfigure(configure);
	}



	/**
	 * 修改角色
	 * @param Configure
	 * @return
	 */
	public Integer updateConfigure(SVDS_Configure configure) {
		return dao.updateConfigure(configure);
	}


}
