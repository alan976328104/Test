package ac.drsi.nestor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    
        /**
		 * 资源映射路径
		 * addResourceHandler：访问映射路径
		 * addResourceLocations：资源绝对路径
		 */
        registry.addResourceHandler("/8c7dd922ad47494fc02c388e12c00eac/**").addResourceLocations("file:F:/files/");
    }
}
