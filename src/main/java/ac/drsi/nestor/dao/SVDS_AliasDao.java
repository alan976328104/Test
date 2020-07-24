package ac.drsi.nestor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.StatementType;

import ac.drsi.nestor.entity.SVDS_Alias;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.sql.SVDS_AliasSqlProvider;

@Mapper
public interface SVDS_AliasDao {
	/**
	 * 查询所有标签
	 * 
	 * @return
	 */
	@Select("select * from SVDS_alias  ORDER BY aliasId")
	@Results(id = "folMap", value = {
			@Result(column = "aliasId", property = "aliasId", javaType = Integer.class),
			@Result(column = "aliasName", property = "aliasName", javaType = String.class),
			@Result(column = "aliasDate", property = "aliasDate", javaType = String.class),
			@Result(column = "aliasType", property = "aliasType", javaType = Integer.class),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			@Result(column = "parentId", property = "parentId", javaType = Integer.class),
			@Result(column = "lv", property = "lv", javaType = Integer.class),
			@Result(column = "icon", property = "icon", javaType = String.class) })
	public List<SVDS_Alias> listAliasAll();

	/***
	 * 根据用户查询标签
	 * 
	 * @param aliasName
	 * @return
	 */
	@Select("select * from SVDS_alias where userId = #{userId} or userId=0 or aliastype=1 ORDER BY aliasId")
	@ResultMap("folMap")
	public List<SVDS_Alias> listAliasByUser(@Param("userId") Integer userId);

	/**
	 * 查询标签所有总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_alias")
	public Integer countAliasAll();

	/**
	 * 添加标签
	 * 
	 * @param SVDS_Alias
	 * @return
	 */

	@InsertProvider(type = SVDS_AliasSqlProvider.class, method = "insertAlias")
	@SelectKey(before = false, statement = "select max(aliasId) as aliasId from SVDS_alias", keyProperty = "aliasId", statementType = StatementType.STATEMENT, resultType = Integer.class)
	public Integer insertAlias(SVDS_Alias alias);

	/**
	 * 修改标签
	 * 
	 * @param SVDS_Alias
	 * @return
	 */
	@UpdateProvider(type = SVDS_AliasSqlProvider.class, method = "updateAlias")
	public Integer updateAlias(SVDS_Alias alias);

	/***
	 * 根据Id查询标签
	 * 
	 * @param AliasId
	 * @return
	 */
	@Select("select * from SVDS_alias where aliasId in (select aliasId from svds_aliasfile where aliasId in (select aliasId from SVDS_alias where  userId=#{userId} or aliasType=1) and fileId=#{fileId}) ORDER BY aliasId")
	@ResultMap("folMap")
	public List<SVDS_Alias> listAliasById(Map<String, Object> param);

	/***
	 * 根据Id查询标签
	 * 
	 * @param AliasId
	 * @return
	 */
	@Select("select * from SVDS_alias where aliasId =#{aliasId} ORDER BY aliasId")
	@ResultMap("folMap")
	public SVDS_Alias getAliasById(@Param("aliasId") Integer aliasId);

	/***
	 * 根据名称查询标签
	 * 
	 * @param aliasName
	 * @return
	 */
	@Select("select * from SVDS_alias where aliasName = #{aliasName} ORDER BY aliasId")
	@ResultMap("folMap")
	public SVDS_Alias getByName(@Param("aliasName") String aliasName);

	/***
	 * 根据名称和用户Id查询标签
	 * 
	 * @param aliasName
	 * @return
	 */
	@Select("select * from SVDS_alias where (aliasName like '%' || #{aliasName} || '%' and userId=#{userId} ) or (aliasName like '%' || #{aliasName} || '%' and aliasType=1) ORDER BY aliasId")
	@ResultMap("folMap")
	public List<SVDS_Alias> listAliasByUserName(
			@Param("aliasName") String aliasName,
			@Param("userId") Integer userId);

	/**
	 * 根据标签名称，用户Id和父级Id判断同济是否存在
	 * 
	 * @param aliasName
	 * @param userId
	 * @param parentId
	 * @return
	 */
	@Select("select * from SVDS_alias where aliasName = #{aliasName}  and userId=#{userId} and parentId=#{parentId} ORDER BY aliasId")
	@ResultMap("folMap")
	public SVDS_Alias getAliasByUserName(@Param("aliasName") String aliasName,
			@Param("userId") Integer userId, @Param("parentId") Integer parentId);

