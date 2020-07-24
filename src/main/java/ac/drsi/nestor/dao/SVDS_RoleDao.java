package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.StatementType;

import ac.drsi.nestor.entity.SVDS_Role;
import ac.drsi.nestor.sql.SVDS_RoleSqlProvider;

@Mapper
public interface SVDS_RoleDao {
	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	@Select("select * from SVDS_role where roleId not in(1,2,3,4) ORDER BY roleId")
	@Results(id = "roleMap", value = {
			@Result(column = "roleId", property = "roleId", javaType = Integer.class),
			@Result(column = "roleName", property = "roleName", javaType = String.class),
			@Result(column = "describe", property = "describe", javaType = String.class),
			@Result(column = "userId", property = "userId", javaType = Integer.class)})
	public List<SVDS_Role> getRoleAll();

	/**
	 * 根据名称模糊查询
	 * 
	 * @param rolename
	 * @return
	 */
	@Select("select * from SVDS_role where roleName like '%' || #{roleName} || '%' and roleId not in(1,2,3,4) ORDER BY roleId")
	@ResultMap("roleMap")
	public List<SVDS_Role> getRoleByNames(@Param("roleName") String roleName);

	/*
	 * 查询所有角色的总数
	 */
	@Select("select count(*) from SVDS_role")
	public Integer getRoleAllCount();

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	@InsertProvider(type = SVDS_RoleSqlProvider.class, method = "insertRole")
	@SelectKey(before = false, statement = "select max(roleId) as roleId from SVDS_role", keyProperty = "roleId", statementType = StatementType.STATEMENT, resultType = Integer.class)
	public Integer insertRole(SVDS_Role role);

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 */
	@UpdateProvider(type = SVDS_RoleSqlProvider.class, method = "updateRole")
	public Integer updateRole(SVDS_Role role);

	/***
	 * 根据Id查询角色
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("select * from SVDS_role where roleId =#{roleId} ORDER BY roleId")
	@ResultMap("roleMap")
	public SVDS_Role getRoleById(@Param("roleId") Integer roleId);

	/***
	 * 根据名称查询角色
	 * 
	 * @param roleName
	 * @return
	 */
	@Select("select * from SVDS_role where roleName =#{roleName} ORDER BY roleId")
	@ResultMap("roleMap")
	public SVDS_Role getRoleByName(@Param("roleName") String roleName);
	
	/***
	 * 根据专业管理员创建名称查询角色
	 * 
	 * @param roleName
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_role where roleName =#{roleName} ORDER BY roleId")
	@ResultMap("roleMap")
	public SVDS_Role getRoleByMajorName(@Param("roleName") String roleName);

	/***
	 * 根据用户Id查询角色
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("select * from SVDS_role where userId =#{userId} and roleId not in(1,2,3,4) ORDER BY roleId")
	@ResultMap("roleMap")
	public List<SVDS_Role> listRoleByUserId(@Param("userId") Integer userId);

	/**
	 * 根据用户Id修改角色
	 * 
	 * @param role
	 * @return
	 */
	@UpdateProvider(type = SVDS_RoleSqlProvider.class, method = "updateRoleByUserId")
	public Integer updateRoleByUserId(SVDS_Role role,Integer userId);

	/**
	 * 根据Id删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({ "delete from SVDS_role where roleId = #{roleId}" })
	public Integer deleteRoleByRoleId(@Param("roleId") Integer roleId);

	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_role",
			"where ",
			"<foreach collection='ids' item='roleId' open='(' separator='or' close=')'>",
			" roleId in #{roleId}", "</foreach>", "</script>" })
	public Integer deleteRole(@Param("ids") List<Integer> ids);
}
