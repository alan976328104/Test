package ac.drsi.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Excel超大数据读取，抽象Excel2007读取器，excel2007的底层数据结构是xml文件，采用SAX的事件驱动的方法解析
 * xml，需要继承DefaultHandler，在遇到文件内容时，事件会触发，这种做法可以大大降低 内存的耗费，特别使用于大数据量的文件。
 * 
 * @version 2014-9-2
 */
public abstract class ExUtils extends DefaultHandler {

	// 共享字符串表
	private SharedStringsTable sst;

	// 上一次的内容
	private String lastContents;
	private boolean nextIsString;

	private int sheetIndex = -1;
	private List<String> rowList = new ArrayList<String>();

	// 当前行
	private int curRow = 0;
	// 当前列
	private int curCol = 0;
	// 日期标志
	private boolean dateFlag;
	// 数字标志
	private boolean numberFlag;

	private boolean isTElement;

	/**
	 * 遍历工作簿中所有的电子表格
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public void process(String filename) throws Exception {
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> sheets = r.getSheetsData();
		while (sheets.hasNext()) {
			curRow = 0;
			sheetIndex++;
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
		}
	}

	/**
	 * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
	 * 
	 * @param filename
	 * @param sheetId
	 * @throws Exception
	 */
	public void process(String filename, int sheetId) throws Exception {
		OPCPackage pkg;
		try {
			pkg = OPCPackage.open(filename);
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();
			XMLReader parser = fetchSheetParser(sst);
			// 根据 rId# 或 rSheet# 查找sheet
			InputStream sheet2 = r.getSheet("rId" + sheetId);
			sheetIndex++;
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();
		} catch (Exception e) {

		}

	}

	public XMLReader fetchSheetParser(SharedStringsTable sst)
			throws SAXException {
		XMLReader parser = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		this.sst = sst;
		parser.setContentHandler(this);
		return parser;
	}

	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {

		// System.out.println("startElement: " + localName + ", " + name + ", "
		// + attributes);

		// c => 单元格
		if ("c".equals(name)) {
			// 如果下一个元素是 SST 的索引，则将nextIsString标记为true
			String cellType = attributes.getValue("t");
			if ("s".equals(cellType)) {
				nextIsString = true;
			} else {
				nextIsString = false;
			}
			// 日期格式
			String cellDateType = attributes.getValue("s");
			if ("1".equals(cellDateType)) {
				dateFlag = true;
			} else {
				dateFlag = false;
			}
			String cellNumberType = attributes.getValue("s");
			if ("2".equals(cellNumberType)) {
				numberFlag = true;
			} else {
				numberFlag = false;
			}

		}
		// 当元素为t时
		if ("t".equals(name)) {
			isTElement = true;
		} else {
			isTElement = false;
		}

		// 置空
		lastContents = "";
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {

		// System.out.println("endElement: " + localName + ", " + name);

		// 根据SST的索引值的到单元格的真正要存储的字符串
		// 这时characters()方法可能会被调用多次
		if (nextIsString) {
			try {
				int idx = Integer.parseInt(lastContents);
				lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
						.toString();
			} catch (Exception e) {

			}
		}
		// t元素也包含字符串
		if (isTElement) {
			String value = lastContents.trim();
			rowList.add(curCol, value);
			curCol++;
			isTElement = false;
			// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
			// 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
		} else if ("v".equals(name)) {
			String value = lastContents.trim();
			value = value.equals("") ? " " : value;
			try {
				// 日期格式处理
				if (dateFlag) {
					Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"dd/MM/yyyy");
					value = dateFormat.format(date);
				}
				// 数字类型处理
				if (numberFlag) {
					BigDecimal bd = new BigDecimal(value);
					value = bd.setScale(3, BigDecimal.ROUND_UP).toString();
				}
			} catch (Exception e) {
				// 转换失败仍用读出来的值
			}
			rowList.add(curCol, value);
			curCol++;
		} else {
			// 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
			if (name.equals("row")) {
				getRows(sheetIndex + 1, curRow, rowList);
				rowList.clear();
				curRow++;
				curCol = 0;
			}
		}

	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// 得到单元格内容的值
		lastContents += new String(ch, start, length);
	}

