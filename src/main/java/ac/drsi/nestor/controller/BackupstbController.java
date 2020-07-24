package ac.drsi.nestor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.common.DateUtils;
import ac.drsi.common.OralcUtils;
import ac.drsi.nestor.dao.BackupsTbDao;
import ac.drsi.nestor.dao.ClearOracle;
import ac.drsi.nestor.entity.Backups;
/**
 * 用于系统日志备份
 * @author CZK
 *
 */
@RestController
public class BackupstbController {
	private  String baseurl="F:\\rizhibeifen";
	@Autowired
	BackupsTbDao backupstbDao;
	@Autowired
	ClearOracle clearOracle;
	@RequestMapping(value = "/getAllbackupstb", method = RequestMethod.GET)
	public List<Backups> getAllbackupstb(){
		return backupstbDao.getAllbackupstb();
	}
	
	@RequestMapping(value = "/addBackupstb", method = RequestMethod.GET)
	public Integer addBackupstb(String username,String password,String filename){
		try {
			backupstbDao.insertbackupstb(filename, baseurl, DateUtils.getDate());
			OralcUtils.backupsfortablename(username, password, "ORCL", baseurl, "svds_log", filename);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	
	}
	@RequestMapping(value = "/delbackupstb", method = RequestMethod.GET)
	public Integer delbackupstb(Integer id){
		try {
			OralcUtils.delfile(baseurl+"\\"+backupstbDao.getnameByid(id));
			backupstbDao.delbackupstb(id);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	
	}
	@RequestMapping(value = "/recoveryBackupstb", method = RequestMethod.GET)
	public Integer recoveryBackupstb(String username,String password,Integer id){
		Backups b = backupstbDao.getBackupstbByid(id);
		clearOracle();
		OralcUtils.recovery(username, password, "ORCL", b.getUrl()+"\\"+b.getName()+".dmp");
		return 1;
	}
	@RequestMapping(value = "/backupstbnamecount", method = RequestMethod.GET)
	public Integer backupstbnamecount(String name){
		return backupstbDao.countname(name);
	}
	
	public Integer clearOracle(){
		try {
			clearOracle.svds_log();
		} catch (Exception e) {
			System.out.println("删除失败了svds_log表");
		}
		return 1;
	}

}
