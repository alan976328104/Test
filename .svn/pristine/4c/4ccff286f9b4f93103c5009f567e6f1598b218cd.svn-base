package ac.drsi.nestor.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ac.drsi.nestor.entity.SVDS_Button;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_ButtonService;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_RoleButtonService;
import ac.drsi.nestor.service.SVDS_SessionService;

import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PDFCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.ThemeType;

/**
 * 用于在线预览文档
 * @author Administrator
 * 
 */

@RestController
public class DomeController {

	@Value("${posyspath}")
	private String poSysPath;

	@Value("${popassword}")
	private String poPassWord;

	@Value("${filepath}")
	private String filePath;

	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_RoleButtonService roleButtonService;
	@Autowired
	SVDS_ButtonService buttonService;
	@Autowired
	SVDS_SessionService sessionService;
	
/**
 * 
 * @param url 文件路径
 * @param fileId 文件id
 * @return
 */
	@RequestMapping(value = "/office", method = RequestMethod.GET)
	public ModelAndView showIndex(String url,String fileId) {
		String urlName = url.substring(0, url.length() - 5);
		ModelAndView mv = new ModelAndView(urlName);
		mv.addObject("time", "");
		if(fileId!=null){
			System.out.println("文件Id:"+fileId);
			mv.addObject("fileId", fileId);
			SVDS_Files files = filesService.getFilesById(Integer.parseInt(fileId));
			mv.addObject("fileUrl", files.getFileUrl());
		}
		return mv;
	}
	
	/**
	 * 显示WORD文档
	 * @param request 请求协议
	 * @param map 参数
	 * @return
	 */

