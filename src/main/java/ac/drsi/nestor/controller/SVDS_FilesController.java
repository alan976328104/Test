package ac.drsi.nestor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.util.IOUtils;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import ac.drsi.common.DateUtils;
import ac.drsi.common.ExUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.common.SolrUtils;
import ac.drsi.common.StringUtil;
import ac.drsi.common.ZipUtils;
import ac.drsi.nestor.dao.FolderUpDao;
import ac.drsi.nestor.dao.TabsDao;
import ac.drsi.nestor.dao.infoDao;
import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.InfoData;
import ac.drsi.nestor.entity.SVDS_AliasFile;
import ac.drsi.nestor.entity.SVDS_Button;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.entity.SVDS_RoleMenu;
import ac.drsi.nestor.entity.SVDS_Security;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SearchBean;
import ac.drsi.nestor.entity.Tabs;
import ac.drsi.nestor.service.SVDS_AliasFileService;
import ac.drsi.nestor.service.SVDS_AliasService;
import ac.drsi.nestor.service.SVDS_ButtonService;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_MenuService;
import ac.drsi.nestor.service.SVDS_RecycleService;
import ac.drsi.nestor.service.SVDS_RoleButtonService;
import ac.drsi.nestor.service.SVDS_RoleMenuService;
import ac.drsi.nestor.service.SVDS_SecurityService;
import ac.drsi.nestor.service.SVDS_SessionService;

@Controller
public class SVDS_FilesController {
	@Autowired
	SVDS_FilesService filesService;
	@Autowired
	SVDS_RecycleService recycleService;
	@Autowired
	SVDS_SecurityService securityService;
	@Autowired
	SVDS_AliasService aliasService;
	@Autowired
	SVDS_AliasFileService aliasFileService;
	@Autowired
	SVDS_LogService logService;
	@Autowired
	SVDS_MenuService menuService;
	@Autowired
	SVDS_RoleMenuService roleMenuService;
	@Autowired
	SVDS_RoleButtonService roleButtonService;
	@Autowired
	SVDS_ButtonService buttonService;
	@Autowired
	FolderUpDao dao;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	TabsDao tabsDao;
	@Autowired
	infoDao infoDao;