	/**
	 * 获取行数据回调
	 * 
	 * @param sheetIndex
	 * @param curRow
	 * @param rowList
	 */
	public abstract void getRows(int sheetIndex, int curRow,
			List<String> rowList);

	private static Logger logger  = Logger.getLogger(POIUtil.class);
	private final static String xls = "xls";
	private final static String xlsx = "xlsx";
	
	/**
	 * 读入excel文件，解析后返回
	 * @param file
	 * @throws IOException 
	 */
	public static List<String[]> readExcel(File file) throws IOException{
		//检查文件
		checkFile(file);
    	//获得Workbook工作薄对象
    	Workbook workbook = getWorkBook(file);
    	//创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
    	List<String[]> list = new ArrayList<String[]>();
    	if(workbook != null){
    		for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
    			//获得当前sheet工作表
        		Sheet sheet = workbook.getSheetAt(sheetNum);
        		if(sheet == null){
        			continue;
        		}
        		//获得当前sheet的开始行
        		int firstRowNum  = sheet.getFirstRowNum();
        		//获得当前sheet的结束行
        		int lastRowNum = sheet.getLastRowNum();
        		//循环除了第一行的所有行
        		for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++){
        			//获得当前行
        			Row row = sheet.getRow(rowNum);
        			if(row == null){
        				continue;
        			}
        			//获得当前行的开始列
        			int firstCellNum = row.getFirstCellNum();
        			//获得当前行的列数
        			int lastCellNum = row.getPhysicalNumberOfCells();
        			String[] cells = new String[row.getPhysicalNumberOfCells()];
        			//循环当前行
        			for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
        				Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
        			}
        			list.add(cells);
        		}
    		}
    		workbook.close();
    	}
		return list;
    }
	public static void checkFile(File file) throws IOException{
		//判断文件是否存在
    	if(null == file){
    		logger.error("文件不存在！");
    		throw new FileNotFoundException("文件不存在！");
    	}
		//获得文件名
    	String fileName = file.getName();
    	//判断文件是否是excel文件
    	if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
    		logger.error(fileName + "不是excel文件");
    		throw new IOException(fileName + "不是excel文件");
    	}
	}
	public static Workbook getWorkBook(File file) {
		//获得文件名
    	String fileName = file.getName();
    	//创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			//获取excel文件的io流
			FileInputStream is = new FileInputStream(file); 
			//根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if(fileName.endsWith(xls)){
				//2003
				workbook = new HSSFWorkbook(is);
			}else if(fileName.endsWith(xlsx)){
				//2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return workbook;
	}
	public static String getCellValue(Cell cell){
		String cellValue = "";
		if(cell == null){
			return cellValue;
		}
		//把数字当成String来读，避免出现1读成1.0的情况
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}
		//判断数据的类型
        switch (cell.getCellType()){
	        case Cell.CELL_TYPE_NUMERIC: //数字
	            cellValue = String.valueOf(cell.getNumericCellValue());
	            break;
	        case Cell.CELL_TYPE_STRING: //字符串
	            cellValue = String.valueOf(cell.getStringCellValue());
	            break;
	        case Cell.CELL_TYPE_BOOLEAN: //Boolean
	            cellValue = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_FORMULA: //公式
	            cellValue = String.valueOf(cell.getCellFormula());
	            break;
	        case Cell.CELL_TYPE_BLANK: //空值 
	            cellValue = "";
	            break;
	        case Cell.CELL_TYPE_ERROR: //故障
	            cellValue = "非法字符";
	            break;
	        default:
	            cellValue = "未知类型";
	            break;
        }
		return cellValue;
	}

	/**
	 * 测试方法
	 */
	
	int next=1;
	public static void main(String[] args) throws Exception {
		List<String[]> list=	ExUtils.readExcel(new File("F:/info.xls"));
		for (int i = 0; i < list.size(); i++) {
			System.out.println(Arrays.toString(list.get(i)));
		}
		
		
	}
}