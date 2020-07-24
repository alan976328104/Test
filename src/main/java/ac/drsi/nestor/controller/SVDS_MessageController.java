package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import ac.drsi.nestor.entity.SVDS_AliasFile;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Message;
import ac.drsi.nestor.entity.SVDS_Security;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_AliasFileService;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MessageService;
import ac.drsi.nestor.service.SVDS_SecurityService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;

import com.alibaba.fastjson.JSON;

@Controller
public class SVDS_MessageController {
	@Autowired
	SVDS_MessageService messageService;
	@Autowired
	SVDS_UserService userService;
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SecurityService securityService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_AliasFileService aliasFileService;
/**
 * 获取用户数量
 * @param userId
 * @param request
 * @return
 */
	@RequestMapping(value = "/getMessageByState", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_Message> getMessageByReceivedId(Integer userId,HttpServletRequest request) {
		/*SVDS_User loginUser = (SVDS_User) request.getSession().getAttribute(
				"user");*/
		List<SVDS_Message> messages = messageService
				.listMessageState(userId);
		if (messages.size() != 0) {
			return messages;
		} else {
			return null;
		}
	}

	/**
	 * 分页查询接消息
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pageMessage", method = RequestMethod.POST)
	@ResponseBody
	public String pageInfo(@RequestParam int pageNumber, int pageSize,
			String username,HttpServletRequest request)  {
		List<SVDS_Message> messages = null;
		List<SVDS_Message> pageInfo = null;
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		System.out.println(username);
		if(username == "" || username == null){
			messages = messageService.listMessageByReceived(loginUser.getUserId());
			pageInfo = messageService.listMessageByReceived(pageNumber, pageSize,
					loginUser.getUserId());
		}else{
			messages = messageService.listMessageByName(loginUser.getUserId(),username);
			pageInfo = messageService.listMessageByName(pageNumber, pageSize,
					loginUser.getUserId(),username);
		}
		
		int total = messages.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		System.out.println(result.toString());
		return result.toString();
	}

	/**
	 * 分页查询分享消息
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pageSharer", method = RequestMethod.POST)
	@ResponseBody
	public String pageSharer(@RequestParam int pageNumber, int pageSize,String selectName, String selectVal,HttpServletRequest request) {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		SVDS_Files file = new SVDS_Files();
		System.out.println(file);
		SVDS_Message svds_message=new SVDS_Message();
		List<SVDS_Message> messages = new ArrayList<SVDS_Message>();
		List<Integer> aliasFileIds = new ArrayList<Integer>();
		List<Integer> messsFileIds = new ArrayList<Integer>();
		System.out.println(selectName);
		System.out.println(selectVal);
		if (selectName == "fileName" || selectName.equals("fileName")) {
			file.setFileName(selectVal);
		} else if (selectName == "fileDate" || selectName.equals("fileDate")) {
			svds_message.setMessageDate(selectVal);
		} else if (selectName == "format" || selectName.equals("format")) {
			file.setFormat(selectVal);
		} else if (selectName == "securityId"
				|| selectName.equals("securityId")) {
			System.out.println(selectVal);
			SVDS_Security security = securityService.getSecurityById(Integer
					.parseInt(selectVal));
			file.setSecurity(security);
		}
		if(selectName == "alias" || selectName.equals("alias")){
			List<SVDS_AliasFile> aliasFile=aliasFileService.listAliasFileById(Integer
					.parseInt(selectVal));
			List<SVDS_Message> message = messageService.listMessageBySharer(loginUser.getUserId());
			if (message != null) {
				for (SVDS_Message svds_Message : message) {
					messsFileIds.add(svds_Message.getFiles().getFileId());
				}
			}
			if (aliasFile != null) {
				for (SVDS_AliasFile svds_AliasFile : aliasFile) {
					aliasFileIds.add(svds_AliasFile.getFiles().getFileId());
				}
			}
			List<Integer> resultFileIds = getRepetition(aliasFileIds,
					messsFileIds);
			for (SVDS_Message svds_Message : message) {
				for (int i = 0; i < resultFileIds.size(); i++) {
					if(svds_Message.getFiles().getFileId()==resultFileIds.get(i)){
						messages.add(svds_Message);
					}
				}
			}
			//file.setAlias(alias);
		}else{
			System.out.println(file);
			System.out.println(loginUser.getUserId());
			messages=messageService.selectMessageSql(file,loginUser.getUserId(),svds_message);
		}
		if (messages != null) {
			JSONArray data = JSONArray.fromObject(messages);
			JSONObject result = new JSONObject();
			result.put("rows", data);
			result.put("total", messages.size());
			System.out.println(result.toString());
			return result.toString();
		} else {
			return null;
		}
	}
	/**
	 * 两个list取重复
	 * 
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static List<Integer> getRepetition(List<Integer> list1,
			List<Integer> list2) {
		List<Integer> result = new ArrayList<Integer>();
		for (Integer integer : list2) {// 遍历list1
			if (list1.contains(integer)) {// 如果存在这个数
				result.add(integer);// 放进一个list里面，这个list就是交集
			}
		}
		return result;
	}
	/**
	 * 添加消息
	 * 
	 * @param fileName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertMessage", method = RequestMethod.POST)
	@ResponseBody
	public String insertMessage(HttpServletRequest request) throws UnknownHostException {
		SVDS_Message message = new SVDS_Message();
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		String data = request.getParameter("dataAll");
		JSONArray conditionList = JSONArray.fromObject(data);
		Integer fileCont=0;
		List<Integer> messageIds = new ArrayList<Integer>();
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
				message.setFiles(files);
				message.setMessageDate(DateUtils.getDate());
				message.setReceived(received);
				message.setSharer(loginUser);
				message.setState(0);
				Integer messageId = messageService.insertMessage(message);
				if(messageId>0){
					messageIds.add(messageId);
					logService.insertLog(new SVDS_Log("执行了分享操作", DateUtils
							.getDate(), loginUser, files.getMenu(), files,IpAddressUtils.getIpAddress(request),"成功"));
				}
			}
		}
		if (messageIds.size() != 0
				&& messageIds.size() ==  fileCont) {
		
			return "ok";
		} else {
			return null;
		}
	}
	/**
	 * 读取消息
	 * 
	 * @param messageIds
	 * @return
	 */
	@RequestMapping(value = "/updateMessageState", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateMessageState(Integer messageId){
		SVDS_Message message=messageService.getMessageById(messageId);
		message.setState(1);
		Integer isSuccess=messageService.updateMessage(message);
		if(isSuccess>0){
			return isSuccess;
		}
		return 0;
	}
	
	/**
	 * 删除消息
	 * 
	 * @param messageIds
	 * @return
	 */
	@RequestMapping(value = "/deleteMessage", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteMessage(
			@RequestParam(value = "messageIds[]", required = false, defaultValue = "") Integer[] messageIds) {
		List<Integer> ids = Arrays.asList(messageIds);
		System.out.println(ids);
		Integer isSuccess = messageService.deleteMessage(ids);
		System.out.println(isSuccess);
		if (isSuccess > 0) {
			return isSuccess;
		}
		return null;
	}
	/**
	 * 清空消息
	 * 
	 * @param messageIds
	 * @return
	 */
	@RequestMapping(value = "/deleteAllMessage", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteAllMessage(HttpServletRequest request) {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		Integer isSuccess = messageService.deleteAllMessage(loginUser.getUserId());
		if (isSuccess > 0) {
			return isSuccess;
		}
		return null;
	}
}
