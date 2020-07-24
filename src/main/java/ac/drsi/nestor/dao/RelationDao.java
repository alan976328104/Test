package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.Phenomenon;
import ac.drsi.nestor.entity.Rule;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Major;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_Relation;
import ac.drsi.nestor.entity.SVDS_Rule;
import ac.drsi.nestor.entity.SVDS_Security;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.Tabs;

@Mapper
public interface RelationDao {
	
	
	@Select("select * from svds_relation   where id in(select relationid from svds_rule where projectid in (select menuid from svds_rolemenu where roleid =(select roleid from svds_users where userid = #{id})))")
	public List<SVDS_Relation> findallrelation(Integer id);
	
	@Select("select count(*) from svds_relation where name = #{name}")
	public Integer ishavaname(String name);
	
	@Insert({ "insert into svds_relation(id,name,project,menuname) values(nvl((select max(id) from svds_relation),0)+1,#{name},#{menuid},#{menuname})" })
	public Integer addRelation(@Param("name")String name,@Param("menuid")String menuid,@Param("menuname")String menuname);
	
	@Insert({ "insert into svds_rule(id,name,content,ordersort,relationid,projectid) values(nvl((select max(id) from svds_rule),0)+1,#{name},#{content},#{ordersort},#{relationid},#{projectid})" })
	public Integer addrule(@Param("name")String name,@Param("content")String content,@Param("ordersort")Integer ordersort,@Param("relationid")Integer relationid,@Param("projectid")Integer projectid);
	@Select("select text from svds_menu where id =#{menuid}")
	public String getMenename(Integer menuid);
	
	@Select("select id from svds_relation where name = #{name}")
	public Integer findRelationidbyname(String name);
	
	@Delete({"delete from svds_relation where id = #{id}"})
	public Integer delRelation(Integer id);
	@Delete({"delete from svds_rule where relationid = #{id}"})
	public Integer delRule(Integer id);
	
	@Select("select name from svds_rule where projectid=#{projectid} and ordersort = 0 and name like #{fileidlike}  and rownum <2")
	public String getXstlcell(@Param("projectid")Integer projectid,@Param("fileid")Integer fileid,@Param("fileidlike")String fileidlike); 
	
	@Select("select relationid from svds_rule where projectid=#{projectid} and ordersort = 0 and name like #{fileidlike}  and rownum <2")
	public Integer getRelationByid(@Param("projectid")Integer projectid,@Param("fileid")Integer fileid,@Param("fileidlike")String fileidlike);
	@Select("select count(1) from svds_rule where relationid = #{id}")
	public Integer getRuleCount(Integer id);
	@Select("select name from svds_rule where projectid=#{projectid} and relationid = #{relationid} and ordersort = #{ordersort}")
	public String getRuleName(@Param("projectid")Integer projectid,@Param("relationid")Integer relationid,@Param("ordersort")Integer ordersort);
	
	
	
