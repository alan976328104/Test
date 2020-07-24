package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Visited;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.sql.SVDS_VisitedSqlProvider;

@Mapper
public interface SVDS_VisitedDao {
	/**
	 * 查询所有记录
	 * @return
	 */
	@Select("select * from SVDS_Visited order by vid desc ")
	@Results(id = "visitedMap", value = {
			@Result(column = "vId", property = "vId", javaType = Integer.class),
			@Result(column = "lastTime", property = "lastTime", javaType = String.class),
			@Result(column = "fileId", property = "file", javaType = SVDS_Files.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_FilesDao.getFilesById")),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			@Result(column = "counts", property = "counts", javaType = Integer.class) })
	public List<SVDS_Visited> getVisitedAll();

	/**
	 * 根据用户Id查询点击次数从大到小排序
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_Visited where userId=#{userId} and counts>0 and fileId in(select fileId from svds_files where state=0) order by counts desc")
	@ResultMap("visitedMap")
	public List<SVDS_Visited> listCountByUserId(@Param("userId") Integer userId);

	/**
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_Visited")
	public Integer getVisitedAllCount();

	/**
	 * 添加消息
	 * 
	 * @param visited
	 * @return
	 */
	@InsertProvider(type = SVDS_VisitedSqlProvider.class, method = "insertVisited")
	public Integer insertVisited(SVDS_Visited visited);

	/**
	 * 修改消息
	 * 
	 * @param visited
	 * @return
	 */
	@UpdateProvider(type = SVDS_VisitedSqlProvider.class, method = "updateVisited")
	public Integer updateVisited(SVDS_Visited visited);

	/***
	 * 根据Id查询记录
	 * 
	 * @param vId
	 * @return
	 */
	@Select("select * from SVDS_Visited where vId =#{vId}")
	@ResultMap("visitedMap")
	public SVDS_Visited getVisitedById(@Param("vId") Integer vId);

	/***
	 * 根据用户id文件Id查找对象
	 * 
	 * @param userId
	 * @param fileId
	 * @return
	 */
	@Select("select * from SVDS_Visited where userId =#{userId} and fileId=#{fileId} and rownum<2")
	@ResultMap("visitedMap")
	public SVDS_Visited getVisitedByFileId(@Param("userId") Integer userId,
			@Param("fileId") Integer fileId);

	/***
	 * 根据查询点击次数最多的
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_Visited where counts>0 and fileId in (select fileId from svds_files where menuId in(select menuId from svds_rolemenu where roleId=(select roleId from svds_users where userId=#{userId} )) and state=0) order by counts desc")
	@ResultMap("visitedMap")
	public List<SVDS_Visited> listCount(@Param("userId") Integer userId);

	/**
	 * 根据文件Id删除文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_Visited",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>", "</script>" })
	public Integer deleteByFileId(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据选项卡删除数据
	 * @param tabsId
	 * @return
	 */
	@Delete({"delete from SVDS_Visited where fileId in(select fileId from svds_files where tabsId=#{tabsId})"})
	public Integer deleteByTabsId(@Param("tabsId") Integer tabsId);
	
	/**
	 * 根据用户Id删除记录
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_Visited",
			"where ",
			"<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" userId in #{userId} ", "</foreach>", "</script>" })
	public Integer deleteByUserId(@Param("ids") List<Integer> ids);

	/**
	 * 删除记录
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_Visited",
			"where ",
			"<foreach collection='ids' item='vId' open='(' separator='or' close=')'>",
			" vId in #{vId} ", "</foreach>", "</script>" })
	public Integer deleteVisited(@Param("ids") List<Integer> ids);
}
