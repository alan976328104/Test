package ac.drsi.nestor.entity;

public class SVDS_CollectFile {
	private Integer colId;// 收藏文件Id
	private String colDate;// 收藏文件日期
	private SVDS_Files files;// 文件对象
	private SVDS_Folder folder;// 收藏夹对象

	/**
	 * 获取收藏文件Id
	 * 
	 * @return
	 */
	public Integer getColId() {
		return colId;
	}

	/**
	 * 设置收藏文件Id
	 * 
	 * @param colId
	 *            收藏文件Id
	 */
	public void setColId(Integer colId) {
		this.colId = colId;
	}

	/**
	 * 获取收藏文件日期
	 * 
	 * @return
	 */
	public String getColDate() {
		return colDate;
	}

	/**
	 * 设置收藏文件日期
	 * 
	 * @param colDate
	 *            收藏文件日期
	 */
	public void setColDate(String colDate) {
		this.colDate = colDate;
	}

	/**
	 * 获取文件对象
	 * 
	 * @return
	 */
	public SVDS_Files getFiles() {
		return files;
	}

	/**
	 * 设置文件对象
	 * 
	 * @param files
	 *            文件对象
	 */
	public void setFiles(SVDS_Files files) {
		this.files = files;
	}

	/**
	 * 获取收藏夹对象
	 * 
	 * @return
	 */
	public SVDS_Folder getFolder() {
		return folder;
	}

	/**
	 * 设置收藏夹对象
	 * 
	 * @param folder
	 *            收藏夹对象
	 */
	public void setFolder(SVDS_Folder folder) {
		this.folder = folder;
	}

}
