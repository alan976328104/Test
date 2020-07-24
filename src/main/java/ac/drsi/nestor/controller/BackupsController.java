package ac.drsi.nestor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.common.DateUtils;
import ac.drsi.common.OralcUtils;
import ac.drsi.nestor.dao.BackupsDao;
import ac.drsi.nestor.dao.ClearOracle;
import ac.drsi.nestor.entity.Backups;
/**
 * 用于系统备份
 * @author CZK
 *
 */
@RestController
public class BackupsController {
	private  String baseurl="F:\\beifen";
	@Autowired
	BackupsDao backupsDao; 
	@Autowired
	ClearOracle clearOracle;
	/**
	 * 全部备份功能
	 * @return
	 */
	@RequestMapping(value = "/getallbackups", method = RequestMethod.GET)
	public List<Backups> getallbackups(){
		return backupsDao.getAllbackups();
	}
	/**
	 * 添加备份
	 * @param username数据库用户名
	 * @param password 数据密码
	 * @param filename 文件名称
	 * @return
	 */
	@RequestMapping(value = "/addBackups", method = RequestMethod.GET)
	public Integer addBackups(String username,String password,String filename){
		try {
			backupsDao.insertbackups(filename, baseurl, DateUtils.getDate());
			OralcUtils.backups(username, password, "ORCL", baseurl, filename);
			
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	
	}
	/**
	 * 删除系统备份
	 * @param id 备份id
	 * @return
	 */
	@RequestMapping(value = "/delbackups", method = RequestMethod.GET)
	public Integer delbackups(Integer id){
		try {
			OralcUtils.delfile(baseurl+"\\"+backupsDao.getnameByid(id));
			backupsDao.delbackups(id);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	
	}
	
	/**
	 * 	还原备份
	 * @param username 用户名
	 * @param password 密码
	 * @param id 备份id
	 * @return
	 */
	@RequestMapping(value = "/recoveryBackups", method = RequestMethod.GET)
	public Integer recoveryBackups(String username,String password,Integer id){
		Backups b = backupsDao.getBackupsByid(id);
		clearOracle();
		OralcUtils.recovery(username, password, "ORCL", b.getUrl()+"\\"+b.getName()+".dmp");
		return 1;
	}
	/**
	 * 获取数据库备份数量
	 * @param name 备份名称
	 * @return
	 */
	@RequestMapping(value = "/backupsnamecount", method = RequestMethod.GET)
	public Integer backupsnamecount(String name){
		return backupsDao.countname(name);
	}
	/**
	 * 删除数据库所有表
	 * @return
	 */
	public Integer clearOracle(){
		clearOracle.svds_users();
		clearOracle.delsvds_alias();
		clearOracle.delsvds_aliasfile();
		clearOracle.delsvds_backups();
		clearOracle.delsvds_backupstb();
		clearOracle.delsvds_button();
		clearOracle.svds_collectfile();
		clearOracle.svds_configure();
		clearOracle.svds_csvtemp();
		clearOracle.svds_dept();
		clearOracle.svds_files();
		clearOracle.svds_folder();
		clearOracle.svds_info();
		clearOracle.svds_infodata();
		clearOracle.svds_log();
//		clearOracle.svds_major();
		clearOracle.svds_menu();
		clearOracle.svds_message();
		clearOracle.svds_phenomenon();
		clearOracle.svds_photo();
		clearOracle.svds_recycle();
		clearOracle.svds_relation();
		clearOracle.svds_role();
		clearOracle.svds_rolebutton();
		clearOracle.svds_rolemenu();
		clearOracle.svds_rule();
		clearOracle.svds_security();
		clearOracle.svds_session();
		clearOracle.svds_tabs();
		clearOracle.svds_userall();
		
		clearOracle.svds_visited();
		return 1;
	}
	
}
