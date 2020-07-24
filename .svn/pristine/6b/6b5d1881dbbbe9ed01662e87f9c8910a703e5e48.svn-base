package ac.drsi.nestor;

import java.io.File;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

/**
 * 启动文件
 * 
 */
@SpringBootApplication
@Configurable 
public class Application {
	public static void main(String[] args) {
		File file=new File("F:\\temp");
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public MultipartConfigElement getMultiConfig() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("F:\\temp");
		factory.setMaxFileSize("921600Mb");
		factory.setMaxRequestSize("921600Mb");
		return factory.createMultipartConfig();
	}
	
	

}
