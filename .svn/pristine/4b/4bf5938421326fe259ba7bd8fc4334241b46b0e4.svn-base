package ac.drsi.nestor.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
/**
 * 用于导出Excle文件
 * @author CZK
 *
 */
@Service
public class ExportExcelService {
/**
 * 输入一个数据集合并导出为Excel
 * @param list
 * @return
 */
	 //String[] excelHeader = { "Sno", "Name", "Age"};  
	    public HSSFWorkbook export(List<String[]> list) {  
	     //   HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFWorkbook wb = new HSSFWorkbook();  
	        HSSFSheet sheet = wb.createSheet("info");  
	        HSSFRow row = sheet.createRow((int) 0);  
	        HSSFCellStyle style = wb.createCellStyle();  
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	  
	        for (int i = 0; i < list.size(); i++) {  
	            HSSFCell cell = row.createCell(i);  
	         //   cell.setCellValue(excelHeader[i]);  
	            cell.setCellStyle(style);  
	            sheet.autoSizeColumn(i);  
	         // sheet.SetColumnWidth(i, 100 * 256);  
	        }  
	  
	        for (int i = 0; i < list.size(); i++) {  
	            row = sheet.createRow(i );  
	            
	            for (int j = 0; j < list.get(i).length; j++) {
	            	 //String[] student = list.get(i);  
	 	            row.createCell(j).setCellValue(list.get(i)[j]);  
	 	            
				}
	            
	        }  
	        return wb;  
	    }  
	
	    
	    /**
	     * 导出一个有文件名称的Excle
	     * @param response
	     * @param rowList 数据集合
	     * @param fileName文件名称
	     */
	    public static void exportExcel(HttpServletResponse response, List<String[]> rowList, String fileName) {
	        OutputStream output = null;
	        try {
	            SXSSFWorkbook workbook = new SXSSFWorkbook();
	            SXSSFSheet sheet = workbook.createSheet();
	            SXSSFRow firstRow = sheet.createRow(0);
	           
	            int rowNum = 1;
	            for(int i = 0; i < rowList.size(); i++){
	                SXSSFRow row = sheet.createRow(rowNum);
	                String[] detailRowList = rowList.get(i);
	                for (int j = 0; j < detailRowList.length; j++) {
	                    row.createCell(j).setCellValue(detailRowList[j]);
	                }
	                rowNum++;
	            }
	            output = response.getOutputStream();
	            response.reset();
	            response.setContentType("application/x-download");
	            response.setCharacterEncoding("utf-8");
	            response.setHeader("Content-Disposition", "attachment;filename="
	                    + new String(fileName.getBytes("gbk"), "iso8859-1")+".xlsx");
	            workbook.write(output);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (output != null) {
	                    output.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
}
