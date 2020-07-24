package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_User;

public class SVDS_UserSqlProvider {
	/**
	 * 修改用户
	 * @param user
	 * @return
	 */
	public String updateUser(final SVDS_User user) {
		return new SQL() {
			{
				UPDATE("SVDS_users");
				if (user.getUsername() != null) {
					SET("username = #{username}");
				}
				if (user.getPwd() != null) {
					SET("pwd = #{pwd}");
				}
				if (user.getCardno() != null) {
					SET("cardno = #{cardno}");
				}
				if (user.getSex() != null) {
					SET("sex = #{sex}");
				}
				if (user.getCode() != null) {
					SET("code = #{code}");
				}
				if (user.getKeyno() != null) {
					SET("keyno = #{keyno}");
				}
				if (user.getDeptname() != null) {
					SET("deptname = #{deptname}");
				}
				if (user.getSecretlevel()!= null) {
					SET("secretlevel = #{secretlevel}");
				}
				if (user.getRole() != null) {
					SET("roleId = #{role.roleId}");
				}
				if (user.getId() != null) {
					SET("id = #{id}");
				}
				WHERE("userId = #{userId}");
			}
		}.toString();
	}
/**
 * 添加用户
 * @param user
 * @return
 */
	public String insertUser(final SVDS_User user) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_users");
				if (user.getUserId() == null) {
					VALUES("userId", "nvl((select max(userid) from SVDS_users),0)+1");
				}
				if (user.getId() != null) {
					VALUES("id", "#{id}");
				}
				if (user.getUsername() != null) {
					VALUES("username", "#{username}");
				}
				if (user.getPwd() != null) {
					VALUES("pwd", "#{pwd}");
				}
				if (user.getCardno() != null) {
					VALUES("cardno", "#{cardno}");
				}
				if (user.getSex() != null) {
					VALUES("sex", " #{sex}");
				}
				if (user.getCode() != null) {
					VALUES("code", "#{code}");
				}
				if (user.getKeyno() != null) {
					VALUES("keyno", "#{keyno}");
				}
				if (user.getDeptname() != null) {
					VALUES("deptname", "#{deptname}");
				}
//				if (user.getMajor() != null) {
//					VALUES("majorId", "#{major.majorId}");
//				}
				if (user.getSecretlevel()!= null) {
					VALUES("secretlevel", "#{secretlevel}");
				}
				if (user.getRole() != null) {
					VALUES("roleId", "#{role.roleId}");
				}
			}
		}.toString();

	}
}
