package ac.drsi.nestor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.StatementType;

import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Major;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_Security;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SearchBean;
import ac.drsi.nestor.sql.SVDS_FilesSqlProvider;

@Mapper
public interface SVDS_FilesDao {
	/**
	 * 查询前20条文件
	 * 
	 * @return
	 */
	@Select("select * from SVDS_files where state=0 and  rownum<=20  ORDER BY fileDate desc ")
	@Results(id = "fileMap", value = {
			@Result(column = "fileId", property = "fileId", javaType = Integer.class),
			@Result(column = "fileName", property = "fileName", javaType = String.class),
			@Result(column = "fileUrl", property = "fileUrl", javaType = String.class),
			@Result(column = "fileDate", property = "fileDate", javaType = String.class),
			@Result(column = "fileVersion", property = "fileVersion", javaType = String.class),
			@Result(column = "format", property = "format", javaType = String.class),
			@Result(column = "securityId", property = "security", javaType = SVDS_Security.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_SecurityDao.getSecurityById")),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			// @Result(column = "userId", property = "newUserId", javaType =
			// Integer.class),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "tabsid", property = "tabsId", javaType = Integer.class),
			@Result(column = "menuId", property = "menu", javaType = SVDS_Menu.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MenuDao.getMenuById")),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "pId", property = "pId", javaType = Integer.class),
			// @Result(column = "majorId", property = "major", javaType =
			// SVDS_Major.class, one = @One(select =
			// "ac.drsi.nestor.dao.SVDS_MajorDao.getMajorById")),
			@Result(column = "location", property = "location", javaType = String.class),
			@Result(column = "abstracts", property = "abstracts", javaType = String.class),
			@Result(column = "fileSize", property = "fileSize", javaType = String.class),
			@Result(column = "fileId", property = "alias", many = @Many(select = "ac.drsi.nestor.dao.SVDS_AliasDao.listAliasById")) })
	public List<SVDS_Files> getFilesAll();

	/**
	 * 根据选项卡Id，密级，文件名称，用户Id查询
	 * 
	 * @param tabsId
	 * @param securityId
	 * @param fileName
	 * @param userId
	 * @return
	 */
	@Select("select fileId,fileName,fileUrl,fileDate,fileVersion,format,securityId,userId,state,tabsid,menuId,remark,pId,location,abstracts,fileSize,#{userId} as newUserId from SVDS_files where state=0 and tabsId=#{tabsId} and securityId<=#{securityId} and fileName like '%' || #{fileName} || '%' ORDER BY cast(REGEXP_SUBSTR(fileName, '^[0-9]+') as int), nlssort(fileName, 'NLS_SORT=SCHINESE_PINYIN_M'),filedate desc ")
	@Results({
			@Result(column = "fileId", property = "fileId", javaType = Integer.class),
			@Result(column = "fileName", property = "fileName", javaType = String.class),
			@Result(column = "fileUrl", property = "fileUrl", javaType = String.class),
			@Result(column = "fileDate", property = "fileDate", javaType = String.class),
			@Result(column = "fileVersion", property = "fileVersion", javaType = String.class),
			@Result(column = "format", property = "format", javaType = String.class),
			@Result(column = "securityId", property = "security", javaType = SVDS_Security.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_SecurityDao.getSecurityById")),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			@Result(column = "newUserId", property = "newUserId", javaType = Integer.class),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "tabsid", property = "tabsId", javaType = Integer.class),
			@Result(column = "menuId", property = "menu", javaType = SVDS_Menu.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MenuDao.getMenuById")),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "pId", property = "pId", javaType = Integer.class),
			// @Result(column = "majorId", property = "major", javaType =
			// SVDS_Major.class, one = @One(select =
			// "ac.drsi.nestor.dao.SVDS_MajorDao.getMajorById")),
			@Result(column = "location", property = "location", javaType = String.class),
			@Result(column = "abstracts", property = "abstracts", javaType = String.class),
			@Result(column = "fileSize", property = "fileSize", javaType = String.class),
			@Result(column = "fileId=fileId,userId=newUserId", property = "alias", many = @Many(select = "ac.drsi.nestor.dao.SVDS_AliasDao.listAliasById")) })
	public List<SVDS_Files> getFilesByTabsid(@Param("tabsId") Integer tabsId,
			@Param("securityId") Integer securityId,
			@Param("fileName") String fileName, @Param("userId") Integer userId);

	/**
	 * 根据选项卡Id，密级，文件名称，用户Id排序查询
	 * 
	 * @param tabsId
	 * @param securityId
	 * @param fileName
	 * @param userId
	 * @param sortName
	 * @return
	 */
	@Select("select fileId,fileName,fileUrl,fileDate,fileVersion,format,securityId,userId,state,tabsid,menuId,remark,pId,location,abstracts,fileSize,#{userId} as newUserId from SVDS_files where state=0 and tabsId=#{tabsId} and securityId<=#{securityId} and fileName like '%' || #{fileName} || '%' ORDER BY cast(REGEXP_SUBSTR(fileName, '^[0-9]+') as int) ${sortName}, nlssort(fileName, 'NLS_SORT=SCHINESE_PINYIN_M') ${sortName} ,fileDate desc")
	@Results({
			@Result(column = "fileId", property = "fileId", javaType = Integer.class),
			@Result(column = "fileName", property = "fileName", javaType = String.class),
			@Result(column = "fileUrl", property = "fileUrl", javaType = String.class),
			@Result(column = "fileDate", property = "fileDate", javaType = String.class),
			@Result(column = "fileVersion", property = "fileVersion", javaType = String.class),
			@Result(column = "format", property = "format", javaType = String.class),
			@Result(column = "securityId", property = "security", javaType = SVDS_Security.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_SecurityDao.getSecurityById")),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			@Result(column = "newUserId", property = "newUserId", javaType = Integer.class),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "tabsid", property = "tabsId", javaType = Integer.class),
			@Result(column = "menuId", property = "menu", javaType = SVDS_Menu.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MenuDao.getMenuById")),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "pId", property = "pId", javaType = Integer.class),
			// @Result(column = "majorId", property = "major", javaType =
			// SVDS_Major.class, one = @One(select =
			// "ac.drsi.nestor.dao.SVDS_MajorDao.getMajorById")),
			@Result(column = "location", property = "location", javaType = String.class),
			@Result(column = "abstracts", property = "abstracts", javaType = String.class),
			@Result(column = "fileSize", property = "fileSize", javaType = String.class),
			@Result(column = "fileId=fileId,userId=newUserId", property = "alias", many = @Many(select = "ac.drsi.nestor.dao.SVDS_AliasDao.listAliasById")) })
	public List<SVDS_Files> listFilesByTabsidSort(
			@Param("tabsId") Integer tabsId,
			@Param("securityId") Integer securityId,
			@Param("fileName") String fileName,
			@Param("userId") Integer userId, @Param("sortName") String sortName);

	/**
	 * 根据选项卡id查询
	 * 
	 * @param tabsId
	 * @return
	 */
	@Select("select * from SVDS_files where state=0 and tabsId=#{tabsId} ")
	@ResultMap("fileMap")
	public List<SVDS_Files> listFilesByTabsId(@Param("tabsId") Integer tabsId);

	/**
	 * 根据选项卡Id和菜单Id 查询未删除的文件
	 * 
	 * @param tabsId
	 * @param menuId
	 * @return
	 */
	@Select("select * from SVDS_files where state=0 and tabsId=#{tabsId} and menuId=#{menuId}")
	@ResultMap("fileMap")
	public List<SVDS_Files> listFilesByTabIdMenuId(
			@Param("tabsId") Integer tabsId, @Param("menuId") Integer menuId);

	/**
	 * 根据已删除的选项卡Id查询文件
	 * 
	 * @param tabsId
	 * @return
	 */
	@Select("select * from SVDS_files where tabsId=#{tabsId} ")
	@ResultMap("fileMap")
	public List<SVDS_Files> listFilesByDelTabsId(@Param("tabsId") Integer tabsId);
	
	/**
	 * 根据已删除的选项卡Id删除文件
	 * 
	 * @param tabsId
	 * @return
	 */
	@Delete({"delete  from SVDS_files where tabsId=#{tabsId}"})
	public Integer delFileByTabId(@Param("tabsId") Integer tabsId);

	/**
	 * 根据选项卡id集合查询
	 * 
	 * @param ids
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_files",
			" where state=0 and  ",
			"<foreach collection='ids' item='tabsId' open='(' separator='or' close=')'>",
			" tabsId in #{tabsId} ", "</foreach>", "</script>" })
	@ResultMap("fileMap")
	public List<SVDS_Files> listFilesByTabsIds(@Param("ids") List<Integer> ids);

	/**
	 * 根据文件名称模糊查询
	 * 
	 * @param fileName
	 * @return
	 */
	@Select("select * from SVDS_files where fileName like '%' || #{fileName} || '%' and state=0 ORDER BY fileId")
	@ResultMap("fileMap")
	public List<SVDS_Files> listFilesByName(@Param("fileName") String fileName);

	/**
	 * 根据文件名称和选项卡Id判断文件是否存在(未删除)
	 * 
	 * @param fileName
	 *            文件名称
	 * @param tabsId
	 *            选项卡Id
	 * @return
	 */
	@Select("select * from SVDS_files where fileName like '%' || #{fileName} and tabsId=#{tabsId} and state=0 order by filedate  desc")
	@ResultMap("fileMap")
	public List<SVDS_Files> getFilesByName(@Param("fileName") String fileName,
			@Param("tabsId") Integer tabsId);

	/**
	 * 根据文件名称和选项卡Id判断文件是否存在(未删除)
	 * 
	 * @param fileName
	 *            文件名称
	 * @param tabsId
	 *            选项卡Id
	 * @return
	 */
	@Select("select * from SVDS_files where fileName like '%' || #{fileName} and tabsId=#{tabsId} and fileVersion=#{fileVersion} and state=0 order by filedate  desc")
	@ResultMap("fileMap")
	public List<SVDS_Files> getFilesByNameVersion(
			@Param("fileName") String fileName,
			@Param("tabsId") Integer tabsId,
			@Param("fileVersion") String fileVersion);

	/**
	 * 根据文件名称和选项卡Id判断文件是否存在(全部文件)
	 * 
	 * @param fileName
	 *            文件名称
	 * @param tabsId
	 *            选项卡Id
	 * @return
	 */
	@Select("select * from SVDS_files where fileName like '%' || #{fileName} and tabsId=#{tabsId}  order by filedate  desc")
	@ResultMap("fileMap")
	public List<SVDS_Files> getFilesByNameAll(
			@Param("fileName") String fileName, @Param("tabsId") Integer tabsId);

	/**
	 * 根据文件名称和选项卡Id查询最大版本
	 * 
	 * @param fileName
	 *            文件名称
	 * @param tabsId
	 *            选项卡Id
	 * @return
	 */
	@Select("select max(substr(fileVersion,instr(fileVersion,'.')-1,1)) from SVDS_files where fileName like '%' || #{fileName} and tabsId=#{tabsId} and state=0  order by filedate  desc")
	public Integer getFilesByFileVersion(@Param("fileName") String fileName,
			@Param("tabsId") Integer tabsId);

	/**
	 * 根据文件路径查询
	 * 
	 * @param fileUrl
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_files",
			"where ",
			"<foreach collection='fileUrls' item='fileUrl' open='(' separator='or' close=')'>",
			" fileUrl in #{fileUrl} ", "</foreach>", "ORDER BY fileId",
			"</script>" })
	@ResultMap("fileMap")
	public List<SVDS_Files> getFilesByUrl(
			@Param("fileUrls") List<String> fileUrl);

	/**
	 * 多条件查询
	 * 
	 * @param files
	 * @return
	 */
	@SelectProvider(type = SVDS_FilesSqlProvider.class, method = "selectWhitFilesSql")
	@ResultMap("fileMap")
	public List<SVDS_Files> selectWhitFilesSql(@Param("file") SVDS_Files file,
			Map<String, Object> param);

	@SelectProvider(type = SVDS_FilesSqlProvider.class, method = "selectFilesSql")
	@ResultMap("fileMap")
	public List<SVDS_Files> selectFilesSql(List<SearchBean> bean);

	/**
	 * 查询文件所有总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_files")
	public Integer getFilesAllCount();

	/**
	 * 查询所有文件格式
	 * 
	 * @return
	 */
	@Select("select distinct(format) from svds_files order by format")
	public List<String> listFormat();

	/**
	 * 添加文件
	 * 
	 * @param SVDS_Files
	 * @return
	 */
	@InsertProvider(type = SVDS_FilesSqlProvider.class, method = "insertFiles")
	@SelectKey(before = false, statement = "select max(fileId) as fileId from SVDS_files", keyProperty = "fileId", statementType = StatementType.STATEMENT, resultType = Integer.class)
	public Integer insertFiles(SVDS_Files files);

	/**
	 * 修改文件
	 * 
	 * @param SVDS_Files
	 * @return
	 */
	@UpdateProvider(type = SVDS_FilesSqlProvider.class, method = "updateFiles")
	public Integer updateFiles(SVDS_Files files);

	/**
	 * 修改备注字段
	 * 
	 * @param Remark
	 * @return
	 */
	@Update("update svds_files set remark=#{remark} where fileId=#{fileId}")
	public Integer updateRemark(@Param("remark") String remark,
			@Param("fileId") Integer fileId);

	/**
	 * 清空摘要字段
	 * 
	 * @return
	 */
	@Update("update svds_files set abstracts=''")
	public Integer emptyAbstracts();

	/***
	 * 根据Id查询文件
	 * 
	 * @param fileId
	 * @return
	 */
	@Select("select * from SVDS_files where fileId =#{fileId} ORDER BY fileId")
	@ResultMap("fileMap")
	public SVDS_Files getFilesById(@Param("fileId") Integer fileId);

	/***
	 * 根据父级Id和文件日期倒叙查询第一条数据
	 * 
	 * @param pId
	 *            父级Id
	 * @return
	 */
	@Select("select * from (select  * from svds_files where pId=#{pId} and state=0  order by filedate  desc) where ROWNUM <=1")
	@ResultMap("fileMap")
	public SVDS_Files getFilesByParentId(@Param("pId") Integer pId);

	/***
	 * 根据父级Id查询并更新父级Id
	 * 
	 * @param pId
	 *            父级Id
	 * @return
	 */
	@Update("update svds_files set pId=#{newPId} where pId=#{oldPId}")
	public Integer updateFilesByPId(@Param("oldPId") Integer oldPId,
			@Param("newPId") Integer newPId);

	/***
	 * 根据密级Id查询文件
	 * 
	 * @param fileId
	 * @return
	 */
	@Select("select * from SVDS_files where securityId =#{securityId} and tabsId=#{tabsId} and state=0 ORDER BY fileId")
	@ResultMap("fileMap")
	public List<SVDS_Files> listBySecurityId(
			@Param("securityId") Integer securityId,
			@Param("tabsId") Integer tabsId);

	/***
	 * 根据Id批量查询文件
	 * 
	 * @param fileId
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_files",
			"where ",
			"<foreach collection='fileIds' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>", "ORDER BY fileId",
			"</script>" })
	@ResultMap("fileMap")
	public List<SVDS_Files> getFilesByIds(
			@Param("fileIds") List<Integer> fileIds);

	/***
	 * 根据Id批量查询文件
	 * 
	 * @param fileId
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_files",
			" where ",
			"<foreach collection='fileIds' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>",
			"and fileName like '%' || #{fileName} || '%' ", " and state=0 ",
			"ORDER BY fileId", "</script>" })
	@ResultMap("fileMap")
	public List<SVDS_Files> getFilesByIdsAndFileName(
			@Param("fileIds") List<Integer> fileIds,
			@Param("fileName") String fileName);

	/**
	 * 根据文件Id批量日期排序文件
	 * 
	 * @param fileIds
	 * @param sortOrder
	 * @param userId
	 * @return
	 */
	@Select({
			"<script>",
			"select fileId,fileName,fileUrl,fileDate,fileVersion,format,securityId,userId,state,tabsid,menuId,remark,pId,majorId,location,abstracts,fileSize,#{userId} as newUserId from SVDS_files",
			"where ",
			"<foreach collection='fileIds' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>", "ORDER BY fileDate ",
			"${sortOrder}", "</script>" })
	@Results({
			@Result(column = "fileId", property = "fileId", javaType = Integer.class),
			@Result(column = "fileName", property = "fileName", javaType = String.class),
			@Result(column = "fileUrl", property = "fileUrl", javaType = String.class),
			@Result(column = "fileDate", property = "fileDate", javaType = String.class),
			@Result(column = "fileVersion", property = "fileVersion", javaType = String.class),
			@Result(column = "format", property = "format", javaType = String.class),
			@Result(column = "securityId", property = "security", javaType = SVDS_Security.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_SecurityDao.getSecurityById")),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			@Result(column = "newUserId", property = "newUserId", javaType = Integer.class),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "tabsid", property = "tabsId", javaType = Integer.class),
			@Result(column = "menuId", property = "menu", javaType = SVDS_Menu.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MenuDao.getMenuById")),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "pId", property = "pId", javaType = Integer.class),
			// @Result(column = "majorId", property = "major", javaType =
			// SVDS_Major.class, one = @One(select =
			// "ac.drsi.nestor.dao.SVDS_MajorDao.getMajorById")),
			@Result(column = "location", property = "location", javaType = String.class),
			@Result(column = "abstracts", property = "abstracts", javaType = String.class),
			@Result(column = "fileSize", property = "fileSize", javaType = String.class),
			@Result(column = "fileId=fileId,userId=newUserId", property = "alias", many = @Many(select = "ac.drsi.nestor.dao.SVDS_AliasDao.listAliasById")) })
	public List<SVDS_Files> listFilesByIdsSort(
			@Param("fileIds") List<Integer> fileIds,
			@Param("sortOrder") String sortOrder,
			@Param("userId") Integer userId);

	/**
	 * 加入回收站
	 * 
	 * @param ids
	 * @return
	 */
	@Update({
			"<script>",
			"update SVDS_files set state = #{state} where ",
			"<foreach item='fileId' collection='ids' open='(' separator='or' close=')'>",
			"  fileId in #{fileId}", "</foreach>", "</script>" })
	public Integer recycleFiles(@Param("ids") List<Integer> ids,
			@Param("state") Integer state);

	/**
	 * 根据选项卡Id查询未删除的文件
	 * 
	 * @param ids
	 * @return
	 */
	@Select({ "select * from SVDS_files where tabsId=#{tabId} and state=0" })
	public List<SVDS_Files> listFilesByTabId(@Param("tabId") Integer tabId);

	/**
	 * 根据菜单Id查询未删除的文件
	 * 
	 * @param ids
	 * @return
	 */
	@Select({ "select * from SVDS_files where menuId=#{menuId} and state=0" })
	public List<SVDS_Files> listFilesByMenuId(@Param("menuId") Integer menuId);

	/**
	 * 根据菜单Id查询文件
	 * 
	 * @param ids
	 * @return
	 */
	@Select({ "select * from SVDS_files where menuId=#{menuId}" })
	public List<SVDS_Files> listFilesByMenuIdAll(@Param("menuId") Integer menuId);

	/**
	 * 根据选项卡Id查询文件
	 * 
	 * @param ids
	 * @return
	 */
	@Select({ "select * from SVDS_files where tabsId=#{tabId} " })
	public List<SVDS_Files> listFilesByTabIdAll(@Param("tabId") Integer tabId);

	/**
	 * 根据Id删除文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_files",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId}", "</foreach>", "</script>" })
	public Integer deleteFiles(@Param("ids") List<Integer> ids);

	/**
	 * 查询某一年的数据上传量
	 * 
	 * @return
	 */
	@Select({ "select extract(month from to_date(filedate,'yyyy-MM-dd HH24:mi:ss'))||'' as valueName,count(*)||'' as countNum from svds_files where to_char(TO_DATE(filedate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')=#{yearValue} group by extract(month from to_date(filedate,'yyyy-MM-dd HH24:mi:ss')) order by extract(month from to_date(filedate,'yyyy-MM-dd HH24:mi:ss')) " })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listMonth(@Param("yearValue") String yearValue);
	/**
	 * 查询某专业一年的数据上传量
	 * @param menuIds
	 * @return
	 */
	@Select({ "<script>",
		"select extract(month from to_date(filedate,'yyyy-MM-dd HH24:mi:ss'))||'' as valueName,count(*)||'' as countNum from svds_files where to_char(TO_DATE(filedate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')=#{yearValue}",
		"and","<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" menuId in #{menuId}", "</foreach>",
	" group by extract(month from to_date(filedate,'yyyy-MM-dd HH24:mi:ss')) order by extract(month from to_date(filedate,'yyyy-MM-dd HH24:mi:ss')) ","</script>" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listMonthByMenuId(@Param("menuIds") List<Integer> menuIds,@Param("yearValue") String yearValue);
	/**
	 * 查询各类文档占比图
	 * 
	 * @return
	 */
	@Select({ "select format as valueName,count(*) as countNum from svds_files group by format" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listformat();
	
	/**
	 * 查询某专业下各类文档占比图
	 * @param menuIds
	 * @return
	 */
	@Select({
		"<script>",
		"select format as valueName,count(*) as countNum from svds_files",
		"where ",
		"<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" menuId in #{menuId} ", "</foreach>", "group by format ",
		 "</script>" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listformatByMenuId(@Param("menuIds") List<Integer> menuIds);
	/**
	 * 查询文件密级进行文档占比图
	 * 
	 * @return
	 */
	@Select({ "select count(f.securityid) as countNum,s.securityname as valueName from svds_files f left join svds_security s on f.securityid=s.securityid group by f.securityid,s.securityname  order by f.securityId " })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listSecurity();
	/**
	 * 查询某专业下文件密级进行文档占比图
	 * @param menuIds
	 * @return
	 */
	@Select({
		"<script>",
		"select count(f.securityid) as countNum,s.securityname as valueName from svds_files f left join svds_security s on f.securityid=s.securityid",
		"where ",
		"<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" f.menuId in #{menuId} ", "</foreach>", "group by f.securityid,s.securityname  order by f.securityId ",
		 "</script>" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listSecurityByMenuId(@Param("menuIds") List<Integer> menuIds);

	/**
	 * 系统标签数目最多的例题
	 * @return
	 */
	@Select("select * from svds_menu where id in (select menuId from svds_files where fileId in(select fileId from svds_aliasfile group by fileId) and rownum=1 group by menuId )")
	@Results({
		@Result(column = "text", property = "text", javaType = String.class) })
	public SVDS_Menu aliasManyByFile();
	
	/**
	 * TOP5最常访问文件的点击次数
	 * 
	 * @return
	 */
	@Select({ " select f.fileid as fileId, f.fileName as valueName,v.counts as countNum  from svds_files f right join svds_visited v on v.fileId=f.fileId  where v.fileId in(select fileId from (select * from svds_visited  order by counts desc ) where rownum<6) and rownum<6 order by v.counts desc" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) ,
			@Result(column = "fileId", property = "id", javaType = String.class) })
	public List<ChartEntity> listVisited();
	
	/**
	 * 某专业下TOP5最常访问文件的点击次数
	 * @param menuIds
	 * @return
	 */
	@Select({
		"<script>",
		"select f.fileid as fileId, f.fileName as valueName,v.counts as countNum  from svds_files f right join svds_visited v on v.fileId=f.fileId  where v.fileId in(select fileId from (select * from svds_visited  order by counts desc ) where rownum&lt;6) ",
		" and  ",
		"<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" menuId in #{menuId} ", "</foreach>", " and rownum&lt;6 order by v.counts desc ",
		 "</script>" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) ,
			@Result(column = "fileId", property = "id", javaType = String.class) })
	public List<ChartEntity> listVisitedByMenuId(@Param("menuIds") List<Integer> menuIds);
	/**
	 * 某专业下的文档总数
	 * @param menuIds
	 * @return
	 */
	@Select({
		"<script>",
		"select count(*) from svds_files",
		"where ",
		"<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" menuId in #{menuId} ", "</foreach>", 
		 "</script>" })
	public Integer fileCountByMenuId(@Param("menuIds") List<Integer> menuIds);
	
	
	/**
	 * 某专业下各例题文档数占比
	 * @param menuIds
	 * @return
	 */
	@Select({
		"<script>",
		"select m.text as valueName,menuId,count(*) as countNum from svds_files f left join svds_menu m on m.id=f.menuId",
		"where ",
		"<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" f.menuId in #{menuId} ", "</foreach>", "group by f.menuId,m.text",
		 "</script>" })
	@Results({
		@Result(column = "valueName", property = "name", javaType = String.class),
		@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listfileCountByMenuId(@Param("menuIds") List<Integer> menuIds);
	/**
	 * 某专业下各例题数据量占比
	 * @param menuIds
	 * @return
	 */
	@Select({
		"<script>",
		"select m.text as valueName,menuId,sum(filesize) as countNum from svds_files f left join svds_menu m on m.id=f.menuId",
		"where ",
		"<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" f.menuId in #{menuId} ", "</foreach>", "group by f.menuId,m.text",
		 "</script>" })
	@Results({
		@Result(column = "valueName", property = "name", javaType = String.class),
		@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listfileSizeByMenuId(@Param("menuIds") List<Integer> menuIds);
	
	/**
	 * 系统年新装载数据量统计
	 * @return
	 */
	@Select({ "select to_char(TO_DATE(filedate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy') as valueName,count(to_char(TO_DATE(filedate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')) as countNum from svds_files   group by to_char(TO_DATE(filedate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listFileCountAll();
	
	/**
	 * 某专业的年新装载数据量统计
	 * @param menuIds
	 * @return
	 */
	@Select({
		"<script>",
		"select to_char(TO_DATE(filedate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy') as valueName,count(to_char(TO_DATE(filedate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')) as countNum from svds_files ",
		"where ",
		"<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" menuId in #{menuId} ", "</foreach>", "group by to_char(TO_DATE(filedate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')",
		 "</script>" })
	@Results({
			@Result(column = "valueName", property = "name", javaType = String.class),
			@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> listFileCountAllByMenuId(@Param("menuIds") List<Integer> menuIds);
	
	/**
	 * 某专业的系统标签总数
	 * @param menuIds
	 * @return
	 */
	@Select({
		"<script>",
		"select count(* ) from svds_files  where fileId in(select fileId from svds_aliasfile where aliasId in (select aliasId from svds_alias where aliasId in (select aliasId from svds_aliasfile group by aliasId ) and aliastype=1) group by fileId)",
		"and ",
		"<foreach collection='menuIds' item='menuId' open='(' separator='or' close=')'>",
		" menuId in #{menuId} ", "</foreach>", 
		 "</script>" })
	public Integer sysAliasCountByMenuId(@Param("menuIds") List<Integer> menuIds);
}
