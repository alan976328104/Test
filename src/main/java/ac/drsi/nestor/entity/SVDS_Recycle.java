package ac.drsi.nestor.entity;

public class SVDS_Recycle {

	private Integer recId;// 回收站Id
	private String recDate;// 删除日期
	private SVDS_Files files;// 删除文件
	private SVDS_User user;// 操作用户

	/**
	 * 获取回收站Id
	 * 
	 * @return
	 */
	public Integer getRecId() {
		return recId;
	}

	/**
	 * 设置回收站Id
	 * 
	 * @param recId
	 */
	public void setRecId(Integer recId) {
		this.recId = recId;
	}

	/**
	 * 获取删除日期
	 * 
	 * @return
	 */
	public String getRecDate() {
		return recDate;
	}

	/**
	 * 设置删除日期
	 * 
	 * @param recDate
	 *            删除日期
	 */
	public void setRecDate(String recDate) {
		this.recDate = recDate;
	}

	/**
	 * 获取删除文件
	 * 
	 * @return
	 */
	public SVDS_Files getFiles() {
		return files;
	}

	/**
	 * 设置删除文件
	 * 
	 * @param files
	 *            删除文件
	 */
	public void setFiles(SVDS_Files files) {
		this.files = files;
	}

	/**
	 * 获取操作用户
	 * 
	 * @return
	 */
	public SVDS_User getUser() {
		return user;
	}

	/**
	 * 设置操作用户
	 * 
	 * @param user
	 *            操作用户
	 */
	public void setUser(SVDS_User user) {
		this.user = user;
	}
}
