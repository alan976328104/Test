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
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Message;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.sql.SVDS_MessageSqlProvider;

@Mapper
public interface SVDS_MessageDao {
	/*
	 * 查询所有消息
	 */
	@Select("select * from SVDS_message order by messageDate desc ")
	@Results(id = "messageMap", value = {
			@Result(column = "messageId", property = "messageId", javaType = Integer.class),
			@Result(column = "messageDate", property = "messageDate", javaType = String.class),
			@Result(column = "fileId", property = "files", javaType = SVDS_Files.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_FilesDao.getFilesById")),
			@Result(column = "received", property = "received", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			@Result(column = "sharer", property = "sharer", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")),
			@Result(column = "state", property = "state", javaType = Integer.class) })
	public List<SVDS_Message> getMessageAll();

	/**
	 * 根据姓名模糊查询
	 * 
	 * @param messagename
	 * @return
	 */
	@Select("select * from SVDS_message where messagename like '%' || #{messagename} || '%' order by messageDate desc")
	@ResultMap("messageMap")
	public List<SVDS_Message> getMessageByName(
			@Param("messagename") String messagename);

	/*
	 * 查询所有消息的总数
	 */
	@Select("select count(*) from message")
	public Integer getMessageAllCount();

	/**
	 * 添加消息
	 * 
	 * @param SVDS_Message
	 * @return
	 */
	@InsertProvider(type = SVDS_MessageSqlProvider.class, method = "insertMessage")
	public Integer insertMessage(SVDS_Message message);

	/**
	 * 修改消息
	 * 
	 * @param SVDS_Message
	 * @return
	 */
	@UpdateProvider(type = SVDS_MessageSqlProvider.class, method = "updateMessage")
	public Integer updateMessage(SVDS_Message message);

	/**
	 * 条件查询分享者的消息
	 * 
	 * @param file
	 * @param userId
	 * @return
	 */
	@SelectProvider(type = SVDS_MessageSqlProvider.class, method = "selectMessageSql")
	@ResultMap("messageMap")
	public List<SVDS_Message> selectMessageSql(@Param("file") SVDS_Files file,
			@Param("userId") Integer userId,
			@Param("message") SVDS_Message message);

	/***
	 * 根据消息Id查询消息
	 * 
	 * @param messageId
	 * @return
	 */
	@Select("select * from SVDS_message where messageId =#{messageId} order by messageDate desc")
	@ResultMap("messageMap")
	public SVDS_Message getMessageById(@Param("messageId") Integer messageId);

	/***
	 * 根据接收者Id查询消息
	 * 
	 * @param messageId
	 * @return
	 */
	@Select("select * from SVDS_message where received =#{userId} order by messageDate desc")
	@ResultMap("messageMap")
	public List<SVDS_Message> listMessageByReceived(
			@Param("userId") Integer userId);

	/***
	 * 根据接收者Id查询未读消息
	 * 
	 * @param messageId
	 * @return
	 */
	@Select("select * from SVDS_message where received =#{userId} and state=0 order by messageDate desc ")
	@ResultMap("messageMap")
	public List<SVDS_Message> listMessageState(@Param("userId") Integer userId);

	/***
	 * 查询个人分享消息
	 * 
	 * @param messageId
	 * @return
	 */
	@Select("select * from SVDS_message where sharer =#{userId} order by messageDate desc")
	@ResultMap("messageMap")
	public List<SVDS_Message> listMessageBySharer(
			@Param("userId") Integer userId);

	/***
	 * 模糊查询分享者
	 * 
	 * @param messageId
	 * @return
	 */
	@Select("select * from SVDS_message where received =#{userId} and sharer in (select userId from svds_users where username like '%' || #{username} || '%' ) order by messageDate desc")
	@ResultMap("messageMap")
	public List<SVDS_Message> listMessageByName(
			@Param("userId") Integer userId, @Param("username") String username);

	/**
	 * 清空消息
	 * 
	 * @param userId
	 * @return
	 */
	@Delete("delete from svds_message where received =#{userId}")
	public Integer deleteAllMessage(@Param("userId") Integer userId);

	/**
	 * 根据文件Id删除文件
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_message",
			"where ",
			"<foreach collection='ids' item='fileId' open='(' separator='or' close=')'>",
			" fileId in #{fileId} ", "</foreach>", "</script>" })
	public Integer deleteByFileId(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据选项卡删除数据
	 * @param tabsId
	 * @return
	 */
	@Delete({"delete from SVDS_message where fileId in(select fileId from svds_files where tabsId=#{tabsId})"})
	public Integer deleteByTabsId(@Param("tabsId") Integer tabsId);

	/**
	 * 根据Id集合删除消息
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_message",
			"where ",
			"<foreach collection='ids' item='messageId' open='(' separator='or' close=')'>",
			" messageId in #{messageId} ", "</foreach>", "</script>" })
	public Integer deleteMessage(@Param("ids") List<Integer> ids);
	
	/**
	 * 根据用户Id删除记录
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_message",
			"where ",
			"<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" received in #{userId} ", "</foreach>",
			"or","<foreach collection='ids' item='userId' open='(' separator='or' close=')'>",
			" sharer in #{userId} ", "</foreach>",
			"</script>" })
	public Integer deleteByUserId(@Param("ids") List<Integer> ids);
	
}
