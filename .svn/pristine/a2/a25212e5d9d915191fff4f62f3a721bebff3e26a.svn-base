package ac.drsi.nestor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.nestor.dao.SVDS_LoginDao;
import ac.drsi.nestor.entity.SVDS_User;
@Service
public class SVDS_LoginService {
	@Autowired
	SVDS_LoginDao dao;
	/*
	 * 登录
	 */
	public SVDS_User Login(String username, String password) {
		return dao.Login(username, password);
	}
	
	public SVDS_User keyLogin(String card){
		
		return dao.keyLogin(card);
	}
}
