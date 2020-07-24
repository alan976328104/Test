package ac.drsi.nestor.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ac.drsi.nestor.dao.ExprotExcleDao;
import ac.drsi.nestor.entity.SVDS_InfoData;
import ac.drsi.nestor.service.ExportExcelService;

/**
 * 导出EXCEL
 * @author CZK
 *
 */

@Controller
public class ExportExcelController {
	
	  
	    @Autowired  
	    private ExportExcelService studentExportService;  
	    @Autowired
	    ExprotExcleDao dao;
	    /**
	     * 
	     * @param menuid菜单id	 
	     * @param request http请求
	     * @param response http响应
	     * @throws Exception 异常处理
	     */
	    @RequestMapping(value = "/exportExcel")  
	    public void exportExcel(Integer menuid,HttpServletRequest request, HttpServletResponse response)   
	    throws Exception {  
	        HSSFWorkbook wb = studentExportService.export(getarr(menuid));  
	        response.setContentType("application/vnd.ms-excel");  
	        response.setHeader("Content-disposition", "attachment;filename=info.xls");  
	        OutputStream ouputStream = response.getOutputStream();  
	        wb.write(ouputStream);  
	        ouputStream.flush();  
	        ouputStream.close();  
	   }  
	    /**
	     * 获取基本基本页面的表格
	     * @param str  基本信息id
	     * @return 
	     */
	    public List<String[]> getarr(Integer str){
	    	List<String[]> list=new ArrayList<>();
	    	
	    	List<SVDS_InfoData> infos =  dao.exprotExcelBymenuid(str);
	    	for (int i = 0; i <infos.size(); i++) {
				list.add(new String[]{infos.get(i).getMark(),infos.get(i).getName(),infos.get(i).getValue()});
			}
			
	    	return list;
	    }
	    
	   
}
