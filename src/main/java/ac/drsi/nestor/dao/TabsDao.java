package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.Tabs;

@Mapper
public interface TabsDao {
	/**
	 * 根据Id查询未删除的选项卡
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from svds_tabs where id=#{id} and state=0 order by sortOrder desc")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "sortOrder", property = "sortOrder"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.TabsDao.listByParetId")) })
	public Tabs getTabsById(Integer id);
	
	@Select("select * from svds_tabs where parentId=#{id} and state=0 ")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "sortOrder", property = "sortOrder"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.TabsDao.listTabsByParentId")) })
	public List<Tabs> listTabsByParentId(Integer id);
	
	@Select("select * from svds_tabs where parentId=#{id}")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "sortOrder", property = "sortOrder"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.TabsDao.listTabsResByParentId")) })
	public List<Tabs> listTabsResByParentId(Integer id);

	/**
	 * 根据删除的Id查询选项卡
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from svds_tabs where id=#{id}")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.TabsDao.listByParetId")) })
	public Tabs getDelTabsById(Integer id);

	/**
	 * 根据Id查询选项卡
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from svds_tabs where id=#{id}")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid") })
	public Tabs getTabsByIdAll(Integer id);
	
	/**
	 * 根据Id修改选项卡名称
	 * 
	 * @param name
	 * @param id
	 * @return
	 */
	@Update("update svds_tabs set name=#{name} where id=#{id}")
	public Integer updateTabs(@Param("name") String name,
			@Param("id") Integer id);

	/**
	 * 根据名称和等级判断是否存在
	 * 
	 * @param name
	 * @param lv
	 * @return
	 */
	@Select("select * from svds_tabs where name=#{name} and lv=#{lv}  order by sortOrder desc")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "sortOrder", property = "sortOrder"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.TabsDao.listByParetId")) })
	public Tabs isExitTabByName(@Param("name") String name,
			@Param("lv") Integer lv);

	/**
	 * 根据Id删除选项卡
	 * 
	 * @param id
	 * @return
	 */
	@Delete({ "delete from svds_tabs where id=#{id}" })
	public Integer delTabs(Integer id);
	
	/**
	 * 根据菜单Id等级父级Id查询
	 * @param menuId
	 * @param lv
	 * @param parentId
	 * @return
	 */
	@Select({ "select * from svds_tabs where menuId=#{menuId} and lv=#{lv} and parentId=#{parentId} and state=0  order by sortOrder desc,cast(REGEXP_SUBSTR(name, '^[0-9]+') as int), nlssort(name, 'NLS_SORT=SCHINESE_PINYIN_M')" })
	@Results({
		@Result(column = "id", property = "id"),
		@Result(column = "name", property = "name"),
		@Result(column = "lv", property = "lv"),
		@Result(column = "state", property = "state"),
		@Result(column = "parentid", property = "parentid"),
		@Result(column = "sortOrder", property = "sortOrder"),
		@Result(column = "menuid", property = "menuid") })
	public List<Tabs> listTabByLv(@Param("menuId")Integer menuId,@Param("lv")Integer lv,@Param("parentId")Integer parentId);
	/**
	 * 根据Id修改选项卡置最前
	 * 
	 * @param id
	 * @return
	 */
	@Update("update svds_tabs set sortOrder = #{sortOrder} where id = #{id} ")
	public Integer updateTabsOrder(@Param("id") Integer id,@Param("sortOrder")Integer sortOrder);
	/**
	 * 根据选项卡Id和菜单Id查询选项卡排序值
	 * @param id
	 * @param menuId
	 * @return
	 */
	@Select("select * from svds_tabs where id=#{id} and menuId=#{menuId} and parentId=#{parentId}")
	@Results({
		@Result(column = "id", property = "id"),
		@Result(column = "name", property = "name"),
		@Result(column = "lv", property = "lv"),
		@Result(column = "state", property = "state"),
		@Result(column = "parentid", property = "parentid"),
		@Result(column = "menuid", property = "menuid"),
		@Result(column = "sortOrder", property = "sortOrder")})
	public Tabs getTabsOrderById(@Param("id") Integer id,@Param("menuId")Integer menuId,@Param("parentId")Integer parentId);
	
	/**
	 * 查最大排序值
	 * @param id
	 * @param menuId
	 * @param parentId
	 * @return
	 */
	@Select("select max(sortOrder) from svds_tabs where menuId=#{menuId} and parentId=#{parentId} and lv=#{lv}")
	public Integer getTabsOrderMax(@Param("menuId")Integer menuId,@Param("parentId")Integer parentId,@Param("lv") Integer lv);

	/**
	 * 根据Id修改选项卡状态（未删除，已删除）
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	@Update("update svds_tabs set state = #{state} where id = #{id}")
	public Integer updateTabsState(@Param("id") Integer id,
			@Param("state") Integer state);
	
	/**
	 * 根据选项Id查询文件Id
	 * 
	 * @param id
	 * @return
	 */
	@Select("select fileid from svds_files where tabsid = #{id}")
	public List<String> getFileidBytabsid(Integer id);

	/**
	 * 根据选项卡Id查询到文件Id，再根据文件Id删除收藏文件
	 * 
	 * @param id
	 * @return
	 */
	@Delete({ "delete from svds_collectfile where fileid in(select fileid from svds_files where tabsid = #{id})" })
	public Integer delsvds_collectfile(Integer id);

