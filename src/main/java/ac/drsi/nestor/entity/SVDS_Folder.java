package ac.drsi.nestor.entity;

import java.util.List;

public class SVDS_Folder {
	private Integer folderId;// 收藏夹Id
	private String folderName;// 收藏夹名称
	private String folderDate;// 创建日期
	private SVDS_User user;// 创建者
	private Integer parentId;// 父级Id
	private String icon;// 图标
	private List<SVDS_Folder> children;// 子级集合

	/**
	 * 获取收藏夹Id
	 * 
	 * @return
	 */
	public Integer getFolderId() {
		return folderId;
	}

	/**
	 * 设置收藏夹Id
	 * 
	 * @param folderId
	 *            收藏夹Id
	 */
	public void setFolderId(Integer folderId) {
		this.folderId = folderId;
	}

	/**
	 * 获取收藏夹名称
	 * 
	 * @return
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * 设置收藏夹名称
	 * 
	 * @param folderName
	 *            收藏夹名称
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	/**
	 * 获取创建日期
	 * 
	 * @return
	 */
	public String getFolderDate() {
		return folderDate;
	}

	/**
	 * 设置创建日期
	 * 
	 * @param folderDate
	 *            创建日期
	 */
	public void setFolderDate(String folderDate) {
		this.folderDate = folderDate;
	}

	/**
	 * 获取创建者
	 * 
	 * @return
	 */
	public SVDS_User getUser() {
		return user;
	}

	/**
	 * 设置创建者
	 * 
	 * @param user
	 *            创建者
	 */
	public void setUser(SVDS_User user) {
		this.user = user;
	}

	/**
	 * 获取父级Id
	 * 
	 * @return
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 设置父级Id
	 * 
	 * @param parentId
	 *            父级Id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取图标
	 * 
	 * @return
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * 设置图标
	 * 
	 * @param icon
	 *            图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取子级
	 * 
	 * @return
	 */
	public List<SVDS_Folder> getChildren() {
		return children;
	}

	/**
	 * 设置子级
	 * 
	 * @param children
	 *            子级
	 */
	public void setChildren(List<SVDS_Folder> children) {
		this.children = children;
	}

}
