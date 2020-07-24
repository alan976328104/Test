package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.Backups;

@Mapper
public interface BackupsTbDao {
	/**
	 * 查询全部日志备份
	 * @return
	 */
	@Select("select * from svds_backupstb order by id")
	public List<Backups> getAllbackupstb();
	
	/**
	 * 插入日志备份
	 * @param name
	 * @param url
	 * @param backupsdate
	 * @return
	 */
	@Insert({ "insert into svds_backupstb(id,name,url,backupsdate) values(nvl((select max(id) from svds_backupstb),0)+1,#{name},#{url},#{backupsdate})" })
	public Integer insertbackupstb(@Param("name")String name,@Param("url")String url,@Param("backupsdate")String backupsdate);
	/**
	 * 删除日志备份
	 * @param id
	 * @return
	 */
	@Delete({"delete from svds_backupstb where id = #{id}"})
	public Integer delbackupstb(Integer id);
	/**
	 * 查询日志备份名称
	 * @param id
	 * @return
	 */
	@Select("select name from svds_backupstb where id = #{id}")
	/**
	 * 根据id获得备份名称
	 * @param id
	 * @return
	 */
	public String getnameByid(Integer id);
	/**
	 * 查询单个备份
	 * @param id
	 * @return
	 */
	@Select("select * from svds_backupstb where id = #{id}")
	public Backups getBackupstbByid(Integer id);
	/**
	 * 获得备份数量
	 * @param name
	 * @return
	 */
	@Select("select count(0) from svds_backupstb where name =#{name}")
	public Integer countname(String name);
}
