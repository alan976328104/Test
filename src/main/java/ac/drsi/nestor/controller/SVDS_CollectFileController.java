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
import ac.drsi.nestor.entity.SVDS_CollectFile;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Folder;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Security;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_AliasFileService;
import ac.drsi.nestor.service.SVDS_AliasService;
import ac.drsi.nestor.service.SVDS_CollectFileService;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_FolderService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_SecurityService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_CollectFileController {
	@Autowired
	SVDS_CollectFileService collectFileService;
	@Autowired
	SVDS_FolderService folderService;
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_SecurityService securityService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_AliasService aliasService;
	@Autowired
	SVDS_AliasFileService aliasFileService;

	/***
	 * 根据Id查询收藏文件
	 * 
	 * @param colId
	 * @return
	 */
	@RequestMapping(value = "/CollectFileById", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_CollectFile getCollectFileById(Integer colId) {
		SVDS_CollectFile file = collectFileService.getCollectFileById(colId);
		return file;
	}
	/**
	 * 根据收藏夹ID查询文件
	 * 
	 * @param folderId
	 * @return
	 */
	@RequestMapping(value = "/listByFolderId", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_CollectFile> listByFolderId(Integer folderId) {
		return collectFileService.getFolderById(folderId);
	}

	/**
	 * 根据收藏夹ID查询文件
	 * 
	 * @param folderId
	 * @return
	 */
	@RequestMapping(value = "/folderById", method = RequestMethod.POST)
	@ResponseBody
	public String getFolderById(@RequestParam int pageNumber, int pageSize,
			String sortOrder, Integer folderId) {
		List<SVDS_CollectFile> folders = null;
		if (folderId != null) {
			folders = collectFileService.listFolderById(pageNumber, pageSize,
					sortOrder, folderId);
			if (folders != null) {
				JSONArray array = JSONArray.fromObject(folders);
				JSONObject result = new JSONObject();
				System.out.println(array.toString());
				result.put("rows", array);
				result.put("total", folders.size());
				return result.toString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 根据文件Id移修改所处收藏夹
	 * 
	 * @param fileId
	 * @param folderId
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/moveFile", method = RequestMethod.POST)
	@ResponseBody
	public Integer moveByFileId(
			@RequestParam(value = "fileIds[]", required = false, defaultValue = "") Integer[] fileIds,
			Integer folderId, @RequestParam(value = "colIds[]", required = false, defaultValue = "")Integer[] colIds, HttpServletRequest request) throws UnknownHostException {
		Integer count = 0;
		List<Integer> ids = Arrays.asList(fileIds);
		System.out.println("文件夹"+folderId);
		List<Integer> colids = Arrays.asList(colIds);
		System.out.println("文件夹"+folderId);
		System.out.println("收藏Id"+colids);
		List<SVDS_Files> files = filesService.getFilesByIds(ids);
		if(files!=null){
			for (SVDS_Files svds_Files : files) {
				SVDS_Folder folder = folderService.getFolderById(folderId);
				System.out.println(folder);
				List<SVDS_CollectFile> colFile = collectFileService.getCollectFileByIds(colids);
				for (SVDS_CollectFile svds_CollectFile : colFile) {
					// colFile.setFiles(files);
					System.out.println(svds_Files.getFileId());
					System.out.println(colFile);
					svds_CollectFile.setFolder(folder);
					Integer isSuccess = collectFileService.updateCollectFile(svds_CollectFile);
					if (isSuccess > 0) {
						SVDS_User loginUser = sessionService.getSessionByIp(request);
						logService
								.insertLog(new SVDS_Log("移动收藏文件操作",
										DateUtils.getDate(), loginUser, svds_Files
												.getMenu(), svds_Files,IpAddressUtils.getIpAddress(request),"成功"));
					}
					count = count + 1;
				}
			}
		}
		System.out.println(count);
		System.out.println(ids.size());
		if (count == ids.size()) {
			return 1;
		} else {
			return null;
		}
	}

	/**
	 * 添加收藏文件
	 * 
	 * @param fileName
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/insertCollectFile", method = RequestMethod.POST)
	@ResponseBody
	public String insertCollectFile(
			Integer folderId,
			@RequestParam(value = "fileIds[]", required = false, defaultValue = "") Integer[] fileIds,
			HttpServletRequest request) throws UnknownHostException {
		List<Integer> ids = Arrays.asList(fileIds);
		List<SVDS_Files> files = filesService.getFilesByIds(ids);
		List<Integer> colIds = new ArrayList<Integer>();
		for (SVDS_Files file : files) {
			SVDS_CollectFile isNull = collectFileService.getCollectFile(
					file.getFileId(), folderId);
			if (isNull == null) {
				SVDS_CollectFile collectFile = new SVDS_CollectFile();
				collectFile.setColDate(DateUtils.getDate());
				SVDS_Folder folder = folderService.getFolderById(folderId);
				collectFile.setFolder(folder);
				collectFile.setFiles(file);
				Integer colId = collectFileService
						.insertCollectFile(collectFile);
				colIds.add(colId);
			} else {
				return "exist";
			}
		}
		if (ids.size() == colIds.size()) {
			for (int i = 0; i < files.size(); i++) {
				SVDS_User loginUser = sessionService.getSessionByIp(request);
				logService.insertLog(new SVDS_Log("执行了添加收藏文件操作", DateUtils
						.getDate(), loginUser, files.get(i).getMenu(), files
						.get(i),IpAddressUtils.getIpAddress(request),"成功"));
			}
			return "ok";
		} else {
			return null;
		}
	}

	/**
	 * 根据ID修改收藏文件
	 * 
	 * @param CollectFile
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateCollectFile", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateCollectFile(SVDS_CollectFile CollectFile,
			HttpServletRequest request) throws UnknownHostException {
		if (collectFileService.updateCollectFile(CollectFile) > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改收藏文件操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return collectFileService.updateCollectFile(CollectFile);
		}
		return null;
	}

	/**
	 * 根据ID删除收藏文件
	 * 
	 * @param CollectFileIds
	 * @return
	 * @throws UnknownHostException 
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteCollectFile", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteCollectFile(
			Integer folderId,
			@RequestParam(value = "fileIds[]", required = false, defaultValue = "") Integer[] fileIds,
			HttpServletRequest request) throws UnknownHostException {
		List<Integer> ids = Arrays.asList(fileIds);
		Integer isSuccess = collectFileService.deleteCollectFile(ids, folderId);
		if (isSuccess > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			for (int i = 0; i < ids.size(); i++) {
				SVDS_Files file = filesService.getFilesById(ids.get(i));
				logService.insertLog(new SVDS_Log("执行了删除收藏文件操作", DateUtils
						.getDate(), loginUser, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
			}

			return isSuccess;
		}
		return 0;
	}
/**
 * 收藏文件分页查询
 * @param pageNumber
 * @param pageSize
 * @param selectName
 * @param selectVal
 * @param folderId
 * @return
 */
	@RequestMapping(value = "/pageCollection", method = RequestMethod.POST)
	@ResponseBody
	public String selectColl(@RequestParam int pageNumber, int pageSize,
			String selectName, String selectVal, Integer folderId) {
		SVDS_Files file = new SVDS_Files();
		SVDS_CollectFile collectFile = new SVDS_CollectFile();
		List<SVDS_CollectFile> collectFiles = new ArrayList<SVDS_CollectFile>();
		// List<SVDS_CollectFile> pageCollection = new
		// ArrayList<SVDS_CollectFile>();
		List<Integer> aliasFileIds = new ArrayList<Integer>();
		List<Integer> collFileIds = new ArrayList<Integer>();
		if (selectName == "fileName" || selectName.equals("fileName")) {
			file.setFileName(selectVal);
		} else if (selectName == "fileDate" || selectName.equals("fileDate")) {
			collectFile.setColDate(selectVal);
		} else if (selectName == "format" || selectName.equals("format")) {
			file.setFormat(selectVal);
		} else if (selectName == "securityId"
				|| selectName.equals("securityId")) {
			System.out.println(selectVal);
			SVDS_Security security = securityService.getSecurityById(Integer
					.parseInt(selectVal));
			file.setSecurity(security);
		}
		if (selectName == "alias" || selectName.equals("alias")) {
			List<SVDS_AliasFile> aliasFile = aliasFileService
					.listAliasFileById(Integer.parseInt(selectVal));
			List<SVDS_CollectFile> cols = collectFileService
					.getFolderById(folderId);
			if (cols != null) {
				for (SVDS_CollectFile svds_CollectFile : cols) {
					collFileIds.add(svds_CollectFile.getFiles().getFileId());
				}
			}
			if (aliasFile != null) {
				for (SVDS_AliasFile svds_AliasFile : aliasFile) {
					aliasFileIds.add(svds_AliasFile.getFiles().getFileId());
				}
			}
			List<Integer> resultFileIds = getRepetition(aliasFileIds,
					collFileIds);
			for (SVDS_CollectFile svds_CollectFile : cols) {
				for (int i = 0; i < resultFileIds.size(); i++) {
					if (svds_CollectFile.getFiles().getFileId() == resultFileIds
							.get(i)) {
						collectFiles.add(svds_CollectFile);
					}
				}
			}
		} else {
			collectFiles = collectFileService.selectCollectSql(file, folderId,
					collectFile);
		}
		if (collectFiles != null) {
			JSONArray data = JSONArray.fromObject(collectFiles);
			JSONObject result = new JSONObject();
			result.put("rows", data);
			result.put("total", collectFiles.size());
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
}
