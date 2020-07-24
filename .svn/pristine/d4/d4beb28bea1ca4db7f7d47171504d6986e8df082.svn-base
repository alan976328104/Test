package ac.drsi.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * 用于读取文本文档
 * @author CZK
 *
 */
public class ReadTxtUtils {
	/**
	 * 读取文本文档
	 * @param filePath
	 * @return
	 */
	  public static List<String> readTxtFile(String filePath){
		  List<String> list = new ArrayList<String>();
	        try {
	                String encoding="UTF-8";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ //判断文件是否存在
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);//考虑到编码格式
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                       // System.out.println(lineTxt);
	                        list.add(lineTxt);
	                    }
	                    read.close();
	                    return list;
	        }else{
	            System.out.println("找不到指定的文件");
	            return null;
	        }
	        } catch (Exception e) {
	            System.out.println("读取文件内容出错");
	            e.printStackTrace();
	            return null;
	        }
	     
	    }
	     /**
	      * 测试是否可以使用
	      * @param argv
	      */
	    public static void main(String argv[]){
	        String filePath = "C:\\Users\\Administrator\\Desktop\\test.txt";
//	      "res/";
	        readTxtFile(filePath);
	    }
}
