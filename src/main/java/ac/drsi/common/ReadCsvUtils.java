package ac.drsi.common;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.drsi.nestor.entity.OpenCSV;

import com.alibaba.fastjson.JSONException;
/**
 * 2019年3月20日 曹泽凯
 * 用于读取CSV文件
 * @author CZK
 */
public class ReadCsvUtils {
	//static int size=1;//主要是为了控制循环的次数，因为是定时刷，每次刷的文件行数可能不一样
  //  static long chars=0;//chars指的是字符数
	
	
	
  /**
   * 2019年3月20日 曹泽凯
   * 读取文件内容
   * @param fileName 文件名称及路径
   * @param size 文件大小
   * @param page 文件页码
   * @param chars 偏移量
   * @return
   */
      public static List<OpenCSV> readANDwrite(String fileName,Integer size,Integer page,long chars) {
            
            //大集合，以sessionid为键，以一次session的所有访问记录list为值进行存储
          List<OpenCSV> list = new ArrayList<>();
            
            //一次session的访问记录集合
            File file = new File(fileName);
            
            //java提供的一个可以分页读取文件的类,此类的实例支持对随机访问文件的读取和写入
            RandomAccessFile rf = null;
            
            String tempString = null;
            try {
                
                    //初始化RandomAccessFile，参数一个为文件路径，一个为权限设置，这点与Linux类似，r为读，w为写
                rf = new RandomAccessFile(fileName, "rw");
                
                //设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作
                rf.seek(chars);
                
                //获取文件的行数
              //  int fileSize = getTotalLines(file);
                    for (int i = 0; i < size; i++) {//从上一次读取结束时的文件行数到本次读取文件时的总行数中间的这个差数就是循环次数
                    
                    //一行一行读取
                    tempString = rf.readLine();
                    //文件中文乱码处理
                    tempString = tempString.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                    tempString = tempString.replaceAll("\\+", "%2B");
                    tempString = java.net.URLDecoder.decode(tempString, "GB2312");
                    String str[] = tempString.split(",");
                    //将字符串JSON转换为实体JSON，以便能通过key取value
                    list.add(new OpenCSV(str[0],str[1]));
                  //  System.out.println(tempString);
                }
                //返回此文件中的当前偏移量。 
                chars = rf.getFilePointer();
                System.out.println(chars);
              //  size=fileSize;
            } catch (IOException e) {
                e.printStackTrace();
            }catch(JSONException j){
                
            } finally {
                if (rf != null) {
                    try {
                        rf.close();
                    } catch (IOException e1) {
                    }
                }
            }
            return list;
        }
      //获取文件的行数
    public  static int getTotalLines(File file) throws IOException {
            FileReader in = new FileReader(file);
            LineNumberReader reader = new LineNumberReader(in);
            String s = reader.readLine();
            int lines = 0;
            while (s != null) {
                lines++;
                s = reader.readLine();
            }
            reader.close();
            in.close();
            return lines;
        }
     
      
      
      
      
      
      
      
      
      
      
      
      /**
       * 2019年3月20日 曹泽凯
       * 导出关联规则 
       * @param fileName 文件名称
       * @param begin 起始页
       * @param end 结束页
       * @return
       */
      public static List<String[]> readANDwriteforRelation(String fileName,Integer begin,Integer end) {
          long chars=0;
          //大集合，以sessionid为键，以一次session的所有访问记录list为值进行存储
    	  List<String[]> list = new ArrayList<>();
          //一次session的访问记录集合
          File file = new File(fileName);
          
          //java提供的一个可以分页读取文件的类,此类的实例支持对随机访问文件的读取和写入
          RandomAccessFile rf = null;
          
          String tempString = null;
          try {
              
                  //初始化RandomAccessFile，参数一个为文件路径，一个为权限设置，这点与Linux类似，r为读，w为写
             rf = new RandomAccessFile(fileName, "rw");
              
              //设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作
            //  rf.seek(chars);
              
              //获取文件的行数
            //  int fileSize = getTotalLines(file);
             if(begin>0){
            	 for (int i = 0; i <begin; i++) {
            		   rf.readLine();
				}
             }
                  for (int i = begin; i < end; i++) {//从上一次读取结束时的文件行数到本次读取文件时的总行数中间的这个差数就是循环次数
                  
                  //一行一行读取
                  tempString = rf.readLine();
                  if(tempString!=null){
                  //文件中文乱码处理
                  tempString = tempString.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                  tempString = tempString.replaceAll("\\+", "%2B");
                  tempString = java.net.URLDecoder.decode(tempString, "GB2312");
                  String str[] = tempString.split(",");
                  //将字符串JSON转换为实体JSON，以便能通过key取value
                  list.add(str);
                  }
                //  System.out.println(tempString);
              }
              //返回此文件中的当前偏移量。 
           //   chars = rf.getFilePointer();
            //  System.out.println(chars);
            //  size=fileSize;
          } catch (IOException e) {
              e.printStackTrace();
          }catch(JSONException j){
              
          } finally {
              if (rf != null) {
                  try {
                      rf.close();
                  } catch (IOException e1) {
                  }
              }
          }
          return list;
      }
      
      
      
      
}
