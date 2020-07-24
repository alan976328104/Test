package ac.drsi.nestor.sql;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Log;

public class SVDS_LogSqlProvider {
/**
 * 添加日志
 * @param log
 * @return
 */
	public String insertLog(final SVDS_Log log) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_log");
				if (log.getLogId() == null) {
					VALUES("logId",
							"nvl((select max(logId) from SVDS_log),0)+1");
				}
				if (log.getOperation() != null) {
					VALUES("operation", "#{operation}");
				}
				if (log.getLogDate() != null) {
					VALUES("logDate", "#{logDate}");
				}
				if (log.getUser() != null) {
					VALUES("userId", "#{user.userId}");
				}
				if (log.getMenu() != null) {
					VALUES("menuId", "#{menu.id}");
				}
				if (log.getFile() != null) {
					VALUES("fileId", "#{file.fileId}");
				}
				if (log.getIpData() != null) {
					VALUES("ipData", "#{ipData}");
				}
				if (log.getResult() != null) {
					VALUES("result", "#{result}");
				}
			}
		}.toString();
	}

	/**
	 * 多条件查询日志
	 * @param param
	 * @return
	 */
	public String selectWhitFilesSql(final Map<String, Object> param) {
		StringBuffer sql = new StringBuffer("select * from SVDS_log  where ");
		if (param.get("operation") != null) {
			sql.append(" operation like '%' || #{operation} || '%' and ");
		}
		if (param.get("startDate") != null && param.get("endDate") != null) {
			sql.append(" logDate between #{startDate} and #{endDate} and ");
		}
		if (param.get("startDate") != null  && param.get("endDate") == null) {
			sql.append(" logDate >=#{startDate} and ");
		}
		if (param.get("endDate") != null  && param.get("startDate") == null) {
			sql.append(" logDate <#{endDate} and ");
		}
		if (param.get("userName") != null) {
			sql.append(" userId in(select userId from svds_users where userId in(select userId from svds_log) and userName like '%' || #{userName} || '%') and ");
		}
		if (param.get("fileName") != null) {
			sql.append(" fileId in(select fileId from svds_files where fileId in(select fileId from svds_log) and fileName like '%' || #{fileName} || '%') and ");
		}
		if (param.get("ids") != null) {
			//sql.append(" and menuId is not null ");
			List<Integer> ids=(List<Integer>) param.get("ids");
			sql.append("(");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(" menuId in("+ids.get(i)+") or ");
			}
			if (sql.toString().endsWith("or ")) {
				sql.delete(sql.lastIndexOf("or "), sql.length());
				sql.append(")");
			}
		}
		if (sql.toString().endsWith("where ")) {
			sql.delete(sql.lastIndexOf("where "), sql.length());
		} else if (sql.toString().endsWith("and ")) {
			sql.delete(sql.lastIndexOf(" and "), sql.length());
		} else if (sql.toString().endsWith("or ")) {
			sql.delete(sql.lastIndexOf("or "), sql.length());
		}
		sql.append(" order by logId desc");
		System.out.println(sql.toString());
		return sql.toString();
	}
	/**
	 * 多条件查询日志（安全审计员）1、admin2、系统管理员3、安全审计员4、安全管理员
	 * @param param
	 * @return
	 */
	public String selectWhitLogSql(final Map<String, Object> param) {
		StringBuffer sql = new StringBuffer("select * from SVDS_log  where userId in(2,4) and ");
		if (param.get("operation") != null) {
			sql.append(" operation like '%' || #{operation} || '%' and ");
		}
		if (param.get("startDate") != null && param.get("endDate") != null) {
			sql.append(" logDate between #{startDate} and #{endDate} and ");
		}
		if (param.get("startDate") != null  && param.get("endDate") == null) {
			sql.append(" logDate >=#{startDate} and ");
		}
		if (param.get("endDate") != null  && param.get("startDate") == null) {
			sql.append(" logDate <#{endDate} and ");
		}
		if (param.get("userName") != null) {
			sql.append(" userId in(select userId from svds_users where userId in(2,4) and userName like '%' || #{userName} || '%') and ");
		}
		if (param.get("fileName") != null) {
			sql.append(" fileId in(select fileId from svds_files where fileId in(select fileId from svds_log) and fileName like '%' || #{fileName} || '%') and ");
		}
		if (param.get("ids") != null) {
			//sql.append(" and menuId is not null ");
			List<Integer> ids=(List<Integer>) param.get("ids");
			sql.append("(");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(" menuId in("+ids.get(i)+") or ");
			}
			if (sql.toString().endsWith("or ")) {
				sql.delete(sql.lastIndexOf("or "), sql.length());
				sql.append(")");
			}
		}
		if (sql.toString().endsWith("where ")) {
			sql.delete(sql.lastIndexOf("where "), sql.length());
		} else if (sql.toString().endsWith("and ")) {
			sql.delete(sql.lastIndexOf(" and "), sql.length());
		} else if (sql.toString().endsWith("or ")) {
			sql.delete(sql.lastIndexOf("or "), sql.length());
		}
		sql.append(" order by logId desc");
		System.out.println(sql.toString());
		return sql.toString();
	}
	/**
	 * 多条件查询日志（安全管理员）
	 * @param param
	 * @return
	 */
	public String selectLogSql(final Map<String, Object> param) {
		StringBuffer sql = new StringBuffer("select * from SVDS_log  where userId not in(1,2,4) and ");
		if (param.get("operation") != null) {
			sql.append(" operation like '%' || #{operation} || '%' and ");
		}
		if (param.get("startDate") != null && param.get("endDate") != null) {
			sql.append(" logDate between #{startDate} and #{endDate} and ");
		}
		if (param.get("startDate") != null  && param.get("endDate") == null) {
			sql.append(" logDate >=#{startDate} and ");
		}
		if (param.get("endDate") != null  && param.get("startDate") == null) {
			sql.append(" logDate <#{endDate} and ");
		}
		if (param.get("userName") != null) {
			sql.append(" userId in(select userId from svds_users where userId not in(1,2,4) and userName like '%' || #{userName} || '%') and ");
		}
		if (param.get("fileName") != null) {
			sql.append(" fileId in(select fileId from svds_files where fileId in(select fileId from svds_log) and fileName like '%' || #{fileName} || '%') and ");
		}
		if (param.get("ids") != null) {
			//sql.append(" and menuId is not null ");
			List<Integer> ids=(List<Integer>) param.get("ids");
			sql.append("(");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(" menuId in("+ids.get(i)+") or ");
			}
			if (sql.toString().endsWith(" or ")) {
				sql.delete(sql.lastIndexOf("or "), sql.length());
				sql.append(")");
			}
		}
		if (sql.toString().endsWith("where ")) {
			sql.delete(sql.lastIndexOf("where "), sql.length());
		} else if (sql.toString().endsWith("and ")) {
			sql.delete(sql.lastIndexOf(" and "), sql.length());
		} else if (sql.toString().endsWith("or ")) {
			sql.delete(sql.lastIndexOf("or "), sql.length());
		}
		sql.append(" order by logId desc");
		//System.out.println(sql.toString());
		return sql.toString();
	}
	
	/**
	 * 多条件查询日志（普通用户）
	 * @param param
	 * @return
	 */
	public String selectGenLogSql(final Map<String, Object> param) {
		StringBuffer sql = new StringBuffer("select * from SVDS_log  where userId not in(1,2,3,4) and ");
		if (param.get("operation") != null) {
			sql.append(" operation like '%' || #{operation} || '%' and ");
		}
		if (param.get("startDate") != null && param.get("endDate") != null) {
			sql.append(" logDate between #{startDate} and #{endDate} and ");
		}
		if (param.get("startDate") != null  && param.get("endDate") == null) {
			sql.append(" logDate >=#{startDate} and ");
		}
		if (param.get("endDate") != null  && param.get("startDate") == null) {
			sql.append(" logDate <#{endDate} and ");
		}
		if (param.get("userName") != null) {
			sql.append(" userId in(select userId from svds_users where userId not in(1,2,3,4) and userName like '%' || #{userName} || '%') and ");
		}
		if (param.get("fileName") != null) {
			sql.append(" fileId in(select fileId from svds_files where fileId in(select fileId from svds_log) and fileName like '%' || #{fileName} || '%') and ");
		}
		if (param.get("ids") != null) {
			//sql.append(" and menuId is not null ");
			List<Integer> ids=(List<Integer>) param.get("ids");
			sql.append("(");
			for (int i = 0; i < ids.size(); i++) {
				sql.append(" menuId in("+ids.get(i)+") or ");
			}
			if (sql.toString().endsWith(" or ")) {
				sql.delete(sql.lastIndexOf("or "), sql.length());
				sql.append(")");
			}
		}
		if (sql.toString().endsWith("where ")) {
			sql.delete(sql.lastIndexOf("where "), sql.length());
		} else if (sql.toString().endsWith("and ")) {
			sql.delete(sql.lastIndexOf(" and "), sql.length());
		} else if (sql.toString().endsWith("or ")) {
			sql.delete(sql.lastIndexOf("or "), sql.length());
		}
		sql.append(" order by logId desc");
		System.out.println(sql.toString());
		return sql.toString();
	}
}
