package ac.drsi.nestor.entity;

import java.util.List;

public class SVDS_Menu {
	private Integer id;// 菜单Id
	private String text;// 菜单名称
	private String name;// 菜单名称
	private String icon;// 图标
	private String url;// 路径
	private Integer parentId;// 父级Id
	private String iconCls = "";// AlanCoding 图标 因借用接口
	private Integer lv;// 层级
	private Integer state;// 状态
	private String ename;// 英文名称
	private Integer userid;
	// 子菜单集合
	private List<SVDS_Menu> menus;
	// 子菜单集合
	private List<SVDS_Menu> children;

	/**
	 * 获取菜单Id
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置菜单Id
	 * 
	 * @param id
	 *            菜单Id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取菜单名称
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置菜单名称
	 * 
	 * @param text
	 *            菜单名称
	 */
	public void setText(String text) {
		this.text = text;
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
	 * 获取路径
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置路径
	 * 
	 * @param url
	 *            路径
	 */
	public void setUrl(String url) {
		this.url = url;
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
	 * 获取层级
	 * 
	 * @return
	 */
	public Integer getLv() {
		return lv;
	}

	/**
	 * 设置层级
	 * 
	 * @param lv
	 *            层级
	 */
	public void setLv(Integer lv) {
		this.lv = lv;
	}

	/**
	 * 获取子菜单集合
	 * 
	 * @return
	 */
	public List<SVDS_Menu> getMenus() {
		return menus;
	}

	/**
	 * 设置子菜单集合
	 * 
	 * @param menus
	 *            子菜单集合
	 */
	public void setMenus(List<SVDS_Menu> menus) {
		this.menus = menus;
	}

	/**
	 * 获取菜单名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置菜单名称
	 * 
	 * @param name
	 *            菜单名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取子菜单集合
	 * 
	 * @return
	 */
	public List<SVDS_Menu> getChildren() {
		return children;
	}

	/**
	 * 设置子菜单集合
	 * 
	 * @param children
	 *            子菜单集合
	 */
	public void setChildren(List<SVDS_Menu> children) {
		this.children = children;
	}

	/***
	 * 获取图标样式
	 * 
	 * @return
	 */
	public String getIconCls() {
		return iconCls;
	}

	/**
	 * 设置图标样式
	 * 
	 * @param iconCls
	 */
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	/**
	 * 获取英文名称
	 * 
	 * @return
	 */
	public String getEname() {
		return ename;
	}

	/**
	 * 设置英文名称
	 * 
	 * @param ename
	 *            英文名称
	 */
	public void setEname(String ename) {
		this.ename = ename;
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

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public SVDS_Menu(Integer id, String text, String name, String icon,
			String url, Integer parentId, String iconCls, Integer lv,
			Integer state, String ename, List<SVDS_Menu> menus,
			List<SVDS_Menu> children,Integer userid) {
		super();
		this.id = id;
		this.text = text;
		this.name = name;
		this.icon = icon;
		this.url = url;
		this.parentId = parentId;
		this.iconCls = iconCls;
		this.lv = lv;
		this.state = state;
		this.ename = ename;
		this.menus = menus;
		this.children = children;
		this.userid = userid;
	}

	public SVDS_Menu() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SVDS_Menu [id=" + id + ", text=" + text + ", name=" + name
				+ ", icon=" + icon + ", url=" + url + ", parentId=" + parentId
				+ ", iconCls=" + iconCls + ", lv=" + lv + ", state=" + state
				+ ", ename=" + ename + "]";
	}

}
