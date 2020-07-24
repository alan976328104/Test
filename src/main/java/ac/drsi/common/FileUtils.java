package ac.drsi.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
/**
 * 用于操作文件
 * @author CZK
 *
 */
public class FileUtils {
	/**
	 * 
	 * @param file
	 *            文件
	 * @param path
	 *            文件存放路径
	 * @param fileName
	 *            源文件名
	 * @return
	 * @throws Exception
	 */
	public static void upload(byte[] file, String filePath, String fileName)
			throws Exception {
		// 目标目录
		File targetfile = new File(filePath);
		if (!targetfile.exists()) {
			targetfile.mkdirs();
		} // 二进制流写入
		FileOutputStream out = new FileOutputStream(filePath+ fileName);
		out.write(file);
		out.flush();
		out.close();
	}
	 /**
     * 
     * @param file 文件
     * @param path   文件存放路径
     * @param fileName 原文件名
     * @return
     */
     public static boolean upload(MultipartFile file, String path, String fileName){

            // 生成新的文件名
           // String realPath = path + "/" + FileNameUtils.getFileName(fileName);

            //使用原文件名
            String realPath = path + "/" + fileName;

            File dest = new File(realPath);

            //判断文件父目录是否存在
           /* if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdir();
            }*/

            try {
                //保存文件
                file.transferTo(dest);
                return true;
            } catch (IllegalStateException e) {             
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }

}
