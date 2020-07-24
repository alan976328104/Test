package ac.drsi.nestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.nestor.dao.statisticsDao;
import ac.drsi.nestor.entity.ChartEntity;
import ac.drsi.nestor.entity.SVDS_Menu;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SVDS_MenuService;

@RestController
public class statisticsController {
	@Autowired
	statisticsDao dao;
	@Autowired
	SVDS_MenuService menuService;
	@Autowired
	SVDS_FilesService filesService;
	
	@RequestMapping(value = "/GetrelationNum")
	public Integer  GetrelationNum(){
		
		return dao.GetrelationNum();
	}
	
	@RequestMapping(value = "/Getyanzhengjuzhen")
	public Integer  Getyanzhengjuzhen(){
		return dao.Getyanzhengjuzhen();
	}
	
	@RequestMapping(value = "/GetmajorRelationNum")
	public Integer  GetmajorRelationNum(String menuId){
		List<String> list=dao.Getrelationproject();
		int num = 0;
		for (int i = 0; i <list.size(); i++) {
			if(ishavemenu(menuId,list.get(i))){
				num++;
			}
		}
		return num;
	}
	
	@RequestMapping(value = "/GetmajorprojectRelationNum")
	public Integer  GetmajorprojectRelationNum(String menuId){
		List<String> list=dao.Getrelationproject();
		int num = 0;
		for (int i = 0; i <list.size(); i++) {
			num+=ishaveparojectmenu(menuId,list.get(i));
		}
		return num;
	}
	
	
	
	
	public Integer  ishaveparojectmenu(String menuId,String project){
		String [] projectid = project.split(",");
		int num=0;
		for (int i = 0; i < projectid.length; i++) {
			Integer lv=dao.getMenulv(Integer.parseInt(projectid[i]));
			if(lv==2){
				Integer parentid =dao.getparentid(Integer.parseInt(projectid[i]));
				if(parentid==Integer.parseInt(menuId)){
					num++;
				}else{
					Integer pid =dao.getparentid(Integer.parseInt(projectid[i]));
					for (int j = 0; j < lv-2; j++) {
						pid=dao.getparentid(pid);
					}
					if(pid==Integer.parseInt(menuId)){
						num++;
					}
					
				}
				
			}
			
		}
		
		
		
		return num;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean ishavemenu(String menuId,String project){
		String [] projectid = project.split(",");
		
		
		for (int i = 0; i < projectid.length; i++) {
			Integer lv=dao.getMenulv(Integer.parseInt(projectid[i]));
			if(lv==2){
				Integer parentid =dao.getparentid(Integer.parseInt(projectid[i]));
				if(parentid==Integer.parseInt(menuId)){
					return true;
				}else{
					Integer pid =dao.getparentid(Integer.parseInt(projectid[i]));
					for (int j = 0; j < lv-2; j++) {
						pid=dao.getparentid(pid);
					}
					if(pid==Integer.parseInt(menuId)){
						return true;
					}
					
				}
				
			}
		
		}
		
		return false;
		
	}

	/**
	 * 查询某一年的数据上传量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listMonth", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listMonth(@RequestParam String yearValue) {
		System.out.println("****年份"+yearValue);
		return filesService.listMonth(yearValue);
	}
	/**
	 * 查询某专业一年的数据上传量
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/listMonthByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listMonthByMenuId(@RequestParam Integer menuId,@RequestParam String yearValue) {
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			return filesService.listMonthByMenuId(menuIds,yearValue);
		}else{
			return null;
		}
	}

	/**
	 * 查询各类文档占比图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listformat", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listformat() {
		return filesService.listformat();
	}
	/**
	 * 查询某专业下各类文档占比图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listformatByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listformatByMenuId(@RequestParam Integer menuId) {
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			return filesService.listformatByMenuId(menuIds);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询文件密级进行文档占比图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listSecurity", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listSecurity() {
		return filesService.listSecurity();
	}
	
	/**
	 * 查询某专业下文件密级进行文档占比图
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listSecurityByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listSecurityByMenuId(@RequestParam Integer menuId) {
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			return filesService.listSecurityByMenuId(menuIds);
		}else{
			return null;
		}
	}
	
	/**
	 * 查询所有文件总数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getFilesAllCount", method = RequestMethod.POST)
	@ResponseBody
	public Integer getFilesAllCount() {
		return filesService.getFilesAllCount();
	}

	/**
	 * 系统标签数目最多的例题
	 * 
	 * @return
	 */
	@RequestMapping(value = "/aliasManyByFile", method = RequestMethod.POST)
	@ResponseBody
	public SVDS_Menu aliasManyByFile() {
		return filesService.aliasManyByFile();
	}

	/**
	 * TOP5最常访问文件的点击次数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listVisited", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listVisited() {
		return filesService.listVisited();
	}
	/**
	 * 某专业下TOP5最常访问文件的点击次数
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/listVisitedByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listVisitedByMenuId(@RequestParam Integer menuId) {
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			return filesService.listVisitedByMenuId(menuIds);
		}else{
			return null;
		}
	}
	/**
	 * 某专业下的文档总数
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value = "/fileCountByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public Integer fileCountByMenuId(@RequestParam Integer menuId){
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			return filesService.fileCountByMenuId(menuIds);
		}else{
			return null;
		}
	}
	
	/**
	 * 某专业下各例题文档数占比
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/listfileCountByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listfileCountByMenuId(@RequestParam Integer menuId) {
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			return filesService.listfileCountByMenuId(menuIds);
		}else{
			return null;
		}
	}
	/**
	 * 某专业下各例题数据量占比
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/listfileSizeByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listfileSizeByMenuId(@RequestParam Integer menuId) {
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			List<ChartEntity> entity=filesService.listfileSizeByMenuId(menuIds);
			return entity;
		}else{
			return null;
		}
	}
	/**
	 * 系统年新装载数据量统计
	 * @return
	 */
	@RequestMapping(value = "/listFileCountAll", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listFileCountAll(){
		return filesService.listFileCountAll();
	}
	/**
	 * 某专业的年新装载数据量统计
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/listFileCountAllByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public List<ChartEntity> listFileCountAllByMenuId( @RequestParam Integer menuId){
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			List<ChartEntity> entity=filesService.listFileCountAllByMenuId(menuIds);
			return entity;
		}else{
			return null;
		}
	}
	/**
	 * 某专业的系统标签总数
	 * @param menuIds
	 * @return
	 */
	@RequestMapping(value = "/sysAliasCountByMenuId", method = RequestMethod.POST)
	@ResponseBody
	public Integer sysAliasCountByMenuId(  @RequestParam Integer menuId){
		List<SVDS_Menu> menus=menuService.listMenuByParentId(menuId);
		List<Integer> menuIds=new ArrayList<Integer>();
		if(menus!=null){
			for (SVDS_Menu menu : menus) {
				menuIds.add(menu.getId());
			}
			return filesService.sysAliasCountByMenuId(menuIds);
		}else{
			return null;
		}
	}
	
}
