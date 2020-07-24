package ac.drsi.nestor.entity;

import java.util.ArrayList;
import java.util.List;

public class CeShi {
private String name; 
private Integer menuid;
private  List<CeShi> ceshi;
private List<CeshiData> data=new ArrayList<>();
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}


public Integer getMenuid() {
	return menuid;
}
public void setMenuid(Integer menuid) {
	this.menuid = menuid;
}
public List<CeShi> getCeshi() {
	return ceshi;
}
public void setCeshi(List<CeShi> ceshi) {
	this.ceshi = ceshi;
}
public List<CeshiData> getData() {
	return data;
}
public void setData(List<CeshiData> data) {
	this.data = data;
}

}
