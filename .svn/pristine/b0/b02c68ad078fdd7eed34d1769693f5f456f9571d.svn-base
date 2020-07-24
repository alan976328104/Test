package ac.drsi.nestor.service;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

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
import ac.drsi.nestor.entity.SVDS_Alias;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SVDS_Visited;
import ac.drsi.nestor.entity.Tabs;
/**
 * 2019年3月19日 曹泽凯 
 * 关联数据文件批量上传逻辑层
 * @author CZK
 *
 */
@Service
public class FolderUpService {
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
	
	/**
	 * 2019年3月19日 曹泽凯 
	 * 批量上传文件
	 * @param basePath
	 * @param files 文件集合
	 * @param menuid菜单id
	 * @param miji 文件密级
	 * @param alisname 标签名称
	 * @param alistype标签类型
	 * @param user 用户id
	 * @throws UnknownHostException 
	 */
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

		List<AddFileBean> list = new ArrayList<>();
		
		
		//List<String> size = new ArrayList<>();
		for (MultipartFile file : files) {//循环所有数据文件
			String[] data = file.getOriginalFilename().split("/");//将数据文件结构分割为数组
		
				
				
			
			
			
				//size.add(file.getSize()+"");
			
			
			
			if(data.length<=1){//如果数据层级结构小于1
				String [] sdata = new String[2];
				sdata[0]="test@";
				sdata[1]=data[0];
				list.add(new AddFileBean(sdata, file.getSize()+""));	
			}else{
				list.add(new AddFileBean(data, file.getSize()+""));	
			}
			
		
			System.out.println("---------------"+file.getOriginalFilename());
			String filePath = basePath + "/" + file.getOriginalFilename();
			makeDir(filePath);//将数据文件存入服务器中
			File dest = new File(filePath);
			try {
				file.transferTo(dest);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
		}
		
		List<AddFileBean> newlist = new ArrayList<>();
		/**
		 * 用于去除上传会多一个文件夹名称为"."的文件
		 */
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getArr()[list.get(i).getArr().length-1].equals(".")){
				
			}else{
				newlist.add(list.get(i));
			}
		}
		
		
	//	int count = 0;
		addTabs(newlist,menuid);//添加生成选项卡
		
		
		
		addFiles(newlist, menuid, miji, user, basePath,alisname, alistype, request);//向数据库中添加文件信息以及文件索引
		
		
		
		
		
		
	}

	/**
	 * 2019年3月19日 曹泽凯 
	 * 确保目录存在，不存在则创建
	 * @param filePath
	 */
	private void makeDir(String filePath) {
		if (filePath.lastIndexOf('/') > 0) {
			String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}
/**
 * 2019年3月19日 曹泽凯 
 * 获取文件大小
 * @param list
 * @return
 */
	public int getSize(List<Tabs> list){
		int num=0;
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getName().equals("其他数据")){
				num++;
			}
		}
		
		return num;
	}
		
	 
