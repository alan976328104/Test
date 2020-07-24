package ac.drsi.nestor.service;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ac.drsi.common.DateUtils;
import ac.drsi.common.ExUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.common.SolrUtils;
import ac.drsi.nestor.dao.FolderUpDao;
import ac.drsi.nestor.dao.MenuDao;
import ac.drsi.nestor.dao.SVDS_AliasDao;
import ac.drsi.nestor.dao.SVDS_FilesDao;
import ac.drsi.nestor.dao.SVDS_MenuDao;
import ac.drsi.nestor.dao.SVDS_SecurityDao;
import ac.drsi.nestor.dao.SVDS_UserDao;
import ac.drsi.nestor.dao.SVDS_VisitedDao;
import ac.drsi.nestor.dao.infoDao;
import ac.drsi.nestor.entity.AddFileBean;
import ac.drsi.nestor.entity.FileAndid;
import ac.drsi.nestor.entity.SVDS_Alias;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SVDS_Visited;
import ac.drsi.nestor.entity.Tabs;

@Service
public class FolderUpService2 {
	@Autowired
	FolderUpDao dao;
	@Autowired
	SVDS_FilesDao svds_dao;
	@Autowired
	SVDS_SecurityDao SecurityDao;
	@Autowired
	SVDS_MenuDao menudao;
	@Autowired
	SVDS_VisitedDao svds_VisitedDao;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	MenuDao menudao2;
	@Autowired
	SVDS_UserDao userdao2;
	@Autowired
	infoDao infodao;
	@Autowired
	SVDS_AliasDao alisdao;
	
	
	
	
	public void saveMultiFile(String basePath, MultipartFile[] files,
			Integer menuid, Integer miji,String alisname,Integer alistype, SVDS_User user,HttpServletRequest request) throws UnknownHostException  {
		// System.out.println(files.length);
		/**
		 * 判断数据文件是否为空
		 */
		if (files == null || files.length == 0) {
			return;
		}
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}
		String url="";
		System.out.println("basePath:"+basePath);
		for (MultipartFile file : files) {//循环所有数据文件
			String filePath = basePath + "/" + file.getOriginalFilename();
			
			url=basePath + "/" +filePath.split("/")[1];
			System.out.println("url:"+url);
			makeDir(filePath);//将数据文件存入服务器中
			File dest = new File(filePath);
			try {
				file.transferTo(dest);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println("filename"+files[0].getOriginalFilename()+"filessize"+files.length);
		//System.out.println(files.length);
		if(files.length==1&&files[0].getOriginalFilename().indexOf("基本信息")>=0){
			System.out.println("进来了");
			insertinfo(basePath + "/" +files[0].getOriginalFilename(), menuid);
		}else{
			traverseFolder1(url, menuid, miji, alisname, alistype, user,request);
		}
		
	}
	
	
	
	public void traverseFolder1(String path,Integer menuid, Integer miji,String alisname,Integer alistype, SVDS_User user,HttpServletRequest request) throws UnknownHostException {
        int fileNum = 0;
        int folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
        	  if(!file.isDirectory()){
        		Tabs t = new Tabs("其他数据", 1, menuid, 0);
              	int id = dao.insertTab(t);
              	int	tabsid=t.getId();
              	insertfile(file, tabsid, menuid, miji, alisname, alistype, user, request);
              	return;
              }
        	
        	
            LinkedList<FileAndid> list = new LinkedList<FileAndid>();
            File[] files = file.listFiles();
            Integer othertabsid= 0;
            if(isother(file,0)){
            	Tabs t = new Tabs("其他数据", 1, menuid, 0);
            	int id = dao.insertTab(t);
            	othertabsid=t.getId();
            	//创建其他文件
            }
            
          
            
            for (File file2 : files) {
                if (file2.isDirectory()) {
                 // System.out.println("文件夹:" + file2.getName());
                	Tabs t = new Tabs(file2.getName(), 1, menuid, 0);
                	Integer id = dao.insertTab(t);
                	System.out.println("----------tabsid:"+t.getId());
                    list.add(new FileAndid(file2, t.getId(),0,1));
                    
                    folderNum++;
                } else {
                	if(file2.getName().indexOf("基本信息")<0){
                		insertfile(file2, othertabsid, menuid, miji, alisname, alistype, user, request);
                   
                	}else{
                	insertinfo(file2.getAbsolutePath(), menuid);//添加基本信息
                    System.out.println("这是基本信息");
                	}
                	
                	 fileNum++;
            }
            }
          FileAndid temp_file;
          while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                 boolean b=isother(temp_file.getFile(), 1);
                if(b){
                	Tabs t = new Tabs("其他数据", temp_file.getLv()+1, menuid, temp_file.getId());
                	int id = dao.insertTab(t);
                	othertabsid=t.getId();
                }
                
                System.out.println(temp_file.getFile().getName());
                files = temp_file.getFile().listFiles();
                
                
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                    	Tabs t = new Tabs(file2.getName(),temp_file.getLv()+1, menuid, temp_file.getId());
                    	int id=dao.insertTab(t);
                        list.add(new FileAndid(file2,t.getId(),temp_file.getId(),temp_file.getLv()+1));
                        folderNum++;
                    } else {
                    	if(b){
                    		insertfile(file2, othertabsid, menuid, miji, alisname, alistype, user, request);
                    	}else{
                    		insertfile(file2, temp_file.getId(), menuid, miji, alisname, alistype, user, request);
                    	}
                    
                        System.out.println("文件:" + file2.getName());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);

    }

	
	private void makeDir(String filePath) {
		if (filePath.lastIndexOf('/') > 0) {
			String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}
	public static void main(String[] args) {
		FolderUpService2 f = new FolderUpService2();
		File f2 = new File("f:\\files/新建文件夹");
		
		System.out.println(f.isother(f2, 0));
	}
	
	
	public boolean isother(File file,int state){
		if (file.exists()) {
			
		     File[] files = file.listFiles();
        if (null == files || files.length == 0) {
            System.out.println("文件夹是空的!");
            return false;
        }
        int filenum=0;
        int folder=0;
        
        
        for (File file1 : files) {
			
        	   if (file1.isDirectory()) {
        		   
        		   folder++;
        	   }else{
        		   if(state==0){
        			   if(file1.getName().indexOf("基本信息")>0){
        				   
        			   }else{
        				   filenum++;   
        			   }
        			   
        		   }else{
        			   filenum++; 
        		   }
        		  
        	   }
			
		}
        if(state==0){
        	 if(filenum>0){
             	return true;
             }
        }else{
        	 if(filenum>0&&folder>0){
              	return true;
              }
        }
       
       
        
        return false;
        
        
		}else{
			return false;
		}
	
	
}

	
	
	public void insertfile(File file2,Integer tabsid,Integer menuid, Integer miji,String alisname,Integer alistype, SVDS_User user,HttpServletRequest request) throws UnknownHostException{
		SVDS_Files svdsfile=new SVDS_Files();
		svdsfile.setFileSize(file2.length()+"");
		svdsfile.setFileName(file2.getName());
		svdsfile.setFileUrl(file2.getAbsolutePath());
		svdsfile.setFileDate(DateUtils.getDate());
		svdsfile.setUser(user);
		svdsfile.setFileVersion("1.0");
		svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
		svdsfile.setState(0);
		String location[] =file2.getAbsolutePath().split("/");
		String location1="";
		for (int j = 0; j < location.length; j++) {
			location1+=location[j]+"→";
		}
		
		svdsfile.setLocation(location1.substring(0,location1.length()-1));
	//	svdsfile.setTabsId(dao.getTbsByName("其他数据", menuid, 1));
		svdsfile.setTabsId(tabsid);
		svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
		//svdsfile.setMajor(user.getMajor());
		SVDS_Menu menu = menudao.getMenuById(menuid);
		svdsfile.setMenu(menu);
		Integer id=svds_dao.insertFiles(svdsfile);
		System.out.println("fileid:"+svdsfile.getFileId());
		SVDS_Files file3=svds_dao.getFilesById(svdsfile.getFileId());
		if(isDocument(svdsfile.getFileName())){
			SolrUtils.addFile(file2,svdsfile.getFileId().toString());
			
		}else{
			System.out.println("这不是文档");
		}
		
		if(alisname.equals("")){
			
		}else{
			isertalis(file3.getFileId(), alisname, alistype, user);
		}
		
		logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),user, file3.getMenu(), file3,IpAddressUtils.getIpAddress(request),"成功"));
		svds_VisitedDao.insertVisited(new SVDS_Visited(user, file3, 0, DateUtils.getDate()));
		
		
		 System.out.println("文件:" + file2.getName());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 List<String[]> insertinfolist;
	public void insertinfo(String url,Integer menuid) {
		 insertinfolist = new ArrayList<>();
		 try {
			 System.out.println("url"+url);
			 menudao2.delInfodatabyid(menuid);
			dao.insterInfo(url, menuid);
			 if(url.indexOf("xlsx")>=0||url.indexOf("XLSX")>=0){
				 ExUtils reader = new ExUtils() {
						public void getRows(int sheetIndex, int curRow,
								List<String> rowList) {
							String [] str = new String[rowList.size()];
							for (int i = 0; i < rowList.size(); i++) {
								str[i]=rowList.get(i);
								//System.out.print(rowList.get(i)+"\t");
							}
							insertinfolist.add(str);
							System.out.println();
							
							
						}
					};
					System.out.println(infodao.getInfoByid(menuid).getUrl());
					reader.process(url, 1);
					System.out.println(insertinfolist.size());
			 }else{
				 insertinfolist=ExUtils.readExcel(new File(url));
				 
			 }
		
				for (int i = 0; i < insertinfolist.size(); i++) {
					System.out.println(Arrays.toString(insertinfolist.get(i)));
				}
				
				for (int i = 0; i < insertinfolist.size(); i++) {
					String[] arr = insertinfolist.get(i);
					System.out.println("--------------------------------");
						if(arr.length==2){
							infodao.insterInfo("&", arr[0], arr[1], menuid);	
						}else if(arr.length==3){
							 infodao.insterInfo(arr[0], arr[1], arr[2], menuid);	
						}
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			 
		 
	 }
	
	
	 public boolean isDocument(String name){
		 if(name.indexOf("doc")>=0||
				 name.indexOf("docx")>=0||
				 name.indexOf("DOC")>=0||
				 name.indexOf("DOCX")>=0||
				 name.indexOf("xls")>=0||
				 name.indexOf("XLSX")>=0||
				 name.indexOf("pdf")>=0||
				 name.indexOf("PDF")>=0||
				 name.indexOf("txt")>=0 ||
				 name.indexOf("TXT")>=0
				 ){
			return true;
		 }
		 
		 
		 return false;
	 }
	 
	 
	 
	 
	 
	 
	 public Integer isertalis(Integer fileid,String alisname,Integer alistype,SVDS_User user){
		 String[] str = alisname.split(",");
	for (int i = 0; i < str.length; i++) {
		
		 Integer ishavaid = alisdao.ishavaByid(str[i], alistype);
		 System.out.println();
		 if(ishavaid==null){
			 SVDS_Alias alis = new SVDS_Alias();
			 alis.setAliasDate(DateUtils.getDate());
			// alis.setAliasId(0);
			 alis.setLv(1);
			 alis.setAliasName(str[i]);
			 alis.setAliasType(alistype);
			 alis.setIcon("../assets/images/alias.png");
			 alis.setParentId(alistype);
			 if(alistype!=1){
				 alis.setUser(user);
			 }
			
			 
			 	Integer aliasid=alisdao.insertAlias(alis);
			 	System.out.println("aliasid:"+aliasid);
			 	alisdao.insertAliasFile(aliasid, fileid,DateUtils.getDate(),user.getUserId());
			 	System.out.println("添加标签");
		 }else{
			 System.out.println("添加标签");
			 alisdao.insertAliasFile(ishavaid, fileid,DateUtils.getDate(),user.getUserId());
		 }
			
		}
		 return 1;
	 }
}
