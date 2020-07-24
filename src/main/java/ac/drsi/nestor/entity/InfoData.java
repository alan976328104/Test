package ac.drsi.nestor.entity;

public class InfoData {
 private Integer id;//菜单id
 private String mark;//基本信息标记
 private String name;//名称
 private String value;//值
 private Integer menuid;//菜单id
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getMark() {
	return mark;
}
public void setMark(String mark) {
	this.mark = mark;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public Integer getMenuid() {
	return menuid;
}
public void setMenuid(Integer menuid) {
	this.menuid = menuid;
}
@Override
public String toString() {
	return "InfoData [id=" + id + ", mark=" + mark + ", name=" + name
			+ ", value=" + value + ", menuid=" + menuid + "]";
}
 
}
