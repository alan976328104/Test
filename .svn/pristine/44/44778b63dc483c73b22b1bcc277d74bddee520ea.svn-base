package ac.drsi.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ac.drsi.nestor.entity.OpenCSV;
//1908039
//61057200
//F:\\files\\csv\\p2.csv
/**
 * 测试工具类用于测试分页查询CSV大文件
 * @author CZK
 *
 */


public class testUtils {
	
	 static String URL="D:\\测试";
	
	public static void main(String[] args) throws Exception {
		File file=new File(URL);
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
		digui( URL, 0);

	}
	
	
	public static void  digui(String name,int count) throws IOException{
		String filename =name+"\\测试";
		
		for (int i = 0; i < 5; i++) {
			
		File file=new File(filename+i);
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
			
			for (int j = 0; j < 5; j++) {
				
				File file1=new File(filename+i+"\\测试"+j);
				if(!file1.exists()){//如果文件夹不存在
					file1.mkdir();//创建文件夹
				}
					
					for (int k = 0; k < 5; k++) {
						
						File file2=new File(filename+i+"\\测试"+j+"\\测试"+k);
						if(!file2.exists()){//如果文件夹不存在
							file2.mkdir();//创建文件夹
						}
						
						for (int l = 0; l < 5; l++) {
							
							File file3=new File(filename+i+"\\测试"+j+"\\测试"+k+"\\测试"+l);
							if(!file3.exists()){//如果文件夹不存在
								file3.mkdir();//创建文件夹
							}
							
							
							for (int m = 0; m < 5; m++) {
								
								File file4=new File(filename+i+"\\测试"+j+"\\测试"+k+"\\测试"+l+"\\测试"+m);
								if(!file4.exists()){//如果文件夹不存在
									file4.mkdir();//创建文件夹
								}
								
								
								for (int n = 0; n < 5; n++) {
									BufferedWriter bw;
									bw = new BufferedWriter(new FileWriter(filename+i+"\\测试"+j+"\\测试"+k+"\\测试"+l+"\\测试"+m+"\\"+System.currentTimeMillis()+n+".txt"));
									bw.write("Hello I/O!");//在创建好的文件中写入"Hello I/O"
									bw.close();//一定要关闭文件
									
								}
								
							}
							
							
						}
						
					}
				
			}
		
		
		}
	
		
	
	}	
}