	/**
	 * 根据选项卡Id查询到文件Id，再根据文件Id删除标签文件
	 * 
	 * @param id
	 * @return
	 */
	@Delete({ "delete from svds_aliasfile where fileid in (select fileid from svds_files where tabsid = #{id})" })
	public int delAliasBytabsid(Integer id);

	/**
	 * 根据选项卡Id查询到文件Id，再根据文件Id删除首页推送文件
	 * 
	 * @param id
	 * @return
	 */
	@Delete({ "delete from svds_visited where fileid in(select fileid from svds_files where tabsid = #{id})" })
	public Integer delsvds_visited(Integer id);

	/**
	 * 根据选项卡Id查询到文件Id，再根据文件Id删除消息文件
	 * 
	 * @param id
	 * @return
	 */
	@Delete({ "delete svds_message where fileid in (select fileid from svds_files where tabsid = #{menuid})" })
	public int delMessageBytabsid(Integer id);

	/**
	 * 根据选项卡Id查询到文件Id，再根据文件Id删除日志文件
	 * 
	 * @param id
	 * @return
	 */
	@Delete({ "delete from svds_log where fileid in(select fileid from svds_files where tabsid = #{id})" })
	public Integer delsvds_log(Integer id);

	/**
	 * 根据选项卡Id删除文件
	 * 
	 * @param id
	 * @return
	 */
	@Delete({ "delete from svds_files where tabsid=#{id}" })
	public Integer deltabsforfile(Integer id);
	
	/**
	 * 根据选项Id更新文件路径
	 * @param tabsId  选项卡Id
	 * @param oldName 旧名称
	 * @param newName 新名称
	 * @return
	 */
	@Update("update svds_files set location=replace(location,#{oldName},#{newName}) where tabsId=#{tabsId}")
	public Integer updateFilesLocationByTabsId(@Param("tabsId")Integer tabsId,@Param("oldName")String oldName,@Param("newName")String newName);

	/**
	 * 根据Id查询子级数量
	 * 
	 * @param id
	 * @return
	 */
	@Select("select count(0) from svds_tabs where parentid = #{id}")
	public Integer ishavachildren(Integer id);

	/**
	 * 根据Id查询父级Id
	 * 
	 * @param id
	 * @return
	 */
	@Select("select id from svds_tabs where parentid = #{id}")
	public List<Integer> getchildernids(Integer id);

	/**
	 * 根据Id和菜单Id查询同一级的选项卡
	 * 
	 * @param id
	 * @param menuId
	 * @return
	 */
	@Select("select * from svds_tabs where parentid=#{id} and menuId=#{menuId} and state=0 order by sortOrder desc")
	@Results({
			@Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"),
			@Result(column = "sortOrder", property = "sortOrder"),
			@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.TabsDao.listByParetId")) })
	public List<Tabs> listByTabsId(@Param("id") Integer id,
			@Param("menuId") Integer menuId);

	/**
	 * 根据Id查询同一级的未被删除的选项卡
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from svds_tabs where id in(select id from svds_tabs where parentid=#{id}) and state=0 order by sortOrder desc")
	@Results({ @Result(column = "id", property = "id"),
			@Result(column = "name", property = "name"),
			@Result(column = "lv", property = "lv"),
			@Result(column = "state", property = "state"),
			@Result(column = "parentid", property = "parentid"),
			@Result(column = "menuid", property = "menuid"), 
			@Result(column = "sortOrder", property = "sortOrder")})
	public List<Tabs> listByParetId(Integer id);
/**
 * 根据菜单Id查询未删除的层级选项卡
 * @param menuId 菜单Id
 * @return
 */
	@Select("select * from svds_tabs where state=0 and menuId = #{menuId}")
	@Results({
		@Result(column = "id", property = "id"),
		@Result(column = "name", property = "name"),
		@Result(column = "lv", property = "lv"),
		@Result(column = "state", property = "state"),
		@Result(column = "parentid", property = "parentid"),
		@Result(column = "menuid", property = "menuid"),
		@Result(column = "sortOrder", property = "sortOrder"),
		@Result(column = "id", property = "children", many = @Many(select = "ac.drsi.nestor.dao.TabsDao.listByParetId")) })
	public List<Tabs> listTabsByMenuId(Integer menuId);
	
	/**
	 * 根据菜单Id查询已删除的选项卡
	 * @param menuid
	 * @return
	 */
	@Select("select * from svds_tabs where state=1 and menuid = #{menuid}")
	public List<Tabs> listTabsByState(Integer menuid);

	/**
	 * 根据Id查询选项卡
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from svds_tabs where id=#{id} ")
	public Tabs getTabById(@Param("id") Integer id);

	/**
	 * 根据选项卡id集合查询
	 * 
	 * @param tabsIds
	 * @return
	 */
	@Select({
			"<script>",
			"select * from svds_tabs",
			"where ",
			"<foreach collection='tabsIds' item='id' open='(' separator='or' close=')'>",
			" id in #{id} ", "</foreach>", "</script>" })
	public List<Tabs> getTabsByIds(@Param("tabsIds") List<Integer> tabsIds);
	
	
}
