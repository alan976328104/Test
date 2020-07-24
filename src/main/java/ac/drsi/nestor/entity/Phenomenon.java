package ac.drsi.nestor.entity;

public class Phenomenon {

	private String id;//验证矩阵id
	private String text;//验证矩阵名称
	private String iconCls="icon-blank";//验证矩阵图标
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Phenomenon(String text) {
		super();
		this.text = text;
	}
	public Phenomenon(String id, String text, String iconCls) {
		super();
		this.id = id;
		this.text = text;
		this.iconCls = iconCls;
	}
	public Phenomenon() {
		super();
	}    
	
}
