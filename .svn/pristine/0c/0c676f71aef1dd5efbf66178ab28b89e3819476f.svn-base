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

import ac.drsi.nestor.entity.SVDS_Folder;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.sql.SVDS_FolderSqlProvider;

@Mapper
public interface SVDS_FolderDao {
	/**
	 * 查询所有收藏夹
	 * 
	 * @return
	 */
	@Select("select * from SVDS_folder ORDER BY folderId")
	@Results(id = "folMap", value = {
			@Result(column = "folderId", property = "folderId", javaType = Integer.class),
			@Result(column = "folderName", property = "folderName", javaType = String.class),
			@Result(column = "folderDate", property = "folderDate", javaType = String.class),
			@Result(column = "parentId", property = "parentId", javaType = Integer.class),
			@Result(column = "icon", property = "icon", javaType = String.class),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById"))})
	public List<SVDS_Folder> getFolderAll();

	/**
	 * 根据用户Id查询
	 * 
	 * @param folderDate
	 * @return
	 */
	@Select("select * from SVDS_folder where userId=#{userId} ORDER BY folderId")
	@ResultMap("folMap")
	public List<SVDS_Folder> getFolderByUser(Integer userId);

	/**
	 * 查询收藏夹所有总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_folder")
	public Integer getFolderAllCount();

	/**
	 * 添加收藏夹
	 * 
	 * @param SVDS_Folder
	 * @return
	 */
	@InsertProvider(type = SVDS_FolderSqlProvider.class, method = "insertFolder")
	public Integer insertFolder(SVDS_Folder folder);

	/**
	 * 修改收藏夹
	 * 
	 * @param SVDS_Folder
	 * @return
	 */
	@UpdateProvider(type = SVDS_FolderSqlProvider.class, method = "updateFolder")
	public Integer updateFolder(SVDS_Folder folder);

	/**
	 * 判断是否存在收藏夹
	 * @param folderName
	 * @param parentId
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_folder where folderName =#{folderName} and parentId=#{parentId} and userId=#{userId} ORDER BY folderId")
	@ResultMap("folMap")
	public SVDS_Folder isExistFolder(@Param("folderName") String folderName,@Param("parentId") Integer parentId,@Param("userId") Integer userId);
	
	/***
	 * 根据Id查询收藏夹
	 * 
	 * @param folderId
	 * @return
	 */
	@Select("select * from SVDS_folder where folderId =#{folderId} ORDER BY folderId")
	@ResultMap("folMap")
	public SVDS_Folder getFolderById(@Param("folderId") Integer folderId);
	
	/***
	 * 根据Id查询收藏夹
	 * 
	 * @param folderId
	 * @return
	 */
	@Select("select * from SVDS_folder where parentId =#{parentId} ORDER BY folderId")
	@ResultMap("folMap")
	public SVDS_Folder getFolderByParentId(@Param("parentId") Integer parentId);

	/**
	 * 根据Id删除收藏夹
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"SVDS_folder",
			"where userId=#{userId} and folderId in",
			"<foreach collection='ids' item='folderId' open='(' separator=',' close=')'>",
			"#{folderId}", "</foreach>", "</script>" })
	public Integer deleteFolder(@Param("ids") List<Integer> ids,@Param("userId")Integer userId);
	
	/**
	 * 根据Id删除收藏夹
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"SVDS_folder",
			"where userId in",
			"<foreach collection='userIds' item='userId' open='(' separator=',' close=')'>",
			"#{userId}", "</foreach>", "</script>" })
	public Integer deleteFolderByUserIds(@Param("userIds") List<Integer> userIds);
}
