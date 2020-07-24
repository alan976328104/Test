package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ac.drsi.nestor.dao.SVDS_VisitedDao;
import ac.drsi.nestor.entity.SVDS_Visited;

@Service
public class SVDS_VisitedService {
	@Autowired
	SVDS_VisitedDao dao;

	/**
	 * 查询全部消息
	 * 
	 * @return
	 */
	public List<SVDS_Visited> getVisitedAll() {
		return dao.getVisitedAll();
	}

	/**
	 * 分页查询全部消息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Visited> getVisitedAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Visited> Visiteds = dao.getVisitedAll();
		return Visiteds;
	}

	/**
	 * 根据用户Id查询点击次数从大到小排序
	 * 
	 * @param userId
	 * @return
	 */
	public List<SVDS_Visited> listCountByUserId(Integer userId) {
		return dao.listCountByUserId(userId);
	}

	/**
	 * 查询所有记录总数
	 * 
	 * @return
	 */
	public Integer getVisitedAllCount() {
		return dao.getVisitedAllCount();
	}

	/***
	 * 根据用户查询最近时间记录
	 * 
	 * @param userId
	 * @return
	 */
	public List<SVDS_Visited> listCount(Integer userId) {
		return dao.listCount(userId);
	}

	/**
	 * 添加消息
	 * 
	 * @param visited
	 * @return
	 * @throws Exception
	 */
	public Integer insertVisited(SVDS_Visited visited) {
		return dao.insertVisited(visited);
	}

	/**
	 * 根据Id查询消息
	 * 
	 * @param vId
	 * @return
	 */
	public SVDS_Visited getVisitedById(Integer vId) {
		return dao.getVisitedById(vId);
	}

	/**
	 * 根据用户Id跟文件Id查询对象
	 * 
	 * @param userId
	 * @param fileId
	 * @return
	 */
	public SVDS_Visited getVisitedByFileId(Integer userId, Integer fileId) {
		return dao.getVisitedByFileId(userId, fileId);
	}

	/**
	 * 修改消息
	 * 
	 * @param visited
	 * @return
	 */
	public Integer updateVisited(SVDS_Visited visited) {
		return dao.updateVisited(visited);
	}

	/**
	 * 根据Id删除消息
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteVisited(List<Integer> ids) {
		return dao.deleteVisited(ids);
	}

}