	@RequestMapping(value = "/word", method = RequestMethod.GET)
	public ModelAndView showWord(HttpServletRequest request,
			Map<String, Object> map) {
		String fileId = request.getParameter("fileId");
		String type = request.getParameter("type");
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_Files files = filesService.getFilesById(Integer.parseInt(fileId));
		List<Integer> btnIds = roleButtonService.listRoleButtonByUserId(loginUser.getUserId());
		boolean roleState=false;
		if(btnIds!=null&&btnIds.size()>0){
			List<SVDS_Button> btns = buttonService.getUserButton(btnIds);
			for (int i = 0; i < btns.size(); i++) {
				if(btns.get(i).getBtnName().equals("修改")){
					System.out.println(btns.get(i).getBtnName());
					roleState=true;
				}
			}
		}
		
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		poCtrl.setServerPage("/poserver.zz");// 设置服务页面
	    poCtrl.setMenubar(false);// 隐藏菜单栏
		poCtrl.setTitlebar(true);// 隐藏标题栏
		poCtrl.setTheme(ThemeType.CustomStyle);// 设置主题样式
		//服务器端代码。须放webOpen方法之前设置才可以禁止复制、粘贴的功能。
		poCtrl. setAllowCopy(false);//
		//poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		//poCtrl.addCustomToolButton("关闭", "close()", 21);  
		//poCtrl.addCustomToolButton("全屏切换", "SwitchFullScreen()", 4);
		// 打开word
		String fileName = files.getFileName();
		String title = fileName.substring(0,fileName.lastIndexOf("."));
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(roleState){
			System.out.println(123);
			filePath=files.getFileUrl();
			poCtrl.addCustomToolButton("保存","Save()",1); //添加自定义按钮
			poCtrl.setSaveFilePage("/save");//设置保存的action
			if (type.equals("0")) {
				if (fileType.equals("doc") || fileType.equals("docx")) {
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.docNormalEdit,
							files.getUser().getUsername());
				} else if (fileType.equals("xls") || fileType.equals("xlsx")) {
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.xlsNormalEdit,
							files.getUser().getUsername());
				}else if(fileType.equals("ppt") || fileType.equals("pptx")){
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.pptNormalEdit,
							files.getUser().getUsername());
				}
			}
		}else{
			System.out.println(456);
			poCtrl.setCustomToolbar(false);// 隐藏自定义栏
			poCtrl.setOfficeToolbars(false);// 隐藏office菜单栏
			if (type.equals("0")) {
				if (fileType.equals("doc") || fileType.equals("docx")) {
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.docReadOnly,
							files.getUser().getUsername());
				} else if (fileType.equals("xls") || fileType.equals("xlsx")) {
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.xlsReadOnly,
							files.getUser().getUsername());
				}else if(fileType.equals("ppt") || fileType.equals("pptx")){
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.pptReadOnly,
							files.getUser().getUsername());
				}
			}
		}
		poCtrl.setTagId("PageOfficeCtrl1"); //此行必须
		poCtrl.setOfficeVendor(OfficeVendorType.MSOffice);
		map.put("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		map.put("title", title);
		ModelAndView mv = new ModelAndView("html/word");
		return mv;
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView show(HttpServletRequest request,
			Map<String, Object> map) {
		String fileId = request.getParameter("fileId");
		String type = request.getParameter("type");
		SVDS_Files files = filesService.getFilesById(Integer.parseInt(fileId));
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		List<Integer> btnIds = roleButtonService.listRoleButtonByUserId(loginUser.getUserId());
		boolean roleState=false;
		if(btnIds!=null&&btnIds.size()>0){
			List<SVDS_Button> btns = buttonService.getUserButton(btnIds);
			for (int i = 0; i < btns.size(); i++) {
				if(btns.get(i).getBtnName().equals("修改")){
					System.out.println(btns.get(i).getBtnName());
					roleState=true;
				}
			}
		}
		PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
		poCtrl.setServerPage("/poserver.zz");// 设置服务页面
		poCtrl.setMenubar(false);// 隐藏菜单栏
		poCtrl.setTitlebar(true);// 隐藏标题栏
		poCtrl.setCustomToolbar(true);// 隐藏自定义栏
		poCtrl.setTheme(ThemeType.CustomStyle);// 设置主题样式
		//服务器端代码。须放webOpen方法之前设置才可以禁止复制、粘贴的功能。
		poCtrl. setAllowCopy(false);//
		// 打开word
		String fileName = files.getFileName();
		String title = fileName.substring(0,fileName.lastIndexOf("."));
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		if(roleState){
			filePath=files.getFileUrl();
			poCtrl.addCustomToolButton("保存","Save()",1); //添加自定义按钮
			poCtrl.setSaveFilePage("/save");//设置保存的action
			if (type.equals("0")) {
				if (fileType.equals("doc") || fileType.equals("docx")) {
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.docNormalEdit,
							files.getUser().getUsername());
				} else if (fileType.equals("xls") || fileType.equals("xlsx")) {
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.xlsNormalEdit,
							files.getUser().getUsername());
				}else if(fileType.equals("ppt") || fileType.equals("pptx")){
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.pptNormalEdit,
							files.getUser().getUsername());
				}
			}
		}else{
			System.out.println(123);
			poCtrl.setCustomToolbar(false);// 隐藏自定义栏
			poCtrl.setOfficeToolbars(false);// 隐藏office菜单栏
			if (type.equals("0")) {
				if (fileType.equals("doc") || fileType.equals("docx")) {
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.docReadOnly,
							files.getUser().getUsername());
				} else if (fileType.equals("xls") || fileType.equals("xlsx")) {
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.xlsReadOnly,
							files.getUser().getUsername());
				}else if(fileType.equals("ppt") || fileType.equals("pptx")){
					poCtrl.webOpen(files.getFileUrl(), OpenModeType.pptReadOnly,
							files.getUser().getUsername());
				}
			}
		}
		poCtrl.setTagId("PageOfficeCtrl1"); //此行必须
		poCtrl.setOfficeVendor(OfficeVendorType.MSOffice);
		map.put("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		map.put("title", title);
		ModelAndView mv = new ModelAndView("html/word");
		return mv;
	}
	
	/**
	 * 保存文件
	 * @param request http请求
	 * @param response http响应
	 */
	@RequestMapping("/save")
	public void saveFile(HttpServletRequest request,
			HttpServletResponse response) {
		FileSaver fs = new FileSaver(request, response);
		fs.saveToFile(filePath);
		fs.close();
	}
