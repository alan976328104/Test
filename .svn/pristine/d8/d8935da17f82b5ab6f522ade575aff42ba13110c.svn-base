package ac.drsi.nestor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSONException;

import ac.drsi.common.Excleparser;
import ac.drsi.common.Excleparser.ParseException;
import ac.drsi.common.ExUtils;
import ac.drsi.common.Excleparser1;
import ac.drsi.common.ReadCsvUtils;
import ac.drsi.common.StringUtil;
import ac.drsi.nestor.dao.OpenCSVDao;
import ac.drsi.nestor.dao.RelationExportDao;
import ac.drsi.nestor.dao.SVDS_FilesDao;
import ac.drsi.nestor.entity.Export;
import ac.drsi.nestor.entity.OpenCSV;
import ac.drsi.nestor.entity.Phenomenon;
import ac.drsi.nestor.entity.RelationTemp;
import ac.drsi.nestor.entity.ResultBean;
import ac.drsi.nestor.entity.Rule;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.ExportExcelService;
import ac.drsi.nestor.service.SVDS_SessionService;

/**
 * 2019年3月15日 曹泽凯 用于关联检索处理
 * 
 * @author CZK
 * 
 */
@RestController
public class RelationExportController {

	@Autowired
	private ExportExcelService studentExportService;
	@Autowired
	RelationExportDao rdao;

	@Autowired
	SVDS_FilesDao dao;
	@Autowired
	OpenCSVDao csvDao;

	@Autowired
	SVDS_SessionService sessionService;

	@Autowired
	RelationController controller;