	/**
	 * 根据名称模糊查询
	 * 
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/filesByName", method = RequestMethod.GET)
	@ResponseBody
	public String listFilesByName(String fileName) {
		List<SVDS_Files> files = filesService.listFilesByName(fileName);
		JSONArray data = JSONArray.fromObject(files);
		JSONObject result = new JSONObject();
		result.put("rows", data);
		result.put("total", files.size());
		return result.toString();
	}

	@RequestMapping(value = "/filesById", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Files getFilesById(Integer fileId) {
		SVDS_Files file = filesService.getFilesById(fileId);
		return file;
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pageFiles", method = RequestMethod.POST)
	@ResponseBody
	public String pageInfo(@RequestParam int pageNumber, int pageSize,
			String fileName) {
		List<SVDS_Files> files = null;
		List<SVDS_Files> pageInfo = null;
		if (fileName == "" || fileName == null) {
			files = filesService.getFilesAll();
			pageInfo = filesService.getFilesAll(pageNumber, pageSize);
		} else {
			files = filesService.listFilesByName(fileName);
			pageInfo = filesService.listFilesByName(pageNumber, pageSize,
					fileName);
		}
		int total = files.size();
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		return result.toString();
	}

	public List<SVDS_Files> filtrateFiles(List<SVDS_Files> list) {
		List<SVDS_Files> newFiles = new ArrayList<SVDS_Files>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(j).getFileId().equals(list.get(i).getFileId())) {
					newFiles.add(list.get(j));
					list.remove(list.get(j));
				}
			}
		}
		return newFiles;
	}

	public List<SVDS_AliasFile> filtrateAliasFiles(List<SVDS_AliasFile> list) {
		List<SVDS_AliasFile> newAliasFiles = new ArrayList<SVDS_AliasFile>();
		for (int i = 0; i < list.size(); i++) {
			for (int j = list.size() - 1; j > i; j--) {
				if (list.get(i).getFiles().getFileId() == list.get(j)
						.getFiles().getFileId()) {
					newAliasFiles.add(list.get(j));
					list.remove(list.get(j));
				}
			}
		}
		return newAliasFiles;
	}

	/**
	 * 多条件查询分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/pageSearch", method = RequestMethod.POST)
	@ResponseBody
	public String selectWhitFilesSql(@RequestParam int pageNumber,
			String sortOrder, int pageSize, String inputall,
			HttpServletRequest request) {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		filesService.emptyAbstracts();
		List<SVDS_Files> files = new ArrayList<SVDS_Files>();
		List<Integer> fileIds = new ArrayList<Integer>();
		List<Integer> menuIds = new ArrayList<Integer>();
		List<SVDS_Files> pageInfo = null;
		Map<String, Object> param = new HashMap<String, Object>();
		List<SearchBean> searchBean = new ArrayList<SearchBean>();
		JSONArray json = JSONArray.fromObject(inputall);
		List<SVDS_AliasFile> aliasFiles = new ArrayList<SVDS_AliasFile>();
		String type = "";
		// boolean isFirst=true;
		QueryResponse response;
		SolrDocumentList results = new SolrDocumentList();
		Map<String, Map<String, List<String>>> highlighting = new HashMap<String, Map<String, List<String>>>();
		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i);
				SVDS_Files file = new SVDS_Files();
				if (job.get("searchName").toString().equals("fileName")) {
					file.setFileName(job.get("searchVal").toString());
					System.out.println("文件名称");
					System.out.println(job.get("condSelect").toString());
					System.out.println(job.get("searchVal").toString());
					param.put(job.get("searchName").toString(),
							job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(job.get("searchVal").toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				} else if (job.get("searchName").toString().equals("fileDate")) {
					file = new SVDS_Files();
					file.setFileDate(job.get("searchVal").toString());
					System.out.println("日期");
					System.out.println(job.get("condSelect").toString());
					System.out.println(job.get("searchVal").toString());
					param.put(job.get("searchName").toString(),
							job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(job.get("searchVal").toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				} else if (job.get("searchName").toString().equals("format")) {
					file.setFormat(job.get("searchVal").toString());
					System.out.println("格式");
					System.out.println(job.get("condSelect").toString());
					System.out.println(job.get("searchVal").toString());
					param.put(job.get("searchName").toString(),
							job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(job.get("searchVal").toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				} else if (job.get("searchName").toString()
						.equals("securityId")) {
					SVDS_Security security = securityService
							.getSecurityByName(job.get("searchVal").toString());
					file.setSecurity(security);
					System.out.println("密级");
					System.out.println(job.get("searchVal").toString());
					System.out.println(job.get("condSelect").toString());
					param.put(job.get("searchName").toString(),
							job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(security.getSecurityId().toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				} else if (job.get("searchName").toString().equals("item")) {
					System.out.println("项目名");
					System.out.println(job.get("condSelect").toString());
					System.out.println(job.get("searchVal").toString());
					SVDS_Menu menu = menuService.getMenuByName(job.get(
							"searchVal").toString());
					file.setMenu(menu);
					param.put(job.get("searchName").toString(),
							job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(menu.getId().toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				}
				if (i != 0) {// 查询不是第一个
					job = json.getJSONObject(i - 1);
					type = job.get("condSelect").toString();
				} else {
					type = job.get("condSelect").toString();
				}
			}
			System.out.println(searchBean.toString());
			if (searchBean.size() > 0) {
				List<SVDS_Files> oneFiles = filesService
						.selectFilesSql(searchBean);
				// List<SVDS_Files> oneFiles =
				// filesService.selectWhitFilesSql(file, param);
				for (SVDS_Files svds_Files : oneFiles) {
					files.add(svds_Files);
				}
			}
			if (files.size() > 0) {
				// System.out.println(isFirst);
				// if ((type.equals("0") || type == "0")&&isFirst==true) {// 和
				// files = filtrateFiles(files);
				// }
				for (int i = 0; i < files.size(); i++) {
					List<SVDS_RoleMenu> rolemenus = roleMenuService
							.listByMenuId(files.get(i).getMenu().getId());
					for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
						if (svds_RoleMenu.getRoleId() == loginUser.getRole()
								.getRoleId()) {
							fileIds.add(files.get(i).getFileId());
						}
					}
				}
			}
			System.out.println("文件属性查询ids数量：" + fileIds.toString());
			// 标签
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i);
				if (job.get("searchName").toString().equals("alias")) {
					// SVDS_Alias alias =
					// aliasService.getByName(job.get("searchVal").toString());
					// if (alias != null) {
					//
					System.out.println("标签");
					// }
					List<SVDS_AliasFile> afs = aliasFileService
							.listAliasFileById(Integer.parseInt(job.get(
									"searchVal").toString()));

					for (SVDS_AliasFile svds_AliasFile : afs) {
						aliasFiles.add(svds_AliasFile);
					}
					if (i != 0) {// 标签查询不是第一个
						job = json.getJSONObject(i - 1);
						type = job.get("condSelect").toString();
					} else {
						type = job.get("condSelect").toString();
					}
				}
			}
			System.out.println("标签查询文件数量：" + aliasFiles.size());
			if (aliasFiles.size() != 0) {
				if (type.equals("0") || type == "0") {// 和
					System.out.println("文件id数量：" + fileIds.size());
					if (fileIds.size() != 0) {
						List<Integer> list = new ArrayList<Integer>();
						for (int i = 0; i < aliasFiles.size(); i++) {
							Integer fileId = aliasFiles.get(i).getFiles()
									.getFileId();
							SVDS_Files fileM = filesService
									.getFilesById(fileId);
							List<SVDS_RoleMenu> rolemenus = roleMenuService
									.listByMenuId(fileM.getMenu().getId());
							for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
								if (svds_RoleMenu.getRoleId() == loginUser
										.getRole().getRoleId()) {
									list.add(fileId);
								}
							}
						}
						fileIds.retainAll(list);
					} else {
						System.out.println("标签查询");
						aliasFiles = filtrateAliasFiles(aliasFiles);
						for (int i = 0; i < aliasFiles.size(); i++) {
							Integer fileId = aliasFiles.get(i).getFiles()
									.getFileId();
							SVDS_Files fileM = filesService
									.getFilesById(fileId);
							List<SVDS_RoleMenu> rolemenus = roleMenuService
									.listByMenuId(fileM.getMenu().getId());
							for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
								if (svds_RoleMenu.getRoleId() == loginUser
										.getRole().getRoleId()) {
									fileIds.add(fileId);
								}
							}
						}
					}
				} else {
					for (int i = 0; i < aliasFiles.size(); i++) {
						Integer fileId = aliasFiles.get(i).getFiles()
								.getFileId();
						SVDS_Files fileM = filesService.getFilesById(fileId);
						List<SVDS_RoleMenu> rolemenus = roleMenuService
								.listByMenuId(fileM.getMenu().getId());
						for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
							if (svds_RoleMenu.getRoleId() == loginUser
									.getRole().getRoleId()) {
								fileIds.add(fileId);
							}
						}
					}
				}
			}
			// 基本信息
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i);
				if (job.get("searchName").toString().equals("basic")) {
					System.out.println("基本属性");
					if (job.get("rangeVal") != null) {
						Integer maxId;
						System.out.println("范围查询");
						List<Integer> ids = new ArrayList<Integer>();
						ids = infoDao.listInfoDataByValue(job.get("searchVal")
								.toString());
						for (int j = 0; j < ids.size(); j++) {
							maxId = infoDao.getInfoDataByMaxId(ids.get(j));
							if (maxId == null) {
								maxId = infoDao.getInfoDataById(ids.get(j));
							}
							InfoData infoData = infoDao.getInfoDataByRange(ids
									.get(j), maxId, job.get("rangeVal")
									.toString());
							if (infoData != null) {
								if (!job.get("inputStart").toString()
										.equals("")
										&& !job.get("inputEnd").toString()
												.equals("")) {
									if (StringUtil.getInteger(infoData
											.getValue()) > Integer.parseInt(job
											.get("inputStart").toString())
											&& StringUtil.getInteger(infoData
													.getValue()) < Integer
													.parseInt(job.get(
															"inputEnd")
															.toString())) {
										menuIds.add(infoData.getMenuid());
									}
								}
							}
						}
					} else {
						menuIds = infoDao.menuIdInfoDataByValue(job.get(
								"searchVal").toString());
					}
					if (i != 0) {// 基本信息不是第一个
						job = json.getJSONObject(i - 1);
						type = job.get("condSelect").toString();
					} else {
						type = job.get("condSelect").toString();
					}
				}
			}
			if (menuIds.size() != 0) {
				if (type.equals("0") || type == "0") {// 和
					if (fileIds.size() != 0) {
						List<Integer> list = new ArrayList<Integer>();
						for (int i = 0; i < menuIds.size(); i++) {
							List<SVDS_RoleMenu> rolemenus = roleMenuService
									.listByMenuId(menuIds.get(i));
							for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
								if (svds_RoleMenu.getRoleId() == loginUser
										.getRole().getRoleId()) {
									List<SVDS_Files> filesMenu = filesService
											.listFilesByMenuId(menuIds.get(i));
									for (SVDS_Files svds_Files : filesMenu) {
										list.add(svds_Files.getFileId());
									}
								}
							}
						}
						fileIds.retainAll(list);
					}
				} else {
					for (int i = 0; i < menuIds.size(); i++) {
						List<SVDS_RoleMenu> rolemenus = roleMenuService
								.listByMenuId(menuIds.get(i));
						for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
							if (svds_RoleMenu.getRoleId() == loginUser
									.getRole().getRoleId()) {
								List<SVDS_Files> filesMenu = filesService
										.listFilesByMenuId(menuIds.get(i));
								for (SVDS_Files svds_Files : filesMenu) {
									fileIds.add(svds_Files.getFileId());
								}
							}
						}
					}
				}
			}
			// 全文检索
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i);
				if (job.get("searchName").toString().equals("fullSearch")) {
					response = SolrUtils.Query(job.get("searchVal").toString());
					results = response.getResults();
					highlighting = response.getHighlighting();
					request.getSession().setAttribute("keyword",
							job.get("searchVal").toString());
					if (i != 0) {// 全文检索查询不是第一个
						job = json.getJSONObject(i - 1);
						type = job.get("condSelect").toString();
						System.out.println("全文检索查询不是第一个");
					} else {
						type = job.get("condSelect").toString();
					}
				}
			}
			if (results.size() != 0) {
				if (type.equals("0") || type == "0") {// 和
					List<Integer> list = new ArrayList<Integer>();
					for (SolrDocument doc : results) {
						Integer fileId = Integer.parseInt(doc.getFieldValue(
								"id").toString());
						System.out.println("文件Id:" + fileId);
						SVDS_Files fileM = filesService.getFilesById(fileId);
						System.out.println(fileM);
						List<String> hlRows = highlighting.get(doc.get("id"))
								.get("name");
						if (hlRows != null && hlRows.size() != 0) {
							fileM.setFileName(hlRows.get(0));
						}
						List<String> hlTitleRows = highlighting.get(
								doc.get("id")).get("attr_content");
						if (hlTitleRows != null && hlTitleRows.size() != 0) {
							try {
								fileM.setAbstracts(hlTitleRows.get(0));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								// e.printStackTrace();
							}
						}
						System.out.println(fileM);
						if (fileM != null) {
							List<SVDS_RoleMenu> rolemenus = roleMenuService
									.listByMenuId(fileM.getMenu().getId());
							for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
								if (svds_RoleMenu.getRoleId() == loginUser
										.getRole().getRoleId()) {
									filesService.updateFiles(fileM);
									list.add(fileId);
								}
							}
						}
					}
					fileIds.retainAll(list);
				} else {
					for (SolrDocument doc : results) {
						Integer fileId = Integer.parseInt(doc.getFieldValue(
								"id").toString());
						SVDS_Files fileM = filesService.getFilesById(fileId);
						System.out.println(fileId);
						System.out.println(highlighting.get(doc.get("id")));
						List<String> hlTitleRows = highlighting.get(
								doc.get("id")).get("attr_content");
						System.out.println(hlTitleRows.toString());
						if (hlTitleRows != null && hlTitleRows.size() != 0) {
							try {
								fileM.setAbstracts(hlTitleRows.get(0));
							} catch (Exception e) {

							}
						}
						if (fileM != null) {
							List<SVDS_RoleMenu> rolemenus = roleMenuService
									.listByMenuId(fileM.getMenu().getId());
							for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
								if (svds_RoleMenu.getRoleId() == loginUser
										.getRole().getRoleId()) {
									filesService.updateFiles(fileM);
									fileIds.add(fileId);
								}
							}
						}
					}
				}
			}
		}
		System.out.println(fileIds);
		if (fileIds.size() != 0) {
			files = filesService.getFilesByIds(fileIds);
			pageInfo = filesService.listFilesByIdsSort(pageNumber, pageSize,
					sortOrder, fileIds, loginUser.getUserId());
			request.getSession().removeAttribute("fileIds");
			request.getSession().setAttribute("fileIds", fileIds);
		}
		if (pageInfo != null) {
			int total = files.size();
			JSONObject result = new JSONObject();
			result.put("rows", pageInfo);
			result.put("total", total);
			return result.toString();
		} else {
			return null;
		}

	}

	/**
	 * 二次查询分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/page2Search", method = RequestMethod.POST)
	@ResponseBody
	@SuppressWarnings("unchecked")
	public String select2WhitFilesSql(@RequestParam int pageNumber,
			String sortOrder, int pageSize, String inputall,
			HttpServletRequest request) {
		List<Integer> fileIdSession = (List<Integer>) request.getSession()
				.getAttribute("fileIds");
		List<SVDS_Files> files = new ArrayList<SVDS_Files>();
		List<Integer> fileIds = new ArrayList<Integer>();
		List<SVDS_Files> pageInfo = null;
		// Map<String, Object> param = new HashMap<String, Object>();
		List<SearchBean> searchBean = new ArrayList<SearchBean>();
		JSONArray json = JSONArray.fromObject(inputall);
		SVDS_Files file = new SVDS_Files();
		List<SVDS_AliasFile> aliasFiles = new ArrayList<SVDS_AliasFile>();
		String type = "";
		QueryResponse response;
		SolrDocumentList results = new SolrDocumentList();
		Map<String, Map<String, List<String>>> highlighting = new HashMap<String, Map<String, List<String>>>();
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i);
				if (job.get("searchName").toString().equals("fileName")) {
					file.setFileName(job.get("searchVal").toString());
					System.out.println("文件名称");
					// param.put(job.get("searchName").toString(),job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(job.get("searchVal").toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				} else if (job.get("searchName").toString().equals("fileDate")) {
					file.setFileDate(job.get("searchVal").toString());
					System.out.println("日期");
					// param.put(job.get("searchName").toString(),job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(job.get("searchVal").toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				} else if (job.get("searchName").toString().equals("format")) {
					file.setFormat(job.get("searchVal").toString());
					System.out.println("格式");
					// param.put(job.get("searchName").toString(),job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(job.get("searchVal").toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				} else if (job.get("searchName").toString()
						.equals("securityId")) {
					SVDS_Security security = securityService
							.getSecurityByName(job.get("searchVal").toString());
					file.setSecurity(security);
					System.out.println("密级");
					// param.put(job.get("searchName").toString(),job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(security.getSecurityId().toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				} else if (job.get("searchName").toString().equals("item")) {
					System.out.println("项目名");
					System.out.println(job.get("condSelect").toString());
					System.out.println(job.get("searchVal").toString());
					SVDS_Menu menu = menuService.getMenuByName(job.get(
							"searchVal").toString());
					file.setMenu(menu);
					// param.put(job.get("searchName").toString(),job.get("condSelect").toString());
					SearchBean bean = new SearchBean();
					bean.setName(job.get("searchName").toString());
					bean.setVal(menu.getId().toString());
					bean.setWith(job.get("condSelect").toString());
					searchBean.add(bean);
				}
			}
			/*
			 * if (searchBean.size()> 0) { files =
			 * filesService.selectWhitFilesSql(file, param); if (files.size() >
			 * 0) { for (int i = 0; i < files.size(); i++) { if
			 * (files.get(i).getMajor().getMajorId() == loginUser
			 * .getMajor().getMajorId()) {
			 * fileIds.add(files.get(i).getFileId()); } } } }
			 */
			if (searchBean.size() > 0) {
				List<SVDS_Files> oneFiles = filesService
						.selectFilesSql(searchBean);
				for (SVDS_Files svds_Files : oneFiles) {
					files.add(svds_Files);
				}
			}

