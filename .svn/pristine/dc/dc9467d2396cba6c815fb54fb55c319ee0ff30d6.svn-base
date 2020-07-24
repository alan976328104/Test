package ac.drsi.nestor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.common.SolrUtils;
import ac.drsi.nestor.dao.MenuDao;
import ac.drsi.nestor.dao.SolrConfileDao;
/**
 * 用于全文检索的处理
 * @author CZK
 *
 */
@RestController
public class SolrConfigController {
	@Autowired
	SolrConfileDao dao;
	@Autowired
	MenuDao menu;
	/**
	 * 获得所有文件id
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/Solrceshishuju", method = RequestMethod.GET)
		public List<String> ceshishuju(Integer id){
			return menu.getFileidBymenuid(id);
		}
	
	/**
	 * 同步索引与数据库文件
	 * @return
	 */
	@RequestMapping(value = "/SolrConfigsynch", method = RequestMethod.GET)
	 public Integer SolrConfigsynch(){
		 
		
		
		 return synch(SolrUtils.queryAllid(),dao.getAllfileid());
	 }
	/**
	 * 获得索引全部id
	 * @return
	 */
	@RequestMapping(value = "/SolrgetAllid", method = RequestMethod.GET)
	 public List<Integer> SolrgetAllid(){
		 
		
		
		 return SolrUtils.queryAllid();
	 }
	/**
	 * 同步数据库如索引
	 * @param solrids 索引全部文件id
	 * @param fileids 数据库文件id
	 * @return
	 */
	public Integer  synch(List<Integer> solrids,List<Integer> fileids){
		try {
			boolean b= true;
			for (int i = 0; i <solrids.size(); i++) {
				b =true;
				for (int j = 0; j < fileids.size(); j++) {
					if(solrids.get(i)==fileids.get(j)){
						b=false;
						break;
					}
				}
				
				if(b){
					SolrUtils.removeById(solrids.get(i).toString());
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}
}
