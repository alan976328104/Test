package ac.drsi.nestor.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ac.drsi.nestor.dao.SVDS_LogDao;
import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Log;

@Service
public class SVDS_LogService {
	@Autowired
	SVDS_LogDao dao;

	/**
	 * 查询全部日志
	 * 
	 * @return
	 */
	public List<SVDS_Log> getLogAll() {
		return dao.getLogAll();
	}

	/**
	 * 分页查询全部日志
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Log> getLogAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Log> Logs = dao.getLogAll();
		return Logs;
	}

	/**
	 * 根据日期查询日志
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SVDS_Log> getLogByDate(String startDate, String endDate) {
		return dao.getLogByDate(startDate, endDate);
	}

	/**
	 * 根据日期查询日志分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<SVDS_Log> getLogByDate(int pageNum, int pageSize,
			String startDate, String endDate) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Log> Logs = dao.getLogByDate(startDate, endDate);
		return Logs;
	}

	/**
	 * 组合查询
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Log> listLogByParam(Map<String, Object> param) {
		return dao.listLogByParam(param);
	}

	/**
	 * 组合查询分页
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Log> listLogByParam(int pageNumber, int pageSize,
			Map<String, Object> param) {
		PageHelper.startPage(pageNumber, pageSize);
		List<SVDS_Log> Logs = dao.listLogByParam(param);
		return Logs;
	}
	/**
	 * 审计员组合查询分页
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Log> listLogByUserId(int pageNumber, int pageSize,
			Map<String, Object> param) {
		PageHelper.startPage(pageNumber, pageSize);
		List<SVDS_Log> Logs = dao.selectWhitLogSql(param);
		return Logs;
	}


	/**
	 * 审计员查询
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Log> listLogByUserId(Map<String, Object> param) {
		return dao.selectWhitLogSql(param);
	}

	/**
	 * 安全管理员组合查询分页
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Log> listLogBySafe(int pageNumber, int pageSize,
			Map<String, Object> param) {
		PageHelper.startPage(pageNumber, pageSize);
		List<SVDS_Log> Logs = dao.selectLogSql(param);
		return Logs;
	}

	/**
	 * 安全管理员查询
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Log> listLogBySafe(Map<String, Object> param) {
		return dao.selectLogSql(param);
	}
	
	/**
	 * 普通用户组合查询分页
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Log> listLogByGen(int pageNumber, int pageSize,
			Map<String, Object> param) {
		PageHelper.startPage(pageNumber, pageSize);
		List<SVDS_Log> Logs = dao.selectGenLogSql(param);
		return Logs;
	}

	/**
	 * 普通用户查询
	 * 
	 * @param param
	 * @return
	 */
	public List<SVDS_Log> listLogByGen(Map<String, Object> param) {
		return dao.selectGenLogSql(param);
	}


	/**
	 * 根据用户Id查询记录
	 * 
	 * @param userId
	 * @return
	 */
	public List<SVDS_Log> listByUserId(Integer userId) {
		return dao.listByUserId(userId);
	}

	/**
	 * 根据用户Id查询记录分页
	 * 
	 * @param userId
	 * @return
	 */
	public List<SVDS_Log> listByUserId(int pageNumber, int pageSize,
			Integer userId) {
		PageHelper.startPage(pageNumber, pageSize);
		List<SVDS_Log> Logs = dao.listByUserId(userId);
		return Logs;
	}

	/**
	 * 查询所有日志总数
	 * 
	 * @return
	 */
	public Integer getLogAllCount() {
		return dao.getLogAllCount();
	}

	/**
	 * 添加日志
	 * 
	 * @param SVDS_Log
	 * @return
	 */
	public int insertLog(SVDS_Log log) {
		return dao.insertLog(log);
	}

	/**
	 * 根据Id查询日志
	 * 
	 * @param LogId
	 * @return
	 */
	public SVDS_Log getLogById(Integer logId) {
		return dao.getLogById(logId);
	}

	/**
	 * 修改日志
	 * 
	 * @param SVDS_Log
	 * @return
	 */
	public Integer updateLog(SVDS_Log log) {
		return dao.updateLog(log);
	}

	/**
	 * 根据Id删除日志
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteLog(List<Integer> ids) {
		return dao.deleteLog(ids);
	}

	/**
	 * 根据文件Id删除日志
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteByFileId(List<Integer> ids) {
		return dao.deleteByFileId(ids);
	}
	
	/**
	 * 根据选项卡Id删除数据
	 * @param tabsId
	 * @return
	 */
	public Integer deleteLogByTabsId(@Param("tabsId") Integer tabsId){
		return dao.deleteLogByTabsId(tabsId);
	}

	/**
	 * 总访问量
	 * 
	 * @return
	 */
	public Integer totalView() {
		Integer count = dao.totalView();
		if (count != null) {
			return count;
		} else {
			return 0;
		}
	}

	/**
	 * 日访问量
	 * 
	 * @return
	 */
	public Integer listToDay() {
		Integer count = dao.listToDay();
		if (count != null) {
			return count;
		} else {
			return 0;
		}
	}

	/**
	 * 年访问量
	 * 
	 * @return
	 */
	public List<ChartEntity> yearVisits() {
		List<ChartEntity> entity = dao.yearVisits();
		if (entity != null) {
			return entity;
		} else {
			return null;
		}
	}

	/**
	 * 月访问量
	 * @param yearValue
	 * @return
	 */
	public List<ChartEntity> monthVisits(String yearValue) {
		List<ChartEntity> entity = dao.monthVisits(yearValue);
		if (entity != null) {
			return entity;
		} else {
			return null;
		}
	}
}
