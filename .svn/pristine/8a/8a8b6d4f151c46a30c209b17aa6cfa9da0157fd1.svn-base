package ac.drsi.nestor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ac.drsi.nestor.service.SVDS_UserService;
/**
 * 用于首页跳转页面
 * @author CZK
 *
 */
@Controller
public class IndexController {
	@Autowired
	private MessageSource messageSource;
	@Autowired
	SVDS_UserService userService;

	@RequestMapping("/")
	public String getLogin(Model model) {
		//Locale locale = LocaleContextHolder.getLocale();
		/*model.addAttribute("login",
				messageSource.getMessage("login", null, locale));
		model.addAttribute("remember",
				messageSource.getMessage("remember", null, locale));
		model.addAttribute("wait",
				messageSource.getMessage("wait", null, locale));
		model.addAttribute("username",
				messageSource.getMessage("username", null, locale));
		model.addAttribute("pwd", messageSource.getMessage("pwd", null, locale));
		model.addAttribute("errorUser",
				messageSource.getMessage("errorUser", null, locale));*/
		return "login";
	}
	
	@RequestMapping("/index.html")
	public String postIndex(Model model, HttpServletRequest request) {
		return "index";
	}
	@RequestMapping("/html/UserManagement.html")
	public String userManagement(Model model, HttpServletRequest request) {
		return "html/UserManagement";
	}

}
