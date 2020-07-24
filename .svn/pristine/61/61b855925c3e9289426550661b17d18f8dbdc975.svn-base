package ac.drsi.nestor.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ac.drsi.nestor.dao.SVDS_ButtonDao;
import ac.drsi.nestor.entity.SVDS_Button;

@Service
public class SVDS_ButtonService {
	@Autowired
	SVDS_ButtonDao dao;

	/**
	 * 查询全部按钮
	 * 
	 * @return
	 */
	public List<SVDS_Button> getButtonAll() {
		return dao.getButtonAll();
	}

	/**
	 * 根据按钮名称进行模糊查询
	 * 
	 * @param ButtonName
	 * @return
	 */
	public List<SVDS_Button> listButtonByName(String ButtonName) {
		return dao.listButtonByName(ButtonName);
	}

	/**
	 * 根据名称进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param ButtonName
	 * @return
	 */
	public List<SVDS_Button> listButtonByName(int pageNum, int pageSize,
			String ButtonName) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Button> Buttons = dao.listButtonByName(ButtonName);
		return Buttons;
	}

	/**
	 * 查询用户操作权限
	 * 
	 * @param btnIds
	 * @return
	 */
	public List<SVDS_Button> getUserButton(List<Integer> btnIds) {
		List<SVDS_Button> btns = new ArrayList<SVDS_Button>();
		for (int i = 0; i < btnIds.size(); i++) {
			SVDS_Button button = dao.getButtonById(btnIds.get(i));
			btns.add(button);
		}
		if(btns.size()>0){
			return btns;
		}else{
			return null;
		}
	}

	/**
	 * 根据名称查询按钮
	 * 
	 * @param btnName
	 * @return
	 */
	public SVDS_Button getButtonByName(String btnName) {
		return dao.getButtonByName(btnName);
	}

	/**
	 * 查询所有按钮总数
	 * 
	 * @return
	 */
	public Integer getButtonAllCount() {
		return dao.getButtonAllCount();
	}

	/**
	 * 添加按钮
	 * 
	 * @param Button
	 * @return
	 */
	public int insertButton(SVDS_Button Button) {
		return dao.insertButton(Button);
	}

	/**
	 * 修改按钮
	 * 
	 * @param button
	 * @return
	 */
	public Integer updateButton(SVDS_Button button) {
		return dao.updateButton(button);
	}
	/**
	 * 根据菜单Id查询角色id
	 * 
	 * @param menuId
	 * @return
	 */
	public List<SVDS_Button> listByButtonIds( List<Integer> btnIds){
		return dao.listByButtonIds(btnIds);
	}
	/**
	 * 根据Id删除按钮
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteButton(List<Integer> ids) {
		return dao.deleteButton(ids);
	}

}
