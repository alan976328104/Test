package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.SVDS_RoleMenu;

@Mapper
public interface SVDS_RoleMenuDao {
	/**
	 * 根据角色Id查询所拥有的菜单菜单权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Select("select * from SVDS_rolemenu where roleId=#{roleId} ORDER BY menuId")
	@Results({
			@Result(column = "roleId", property = "roleId", javaType = Integer.class),
			@Result(column = "menuId", property = "menuId", javaType = Integer.class) })
	public List<SVDS_RoleMenu> getRoleMenuAll(Integer roleId);

	/**
	 * 给角色添加菜单权限
	 * 
	 * @param SVDS_RoleMenu
	 * @return
	 */
	@Insert({ "insert into SVDS_rolemenu(roleId,menuId) values(#{roleId},#{menuId})" })
	public Integer insertRoleMenu(SVDS_RoleMenu roleMenu);

	/**
	 * 删除角色所拥有的菜单权限
	 * 
	 * @param roleId
	 * @return
	 */
	@Delete("delete from SVDS_rolemenu where roleId=#{roleId} ")
	public Integer deleteRoleMenu(@Param("roleId") Integer roleId);

	/**
	 * 根据角色id集合删除角色拥有的菜单权限
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_rolemenu",
			"where ",
			"<foreach collection='ids' item='roleId' open='(' separator='or' close=')'>",
			" roleId in #{roleId}", "</foreach>", "</script>" })
	public Integer deleteRoleMenuByRoleIds(@Param("ids") List<Integer> ids);

	/**
	 * 根据菜单Id查询角色id
	 * 
	 * @param menuId
	 * @return
	 */
	@Select("select * from SVDS_rolemenu where menuId=#{menuId} ORDER BY roleId")
	@Results({
			@Result(column = "roleId", property = "roleId", javaType = Integer.class),
			@Result(column = "menuId", property = "menuId", javaType = Integer.class) })
	public List<SVDS_RoleMenu> listByMenuId(Integer menuId);

	/**
	 * 根据菜单Id查询角色id
	 * 
	 * @param menuId
	 * @return
	 */
	@Select({
			"<script>",
			"select",
			" * from",
			"SVDS_rolemenu",
			"where ",
			"<foreach collection='ids' item='menuId' open='(' separator='or' close=')'>",
			" menuId in #{menuId}", "</foreach>", "</script>" })
	@Results({
			@Result(column = "roleId", property = "roleId", javaType = Integer.class),
			@Result(column = "menuId", property = "menuId", javaType = Integer.class) })
	public List<SVDS_RoleMenu> listByMenuIds(@Param("ids") List<Integer> menuId);
}
