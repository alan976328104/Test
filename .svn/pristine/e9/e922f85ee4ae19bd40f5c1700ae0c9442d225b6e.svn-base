package ac.drsi.nestor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ceshi {
	@RequestMapping("/cesdownload")
	public void downloadFile(Integer fileId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//SVDS_Files files = filesService.getFilesById(fileId);
		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("application/octet-stream");
		FileInputStream fis = null;
		try {
			File file = new File("C:\\Users\\Administrator\\Desktop\\注册表修改文件夹.txt");
			fis = new FileInputStream(file);
			response.setHeader("Content-Disposition", "attachment; filename="
					+ file.getName());
			IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
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
