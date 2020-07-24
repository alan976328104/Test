package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.SVDS_Button;

@Mapper
public interface SVDS_ButtonDao {
	/**
	 * 查询所有按钮
	 * 
	 * @return
	 */
	@Select("select * from SVDS_button ORDER BY btnId")
	@Results({
			@Result(column = "btnId", property = "btnId", javaType = Integer.class),
			@Result(column = "btnName", property = "btnName", javaType = String.class) })
	public List<SVDS_Button> getButtonAll();

	/**
	 * 根据名称模糊查询
	 * 
	 * @param Buttonname
	 * @return
	 */
	@Select("select * from SVDS_button where btnName like '%' || #{btnName} || '%' ORDER BY btnId")
	@Results({
			@Result(column = "btnId", property = "btnId", javaType = Integer.class),
			@Result(column = "btnName", property = "btnName", javaType = String.class)})
	public List<SVDS_Button> listButtonByName(@Param("btnName") String ButtonName);

	/**
	 * 查询按钮总数
	 * @return
	 */
	@Select("select count(*) from SVDS_button")
	public Integer getButtonAllCount();

	/**
	 * 添加按钮
	 * 
	 * @param SVDS_Button
	 * @return
	 */
	@Insert({ "insert into SVDS_button(btnId,btnName) values(nvl((select max(btnId) from SVDS_button),0)+1,#{btnName})" })
	public Integer insertButton(SVDS_Button button);

	/**
	 * 修改按钮名称
	 * 
	 * @param SVDS_Button
	 * @return
	 */
	@Update("update SVDS_button set btnName=#{btnName} where btnId=#{btnId}")
	public Integer updateButton(SVDS_Button button);
	
	/***
	 * 根据Id查询按钮
	 * 
	 * @param btnId
	 * @return
	 */
	@Select("select * from SVDS_button where btnId =#{btnId} ORDER BY btnId")
	@Results({
		@Result(column = "btnId", property = "btnId", javaType = Integer.class),
		@Result(column = "btnName", property = "btnName", javaType = String.class)})
	public SVDS_Button getButtonById(Integer btnId);
	
	/***
	 * 根据名称查询按钮
	 * 
	 * @param btnName
	 * @return
	 */
	@Select("select * from SVDS_button where btnName =#{btnName} ORDER BY btnId")
	@Results({
		@Result(column = "btnId", property = "btnId", javaType = Integer.class),
		@Result(column = "btnName", property = "btnName", javaType = String.class)})
	public SVDS_Button getButtonByName(String btnName);
	/**
	 * 根据Id集合查询按钮
	 * 
	 * @param btnIds
	 * @return
	 */
	@Select({
			"<script>",
			"select",
			" * from",
			"SVDS_button",
			"where ",
			"<foreach collection='ids' item='btnId' open='(' separator='or' close=')'>",
			" btnId in #{btnId}", "</foreach>", "</script>" })
	@Results({
		@Result(column = "btnId", property = "btnId", javaType = Integer.class),
		@Result(column = "btnName", property = "btnName", javaType = String.class)})
	public List<SVDS_Button> listByButtonIds(@Param("ids") List<Integer> btnIds);
	/**
	 * 删除按钮
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_button",
			"where btnId in",
			"<foreach collection='ids' item='btnId' open='(' separator=',' close=')'>",
			"#{ButtonId}", "</foreach>", "</script>" })
	public Integer deleteButton(@Param("ids") List<Integer> ids);
}
