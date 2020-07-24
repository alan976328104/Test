package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.StatementType;

import ac.drsi.nestor.entity.SVDS_Dept;
import ac.drsi.nestor.sql.SVDS_DeptSqlProvider;

@Mapper
public interface SVDS_DeptDao {
	/**
	 * 查询所有部门
	 * 
	 * @return
	 */
	@Select("select * from SVDS_dept where parentId is null order by deptId")
	@Results({
			@Result(column = "DEPTID", property = "deptId", javaType = Integer.class),
			@Result(column = "parentId", property = "parentId", javaType = String.class),
			@Result(column = "DEPTNAME", property = "deptName", javaType = String.class),
			@Result(column = "DEPTCODE", property = "deptCode", javaType = String.class),
			@Result(column = "DEPTCODE", property = "children", many = @Many(select = "ac.drsi.nestor.dao.SVDS_DeptDao.listDeptByParentId")) })
	public List<SVDS_Dept> getDeptAll();
/**
 * 根据deptCode查询父级
 * @param deptCode
 * @return
 */
	@Select("select * from SVDS_dept where parentId=#{deptCode}")
	@Results({
			@Result(column = "DEPTID", property = "deptId", javaType = Integer.class),
			@Result(column = "parentId", property = "parentId", javaType = String.class),
			@Result(column = "DEPTNAME", property = "deptName", javaType = String.class),
			@Result(column = "DEPTCODE", property = "deptCode", javaType = String.class) })
	public List<SVDS_Dept> listDeptByParentId(Integer deptCode);

	/**
	 * 根据部门名称模糊查询
	 * 
	 * @param deptname
	 * @return
	 */
	@Select("select * from dept where deptName like '%' || #{deptName} || '%' ORDER BY DEPTID")
	@Results({
			@Result(column = "DEPTID", property = "deptId", javaType = Integer.class),
			@Result(column = "parentId", property = "parentId", javaType = String.class),
			@Result(column = "DEPTNAME", property = "deptName", javaType = String.class),
			@Result(column = "DEPTCODE", property = "deptCode", javaType = String.class) })
	public List<SVDS_Dept> getDeptByName(@Param("deptName") String deptName);

	/**
	 * 查询部门所有总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_dept")
	public Integer getDeptAllCount();

	/**
	 * 添加部门
	 * 
	 * @param dept
	 * @return
	 */
	@InsertProvider(type = SVDS_DeptSqlProvider.class, method = "insertDept")
	@SelectKey(before = false, statement = "select max(deptId) as deptId from SVDS_dept", keyProperty = "deptId", statementType = StatementType.STATEMENT, resultType = Integer.class)
	public Integer insertDept(SVDS_Dept dept);

	/**
	 * 修改部门
	 * 
	 * @param dept
	 * @return
	 */
	@UpdateProvider(type = SVDS_DeptSqlProvider.class, method = "updateDept")
	public Integer updateDept(SVDS_Dept dept);

	/***
	 * 根据Id查询部门
	 * 
	 * @param deptId
	 * @return
	 */
	@Select("select * from SVDS_dept where deptId =#{deptId} ORDER BY DEPTID")
	@Results({
			@Result(column = "DEPTID", property = "deptId", javaType = Integer.class),
			@Result(column = "DEPTNAME", property = "deptName", javaType = String.class) })
	public SVDS_Dept getDeptById(Integer deptId);

	/**
	 * 根据Id删除部门
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_dept",
			"where deptId in",
			"<foreach collection='ids' item='deptId' open='(' separator=',' close=')'>",
			"#{deptId}", "</foreach>", "</script>" })
	public Integer deleteDept(@Param("ids") List<Integer> ids);
	
	/**
	 * 删除全部
	 * @return
	 */
	@Delete({"delete from svds_dept"})
	public Integer deleteDeptAll();
}
