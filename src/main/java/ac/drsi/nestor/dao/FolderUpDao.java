package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;

import ac.drsi.nestor.entity.RelationData;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.Tabs;
import ac.drsi.nestor.sql.SVDS_TabsSqlProvider;

@Mapper
public interface FolderUpDao {
/**
 * 获得全部选项卡名称
 * @param menuid
 * @return
 */
	@Select("select name from svds_tabs where menuid = #{menuid}")
	public List<String> getMenuTabs(Integer menuid);
	
	/**
	 * 获得菜单下所有选项卡
	 * @param menuid
	 * @return
	 */
	@Select("select * from svds_tabs where menuid = #{menuid} and lv = 1 and state=0 order by id")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.FolderUpDao.getTabsBytabsid")) })
	public List<Tabs> getTabsByMenuid(Integer menuid);
/**
 * 根据父节点id获得选项卡
 * @param id
 * @return
 */
	@Select("select * from svds_tabs where parentid = #{id} and state=0")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.FolderUpDao.getTabsByParentId")) })
	public List<Tabs> getTabsBytabsid(Integer id);
/**
 * 根据父节点id获得选项卡
 * @param id
 * @return
 */
	@Select("select * from svds_tabs where parentid = #{id} and state=0")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.FolderUpDao.getTabsByParentId1")) })
	public List<Tabs> getTabsByParentId(Integer id);

	/**
	 * 根据父节点id获得选项卡
	 * @param id
	 * @return
	 */
	@Select("select * from svds_tabs where parentid = #{id} and state=0")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.FolderUpDao.getTabsByParentId2")) })
	public List<Tabs> getTabsByParentId1(Integer id);

	/**
	 * 根据父节点id获得选项卡
	 * @param id
	 * @return
	 */
	@Select("select * from svds_tabs where parentid = #{id} and state=0")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid")
		 })
	public List<Tabs> getTabsByParentId2(Integer id);

	
	/**
	 * 插入选项卡
	 * @param tabs
	 * @return
	 */
	@InsertProvider(type = SVDS_TabsSqlProvider.class, method = "insertTabs")
	@SelectKey(before = false, statement = "select max(id) as id from svds_tabs", keyProperty = "id", resultType = Integer.class)
	public Integer insertTabs(Tabs tabs);
	/**
	 * 根据id获得单个选项卡
	 * @param id
	 * @return
	 */
	@Select("select * from svds_tabs where id = #{id}")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid") })
	public Tabs getTabsBytabsId(Integer id);
	
	
	/**
	 * 获得选项卡等级
	 * @param name
	 * @param lv
	 * @param menuId
	 * @return
	 */
	@Select("select * from svds_tabs where name = #{name} and lv=#{lv} and menuId=#{menuId}")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid") })
	public Tabs getTabsByLv(@Param("name")String name,@Param("lv")Integer lv,@Param("menuId")Integer menuId);
	
	
	@SelectKey(before = false, statement = "select max(id) as id from SVDS_tabs", keyProperty = "id", statementType = StatementType.STATEMENT, resultType = Integer.class)
	@Insert({ "insert into SVDS_tabs(id,name,parentid,lv,menuid,state，sortorder) values(nvl((select max(id) from SVDS_tabs),0)+1,#{name},#{parentid},#{lv},#{menuid},0,0)" })
	public Integer insertTab(Tabs tabs);
	@Select({"select  count(*) from svds_tabs where name = #{name} and menuid = #{menuid} and parentid = #{parentid}"})
	public Integer tabishave(@Param("name")String name,@Param("menuid")Integer menuid,@Param("parentid")Integer parentid);
	@Select({"select count(1) from svds_tabs where name=#{name} and parentid=#{parentid} and menuid = #{menuid} and lv=#{lv}"})
	public Integer getTabscount(Tabs tabs);
	@Select({"select id from svds_tabs where name = #{name} and menuid = #{menuid} and lv=#{lv} and rownum <2"})
	public Integer getTbsByName(@Param("name")String name,@Param("menuid")Integer menuid,@Param("lv")Integer lv);
	
	@Select({"select id from svds_tabs where name = #{name} and menuid = #{menuid} and lv=#{lv} and parentid = #{parentid} and rownum <2"})
	public Integer getTbsparidByName(@Param("name")String name,@Param("menuid")Integer menuid,@Param("lv")Integer lv,@Param("parentid") Integer parentid);
	
	
	
	@Insert({"insert into svds_files(fileid,fileurl,filename,filedate,securityId,userId,state,menuid,tabsid) values(nvl((select max(fileid) from svds_files),0)+1,#{fileurl},#{filename},#{filedate},#{securityId},#{userId},0,#{menuid},#{tabsid})"})
	public Integer inserFileByFolderup(@Param("fileurl")String fileurl,@Param("filename")String filename,@Param("filedate")String filedate,
			@Param("securityId")Integer securityId,@Param("userId")Integer userId,@Param("menuid")Integer menuid,@Param("tabsid")Integer tabsid);
	@Insert({"insert into svds_info(id,url,menuid) values(nvl((select max(id) from svds_info),0)+1,#{url},#{menuid})"})
	public Integer insterInfo(@Param("url")String url,@Param("menuid")Integer menuid);
	@Select({"select name from svds_tabs where parentid =( select id from svds_tabs where menuid = #{menuid} and name = #{name} and lv=#{lv} and rownum <2)"})
	public List<String> ishave(@Param("menuid")Integer menuid,@Param("name")String name,@Param("lv")Integer lv);
	
	
	@Select("select * from svds_files where  tabsid = #{id}")
	@Results({ @Result(column = "fileid", property = "id"),
			@Result(column = "filename", property = "name"),
			@Result(column = "fileurl", property = "url") })
	public List<RelationData> getfileBytabsIdforRelation(Integer id);
	
	@Select("select id from svds_tabs where name=#{name} and parentid=#{parentid} and menuid = #{menuid}")
	public Integer gettabsidbyarr(@Param("name")String name,@Param("parentid")Integer parentid,@Param("menuid")Integer menuid);
	
	@Select("select id from svds_tabs where name=#{name} and parentid=#{parentid} and menuid = #{menuid} and lv=#{lv} and rownum <2")
	public Integer getTabsidcountD(Tabs t);
	
	@Select("select count(1) from svds_files where  tabsid = #{tabsid}  and filename = #{name}")
	public Integer ishaveFilenameoOrTabsid(@Param("tabsid")Integer tabsid,@Param("name")String name);
}
