package ac.drsi.nestor.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.common.SolrUtils;
import ac.drsi.common.StringUtil;
import ac.drsi.nestor.dao.FolderUpDao;
import ac.drsi.nestor.dao.HistoryDao;
import ac.drsi.nestor.entity.Plupload;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_Security;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SVDS_Visited;
import ac.drsi.nestor.entity.Tabs;
import ac.drsi.nestor.service.PluploadService;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_SecurityService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_VisitedService;
/**
 * 用于文件上传
 * @author CZK
 *
 */
@Controller
public class PluploadController {
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_SecurityService securityService;
	@Autowired
	SVDS_MenuService menuService;
	@Autowired
	FolderUpDao dao;
	@Autowired
	HistoryDao hdao;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	PluploadService pluploadService;
	@Autowired
	SVDS_VisitedService visitedService;
	@Autowired
	SVDS_SessionService sessionService;

	@Value("${filepath}")
	private String filePath;

//	@RequestMapping(value = "/getSecurity", method = RequestMethod.POST)
//	@ResponseBody
//	public String securityId(Integer securityId, HttpSession session) {
//		SVDS_Security security = securityService.getSecurityById(securityId);
//		session.setMaxInactiveInterval(18000);
//		// System.out.println(security);
//		if (security != null) {
//			session.setAttribute("security", security);
//			return security.toString();
//		} else {
//			return null;
//		}
//	}
/**
 * 判断文件是否上传过
 * @param filesName
 * @param request
 * @param tabsId
 * @return
 */
	@RequestMapping(value = "/existFile", method = RequestMethod.POST)
	@ResponseBody
	public String existFile(
			@RequestParam(value = "filesName[]", required = false, defaultValue = "") String[] filesName,
			HttpServletRequest request,Integer tabsId) {
		System.out.println(tabsId);
		File fileD = new File(filePath);
		if (fileD.exists()) {
			if (fileD.isDirectory()) {
				System.out.println("文件夹存在");
				for (int i = 0; i < filesName.length; i++) {
					System.out.println(filesName[i]);
					if (filesService.getFilesByName(filesName[i], tabsId)
							.size() != 0) {
						//System.out.println(filesService.getFilesByName(fileName).size());
						System.out.println("数据库存在");
						return "databaseExist";
					}
				}
			} else {
				return null;
			}
		}else{
			fileD.mkdirs();  
		}
		return null;
	}
/**
 * 文件是覆盖还是生成新的版本
 * @param request
 * @param state
 */
	@RequestMapping(value = "/fileVersion", method = RequestMethod.POST)
	@ResponseBody
	public void saveSession(HttpServletRequest request, String state) {
		request.getSession().removeAttribute("state");
		request.getSession().setAttribute("state", state);
	}

