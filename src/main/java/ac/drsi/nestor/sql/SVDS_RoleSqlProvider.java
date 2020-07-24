package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Role;

public class SVDS_RoleSqlProvider {
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public String updateRole(final SVDS_Role role) {
		return new SQL() {
			{
				UPDATE("SVDS_Role");
				if (role.getRoleName() != null) {
					SET("roleName = #{roleName}");
				}
				if (role.getDescribe() != null) {
					SET("describe = #{describe}");
				}
				WHERE("roleId = #{roleId}");
			}
		}.toString();
	}
	/**
	 * 根据用户Id修改角色
	 * @param role
	 * @return
	 */
	public String updateRoleByUserId(final SVDS_Role role,Integer userId) {
		return new SQL() {
			{
				UPDATE("SVDS_Role");
				if (role.getRoleName() != null) {
					SET("roleName = #{roleName}");
				}
				if (role.getDescribe() != null) {
					SET("describe = #{describe}");
				}
				WHERE("roleId = #{roleId} and userId=#{userId}");
			}
		}.toString();
	}
	/**
	 * 添加角色
	 * @param role
	 * @return
	 */
	public String insertRole(final SVDS_Role role) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_Role");
				if (role.getRoleId() == null) {
					VALUES("roleId", "nvl((select max(roleId) from SVDS_Role),0)+1");
				}
				if (role.getRoleName() != null) {
					VALUES("roleName", "#{roleName}");
				}
				if (role.getDescribe() != null) { 
					VALUES("describe", " #{describe}");
				}
				if (role.getUserId() != null) {
					VALUES("userId", "#{userId}");
				}
			}
		}.toString();

	}
}
