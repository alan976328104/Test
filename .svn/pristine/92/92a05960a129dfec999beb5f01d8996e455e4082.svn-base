package ac.drsi.common;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
/**
 * 用于操作文件
 * @author CZK
 *
 */
public class FilesUtils {
	 List<String[]> list;
	/**
	 * 在basePath下保存上传的文件夹
	 * @param basePath
	 * @param files
	 */
	public  void saveMultiFile(String basePath, MultipartFile[] files) {
		list	= new ArrayList<>();
		//System.out.println(files.length);
		if (files == null || files.length == 0) {
			return;
		}
		if (basePath.endsWith("/")) {
			basePath = basePath.substring(0, basePath.length() - 1);
		}
		for (MultipartFile file : files) {
			list.add(file.getOriginalFilename().split("/"));
			
			System.out.println(file.getOriginalFilename());
			String filePath = basePath + "/" + file.getOriginalFilename();
			makeDir(filePath);
			File dest = new File(filePath);
			try {
				file.transferTo(dest);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		 List<String> data =new ArrayList<>();
		 data.add("测试数据/试验装置/【非密】0实验装置描述.pdf");
		 data.add("测试数据/试验工况I1.1/试验数据/【非密】3实验数据i2.1 run 1 & 2 selected channels (prelim. data).xlsx");
		 data.add("测试数据/试验工况I1.1/试验报告/【非密】2实验报告.docx");
		 data.add("测试数据/试验工况I1.1/测试清单/【非密】1测量清单I1.1Plan.pdf");
		 data.add("测试数据/试验工况I1.1/测试清单/【非密】1测量清单PKLIIIi1.1RUN1 List.pdf");
		 data.add("测试数据/试验工况I1.1/测试清单/【非密】1测量清单PKLIIIi1.1RUN2 List.pdf");
		 
		 List<String[]> list = new ArrayList<>();
		 for (String strings : data) {
			 list.add(strings.split("/"));
		}
		 
		 for (String[] strings : list) {
			System.out.println(Arrays.toString(strings));
		}
		 
	}
	
	/**
	 * 确保目录存在，不存在则创建
	 * @param filePath
	 */
	private  void makeDir(String filePath) {
		if (filePath.lastIndexOf('/') > 0) {
			String dirPath = filePath.substring(0, filePath.lastIndexOf('/'));
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
	}
}
