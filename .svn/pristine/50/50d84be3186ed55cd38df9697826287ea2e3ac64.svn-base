package ac.drsi.nestor.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import ac.drsi.nestor.entity.SVDS_Session;
import ac.drsi.nestor.sql.SVDS_SessionSqlProvider;

@Mapper
public interface SVDS_SessionDao {
	/**
	 * 根据IP地址获取登录者信息
	 * @param ip
	 * @return
	 */
	@Select("select * from SVDS_session where ip = #{ip}")
	@Results(id = "sessionMap", value = {
			@Result(column = "ip", property = "ip", javaType = String.class),
			@Result(column = "value", property = "value", javaType = String.class),
			@Result(column = "key", property = "key", javaType = String.class) })
	public SVDS_Session getSessionByIp(@Param("ip") String ip);
	/**
	 * 插入登录信息
	 * @param ip
	 * @param userId
	 * @param value
	 * @return
	 */
	@InsertProvider(type = SVDS_SessionSqlProvider.class, method = "insertSession")
	public Integer inserSession(SVDS_Session session);
	/**
	 * 根据IP地址修改登录信息
	 * @param ip
	 * @param userId
	 * @param value
	 * @return
	 */
	@UpdateProvider(type = SVDS_SessionSqlProvider.class, method = "updateSession")
	public Integer updateSession(SVDS_Session session);
	/**
	 * 根据Ip删除
	 * @param ip
	 * @return
	 */
	@Delete({"delete svds_session where ip=#{ip}"})
	public Integer deleteSession(String ip);
	/**
	 * 根据Ip和Key删除
	 * @param ip
	 * @param key
	 * @return
	 */
	@Delete({"delete svds_session where ip=#{ip} and key = #{key}"})
	 public Integer deleteSessionBykey(@Param("ip")String ip,@Param("key")String key);
	/**
	 * 根据Ip删除
	 * @param ip
	 * @return
	 */
	@Delete({"delete svds_session where key='userId' and value=#{userId}"})
	public Integer deleteSessionByUserId(Integer userId);
	/**
	 * 根据Ip和key查询信息
	 * @param ip
	 * @param key
	 * @return
	 */
	@Select("select * from SVDS_session where ip = #{ip} and key = #{key}")
	@Results(id = "sessionMap1", value = {
			@Result(column = "ip", property = "ip", javaType = String.class),
			@Result(column = "value", property = "value", javaType = String.class),
			@Result(column = "key", property = "key", javaType = String.class) })
	public SVDS_Session getSession(@Param("ip")String ip,@Param("key")String key);
}