/**
 * 2019年3月19日 曹泽凯 
 * 添加生成选项卡
 * @param arr 文件层级目录信息
 * @param menuid 菜单id
 */
	public void addTabs(List<AddFileBean> arr,Integer menuid) {
		List<String []> list = new ArrayList<>();
		for (int i = 0; i <arr.size(); i++) {
			list.add(arr.get(i).getArr());
		}
		addtab(list,menuid);
		
	}
	
	
	/**
	 * 2019年3月19日 曹泽凯 
	 * 添加选项卡为其他信息的选项卡
	 */
	List<Tabs> tabs = new ArrayList<>();
	public void addtab(List<String[]> arr,Integer menuid){
		tabs = new ArrayList<>();
		for (int i = 0; i < arr.size(); i++) {
			if(arr.get(i)[1].indexOf(".")>0&&arr.get(i)[1].indexOf("基本信息")<0&&arr.get(i).length-1==1){
				System.out.println("进来了--------------------------------");
				if(isrepeat("其他数据",tabs)){
					System.out.println("进来了---------------------2");
					Tabs t = new Tabs("其他数据", 1, menuid, 0);
					if(dao.getTabscount(t)==0){
						dao.insertTab(t);
					}else{
						t.setId(dao.getTabsidcountD(t));
					}
					tabs.add(t);
				}
				
			}else{
				if(arr.get(i)[1].indexOf("基本信息")<0){
					
				
				if(isrepeat(arr.get(i)[1],tabs)){
					Tabs t =new Tabs(arr.get(i)[1], 1, menuid, 0);
					if(dao.getTabscount(t)==0){
						dao.insertTab(t);
					}else{
						t.setId(dao.getTabsidcountD(t));
					}
					tabs.add(t);
				}
				}
			}
		}
		for (int i = 0; i < tabs.size(); i++) {
			addtab1(tabs.get(i),arr,menuid,2);
			//index = 2;
		}
		System.out.println(JSONArray.fromObject(tabs));
	}
		

	/**
	 * 2019年3月19日 曹泽凯 
	 * 添加五级选项卡
	 * @param tab 选项卡
	 * @param arr 文件层级结构
	 * @param menuid 菜单id
	 * @param lv 层级等级
	 */
	public void addtab1(Tabs tab,List<String[]> arr,Integer menuid,Integer lv){
			for (int i = 0; i < arr.size(); i++) {
				if(arr.get(i).length>lv&&(!tab.getName().equals("其他数据"))&&tab.getName().equals(arr.get(i)[lv-1])){
					
					if(arr.get(i)[lv].indexOf(".")>0&&arr.get(i).length-1==lv){
						if(isOtherdata(arr, lv, tab)){
						if(isrepeat("其他数据",tab.getChildren())){
							Tabs t  = new Tabs("其他数据", lv, menuid, tab.getId());
							if(dao.getTabscount(t)==0){
								dao.insertTab(t);
							}else{
								t.setId(dao.getTabsidcountD(t));
							}
							tab.getChildren().add(t);
						}
						}
					}else{
						if(isrepeat(arr.get(i)[lv],tab.getChildren())){
							Tabs t  =new Tabs(arr.get(i)[lv], lv, menuid, tab.getId());
							if(dao.getTabscount(t)==0){
								dao.insertTab(t);
							}else{
								t.setId(dao.getTabsidcountD(t));
							}
							tab.getChildren().add(t);
						}
						
					}
					
					
				}
			}
			
			
			if(lv<6){
			for (int i = 0; i < tab.getChildren().size(); i++) {
				addtab1( tab.getChildren().get(i),arr,menuid,tab.getChildren().get(i).getLv()+1);
				
			}
			
			
			}
		
	}
	
	/**
	 * 2019年3月19日 曹泽凯 
	 * 用于判断选项卡是否存在其他数据
	 * @param arr
	 * @param lv
	 * @param tab
	 * @return
	 */
	public boolean isOtherdata(List<String []> arr,Integer lv,Tabs tab){
		int count=0;
		for (int i = 0; i < arr.size(); i++) {
			if(arr.get(i).length>lv&&(!tab.getName().equals("其他数据"))&&tab.getName().equals(arr.get(i)[lv-1])){
				if(arr.get(i)[lv].indexOf(".")>0){
					
				}else{
					count++;	
				}
				
			}
		}
		
		if(count>0){
			return true;
		}
		return false;
		
	}
	/**
	 * 2019年3月19日 曹泽凯 
	 * 用于判断文件路径中是否已经生成了选项卡
	 * @param name
	 * @param tabs
	 * @return
	 */
	public boolean isrepeat(String name,List<Tabs> tabs){
		if(tabs.size()==0){
			return true;
		}
		for (int i = 0; i < tabs.size(); i++) {
			if(name.equals(tabs.get(i).getName())){
				return false;
			}
		}
		
		return true;
		
	}
		/**
		 * 2019年3月19日 曹泽凯 
		 * 获得文件目录结构长度
		 * @param arr数组
		 * @param name 文件名
		 * @return
		 */
	 public static int getarrsize(String []arr,String name){
		 int num = 0;
		 for (int i = 0; i < arr.length; i++) {
			if(arr[i].equals(name)){
				num++;
			}
		}
		 return num;
	 }
	 /**
	  * 2019年3月19日 曹泽凯 
	  * 获得文件大小
	  * @param arr 数组
	  * @return
	  */
	 public static int getarrfilesize(String []arr){
		 int num = 0;
		 for (int i = 0; i < arr.length; i++) {
			if(arr[i].indexOf(".")>0){
				num++;
			}
		}
		 return num;
	 }
	 /**
	  * 2019年3月19日 曹泽凯 
	  * 将数文件添加进入数据库
	  * @param data 文件目录结构
	  * @param menuid 菜单id
	  * @param miji 文件密级
	  * @param user 用户id
	  * @param basePath 文件路径
	  * @param alisname 标签名称
	  * @param alistype 标签类型
	 * @throws UnknownHostException 
	  */
	 public void addFiles(List<AddFileBean> data,Integer menuid,Integer miji,SVDS_User user,String basePath,String alisname, Integer alistype,HttpServletRequest request) throws UnknownHostException {
		 for (int i = 0; i <data.size(); i++) {
		
				if(data.get(i).getArr().length>1){
					if(data.get(i).getArr()[1].indexOf(".")>0&&data.get(i).getArr().length-1==1){
						if(data.get(i).getArr()[1].indexOf("基本信息")>=0){
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
								
							}
							
							insertinfo(url, menuid);
						}else{
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[1]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
						//	svdsfile.setTabsId(dao.getTbsByName("其他数据", menuid, 1));
							svdsfile.setTabsId(findtabsbyidother(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
							
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[1], DateUtils.getDate(), miji, userid, menuid, dao.getTbsByName("其他数据", menuid, 1));
							}
						}
					
					}
				}
			}
			
			
			for (int i = 0; i < data.size(); i++) {
				if(data.get(i).getArr().length>2){
					if(data.get(i).getArr()[2].indexOf(".")>0&&data.get(i).getArr().length-1==2){
						if(dao.ishave(menuid, data.get(i).getArr()[1], 1).size()>0){
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[2]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							svdsfile.setLocation(location.substring(0,location.length()-1));
							//svdsfile.setTabsId( dao.getTbsparidByName("其他数据", menuid, 2, dao.getTbsByName(data.get(i).getArr()[1], menuid, 1)));
							svdsfile.setTabsId(findtabsbyidother(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
						//	SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, menu, file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[2], DateUtils.getDate(), miji, userid, menuid, dao.getTbsparidByName("其他数据", menuid, 2, dao.getTbsByName(data.get(i)[1], menuid, 1)));
							}
						}else{
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[2]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
							//svdsfile.setTabsId(dao.getTbsByName(data.get(i).getArr()[1], menuid, 1));
							svdsfile.setTabsId(findtabsbyid(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
							
							//SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[2], DateUtils.getDate(), miji, userid, menuid, dao.getTbsByName(data.get(i)[1], menuid, 1));
							}
							}
						
					}
				}
			}
			
			for (int i = 0; i < data.size(); i++) {
				if(data.get(i).getArr().length>3){
					if(data.get(i).getArr()[3].indexOf(".")>0&&data.get(i).getArr().length-1==3){
						if(dao.ishave(menuid, data.get(i).getArr()[2], 2).size()>0){
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[3]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
							//svdsfile.setTabsId(dao.getTbsparidByName("其他数据", menuid, 3, dao.getTbsByName(data.get(i).getArr()[2], menuid, 2)));
							svdsfile.setTabsId(findtabsbyidother(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
							//SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[3], DateUtils.getDate(), miji, userid, menuid, dao.getTbsparidByName("其他数据", menuid, 3, dao.getTbsByName(data.get(i)[2], menuid, 2)));
							}
							}else{
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[3]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
							//svdsfile.setTabsId(dao.getTbsByName(data.get(i).getArr()[2], menuid, 2));
							svdsfile.setTabsId(findtabsbyid(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
							
						//	SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[3], DateUtils.getDate(), miji, userid, menuid, dao.getTbsByName(data.get(i)[2], menuid, 2));
							}
							}
						
					}
				}
			}
			
			
			for (int i = 0; i < data.size(); i++) {

				if(data.get(i).getArr().length>4){
					if(data.get(i).getArr()[4].indexOf(".")>0&&data.get(i).getArr().length-1==4){
						if(dao.ishave(menuid, data.get(i).getArr()[3], 3).size()>0){
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[4]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
							//svdsfile.setTabsId(dao.getTbsparidByName("其他数据", menuid, 4, dao.getTbsByName(data.get(i).getArr()[3], menuid, 3)));
							svdsfile.setTabsId(findtabsbyidother(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
						//	SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[4], DateUtils.getDate(), miji, userid, menuid, dao.getTbsparidByName("其他数据", menuid, 4, dao.getTbsByName(data.get(i)[3], menuid, 3)));
							}
							}else{
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[4]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
						//	svdsfile.setTabsId( dao.getTbsByName(data.get(i).getArr()[3], menuid, 3));
							svdsfile.setTabsId(findtabsbyid(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
							//SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[4], DateUtils.getDate(), miji, userid, menuid, dao.getTbsByName(data.get(i)[3], menuid, 3));
							}
							}
						
					}
				}
			
				
			}
			
			
			
			
			
			
			//5
			for (int i = 0; i < data.size(); i++) {

				if(data.get(i).getArr().length>5){
					if(data.get(i).getArr()[5].indexOf(".")>0&&data.get(i).getArr().length-1==1){
						if(dao.ishave(menuid, data.get(i).getArr()[4], 4).size()>0){
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[5]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
							//svdsfile.setTabsId(dao.getTbsparidByName("其他数据", menuid, 5, dao.getTbsByName(data.get(i).getArr()[4], menuid, 4)));
							svdsfile.setTabsId(findtabsbyidother(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
						//	SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[4], DateUtils.getDate(), miji, userid, menuid, dao.getTbsparidByName("其他数据", menuid, 4, dao.getTbsByName(data.get(i)[3], menuid, 3)));
							}
							}else{
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[5]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
						//	svdsfile.setTabsId( dao.getTbsByName(data.get(i).getArr()[4], menuid, 4));
							svdsfile.setTabsId(findtabsbyid(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
						//	svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
							//SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[4], DateUtils.getDate(), miji, userid, menuid, dao.getTbsByName(data.get(i)[3], menuid, 3));
							}
							}
						
					}
				}
			
			}
			
			
			//6
			for (int i = 0; i < data.size(); i++) {

				if(data.get(i).getArr().length>6){
					if(data.get(i).getArr()[6].indexOf(".")>0&&data.get(i).getArr().length-1==6){
						if(dao.ishave(menuid, data.get(i).getArr()[5], 5).size()>0){
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[6]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
							//svdsfile.setTabsId(dao.getTbsparidByName("其他数据", menuid, 6, dao.getTbsByName(data.get(i).getArr()[5], menuid, 5)));
							svdsfile.setTabsId(findtabsbyidother(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
						//	svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
						//	SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[4], DateUtils.getDate(), miji, userid, menuid, dao.getTbsparidByName("其他数据", menuid, 4, dao.getTbsByName(data.get(i)[3], menuid, 3)));
							}
							}else{
							String url=basePath;
							for (int j = 0; j < data.get(i).getArr().length; j++) {
								if(data.get(i).getArr()[j].equals("test@")){
									
								}else{
									url+="/"+data.get(i).getArr()[j];	
								}
							}
							
							SVDS_Files svdsfile=new SVDS_Files();
							svdsfile.setFileSize(data.get(i).getSize());
							svdsfile.setFileName(data.get(i).getArr()[6]);
							svdsfile.setFileUrl(url);
							svdsfile.setFileDate(DateUtils.getDate());
							svdsfile.setUser(user);
							svdsfile.setFileVersion("1.0");
							svdsfile.setSecurity(SecurityDao.getSecurityById(miji));
							svdsfile.setState(0);
							String location =menudao2.findMenuByid(menuid)+"→";
							for (int j = 1; j < data.get(i).getArr().length; j++) {
								location+=data.get(i).getArr()[j]+"→";
							}
							
							svdsfile.setLocation(location.substring(0,location.length()-1));
							//svdsfile.setTabsId( dao.getTbsByName(data.get(i).getArr()[5], menuid, 5));
							svdsfile.setTabsId(findtabsbyid(menuid, data.get(i).getArr(), 0, 1));
							svdsfile.setFormat(svdsfile.getFileName().substring(svdsfile.getFileName().lastIndexOf(".")+1));
							//svdsfile.setMajor(user.getMajor());
							if(dao.ishaveFilenameoOrTabsid(svdsfile.getTabsId(), svdsfile.getFileName())==0){
							SVDS_Menu menu = menudao.getMenuById(menuid);
							svdsfile.setMenu(menu);
							Integer id=svds_dao.insertFiles(svdsfile);
							File fi =new File(url) ;
							System.out.println("fileid:"+svdsfile.getFileId());
							SVDS_Files file=svds_dao.getFilesById(svdsfile.getFileId());
							if(isDocument(svdsfile.getFileName())){
								SolrUtils.addFile(fi,svdsfile.getFileId().toString());
								
							}else{
								System.out.println("这不是文档");
							}
							
							if(alisname.equals("")){
								
							}else{
								isertalis(file.getFileId(), alisname, alistype, user);
							}
							//SolrUtils.addFile(fi,svdsfile.getFileId().toString());
							logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils.getDate(),
									user, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
							svds_VisitedDao.insertVisited(new SVDS_Visited(user, file, 0, DateUtils.getDate()));
							//dao.inserFileByFolderup(url, data.get(i)[4], DateUtils.getDate(), miji, userid, menuid, dao.getTbsByName(data.get(i)[3], menuid, 3));
							}
							}
						
					}
				}
			
			}
			
			
			
			
	 }
	 
	 List<String[]> insertinfolist;
	 /**
	  * 2019年3月19日 曹泽凯 
	  * 插入基本信息
	  * @param url 路径
	  * @param menuid 菜单id
	  */
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
	 
	 /**
	  * 2019年3月19日 曹泽凯 
	  * 是否存在标签
	  * @param fileid 文件id
	  * @param alisname 标签名
	  * @param alistype 标签类型
	  * @param user 操作用户
	  * @return
	  */
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
	 int id = 0;
	 /**
	  * 2019年3月22日 曹泽凯 
	  * 查询选项卡id
	  * @param menuid 菜单id
	  * @param arr 数组
	  * @param parentid 父级id
	  * @param lv 层级
	  * @return
	  */
	 public Integer findtabsbyid(Integer menuid ,String []arr,Integer parentid,Integer lv){
		 System.out.println("findtabsbyid:name"+arr[lv]+"parentid"+parentid+"menuid"+menuid);
		id= dao.gettabsidbyarr(arr[lv], parentid, menuid);
		lv++;
		if(arr.length-1>lv){
			findtabsbyid(menuid,arr,id,lv);
			
		}
		 return id;
	 }
	 /**
	  * 2019年3月19日 曹泽凯 
	  * 查询其他选项卡
	  * @param menuid菜单id
	  * @param arr 数组
	  * @param parentid 父id
	  * @param lv 层级
	  * @return
	  */
	 public Integer findtabsbyidother(Integer menuid ,String []arr,Integer parentid,Integer lv){
		
		 arr[arr.length-1]="其他数据";
		System.out.println("name"+arr[lv]+"parentid"+parentid+"menuid"+menuid);
		id= dao.gettabsidbyarr(arr[lv], parentid, menuid);
		 lv++;
		if(arr.length>lv){
			findtabsbyid(menuid,arr,id,lv);
		}
		 return id;
	 }
	 /**
	  * 2019年3月22日 曹泽凯 
	  * 查询是否为文档
	  * @param name 文件名
	  * @return
	  */
	 public boolean isDocument(String name){
		 if(name.indexOf("doc")>=0||
				 name.indexOf("docx")>=0||
				 name.indexOf("DOC")>=0||
				 name.indexOf("DOCX")>=0||
				 name.indexOf("xls")>=0||
				 name.indexOf("XLSX")>=0||
				 name.indexOf("pdf")>=0||
				 name.indexOf("PDF")>=0
				
				 ){
			return true;
		 }
		 
		 
		 return false;
	 }
	 
	

}
