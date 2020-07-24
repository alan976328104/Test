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
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.StatementType;

import ac.drsi.nestor.entity.SVDS_Role;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.sql.SVDS_UserSqlProvider;

@Mapper
public interface SVDS_UserDao {
	/**
	 * 查询所有用户(不包括三元)
	 * 
	 * @return
	 */
	@Select("select * from SVDS_users where state is null ORDER BY USERID ")
	@Results(id = "userMap", value = {
			@Result(column = "USERID", property = "userId", javaType = Integer.class),
			@Result(column = "id", property = "id", javaType = String.class),
			@Result(column = "USERNAME", property = "username", javaType = String.class),
			@Result(column = "SEX", property = "sex", javaType = Integer.class),
			@Result(column = "code", property = "code", javaType = String.class),
			@Result(column = "cardno", property = "cardno", javaType = String.class),
			@Result(column = "keyno", property = "keyno", javaType = String.class),
			@Result(column = "deptname", property = "deptname", javaType = String.class),
			//@Result(column = "majorId", property = "major", javaType = SVDS_Major.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MajorDao.getMajorById")),
			@Result(column = "secretlevel", property = "secretlevel", javaType = String.class),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "roleId", property = "role", javaType = SVDS_Role.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_RoleDao.getRoleById")) })
	public List<SVDS_User> getUserAll();

	/**
	 * 根据姓名模糊查询
	 * 
	 * @param username
	 * @return
	 */
	@Select("select * from SVDS_users where username like '%' || #{username} || '%' ORDER BY USERID")
	@ResultMap("userMap")
	public List<SVDS_User> getUserByName(@Param("username") String username);
	
	/**
	 * 查询系统管理员创建的用户
	 * @return
	 */
	@Select("select * from SVDS_users where usId=2 ORDER BY USERID")
	@ResultMap("userMap")
	public List<SVDS_User> listUserBySys();

	/**
	 * 查询所有用户的总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_users")
	public Integer getUserAllCount();

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@InsertProvider(type = SVDS_UserSqlProvider.class, method = "insertUser")
	@SelectKey(before = false, statement = "select max(userId) as userId from SVDS_users", keyProperty = "userId", statementType = StatementType.STATEMENT, resultType = Integer.class)
	public Integer insertUser(SVDS_User user);

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@UpdateProvider(type = SVDS_UserSqlProvider.class, method = "updateUser")
	public Integer updateUser(SVDS_User user);

	/***
	 * 根据用户Id查询用户
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_users where userId =#{userId} ORDER BY USERID")
	@ResultMap("userMap")
	public SVDS_User getUserById(@Param("userId") Integer userId);

	/***
	 * 根据Id查询用户是否存在
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from SVDS_users where id =#{id} ORDER BY USERID")
	@ResultMap("userMap")
	public SVDS_User existUserById(@Param("id") String id);

	/***
	 * 根据角色Id查询用户
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_users where roleId =#{roleId} ORDER BY USERID")
	@ResultMap("userMap")
	public List<SVDS_User> listUserByRoleId(@Param("roleId") Integer roleId);

	/***
	 * 根据部门名称模糊查询用户
	 * 
	 * @param deptname
	 * @return
	 */
	@Select("select * from SVDS_users where deptname like '%' ||  #{deptname} || '%' ORDER BY USERID")
	@ResultMap("userMap")
	public List<SVDS_User> getUserByDeptName(@Param("deptname") String deptname);

	/***
	 * 根据部门名称查询用户
	 * 
	 * @param deptname
	 * @return
	 */
	@Select("select * from SVDS_users where deptname = #{deptname} and username like '%' || #{username} || '%' and state is null  ORDER BY USERID")
	@ResultMap("userMap")
	public List<SVDS_User> listUserByDeptName(
			@Param("deptname") String deptname,
			@Param("username") String username);

	/***
	 * 根据密级查询用户
	 * 
	 * @param secretlevel
	 * @return
	 */
	@Select({
			"<script>",
			"select",
			" * from",
			"SVDS_users",
			"where deptname like '%' ||  #{deptname} || '%' and userId not in ( #{userId} ) and ",
			"<foreach collection='vals' item='secretlevel' open='(' separator='or' close=')'>",
			" secretlevel in #{secretlevel}",
			"</foreach>",
			" and  ",
			"<foreach collection='roleIds' item='roleId' open='(' separator='or' close=')'>",
			" roleId in #{roleId}", "</foreach>", "</script>" })
	@ResultMap("userMap")
	public List<SVDS_User> listUserBySecretlevel(
			@Param("deptname") String deptname,
			@Param("vals") List<String> secretlevel,
			@Param("userId") Integer userId,
			@Param("roleIds") List<Integer> roleIds);

	/**
	 * 分配用户
	 * 
	 * @param ids
	 * @return
	 */
	@Update({
			"<script>",
			"update",
			"SVDS_users",
			"set roleId=#{roleId}",
			" where ",
			"<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" userId in #{userId}", "</foreach>", "</script>" })
	public Integer updateRoleUser(@Param("roleId") Integer roleId,
			@Param("ids") List<Integer> ids);

	/**
	 * 根据用户id集合删除用户
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_users",
			"where ",
			"<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" userId in #{userId}", "</foreach>", "</script>" })
	public Integer deleteUser(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据用户id删除用户
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({"delete from svds_users where userId =#{userId}" })
	public Integer deleteUserById(@Param("userId") Integer userId);
	
}