	/**
	 * 2019年3月15日 曹泽凯 
	 * 获得该文件那列为关联检索的列
	 * @param fileid 文件id
	 * @return
	 */
	@RequestMapping(value = "/getCellByRelation", method = RequestMethod.GET)
	public Integer getCellByRelation(String fileid) {
		try {
			System.out.println("fileid" + fileid);
			String menuid = rdao.findfilemidbyfid(Integer.parseInt(fileid))
					+ "";// 通过文件id获得菜单id
			System.out.println(menuid);
			System.out.println(fileid);
			Integer i = Integer.parseInt(rdao.findallrelation(menuid,
					"%" + fileid + "%").split("\\$")[0]);// 将存入数据库的关联检索内容解析出那一列
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 2019年3月15日 曹泽凯 
	 * 用于打开CSV文件
	 * @param fileid 文件id
	 * @param session http的session存储
	 * @param request http的请求
	 */
	List<ArrayList<String[]>> dlist;

	@RequestMapping(value = "/getRelationExportcsv", method = RequestMethod.POST)
	public ResultBean openCsv(Integer fileid, HttpSession session,
			HttpServletRequest request) throws Exception {

		SVDS_User user = sessionService.getSessionByIp(request);
		// System.out.println("page:"+page+"rows"+rows);
		System.out.println("fileid" + fileid);
		if (csvDao.isnull(user.getUserId()) == 0) {
			csvDao.insertCSVtemp(user.getUserId(), 0, 1);
		}
		SVDS_Files file = dao.getFilesById(fileid);
		// file.getFileUrl().substring(file.getFileUrl().lastIndexOf(".")).equals(".xlsx")

		if (file.getFileUrl().substring(file.getFileUrl().lastIndexOf("."))
				.equals(".xlsx")
				|| file.getFileUrl()
						.substring(file.getFileUrl().lastIndexOf("."))
						.equals(".xls")) {
			File tempFile = new File(file.getFileUrl());
			// 传入一个路径产生流再将流传入工具类，返回解析对象，Excel的所有数据就被解析到List<String[]>
			// 里面，遍历list任由你处置。
			FileInputStream inputStream = new FileInputStream(tempFile);
			Excleparser parse = new Excleparser().parse(inputStream, 0, 100);
			List<OpenCSV> datas = parse.getDatas();
			System.out.println(user.getUserId());
			System.out.println(file.getFileUrl());
			System.out.println(csvDao.isnull(user.getUserId()));
			// System.out.println(csvDao.getchars(user.getUserId(),page));

			/*
			 * dlist = new ArrayList<>(); dlist.add(new ArrayList<String[]>());
			 * ExUtils reader = new ExUtils() { public void getRows(int
			 * sheetIndex, int curRow, List<String> rowList) { if (sheetIndex !=
			 * next) { next = sheetIndex; dlist.add(new ArrayList<String[]>());
			 * } String[] row = new String[rowList.size()]; for (int i = 0; i <
			 * rowList.size(); i++) { row[i] = rowList.get(i); } dlist.get(next
			 * - 1).add(row); } }; reader.process(file.getFileUrl());
			 */

			return new ResultBean(100, datas);
		} else {
			System.out.println(user.getUserId());
			System.out.println(file.getFileUrl());
			System.out.println(csvDao.isnull(user.getUserId()));
			// System.out.println(csvDao.getchars(user.getUserId(),page));
			return new ResultBean(100, readANDwrite(file.getFileUrl(), 100));
		}

	}

	/**
	 * 2019年3月13日 曹泽凯 
	 * 读取Excle文件
	 * @param fileName 文件路径
	 * @param size 读取多少条
	 * @return
	 */
	public List<OpenCSV> readANDwrite(String fileName, Integer size) {

		// 大集合，以sessionid为键，以一次session的所有访问记录list为值进行存储
		List<OpenCSV> list = new ArrayList<>();

		// 一次session的访问记录集合
		File file = new File(fileName);

		// java提供的一个可以分页读取文件的类,此类的实例支持对随机访问文件的读取和写入
		RandomAccessFile rf = null;

		String tempString = null;
		try {

			// 初始化RandomAccessFile，参数一个为文件路径，一个为权限设置，这点与Linux类似，r为读，w为写
			rf = new RandomAccessFile(fileName, "rw");

			// 设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作
			// rf.seek(chars);

			// 获取文件的行数
			// int fileSize = getTotalLines(file);
			for (int i = 0; i < size; i++) {// 从上一次读取结束时的文件行数到本次读取文件时的总行数中间的这个差数就是循环次数

				// 一行一行读取
				tempString = rf.readLine();
				// 文件中文乱码处理
				if (tempString != null) {
					System.out.println(StringUtil.getEncoding(tempString));

					// tempString = tempString.replaceAll("%(?![0-9a-fA-F]{2})",
					// "%25");
					// tempString = tempString.replaceAll("\\+", "%2B");
					// tempString = java.net.URLDecoder.decode(tempString,
					// "GB2312");
					// tempString= StringUtil.encodeUTF8(tempString);
					System.out.println(tempString);
					if (tempString.indexOf(",") > 0) {

						String str[] = tempString.split(",");
						// 将字符串JSON转换为实体JSON，以便能通过key取value
						list.add(new OpenCSV(str[0], str[1]));
					} else {
						list.add(new OpenCSV(tempString, ""));
					}
					// System.out.println(tempString);
				}
			}
			// 返回此文件中的当前偏移量。
			// chars = rf.getFilePointer();
			// if(page<csvDao.getMaxPage(user.getUserId())){

			// }else{
			// csvDao.insertCSVtemp(user.getUserId(), chars,page+1);
			// }

			// System.out.println(chars);
			// // size=fileSize;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException j) {

		} finally {
			if (rf != null) {
				try {
					rf.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

	// 获取文件的行数
	static int getTotalLines(String file) throws IOException {

		FileReader in = new FileReader(new File(file));
		LineNumberReader reader = new LineNumberReader(in);
		String s = reader.readLine();
		int lines = 0;
		while (s != null) {
			lines++;
			s = reader.readLine();
		}
		reader.close();
		in.close();
		return lines;
	}

	/**
	 * 2019年3月13日 曹泽凯 
	 * 将关联检索导出为Excel
	 * @param data 导出的数据
	 * @param response http的相应
	 * @param request http请求
	 */
	@RequestMapping(value = "/RelationExportExcle", method = RequestMethod.POST)
	public void RelationExportExcle(String data, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		// HSSFWorkbook wb =
		// studentExportService.export(getarr(data,fileid,begin,end,request));
		// response.setContentType("application/vnd.ms-excel");
		// response.setHeader("Content-disposition",
		// "attachment;filename=info.xls");
		// OutputStream ouputStream = response.getOutputStream();
		// wb.write(ouputStream);
		// ouputStream.flush();
		// ouputStream.close();
		List<String[]> rowList = new ArrayList<>();
		JSONArray arr = JSONArray.fromObject(data);
		for (int i = 0; i < arr.size(); i++) {
			JSONObject object = arr.getJSONObject(i);
			String[] strs = new String[object.size()];

			for (int j = 0; j < object.size(); j++) {
				strs[j] = object.getString("f" + j);
			}
			rowList.add(strs);
		}
		ExportExcelService.exportExcel(response, rowList, "info");
	}

	/**
	 * 2019年3月16日 曹泽凯 
	 * 用于关联数据处理选择那一列
	 * @param data 选择列的集合
	 * @param fileid 文件id
	 * @param begin 处理结果行数起始
	 * @param end 处理好结果行结束
	 * @param request http请求
	 * @param targer 引用
	 */
	@RequestMapping(value = "/RelationExportExcley", method = RequestMethod.GET)
	public List<String[]> getarr(String data, Integer fileid, Integer begin,
			Integer end, HttpServletRequest request, Integer targer)
			throws IOException, InvalidFormatException,
			ac.drsi.common.Excleparser1.ParseException {
		// 因大于1000太卡了 所以默认只展示1000行

		List<String[]> list = new ArrayList<>();
		System.out.println(data);
		JSONArray arr = JSONArray.fromObject(data);
		Integer index = 0;
		Integer size = end - begin;

		Integer maxCell = 0;
		for (int i = 0; i < arr.size(); i++) {
			Integer f1 = arr.getJSONObject(i).getInt("f1");
			Integer f2 = arr.getJSONObject(i).getInt("f2");
			if (f1 == 1) {
				maxCell++;
			}

			if (f2 == 1) {
				maxCell++;
			}
		}
		System.out.println("maxCell" + maxCell);
		for (int i = 0; i < arr.size(); i++) {
			Integer fileId = arr.getJSONObject(i).getInt("fileId");
			String name = arr.getJSONObject(i).getString("name");
			Integer f1 = arr.getJSONObject(i).getInt("f1");
			Integer f2 = arr.getJSONObject(i).getInt("f2");

			if (f1 == 0 && f2 == 0) {

			} else {
				if (f1 == 1 && f2 == 1) {
					// 全都选择了

					List<String[]> tlist = getthisdataByfileid(fileId, begin,
							end, request);
					if (list.size() < tlist.size()) {
						int sizex = tlist.size() - list.size();
						for (int j = 0; j < sizex; j++) {
							list.add(new String[maxCell]);
						}
					}
					for (int j = 0; j < tlist.size(); j++) {
						list.get(j)[index] = tlist.get(j)[0];
						list.get(j)[index + 1] = tlist.get(j)[1];
						// temp.get(j).getData().add(tlist.get(j)[0]);
						// temp.get(j).getData().add(tlist.get(j)[1]);
					}
					index++;
					index++;
				} else {
					if (f1 == 1) {
						// 只选择了f1
						List<String[]> tlist = getthisdataByfileid(fileId,
								begin, end, request);
						System.out.println(tlist.size());
						if (list.size() < tlist.size()) {
							int sizex = tlist.size() - list.size();
							for (int j = 0; j < sizex; j++) {
								list.add(new String[maxCell]);
							}
						}
						System.out.println(list.size());
						for (int j = 0; j < tlist.size(); j++) {
							list.get(j)[index] = tlist.get(j)[0];
						}
						index++;
					} else {
						// 只选择了f2
						List<String[]> tlist = getthisdataByfileid(fileId,
								begin, end, request);
						if (list.size() < tlist.size()) {
							int sizex = tlist.size() - list.size();
							for (int j = 0; j < sizex; j++) {
								list.add(new String[maxCell]);
							}
						}
						for (int j = 0; j < tlist.size(); j++) {
							list.get(j)[index] = tlist.get(j)[1];
						}
						index++;
					}

				}

			}

		}

		return list;
	}

	/**
	 * 2019年3月16日 曹泽凯 
	 * 根据文件id获得需要导出的文件
	 * @param fileId 文件id
	 * @param begin 起始页
	 * @param end 终止页
	 * @param request http的请求
	 */
	public List<String[]> getthisdataByfileid(Integer fileId, Integer begin,
			Integer end, HttpServletRequest request)
			throws InvalidFormatException, IOException,
			ac.drsi.common.Excleparser1.ParseException {

		SVDS_Files files = dao.getFilesById(fileId);
		if (files.getFileUrl().substring(files.getFileUrl().lastIndexOf("."))
				.equals(".xlsx")
				|| files.getFileUrl()
						.substring(files.getFileUrl().lastIndexOf("."))
						.equals(".xls")) {
			if (begin == 0 && end == 0) {
				end = Integer.MAX_VALUE;
			}
			File tempFile = new File(files.getFileUrl());
			// 传入一个路径产生流再将流传入工具类，返回解析对象，Excel的所有数据就被解析到List<String[]>
			// 里面，遍历list任由你处置。
			FileInputStream inputStream = new FileInputStream(tempFile);
			Excleparser1 parse = new Excleparser1().parse(inputStream, begin,
					end);
			// System.out.println(csvDao.getchars(user.getUserId(),page));
			return parse.getDatas();
		} else {
			if (begin == 0 & end == 0) {
				try {
					end = ReadCsvUtils.getTotalLines(new File(files
							.getFileUrl()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			List<String[]> list = ReadCsvUtils.readANDwriteforRelation(
					files.getFileUrl(), begin, end);
			return list;
		}

	}

	/**
	 * 2019年3月16日 曹泽凯 
	 * 插入一个临时关联请求
	 * @param name 请求名称
	 * @param data 请求内容
	 * @param columndata 请求列内容
	 * @return
	 */
	@RequestMapping(value = "/insertRelationtemp", method = RequestMethod.POST)
	public Integer insertRelationtemp(String name, String data,
			String columndata, HttpServletRequest request) {
		SVDS_User user = sessionService.getSessionByIp(request);
		return rdao
				.insertRelationtemp(data, name, columndata, user.getUserId());
	}

	/**
	 * 2019年3月16日 曹泽凯 查找一个临时关联检索
	 * @param request http请求
	 * @return
	 */
	@RequestMapping(value = "/findRelationtemp", method = RequestMethod.GET)
	public List<Phenomenon> findRelationtemp(HttpServletRequest request) {
		SVDS_User user = sessionService.getSessionByIp(request);
		return rdao.findRelationtemp(user.getUserId());
	}

	/**
	 * 2019年3月16日 曹泽凯 
	 * 根据id查找一个临时关联检索
	 * @param id 关联检索id
	 * @param request http请求
	 * @return
	 */
	@RequestMapping(value = "/findRelationTempbyid", method = RequestMethod.GET)
	public RelationTemp findRelationTempbyid(Integer id,
			HttpServletRequest request) {
		SVDS_User user = sessionService.getSessionByIp(request);
		return rdao.findRelationTemp(id, user.getUserId());
	}

	@RequestMapping(value = "/delRelationtempbyid", method = RequestMethod.GET)
	public Integer delRelationtempbyid(Integer id) {
		return rdao.delRelationtempbyid(id);

	}

	/**
	 * 2019年3月17日 曹泽凯
	 * 检查关联检索 
	 * @param filename 文件名称
	 * @param response http响应
	 * @param request http请求
	 * @throws OpenXML4JException
	 * @throws SAXException
	 * @throws Exception
	 */
	@RequestMapping(value = "/verification", method = RequestMethod.POST)
	public void verification(String filename, HttpServletResponse response,
			HttpServletRequest request) throws OpenXML4JException,
			SAXException, Exception {
		System.out.println("filename" + filename);
		Integer fileid = Integer.parseInt(filename);
		Integer rowindex = Integer.parseInt(controller.getXstlcell(fileid));
		List<String[]> rowList = readExecl(
				dao.getFilesById(fileid).getFileUrl()).get(0);
		List<String[]> exportrowList = new ArrayList();
		exportrowList.add(new String[] { "索引名称", "数量", "文件名称" });
		for (int i = 0; i < rowList.size(); i++) {
			if (rowList.get(i).length > rowindex) {

				System.out.println(rowList.get(i)[rowindex]);
				List<SVDS_Files> filelist = controller.getRelationSearch(
						rowList.get(i)[rowindex], fileid);
				String arr[] = new String[3];
				arr[0] = rowList.get(i)[rowindex];
				arr[1] = filelist.size() + "";
				arr[2] = "";
				for (int j = 0; j < filelist.size(); j++) {
					arr[2] += filelist.get(j).getFileName() + "    ";
				}
				exportrowList.add(arr);
			}

		}
		System.out.println("exportrowListsize" + exportrowList.size());
		ExportExcelService.exportExcel(response, exportrowList, "info");
	}

	int next = 1;
	List<List<String[]>> data;

	public List<List<String[]>> readExecl(String fileUrl) throws Exception,
			OpenXML4JException, SAXException {
		// fileUrl="F:\\files\\职责分工.xlsx";
		// System.out.println(fileUrl);
		data = new ArrayList<>();
		next = 1;
		// List<String>
		List<String> columns = new ArrayList<String>();
		JSONObject jsonObject = new JSONObject();
		if (fileUrl.substring(fileUrl.length() - 3).equals("xls")) {
			/*
			 * List<String[]> templist =ExUtils.readExcel(new File(fileUrl)) ;
			 * List<String[]> templist2=new ArrayList<>(); for (int i = 0; i <
			 * templist.size(); i++) {
			 * System.out.println(Arrays.toString(templist.get(i)));
			 * templist2.add(templist.get(i));
			 * if(templist.get(i)[0]==null&&templist.get(i).length==1){
			 * data.add(templist2); templist2=new ArrayList<>(); } }
			 * data.add(templist2); for (int i = 0; i <
			 * data.get(0).get(0).length; i++) {
			 * columns.add(data.get(0).get(0)[i]); }
			 */
			jxl.Workbook readwb = null;

			try {

				// String files = "F:\\sss.xls";
				InputStream instream = new FileInputStream(fileUrl);
				readwb = jxl.Workbook.getWorkbook(instream);
				// 获取Sheet表个数
				int num = readwb.getNumberOfSheets();
				for (int i = 0; i < num; i++) {
					List<String[]> templis = new ArrayList<>();
					System.out.println("第" + (i + 1) + "张表");
					jxl.Sheet readsheet = readwb.getSheet(i);
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
							jxl.Cell cell = readsheet.getCell(n, m);
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
				return data;
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

		return data;
	}

}
