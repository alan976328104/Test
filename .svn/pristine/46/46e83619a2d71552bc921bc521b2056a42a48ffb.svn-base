package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SVDS_UserAll;

public class SVDS_UserAllSqlProvider {
/**
 * 添加所有用户
 * @param user
 * @return
 */
	public String insertUserAll(final SVDS_UserAll user) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_userAll");
				if (user.getUserAllId() == null) {
					VALUES("userAllId", "nvl((select max(userAllid) from SVDS_userAll),0)+1");
				}
				if (user.getId() != null) {
					VALUES("id", "#{id}");
				}
				if (user.getUsername() != null) {
					VALUES("username", "#{username}");
				}
				if (user.getCardno() != null) {
					VALUES("cardno", "#{cardno}");
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
				if (user.getSecretlevel()!= null) {
					VALUES("secretlevel", "#{secretlevel}");
				}
			}
		}.toString();

	}
}
