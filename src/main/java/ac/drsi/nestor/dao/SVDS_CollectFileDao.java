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
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import ac.drsi.nestor.entity.SVDS_CollectFile;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Folder;
import ac.drsi.nestor.sql.SVDS_CollectFileSqlProvider;
import ac.drsi.nestor.sql.SVDS_FilesSqlProvider;

@Mapper
public interface SVDS_CollectFileDao {
	/**
	 * 查询所有收藏文件
	 * 
	 * @return
	 */
	@Select("select * from SVDS_collectFile ORDER BY colId")
	@Results(id = "colMap", value = {
			@Result(column = "colId", property = "colId", javaType = Integer.class),
			@Result(column = "colDate", property = "colDate", javaType = String.class),
			@Result(column = "fileId", property = "files", javaType = SVDS_Files.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_FilesDao.getFilesById")),
			@Result(column = "folderId", property = "folder", javaType = SVDS_Folder.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_FolderDao.getFolderById"))})
	public List<SVDS_CollectFile> getCollectFileAll();

	/**
	 * 根据收藏文件名称模糊查询
	 * 
	 * @param colDate
	 * @return
	 */
	@Select("select * from SVDS_collectFile where colDate like '%' || #{colDate} || '%' ORDER BY colId")
	@ResultMap("colMap")
	public List<SVDS_CollectFile> getCollectFileByName(
			@Param("colDate") String colDate);

	/**
	 * 查询收藏文件所有总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_collectFile")
	public Integer getCollectFileAllCount();

	/**
	 * 添加收藏文件
	 * 
	 * @param SVDS_CollectFile
	 * @return
	 */
	@Insert({ "insert into SVDS_collectFile(colId,colDate,fileId,folderId) values(nvl((select max(colId) from SVDS_collectFile),0)+1,#{colDate},#{files.fileId},#{folder.folderId})" })
	public Integer insertCollectFile(SVDS_CollectFile collectFile);

	/**
	 * 修改收藏文件
	 * 
	 * @param SVDS_CollectFile
	 * @return
	 */
	@UpdateProvider(type = SVDS_CollectFileSqlProvider.class, method = "updateCollectFile")
	public Integer updateCollectFile(SVDS_CollectFile collectFile);

	/***
	 * 根据Id查询收藏文件
	 * 
	 * @param colId
	 * @return
	 */
	@Select("select * from SVDS_collectFile where colId =#{colId} ORDER BY colId")
	@ResultMap("colMap")
	public SVDS_CollectFile getCollectFileById(@Param("colId") Integer colId);

	/**
	 * 根据收藏夹ID查询文件
	 * 
	 * @param folderId
	 * @return
	 */
	@Select("select * from SVDS_collectFile where folderId =#{folderId} ORDER BY colDate desc")
	@ResultMap("colMap")
	public List<SVDS_CollectFile> getFolderById(@Param("folderId") Integer folderId);

	/***
	 * 是否已收藏过
	 * 
	 * @param colId
	 * @return
	 */
	@Select("select * from SVDS_collectFile where fileId =#{fileId} and folderId=#{folderId} ORDER BY colId")
	@ResultMap("colMap")
	public SVDS_CollectFile getCollectFile(@Param("fileId") Integer fileId,@Param("folderId") Integer folderId);
	

	/**
	 * 收藏文件条件查询
	 * 
	 * @param files
	 * @return
	 */
	@SelectProvider(type = SVDS_FilesSqlProvider.class, method = "selectCollectSql")
	@ResultMap("colMap")
	public List<SVDS_CollectFile> selectCollectSql(@Param("file") SVDS_Files file,@Param("folderId")Integer folderId,@Param("collectFile") SVDS_CollectFile collectFile);

	/***
	 * 根据批量查询收藏文件
	 * 
	 * @param colId
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_collectFile",
			"where colId in",
			"<foreach collection='colIds' item='colId' open='(' separator=',' close=')'>",
			"#{colId}", "</foreach>", "ORDER BY colId", "</script>" })
	@ResultMap("colMap")
	public List<SVDS_CollectFile> getCollectFileByIds(
			@Param("colIds") List<Integer> colIds);
	/**
	 * 根据文件Id删除文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_collectFile",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>", "</script>" })
	public Integer deleteByFileId(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据选项卡删除数据
	 * @param tabsId
	 * @return
	 */
	@Delete({"delete from SVDS_collectFile where fileId in(select fileId from svds_files where tabsId=#{tabsId})"})
	public Integer deleteByTabsId(@Param("tabsId") Integer tabsId);
	
	/**
	 * 根据收藏夹Id删除文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_collectFile",
			"where ",
			"<foreach collection='ids' item='folderId' open='(' separator='or' close=')'>",
			" folderId in #{folderId} ", "</foreach>", "</script>" })
	public Integer deleteByFolderIds(@Param("ids") List<Integer> folderIds);
	/**
	 * 根据Id删除收藏文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_collectFile",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>","and folderId=#{folderId}",  "</script>" })
	public Integer deleteCollectFile(@Param("ids") List<Integer> ids,@Param("folderId")Integer folderId);
	/**
	 * 根据用户Id删除收藏文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_collectFile",
			"where folderId in ( select folderId from svds_folder where ",
			"<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" userId in #{userId} ", "</foreach>", " ) ","</script>" })
	public Integer deleteByUserId(@Param("ids") List<Integer> ids);
}
