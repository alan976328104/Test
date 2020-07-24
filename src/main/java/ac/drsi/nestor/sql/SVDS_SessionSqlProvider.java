package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Session ;

public class SVDS_SessionSqlProvider {
	/**
	 * 添加session
	 * @param session
	 * @return
	 */
	public String insertSession(final SVDS_Session  session) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_Session ");
				if (session.getsId() == null) {
					VALUES("sId",
							"nvl((select max(sid) from SVDS_Session ),0)+1");
				}
				if (session.getIp() != null) {
					VALUES("ip", "#{ip}");
				}
				if (session.getValue() != null) {
					VALUES("value", "#{value}");
				}
				if (session.getKey()!= null) {
					VALUES("key", "#{key}");
				}
			}
		}.toString();
	}
/**
 * 修改session
 * @param session
 * @return
 */
	public String updateSession(final SVDS_Session  session) {
		return new SQL() {
			{
				UPDATE("SVDS_Session ");
				if (session.getValue() != null) {
					SET("counts = #{counts}");
				}
				if (session.getKey() != null) {
					SET("key = #{key}");
				}
				WHERE("ip = #{ip}");
			}
		}.toString();
	}
}
