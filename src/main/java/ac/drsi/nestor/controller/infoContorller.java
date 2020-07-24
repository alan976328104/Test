package ac.drsi.nestor.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.nestor.dao.infoDao;
import ac.drsi.nestor.entity.InfoData;
import ac.drsi.nestor.entity.InfoDataBean;
import ac.drsi.common.ExUtils;
/**
 * 用于基本信息
 * @author CZK
 *
 */
@RestController
public class infoContorller {
	@Autowired
	infoDao dao;
	
	/**
	 * 获得基本信息
	 * @param menuid菜单id
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getinfoexcel", method = RequestMethod.GET)
	public List<InfoData> getinfo(Integer menuid) throws Exception{
		//List<InfoData> data=dao.getinfodatabyid(menuid);
//		System.out.println(data.size());
//		
//		list = new ArrayList<>();
//		for (int i = 0; i < data.size(); i++) {
//			list.add(new String[]{data.get(i).getName(),data.get(i).getValue()});
//		}
		return dao.getinfodatabyid(menuid);
	}
	 
	 /**
	  * 用于获取基本信息 不同于getinfo的格式类型
	  * @return
	  */
	 @RequestMapping(value = "/getInfoList")
	 public List<InfoDataBean> getInfoList(){
		 List<InfoDataBean> list = new ArrayList<>();
		 list=dao.getCase();
		 for (int i = 0; i < list.size(); i++) {
			 if(i== list.size()-1){
				 list.get(i).setChildren(dao.getCaseChildren(list.get(i).getId(), Integer.MAX_VALUE));;
			 }else{
				 list.get(i).setChildren(dao.getCaseChildren(list.get(i).getId(), list.get(i+1).getId()));;
			 }
		 }
		 
		 return getInfoListdistinct(list);
	 }
	 /**
	  * 获得基本信息并排序
	  * @param list
	  * @return
	  */
	 public List<InfoDataBean> getInfoListdistinct( List<InfoDataBean> list){
		 
		 List<InfoDataBean> newlist = new ArrayList<>();
		 boolean b=true;
		 Integer index = 0;
		 for (int i = 0; i < list.size(); i++) {
			 
			 b=true;
			for (int j = 0; j <newlist.size(); j++) {
				if(list.get(i).getValue().equals(newlist.get(j).getValue())){
					b=false;
					index = j;
				}
			}
			if(b){
				newlist.add(list.get(i));
			}else{
				boolean boo= true;
				for (int j = 0; j <list.get(i).getChildren().size(); j++) {
					boo= true;
					for (int j2 = 0; j2 < newlist.get(index).getChildren().size(); j2++) {
						if(list.get(i).getChildren().get(j).getName().equals(newlist.get(index).getChildren().get(j2).getName())){
							boo= false;
							//System.out.println(list.get(i).getChildren().get(j).getValue());
						}
					}
					if(boo){
						
						 newlist.get(index).getChildren().add(list.get(i).getChildren().get(j));
						 //System.out.println(1111);
					}
				}
				
				
			}
			 
			
		 }
		 
		 return newlist;
	 }
	 
}
