package ac.drsi.nestor.entity;

import java.util.ArrayList;
import java.util.List;

public class SVDS_Dept {
	private Integer deptId;// 部门Id
	private String deptName;// 部门名称
	private String deptCode;// 部门code
	private String parentId;// 父级Id
	private List<SVDS_Dept> children = new ArrayList<SVDS_Dept>();// 子级集合

	/**
	 * 获取部门Id
	 * 
	 * @return
	 */
	public Integer getDeptId() {
		return deptId;
	}

	/**
	 * 设置部门Id
	 * 
	 * @param deptId
	 *            部门Id
	 */
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	/**
	 * 获取部门名称
	 * 
	 * @return
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * 设置部门名称
	 * 
	 * @param deptName
	 *            部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * 获取部门code
	 * 
	 * @return
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * 设置部门code
	 * 
	 * @param deptCode
	 *            部门code
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 获取父级Id
	 * 
	 * @return
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置部门Id
	 * 
	 * @param parentId
	 *            部门Id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取子级集合
	 * 
	 * @return
	 */
	public List<SVDS_Dept> getChildren() {
		return children;
	}

	/**
	 * 设置子级集合
	 * 
	 * @param children
	 *            子级集合
	 */
	public void setChildren(List<SVDS_Dept> children) {
		this.children = children;
	}

}
