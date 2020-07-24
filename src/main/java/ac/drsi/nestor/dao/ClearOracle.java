package ac.drsi.nestor.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
/**
 * 该类用于清除数据库所有表
 * @author CZK
 *
 */
@Mapper
public interface ClearOracle {
	
	@Delete({"drop table svds_alias"})
	public Integer delsvds_alias();
	@Delete({"drop table svds_aliasfile"})
	public Integer delsvds_aliasfile();
	@Delete({"drop table svds_backups"})
	public Integer delsvds_backups();
	@Delete({"drop table svds_backupstb"})
	public Integer delsvds_backupstb();
	@Delete({"drop table svds_button"})
	public Integer delsvds_button();
	@Delete({"drop table svds_collectfile"})
	public Integer svds_collectfile();
	@Delete({"drop table svds_configure"})
	public Integer svds_configure();
	@Delete({"drop table svds_csvtemp"})
	public Integer svds_csvtemp();
	@Delete({"drop table svds_dept"})
	public Integer svds_dept();
	@Delete({"drop table svds_files"})
	public Integer svds_files();
	@Delete({"drop table svds_folder"})
	public Integer svds_folder();
	@Delete({"drop table svds_info"})
	public Integer svds_info();
	@Delete({"drop table svds_infodata"})
	public Integer svds_infodata();
	@Delete({"drop table svds_log"})
	public Integer svds_log();
//	@Delete({"drop table svds_major"})
//	public Integer svds_major();
	@Delete({"drop table svds_menu"})
	public Integer svds_menu();
	@Delete({"drop table svds_message"})
	public Integer svds_message();
	@Delete({"drop table svds_phenomenon"})
	public Integer svds_phenomenon();
	@Delete({"drop table svds_photo"})
	public Integer svds_photo();
	@Delete({"drop table svds_recycle"})
	public Integer svds_recycle();
	@Delete({"drop table svds_relation"})
	public Integer svds_relation();
	@Delete({"drop table svds_role"})
	public Integer svds_role();
	@Delete({"drop table svds_rolebutton"})
	public Integer svds_rolebutton();
	@Delete({"drop table svds_rolemenu"})
	public Integer svds_rolemenu();
	@Delete({"drop table svds_rule"})
	public Integer svds_rule();
	@Delete({"drop table svds_security"})
	public Integer svds_security();
	@Delete({"drop table svds_session"})
	public Integer svds_session();
	@Delete({"drop table svds_tabs"})
	public Integer svds_tabs();
	@Delete({"drop table svds_userall"})
	public Integer svds_userall();
	@Delete({"drop table svds_users"})
	public Integer svds_users();
	@Delete({"drop table svds_visited"})
	public Integer svds_visited();

	
}
