package ac.drsi.nestor.entity;

public class SVDS_Message {
	private Integer messageId;// 消息Id
	private String messageDate;// 消息日期
	private SVDS_Files files;// 文件
	private SVDS_User received;// 接受者
	private SVDS_User sharer;// 分享者
	private Integer state;// 状态（是否查看）

	/**
	 * 获取消息Id
	 * 
	 * @return
	 */
	public Integer getMessageId() {
		return messageId;
	}

	/**
	 * 设置消息Id
	 * 
	 * @param messageId
	 *            消息Id
	 */
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	/**
	 * 消息日期
	 * 
	 * @return
	 */
	public String getMessageDate() {
		return messageDate;
	}

	/**
	 * 设置消息日期
	 * 
	 * @param messageDate
	 *            消息日期
	 */
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}

	/**
	 * 获取文件
	 * 
	 * @return
	 */
	public SVDS_Files getFiles() {
		return files;
	}

	/**
	 * 设置文件
	 * 
	 * @param files
	 *            文件对象
	 */
	public void setFiles(SVDS_Files files) {
		this.files = files;
	}

	/**
	 * 获取接收者
	 * 
	 * @return
	 */
	public SVDS_User getReceived() {
		return received;
	}

	/**
	 * 设置接收者
	 * 
	 * @param received
	 *            接收者
	 */
	public void setReceived(SVDS_User received) {
		this.received = received;
	}

	/**
	 * 获取分享着
	 * 
	 * @return
	 */
	public SVDS_User getSharer() {
		return sharer;
	}

	/**
	 * 设置分享者
	 * 
	 * @param sharer
	 *            分享者
	 */
	public void setSharer(SVDS_User sharer) {
		this.sharer = sharer;
	}

	/**
	 * 获取状态
	 * 
	 * @return
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * 设置状态
	 * 
	 * @param state
	 *            状态
	 */
	public void setState(Integer state) {
		this.state = state;
	}

}
