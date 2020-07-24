package ac.drsi.nestor.entity;

import java.util.List;

public class SVDS_Alias {
	private Integer aliasId;// 标签Id
	private String aliasName;// 标签名称
	private String aliasDate;// 创建标签日期
	private Integer aliasType;// 标签类型（个人标签，系统标签）
	private SVDS_User user;// 标签创建者
	private Integer parentId;// 标签所属父级Id
	private Integer lv;// 标签层级
	private String icon;// 标签图标

	private List<SVDS_Alias> children;// 标签子集合

	/**
	 * 获取标签Id
	 * 
	 * @return
	 */
	public Integer getAliasId() {
		return aliasId;
	}

	/**
	 * 设置标签Id
	 * 
	 * @param aliasId
	 *            标签Id
	 */
	public void setAliasId(Integer aliasId) {
		this.aliasId = aliasId;
	}

	/**
	 * 获取标签名称
	 * 
	 * @return
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * 设置标签名称
	 * 
	 * @param aliasName
	 *            标签名称
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	/**
	 * 获取标签日期
	 * 
	 * @return
	 */
	public String getAliasDate() {
		return aliasDate;
	}

	/**
	 * 设置标签日期
	 * 
	 * @param aliasDate
	 *            标签日期
	 */
	public void setAliasDate(String aliasDate) {
		this.aliasDate = aliasDate;
	}

	/**
	 * 获取标签类型
	 * 
	 * @return
	 */
	public Integer getAliasType() {
		return aliasType;
	}

	/**
	 * 设置标签类型
	 * 
	 * @param aliasType
	 *            标签类型
	 */
	public void setAliasType(Integer aliasType) {
		this.aliasType = aliasType;
	}

	/**
	 * 获取用户对象
	 * 
	 * @return
	 */
	public SVDS_User getUser() {
		return user;
	}

	/**
	 * 设置用户对象
	 * 
	 * @param user
	 *            用户对象
	 */
	public void setUser(SVDS_User user) {
		this.user = user;
	}

	/**
	 * 获取标签父级Id
	 * 
	 * @return
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 设置标签父级Id
	 * 
	 * @param parentId
	 *            父级Id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取标签层级
	 * 
	 * @return
	 */
	public Integer getLv() {
		return lv;
	}

	/**
	 * 设置标签层级
	 * 
	 * @param lv
	 *            标签层级
	 */
	public void setLv(Integer lv) {
		this.lv = lv;
	}

	/**
	 * 获取子级集合
	 * 
	 * @return
	 */
	public List<SVDS_Alias> getChildren() {
		return children;
	}

	/**
	 * 设置子级集合
	 * 
	 * @param children
	 *            标签子级集合
	 */
	public void setChildren(List<SVDS_Alias> children) {
		this.children = children;
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
	 *            标签图标
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
