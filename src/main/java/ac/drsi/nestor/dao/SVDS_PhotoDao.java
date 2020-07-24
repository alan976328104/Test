package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import ac.drsi.nestor.entity.SVDS_Photo;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.sql.SVDS_PhotoSqlProvider;

@Mapper
public interface SVDS_PhotoDao {

	/**
	 * 根据用户ID查找用户头像
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select * from SVDS_photo where userId= #{userId}")
	@Results(value = {
			@Result(column = "photoId", property = "photoId", javaType = Integer.class),
			@Result(column = "photoName", property = "photoName", javaType = String.class),
			@Result(column = "photoType", property = "photoType", javaType = String.class),
			@Result(column = "photoUrl", property = "photoUrl", javaType = String.class),
			@Result(column = "picture", property = "picture", javaType = byte[].class),
			@Result(column = "userId", property = "user", javaType = SVDS_User.class, one = @One(select = "ac.drsi.nestor.dao.SVDS_UserDao.getUserById")) })
	public SVDS_Photo getByUserId(Integer userId);

	/**
	 * 添加头像
	 * 
	 * @param photo
	 * @return
	 */
	@InsertProvider(type = SVDS_PhotoSqlProvider.class, method = "insertPhoto")
	public Integer insertPhoto(SVDS_Photo photo);

	/**
	 * 修改头像名称
	 * 
	 * @param photo
	 * @return
	 */
	@UpdateProvider(type = SVDS_PhotoSqlProvider.class, method = "updatePhoto")
	public Integer updatePhoto(SVDS_Photo photo);

	/**
	 * 删除头像
	 * 
	 * @param ids
	 * @return
	 */
	@Delete({
			"<script>",
			"delete",
			"from SVDS_photo",
			"where photoId in",
			"<foreach collection='ids' item='photoId' open='(' separator=',' close=')'>",
			"#{photoId}", "</foreach>", "</script>" })
	public Integer deletePhoto(@Param("ids") List<Integer> ids);
}
