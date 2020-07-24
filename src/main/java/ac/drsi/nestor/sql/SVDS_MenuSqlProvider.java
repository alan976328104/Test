package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Menu;

public class SVDS_MenuSqlProvider {
	
	/**
	 * 修改菜单
	 * @param menu
	 * @return
	 */
	public String updateMenu(final SVDS_Menu menu) {
		return new SQL() {
			{
				UPDATE("SVDS_Menu");
				if (menu.getText() != null) {
					SET("text = #{text}");
				}
				if(menu.getEname()!=null){
					SET("ename=#{ename}");
				}
				WHERE("id = #{id}");
			}
		}.toString();
	}
}
