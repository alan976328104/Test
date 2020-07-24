package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import ac.drsi.nestor.entity.SVDS_Major;

@Mapper
public interface SVDS_MajorDao {
	/**
	 * 查询所有专业
	 * 
	 * @return
	 */
	@Select("select * from SVDS_major ORDER BY majorID")
	@Results(id = "majorMap", value = {
			@Result(column = "majorID", property = "majorId", javaType = Integer.class),
			@Result(column = "majorNAME", property = "majorName", javaType = String.class) })
	public List<SVDS_Major> getMajorAll();

	/**
	 * 根据专业名称模糊查询
	 * 
	 * @param majorname
	 * @return
	 */
	@Select("select * from SVDS_major where majorName like '%' || #{majorName} || '%' ORDER BY majorID")
	@ResultMap("majorMap")
	public List<SVDS_Major> getMajorByName(@Param("majorName") String majorName);

	/**
	 * 查询专业所有总数
	 * 
	 * @return
	 */
	@Select("select count(*) from SVDS_major")
	public Integer getMajorAllCount();

	/**
	 * 添加专业
	 * 
	 * @param major
	 * @return
	 */
	@Insert({ "insert into SVDS_major(majorId,majorName) values(nvl((select max(majorId) from SVDS_major),0)+1,#{majorName})" })
	public Integer insertMajor(SVDS_Major major);

	/**
	 * 修改专业
	 * 
	 * @param major
	 * @return
	 */
	@Update("update SVDS_major set majorName=#{majorName} where majorId=#{majorId}")
	public Integer updateMajor(SVDS_Major major);

	/***
	 * 根据Id查询专业
	 * 
	 * @param majorId
	 * @return
	 */
	@Select("select * from SVDS_major where majorId =#{majorId} ORDER BY majorID")
	@ResultMap("majorMap")
	public SVDS_Major getMajorById(Integer majorId);

	/**
	 * 根据Id删除专业
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_major",
			"where majorId in",
			"<foreach collection='ids' item='majorId' open='(' separator=',' close=')'>",
			"#{majorId}", "</foreach>", "</script>" })
	public Integer deleteMajor(@Param("ids") List<Integer> ids);
}
