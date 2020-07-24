package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

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
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SVDS_Visited;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_UserService;
import ac.drsi.nestor.service.SVDS_VisitedService;

@Controller
public class SVDS_VisitedController {
	@Autowired
	SVDS_VisitedService visitedService;
	@Autowired
	SVDS_UserService userService;
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_LogService logService;

//	@RequestMapping(value = "/getvisitedByReceivedId", method = RequestMethod.POST)
//	@ResponseBody
//	public List<SVDS_Visited> getVisitedByReceivedId(HttpServletRequest request) {
//		SVDS_User loginUser = (SVDS_User) request.getSession().getAttribute(
//				"user");
//		List<SVDS_Visited> visiteds = visitedService
//				.listvisitedByReceived(loginUser.getUserId());
//		if (visiteds.size() != 0) {
//			return visiteds;
//		} else {
//			return null;
//		}
//	}

	/**
	 * 根据用户Id查询点击次数从大到小排序分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCountByUserId", method = RequestMethod.POST)
	@ResponseBody
	public String listCountByUserId(Integer userId,HttpServletRequest request) throws Exception {
		List<SVDS_Visited> visiteds =  visitedService.listCountByUserId(userId);
		if(visiteds.size()>0){
			JSONArray data=JSONArray.fromObject(visiteds);
			return data.toString();
		}else{
			return null;
		}
	}
	
	/**
	 * 根据用户Id查询点击次数从大到小排序分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCount", method = RequestMethod.POST)
	@ResponseBody
	public String listCount(Integer userId) throws Exception {
		List<SVDS_Visited> visiteds  = visitedService.listCount(userId);
		if(visiteds.size()>0){
			JSONArray data=JSONArray.fromObject(visiteds);
			return data.toString();
		}else{
			return null;
		}
	}

	/**
	 * 添加记录
	 * 
	 * @param fileName
	 * @return
	 */
/*	@RequestMapping(value = "/insertVisited", method = RequestMethod.POST)
	@ResponseBody
	public String insertVisited(HttpServletRequest request) {
		SVDS_Visited visited = new SVDS_Visited();
		SVDS_User loginUser = (SVDS_User) request.getSession().getAttribute(
				"user");
		String data = request.getParameter("dataAll");
		JSONArray conditionList = JSONArray.fromObject(data);
		Integer fileCont=0;
		List<Integer> visitedIds = new ArrayList<Integer>();
		for (int i = 0; i < conditionList.size(); i++) {
			com.alibaba.fastjson.JSONObject json = JSON.parseObject(conditionList.get(i).toString());
			JSONArray dataFile = JSONArray.fromObject(json.get("dataFile"));
			fileCont+=dataFile.size();
			for (int j = 0; j < dataFile.size(); j++) {
				System.out.println(json.get("userId"));
				System.out.println(dataFile.get(j));
				SVDS_User received = userService.getUserById(Integer
						.parseInt(json.get("userId").toString()));
				SVDS_Files files = filesService.getFilesById(Integer
						.parseInt(dataFile.get(j).toString()));
				visited.setFiles(files);
				visited.setvisitedDate(DateUtils.getDate());
				visited.setReceived(received);
				visited.setSharer(loginUser);
				Integer visitedId = visitedService.insertvisited(visited);
				visitedIds.add(visitedId);
			}
		}
		if (visitedIds.size() != 0
				&& visitedIds.size() ==  fileCont) {
		
			return "ok";
		} else {
			return null;
		}
	}*/
	/**
	 * 更新文件点击次数
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateCounts", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateVisited(Integer userId,Integer fileId,HttpServletRequest request) throws UnknownHostException{
		SVDS_Visited visited=visitedService.getVisitedByFileId(userId, fileId);
		Integer isSuccess;
		SVDS_Files file=filesService.getFilesById(fileId);
		SVDS_User user=userService.getUserById(userId);
		if(visited!=null){
			Integer counts=visited.getCounts();
			visited.setCounts(counts+1);
			visited.setLastTime(DateUtils.getDate());
			isSuccess = visitedService.updateVisited(visited);
		}else{
			SVDS_Visited visitedInsert=new SVDS_Visited();
			visitedInsert.setCounts(1);
			visitedInsert.setFile(file);
			visitedInsert.setUser(user);
			visitedInsert.setLastTime(DateUtils.getDate());
			isSuccess=visitedService.insertVisited(visitedInsert);
		}
		if (isSuccess > 0) {
			logService.insertLog(new SVDS_Log("执行了打开文件操作", DateUtils.getDate(),
					user, file.getMenu(),file,IpAddressUtils.getIpAddress(request),"成功"));
			return isSuccess;
		}
		return null;
	}

	/**
	 * 删除记录
	 * 
	 * @param visitedIds
	 * @return
	 */
	@RequestMapping(value = "/deleteVisited", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteVisited(
			@RequestParam(value = "visitedIds[]", required = false, defaultValue = "") Integer[] visitedIds) {
		List<Integer> ids = Arrays.asList(visitedIds);
		Integer isSuccess = visitedService.deleteVisited(ids);
		if (isSuccess > 0) {
			return isSuccess;
		}
		return null;
	}
}
