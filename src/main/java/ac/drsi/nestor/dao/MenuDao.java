package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.Menu;

@Mapper
public interface MenuDao {

	@Select("select * from svds_menu where text in('物理','热工','燃料')")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "text", property = "text"),
			@Result(column = "url", property = "url"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.MenuDao.findallmenuByid")) })
	public List<Menu> findallmenuforManager();

	@Select("select * from svds_menu where parentid=#{id}")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "text", property = "text"),
			@Result(column = "url", property = "url"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.MenuDao.findallMenuByid1")) })
	public List<Menu> findallmenuByid(Integer id);

	@Select("select * from svds_menu where parentid=#{id}")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "text", property = "text"),
			@Result(column = "url", property = "url"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state") })
	public List<Menu> findallMenuByid1(Integer id);

	@Insert({ "insert into SVDS_menu(id,text,icon,url,parentid,lv,state,userid) values(nvl((select max(id) from SVDS_menu),0)+1,#{text},#{iconCls},#{url},#{parentid},#{lv},0,#{userid})" })
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public Integer insertMenuforManager(Menu menu);

	// 添加权限
	@Insert({ "insert into svds_rolemenu(ROLEID,MENUID) values(#{roleid},#{menuid})" })
	public Integer insertMenuOrRole(@Param("roleid")Integer roleid,@Param("menuid")Integer menuid);

	@Insert({ "insert into svds_rolebutton(ROLEID,BTNID,MENUID) values(#{roleid},#{btnid},#{menuid})" })
	public Integer insertbuttonOrRole(@Param("roleid")Integer roleid,@Param("btnid")Integer btnid,@Param("menuid")Integer menuid);
	@Select("select btnid from svds_button")
	public List<Integer> getallButtonids();
	
	
	
	@Delete({ "delete from SVDS_menu where id=#{id}" })
	public Integer delMenuforManager(Integer id);

	@Update("update SVDS_menu set state = #{state} where id = #{id}")
	public Integer updateMenuforManager(@Param("id")Integer id,@Param("state")Integer state);

	@Delete({ "delete from svds_rolemenu where menuid = #{id}" })
	public Integer delMenuRole(Integer id);
	@Delete({ "delete from svds_rolebutton where menuid = #{id}" })
	public Integer delbtnrole(Integer id);
	@Delete({ "delete from svds_visited where fileid in(select fileid from svds_files where menuid = #{id})" })
	public Integer delsvds_visited(Integer id);

	@Delete({ "delete from svds_collectfile where fileid in(select fileid from svds_files where menuid = #{id})" })
	public Integer delsvds_collectfile(Integer id);

	@Delete({ "delete from svds_log where fileid in(select fileid from svds_files where menuid = #{id})" })
	public Integer delsvds_log(Integer id);

	@Delete({ "delete from svds_info where menuid = #{id}" })
	public int delinfoByMenuid(Integer menuid);

	@Delete({ "delete from svds_files where menuid = #{id}" })
	public int delFileBymenuid(Integer menuid);
	
	@Update("update SVDS_files set state = #{state} where menuid = #{menuid}")
	public int updateFileByMenuid(@Param("menuid")Integer menuid,@Param("state")Integer state);

	@Delete({ "delete from svds_tabs where menuid = #{id}" })
	public int deltabsByMenuid(Integer menuid);

	@Update("update svds_tabs set state = #{state} where menuid = #{menuid}")
	public int updatetabsByMenuid(@Param("menuid")Integer menuid,@Param("state")Integer state);

	@Delete({ "delete from svds_infodata where menuid = #{id}" })
	public int delInfodatabyid(Integer menuid);

	@Delete({ "delete from svds_aliasfile where fileid in (select fileid from svds_files where menuid = #{id})" })
	public int delAliasBymenuid(Integer menuid);
	@Delete({ "delete from svds_recycle where fileid in (select fileid from svds_files where menuid = #{id})" })
	public int delsvdsrecycleByid(Integer menuid);

	@Delete({ "delete from svds_rule where projectid = #{menuid}" })
	public int delruleByMenuid(Integer menuid);

	@Delete({ "delete svds_message where fileid in (select fileid from svds_files where menuid = #{menuid})" })
	public int delMessageBymenuid(Integer menuid);

	@Select("select text from svds_menu where id=#{menuid}")
	public String findMenuByid(Integer menuid);

	@Select("select count(0) from svds_menu where text = #{name}")
	public Integer ishavemenuname(String name);

	@Select("select fileid from svds_files where menuid = #{menuid}")
	public List<String> getFileidBymenuid(Integer menuid);

	@Select("select fileid from svds_files where menuid = #{menuid}")
	public List<Integer> getFileIdBymenuId(Integer menuid);

	@Select("select count(0) from svds_menu where parentid = #{menuid}")
	public int ishavechildren(Integer menuid);

	@Select("select id from svds_menu where parentid =#{menuid}")
	public List<Integer> getChildrenids(Integer menuid);

	@Update("update svds_menu set parentid = #{targetid},lv = #{lv} where id = #{id}")
	public Integer yidongMenud(@Param("id") Integer id,
			@Param("targetid") Integer targetid, @Param("lv") Integer lv);
	
	
	@Select("select id from svds_menu where id in(select menuid from svds_rolemenu where  roleid =( select roleid from svds_users where userid = #{userid} ))  and lv >0")
	public List<Integer> getidByrole(Integer userid);
	
	@Select("select userid from svds_users where username = #{username} and PWD = #{password}")
	public Integer getUserid(@Param("username")String username,@Param("password")String password);

	@Select("select count(0) from svds_rolebutton where roleid=#{roleid} and btnid=3 and menuid=#{menuid}")
	public Integer getquanxian(@Param("roleid")Integer roleid,@Param("menuid")Integer menuid);
}