package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_PhotoDao;
import ac.drsi.nestor.entity.SVDS_Photo;

@Service
public class SVDS_PhotoService {
	@Autowired
	SVDS_PhotoDao dao;

	public SVDS_Photo getByUserId(Integer userId){
		return dao.getByUserId(userId);
	}
	
	/**
	 * 添加头像
	 * @param Photo
	 * @return
	 */
	public int insertPhoto(SVDS_Photo Photo) {
		return dao.insertPhoto(Photo);
	}

	/**
	 * 修改头像
	 * @param Photo
	 * @return
	 */
	public Integer updatePhoto(SVDS_Photo Photo) {
		return dao.updatePhoto(Photo);
	}

	/**
	 * 根据Id删除头像
	 * @param ids
	 * @return
	 */
	public Integer deletePhoto(List<Integer> ids) {
		return dao.deletePhoto(ids);
	}

}
