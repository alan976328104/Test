package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.Phenomenon;

@Mapper
public interface OpenCSVDao {
	
	@Select("select charts from svds_csvtemp  where userid =#{id} and page=#{page}")
	public Long getchars(@Param("id")Integer id,@Param("page")Integer page);
	
	@Select("select count(*) from svds_csvtemp where userid = #{id}")
	public Integer isnull(Integer id);
	
	@Select("select count(*) from svds_csvtemp where userid = #{id} and page=#{page}")
	public Integer isnull2(@Param("id")Integer id,@Param("page")Integer page);
	
	@Insert({ "insert into svds_csvtemp(userid,charts,page) values(#{userid},#{charts},#{page})" })
	public Integer insertCSVtemp(@Param("userid")Integer id,@Param("charts")long charts,@Param("page")Integer page);
	
	
	@Update({"update svds_csvtemp set  charts =#{charts} where userid = #{userid} and page=#{page}"})
	public Integer setCSVtemp(@Param("userid")Integer id,@Param("charts")long charts,@Param("page")Integer page);
	
	@Update({"update svds_csvtemp set  charts =#{charts},page=#{page} where userid = #{userid}  "})
	public Integer setCSVtemp2(@Param("userid")Integer id,@Param("charts")long charts,@Param("page")Integer page);
	
	@Select("select max(page) from svds_csvtemp where userid = #{id}")
	public Integer getMaxPage(Integer id);
	
	@Delete("delete from svds_csvtemp where userid = #{id}")
	public Integer delCsvTemmp(Integer id);
	
	@Select("select filename from svds_files where fileid = #{id}")
	public String getfileNameByid(Integer id);
	
	@Select("select fileUrl from svds_files where fileid = #{id}")
	public String OpenVideo(Integer id);
	
	
}
