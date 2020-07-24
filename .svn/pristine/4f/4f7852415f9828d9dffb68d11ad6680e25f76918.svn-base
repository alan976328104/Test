package ac.drsi.nestor.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.dao.SVDS_SessionDao;
import ac.drsi.nestor.dao.SVDS_UserDao;
import ac.drsi.nestor.entity.SVDS_Session;
import ac.drsi.nestor.entity.SVDS_User;

@Service
public class SVDS_SessionService {
	@Autowired
	SVDS_SessionDao dao;
	@Autowired
	SVDS_UserDao userDao;
	
	/**
	 * 根据IP地址获取登录者信息
	 * @param ip
	 * @return
	 * @throws Exception 
	 */
	public SVDS_User getSessionByIp(HttpServletRequest request){
		 String ip;
		try {
			ip = IpAddressUtils.getIpAddress(request);
			System.out.println("IP地址："+ip);
			if(ip!=null){
				SVDS_Session session=dao.getSession(ip, "userId");
				if(session.getKey().equals("userId")){
					String userId=session.getValue();
					SVDS_User user=userDao.getUserById(Integer.parseInt(userId));
					return user;
				}else{
					return null;
				}
			}else{
				return null;
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 插入登录信息
	 * @param ip
	 * @param userId
	 * @param value
	 * @return
	 */
	public Integer inserSession( String ip, String key, String value){
		dao.deleteSession(ip);
		SVDS_Session sessionNew =new SVDS_Session();
		sessionNew.setIp(ip);
		sessionNew.setKey(key);
		sessionNew.setValue(value);
		return dao.inserSession(sessionNew);
	}
	/**
	 * 根据IP地址修改登录信息
	 * @param ip
	 * @param userId
	 * @param value
	 * @return
	 */
//	public Integer updateSession(String ip, Integer userId,String value){
//		return dao.updateSession(session);
//	}
	/**
	 * 根据键和值获得session
	 * @param request
	 * @param key
	 * @return
	 */
	
	public SVDS_Session getSession(HttpServletRequest request,String key){
		String ip;
		try {
			ip = IpAddressUtils.getIpAddress(request);
			return dao.getSession(ip, key);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return null;
		}
	
	}
	
	/**
	 * 删除验证矩阵原有的session并添加新的session
	 * @param ip
	 * @param key
	 * @param value
	 * @return
	 */
	public Integer inserSessionByPh( String ip, String key, String value){
		//dao.deleteSession(ip);
		dao.deleteSessionBykey(ip, key);
		SVDS_Session sessionNew =new SVDS_Session();
		sessionNew.setIp(ip);
		sessionNew.setKey(key);
		sessionNew.setValue(value);
		return dao.inserSession(sessionNew);
	}
	/**
	 * 根据Ip删除session
	 * @param ip
	 * @return
	 */
	public Integer deleteSessionBykey( String ip){
		return dao.deleteSessionBykey(ip, "phid");
	}
}
