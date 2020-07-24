package ac.drsi.nestor.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.SVDS_Configure;

@Mapper
public interface SVDS_ConfigureDao {
	/**
	 * 查询名称
	 * 
	 * @return
	 */
	@Select("select * from SVDS_Configure ")
	@Results(id="ConfigureMap",value={
			@Result(column = "dataName", property = "dataName", javaType = String.class),
			@Result(column = "infoError", property = "infoError", javaType = String.class),
			@Result(column = "info", property = "info", javaType = String.class)})
	public SVDS_Configure getConfigure();


	/**
	 * 添加名称
	 * 
	 * @param Configure
	 * @return
	 */
	@Insert({ "insert into SVDS_Configure(dataName,infoError,info) values(#{dataName},#{infoError},#{info})" })
	public Integer insertConfigure(SVDS_Configure configure);

	/**
	 * 修改名称
	 * 
	 * @param Configure
	 * @return
	 */
	@Update("update SVDS_Configure set dataName=#{dataName} , infoError=#{infoError}, info=#{info}")
	public Integer updateConfigure(SVDS_Configure configure);

	

}
