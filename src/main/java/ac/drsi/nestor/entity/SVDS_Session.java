package ac.drsi.nestor.entity;

public class SVDS_Session {
	private Integer sId;// sessionId
	private String ip;// ip地址
	private String key;// 关键字
	private String value;// 值

	/**
	 * 获取sessionId
	 * 
	 * @return
	 */
	public Integer getsId() {
		return sId;
	}

	/**
	 * 设置sessionId
	 * 
	 * @param sId
	 *            sessionId
	 */
	public void setsId(Integer sId) {
		this.sId = sId;
	}

	/**
	 * 获取ip地址
	 * 
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置IP地址
	 * 
	 * @param ip
	 *            IP地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取关键字
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置关键字
	 * 
	 * @param key
	 *            关键字
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 *            值
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
