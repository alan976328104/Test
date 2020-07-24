package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.Backups;

@Mapper
public interface BackupsDao {
	/**
	 * 查询全部的备份
	 * @return
	 */
	@Select("select * from svds_backups order by id")
	public List<Backups> getAllbackups();
	
	/**
	 * 插入备份
	 * @param name
	 * @param url
	 * @param backupsdate
	 * @return
	 */
	@Insert({ "insert into svds_backups(id,name,url,backupsdate) values(nvl((select max(id) from svds_backups),0)+1,#{name},#{url},#{backupsdate})" })
	public Integer insertbackups(@Param("name")String name,@Param("url")String url,@Param("backupsdate")String backupsdate);
	/**
	 * 删除备份
	 * @param id
	 * @return
	 */
	@Delete({"delete from svds_backups where id = #{id}"})
	public Integer delbackups(Integer id);
	/**
	 * 查询所有备份名称
	 * @param id
	 * @return
	 */
	@Select("select name from svds_backups where id = #{id}")
	public String getnameByid(Integer id);
	/**
	 * 查询单个备份
	 * @param id
	 * @return
	 */
	@Select("select * from svds_backups where id = #{id}")
	public Backups getBackupsByid(Integer id);
	/**
	 * 查询备份数量
	 * @param name
	 * @return
	 */
	@Select("select count(0) from svds_backups where name =#{name}")
	public Integer countname(String name);
	
	
	
	
}
