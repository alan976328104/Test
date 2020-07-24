package ac.drsi.nestor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_MessageDao;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Message;

import com.github.pagehelper.PageHelper;

@Service
public class SVDS_MessageService {
	@Autowired
	SVDS_MessageDao dao;

	/**
	 * 查询全部消息
	 * 
	 * @return
	 */
	public List<SVDS_Message> getMessageAll() {
		return dao.getMessageAll();
	}

	/**
	 * 分页查询全部消息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<SVDS_Message> getMessageAll(int pageNum, int pageSize) {
		// 使用分页插件,核心代码就这一行
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Message> messages = dao.getMessageAll();
		return messages;
	}

	/**
	 * 根据消息姓名进行模糊查询
	 * 
	 * @param Messagename
	 * @return
	 */
	public List<SVDS_Message> getMessageByName(String Messagename) {
		return dao.getMessageByName(Messagename);
	}

	/**
	 * 根据消息姓名进行模糊查询分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param Messagename
	 * @return
	 */
	public List<SVDS_Message> getMessageByName(int pageNum, int pageSize,
			String Messagename) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Message> Messages = dao.getMessageByName(Messagename);
		return Messages;
	}

	/**
	 * 查询所有消息总数
	 * 
	 * @return
	 */
	public Integer getMessageAllCount() {
		return dao.getMessageAllCount();
	}

	/**
	 * 根据接收者Id查找未读消息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_Message> listMessageState(Integer userId) {
		return dao.listMessageState(userId);
	}

	/**
	 * 根据接收者Id查找消息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_Message> listMessageByReceived(Integer userId) {
		return dao.listMessageByReceived(userId);
	}

	/**
	 * 根据接收者Id查找消息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_Message> listMessageByReceived(int pageNum, int pageSize,
			Integer userId) {
		PageHelper.startPage(pageNum, pageSize);
		List<SVDS_Message> messages = dao.listMessageByReceived(userId);
		return messages;
	}

	/**
	 * 查找个人分享消息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_Message> listMessageBySharer(Integer userId) {
		return dao.listMessageBySharer(userId);
	}

	/**
	 * 查找个人分享消息
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_Message> listMessageBySharer(int pageNumer, int pageSize,
			Integer userId) {
		PageHelper.startPage(pageNumer, pageSize);
		List<SVDS_Message> messages = dao.listMessageBySharer(userId);
		return messages;
	}

	/**
	 * 条件查询个人分享
	 * 
	 * @param file
	 * @param userId
	 * @return
	 */
	public List<SVDS_Message> selectMessageSql(SVDS_Files file, Integer userId,SVDS_Message message) {
		return dao.selectMessageSql(file, userId,message);
	}

	/**
	 * 根据用户名查询
	 * 
	 * @param userId
	 * @return
	 */
	public List<SVDS_Message> listMessageByName(Integer userId, String username) {
		return dao.listMessageByName(userId, username);
	}

	/**
	 * 根据用户名查询
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SVDS_Message> listMessageByName(int pageNumer, int pageSize,
			Integer userId, String username) {
		PageHelper.startPage(pageNumer, pageSize);
		List<SVDS_Message> messages = dao.listMessageByName(userId, username);
		return messages;
	}

	/**
	 * 添加消息
	 * 
	 * @param Message
	 * @return
	 * @throws Exception
	 */
	public int insertMessage(SVDS_Message Message) {
		return dao.insertMessage(Message);
	}

	/**
	 * 根据Id查询消息
	 * 
	 * @param MessageId
	 * @return
	 */
	public SVDS_Message getMessageById(Integer MessageId) {
		return dao.getMessageById(MessageId);
	}

	/**
	 * 修改消息
	 * 
	 * @param Message
	 * @return
	 */
	public Integer updateMessage(SVDS_Message Message) {
		return dao.updateMessage(Message);
	}

	/**
	 * 根据Id删除消息
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteMessage(List<Integer> ids) {
		return dao.deleteMessage(ids);
	}

	/**
	 * 根据Id删除消息
	 * 
	 * @param ids
	 * @return
	 */
	public Integer deleteAllMessage(Integer userId) {
		return dao.deleteAllMessage(userId);
	}
}
