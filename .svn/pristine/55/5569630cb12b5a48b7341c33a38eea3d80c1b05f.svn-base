package ac.drsi.nestor.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.sql.SVDS_LogSqlProvider;

@Mapper
public interface SVDS_LogDao {
	/**
	 * 查询所有日志
	 * 
	 * @return
	 */
	@Select("select * from SVDS_Log ORDER BY logId desc")
	@Results(id = "LogMap", value = {
			@Result(column = "logId", property = "logId", javaType = Integer.class),
			@Result(column = "operation", property = "operation", javaType = String.class),
			@Result(column = "ipData", property = "ipData", javaType = String.class),
			@Result(column = "result", property = "result", javaType = String.class),
			@Result(column = "menuId", property = "menu", javaType = SVDS_Menu.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_MenuDao.getMenuById")),
			@Result(column = "fileId", property = "file", javaType = SVDS_Files.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_FilesDao.getFilesById")),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")) })
	public List<SVDS_Log> getLogAll();

	/**
	 * 查询日志总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_Log")
	public Integer getLogAllCount();

	/**
	 * 添加日志
	 * 
	 * @param Log
	 * @return
	 */
	@InsertProvider(type = SVDS_LogSqlProvider.class, method = "insertLog")
	public Integer insertLog(SVDS_Log Log);

	/**
	 * 修改日志
	 * 
	 * @param Log
	 * @return
	 */
	@Update("update SVDS_Log set LogName=#{LogName} where logId=#{logId}")
	public Integer updateLog(SVDS_Log Log);

	/***
	 * 根据Id查询日志
	 * 
	 * @param LogId
	 * @return
	 */
	@Select("select * from SVDS_Log where logId =#{logId} ORDER BY logId desc")
	@ResultMap("LogMap")
	public SVDS_Log getLogById(Integer LogId);
	
	/***
	 * 根据用户Id查询记录
	 * 
	 * @param LogId
	 * @return
	 */
	@Select("select * from (select * from svds_log where userId=#{userId}  and fileId in (select fileId from svds_files where state=0) ORDER BY logDate desc ) where rownum<=10")
	@ResultMap("LogMap")
	public List<SVDS_Log> listByUserId(Integer userId);

	/**
	 * 根据日期查询日志
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Select("select * from SVDS_Log where logDate between #{startDate} and #{endDate}  ORDER BY logId desc")
	@ResultMap("LogMap")
	public List<SVDS_Log> getLogByDate(@Param("startDate") String startDate,
			@Param("endDate") String endDate);
	/**
	 * 组合查询
	 * @param param
	 * @return
	 */
	@SelectProvider(type = SVDS_LogSqlProvider.class, method = "selectWhitFilesSql")
	@ResultMap("LogMap")
	public List<SVDS_Log> listLogByParam(Map<String, Object> param);
	
	/**
	 * 安全审计员查询
	 * @param param
	 * @return
	 */
	@SelectProvider(type = SVDS_LogSqlProvider.class, method = "selectWhitLogSql")
	@ResultMap("LogMap")
	public List<SVDS_Log> selectWhitLogSql(Map<String, Object> param);
	
	/**
	 * 安全管理员查询
	 * @param param
	 * @return
	 */
	@SelectProvider(type = SVDS_LogSqlProvider.class, method = "selectLogSql")
	@ResultMap("LogMap")
	public List<SVDS_Log> selectLogSql(Map<String, Object> param);
	/**
	 * 普通用户查询
	 * @param param
	 * @return
	 */
	@SelectProvider(type = SVDS_LogSqlProvider.class, method = "selectGenLogSql")
	@ResultMap("LogMap")
	public List<SVDS_Log> selectGenLogSql(Map<String, Object> param);
	/**
	 * 根据文件Id删除文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_Log",
			"where fileId in",
			"<foreach collection='ids' item='fileId' open='(' separator=',' close=')'>",
			"#{fileId}", "</foreach>", "</script>" })
	public Integer deleteByFileId(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据选项卡Id删除数据
	 * @param tabsId
	 * @return
	 */
	@Delete({"delete from svds_log where fileid in(select fileId from svds_files where tabsId=#{tabsId})"})
	public Integer deleteLogByTabsId(@Param("tabsId") Integer tabsId);

	/**
	 * 删除日志
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_Log",
			"where logId in",
			"<foreach collection='ids' item='LogId' open='(' separator=',' close=')'>",
			"#{logId}", "</foreach>", "</script>" })
	public Integer deleteLog(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据用户Id删除日志
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_Log",
			"where ",
			"<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" userId in #{userId} ", "</foreach>", "</script>" })
	public Integer deleteByUserId(@Param("ids") List<Integer> ids);
	
	/**
	 * 总访问量
	 * @return
	 */
	@Select("select count(*) from svds_log where operation='登录系统'")
	public Integer totalView();
	
	/**
	 * 日访问量
	 * @return
	 */
	@Select("select count(*) from svds_log where operation='登录系统' and  to_char(TO_DATE(logdate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy-mm-dd')=to_char(sysdate,'yyyy-mm-dd')")
	public Integer listToDay();
	/**
	 * 年访问量
	 * @return
	 */
	@Select("select to_char(TO_DATE(logdate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy') as valueName,count(to_char(TO_DATE(logdate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')) as countNum from svds_log where operation='登录系统'  group by to_char(TO_DATE(logdate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')")
	@Results({
		@Result(column = "valueName", property = "name", javaType = String.class),
		@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> yearVisits();
	
	/**
	 * 月访问量
	 * @param yearValue
	 * @return
	 */
	@Select("select ltrim(to_char(TO_DATE(logdate, 'yyyy-MM-dd hh24:mi:ss'),'mm'),'0') as valueName,count(to_char(TO_DATE(logdate, 'yyyy-MM-dd hh24:mi:ss'),'mm')) as countNum from svds_log where operation='登录系统' and to_char(TO_DATE(logdate, 'yyyy-MM-dd hh24:mi:ss'),'yyyy')=#{yearValue}  group by to_char(TO_DATE(logdate, 'yyyy-MM-dd hh24:mi:ss'),'mm') ")
	@Results({
		@Result(column = "valueName", property = "name", javaType = String.class),
		@Result(column = "countNum", property = "value", javaType = String.class) })
	public List<ChartEntity> monthVisits(String yearValue);
}
