package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface statisticsDao {
	
	
	@Select("select count(0) from svds_relation")
	public Integer  GetrelationNum();
	
	@Select("select count(0) from svds_menu where url = 'html/yanzhengjuzhen.html'")
	public Integer Getyanzhengjuzhen();
	
	
	@Select("select project from svds_relation")
	public List<String>  Getrelationproject();
	
	@Select("select lv from svds_menu where id = #{menuid}")
	public Integer getMenulv(Integer menuid);
	
	@Select("select parentid from svds_menu where id = #{menuid}")
	public Integer getparentid(Integer menuid);
}
