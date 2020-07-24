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
import ac.drsi.nestor.entity.SVDS_RoleMenu;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_AliasFileService;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_RoleMenuService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_AliasFileController {
	@Autowired
	SVDS_AliasFileService aliasFileService;
	@Autowired
	SVDS_FilesService fileService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_RoleMenuService roleMoenuService;
	/**
	 * 根据标签Id查询
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/listById", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_AliasFile> listById(Integer aliasId){
		return aliasFileService.listAliasFileById(aliasId);
	}
	/**
	 * 根据标签Id分页查询
	 * 
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/aliasFileById", method = RequestMethod.POST)
	@ResponseBody
	public String getAliasFileById(@RequestParam int pageNumber, int pageSize,String sortOrder,
			Integer aliasId, Integer aliasType,String fileName,HttpServletRequest request) {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		if (aliasId != null) {
			List<SVDS_AliasFile> aliasFiles = new ArrayList<SVDS_AliasFile>();
			List<SVDS_Files> files = new ArrayList<SVDS_Files>();
			List<SVDS_AliasFile> pageInfo = new ArrayList<SVDS_AliasFile>();
			List<Integer> fileIds = new ArrayList<Integer>();
			System.out.println("标签Id："+aliasId);
			aliasFiles = aliasFileService.listAliasFileById(aliasId);
			System.out.println("文件标签数量："+aliasFiles.size());
			for (SVDS_AliasFile svds_aliasfile : aliasFiles) {
				List<SVDS_RoleMenu> rolemenus = roleMoenuService
						.listByMenuId(svds_aliasfile.getFiles().getMenu().getId());
				for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
					if (svds_RoleMenu.getRoleId() == loginUser.getRole()
							.getRoleId()) {
						fileIds.add(svds_aliasfile.getFiles().getFileId());
					}
				}
			}
			if(fileName!=""){
				System.out.println("文件名称"+fileName);
				files=fileService.getFilesByIdsAndFileName(fileIds, fileName);
				fileIds = new ArrayList<Integer>();
				for (SVDS_Files svds_file : files) {
					fileIds.add(svds_file.getFileId());
				}
			}
			
			if(aliasId>0&&fileIds.size()>0){
				System.out.println(fileIds.toString());
				System.out.println(aliasId);
				pageInfo = aliasFileService.listByFileIdAndAliasId(fileIds, aliasId,sortOrder);
			}
			
			//			if (aliasType != 0) {
//				SVDS_User loginUser = sessionService.getSessionByIp();
//				if (loginUser.equals("D")) {// 非密(未修改完成)
//					for (int i = 0; i < aliasFiles.size(); i++) {
//						SVDS_Files file = new SVDS_Files();
//						file = fileService.getFilesById(aliasFiles.get(i)
//								.getFiles().getFileId());
//						if (file.getSecurity().getSecurityId() == 1) {
//							fileIds.add(file.getFileId());
//						}
//					}
//				} else {
//					System.out.println(aliasFiles.size());
//					for (int i = 0; i < aliasFiles.size(); i++) {
//						System.out.println(aliasFiles.get(i).getFiles().getFileId());
//						fileIds.add(aliasFiles.get(i).getFiles().getFileId());
//					}
//				}
//			} else {
//				for (int i = 0; i < aliasFiles.size(); i++) {
//					fileIds.add(aliasFiles.get(i).getFiles().getFileId());
//				}
//			}
//			if (fileIds.size() != 0) {
//				files = fileService.getFilesByIds(fileIds);
//				pageInfo = fileService.listFilesByIdsSort(pageNumber, pageSize,sortOrder,
//						fileIds,loginUser.getUserId());
//			}

			int total = files.size();
			JSONArray array = JSONArray.fromObject(pageInfo);
			JSONObject result = new JSONObject();
			System.out.println(array.toString());
			result.put("rows", array);
			result.put("total", pageInfo.size());
			return result.toString();
		} else {
			return null;
		}
	}

	/**
	 * 根据标签Id删除添加过的文件
	 * 
	 * @param ids
	 * @param aliasId
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/deleteAliasFile", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteAliasFile(Integer aliasId,@RequestParam(value = "fileIds[]", required = false, defaultValue = "") Integer[] fileIds,HttpServletRequest request) throws UnknownHostException {
		List<Integer> ids = Arrays.asList(fileIds);
		Integer isSuccess=aliasFileService.deleteAliasFile(ids, aliasId);
		if(isSuccess>0){
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			for(int i=0;i<ids.size();i++){
			SVDS_Files file=fileService.getFilesById(ids.get(i));
				logService.insertLog(new SVDS_Log("执行了删除标签文件操作", DateUtils
						.getDate(), loginUser, file.getMenu(),file,IpAddressUtils.getIpAddress(request),"成功"));
			}
			
			return 1;
		}
		return 0;
	}
}