			if (files.size() > 0) {
				for (int i = 0; i < files.size(); i++) {
					List<SVDS_RoleMenu> rolemenus = roleMenuService
							.listByMenuId(files.get(i).getMenu().getId());
					for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
						if (svds_RoleMenu.getRoleId() == loginUser.getRole()
								.getRoleId()) {
							fileIds.add(files.get(i).getFileId());
						}
					}
				}
			}

			// 标签
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i);
				if (job.get("searchName").toString().equals("alias")) {
					// SVDS_Alias alias =
					// aliasService.getByName(job.get("searchVal").toString());
					// if (alias != null) {
					//
					System.out.println("标签");
					// }
					List<SVDS_AliasFile> afs = aliasFileService
							.listAliasFileById(Integer.parseInt(job.get(
									"searchVal").toString()));

					for (SVDS_AliasFile svds_AliasFile : afs) {
						aliasFiles.add(svds_AliasFile);
					}
					if (i != 0) {// 标签查询不是第一个
						job = json.getJSONObject(i - 1);
						type = job.get("condSelect").toString();
					} else {
						type = job.get("condSelect").toString();
					}
				}
			}
			if (aliasFiles.size() != 0) {
				if (type.equals("0") || type == "0") {// 和
					System.out.println("文件id数量：" + fileIds.size());
					if (fileIds.size() != 0) {
						List<Integer> list = new ArrayList<Integer>();
						for (int i = 0; i < aliasFiles.size(); i++) {
							Integer fileId = aliasFiles.get(i).getFiles()
									.getFileId();
							SVDS_Files fileM = filesService
									.getFilesById(fileId);
							List<SVDS_RoleMenu> rolemenus = roleMenuService
									.listByMenuId(fileM.getMenu().getId());
							for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
								if (svds_RoleMenu.getRoleId() == loginUser
										.getRole().getRoleId()) {
									list.add(fileId);
								}
							}
						}
						fileIds.retainAll(list);
					} else {
						System.out.println("标签查询");
						aliasFiles = filtrateAliasFiles(aliasFiles);
						for (int i = 0; i < aliasFiles.size(); i++) {
							Integer fileId = aliasFiles.get(i).getFiles()
									.getFileId();
							SVDS_Files fileM = filesService
									.getFilesById(fileId);
							List<SVDS_RoleMenu> rolemenus = roleMenuService
									.listByMenuId(fileM.getMenu().getId());
							for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
								if (svds_RoleMenu.getRoleId() == loginUser
										.getRole().getRoleId()) {
									fileIds.add(fileId);
								}
							}
						}
					}
				} else {
					for (int i = 0; i < aliasFiles.size(); i++) {
						Integer fileId = aliasFiles.get(i).getFiles()
								.getFileId();
						SVDS_Files fileM = filesService.getFilesById(fileId);
						List<SVDS_RoleMenu> rolemenus = roleMenuService
								.listByMenuId(fileM.getMenu().getId());
						for (SVDS_RoleMenu svds_RoleMenu : rolemenus) {
							if (svds_RoleMenu.getRoleId() == loginUser
									.getRole().getRoleId()) {
								fileIds.add(fileId);
							}
						}
					}
				}
			}

			/*
			 * for (int i = 0; i < json.size(); i++) { JSONObject job =
			 * json.getJSONObject(i); if
			 * (job.get("searchName").toString().equals("alias")) {
			 * System.out.println("结果中标签查询");
			 * System.out.println(job.get("searchVal").toString());
			 * List<SVDS_AliasFile> afs = aliasFileService
			 * .listAliasFileById(Integer.parseInt(job.get(
			 * "searchVal").toString())); for (SVDS_AliasFile svds_AliasFile :
			 * afs) { aliasFiles.add(svds_AliasFile); } if (i != 0) {//
			 * 标签查询不是第一个 job = json.getJSONObject(i - 1); type =
			 * job.get("condSelect").toString(); } else { type =
			 * job.get("condSelect").toString(); } } } if (aliasFiles.size() !=
			 * 0) { if (type.equals("0") || type == "0") {// 和 if
			 * (fileIds.size() != 0) { List<Integer> list = new
			 * ArrayList<Integer>(); for (int i = 0; i < aliasFiles.size(); i++)
			 * { Integer fileId = aliasFiles.get(i).getFiles() .getFileId();
			 * SVDS_Files fileM = filesService .getFilesById(fileId); // if
			 * (fileM.getMajor().getMajorId() ==
			 * loginUser.getMajor().getMajorId()) { // fileIds.add(fileId); // }
			 * } fileIds.retainAll(list); } } else { for (int i = 0; i <
			 * aliasFiles.size(); i++) { Integer fileId =
			 * aliasFiles.get(i).getFiles() .getFileId(); SVDS_Files fileM =
			 * filesService.getFilesById(fileId); // if
			 * (fileM.getMajor().getMajorId() ==
			 * loginUser.getMajor().getMajorId()) { // fileIds.add(fileId); // }
			 * } } }
			 */
		}
		if (fileIds.size() != 0) {
			// System.out.println(fileIdSession.toString());
			if (fileIdSession.size() != 0) {
				fileIds.retainAll(fileIdSession);
			}
			files = filesService.getFilesByIds(fileIds);
			pageInfo = filesService.selectWhitFilesSql(pageNumber, pageSize,
					sortOrder, fileIds, loginUser.getUserId());
			request.getSession().removeAttribute("fileIds");
			request.getSession().setAttribute("fileIds", fileIds);
		}
		if (pageInfo != null && pageInfo.size() > 0) {
			int total = files.size();
			JSONObject result = new JSONObject();
			result.put("rows", pageInfo);
			result.put("total", total);
			return result.toString();
		} else {
			return null;
		}

	}

	/**
	 * 判断文件是否拥有权限
	 * 
	 * @param tabsId
	 * @param menuId
	 * @param request
	 * @param fileIds
	 * @return
	 */
	@RequestMapping(value = "/isFileRole", method = RequestMethod.POST)
	@ResponseBody
	public String isFileRole(
			Integer tabsId,
			Integer menuId,
			HttpServletRequest request,
			@RequestParam(value = "fileIds[]", required = false, defaultValue = "") Integer[] fileIds) {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		Integer menuIdAf = 0;
		Integer btnIdcont = 0;
		List<Integer> menuIds = new ArrayList<Integer>();
		List<Integer> btnIds = new ArrayList<Integer>();
		if (tabsId != null) {
			menuIdAf = tabsDao.getTabsById(tabsId).getMenuid();
		} else if (menuId != null && tabsId == null) {
			menuIdAf = menuId;
		} else {
			List<Integer> ids = Arrays.asList(fileIds);
			List<SVDS_Files> fileList = filesService.getFilesByIds(ids);
			if (fileList != null && fileList.size() > 0) {
				for (int i = 0; i < fileList.size(); i++) {
					menuIds.add(fileList.get(i).getMenu().getId());
				}
			}
		}
		System.out.println("菜单Id：" + menuIds);
		if (menuIds.size() > 0) {
			for (int i = 0; i < menuIds.size(); i++) {
				List<Integer> menuBtnIds = roleButtonService
						.listRoleButtonByUserIdMenuId(loginUser.getUserId(),
								menuIds.get(i));
				System.out.println(menuBtnIds);
				if (menuBtnIds != null && menuBtnIds.size() > 0) {
					for (int j = 0; j < menuBtnIds.size(); j++) {
						btnIds.add(menuBtnIds.get(j));
					}
				}
			}
		} else {
			btnIds = roleButtonService.listRoleButtonByUserIdMenuId(
					loginUser.getUserId(), menuIdAf);
		}
		// System.out.println(btnIds);
		if (btnIds != null && btnIds.size() > 0) {
			List<SVDS_Button> btns = buttonService.getUserButton(btnIds);
			for (int i = 0; i < btns.size(); i++) {
				System.out.println(btns.get(i).getBtnName());
				if (btns.get(i).getBtnName().equals("下载")) {
					btnIdcont++;
				}
			}
		}
		if (menuIds.size() == btnIdcont) {
			return "ok";
		} else {
			return "no";
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param fileId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public void downloadFile(Integer fileId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("下载单个文件Id：" + fileId);
		SVDS_Files files = filesService.getFilesById(fileId);
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("application/octet-stream");
		FileInputStream fis = null;
		try {
			File file = new File(files.getFileUrl());
			fis = new FileInputStream(file);
			String fileName = file.getName();
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
			IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();
			logService.insertLog(new SVDS_Log("执行了下载文件操作", DateUtils.getDate(),
					loginUser, files.getMenu(), files,IpAddressUtils.getIpAddress(request),"成功"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	List<SVDS_Files> fileTabs = new ArrayList<SVDS_Files>();

	/**
	 * 获取该选项卡子级里的文件
	 * 
	 * @param tabs
	 * @return
	 */
	public List<SVDS_Files> getChildren(List<Tabs> tabs) {
		for (int i = 0; i < tabs.size(); i++) {
			Tabs tab = tabsDao.getTabsById(tabs.get(i).getId());
			System.out.println("选项卡Id：" + tab.getId());
			if (tab.getChildren().size() > 0) {
				getChildren(tab.getChildren());
			} else {
				List<SVDS_Files> filesTab = filesService.listFilesByTabsId(tab
						.getId());
				for (SVDS_Files svds_Files : filesTab) {
					fileTabs.add(svds_Files);
				}
			}
		}
		System.out.println(fileTabs.size());
		return fileTabs;
	}

	/**
	 * 批量下载文件
	 * 
	 * @param fileId
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/downloadFiles")
	public void downloadFiles(Integer tabsId, Integer type, Integer menuId,
			HttpServletRequest request, HttpServletResponse response,
			Integer[] fileIds) throws Exception {
		List<SVDS_Files> fileList = new ArrayList<SVDS_Files>();
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		String fileName = "";
		System.out.println("tabsId:" + tabsId);
		if (tabsId != null) {
			Tabs tabs = tabsDao.getTabsById(tabsId);
			fileName = tabs.getName();
			System.out.println("选项卡下载");
			System.out.println(tabs.getChildren().size());
			if (tabs.getChildren().size() > 0) {
				fileList = getChildren(tabs.getChildren());
			} else {
				fileList = filesService.listFilesByTabsId(tabsId);
			}
			String userAgent = request.getHeader("user-agent").toLowerCase();
			if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
				// win10 ie edge 浏览器 和其他系统的ie
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				// fe
				fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
			}
			// response.setContentType("APPLICATION/OCTET-STREAM");
			System.out.println(fileName);
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName + ".zip");
			try {
				ZipOutputStream zipOutputStream = new ZipOutputStream(
						response.getOutputStream());

				// ZipOutputStream zipOutputStream = new ZipOutputStream(
				// response.getOutputStream());
				// // zipOutputStream.setEncoding(ZipUtils.CHAR_SET);

				if (tabs.getChildren().size() > 0) {
					getChildrenName(tabs.getChildren(), request, response,
							zipOutputStream, tabsId, loginUser);
				} else {
					List<File> files = new ArrayList<File>();
					for (SVDS_Files svds_file : fileList) {
						files.add(new File(svds_file.getFileUrl()));
						logService.insertLog(new SVDS_Log("执行了选项卡下载文件操作",
								DateUtils.getDate(), loginUser, svds_file
										.getMenu(), svds_file,IpAddressUtils.getIpAddress(request),"成功"));
					}
					if (CollectionUtils.isEmpty(files) == false) {
						for (int i = 0, size = files.size(); i < size; i++) {
							ZipUtils.compress(files.get(i), zipOutputStream,
									ZipUtils.BASE_DIR);
						}
					}
				}
				// 冲刷输出流
				zipOutputStream.flush();
				// 关闭输出流
				zipOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (menuId != null && tabsId == null) {
			System.out.println("项目下载");
			SVDS_Menu menu = menuService.getMenuById(menuId);
			fileName = menu.getText();
			tabsDao.listTabsByMenuId(menuId);
			List<Tabs> tabList = tabsDao.listTabsByMenuId(menuId);
			String userAgent = request.getHeader("user-agent").toLowerCase();
			try {
				if (userAgent.contains("msie")
						|| userAgent.contains("like gecko")) {
					fileName = URLEncoder.encode(fileName, "UTF-8");
				} else {
					fileName = new String(fileName.getBytes("utf-8"),
							"iso-8859-1");
				}

				System.out.println(fileName);
				response.setCharacterEncoding("utf-8");
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition",
						"attachment; filename=" + fileName + ".zip");
				ZipOutputStream out = new ZipOutputStream(
						response.getOutputStream());
				for (Tabs tabs : tabList) {
					List<String> str = new ArrayList<String>();
					List<String> content = getLocation(tabs, str, 0);
					String base = "";
					for (int i = content.size() - 1; i >= 0; i--) {
						base += content.get(i) + "/";
					}
					fileList = filesService.listFilesByTabIdMenuId(
							tabs.getId(), menuId);
					List<File> files = new ArrayList<File>();
					for (SVDS_Files svds_file : fileList) {
						files.add(new File(svds_file.getFileUrl()));
						logService.insertLog(new SVDS_Log("执行了项目文件下载操作",
								DateUtils.getDate(), loginUser, svds_file
										.getMenu(), svds_file,IpAddressUtils.getIpAddress(request),"成功"));
					}
					ZipUtils.compressList(out, files, base);
				}
				// 冲刷输出流
				out.flush();
				// 关闭输出流
				out.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("文件id批量下载");
			List<Integer> ids = Arrays.asList(fileIds);
			fileList = filesService.getFilesByIds(ids);
			fileName = DateUtils.getDate();
			System.out.println(fileName);
			// response.setContentType("APPLICATION/OCTET-STREAM");
			System.out.println(fileName);
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName + ".zip");
			try {
				List<File> files = new ArrayList<File>();
				for (SVDS_Files svds_file : fileList) {
					files.add(new File(svds_file.getFileUrl()));
					logService.insertLog(new SVDS_Log("执行了下载文件操作", DateUtils
							.getDate(), loginUser, svds_file.getMenu(),
							svds_file,IpAddressUtils.getIpAddress(request),"成功"));
				}
				ZipOutputStream zipOutputStream = new ZipOutputStream(
						response.getOutputStream());
				// zipOutputStream.setEncoding(ZipUtils.CHAR_SET);
				if (CollectionUtils.isEmpty(files) == false) {
					for (int i = 0, size = files.size(); i < size; i++) {
						ZipUtils.compress(files.get(i), zipOutputStream,
								ZipUtils.BASE_DIR);
					}
				}
				// 冲刷输出流
				zipOutputStream.flush();
				// 关闭输出流
				zipOutputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 选项卡下载文件处理
	 * 
	 * @param tabs
	 *            子级集合
	 * @param request
	 *            请求
	 * @param response
	 *            请求对象
	 * @param out
	 *            zip下载对象
	 * @param parentId
	 *            下载的选项卡Id
	 * @param loginUser
	 *            登录用户
	 * @throws Exception
	 */
	public void getChildrenName(List<Tabs> tabs, HttpServletRequest request,
			HttpServletResponse response, ZipOutputStream out,
			Integer parentId, SVDS_User loginUser) throws Exception {
		for (int i = 0; i < tabs.size(); i++) {
			System.out.println("tabsId：" + tabs.get(i).getId());
			Tabs tab = tabsDao.getTabsById(tabs.get(i).getId());
			if (tab != null) {
				System.out.println("tabs子级数量：" + tab.getChildren().size());
				if (tab.getChildren().size() > 0) {
					System.out.println(123);
					getChildrenName(tab.getChildren(), request, response, out,
							parentId, loginUser);
				} else {
					System.out.println(tab.getName());
					System.out.println(tab.getChildren());
					List<String> str = new ArrayList<String>();
					String base = "";
					List<String> content = getLocation(tab, str, parentId);
					if (content != null) {
						for (int j = content.size() - 1; j >= 0; j--) {
							base += content.get(j) + "/";
						}
					}
					List<SVDS_Files> fileList = filesService
							.listFilesByTabIdMenuId(tab.getId(),
									tab.getMenuid());
					List<File> files = new ArrayList<File>();
					for (SVDS_Files svds_file : fileList) {
						files.add(new File(svds_file.getFileUrl()));
						logService.insertLog(new SVDS_Log("执行了选项卡下载文件操作",
								DateUtils.getDate(), loginUser, svds_file
										.getMenu(), svds_file,IpAddressUtils.getIpAddress(request),"成功"));
					}
					ZipUtils.compressList(out, files, base);
				}
			}
		}
	}

	@RequestMapping(value = "/ceshi", method = RequestMethod.GET)
	// @ResponseBody
	public void ceshi(HttpServletRequest request, HttpServletResponse response) {
		Tabs tabs = tabsDao.getTabsById(6);
		String fileName = tabs.getName();
		String userAgent = request.getHeader("user-agent").toLowerCase();
		try {
			if (userAgent.contains("msie") || userAgent.contains("like gecko")) {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			} else {
				fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
			}

			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ fileName + ".zip");
			ZipOutputStream out = new ZipOutputStream(
					response.getOutputStream());
			SVDS_User user = new SVDS_User();
			if (tabs.getChildren().size() > 0) {
				getChildrenName(tabs.getChildren(), request, response, out, 6,
						user);
			}
			// 冲刷输出流
			out.flush();
			// 关闭输出流
			out.close();
			// ZipOutputStream out=new
			// ZipOutputStream(response.getOutputStream());
			// System.out.println();
			// List<SVDS_Files> fileList
			// =filesService.listFilesByTabIdMenuId(tabs.getId(),64);
			// List<File> files = new ArrayList<File>();
			// for (SVDS_Files svds_file : fileList) {
			// files.add(new File(svds_file.getFileUrl()));
			// // logService.insertLog(new SVDS_Log("执行了下载文件操作", DateUtils
			// // .getDate(), loginUser, svds_file.getMenu(),
			// // svds_file));
			// }
			// ZipUtils.compressList(out,files,base);

			// for (Tabs tabs : tabList) {
			// List<String> str=new ArrayList<String>();
			// List<String> content=getLocation(tabs,str);
			// String base="";
			// for(int i=content.size()-1;i>=0;i--){
			// base+=content.get(i)+"/";
			// }
			// List<SVDS_Files> fileList
			// =filesService.listFilesByTabIdMenuId(tabs.getId(),64);
			// List<File> files = new ArrayList<File>();
			// for (SVDS_Files svds_file : fileList) {
			// files.add(new File(svds_file.getFileUrl()));
			// // logService.insertLog(new SVDS_Log("执行了下载文件操作", DateUtils
			// // .getDate(), loginUser, svds_file.getMenu(),
			// // svds_file));
			// }
			// ZipUtils.compressList(out,files,base);
			// }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getLocation(Tabs tab, List<String> str, Integer parentId) {
		str.add(tab.getName());
		if (tab.getParentid() != parentId) {
			Tabs t = tabsDao.getTabsByIdAll(tab.getParentid());
			if (t != null) {
				getLocation(t, str, parentId);
			}
		}
		return str;
	}

	/**
	 * 打开Excel文件
	 * 
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String showExcel(HttpServletRequest request, ModelMap map) {
		String fileId = request.getParameter("fileId");
		SVDS_Files files = filesService.getFilesById(Integer.parseInt(fileId));
		map.put("fileUrl", files.getFileUrl());
		return "html/list";
	}

	@RequestMapping(value = "/newlist", method = RequestMethod.GET)
	public String showNewExcel(HttpServletRequest request, ModelMap map) {
		String fileId = request.getParameter("fileId");
		SVDS_Files files = filesService.getFilesById(Integer.parseInt(fileId));
		map.put("fileUrl", files.getFileUrl());
		return "html/newlist";
	}

	/**
	 * 存储对比生成图表的数据
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/graphing", method = RequestMethod.POST)
	@ResponseBody
	public String showGraphing(HttpServletRequest request, HttpSession session) {
		String contentHead = request.getParameter("contentHead");
		String con = request.getParameter("content");
		// session.setAttribute("contentHead", contentHead);
		session.setAttribute("con", con);
		return "ok";
	}

	/**
	 * 获取对比生成图表的数据
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/graphingData", method = RequestMethod.POST)
	@ResponseBody
	public String dataGraphing(HttpServletRequest request, HttpSession session) {
		// String contentHead =
		// request.getSession().getAttribute("contentHead").toString();
		String content = request.getSession().getAttribute("con").toString();
		JSONObject result = new JSONObject();
		// result.put("contentHead", contentHead);
		result.put("content", content);
		return result.toString();
	}

	/**
	 * 点击文件预览excel文件
	 */
	List<List<String[]>> data = null;
	int next = 1;

	@RequestMapping(value = "/readExecl", method = RequestMethod.POST)
	@ResponseBody
	public String readExecl(String fileUrl) throws Exception,
			OpenXML4JException, SAXException {
		data = new ArrayList<>();
		List<String> columns = new ArrayList<String>();
		JSONObject jsonObject = new JSONObject();
		if (fileUrl.substring(fileUrl.length() - 3).equals("xls")) {
			Workbook readwb = null;
			try {

				// String files = "F:\\sss.xls";
				InputStream instream = new FileInputStream(fileUrl);
				readwb = Workbook.getWorkbook(instream);
				// 获取Sheet表个数
				int num = readwb.getNumberOfSheets();
				for (int i = 0; i < num; i++) {
					List<String[]> templis = new ArrayList<>();
					System.out.println("第" + (i + 1) + "张表");
					Sheet readsheet = readwb.getSheet(i);
					String sheetName = readsheet.getName();// 获取Sheet名称
					// 获取Sheet表中所包含的总列数
					int rsColumns = readsheet.getColumns();
					// 获取Sheet表中所包含的总行数
					int rsRows = readsheet.getRows();
					for (int m = 0; m < rsRows; m++) {
						templis.add(new String[rsRows]);
						for (int n = 0; n < rsColumns; n++) {
							templis.get(m)[n] = readsheet.getCell(n, m)
									.getContents();
							Cell cell = readsheet.getCell(n, m);
							// String cellinfo = readsheet.getCell(j,
							// i).getContents();
							System.out.print(cell.getContents() + " ");
						}
						System.out.println("");
					}
					data.add(templis);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				readwb.close();
			}

		} else if (fileUrl.substring(fileUrl.length() - 4).equals("xlsx")) {
			data.add(new ArrayList<String[]>());
			ExUtils reader = new ExUtils() {
				public void getRows(int sheetIndex, int curRow,
						List<String> rowList) {
					if (sheetIndex != next) {
						next = sheetIndex;
						data.add(new ArrayList<String[]>());
					}
					String[] row = new String[rowList.size()];
					for (int i = 0; i < rowList.size(); i++) {
						row[i] = rowList.get(i);
					}
					data.get(next - 1).add(row);
				}
			};
			reader.process(fileUrl);
			try {
				for (int i = 0; i < data.get(0).get(0).length; i++) {
					columns.add(data.get(0).get(0)[i]);
				}
			} catch (Exception e) {
				// e.printStackTrace();
				JSONArray jso = new JSONArray();
				jsonObject.put("columns", "内容为空");
				jsonObject.put("formdata", "内容为空");
				jso.add(jsonObject);
				return jso.toString();
			}
		} else {
			return null;

		}
		// System.out.println(data.remove(0));
		JSONArray jso = new JSONArray();
		System.out.println("----------------------------------------");
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).size(); j++) {
				// System.out.println(Arrays.toString(data.get(i).get(j)));
			}
			if (data.get(i).size() > 0) {
				jsonObject.put("columns", " ");
				jsonObject.put("formdata", data.get(i));
			}

			jso.add(jsonObject);
		}

		System.out.println(jso.toString());

		return jso.toString();
	}

	/**
	 * 根据ID修改文件
	 * 
	 * @param Files
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateFiles", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateFiles(SVDS_Files Files, HttpServletRequest request) throws UnknownHostException {
		if (filesService.updateFiles(Files) > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("修改文件信息操作", DateUtils.getDate(),
					loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return filesService.updateFiles(Files);
		}
		return null;
	}

	/**
	 * 修改备注字段
	 * 
	 * @param Files
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateRemark", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateRemark(Integer fileId, String remark,
			HttpServletRequest request) throws UnknownHostException {
		Integer isSuccess = filesService.updateRemark(remark, fileId);
		SVDS_Files file = filesService.getFilesById(fileId);
		if (isSuccess > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("修改备注字段操作", DateUtils.getDate(),
					loginUser, file.getMenu(), file,IpAddressUtils.getIpAddress(request),"成功"));
			return isSuccess;
		}
		return null;
	}

	/**
	 * 文件移动修改文件信息
	 * 
	 * @param Files
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateFilesId", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateFilesId(
			Integer tabsId,
			Integer menuId,
			@RequestParam(value = "fileIds[]", required = false, defaultValue = "") Integer[] fileIds,
			HttpServletRequest request) throws UnknownHostException {
		Integer count = 0;
		List<Integer> ids = Arrays.asList(fileIds);
		List<SVDS_Files> files = filesService.getFilesByIds(ids);
		SVDS_Menu menu = menuService.getMenuById(menuId);
		for (SVDS_Files svds_Files : files) {
			svds_Files.setTabsId(tabsId);
			svds_Files.setMenu(menu);
			Integer isSuccess = filesService.updateFiles(svds_Files);
			if (isSuccess > 0) {
				SVDS_User loginUser = sessionService.getSessionByIp(request);
				logService.insertLog(new SVDS_Log("移动文件操作",
						DateUtils.getDate(), loginUser, svds_Files.getMenu(),
						svds_Files,IpAddressUtils.getIpAddress(request),"成功"));
				count = count + 1;
			}
		}
		if (count == ids.size()) {
			return 1;
		} else {
			return null;
		}
	}

	/**
	 * 查询全部文件格式
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listFormatAll", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray listFormatAll() {
		List<String> formats = filesService.listFormat();
		if (formats.size() != 0) {
			JSONArray data = JSONArray.fromObject(formats);
			return data;
		} else {
			return null;
		}
	}

	/**
	 * 根据密级ID查询文件
	 * 
	 * @param securityId
	 * @return
	 */
	@RequestMapping(value = "/listBySecurityId", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_Files> listBySecurityId(Integer securityId,
			HttpServletRequest request) {
		String tabsId = request.getSession().getAttribute("tabsId").toString();
		List<SVDS_Files> files = filesService.listBySecurityId(securityId,
				Integer.parseInt(tabsId));
		return files;
	}

	/**
	 * 根据ID删除文件
	 * 
	 * @param FilesIds
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/recycleFiles", method = RequestMethod.POST)
	@ResponseBody
	public Integer deleteFiles(
			@RequestParam(value = "fileIds[]", required = false, defaultValue = "") Integer[] fileIds,
			HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		List<Integer> ids = Arrays.asList(fileIds);
		Integer isSuccess = filesService.recycleFiles(ids, 1, loginUser);
		if (isSuccess > 0) {
			for (int i = 0; i < ids.size(); i++) {
				SVDS_Files files = filesService.getFilesById(ids.get(i));
				logService
						.insertLog(new SVDS_Log("删除文件操作", DateUtils.getDate(),
								loginUser, files.getMenu(), files,IpAddressUtils.getIpAddress(request),"成功"));
			}
			return isSuccess;
		}
		return null;
	}

}
