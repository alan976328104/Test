package ac.drsi.nestor.entity;

import java.util.ArrayList;
import java.util.List;

public class InfoDataBean {
	Integer id;//基本信息id
	String mark;//基本信息标记
	String name;//基本信息名称
	String value;//值
	Integer menuid;//菜单id
	List<InfoDataBean> children = new ArrayList<>();
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
	public List<InfoDataBean> getChildren() {
		return children;
	}
	public void setChildren(List<InfoDataBean> children) {
		this.children = children;
	}
	
	
}
