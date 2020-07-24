package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.History;
import ac.drsi.nestor.entity.Menu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.Tabs;

@Mapper
public interface HistoryDao {

	@Select("select * from  svds_files where rownum<=20 order by fileid desc")
    @Results({
    	  @Result(column="fileid",property="id"),
            @Result(column="filename",property="name"),
            @Result(column="fileurl",property="url"),
            @Result(column="filedate",property="date"),
            @Result(column="tabsid",property="tabs",javaType = Tabs.class, one = @One(select = "ac.drsi.nestor.dao.HistoryDao.getTabsbyid")),
            @Result(column="menuid",property="menu",javaType = Menu.class, one = @One(select = "ac.drsi.nestor.dao.HistoryDao.getMenuByid"))
    })
	public List<History> getHistoryDao();
	
	@Select("select * from svds_tabs where id = #{id}")
	@Results({
        @Result(column="id",property="id"),
        @Result(column="name",property="name"),
        @Result(column="parentid",property="parentid"),
        @Result(column="lv",property="lv"),
        @Result(column = "state", property = "state"),
        @Result(column="menuid",property="menuid")
})
	public  Tabs getTabsbyid(Integer id);
	
	@Select("select * from svds_menu where id = #{id}")
	@Results({
        @Result(column="id",property="id"),
        @Result(column="text",property="text"),
        @Result(column="parentid",property="parentid"),
        @Result(column="state",property="lv")
})
	public Menu getMenuByid(Integer id);
	
}
