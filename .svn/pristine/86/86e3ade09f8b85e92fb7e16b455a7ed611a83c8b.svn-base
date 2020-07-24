package ac.drsi.nestor.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ac.drsi.nestor.entity.SVDS_Files;
/**
 * 用于下载模板和用户手册
 * @author CZK
 *
 */
@Controller
public class TemplateDown {
	/**
	 * 模板下载
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadTemplate")
	public String downloadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 String fileName = "Template.rar";// 设置文件名，根据业务需要替换成要下载的文件名
	        if (fileName != null) {
	            //设置文件路径
	            String realPath = "F:\\files\\";
	            File file = new File(realPath , fileName);
	            if (file.exists()) {
	                response.setContentType("application/force-download");// 设置强制下载不打开
	                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
	                byte[] buffer = new byte[1024];
	                FileInputStream fis = null;
	                BufferedInputStream bis = null;
	                try {
	                    fis = new FileInputStream(file);
	                    bis = new BufferedInputStream(fis);
	                    OutputStream os = response.getOutputStream();
	                    int i = bis.read(buffer);
	                    while (i != -1) {
	                        os.write(buffer, 0, i);
	                        i = bis.read(buffer);
	                    }
	                    System.out.println("success");
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } finally {
	                    if (bis != null) {
	                        try {
	                            bis.close();
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                    if (fis != null) {
	                        try {
	                            fis.close();
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                }
	            }
	        }
	return null;
	}
	/**
	 * 用户手册下载
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadHelp")
	public String downloadHelp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 String fileName = "用户手册.docx";// 设置文件名，根据业务需要替换成要下载的文件名
	        if (fileName != null) {
	            //设置文件路径
	            String realPath = "F:\\files\\";
	            File file = new File(realPath , fileName);
	            if (file.exists()) {
	                response.setContentType("application/force-download");// 设置强制下载不打开
	                response.addHeader("Content-Disposition", "attachment;fileName=help.docx");// 设置文件名
	                byte[] buffer = new byte[1024];
	                FileInputStream fis = null;
	                BufferedInputStream bis = null;
	                try {
	                    fis = new FileInputStream(file);
	                    bis = new BufferedInputStream(fis);
	                    OutputStream os = response.getOutputStream();
	                    int i = bis.read(buffer);
	                    while (i != -1) {
	                        os.write(buffer, 0, i);
	                        i = bis.read(buffer);
	                    }
	                    System.out.println("success");
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } finally {
	                    if (bis != null) {
	                        try {
	                            bis.close();
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                    if (fis != null) {
	                        try {
	                            fis.close();
	                        } catch (IOException e) {
	                            e.printStackTrace();
	                        }
	                    }
	                }
	            }
	        }
	return null;
	}
}
