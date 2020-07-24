package ac.drsi.nestor.entity;

public class RelationTemp {
	private Integer id;//关联检索id
	private String data;//关联检索内容
	private String columndata;//关联检索内容
	private String name;//关联检索名称
	private Integer userid;//用户名
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getColumndata() {
		return columndata;
	}
	public void setColumndata(String columndata) {
		this.columndata = columndata;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
}
