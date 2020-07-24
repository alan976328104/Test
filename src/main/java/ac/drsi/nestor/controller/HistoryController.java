package ac.drsi.nestor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.nestor.dao.HistoryDao;
import ac.drsi.nestor.entity.History;
/**
 * 用于首页推送信息
 * @author CZK
 *
 */
@RestController
public class HistoryController {
		@Autowired
		HistoryDao dao;
		@RequestMapping(value = "/getHistory", method = RequestMethod.POST)
		public List<History> getHistory(){
			List<History> list =  dao.getHistoryDao();
			
			return dao.getHistoryDao();
		}
	
}
