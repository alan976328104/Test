package ac.drsi.nestor.entity;

public class Files {
	private Integer fileId;//文件id
	private String fileName;//文件名称
	private String fileUrl;//文件路径
	private String fileDate;//文件日期
	private String fileVersion;//文件版本
	private SVDS_Security security;//文件密级
	private Integer userid;//用户id
	private Integer state;//文件状态
	private String tabs1;//选项卡层级
	private String tabs2;//选项卡层级
	private String tabs3;//选项卡层级
	private Integer menuid;//菜单id
	public Integer getFileId() {
		return fileId;
	}
	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getFileDate() {
		return fileDate;
	}
	public void setFileDate(String fileDate) {
		this.fileDate = fileDate;
	}
	public String getFileVersion() {
		return fileVersion;
	}
	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}
	public SVDS_Security getSecurity() {
		return security;
	}
	public void setSecurity(SVDS_Security security) {
		this.security = security;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getTabs1() {
		return tabs1;
	}
	public void setTabs1(String tabs1) {
		this.tabs1 = tabs1;
	}
	public String getTabs2() {
		return tabs2;
	}
	public void setTabs2(String tabs2) {
		this.tabs2 = tabs2;
	}
	public String getTabs3() {
		return tabs3;
	}
	public void setTabs3(String tabs3) {
		this.tabs3 = tabs3;
	}
	public Integer getMenuid() {
		return menuid;
	}
	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}
	public Files(Integer fileId, String fileName, String fileUrl,
			String fileDate, String fileVersion, SVDS_Security security,
			Integer userid, Integer state, String tabs1, String tabs2,
			String tabs3, Integer menuid) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.fileDate = fileDate;
		this.fileVersion = fileVersion;
		this.security = security;
		this.userid = userid;
		this.state = state;
		this.tabs1 = tabs1;
		this.tabs2 = tabs2;
		this.tabs3 = tabs3;
		this.menuid = menuid;
	}
	public Files() {
	}
	
	
	
}