	/**
	 * Plupload文件上传处理方法
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/pluploadUpload")
	public void upload(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("进来了");
		String fileSize=request.getParameter("fileSize");
		String securityId = request.getParameter("securityId");
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		String state = (String) request.getSession().getAttribute("state");
		Integer tabsId = (Integer) request.getSession().getAttribute("tabsId");
		Integer menuId = (Integer) request.getSession().getAttribute("menuId");
		String FileDir = filePath;
		Tabs tabs=dao.getTabsBytabsId(tabsId);
		List<String> str=new ArrayList<String>();
		List<String> content=getLocation(tabs,str);
		SVDS_Menu menu=menuService.getMenuById(menuId);
		plupload.setRequest(request);// 手动传入Plupload对象HttpServletRequest属性
		// 文件存储绝对路径,会是一个文件夹，项目相应Servlet容器下的"pluploadDir"文件夹，还会以用户唯一id作划分
	    FileDir=FileDir+menu.getName()+"\\";
		for(int i=content.size()-1;i>=0;i--){
			FileDir+=content.get(i)+"\\";
		}
		System.out.println(FileDir);
		File dir = new File(FileDir);
		if (!dir.exists()) {
			dir.mkdirs();// 可创建多级目录，而mkdir()只能创建一级目录
		}
		List<SVDS_Files> filesExit = filesService.getFilesByName(
				plupload.getName(), tabsId);
		List<SVDS_Files> filesExitAll = filesService.getFilesByNameAll(
				plupload.getName(), tabsId);
		Integer filesCount = filesExit.size();
		if (state.equals("1") || state == "1") {// 覆盖还是生产新的版本 0覆盖 1生产新的版本
			// 开始上传文件
			System.out.println("生产新的版本");
			Integer maxVersion=filesService.getFilesByFileVersion(plupload.getName(), tabsId);
			pluploadService.upload(plupload, dir, (filesExitAll.size() + 1), tabsId);
			SVDS_Files files = new SVDS_Files();
			files.setFileName(plupload.getName());
			files.setFileUrl(FileDir + (filesExitAll.size() + 1) + ".0"  
					+ plupload.getName());
			files.setFileDate(DateUtils.getDate());
			files.setUser(loginUser);
			if(maxVersion!=null){
				files.setFileVersion((maxVersion + 1) + ".0");
			}else{
				files.setFileVersion(2 + ".0");
			}
			files.setSecurity(securityService.getSecurityById(Integer
					.parseInt(securityId)));
			files.setState(0);
			files.setTabsId(tabsId);
			files.setpId(0);
			files.setFileSize(fileSize);
			System.out.println("文件格式："+plupload.getName().substring(plupload.getName().lastIndexOf(".")+1).toLowerCase());
			files.setFormat(plupload.getName().substring(plupload.getName().lastIndexOf(".")+1).toLowerCase());
			//files.setMajor(loginUser.getMajor());
//			SVDS_Menu menu = menuService.getMenuById(menuId);
			files.setMenu(menu);
			StringBuffer location = new StringBuffer();
			location.append(menu.getName());
//			Tabs tab=hdao.getTabsbyid(tabsId);
//			List<String> str=new ArrayList<String>();
//			List<String> content=getLocation(tab,str);
			for(int i=content.size()-1;i>=0;i--){
				location.append("→"+content.get(i));
			}
			files.setLocation(location.toString());
			Integer isSuccess = filesService.insertFiles(files);
			if (isSuccess > 0) {
				SVDS_Files fileOld = new SVDS_Files();
				if (filesExitAll.size() == 1) {
					fileOld = filesExit.get(0);
					fileOld.setFileVersion("1.0");
					fileOld.setpId(files.getFileId());
//					files.setFileSize(fileSize);
//					files.setFileSize(fileSize);
					filesService.updateFiles(fileOld);
				} else {
					for (SVDS_Files svds_Files : filesExit) {
						svds_Files.setpId(files.getFileId());
//						svds_Files.setFileSize(fileSize);
						filesService.updateFiles(svds_Files);
					}
//					for(int i=0;i<filesExit.size();i++){
//						if(i==0){
//							filesExit.get(i).setpId(files.getFileId());
//						}else{
//							filesExit.get(i).setpId(filesExit.get(i-1).getFileId());
//						}
//						
////						svds_Files.setFileSize(fileSize);
//						filesService.updateFiles(filesExit.get(i));
//					}
				}
				SolrUtils.addFile(new File(files.getFileUrl()), files.getFileId()
						.toString());
				visitedService.insertVisited(new SVDS_Visited(loginUser, files, 0, DateUtils
						.getDate()));
				logService.insertLog(new SVDS_Log("执行了生产新的版本操作", DateUtils
						.getDate(), loginUser, files.getMenu(), files,IpAddressUtils.getIpAddress(request),"成功"));
			}else{
				//logService.insertLog(new SVDS_Log("执行了生产新的版本操作", DateUtils.getDate(), loginUser, files.getMenu(), files,StringUtil.getLocalIp(request),"失败"));
			}
		} else if (state.equals("0") || state == "0") {
			System.out.println("覆盖文件");
			pluploadService.upload(plupload, dir, filesCount, tabsId);
			for (SVDS_Files svds_Files : filesExit) {
				svds_Files.setFileSize(fileSize);
				filesService.updateFiles(svds_Files);
				SolrUtils.addFile(new File(svds_Files.getFileUrl()), svds_Files.getFileId()
						.toString());
				logService.insertLog(new SVDS_Log("执行了覆盖文件操作", DateUtils.getDate(),
						loginUser, svds_Files.getMenu(),svds_Files,IpAddressUtils.getIpAddress(request),"成功"));
			}
			
		} else {
			pluploadService.upload(plupload, dir, null, tabsId);
			SVDS_Files files = new SVDS_Files();
			files.setFileName(plupload.getName());
			files.setFileUrl(FileDir  + plupload.getName());
			files.setFileDate(DateUtils.getDate());
			files.setUser(loginUser);
			files.setFileVersion("1.0");
			files.setSecurity(securityService.getSecurityById(Integer
					.parseInt(securityId)));
			files.setState(0);
			files.setFileSize(fileSize);
			files.setTabsId(tabsId);
			System.out.println("文件格式："+plupload.getName().substring(plupload.getName().lastIndexOf(".")+1).toLowerCase());
			files.setFormat(plupload.getName().substring(plupload.getName().lastIndexOf(".")+1).toLowerCase());
		//	files.setMajor(loginUser.getMajor());
//			SVDS_Menu menu = menuService.getMenuById(menuId);
			files.setMenu(menu);
			StringBuffer location = new StringBuffer();
			location.append(menu.getName());
//			Tabs tab=hdao.getTabsbyid(tabsId);
//			List<String> str=new ArrayList<String>();
//			List<String> content=getLocation(tab,str);
			for(int i=content.size()-1;i>=0;i--){
				location.append("→"+content.get(i));
			}
			files.setLocation(location.toString());
			Integer fileId = filesService.insertFiles(files);
			if (fileId > 0) {
				System.out.println(fileId+"文件上传Id："+files.getFileId()
						.toString());
				SolrUtils.addFile(new File(files.getFileUrl()), files.getFileId()
						.toString());
				SVDS_Files logFile = filesService.getFilesById(files
						.getFileId());
				visitedService.insertVisited(new SVDS_Visited(loginUser, logFile, 0, DateUtils
						.getDate()));
				logService.insertLog(new SVDS_Log("执行了上传文件操作", DateUtils
						.getDate(), loginUser, logFile.getMenu(), logFile,IpAddressUtils.getIpAddress(request),"成功"));
			}
		}
	}
	//List<String> str=new ArrayList<String>();
	public List<String> getLocation(Tabs tab,List<String> str){
		str.add(tab.getName());
		if(tab.getParentid()!=0){
			Tabs t=hdao.getTabsbyid(tab.getParentid());
			getLocation(t,str);
		}
		return str;
	}
	
	/**
	 * 更新图标
	 * @param image
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateImg", method = RequestMethod.POST)
	@ResponseBody
    public String upload( MultipartFile image,
            HttpServletRequest request) {
        //String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/assets/images/";
		///String t=Thread.currentThread().getContextClassLoader().getResource("").getPath(); 
		// int num=t.indexOf(".metadata");
		// String path=t.substring(1,num).replace('/', '\\')+"SVDS\\WebContent\\文件";
        System.out.println(request.getSession().getServletContext().getRealPath(""));
       // File directory = new File(basePath);
        //if (!directory.exists()) {
            //directory.mkdirs();
       // }
        try {
        	 //System.out.println(image.getName());
             //System.out.println(image.getOriginalFilename());
           // image.transferTo(new File(basePath + "logo.png"));
        } catch (Exception e) {
            // TODO
        }
        return "success";
    }
}
