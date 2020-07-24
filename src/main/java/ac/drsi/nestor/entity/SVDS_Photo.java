package ac.drsi.nestor.entity;

import java.util.Arrays;

public class SVDS_Photo {
	private Integer photoId;
	private String photoName;
	private String photoType;
	private String photoUrl;
	private byte[] picture;
	private SVDS_User user;

	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public String getPhotoType() {
		return photoType;
	}

	public void setPhotoType(String photoType) {
		this.photoType = photoType;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public SVDS_User getUser() {
		return user;
	}

	public void setUser(SVDS_User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Photo [photoId=" + photoId + ", photoName=" + photoName
				+ ", photoType=" + photoType + ", photoUrl=" + photoUrl
				+ ", picture=" + Arrays.toString(picture) + ", user=" + user
				+ "]";
	}

}
