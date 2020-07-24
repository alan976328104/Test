package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Alias;

public class SVDS_AliasSqlProvider {
	/**
	 * 添加标签
	 * 
	 * @param alias
	 * @return
	 */
	public String insertAlias(final SVDS_Alias alias) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_alias");
				if (alias.getAliasId() == null) {
					VALUES("aliasId",
							"nvl((select max(aliasId) from SVDS_alias),0)+1");
				}
				if (alias.getAliasName() != null) {
					VALUES("aliasName", "#{aliasName}");
				}
				if (alias.getAliasDate() != null) {
					VALUES("aliasDate", "#{aliasDate}");
				}
				if (alias.getAliasType() != null) {
					VALUES("aliasType", "#{aliasType}");
				}
				if (alias.getParentId() != null) {
					VALUES("parentId", "#{parentId}");
				}
				if (alias.getLv() != null) {
					VALUES("lv", "#{lv}");
				}
				if (alias.getIcon() != null) {
					VALUES("icon", "#{icon}");
				}
				if (alias.getUser() != null) {
					VALUES("userId", "#{user.userId}");
				}
			}
		}.toString();
	}

	/**
	 * 更新标签
	 * 
	 * @param alias
	 * @return
	 */
	public String updateAlias(final SVDS_Alias alias) {
		return new SQL() {
			{
				UPDATE("SVDS_alias");
				if (alias.getAliasName() != null) {
					SET("aliasName = #{aliasName}");
				}
				if (alias.getAliasDate() != null) {
					SET("aliasDate = #{aliasDate}");
				}
				if (alias.getAliasType() != null) {
					SET("aliasType = #{aliasType}");
				}
				if (alias.getParentId() != null) {
					SET("parentId = #{parentId}");
				}
				if (alias.getLv() != null) {
					SET("lv = #{lv}");
				}
				if (alias.getIcon() != null) {
					SET("icon = #{icon}");
				}
				if (alias.getUser() != null) {
					SET("userId = #{user.userId}");
				}
				WHERE("aliasId = #{aliasId}");
			}
		}.toString();
	}
}
