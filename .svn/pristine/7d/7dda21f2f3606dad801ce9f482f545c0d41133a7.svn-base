package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Photo;

public class SVDS_PhotoSqlProvider {
	public String updatePhoto(final SVDS_Photo photo) {
		return new SQL() {
			{
				UPDATE("SVDS_photo");
				if (photo.getPhotoName() != null) {
					SET("photoName = #{photoName}");
				}
				if (photo.getPhotoType() != null) {
					SET("photoType = #{photoType}");
				}
				if (photo.getPhotoUrl() != null) {
					SET("photoUrl = #{photoUrl}");
				}
				if (photo.getPicture() != null) {
					SET("picture = #{picture}");
				}
				if (photo.getUser() != null) {
					SET("userId = #{user.userId}");
				}
				WHERE("photoId = #{photoId}");
			}
		}.toString();
	}

	public String insertPhoto(final SVDS_Photo photo) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_photo");
				if (photo.getPhotoId() == null) {
					VALUES("photoId", "nvl((select max(photoId) from SVDS_photo),0)+1");
				}
				if (photo.getPhotoName() != null) {
					VALUES("photoName", "#{photoName}");
				}
				if (photo.getPhotoType() != null) { 
					VALUES("photoType", " #{photoType}");
				}
				if (photo.getPhotoUrl() != null) {
					VALUES("photoUrl", "#{photoUrl}");
				}
				if (photo.getPicture() != null) {
					VALUES("picture", "#{picture}");
				}
				if (photo.getUser() != null) {
					VALUES("userId", "#{user.userId}");
				}
			}
		}.toString();

	}
}
