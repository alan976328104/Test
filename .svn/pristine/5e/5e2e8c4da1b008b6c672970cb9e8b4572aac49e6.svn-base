package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.Phenomenon;

@Mapper
public interface PhenomenonDao {
	@Select("select * from (select * from svds_menu where parentid in (select id from svds_menu where lv = 1) or parentid in (select id from svds_menu where parentid in (select id from svds_menu where lv = 1)) or lv >3) where url !='html/yanzhengjuzhen.html' and id not in(select id from svds_menu where id  in ( select parentid from svds_menu ) ) and state = 0 and id in (select menuid from svds_rolemenu where  roleid =( select roleid from svds_users where userid = #{userid} ))")
	public List<Phenomenon> findAllPhenomenonmenu(Integer userid);
	
	@Select("select distinct name from svds_infodata where mark = '@'")
	public List<String> findAllPhenomenon();
	
	
	@Select("select count(*) from svds_infodata where mark = '@'  and menuid = #{menuid} and name = #{name}")
	public Integer ishave(@Param("menuid")Integer menuid,@Param("name") String name);
	
	
	
	@Select("select distinct id from svds_menu where text =#{name}")	
	public Integer findmenuByidforname(String name);
	
	
	@Insert({ "insert into svds_phenomenon(id,menuid,value) values(nvl((select max(id) from svds_phenomenon),0)+1,#{menuid},#{value})" })
	public Integer addphenomenon(@Param("menuid")Integer menuid,@Param("value")String value);
	
	@Select("select value from svds_phenomenon where menuid =#{menuid} and rownum <2")	
	public String getphvalue(@Param("menuid") Integer menuid);
	
	@Delete({"delete from svds_phenomenon where menuid = #{id}"})
	public Integer delph(Integer id);
	 
	
}