/**
 * 打开PDF
 * @param request http请求
 * @param map 参数
 * @return
 */
	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	public ModelAndView showPdf(HttpServletRequest request,
			Map<String, Object> map) {
		String fileId = request.getParameter("fileId");
		SVDS_Files files = filesService.getFilesById(Integer.parseInt(fileId));
		PDFCtrl poCtrl = new PDFCtrl(request);
		poCtrl.setServerPage("/poserver.zz");// 设置服务页面
		poCtrl.setMenubar(false);// 隐藏菜单栏
		//poCtrl.setTitlebar(false);// 隐藏标题栏
		//poCtrl.setCustomToolbar(false);// 隐藏自定义栏
		poCtrl.setTheme(ThemeType.CustomStyle);// 设置主题样式
		//服务器端代码。须放webOpen方法之前设置才可以禁止复制、粘贴的功能。
		poCtrl. setAllowCopy(false);//
		//poCtrl.setJsFunction_AfterDocumentOpened( "AfterDocumentOpened()");
		//poCtrl.addCustomToolButton("关闭", "close", 21);  
		//poCtrl.addCustomToolButton("打印", "PrintFile()", 6);
	   // poCtrl.addCustomToolButton("隐藏/显示书签", "SetBookmarks()", 0);
	   // poCtrl.addCustomToolButton("-", "", 0);
	   // poCtrl.addCustomToolButton("实际大小", "SetPageReal()", 16);
	   // poCtrl.addCustomToolButton("适合页面", "SetPageFit()", 17);
	   // poCtrl.addCustomToolButton("适合宽度", "SetPageWidth()", 18);
	  //  poCtrl.addCustomToolButton("-", "", 0);
	    poCtrl.addCustomToolButton("首页", "FirstPage()", 8);
	    poCtrl.addCustomToolButton("上一页", "PreviousPage()", 9);
	    poCtrl.addCustomToolButton("下一页", "NextPage()", 10);
	    poCtrl.addCustomToolButton("尾页", "LastPage()", 11);
	    poCtrl.addCustomToolButton("-", "", 0);
	    poCtrl.addCustomToolButton("向左旋转90度", "SetRotateLeft()", 12);
	    poCtrl.addCustomToolButton("向右旋转90度", "SetRotateRight()", 13);
		// 打开word
		String fileName = files.getFileName();
		poCtrl.webOpen(files.getFileUrl());
		map.put("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
		String fileType = fileName.substring(0, fileName.lastIndexOf(".") + 1);
		map.put("title", fileName.replace(fileType, ""));
		ModelAndView mv = new ModelAndView("html/word");
		return mv;
	}
	
	/**
	 * 添加PageOffice的服务器端授权程序Servlet（必须）
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		com.zhuozhengsoft.pageoffice.poserver.Server poserver = new com.zhuozhengsoft.pageoffice.poserver.Server();
		poserver.setSysPath(poSysPath);// 设置PageOffice注册成功后,license.lic文件存放的目录
		ServletRegistrationBean srb = new ServletRegistrationBean(poserver);
		srb.addUrlMappings("/poserver.zz");
		srb.addUrlMappings("/posetup.exe");
		srb.addUrlMappings("/pageoffice.js");
		srb.addUrlMappings("/jquery.min.js");
		srb.addUrlMappings("/pobstyle.css");
		srb.addUrlMappings("/sealsetup.exe");
		return srb;//
	}

	/**
	 * 添加印章管理程序Servlet（可选）
	 * 
	 * @return
	 */
	@Bean
	public ServletRegistrationBean servletRegistrationBean2() {
		com.zhuozhengsoft.pageoffice.poserver.AdminSeal adminSeal = new com.zhuozhengsoft.pageoffice.poserver.AdminSeal();
		adminSeal.setAdminPassword(poPassWord);// 设置印章管理员admin的登录密码
		adminSeal.setSysPath(poSysPath);// 设置印章数据库文件poseal.db存放的目录
		ServletRegistrationBean srb = new ServletRegistrationBean(adminSeal);
		srb.addUrlMappings("/adminseal.zz");
		srb.addUrlMappings("/sealimage.zz");
		srb.addUrlMappings("/loginseal.zz");
		return srb;//
	}

}