	@Select("select * from svds_files where tabsid = #{tabsid}  and filename like #{filename}")
	@Results(id = "fileMap", value = {
			@Result(column = "fileId", property = "fileId", javaType = Integer.class),
			@Result(column = "fileName", property = "fileName", javaType = String.class),
			@Result(column = "fileUrl", property = "fileUrl", javaType = String.class),
			@Result(column = "fileDate", property = "fileDate", javaType = String.class),
			@Result(column = "fileVersion", property = "fileVersion", javaType = String.class),
			@Result(column = "format", property = "format", javaType = String.class),
			@Result(column = "securityId", property = "security", javaType = SVDS_Security.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_SecurityDao.getSecurityById")),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "tabsid", property = "tabsId", javaType = Integer.class),
			@Result(column = "menuId", property = "menu", javaType = SVDS_Menu.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MenuDao.getMenuById")),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "pId", property = "pId", javaType = Integer.class),
			//@Result(column = "majorId", property = "major", javaType = SVDS_Major.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MajorDao.getMajorById")),
			@Result(column = "location", property = "location", javaType = String.class),
			@Result(column = "abstracts", property = "abstracts", javaType = String.class),
			@Result(column = "fileSize", property = "fileSize", javaType = String.class),
			@Result(column="fileId",property="alias",many=@Many(select = "ac.drsi.nestor.dao.SVDS_AliasDao.listAliasById"))
	})
	public List<SVDS_Files> gettabsidbyfile(@Param("tabsid")Integer tabsid,@Param("filename")String filename);
	
	
	@Select("select count(1) from svds_rule where projectid in (select menuid from svds_files where fileid =#{fileid}) and ordersort = 0 and name like #{fileidlike} ")
	public Integer  isHave(@Param("fileid")Integer fileid,@Param("fileidlike")String fileidlike);
	
	
	@Select("<script>select * from (select * from svds_menu where parentid in (select id from svds_menu where lv = 1) or parentid in (select id from svds_menu where parentid in (select id from svds_menu where lv = 1)) or lv >3) where url !='html/yanzhengjuzhen.html' and id not in(select id from svds_menu where id  in ( select parentid from svds_menu ) ) and state = 0 and id in (select menuid from svds_rolemenu where  roleid =("
			+ " select roleid from svds_users where userid =#{userid} )) and id not in <foreach collection='ids' open='(' item='id_' separator=',' close=')'> #{id_}</foreach> </script>")
	 public List<Phenomenon> findAllrelationProject(@Param("userid")Integer userid,@Param("ids")String[] ids);
	@Select("select wm_concat(project) from svds_relation")
	public String findids();
	
	@Update("update svds_relation set project = #{project},menuname=#{menuname} where id = #{id}")
	public Integer updateRelation(@Param("project")String project,@Param("menuname")String menuname,@Param("id")Integer id);
	
	@Select("select * from svds_relation where id=#{id} order by id")
	public SVDS_Relation findrelationbyid(Integer id);
	@Select("select * from svds_rule where relationid=#{relationid} and projectid=#{projectid} order by ordersort")
	public List<SVDS_Rule> findruleBymenuidprojectid(@Param("relationid")Integer relationid,@Param("projectid")Integer projectid);
	
	@Select("select * from svds_files where filename=#{filename} and menuid=#{menuid} and rownum <2")
	@Results(id = "fileMap2", value = {
			@Result(column = "fileId", property = "fileId", javaType = Integer.class),
			@Result(column = "fileName", property = "fileName", javaType = String.class),
			@Result(column = "fileUrl", property = "fileUrl", javaType = String.class),
			@Result(column = "fileDate", property = "fileDate", javaType = String.class),
			@Result(column = "fileVersion", property = "fileVersion", javaType = String.class),
			@Result(column = "format", property = "format", javaType = String.class),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "tabsid", property = "tabsId", javaType = Integer.class),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "pId", property = "pId", javaType = Integer.class),
			@Result(column = "location", property = "location", javaType = String.class),
			@Result(column = "abstracts", property = "abstracts", javaType = String.class),
			@Result(column = "fileSize", property = "fileSize", javaType = String.class)
	})
	public SVDS_Files findfilesbymenuidorname(@Param("filename")String filename,@Param("menuid")Integer menuid);
	
	@Select("select * from svds_tabs where id=#{tabsid} and rownum <2")
	public Tabs findtabsbytabsid(Integer tabsid);
	
	@Select("select * from svds_tabs where name=#{name} and lv=#{lv} and menuid=#{menuid} and rownum <2")
	public Tabs findtabsbytabsidandname(@Param("name")String name,@Param("lv")Integer lv,@Param("menuid")Integer menuid);
	@Select("select menuid from svds_files where fileid = #{id}")
	public Integer findMenuidByFileid(Integer id);
	
	
	@Select("select * from svds_rule where relationid = #{id} and projectid = #{projectid} order by ordersort")
	public List<Rule> findrulebyrelationid(@Param("id")Integer id,@Param("projectid")Integer projectid);
}