	/**
	 * 根据标签名称和用户Id查询
	 * 
	 * @param aliasName
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_alias where aliasName = #{aliasName}  and userId=#{userId} ORDER BY aliasId")
	@ResultMap("folMap")
	public SVDS_Alias getAliasByUserId(@Param("aliasName") String aliasName,
			@Param("userId") Integer userId);

	/**
	 * 根据用户Id删除标签
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_alias",
			"where ",
			"<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" userId in #{userId} ", "</foreach>", "</script>" })
	public Integer deleteByUserId(@Param("ids") List<Integer> ids);
	/**
	 * 根据标签名称查询个人标签
	 * 
	 * @param aliasName
	 * @return
	 */
	@Select("select * from SVDS_alias where aliasName = #{aliasName}  and aliasType=1 ORDER BY aliasId")
	@ResultMap("folMap")
	public SVDS_Alias getAliasByType(@Param("aliasName") String aliasName);

	/***
	 * 根据日期查询标签
	 * 
	 * @param AliasId
	 * @return
	 */
	@Select("select * from SVDS_alias where aliasDate =#{aliasDate}")
	@ResultMap("folMap")
	public SVDS_Alias getAliasByDate(String aliasDate);

	/***
	 * 根据Id集合查询标签
	 * 
	 * @param AliasId
	 * @return
	 */
	@Select({
			"<script>",
			"select * from SVDS_alias",
			"where aliasId in",
			"<foreach collection='ids' item='aliasId' open='(' separator=',' close=')'>",
			"#{aliasId}", "</foreach>", "</script>" })
	@ResultMap("folMap")
	public List<SVDS_Alias> listAliasByIds(@Param("ids") List<Integer> ids);

	/**
	 * 根据Id删除标签
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_alias",
			"where aliasId in",
			"<foreach collection='ids' item='aliasId' open='(' separator=',' close=')'>",
			"#{aliasId}", "</foreach>", "</script>" })
	public Integer deleteAlias(@Param("ids") List<Integer> ids);

	/**
	 * 根据标签名称和标签类型查询
	 * 
	 * @param alistname
	 * @param alistype
	 * @return
	 */
	@Select("select aliasid from svds_alias where aliasname = #{alistname} and aliastype = #{alistype}")
	public Integer ishavaByid(@Param("alistname") String alistname,
			@Param("alistype") Integer alistype);

	/**
	 * 给文件添加标签
	 * 
	 * @param aliasId
	 * @param fileId
	 * @param aliasdate
	 * @param userId
	 * @return
	 */
	@Insert({ "insert into SVDS_AliasFile(aliasId,fileId,afid,aliasdate) values(#{aliasId},#{fileId},nvl((select max(aliasId) from SVDS_AliasFile),0)+1,#{aliasdate})" })
	public Integer insertAliasFile(@Param("aliasId") Integer aliasId,
			@Param("fileId") Integer fileId,
			@Param("aliasdate") String aliasdate,
			@Param("userId") Integer userId);

	/**
	 * 添加标签
	 * 
	 * @param alias
	 * @return
	 */
	@Insert({ "insert into SVDS_Alias(aliasId,aliasname,aliasdate,aliastype,userid,parentid,lv,icon) values("
			+ "nvl((select max(aliasId) from SVDS_Alias),0)+1,#{aliasName},#{aliasDate},#{aliasType},#{user.userId},#{parentId},#{lv},#{icon})" })
	@Options(useGeneratedKeys = true, keyProperty = "aliasId")
	public Integer inserAliasByfiles(SVDS_Alias alias);

	/**
	 * 系统标签的总数
	 * 
	 * @return
	 */
	@Select("select count(*)-1 from svds_alias where aliastype=1")
	public Integer sysAliasCount();

	/**
	 * 个人标签的总数
	 * 
	 * @return
	 */
	@Select("select count(*)-1 from svds_alias where aliastype=2")
	public Integer perAliasCount();

	/**
	 * 创建个人标签最多的用户的个人标签数
	 * 
	 * @return
	 */
	@Select("select * from(select count(*) as bigcount from svds_alias where aliastype=2 group by userId order by bigcount desc) svds_alias WHERE rownum = 1")
	public Integer perAliasBigCount();
}
