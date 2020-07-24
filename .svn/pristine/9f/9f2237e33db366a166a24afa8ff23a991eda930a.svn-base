package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Dept;

public class SVDS_DeptSqlProvider {
	/**
	 * 修改部门
	 * 
	 * @param dept
	 * @return
	 */
	public String updateDept(final SVDS_Dept dept) {
		return new SQL() {
			{
				UPDATE("SVDS_dept");
				if (dept.getDeptName() != null) {
					SET("deptName = #{deptName}");
				}
				WHERE("deptId = #{deptId}");
			}
		}.toString();
	}

	/**
	 * 添加部门
	 * 
	 * @param dept
	 * @return
	 */
	public String insertDept(final SVDS_Dept dept) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_dept");
				if (dept.getDeptId() == null) {
					VALUES("deptId",
							"nvl((select max(deptId) from SVDS_dept),0)+1");
				}
				if (dept.getDeptName() != null) {
					VALUES("deptName", "#{deptName}");
				}
				if (dept.getParentId() != null) {
					VALUES("parentId", "#{parentId}");
				}
				if (dept.getDeptCode() != null) {
					VALUES("deptCode", "#{deptCode}");
				}
			}
		}.toString();

	}
}
