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

import ac.drsi.nestor.entity.SVDS_Alias;
import ac.drsi.nestor.entity.SVDS_AliasFile;
import ac.drsi.nestor.entity.SVDS_Files;

@Mapper
public interface SVDS_AliasFileDao {
	/**
	 * 根据用户Id查询标签
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("select * from SVDS_AliasFile  ORDER BY aliasId")
	@Results(id = "aliasFileMap", value = {
			@Result(column = "afId", property = "afId", javaType = Integer.class),
			@Result(column = "aliasId", property = "alias", javaType = SVDS_Alias.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_AliasDao.getAliasById")),
			@Result(column = "fileId", property = "files", javaType = SVDS_Files.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_FilesDao.getFilesById")),
			@Result(column = "aliasDate", property = "aliasDate", javaType = String.class)})
	public List<SVDS_AliasFile> getAliasFileAll();

	/**
	 * 根据标签Id查询标签
	 * 
	 * @param aliasId
	 * @return
	 */
	@Select("select * from SVDS_AliasFile where aliasId=#{aliasId}")
	@ResultMap("aliasFileMap")
	public List<SVDS_AliasFile> listAliasFileById(Integer aliasId);

	/**
	 * 根据文件Id查询标签
	 * 
	 * @param fileId
	 * @return
	 */
	@Select("select * from SVDS_AliasFile where fileId=#{fileId}")
	@ResultMap("aliasFileMap")
	public List<SVDS_AliasFile> listByFileId(Integer fileId);

	/**
	 * 判断是否添加过标签
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("select * from SVDS_AliasFile where aliasId=#{aliasId} and fileId=#{fileId}")
	@ResultMap("aliasFileMap")
	public SVDS_AliasFile existAliasFileById(@Param("aliasId") Integer aliasId,
			@Param("fileId") Integer fileId);

	/**
	 * 给文件添加标签
	 * 
	 * @param AliasFile
	 * @return
	 */
	@Insert({ "insert into SVDS_AliasFile(afId,aliasId,fileId,aliasDate) values(nvl((select max(afId) from SVDS_AliasFile),0)+1,#{alias.aliasId},#{files.fileId},#{aliasDate})" })
	public Integer insertAliasFile(SVDS_AliasFile aliasFile);
	/**
	 * 根据标签Id和文件Id查询
	 * @param ids
	 * @param aliasId
	 * @param sortOrder
	 * @return
	 */
	@Select({
			"<script>",
			"select *",
			"from SVDS_AliasFile",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>","and aliasId=#{aliasId}", "ORDER BY aliasDate ",
			"${sortOrder}", "</script>" })
	@ResultMap("aliasFileMap")
	public List<SVDS_AliasFile> listByFileIdAndAliasId(@Param("ids") List<Integer> ids,@Param("aliasId") Integer aliasId,@Param("sortOrder") String sortOrder);
	
	/**
	 * 根据标签Id集合查询
	 * 
	 * @param ids
	 * @return
	 */
	@Select({
			"<script>",
			"select *",
			"from SVDS_AliasFile",
			"where ",
			"<foreach collection='ids' item='aliasId' open='(' separator='or' close=')'>",
			" aliasId in #{aliasId} ", "</foreach>", "</script>" })
	@ResultMap("aliasFileMap")
	public List<SVDS_AliasFile> listByAliasIds(@Param("ids") List<Integer> ids);
	/**
	 * 根据文件Id集合删除文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_AliasFile",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>", "</script>" })
	public Integer deleteByFileId(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据选项卡删除数据
	 * @param tabsId
	 * @return
	 */
	@Delete({"delete from SVDS_AliasFile where fileId in(select fileId from svds_files where tabsId=#{tabsId})"})
	public Integer deleteByTabsId(@Param("tabsId") Integer tabsId);
	
	/**
	 * 根据标签Id集合删除文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_AliasFile",
			"where ",
			"<foreach collection='aliasIds' item='aliasId' open='(' separator='or' close=')'>",
			" aliasId in #{aliasId} ", "</foreach>", "</script>" })
	public Integer deleteByaliasIds(@Param("aliasIds") List<Integer> aliasIds);

	/**
	 * 根据标签Id文件Id集合删除文件标签
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_AliasFile",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>", "and aliasId=#{aliasId}", "</script>" })
	public Integer deleteAliasFile(@Param("ids") List<Integer> ids,
			@Param("aliasId") Integer aliasId);
	
	/**
	 * 根据用户Id删除文件标签
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			" from SVDS_AliasFile ",
			" where ",
			" aliasId in (select aliasId from svds_alias where ",
			"<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" userId in #{userId} ", "</foreach>"," )", "</script>" })
	public Integer deleteByUserId(@Param("ids") List<Integer> ids);

}
