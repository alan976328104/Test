package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Recycle;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_RecycleService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_RecycleController {
	@Autowired
	SVDS_RecycleService recycleService;
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

//	@RequestMapping(value = "/RecycleById", method = RequestMethod.POST)
//	@ResponseBody
//	public SVDS_Recycle getRecycleById(Integer fileId) {
//		SVDS_Recycle file = recycleService.getRecycleById(fileId);
//		return file;
//	}

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageRecycle", method = RequestMethod.POST)
	@ResponseBody
	public String pageInfo(@RequestParam int pageNumber, int pageSize,
			String fileName) throws Exception {
		List<SVDS_Recycle> recycles = null;
		List<SVDS_Recycle> pageInfo = null;
		System.out.println(fileName);
		if (fileName == "" || fileName == null) {
			recycles = recycleService.getRecycleAll();
			pageInfo = recycleService.getRecycleAll(pageNumber, pageSize);
		} else {
			recycles = recycleService.getRecycleByName(fileName);
			pageInfo = recycleService.getRecycleByName(pageNumber, pageSize,
					fileName);
		}
		int total = recycles.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		return result.toString();
	}
	/**
	 * 根据选项卡Id和菜单Id文件名称分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param fileName
	 * @param menuId
	 * @param tabsId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listRecycleById", method = RequestMethod.POST)
	@ResponseBody
	public String listRecycleById(@RequestParam int pageNumber, int pageSize,
			String fileName,Integer menuId,Integer tabsId) throws Exception {
		List<SVDS_Recycle> recycles = null;
		List<SVDS_Recycle> pageInfo = null;
		System.out.println(fileName);
		System.out.println("menuId:"+menuId);
		System.out.println("tabsId:"+tabsId);
		if(menuId!=null){
			recycles = recycleService.listRecycleByMenuId(menuId,fileName);
			pageInfo = recycleService.listRecycleByMenuId(pageNumber, pageSize,menuId,
						fileName);
		}else if(tabsId!=null){
			recycles = recycleService.listRecycleByTabsId(tabsId,fileName);
			pageInfo = recycleService.listRecycleByTabsId(pageNumber, pageSize,tabsId,fileName);
		}
		if(recycles!=null&&recycles.size()>0){
			int total = recycles.size();
			System.out.println("文件数量："+total);
			JSONObject result = new JSONObject();
			result.put("rows", pageInfo);
			result.put("total", total);
			return result.toString();
		}else{
			return null;
		}
	}
	/**
	 * 根据ID修改回收站
	 * 
	 * @param Recycle
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateRecycle", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateRecycle(SVDS_Recycle Recycle,HttpServletRequest request) throws UnknownHostException {
		if (recycleService.updateRecycle(Recycle) > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改回收站操作",
					DateUtils.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return recycleService.updateRecycle(Recycle);
		}
		return null;
	}

	/**
	 * 根据ID删除回收站
	 * 
	 * @param RecycleIds
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/deleteRecycle", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteRecycle(
			@RequestParam(value = "recIds[]", required = false, defaultValue = "") Integer[] recIds,HttpServletRequest request) throws UnknownHostException {
		List<Integer> ids = Arrays.asList(recIds);
		List<SVDS_Files> files = recycleService.deleteRecycle(ids);
		if (files != null && files.size() > 0) {
			for (SVDS_Files svds_Files : files) {
				SVDS_User loginUser = sessionService.getSessionByIp(request);
				logService
						.insertLog(new SVDS_Log("执行了文件还原操作", DateUtils
								.getDate(), loginUser,svds_Files.getMenu() ,
								svds_Files,IpAddressUtils.getIpAddress(request),"成功"));
			}
			return files.size();
		}
		return null;
	}
	/**
	 * 判断是否有存在相同版本的
	 * @param fileNames 文件名称
	 * @param tabsId 选项卡Id
	 * @return
	 */
	@RequestMapping(value = "/isExistVersion", method = RequestMethod.POST)
	@ResponseBody
	public Integer isExistVersion(@Param("fileAll")String fileAll){
		JSONArray array = JSONArray.fromObject(fileAll);
		Integer isExist=recycleService.isExistVersion(array);
		return isExist;
	}

	/**
	 * 根据Id彻底删除文件
	 * 
	 * @param recIds
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deleteRecycleFile", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteRecycleFile(
			@RequestParam(value = "recIds[]", required = false, defaultValue = "") Integer[] recIds,
			HttpServletRequest request) {
		List<Integer> ids = Arrays.asList(recIds);
		Integer isSuccess=recycleService.deleteRecycleFile(ids);
		if(isSuccess!=null){
			return isSuccess;
		}else{
			return null;
		}
	}
}
