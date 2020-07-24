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
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.SVDS_UserAll;
import ac.drsi.nestor.sql.SVDS_UserAllSqlProvider;

@Mapper
public interface SVDS_UserAllDao {
	/**
	 * 查询所有用户
	 * @return
	 */
	@Select("select * from SVDS_userAll ORDER BY userAllId ")
	@Results(id = "userMap", value = {
			@Result(column = "USERAllID", property = "userAllId", javaType = Integer.class),
			@Result(column = "id", property = "id", javaType = String.class),
			@Result(column = "USERNAME", property = "username", javaType = String.class),
			@Result(column = "code", property = "code", javaType = String.class),
			@Result(column = "cardno", property = "cardno", javaType = String.class),
			@Result(column = "keyno", property = "keyno", javaType = String.class),
			@Result(column = "deptname", property = "deptname",javaType = String.class),
			@Result(column = "secretlevel", property = "secretlevel", javaType = String.class)})
	public List<SVDS_UserAll> getUserAll();

	/**
	 * 根据姓名模糊查询
	 * 
	 * @param username
	 * @return
	 */
	@Select("select * from SVDS_userAll where username like '%' || #{username} || '%' ORDER BY userAllId")
	@ResultMap("userMap")
	public List<SVDS_UserAll> getUserByName(@Param("username") String username);

	/**
	 * 查询所有用户的总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_userAll")
	public Integer getUserAllCount();

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@InsertProvider(type = SVDS_UserAllSqlProvider.class, method = "insertUserAll")
	public Integer insertUserAll(SVDS_UserAll user);


	/***
	 * 根据用户Id查询用户
	 * 
	 * @param userAllId
	 * @return
	 */
	@Select("select * from SVDS_userAll where userAllId =#{userAllId} ORDER BY userAllId")
	@ResultMap("userMap")
	public SVDS_UserAll getUserAllById(@Param("userAllId")Integer userAllId);
	
	/***
	 * 根据部门名称模糊查询用户
	 * 
	 * @param deptname
	 * @return
	 */
	@Select("select * from SVDS_userAll where deptname like '%' ||  #{deptname} || '%' ORDER BY userAllId")
	@ResultMap("userMap")
	public List<SVDS_UserAll> getUserByDeptName(@Param("deptname")String deptname);
	
	/***
	 * 根据部门名称查询用户
	 * 
	 * @param deptname
	 * @return
	 */
	@Select("select * from SVDS_userAll where deptname like '%' ||  #{deptname} || '%' ORDER BY userAllId")
	@ResultMap("userMap")
	public List<SVDS_UserAll> listUserByDeptName(@Param("deptname")String deptname);
	
	/***
	 * 根据Id查询用户是否存在
	 * 
	 * @param id
	 * @return
	 */
	@Select("select * from SVDS_userAll where id =#{id} ")
	@ResultMap("userMap")
	public SVDS_UserAll existUserById(@Param("id")String id);
	
	/**
	 * 删除全部用户
	 * 
	 * @return
	 */
	@Delete({"delete from svds_userAll where 1=1" })
	public Integer deleteUserAll();
	/**
	 * 同步用户密级
	 * 
	 * @param ids
	 * @return
	 */
	@Update({"update svds_users a set secretlevel=(select SECRETLEVEL from svds_userAll b where a.id=b.id and rownum=1)"})
	public Integer updateUserSecurity();

}
