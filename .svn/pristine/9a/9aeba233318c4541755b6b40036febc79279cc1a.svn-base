package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.common.DateUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.entity.SVDS_Folder;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_FolderService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_FolderController {
	@Autowired
	SVDS_FolderService folderService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 查询用户的收藏夹
	 * 
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/folderByUser", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_Folder> getFolderByUser(Integer userId) {
		System.out.println("用户Id："+userId);
		List<SVDS_Folder> folders = folderService.getFolderByUser(userId);
//		if (folders != null && folders.size() == 0) {
//			folderService.insertFolder("默认收藏夹", userId, 0);
//			folders = folderService.getFolderByUser(userId);
//		}
		return folders;
	}

	/**
	 * 查询每个收藏夹的数据
	 * 
	 * @param folderId
	 * @return
	 */
	/*
	 * @RequestMapping(value = "/folderById", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Folder getFolderById(Integer folderId) { Folder
	 * folder = folderService.getFolderById(folderId); return folder; }
	 */

	/**
	 * 添加收藏夹
	 * 
	 * @param fileName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertFolder", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertFolder(String folderName, Integer userId,
			Integer parentId, HttpServletRequest request) throws UnknownHostException {
		System.out.println(folderName);
		System.out.println(parentId);
		System.out.println(userId);
		SVDS_Folder folder = folderService.isExistFolder(folderName, parentId,
				userId);
		System.out.println(folder);
		if (folder == null) {
			Integer folderId = folderService.insertFolder(folderName, userId,
					parentId);
			if (folderId > 0) {
				SVDS_User loginUser = sessionService.getSessionByIp(request);
				logService.insertLog(new SVDS_Log("执行了添加收藏夹操作", DateUtils
						.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
				return folderId;
			} else {
				return null;
			}
		} else {
			return 0;
		}
	}

	/**
	 * 根据ID修改收藏夹的父id
	 * 
	 * @param Alias
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateFolderByParent", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateFolderByParent(Integer forderId, Integer parentId,
			HttpServletRequest request) throws UnknownHostException {
		System.out.println("收藏夹id:" + forderId);
		SVDS_Folder folder = folderService.getFolderById(forderId);
		folder.setParentId(parentId);
		Integer isSuccess = folderService.updateFolder(folder);
		if (isSuccess > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("拖拽收藏夹操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return isSuccess;
		}
		return null;
	}

	/**
	 * 根据ID修改收藏夹
	 * 
	 * @param SVDS_Folder
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateFolder", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateFolder(Integer folderId, String folderName,
			HttpServletRequest request) throws UnknownHostException {
		SVDS_Folder folder = folderService.getFolderById(folderId);
		folder.setFolderName(folderName);
		if (folderService.updateFolder(folder) > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改收藏夹名称操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return folderService.updateFolder(folder);
		}
		return null;
	}

	/**
	 * 根据ID修改收藏夹
	 * 
	 * @param SVDS_Folder
	 * @return
	 */
	@RequestMapping(value = "/updateCol", method = RequestMethod.POST)
	@ResponseBody
	public Integer CollectFiles(
			Integer folderId,
			@RequestParam(value = "fileIds[]", required = false, defaultValue = "") Integer[] fileIds,
			HttpServletRequest request) {
		/*
		 * if (folderService.updateFolder(folder) > 0) { User loginUser = (User)
		 * request.getSession().getAttribute("user"); logService.insertLog(new
		 * Log("执行了修改收藏夹操作", DateUtils.getDate(), loginUser)); return
		 * folderService.updateFolder(folder); }
		 */
		return null;
	}

	/**
	 * 根据ID删除收藏夹
	 * 
	 * @param FolderIds
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/deleteFolder", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteFolder(
			@RequestParam(value = "folderIds[]", required = false, defaultValue = "") Integer[] folderIds,
			HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		System.out.println(folderIds);
		List<Integer> ids = Arrays.asList(folderIds);
		Integer isSuccess = folderService.deleteFolder(ids,
				loginUser.getUserId());
		if (isSuccess != null) {
			logService.insertLog(new SVDS_Log("执行了删除收藏夹操作",
					DateUtils.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return isSuccess;
		}
		return null;
	}
}
