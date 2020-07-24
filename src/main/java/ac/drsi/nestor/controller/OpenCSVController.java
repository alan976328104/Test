package ac.drsi.nestor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONException;

import ac.drsi.common.ReadTxtUtils;
import ac.drsi.nestor.dao.OpenCSVDao;
import ac.drsi.nestor.dao.SVDS_FilesDao;
import ac.drsi.nestor.entity.OpenCSV;
import ac.drsi.nestor.entity.ResultBean;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_SessionService;

@RestController
/**
 * 用于打开CSV文件
 * @author CZK
 *
 */
public class OpenCSVController {
	@Autowired
	SVDS_FilesDao dao; 
	@Autowired
	OpenCSVDao csvDao;
	@Autowired
	SVDS_SessionService sessionService;
	/**
	 * 分页在线打开csv文件
	 * @param fileid文件id
	 * @param page页码
	 * @param rows每页显示多少条
	 * @param state 预览打开还是全部打开 1为预览打开 0为全部打开
	 * @param session
	 * @param request
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/openCsv", method = RequestMethod.POST)
	public ResultBean openCsv(Integer fileid,Integer page,String rows,Integer state,HttpSession session,HttpServletRequest request) throws Exception{
		 
		 SVDS_User user  =sessionService.getSessionByIp(request);//获得用户id
		System.out.println("page:"+page+"rows"+rows);
		System.out.println("fileid"+fileid);
		if(csvDao.isnull(user.getUserId())==0){ 
			csvDao.insertCSVtemp(user.getUserId(), 0,1);//第一次打开该文件将文件插入到数据库临时表中
		}
		SVDS_Files file= dao.getFilesById(fileid);//通过文件id获得文件
		

		System.out.println(user.getUserId());
		System.out.println(file.getFileUrl());
		System.out.println(csvDao.isnull(user.getUserId()));
		System.out.println(csvDao.getchars(user.getUserId(),page));
		if(state==0){ //如果是全部打开
			Integer total=0;
			//System.out.println();
			if(session.getAttribute("CSVfilesize"+fileid)==null){
				 total=getTotalLines(file.getFileUrl()); //获得共有多少也
				 session.setAttribute("CSVfilesize"+fileid, total);
				 //System.out.println("@@@@@@@@@@@@@@@@@");
			}else{
				//System.out.println("$$$$$$$$$$$$$$$$$$$");
				 total=(Integer) session.getAttribute("CSVfilesize"+fileid);//将全部页码存入session
			}
			System.out.println("total:"+total);
			if(total<1000){ //将total转为具体页码
				total=1;
			}else
			if(total%1000==0){
				total=total/1000;
			}else
			
			{
			total = total/1000+1	;
			}
			
			return new ResultBean(total, readANDwrite(file.getFileUrl(),1000,page));
		}else{
			return new ResultBean(1000, readANDwrite(file.getFileUrl(),1000,page));	
		}
		
	}
	 /**
	  * 用于获取文件页码
	  * @param page
	  * @param fileid
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/requestPage")
	 public Integer requestPage(Integer page,Integer fileid,HttpServletRequest request){
		 
		 return 0;
	 }
	 
	 /**
	  * 删除数据库临时的CSV缓存
	  * @param session
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/delCSV", method = RequestMethod.POST)
	 public Integer delCSV(HttpSession session,HttpServletRequest request){
		 System.out.println(session==null);
		 SVDS_User user  =sessionService.getSessionByIp(request);
		 return csvDao.delCsvTemmp(user.getUserId());
	 }
	 /**
	     * 读取文件内容
	     * @param fileName
	     * @throws FileNotFoundException 
	     */
	      public  List<OpenCSV> readANDwrite(String fileName,Integer pageSize,Integer pageNo) throws Exception {
	            
	            //大集合，以sessionid为键，以一次session的所有访问记录list为值进行存储
	          List<OpenCSV> list = new ArrayList<>();
	            
	          FileReader in = new FileReader(new File(fileName));  
	  		LineNumberReader reader = new LineNumberReader(in);  
	  		String s = "";  
	  		/*if (lineNumber <= 0 || lineNumber > getTotalLines(sourceFile)) {  
	  		    System.out.println("不在文件的行数范围(1至总行数)之内。");  
	  		    System.exit(0);  
	  		}  */
	  		int startRow = (pageNo - 1) * pageSize + 1;
	  		int endRow = pageNo * pageSize;
	  		int lines = 0;  
	  		System.out.println("startRow:"+startRow);
	  		System.out.println("endRow:"+endRow);
	  		while (s != null) {  
	  		    lines++;  
	  		    s = reader.readLine();  
	  		    if(lines >= startRow && lines <= endRow) {  
	  		    	if(s!=null){
	  		    		String arr[]=s.split(",");
		  		    	if(arr.length==2){
		  		    		list.add(new OpenCSV(arr[0], arr[1]));
		  		    	}	
	  		    	}
	  		    	
	  		       // System.out.println("line:"+lines+":"+s);  
	  		        //System.exit(0);  
	  		    }  
	  		    
	  		  if(lines>endRow){
			    	reader.close();  
					in.close();  
					break;
			    }
	  		}  
	  		reader.close();  
	  		in.close();  
	            return list;
	        }
	      //获取文件的行数
	     public  static int getTotalLines(String file) throws IOException {
	    	  
	    	  
	            FileReader in = new FileReader(new File(file));
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
	       * 在线打开Txt文档
	       * @param fileid
	       * @return
	       */
	      @RequestMapping(value = "/OpenTxt", method = RequestMethod.GET)
	 	 public List<String> OpenTxt(Integer fileid){
	 		System.out.println("fileid:"+fileid);
	 		 return ReadTxtUtils.readTxtFile(dao.getFilesById(fileid).getFileUrl());
	 	 }
	      /**
	       * 通过文件id获取文件名称
	       * @param id
	       * @return
	       */
	      @RequestMapping(value = "/getcsvfileNameByid", method = RequestMethod.GET)
	      public String getfileNameByid(Integer id){
	    	  
	    	  return csvDao.getfileNameByid(id);
	      }
	      /**
	       * 在线打开图片文件
	       * @param response
	       * @param fileId文件id
	       * @throws Exception
	       */
	      @RequestMapping(value = "/openJpg", method = RequestMethod.GET)
	      public void openJpg(HttpServletResponse response,Integer fileId) throws Exception{
	    	  
	    	  response.setContentType("text/html; charset=UTF-8");
	    	  response.setContentType("image/jpeg");
	            //String fullFileName = getRealPath();
	            FileInputStream fis = new FileInputStream(dao.getFilesById(fileId).getFileUrl());
	            OutputStream os = response.getOutputStream();
	            try {
	                int count = 0;
	                byte[] buffer = new byte[1024 * 1024];
	                while ((count = fis.read(buffer)) != -1)
	                    os.write(buffer, 0, count);
	                os.flush();
	            } catch (IOException e) {
	                e.printStackTrace();
	            } finally {
	                if (os != null)
	                    os.close();
	                if (fis != null)
	                    fis.close();
	            }
	      }
	      
	      /**
	       * 在线打开视频文件
	       * @param request
	       * @param response
	       * @param fileId文件id
	       * @return
	       */
	      @RequestMapping(value = "/openVideo", method = RequestMethod.GET)
	      public String getVideos(HttpServletRequest request, HttpServletResponse response,Integer fileId)
	      {
	    	  String url =  csvDao.OpenVideo(fileId);
	          return  url ;
	      }
	 
	
}
