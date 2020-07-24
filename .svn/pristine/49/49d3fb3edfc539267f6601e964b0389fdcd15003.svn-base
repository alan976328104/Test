package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.sql.SVDS_MenuSqlProvider;

@Mapper
public interface SVDS_MenuDao {
	/**
	 * 查询所有菜单
	 * 
	 * @return
	 */
	@Select("select * from SVDS_menu where state=0 ORDER BY id")
	@Results(id = "menuMap", value = {
			@Result(column = "id", property = "id", javaType = Integer.class),
			@Result(column = "text", property = "text", javaType = String.class),
			@Result(column = "text", property = "name", javaType = String.class),
			@Result(column = "icon", property = "icon", javaType = String.class),
			@Result(column = "url", property = "url", javaType = String.class),
			@Result(column = "parentId", property = "parentId", javaType = Integer.class),
			@Result(column = "lv", property = "lv", javaType = Integer.class),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "ename", property = "ename", javaType = String.class),
			@Result(column = "id", property = "menus", many = @Many(select = "ac.drsi.nestor.dao.SVDS_MenuDao.listMenuByParentId")),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.SVDS_MenuDao.listMenuByParentId")) ,
			@Result(column = "userid", property = "userid", javaType = Integer.class)
	})
	public List<SVDS_Menu> getMenuAll();

	/**
	 * 根据名称模糊查询
	 * 
	 * @param menuname
	 * @return
	 */
	@Select("select * from SVDS_menu where text like '%' || #{text} || '%' ORDER BY id")
	@ResultMap("menuMap")
	public List<SVDS_Menu> listMenuByName(@Param("text") String text);

	/**
	 * 根据名称查询
	 * 
	 * @param menuname
	 * @return
	 */
	@Select("select * from SVDS_menu where text = #{text}  ORDER BY id")
	@ResultMap("menuMap")
	public SVDS_Menu getMenuByName(@Param("text") String text);

	/**
	 * 根据id和名称查询
	 * 
	 * @param menuname
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_menu ",
			" where ",
			" (text like '%' || lower(#{text}) || '%' ",
			" or text like '%' || upper(#{text}) || '%') ",
			// " and lv=2 ",
			" and ",
			"<foreach collection='ids' item='id' open='(' separator='or' close=')'>",
			" id in #{id}", "</foreach>", "</script>" })
	@ResultMap("menuMap")
	public List<SVDS_Menu> searchMenuById(@Param("ids") List<Integer> ids,
			@Param("text") String text);

	/**
	 * 根据基本信息表
	 * 
	 * @param ids
	 * @param text
	 * @return
	 */

	@Select({
			"<script>",
			"select * from SVDS_menu ",
			" where ",
			" id in( select  distinct menuid from svds_infodata where  value  like '%' || lower(#{text}) || '%' ",
			" or name like '%' || upper(#{text}) || '%' )",
			" and lv=2 ",
			" and ",
			"<foreach collection='ids' item='id' open='(' separator='or' close=')'>",
			" id in #{id}", "</foreach>", "</script>" })
	@ResultMap("menuMap")
	public List<SVDS_Menu> searchMenuByinfo(@Param("ids") List<Integer> ids,
			@Param("text") String text);

	/**
	 * 根据id集合查询
	 * 
	 * @param ids
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_menu where lv=2 ",
			" and ",
			"<foreach collection='ids' item='id' open='(' separator='or' close=')'>",
			" id in #{id}", "</foreach>", "</script>" })
	@ResultMap("menuMap")
	public List<SVDS_Menu> listMenuByIds(@Param("ids") List<Integer> ids);

	/**
	 * 查询菜单总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_menu")
	public Integer getMenuAllCount();

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @return
	 */
	@Insert({ "insert into SVDS_menu(id,text,lv) values(nvl((select max(id) from SVDS_menu),0)+1,#{text},#{lv})" })
	public Integer insertMenu(SVDS_Menu menu);

	/**
	 * 修改菜单
	 * 
	 * @param menu
	 * @return
	 */
	@UpdateProvider(type = SVDS_MenuSqlProvider.class, method = "updateMenu")
	public Integer updateMenu(SVDS_Menu menu);

	/***
	 * 根据Id查询菜单
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from SVDS_menu where id =#{id}  ORDER BY id")
	@ResultMap("menuMap")
	public SVDS_Menu getMenuById(Integer id);

	/**
	 * 根据菜单Id更新文件路径
	 * 
	 * @param menuId
	 *            菜单Id
	 * @param oldName
	 *            旧名称
	 * @param newName
	 *            新名称
	 * @return
	 */
	@Update("update svds_files set location=replace(location,#{oldName},#{newName}) where menuId=#{menuId}")
	public Integer updateFilesLocationByMenuId(@Param("menuId") Integer menuId,
			@Param("oldName") String oldName, @Param("newName") String newName);

	/***
	 * 根据父级Id查询子菜单
	 * 
	 * @param parentId
	 * @return
	 */
	//@Select("select * from SVDS_menu where parentId =#{id} and parentId <> 0 and state=0 ORDER BY id")
	
	@Select("select * from svds_menu where url IS NOT NULL or url <> '' and state=0  connect by parentid=prior id start with parentId =#{id}")
	@ResultMap("menuMap")
	public List<SVDS_Menu> listMenuByParentId(Integer id);

	/**
	 * 根据Id集合删除菜单
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_menu",
			"where ",
			"<foreach collection='ids' item='id' open='(' separator='or' close=')'>",
			"id in #{id}", "</foreach>", "</script>" })
	public Integer deleteMenu(@Param("ids") List<Integer> ids);

	/**
	 * 查询回收站里的菜单
	 * 
	 * @param state
	 * @return
	 */
	// @Select("select distinct menuid from svds_files where state=1")
	@Select({
			"<script>",
			"select distinct menuid from svds_tabs",
			"where ",
			"<foreach collection='tabsIds' item='id' open='(' separator='or' close=')'>",
			"id in #{id}", "</foreach>", "</script>" })
	public List<Integer> listMenuByState(@Param("tabsIds") List<Integer> tabsIds);

	/**
	 * 查询删除的选项卡Id
	 * 
	 * @return
	 */
	@Select("select distinct id from svds_tabs where state=1")
	public List<Integer> listTabsByState();

	/**
	 * 查询删除文件的选项卡Id
	 * 
	 * @return
	 */
	@Select("select distinct tabsid from svds_files where state=1")
	public List<Integer> listFilesByState();

	/**
	 * 根据菜单Id查询删除文件的选项卡Id
	 * 
	 * @param menuId
	 * @return
	 */
	@Select("select distinct tabsid from svds_files where menuId=#{menuId} and state=1")
	public List<Integer> listFilesByMenuState(Integer menuId);

	/**
	 * 查询已删除的菜单
	 * 
	 * @return
	 */
	@Select("select * from svds_menu where state=1")
	@Results({
			@Result(column = "id", property = "id", javaType = Integer.class),
			@Result(column = "text", property = "text", javaType = String.class),
			@Result(column = "text", property = "name", javaType = String.class),
			@Result(column = "icon", property = "icon", javaType = String.class),
			@Result(column = "url", property = "url", javaType = String.class),
			@Result(column = "parentId", property = "parentId", javaType = Integer.class),
			@Result(column = "lv", property = "lv", javaType = Integer.class),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "ename", property = "ename", javaType = String.class) })
	public List<SVDS_Menu> listMenusByState();

	/**
	 * 查询各专业的数据占比图
	 * 
	 * @return
	 */
	@Select({ "select count(*) as countNum,m.id as valueName  from svds_files f left join svds_menu m on m.id=f.menuId  group by f.menuId,m.id" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listMenu();

	/**
	 * 查询例题下的专业
	 * 
	 * @return
	 */
	@Select("select * from svds_menu where parentid=1")
	@Results({
		@Result(column = "id", property = "id", javaType = Integer.class),
		@Result(column = "text", property = "text", javaType = String.class),
		@Result(column = "text", property = "name", javaType = String.class),
		@Result(column = "icon", property = "icon", javaType = String.class),
		@Result(column = "url", property = "url", javaType = String.class),
		@Result(column = "parentId", property = "parentId", javaType = Integer.class),
		@Result(column = "lv", property = "lv", javaType = Integer.class),
		@Result(column = "state", property = "state", javaType = Integer.class),
		@Result(column = "ename", property = "ename", javaType = String.class) })
	public List<SVDS_Menu> menuParent();

	/**
	 * 根据Id查询子级数据
	 * @param mId
	 * @return
	 */
	@Select("select * from svds_menu s start with s.id=#{mId} CONNECT by  PRIOR s.id=s.parentId")
	@Results({
		@Result(column = "id", property = "id", javaType = Integer.class),
		@Result(column = "text", property = "text", javaType = String.class),
		@Result(column = "text", property = "name", javaType = String.class),
		@Result(column = "icon", property = "icon", javaType = String.class),
		@Result(column = "url", property = "url", javaType = String.class),
		@Result(column = "parentId", property = "parentId", javaType = Integer.class),
		@Result(column = "lv", property = "lv", javaType = Integer.class),
		@Result(column = "state", property = "state", javaType = Integer.class),
		@Result(column = "ename", property = "ename", javaType = String.class) })
	public List<SVDS_Menu> listChildrenMenu(Integer mId);
	
	/**
	 * 根据父级Id查询子级
	 * @param id 菜单Id
	 * @return 修改于2020年4月29日 增加 where url IS NOT NULL or url <> '' and state=0
	 */
	@Select("select count(*) from svds_menu where url IS NOT NULL or url <> '' and state=0  connect by parentid=prior id start with parentId =#{id}")
	public Integer menuChildren(Integer id);
	/**
	 * 系统标签数目各专业占比及数目
	 * @return
	 */
	@Select(" select count(* )as countNum,menuId as valueName from svds_files  where fileId in(select fileId from svds_aliasfile where aliasId in (select aliasId from svds_alias where aliasId in (select aliasId from svds_aliasfile group by aliasId ) and aliastype=1) group by fileId) group by  menuId")
	@Results({
		@Result(column = "valueName", property = "name", javaType = String.class),
		@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listBySysAlias();
	
}
