package ac.drsi.nestor.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.SVDS_Major;
import ac.drsi.nestor.entity.SVDS_Role;
import ac.drsi.nestor.entity.SVDS_User;

@Mapper
public interface SVDS_LoginDao {
	/**
	 * 根据用户名和密码登录
	 * @param username
	 * @param pwd
	 * @return
	 */
	@Select("select * from SVDS_users where username = #{username} and pwd= #{pwd}  ORDER BY USERID")
	@Results(id = "userMap", value = {
			@Result(column = "USERID", property = "userId", javaType = Integer.class),
			@Result(column = "USERNAME", property = "username", javaType = String.class),
			@Result(column = "SEX", property = "sex", javaType = Integer.class),
			@Result(column = "code", property = "code", javaType = String.class),
			@Result(column = "cardno", property = "cardno", javaType = String.class),
			@Result(column = "keyno", property = "keyno", javaType = String.class),
			@Result(column = "deptname", property = "deptname", javaType = String.class),
		//	@Result(column = "majorId", property = "major", javaType = SVDS_Major.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MajorDao.getMajorById")),
			@Result(column = "secretlevel", property = "secretlevel", javaType = String.class),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "roleId", property = "role", javaType = SVDS_Role.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_RoleDao.getRoleById")) })
	public SVDS_User Login(@Param("username") String username,
			@Param("pwd") String pwd);
	
	
	@Select("select *from svds_users where substr(keyno,instr(keyno,',')+1,length(keyno))= #{card} or substr(keyno,0,instr(keyno,',')-1) =#{card}")
	@Results(id = "userMap1", value = {
			@Result(column = "USERID", property = "userId", javaType = Integer.class),
			@Result(column = "USERNAME", property = "username", javaType = String.class),
			@Result(column = "SEX", property = "sex", javaType = Integer.class),
			@Result(column = "code", property = "code", javaType = String.class),
			@Result(column = "cardno", property = "cardno", javaType = String.class),
			@Result(column = "keyno", property = "keyno", javaType = String.class),
			@Result(column = "deptname", property = "deptname", javaType = String.class),
		//	@Result(column = "majorId", property = "major", javaType = SVDS_Major.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MajorDao.getMajorById")),
			@Result(column = "secretlevel", property = "secretlevel", javaType = String.class),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "roleId", property = "role", javaType = SVDS_Role.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_RoleDao.getRoleById")) })
	public  SVDS_User  keyLogin(@Param("card")String card);
	
}
