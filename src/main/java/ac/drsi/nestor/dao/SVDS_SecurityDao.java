package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.SVDS_Security;

@Mapper
public interface SVDS_SecurityDao {
	/**
	 * 查询所有密级
	 * 
	 * @return
	 */
	@Select("select * from SVDS_security ORDER BY securityID")
	@Results({
			@Result(column = "securityID", property = "securityId", javaType = Integer.class),
			@Result(column = "securityNAME", property = "securityName", javaType = String.class) })
	public List<SVDS_Security> getSecurityAll();

	/**
	 * 根据密级名称模糊查询
	 * 
	 * @param securityname
	 * @return
	 */
	@Select("select * from SVDS_security where securityName like '%' || #{securityName} || '%' ORDER BY securityID")
	@Results({
			@Result(column = "securityID", property = "securityId", javaType = Integer.class),
			@Result(column = "securityNAME", property = "securityName", javaType = String.class) })
	public List<SVDS_Security> listSecurityByName(
			@Param("securityName") String securityName);

	/**
	 * 查询密级所有总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_security")
	public Integer getSecurityAllCount();

	/**
	 * 添加密级
	 * 
	 * @param security
	 * @return
	 */
	@Insert({ "insert into SVDS_security(securityId,securityName) values(nvl((select max(securityId) from SVDS_security),0)+1,#{securityName})" })
	public Integer insertSecurity(SVDS_Security security);

	/**
	 * 修改密级
	 * 
	 * @param security
	 * @return
	 */
	@Update("update SVDS_security set securityName=#{securityName} where securityId=#{securityId}")
	public Integer updateSecurity(SVDS_Security security);

	/***
	 * 根据Id查询密级
	 * 
	 * @param securityId
	 * @return
	 */
	@Select("select * from SVDS_security where securityId =#{securityId} ORDER BY securityID")
	@Results({
			@Result(column = "securityID", property = "securityId", javaType = Integer.class),
			@Result(column = "securityNAME", property = "securityName", javaType = String.class) })
	public SVDS_Security getSecurityById(Integer securityId);

	/***
	 * 根据密级名称查询密级
	 * 
	 * @param securityId
	 * @return
	 */
	@Select("select * from SVDS_security where securityName =#{securityName} ORDER BY securityID")
	@Results({
			@Result(column = "securityID", property = "securityId", javaType = Integer.class),
			@Result(column = "securityNAME", property = "securityName", javaType = String.class) })
	public SVDS_Security getSecurityByName(
			@Param("securityName") String securityName);

	/**
	 * 根据Id删除密级
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_security",
			"where ",
			"<foreach collection='ids' item='securityId' open='(' separator='or' close=')'>",
			" securityId in #{securityId}", "</foreach>", "</script>" })
	public Integer deleteSecurity(@Param("ids") List<Integer> ids);
}
