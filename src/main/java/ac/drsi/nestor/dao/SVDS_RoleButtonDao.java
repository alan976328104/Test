package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.SVDS_RoleButton;

@Mapper
public interface SVDS_RoleButtonDao {
	/**
	 * 根据角色Id查询所拥有的按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("select * from SVDS_rolebutton where roleId=#{roleId} ORDER BY roleId")
	@Results({
			@Result(column = "roleId", property = "roleId", javaType = Integer.class),
			@Result(column = "btnId", property = "btnId", javaType = Integer.class),
			@Result(column = "menuId", property = "menuId", javaType = Integer.class) })
	public List<SVDS_RoleButton> getRoleButtonAll(
			@Param("roleId") Integer roleId);
	/**
	 * 根据角色Id菜单Id查询所拥有的菜单按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("select * from SVDS_rolebutton where roleId=#{roleId} and menuId=#{menuId} ORDER BY roleId")
	@Results({
			@Result(column = "roleId", property = "roleId", javaType = Integer.class),
			@Result(column = "btnId", property = "btnId", javaType = Integer.class),
			@Result(column = "menuId", property = "menuId", javaType = Integer.class) })
	public List<SVDS_RoleButton> listRoleButtonAllById(
			@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

	/**
	 * 给角色添加按钮权限
	 * 
	 * @param roleButton
	 * @return
	 */
	@Insert({ "insert into SVDS_rolebutton(roleId,btnId,menuId) values(#{roleId},#{btnId},#{menuId})" })
	public Integer insertRoleButton(SVDS_RoleButton roleButton);

	/**
	 * 删除角色所拥有的按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Delete("delete from SVDS_rolebutton where roleId=#{roleId} ")
	public Integer deleteRoleButton(@Param("roleId") Integer roleId);
	
	/**
	 * 根据角色id集合删除角色拥有的菜单权限
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_rolebutton",
			"where ",
			"<foreach collection='ids' item='roleId' open='(' separator='or' close=')'>",
			" roleId in #{roleId}", "</foreach>", "</script>" })
	public Integer deleteRoleButtonByRoleIds(@Param("ids") List<Integer> ids);
	
	/**
	 * 删除角色所拥有的菜单按钮权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Delete("delete from SVDS_rolebutton where roleId=#{roleId} and menuId=#{menuId} ")
	public Integer deleteRoleButtonByMenuId(@Param("roleId") Integer roleId,
			@Param("menuId") Integer menuId);
}
