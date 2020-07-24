package ac.drsi.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 用于备份数据库所有表格
 * @author CZK
 *
 */
public class OralcUtils {
	public static void main(String[] args) {
		  //数据库的名称要使用大写
			//backups("orienttdm_zfy", "orienttdm_zfy", "ORCL", "D:/beifen","beifenming");
			//recovery("orienttdm_zfy", "orienttdm_zfy", "ORCL", "D:/beifen/ORCL.dmp");
	//	delfile("D:\\beifen\\tt");
	//	backupsfortablename("orienttdm_zfy", "orienttdm_zfy", "ORCL", "D:/beifen", "SVDS_LOG", "biao1");
		}
		
		 /**
		  * 备份指定用户数据库
		  * @param userName
		  * 用户名
		  * @param password
		  * 密码
		  * @param databseName
		  * 数据库名称
		  * @param address
		  * 保存地址 d:/oracle/backup
		  */
		 public static void backups(String userName,String password,String databseName,String address,String filename){
		  //拼接dos命令进行数据库备份
		  StringBuffer exp = new StringBuffer("exp ");
		  exp.append(userName);
		  exp.append("/");
		  exp.append(password);
		  exp.append("@");
		  exp.append(databseName);
		  exp.append(" file=");
		  /*
		   * 得到存储地址的最后一个字符，如果有“\”就直接拼装地址，如果没有\就加上/然后拼装数据库名称
		   * */
		  String maxIndex = address.substring(address.length()-1);
		  if("/".equals(maxIndex)||"\\".equals(maxIndex)) {
		   exp.append(address);
		  } else {
		   address = address+"\\";
		   exp.append(address);
		  }
		  File file = new File(address);
		  //如果文件不存在那么就重新创建，包括父目录
		  if(!file.exists()) {
		   file.mkdirs();
		  }
		  exp.append(filename);
		  exp.append(".dmp");
		  System.out.println("开始备份");
		  try{
		   System.out.println(exp.toString());
		   Process p = Runtime.getRuntime().exec(exp.toString());
		   InputStreamReader isr = new InputStreamReader(p.getErrorStream());
		   BufferedReader br = new BufferedReader(isr);
		   String line = null;
		   while((line = br.readLine())!=null) {
		    if(line.indexOf("错误")!=-1) {
		     break;
		    }

		   }
		   p.destroy();
		   p.waitFor();
		   System.out.println("备份成功！");
		  } catch (IOException e) {
		   System.out.println(e.getMessage());

		  } catch (InterruptedException e) {
		   e.printStackTrace();
		  }
		 }

		 //备份指定表
		 public static void backupsfortablename(String userName,String password,String databseName,String address,String tablename,String filename){
			  //拼接dos命令进行数据库备份
			  StringBuffer exp = new StringBuffer("exp ");
			  exp.append(userName);
			  exp.append("/");
			  exp.append(password);
			  exp.append("@");
			  exp.append(databseName);
			  exp.append(" file=");
			  /*
			   * 得到存储地址的最后一个字符，如果有“\”就直接拼装地址，如果没有\就加上/然后拼装数据库名称
			   * */
			  String maxIndex = address.substring(address.length()-1);
			  if("/".equals(maxIndex)||"\\".equals(maxIndex)) {
			   exp.append(address);
			  } else {
			   address = address+"\\";
			   exp.append(address);
			  }
			  File file = new File(address);
			  //如果文件不存在那么就重新创建，包括父目录
			  if(!file.exists()) {
			   file.mkdirs();
			  }
			  exp.append(filename);
			  exp.append(".dmp");
			  
			  exp.append(" tables=(");
			  exp.append(tablename);
			  exp.append(")");
			  System.out.println("开始备份");
			  try{
			   System.out.println(exp.toString());
			   Process p = Runtime.getRuntime().exec(exp.toString());
			   InputStreamReader isr = new InputStreamReader(p.getErrorStream());
			   BufferedReader br = new BufferedReader(isr);
			   String line = null;
			   while((line = br.readLine())!=null) {
			    if(line.indexOf("错误")!=-1) {
			     break;
			    }

			   }
			   p.destroy();
			   p.waitFor();
			   System.out.println("备份成功！");
			  } catch (IOException e) {
			   System.out.println(e.getMessage());

			  } catch (InterruptedException e) {
			   e.printStackTrace();
			  }
			 }
		 
		 //还原指定用户数据库
		 public static void recovery(String userName,String password,String dataBaseName,String address) {
		  //拼接DOS命令进行数据库还原
		  StringBuffer imp = new StringBuffer("imp ");
		  imp.append(userName);
		  imp.append("/");
		  imp.append(password);
		  imp.append("@");
		  imp.append(dataBaseName);
		  imp.append(" file=");
//imp orienttdm_zfy/orienttdm_zfy file=D:\beifen\ORCL.dmp full=y ignore=y
//imp orienttdm_zfy/orienttdm_zfy@ORCL file=D:/beifen\ORCL.dmp		  
		  imp.append(address);
		  imp.append(" full=y ignore=y");
		  File file = new File(address);
		  //判断文件是否存在，存在才进行恢复，不存在就不恢复
		  if(file.exists()) {
		   System.out.println("开始恢复...");
		   try{
		    System.out.println(imp.toString());
		    Process p = Runtime.getRuntime().exec(imp.toString());
		    InputStreamReader isr = new InputStreamReader(p.getErrorStream());
		    BufferedReader br = new BufferedReader(isr);
		    String line = null;
		    while((line=br.readLine())!=null) {
		     if(line.indexOf("错误")!=-1) {
		      break;
		     }
		    }
		    p.destroy();
		    System.out.println("恢复成功！");
		    p.waitFor();


		   }catch(IOException e) {
		    System.out.println(e.getMessage());
		   } catch (InterruptedException e) {
		    System.out.println(e.getMessage());
		   }
		  }
		 }

//删除备份文件		 
		 public static Integer delfile(String url){
		
			 StringBuffer imp = new StringBuffer("cmd /c del ");
			 imp.append(url);
			 imp.append(".dmp");
			 	
			   try {
					 System.out.println(imp);
				Process p = Runtime.getRuntime().exec(imp.toString());
				    InputStreamReader isr = new InputStreamReader(p.getErrorStream());
				    BufferedReader br = new BufferedReader(isr);
				    String line = null;
				    while((line=br.readLine())!=null) {
				     if(line.indexOf("错误")!=-1) {
				      break;
				     }
				    }
				    p.destroy();
				    System.out.println("删除成功！");
				    p.waitFor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return 1;
		 }
		}
