package ac.drsi.nestor.controller;

import java.io.BufferedOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_LogController {
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_MenuService menuService;
	@Autowired
	SVDS_SessionService sessionService;
	/**
	 * 分页查询全部
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageLog", method = RequestMethod.POST)
	@ResponseBody
	public String pageInfo(@RequestParam int pageNumber, int pageSize,
			String startDate, String endDate, String operation,
			String userName, Integer menuId, String fileName) throws Exception {
		List<SVDS_Log> logs = null;
		List<SVDS_Log> pageInfo = null;
		Map<String, Object> param = new HashMap<String, Object>();
		if ((startDate == "" && endDate == "")
				&& (operation == "" && userName == "" && menuId == 0 && fileName == "")) {
			logs = logService.getLogAll();
			pageInfo = logService.getLogAll(pageNumber, pageSize);
		} else {
			if (startDate != "") {
				param.put("startDate", startDate);
			}
			if (endDate != "") {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(endDate);
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
				date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
				String putDate = sdf.format(date); // 增加一天后的日期
				System.out.println(putDate);
				param.put("endDate", putDate);
			}
			if (operation != "") {
				param.put("operation", operation);
			}
			if (userName != "") {
				param.put("userName", userName);
			}
			if (fileName != "") {
				param.put("fileName", fileName);
			}
			if (menuId != 0) {
				System.out.println("菜单Id:" + menuId);
				SVDS_Menu menu = menuService.getMenuById(menuId);
				List<Integer> menuIds = new ArrayList<Integer>();
				List<Integer> ids = getMenuIds(menuIds, menu);
				param.put("ids", ids);
			}
			// System.out.println(param.toString());
			logs = logService.listLogByParam(param);
			pageInfo = logService.listLogByParam(pageNumber, pageSize, param);
		}
		int total = logs.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * 审计员分页查询全部
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLogByUserId", method = RequestMethod.POST)
	@ResponseBody
	public String pageLog(@RequestParam int pageNumber, int pageSize,
			String startDate, String endDate, String operation,
			String userName, Integer menuId, String fileName) throws Exception {
		List<SVDS_Log> logs = null;
		List<SVDS_Log> pageInfo = null;
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(menuId != 0);
		System.out.println("审计员查询");
		Map<String, Object> param = new HashMap<String, Object>();
		/*
		 * if ((startDate == "" && endDate == "") && (operation == "" &&
		 * userName == "" && menuId == 0 && fileName == "")) { logs =
		 * logService.getLogAll(); pageInfo = logService.getLogAll(pageNumber,
		 * pageSize); } else {
		 */
		if (startDate != "") {
			param.put("startDate", startDate);
		}
		if (endDate != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(endDate);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
			String putDate = sdf.format(date); // 增加一天后的日期
			System.out.println(putDate);
			param.put("endDate", putDate);
		}
		if (operation != "") {
			param.put("operation", operation);
		}
		if (userName != "") {
			param.put("userName", userName);
		}
		if (fileName != "") {
			param.put("fileName", fileName);
		}
		if (menuId != 0) {
			System.out.println("菜单Id:" + menuId);
			SVDS_Menu menu = menuService.getMenuById(menuId);
			List<Integer> menuIds = new ArrayList<Integer>();
			List<Integer> ids = getMenuIds(menuIds, menu);
			param.put("ids", ids);
		}
		// System.out.println(param.toString());
		logs = logService.listLogByUserId(param);
		pageInfo = logService.listLogByUserId(pageNumber, pageSize, param);
		// }
		int total = logs.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		return result.toString();
	}

	/**
	 * 获取该菜单id和子级所有的id
	 * 
	 * @param menuIds
	 * @param menu
	 * @return
	 */
	public List<Integer> getMenuIds(List<Integer> menuIds, SVDS_Menu menu) {
		menuIds.add(menu.getId());
		List<SVDS_Menu> menus = menu.getChildren();
		for (SVDS_Menu svds_Menu : menus) {
			menuIds.add(svds_Menu.getId());
			if (svds_Menu.getChildren() != null) {
				getMenuIds(menuIds, svds_Menu);
			}
		}
		System.out.println(menuIds);
		return menuIds;
	}

	/**
	 * 安全管理员分页查询全部
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLogBySafe", method = RequestMethod.POST)
	@ResponseBody
	public String getLogBySys(@RequestParam int pageNumber, int pageSize,
			String startDate, String endDate, String operation,
			String userName, Integer menuId, String fileName) throws Exception {
		List<SVDS_Log> logs = null;
		List<SVDS_Log> pageInfo = null;
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(menuId != 0);
		System.out.println("安全管理员查询日志");
		Map<String, Object> param = new HashMap<String, Object>();
		/*
		 * if ((startDate == "" && endDate == "") && (operation == "" &&
		 * userName == "" && menuId == 0 && fileName == "")) { logs =
		 * logService.getLogAll(); pageInfo = logService.getLogAll(pageNumber,
		 * pageSize); } else {
		 */
		if (startDate != "") {
			param.put("startDate", startDate);
		}
		if (endDate != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(endDate);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
			String putDate = sdf.format(date); // 增加一天后的日期
			System.out.println(putDate);
			param.put("endDate", putDate);
		}
		if (operation != "") {
			param.put("operation", operation);
		}
		if (userName != "") {
			param.put("userName", userName);
		}
		if (fileName != "") {
			param.put("fileName", fileName);
		}
		if (menuId != 0) {
			System.out.println("菜单Id:" + menuId);
			SVDS_Menu menu = menuService.getMenuById(menuId);
			List<Integer> menuIds = new ArrayList<Integer>();
			List<Integer> ids = getMenuIds(menuIds, menu);
			param.put("ids", ids);
		}
		// System.out.println(param.toString());
		logs = logService.listLogBySafe(param);
		pageInfo = logService.listLogBySafe(pageNumber, pageSize, param);
		// }
		int total = logs.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		return result.toString();
	}

	/**
	 * 普通用户分页查询全部
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLogByGen", method = RequestMethod.POST)
	@ResponseBody
	public String listLogByGen(@RequestParam int pageNumber, int pageSize,
			String startDate, String endDate, String operation,
			String userName, Integer menuId, String fileName) throws Exception {
		List<SVDS_Log> logs = null;
		List<SVDS_Log> pageInfo = null;
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println(menuId != 0);
		System.out.println("普通用户查询日志");
		Map<String, Object> param = new HashMap<String, Object>();
		/*
		 * if ((startDate == "" && endDate == "") && (operation == "" &&
		 * userName == "" && menuId == 0 && fileName == "")) { logs =
		 * logService.getLogAll(); pageInfo = logService.getLogAll(pageNumber,
		 * pageSize); } else {
		 */
		if (startDate != "") {
			param.put("startDate", startDate);
		}
		if (endDate != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(endDate);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
			date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
			String putDate = sdf.format(date); // 增加一天后的日期
			System.out.println(putDate);
			param.put("endDate", putDate);
		}
		if (operation != "") {
			param.put("operation", operation);
		}
		if (userName != "") {
			param.put("userName", userName);
		}
		if (fileName != "") {
			param.put("fileName", fileName);
		}
		if (menuId != 0) {
			System.out.println("菜单Id:" + menuId);
			SVDS_Menu menu = menuService.getMenuById(menuId);
			List<Integer> menuIds = new ArrayList<Integer>();
			List<Integer> ids = getMenuIds(menuIds, menu);
			param.put("ids", ids);
		}
		// System.out.println(param.toString());
		logs = logService.listLogByGen(param);
		pageInfo = logService.listLogByGen(pageNumber, pageSize, param);
		// }
		int total = logs.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		return result.toString();
	}
	/**
	 * 分页查询历史记录
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/record", method = RequestMethod.POST)
	@ResponseBody
	public String pageRecord(Integer userId) throws Exception {
		System.out.println("用户Id:" + userId);
		List<SVDS_Log> logs = logService.listByUserId(userId);
		System.out.println(logs.toString());
		if (logs.size() > 0) {
			JSONArray data = JSONArray.fromObject(logs);
			return data.toString();
		} else {
			return null;
		}
	}

	/**
	 * 根据ID删除日志
	 * 
	 * @param LogIds
	 * @return
	 */
	@RequestMapping(value = "/deleteLog", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteLog(
			@RequestParam(value = "btnIds[]", required = false, defaultValue = "") Integer[] btnIds) {
		List<Integer> ids = Arrays.asList(btnIds);
		if (logService.deleteLog(ids) > 0) {
			return logService.deleteLog(ids);
		}
		return null;
	}

	/**
	 * 总访问量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/totalView", method = RequestMethod.POST)
	@ResponseBody
	public Integer totalView() {
		return logService.totalView();
	}

	/**
	 * 日访问量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listToDay", method = RequestMethod.POST)
	@ResponseBody
	public Integer listToDay() {
		return logService.listToDay();
	}

	/**
	 * 年访问量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/yearVisits", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> yearVisits() {
		return logService.yearVisits();
	}

	/**
	 * 月访问量
	 * 
	 * @param yearValue
	 * @return
	 */
	@RequestMapping(value = "/monthVisits", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> monthVisits(@RequestParam String yearValue) {
		return logService.monthVisits(yearValue);
	}

	@RequestMapping(value = "/exportLog")
	public void exportLog(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("application/octet-stream");
		response.setContentType("text/plain");// 一下两行关键的设置
		String fileName = "日志导出.txt";
		String userAgent = request.getHeader("user-agent").toLowerCase();
		if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
			// win10 ie edge 浏览器 和其他系统的ie
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			// fe
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		List<SVDS_Log> logAll = logService.getLogAll();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		System.out.println("开始导出");
		System.out.println(logAll.size());
		for (int i = 0; i < logAll.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			SVDS_Log log = (SVDS_Log) logAll.get(i);
			//map.put("日志Id", (i+1)+"");
			if(log.getUser()!=null){
				map.put("用户名称", log.getUser().getUsername());
			}else{
				map.put("用户名称","");
			}
			map.put("执行时间", log.getLogDate());
			map.put("执行操作", log.getOperation());
			if(log.getFile()!=null){
				map.put("操作文件", log.getFile().getFileName());
				map.put("文件来源", log.getFile().getLocation());
			}else{
				map.put("操作文件", "");
				map.put("文件来源", "");
			}
			map.put("IP地址", log.getIpData());
			map.put("执行结果", log.getResult());
			list.add(map);
		}
		writeToTxt(list,response);
		logService.insertLog(new SVDS_Log("执行了导出日志操作", DateUtils.getDate(),
				loginUser, IpAddressUtils.getIpAddress(request),"成功"));
	}

	public static void writeToTxt(List<Map<String, String>> list,
			HttpServletResponse response) {
		ServletOutputStream outSTr = null;
		BufferedOutputStream Buff = null;
		String enter = "\r\n";
		StringBuffer write;
		try {
			outSTr = response.getOutputStream();// 建立
			Buff = new BufferedOutputStream(outSTr);
			for (int i = 0; i < list.size(); i++) {
				write = new StringBuffer();
				write.append(list.get(i));
				write.append(enter);
				Buff.write(write.toString().getBytes("UTF-8"));
			}
			Buff.flush();
			Buff.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				Buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
