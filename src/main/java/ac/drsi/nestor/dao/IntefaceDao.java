package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.SVDS_Files;

@Mapper
public interface IntefaceDao {
	@Select("select fileId,fileName,'8c7dd922ad47494fc02c388e12c00eac/'||SUBSTR(fileUrl，10) as fileurl,fileDate,fileVersion,format,securityId,userId,state,tabsid,menuId,remark,pId,location,abstracts,fileSize from SVDS_files where  tabsId=#{tabsId}")
	@Results({
			@Result(column = "fileId", property = "fileId", javaType = Integer.class),
			@Result(column = "fileName", property = "fileName", javaType = String.class),
			@Result(column = "fileUrl", property = "fileUrl", javaType = String.class),
			@Result(column = "fileDate", property = "fileDate", javaType = String.class),
			@Result(column = "fileVersion", property = "fileVersion", javaType = String.class),
			@Result(column = "format", property = "format", javaType = String.class),
			@Result(column = "state", property = "state", javaType = Integer.class),
			@Result(column = "tabsid", property = "tabsId", javaType = Integer.class),
			@Result(column = "remark", property = "remark", javaType = String.class),
			@Result(column = "pId", property = "pId", javaType = Integer.class),
			@Result(column = "location", property = "location", javaType = String.class),
			@Result(column = "abstracts", property = "abstracts", javaType = String.class),
			@Result(column = "fileSize", property = "fileSize", javaType = String.class) })
	public List<SVDS_Files> getFilesByTabsid(@Param("tabsId") Integer tabsId);
	
	@Select("select fileId,fileName,'8c7dd922ad47494fc02c388e12c00eac/'||SUBSTR(fileUrl，10) as fileurl,fileDate,fileVersion,format,securityId,userId,state,tabsid,menuId,remark,pId,location,abstracts,fileSize from SVDS_files where  filename like #{name} and menuid  in ( select menuid from svds_rolemenu  where roleid  in ( select roleid from svds_users  where username = #{username} and PWD = #{password}   ) )")
	public List<SVDS_Files> getFIlesByFilename(@Param("name") String name,@Param("username")String username,@Param("password")String password);
	
	
	
	@Select("select  menuid from svds_rolemenu where roleid = (select roleid from svds_users where userid = #{userid})")
	public List<Integer> getUserrole(Integer userid);
	
	@Select("select menuid from svds_tabs where id = #{tabsid}")
	public Integer germenuidfortabs(Integer tabsid);
	
	
	
	
	
	
}
