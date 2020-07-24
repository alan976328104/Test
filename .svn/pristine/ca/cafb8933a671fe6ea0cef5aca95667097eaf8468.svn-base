package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Recycle;
import ac.drsi.nestor.entity.SVDS_User;

@Mapper
public interface SVDS_RecycleDao {
	/**
	 * 查询所有回收站
	 * 
	 * @return
	 */
	@Select("select * from SVDS_recycle where fileId in ( select fileId from svds_files where state=1 ) ORDER BY recDate desc")
	@Results(id = "recMap", value = {
			@Result(column = "recId", property = "recId", javaType = Integer.class),
			@Result(column = "recDate", property = "recDate", javaType = String.class),
			@Result(column = "fileId", property = "files", javaType = SVDS_Files.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_FilesDao.getFilesById")),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")) })
	public List<SVDS_Recycle> getRecycleAll();

	/**
	 * 根据菜单Id和文件名称查询
	 * 
	 * @param menuId
	 * @param fileName
	 * @return
	 */
	@Select("select * from SVDS_recycle where fileId in ( select fileId from svds_files where menuId = #{menuId} and fileName like '%' || #{fileName} || '%' and state=1) ORDER BY recDate desc")
	@ResultMap("recMap")
	public List<SVDS_Recycle> listRecycleByMenuId(
			@Param("menuId") Integer menuId, @Param("fileName") String fileName);

	/**
	 * 根据选项卡Id和文件名称查询
	 * 
	 * @param tabsId
	 * @param fileName
	 * @return
	 */
	@Select("select * from SVDS_recycle where fileId in ( select fileId from svds_files where tabsId = #{tabsId}  and fileName like '%' || #{fileName} || '%' and state=1) ORDER BY recDate desc")
	@ResultMap("recMap")
	public List<SVDS_Recycle> listRecycleByTabsId(
			@Param("tabsId") Integer tabsId, @Param("fileName") String fileName);

	/**
	 * 根据文件名称模糊查询
	 * 
	 * @param fileName
	 * @return
	 */
	@Select("select * from SVDS_recycle where fileId in ( select fileId from svds_files where  fileName like '%' || #{fileName} || '%' and state=1 ) ORDER BY recDate desc")
	@ResultMap("recMap")
	public List<SVDS_Recycle> getRecycleByName(
			@Param("fileName") String fileName);

	/**
	 * 查询回收站所有总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_recycle")
	public Integer getRecycleAllCount();

	/**
	 * 添加回收站
	 * 
	 * @param SVDS_Recycle
	 * @return
	 */
	@Insert({ "insert into SVDS_recycle(recId,recDate,fileId,userId) values(nvl((select max(recId) from SVDS_recycle),0)+1,#{recDate},#{files.fileId},#{user.userId})" })
	public Integer insertRecycle(SVDS_Recycle recycle);

	/**
	 * 修改回收站
	 * 
	 * @param SVDS_Recycle
	 * @return
	 */
	@Update("update SVDS_recycle set recDate=#{recDate} where recId=#{recId}")
	public Integer updateRecycle(SVDS_Recycle recycle);

	/***
	 * 根据Id查询回收站
	 * 
	 * @param recId
	 * @return
	 */
	@Select("select * from SVDS_recycle where recId =#{recId} ORDER BY recId")
	@ResultMap("recMap")
	public SVDS_Recycle getRecycleById(@Param("recId") Integer recId);

	/**
	 * 根据用户ID查询文件
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_recycle where userId =#{userId} ORDER BY recId")
	@ResultMap("recMap")
	public List<SVDS_Recycle> getFolderById(@Param("userId") Integer userId);

	/***
	 * 根据文件查询回收站
	 * 
	 * @param files
	 * @return
	 */
	@Select("select * from SVDS_recycle where fileId =#{files.fileId} ORDER BY recId")
	@ResultMap("recMap")
	public SVDS_Recycle getRecycle(@Param("files") SVDS_Files files);

	/***
	 * 根据id集合批量查询回收站
	 * 
	 * @param recId
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_recycle",
			"where recId in",
			"<foreach collection='recIds' item='recId' open='(' separator='or' close=')'>",
			" recId in #{recId}", "</foreach>", "ORDER BY recId", "</script>" })
	@ResultMap("recMap")
	public List<SVDS_Recycle> getRecycleByIds(
			@Param("recIds") List<Integer> recIds);

	/**
	 * 根据文件Id集合删除回收站
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_recycle",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId}", "</foreach>", "</script>" })
	public Integer deleteRecycleByFileIds(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据选项卡Id删除数据
	 * @param tabsId
	 * @return
	 */
	@Delete({"delete from svds_recycle where fileid in(select fileId from svds_files where tabsId=#{tabsId})"})
	public Integer deleteRecycleByTabsId(@Param("tabsId") Integer tabsId);

	/**
	 * 根据Id集合删除回收站
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_recycle",
			"where ",
			"<foreach collection='ids' item='recId' open='(' separator='or' close=')'>",
			" recId in #{recId}", "</foreach>", "</script>" })
	public Integer deleteRecycle(@Param("ids") List<Integer> ids);
}
