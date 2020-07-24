package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.dao.PhenomenonDao;
import ac.drsi.nestor.entity.Phenomenon;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.ExportExcelService;
import ac.drsi.nestor.service.SVDS_SessionService;
/**
 * 用于验证矩阵操作
 * @author CZK
 *
 */
@RestController
public class PhenomenonController {
	@Autowired
	PhenomenonDao dao;
	
	
	@Autowired
	SVDS_SessionService service;
	/**
	 * 用户获得验证矩阵
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getPhenomenonmenuid", method = RequestMethod.GET)
	public Integer getPhenomenonmenuid(HttpServletRequest request){
		Integer id = 0;
		try {
			id = Integer.parseInt( service.getSession(request, "phid").getValue());
		} catch (Exception e) {
			return 0;
		}
		return id;
	}
	/**
	 * 用于删除验证矩阵
	 * @param request
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/delPhenomenonmenuid", method = RequestMethod.GET)
	public Integer delPhenomenonmenuid(HttpServletRequest request) throws UnknownHostException{
		String ip =  IpAddressUtils.getIpAddress(request);
		return service.deleteSessionBykey(ip);
	}
	/**
	 * 将验证矩阵存入到Sesion中
	 * @param menuid
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/inssessionPhenomenonmenuid", method = RequestMethod.GET)
	public Integer inssessionPhenomenonmenuid(String menuid,HttpServletRequest request){
		//Integer id = 0;
		String ip;
		try {
			ip = IpAddressUtils.getIpAddress(request);
			System.out.println(menuid);
			//int i=;
			return service.inserSessionByPh(ip, "phid", menuid);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	/**
	 * 获得所有的验证矩阵菜单
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findAllPhenomenonmenu", method = RequestMethod.GET)
	public List<Phenomenon> findAllPhenomenonmenu(HttpServletRequest request){
		SVDS_User loginUser = service.getSessionByIp(request);
		return dao.findAllPhenomenonmenu(loginUser.getUserId());
	}
	
	/**
	 * 用于查询验证矩阵中是否包含实验现象
	 * @return
	 */
	@RequestMapping(value = "/findAllPhenomenon", method = RequestMethod.GET)
	public List<Phenomenon> findAllPhenomenon(){
	List<String> list=	dao.findAllPhenomenon();
	List<Phenomenon> data = new ArrayList();
	for (int i = 0; i <list.size(); i++) {
		data.add(new Phenomenon(list.get(i)));
	}
	
		return data;
	}
	
	/**
	 * 
	 * @param xiangmu 项目列表
	 * @param xianxiang 现象列表
	 * @param isxy  0为项目在Y轴 
	 * @return
	 */
	@RequestMapping(value = "/Phenomenon", method = RequestMethod.POST)
	public List<String[]> Phenomenon(String []xiangmu,String[] xianxiang,Integer isxy){
		//[堆外, CSV, 堆内, PKL项目]
		//[现象1子实验, 现象4子实验, 现象2子实验, 现象3子实验, 现象5子实验]
				//0
		
		//判断X轴为项目还是现象
		if(isxy==0){
			
		//创建一个新的数据集合用于返回结果 
		List<String[]> list= new ArrayList<>();
		//创建一个集合用于存数试验现象返回结果的第一行
		String str[] = new String[xianxiang.length+1];
		str[0]="";
		for (int i = 0; i < xianxiang.length; i++) {
			str[i+1]=xianxiang[i];
		}
		
		//将第一行添加到最终的返回结果数组中
		list.add(str);
		
		//开始循环项目
		for (int i = 0; i < xiangmu.length; i++) {
			//创建每一行返回的结果数据
			String arr[] = new String[xianxiang.length+1];
			//通过项目名称获得项目id
			int xid = dao.findmenuByidforname(xiangmu[i]);
			//将当前循环的项目如返回结果当前一行第一列的位置
			arr[0] = xiangmu[i]+"<span style='display: none;'>"+xid+"</span>";
			for (int j = 0; j < xianxiang.length; j++) {
				//判断当前项目是否存在当前现象
				if(ishave(xiangmu[i], xianxiang[j])>0){
					 arr[j+1]="<span style='color:green;font-size: 24px'>○</span>"+"<span style='display: none;'>"+xid+"</span>";
				}else{
					 arr[j+1]="<span style='color:green;font-size: 24px'>×</span>"+"<span style='display: none;'>"+xid+"</span>";
				}
				
			}
			list.add(arr);
		}
		return list;
		}else{
			
			
			
			List<String[]> list= new ArrayList<>();
			String str[] = new String[xiangmu.length+1];
			str[0]="";
			for (int i = 0; i < xiangmu.length; i++) {
				int xid = dao.findmenuByidforname(xiangmu[i]);
				str[i+1]=xiangmu[i]+"<span style='display: none;'>"+xid+"</span>";
			}
			list.add(str);
			for (int i = 0; i < xianxiang.length; i++) {
				String arr[] = new String[xiangmu.length+1];
				arr[0] = xianxiang[i];
				for (int j = 0; j < xiangmu.length; j++) {
					int xid = dao.findmenuByidforname(xiangmu[j]);
					if(ishave( xiangmu[j],xianxiang[i])>0){
						 arr[j+1]="<span style='color:green;font-size: 24px'>○</span>"+"<span style='display: none;'>"+xid+"</span>";
					}else{
						 arr[j+1]="<span style='color:green;font-size: 24px'>×</span>"+"<span style='display: none;'>"+xid+"</span>";
					}
				}
				list.add(arr);
			}
			
			return list;
			
			
			
			
			
			
		}
	}
	
	//去数据库查询该项目是否存在该现象 存在返回1 不存在返回0
	public Integer ishave(String xiangmu,String xianxiang){
		return dao.ishave(dao.findmenuByidforname(xiangmu), xianxiang);
	}
	
	@RequestMapping(value = "/getPhvalue", method = RequestMethod.POST)
	public String getPhvalue(Integer menuid){
		System.out.println(menuid);
		
		return dao.getphvalue(menuid);
	}
	@RequestMapping(value = "/saveph", method = RequestMethod.POST)
	public Integer saveph(Integer menuid,String value){
		dao.delph(menuid);
		return dao.addphenomenon(menuid, value);
	}
	
	
	//导出验证矩阵
	@RequestMapping(value = "/ExprotPhenomenonController", method = RequestMethod.POST)
	public void ExprotPhenomenonController(HttpServletResponse response,String data){
		List<String[]> rowList =new ArrayList<>();
		JSONArray json = JSONArray.fromObject(data);
		for (int i = 0; i < json.size(); i++) {
			json.getJSONArray(i);
			rowList.add(new String[json.getJSONArray(i).size()]);
			for (int j = 0; j < json.getJSONArray(i).size(); j++) {
				rowList.get(i)[j] = json.getJSONArray(i).getString(j);
			}
		}
		ExportExcelService.exportExcel(response,rowList, "info");
	}
}
