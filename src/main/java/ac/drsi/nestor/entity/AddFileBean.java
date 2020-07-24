package ac.drsi.nestor.entity;

public class AddFileBean {
	private String [] arr; //文件
	private String size;//文件大小
	public String[] getArr() {
		return arr;
	}
	public void setArr(String[] arr) {
		this.arr = arr;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public AddFileBean(String[] arr, String size) {
		super();
		this.arr = arr;
		this.size = size;
	}
	public AddFileBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
