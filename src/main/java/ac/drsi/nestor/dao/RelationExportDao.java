package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.Phenomenon;
import ac.drsi.nestor.entity.RelationTemp;
import ac.drsi.nestor.entity.SVDS_Relation;

@Mapper
public interface RelationExportDao {
	
	@Select("select name from svds_rule s where  s.name like  #{fileid} and s.projectid =#{menuid} and (length(s.name) - length(replace(s.name,'$','')) = 2)")
	public String findallrelation(@Param("menuid")String menuid ,@Param("fileid")String fileid);
	

	@Select("select menuid from svds_files where fileid=#{fid}")
	public Integer findfilemidbyfid(Integer fid);
	
	@Insert({ "insert into svds_relationtemp(id,data,name,columndata,userid) values(nvl((select max(id) from svds_relationtemp),0)+1,#{data},#{name},#{columndata},#{userid})" })
	public Integer insertRelationtemp(@Param("data")String data,@Param("name")String name,@Param("columndata")String columndata,@Param("userid")Integer userid);
	
	@Select("select id,name as text from svds_relationtemp where userid =#{userid}")
	public List<Phenomenon> findRelationtemp(@Param("userid")Integer userid);
	@Select("select * from svds_relationtemp where id=#{id} and userid =#{userid}")
	public RelationTemp findRelationTemp(@Param("id")Integer id,@Param("userid")Integer userid);
	@Delete({ "delete from svds_relationtemp where id = #{id}" })
	public Integer delRelationtempbyid(Integer id);
}
