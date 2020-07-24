package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.Info;
import ac.drsi.nestor.entity.InfoData;
import ac.drsi.nestor.entity.InfoDataBean;
import ac.drsi.nestor.entity.SVDS_Files;

@Mapper
public interface infoDao {

	@Select("select * from svds_info where menuid = #{id}  and rownum <2")
	public Info getInfoByid(Integer id);
	
	
	@Insert({"insert into SVDS_INFODATA(id,mark,name,value,menuid) values(nvl((select max(id) from SVDS_INFODATA),0)+1,#{mark},#{name},#{value},#{menuid})"})
	public Integer insterInfo(@Param("mark")String mark,@Param("name")String name,@Param("value")String value,@Param("menuid")Integer menuid);
	
	@Select("select * from svds_infodata where menuid = #{id} order by id")
	public List<InfoData> getinfodatabyid(Integer menuid);
	//@Select("select * from svds_files where menuId in(select menuId from svds_infodata where name = 'case' and value=#{value} order by id)")
	@Select("select menuid from svds_infodata where name = 'case' and value=#{value} order by id")
	public List<Integer> menuIdInfoDataByValue(@Param("value")String value);
	
	@Select("select id from svds_infodata where name = 'case' and value=#{value} order by id")
	public List<Integer> listInfoDataByValue(@Param("value")String value);
	
	@Select("select min(id) from svds_infodata where id>#{id} and name = 'case' order by id")
	public Integer getInfoDataByMaxId(@Param("id")Integer id);
	
	@Select("select max(id) from svds_infodata where id>#{id} order by id")
	public Integer getInfoDataById(@Param("id")Integer id);
	
	@Select("select * from svds_infodata where id>#{minId} and mark='#' and id<=#{maxId} and name=#{name} order by id")
	public InfoData getInfoDataByRange(@Param("minId")Integer minId,@Param("maxId")Integer maxId,@Param("name")String name);
			
	@Select("select distinct( value) from svds_infodata where name ='case'")
	public List<String> getInfoList();
	
	
	@Select("select * from svds_infodata  where name ='case' order by id")
	public List<InfoDataBean> getCase();
	
	@Select("select * from svds_infodata where mark = '#' and id>#{begin} and id<#{end}")
	public List<InfoDataBean> getCaseChildren(@Param("begin")Integer begin,@Param("end")Integer end);
}
