package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.Tabs;

public class SVDS_TabsSqlProvider {
	/**
	 * 添加选项卡
	 * @param tabs
	 * @return
	 */
	public String insertTabs(final Tabs tabs) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_Tabs");
				if (tabs.getId()==null) {
					VALUES("id","nvl((select max(id) from SVDS_Tabs),0)+1");
				}
				if (tabs.getName() != null) {
					VALUES("name", "#{name}");
				}
				if (tabs.getLv() != null) {
					VALUES("lv", "#{lv}");
				}
				if (tabs.getMenuid() != null) {
					VALUES("menuid", "#{menuid}");
				}
				if (tabs.getParentid() != null) {
					VALUES("parentid", "#{parentid}");
				}
				if (tabs.getState() != null) {
					VALUES("state", "#{state}");
				}
			}
		}.toString();
	}
}
