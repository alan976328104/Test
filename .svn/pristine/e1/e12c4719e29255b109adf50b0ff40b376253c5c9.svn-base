package ac.drsi.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
/**
 * 用于Excel读取
 * @author CZK
 *
 */
public class ExcelOperate {
    public static void main(String[] args) {
    	Workbook readwb = null;

    	    try{  

    	            String files = "F:\\sss.xls";
    	            InputStream instream = new FileInputStream(files);   
    	            readwb = Workbook.getWorkbook(instream);   
    	            //获取Sheet表个数
    	            int num = readwb.getNumberOfSheets();
    	            for(int i=0;i<num;i++){
    	                  System.out.println("第"+(i+1)+"张表");
    	                 Sheet readsheet = readwb.getSheet(i);
    	                 String sheetName = readsheet.getName();//获取Sheet名称
    	                 //获取Sheet表中所包含的总列数   
    	                 int rsColumns = readsheet.getColumns();   
    	                 //获取Sheet表中所包含的总行数   
    	                 int rsRows = readsheet.getRows();  
    	                 for(int m= 0; m < rsRows; m++){
    	                    for (int n = 0; n < rsColumns; n++) {
    	                     Cell cell = readsheet.getCell(n, m);
    	                      // String cellinfo = readsheet.getCell(j, i).getContents();
    	                    	  System.out.print(cell.getContents()+" ");
    	                          }
    	                    System.out.println("");
    	                   }
    	             }

    	}catch (Exception e) {   
    	                       e.printStackTrace();    
    	    }finally {           
    	        readwb.close();  
    	    }     


    }
    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List readExcel(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList=new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    List innerList=new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        if(cellinfo.isEmpty()){
                            continue;
                        }
                        innerList.add(cellinfo);
                        //System.out.print(cellinfo);
                    }
                    outerList.add(i, innerList);
                    System.out.println();
                }
                return outerList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}