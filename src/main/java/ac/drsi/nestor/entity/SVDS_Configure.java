package ac.drsi.nestor.entity;

public class SVDS_Configure {
	private String dataName;// 系统名称
	private String infoError;// 系统联系信息
	private String info;// 系统介绍信息

	/**
	 * 获取系统名称
	 * 
	 * @return
	 */
	public String getDataName() {
		return dataName;
	}

	/**
	 * 设置系统名称
	 * 
	 * @param dataName
	 *            系统名称
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * 获取系统联系信息
	 * 
	 * @return
	 */
	public String getInfoError() {
		return infoError;
	}

	/**
	 * 设置系统联系信息
	 * 
	 * @param infoError
	 */
	public void setInfoError(String infoError) {
		this.infoError = infoError;
	}

	/**
	 * 获取系统介绍信息
	 * 
	 * @return
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * 设置系统介绍信息
	 * 
	 * @param info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

}
