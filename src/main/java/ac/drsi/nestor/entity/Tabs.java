package ac.drsi.nestor.entity;

import java.util.ArrayList;
import java.util.List;

public class Tabs {
	private Integer id;// 选项卡Id
	private String name;// 选项卡名称
	private List<Tabs> children = new ArrayList<>();// 子级集合
	private Integer lv;// 选项卡层级
	private Integer menuid;// 菜单Id
	private Integer parentid;// 父级Id
	private Integer state = 0;// 状态
	private Integer sortOrder = 0;// 排序

	/**
	 * 获取选项卡对象构造方法
	 * 
	 * @param name
	 *            选项卡名称
	 * @param lv
	 *            选项卡层级
	 * @param menuid
	 *            菜单Id
	 * @param parentid
	 *            父级Id
	 */
	public Tabs(String name, Integer lv, Integer menuid, Integer parentid) {
		super();
		this.name = name;
		this.lv = lv;
		this.menuid = menuid;
		this.parentid = parentid;
	}

	/**
	 * 获取选项卡对象构造方法（无参）
	 */
	public Tabs() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 获取选项卡对象不同参数构造方法
	 * 
	 * @param id
	 *            选项卡Id
	 * @param name
	 *            选项卡名称
	 * @param children
	 *            子级集合
	 * @param lv
	 *            选项卡层级
	 * @param menuid
	 *            菜单Id
	 * @param parentid
	 *            父级Id
	 */
	public Tabs(Integer id, String name, List<Tabs> children, Integer lv,
			Integer menuid, Integer parentid) {
		super();
		this.id = id;
		this.name = name;
		this.children = children;
		this.lv = lv;
		this.menuid = menuid;
		this.parentid = parentid;
	}

	/**
	 * 获取选项卡Id
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置选项卡Id
	 * 
	 * @param id
	 *            选项卡Id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获取选项卡名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置选项卡名称
	 * 
	 * @param name
	 *            选项卡名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取子级集合
	 * 
	 * @return
	 */
	public List<Tabs> getChildren() {
		return children;
	}

	/**
	 * 设置子级集合
	 * 
	 * @param children
	 *            子级集合
	 */
	public void setChildren(List<Tabs> children) {
		this.children = children;
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
	 * 获取菜单Id
	 * 
	 * @return
	 */
	public Integer getMenuid() {
		return menuid;
	}

	/**
	 * 设置菜单Id
	 * 
	 * @param menuid
	 *            菜单Id
	 */
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	/**
	 * 获取父级Id
	 * 
	 * @return
	 */
	public Integer getParentid() {
		return parentid;
	}

	/**
	 * 设置父级Id
	 * 
	 * @param parentid
	 *            父级Id
	 */
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
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

	/**
	 * 获取排序
	 * 
	 * @return
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * 设置排序
	 * 
	 * @param sortOrder
	 *            排序
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	@Override
	public String toString() {
		return "Tabs [id=" + id + ", name=" + name + ", children=" + children
				+ ", lv=" + lv + ", menuid=" + menuid + ", parentid="
				+ parentid + ", state=" + state + ", sortOrder=" + sortOrder
				+ "]";
	}

}
