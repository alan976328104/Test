package ac.drsi.nestor.controller;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.common.DateUtils;
import ac.drsi.common.HttpUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.dao.FolderUpDao;
import ac.drsi.nestor.dao.IntefaceDao;
import ac.drsi.nestor.dao.SVDS_DeptDao;
import ac.drsi.nestor.dao.SVDS_LogDao;
import ac.drsi.nestor.dao.SVDS_LoginDao;
import ac.drsi.nestor.dao.SVDS_UserAllDao;
import ac.drsi.nestor.entity.SVDS_Dept;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.SVDS_UserAll;
import ac.drsi.nestor.entity.Tabs;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_SessionService;

/**
 * 用于提供给外部接口以及对接员工管理系统接口
 * 
 * @author CZK
 * 
 */
@RestController
public class IntefaceController {
	@Autowired
	SVDS_UserAllDao userAllDao;
	@Autowired
	SVDS_DeptDao deptDao;
	@Autowired
	IntefaceDao dao;

	@Autowired
	FolderUpDao dao2;
	@Autowired
	SVDS_LoginDao loginDao;
	@Autowired
	SVDS_SessionService sessionService;
	@Autowired
	SVDS_LogService logService;

	/**
	 * 用于获得员工系统中全部用户
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIntefaceUserget", method = RequestMethod.GET)
	public String getIntefaceUserget() throws Exception {
		return HttpUtils
				.sendHttpGet("http://mis.drsi.com:11001/DRSIMIS2/public/user/getUsers");
	}

	/**
	 * 用于将员工系统中的数据插入本数据库中
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/userInsert", method = RequestMethod.GET)
	public Integer userInsert(HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		List<Integer> successCount = new ArrayList<Integer>();
//		 String userString =
//		 "{\"data\" : [ {\"code\" : \"A124\",\"sex\" :  \"男\",\"mobile\" : null,\"isdeleted\" : \"F\",\"officephone\" : \"85908841\",\"secretlevel\" : \"\",\"depts\" : [{\"dept\":\"501\", \"deptType\":\"1\"},{\"dept\":\"512\",\"deptType\":\"2\"} ],\"status\" :  \"在册在岗\",\"email\" : \"zhangsan@drsi.ac\",\"emailb\" : \"zhangsan@npp.com\",\"name\" : \"李某\",\"cardno\" : \"1853415546\",\"keyno\" : \"npic123,npic123a\",\"id\" :\"078589ee22b9ee1a0122b9eeb06401e1\"},{\"code\" : \"A124\",\"sex\" :  \"男\",\"mobile\" : null,\"isdeleted\" : \"F\",\"officephone\" : \"85908841\",\"secretlevel\" : \"D\",\"depts\" : [{\"dept\":\"501\", \"deptType\":\"1\"},{\"dept\":\"512\",\"deptType\":\"2\"} ],\"status\" :  \"在册在岗\",\"email\" : \"zhangsan@drsi.ac\",\"emailb\" : \"zhangsan@npp.com\",\"name\": \"张某\",\"cardno\" : \"1853415546\",\"keyno\" : \"npic123,npic123a\",\"id\" :\"078589ea22b9ee1a0122b9eeb06401e1\"}]}";
		
		
		
		
		JSONArray arrayDept=deptObjectIsNull();
		if(arrayDept!=null&&arrayDept.size()>0){
			String userString = HttpUtils
					.sendHttpGet("http://mis.drsi.com:11001/DRSIMIS2/public/user/getUsers?start=0&limit=3000");
			
		
		JSONObject objectUser = JSONObject.fromObject(userString);
		Object isNullObject=objectUser.get("data");
		System.out.println(isNullObject);
		if(isNullObject!=null){
			JSONArray arrayUser = JSONArray.fromObject(isNullObject);
			userAllDao.deleteUserAll();
			for (Object object : arrayUser) {
				JSONObject jb = JSONObject.fromObject(object);
					SVDS_UserAll userAll = new SVDS_UserAll();
						userAll.setCardno(jb.get("cardno").toString());
						userAll.setCode(jb.get("code").toString());
						if(jb.get("secretlevel").toString().equals("")){
							userAll.setSecretlevel("Z");
						}else{
							userAll.setSecretlevel(jb.get("secretlevel").toString());
						}
						
						String deptName = "";
						JSONArray ja = JSONArray.fromObject(jb.get("depts"));
						if(ja.size()>0){
							for (Object dept : arrayDept) {
								JSONObject jbDept = JSONObject.fromObject(dept);
								for (Object depts : ja) {
									JSONObject jbDepts = JSONObject.fromObject(depts);
									if (jbDepts.get("dept").toString().equals(jbDept.get("deptcode").toString())) {
										deptName += jbDept.getString("deptname") + ",";
									}
								}
							}
						}
						if(!jb.get("id").toString().equals("")&&deptName!=""&&!jb.get("name").toString().equals("")&&!jb.get("keyno").toString().equals("")){
							userAll.setId(jb.get("id").toString());
							deptName = deptName.substring(0, deptName.length() - 1);
							userAll.setDeptname(deptName);
							userAll.setUsername(jb.get("name").toString());
							userAll.setKeyno(jb.get("keyno").toString());
							Integer isSuccess = userAllDao.insertUserAll(userAll);
							successCount.add(isSuccess);
						}
			}
		}
		}
		System.out.println("添加用户数量："+successCount.size() );
		if (successCount.size() >0) {
			logService.insertLog(new SVDS_Log("执行了同步用户数据库操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return 1;
		} else {
			logService.insertLog(new SVDS_Log("执行了同步用户数据库操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"失败"));
			return 0;
		}
		// System.out.println(userString);
	}

	/**
	 * 查询全部用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listUserAllByDeptName", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray listUserAllByDeptName(String deptname) {
		System.out.println(deptname);
		List<SVDS_UserAll> users = userAllDao.getUserByDeptName(deptname);
		JSONArray data = JSONArray.fromObject(users);
		System.out.println(data.toString());
		return data;
	}
	/**
	 * 同步用户密级
	 * 
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateUserSecurity", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateUserSecurity(HttpServletRequest request) throws UnknownHostException {
		SVDS_User loginUser = sessionService.getSessionByIp(request);
		Integer isSuccess= userAllDao.updateUserSecurity();
		if(isSuccess>0){
			logService.insertLog(new SVDS_Log("执行了同步用户密级操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return 1;
		} else {
			logService.insertLog(new SVDS_Log("执行了同步用户密级操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"失败"));
			return 0;
		}
	}
	/**
	 * 根据用户名称模糊查询全部用户
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "/userAllByName", method = RequestMethod.POST)
	@ResponseBody
	public List<SVDS_UserAll> userAllByName(String username) {
		System.out.println("用户名称："+username);
		if(username!=""){
			List<SVDS_UserAll> userAll = userAllDao.getUserByName(username);
			return userAll;
		}else{
			return null;
		}
		
	}

	/**
	 * 用于插入全部部门到本地数据库中
	 */
	@RequestMapping(value = "/deptInsert", method = RequestMethod.GET)
	public void deptInsert() {
		String deptString = HttpUtils
				.sendHttpGet("http://mis.drsi.com:11001/DRSIMIS2/public/dept/getDepartments");
		// String deptString =
		// "{'dptfullNameData': [ {deleted:false, deptcode:501, deptname:'一室', parentcode:'', type:1}, {deleted:false, deptcode:51401,deptname:'部门2/办公室',parentcode:514,type:1 }, { deleted:false,deptcode:51402,deptname:'部门3/研发部', parentcode:514, type:2}],treeData: [{attributes: {deleted:false,deptcode:501, deptname:'一室',  parentcode:'', type:1 }, children: [{'attributes' :{deleted:false,deptcode:50112,deptname:'一组', parentcode:'',type:1 },children:[], leaf:true,text:'一组' }], leaf:true,text:'一室 '}],success:true,message:''}";
		JSONObject objectDept = JSONObject.fromObject(deptString);
		System.out.println(objectDept);
		JSONArray arrayDept = JSONArray.fromObject(objectDept
				.get("dptfullNameData"));
		for (Object object : arrayDept) {
			JSONObject jb = JSONObject.fromObject(object);
			SVDS_Dept dept = new SVDS_Dept();
			dept.setDeptName(jb.get("deptname").toString());
			dept.setDeptCode(jb.get("deptcode").toString());
			dept.setParentId(jb.get("parentcode").toString());
			deptDao.insertDept(dept);
		}
		// SVDS_Dept dept=new SVDS_Dept();
		// deptDao.insertDept(dept);
		// System.out.println(deptString);
	}
	
	public JSONArray deptObjectIsNull(){
		
//		 String deptString =
//				 "{'dptfullNameData' : [{ deleted:false,deptcode:501, deptname:'一室',parentcode:'',type:1},{ deleted:false,deptcode:512, deptname:'二室',parentcode:'',type:1}],treeData : [ {deleted : false,deptcode : '501',deptname : '一室',parentcode :'' ,type : 1,children : [ {deleted : false,deptcode : '50112',deptname : '一组',parentcode :'' ,type : 1,children : [],leaf : true,text : '一组'} ],leaf : true,text : '一室'} ]}";

		
		String deptString = HttpUtils
				.sendHttpGet("http://mis.drsi.com:11001/DRSIMIS2/public/dept/getDepartments");
		 
		JSONObject objectDept = JSONObject.fromObject(deptString);
		JSONArray arrayDept = null;
		Object object=objectDept.get("dptfullNameData");
		if(object!=null){
			deptDao.deleteDeptAll();
			arrayDept = JSONArray.fromObject(object);
			for (Object arrObject : arrayDept) {
				JSONObject jb = JSONObject.fromObject(arrObject);
				SVDS_Dept dept = new SVDS_Dept();
				dept.setDeptName(jb.get("deptname").toString());
				dept.setDeptCode(jb.get("deptcode").toString());
				dept.setParentId(jb.get("parentcode").toString());
				deptDao.insertDept(dept);
			}
		}
		return arrayDept;
	}

	/**
	 * 获得所有用户并以POST请求方式获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIntefaceUserpost", method = RequestMethod.POST)
	public String getIntefaceUserpost() throws Exception {
		return HttpUtils
				.sendHttpGet("http://mis.drsi.com:11001/DRSIMIS2/public/user/getUsers");
	}

	/**
	 * 获得员工系统中所有部门并以POST请求方式获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIntefaceDeptpost", method = RequestMethod.POST)
	public String getIntefaceDeptpost() throws Exception {
		return HttpUtils
				.sendHttpGet("http://mis.drsi.com:11001/DRSIMIS2/public/dept/getDepartments");
	}

	/**
	 * 获得员工系统中所有部门并以GET请求方式获取
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIntefaceDeptGet", method = RequestMethod.GET)
	public String getIntefaceDeptGet() throws Exception {
		return HttpUtils
				.sendHttpGet("http://mis.drsi.com:11001/DRSIMIS2/public/dept/getDepartments");
	}

	/**
	 * 分页获取用户
	 * 
	 * @param start从哪里开始
	 * @param limit每页多少条
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getIntefaceUsergetbyparm", method = RequestMethod.GET)
	public String getIntefaceUsergetbyparm(String start, String limit)
			throws Exception {

		return HttpUtils
				.sendHttpGet("http://mis.drsi.com:11001/DRSIMIS2/public/user/getUsers?start="+start+"&limit="+limit);
	}

	/**
	 * 分页获取用户POST方式请求
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = " /getIntefaceUserpostbyparm", method = RequestMethod.POST)
	public String getIntefaceUserpostbyparm(String start, String limit)
			throws Exception {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put(start, start);
		maps.put(limit, limit);
		return HttpUtils
				.sendHttpPost("http://mis.drsi.com:11001/DRSIMIS2/public/user/getUsers, maps");
	}

	/**
	 * 通过数据选项卡id获取选项卡下数据
	 * 
	 * @param tabsid选项卡
	 * @return
	 */
	@RequestMapping(value = "/getFilesByTabsidforInteface")
	public List<SVDS_Files> getFilesByTabsid(Integer tabsid, String name,
			String password) {
		if (name == null || password == null) {
			List<SVDS_Files> l = new ArrayList<>();
			SVDS_Files file = new SVDS_Files();
			file.setFileName("请输入登陆用户名密码");
			l.add(file);
			return l;
		}

		SVDS_User user = loginDao.Login(name, password);
		if (user == null) {
			List<SVDS_Files> l = new ArrayList<>();
			SVDS_Files file = new SVDS_Files();
			file.setFileName("用户名或密码错误");
			l.add(file);
			return l;
		}
		List<Integer> roleid = dao.getUserrole(user.getUserId());
		boolean b = true;
		Integer menuid = dao.germenuidfortabs(tabsid);
		for (int i = 0; i < roleid.size(); i++) {
			if (roleid.get(i) == menuid) {
				b = false;
				break;
			}
		}

		if (b) {
			List<SVDS_Files> l = new ArrayList<>();
			SVDS_Files file = new SVDS_Files();
			file.setFileName("该用户没有对应权限");
			l.add(file);
			return l;
		}

		return dao.getFilesByTabsid(tabsid);
	}

	/**
	 * 通过数据文件名用户名以及密码查询数据库中数据
	 * 
	 * @param filename
	 *            文件名
	 * @param name
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = "/getFIlesByFilename")
	public List<SVDS_Files> getFIlesByFilename(String filename, String name,
			String password) {

		return dao.getFIlesByFilename("%" + filename + "%", name, password);
	}

	@RequestMapping(value = "/getIntefaceTabsData")
	public List<Tabs> getIntefaceTabsData(Integer id, String name,
			String password) {
		if (name == null || password == null) {
			List<Tabs> l = new ArrayList<>();
			l.add(new Tabs("请输入用户名密码参数", 0, 0, 0));
			return l;
		}

		SVDS_User user = loginDao.Login(name, password);
		if (user == null) {
			List<Tabs> l = new ArrayList<>();
			l.add(new Tabs("用户名或密码错误", 0, 0, 0));
			return l;
		}
		List<Integer> roleid = dao.getUserrole(user.getUserId());
		boolean b = true;
		for (int i = 0; i < roleid.size(); i++) {
			if (roleid.get(i) == id) {
				b = false;
				break;
			}
		}

		if (b) {
			List<Tabs> l = new ArrayList<>();
			l.add(new Tabs("该用户没有对应权限", 0, 0, 0));
			return l;
		}

		List<Tabs> list = dao2.getTabsByMenuid(id);
		for (int i = 0; i < list.size(); i++) {// 通过5层循环获选项卡列表
			for (int j = 0; j < list.get(i).getChildren().size(); j++) {
				for (int j2 = 0; j2 < list.get(i).getChildren().get(j)
						.getChildren().size(); j2++) {
					for (int k = 0; k < list.get(i).getChildren().get(j)
							.getChildren().get(j2).getChildren().size(); k++) {
						for (int k2 = 0; k2 < list.get(i).getChildren().get(j)
								.getChildren().get(j2).getChildren().get(k)
								.getChildren().size(); k2++) {
							if (list.get(i).getChildren().get(j).getChildren()
									.get(j2).getChildren().get(k).getChildren()
									.get(k2).getName().equals("其他数据")) {
								Tabs t = list.get(i).getChildren().get(j)
										.getChildren().get(j2).getChildren()
										.get(k).getChildren().get(k2);
								list.get(i).getChildren().get(j).getChildren()
										.get(j2).getChildren().get(k)
										.getChildren().remove(k2);
								list.get(i).getChildren().get(j).getChildren()
										.get(j2).getChildren().get(k)
										.getChildren().add(t);
							}

						}

						if (list.get(i).getChildren().get(j).getChildren()
								.get(j2).getChildren().get(k).getName()
								.equals("其他数据")) {
							Tabs t = list.get(i).getChildren().get(j)
									.getChildren().get(j2).getChildren().get(k);
							list.get(i).getChildren().get(j).getChildren()
									.get(j2).getChildren().remove(k);
							list.get(i).getChildren().get(j).getChildren()
									.get(j2).getChildren().add(t);
						}

					}

					if (list.get(i).getChildren().get(j).getChildren().get(j2)
							.getName().equals("其他数据")) {
						Tabs t = list.get(i).getChildren().get(j).getChildren()
								.get(j2);
						list.get(i).getChildren().get(j).getChildren()
								.remove(j2);
						list.get(i).getChildren().get(j).getChildren().add(t);
					}
				}
				if (list.get(i).getChildren().get(j).getName().equals("其他数据")) {
					Tabs t = list.get(i).getChildren().get(j);
					list.get(i).getChildren().remove(j);
					list.get(i).getChildren().add(t);
				}
			}
			if (list.get(i).getName().equals("其他数据")) {
				Tabs t = list.get(i);
				list.remove(i);
				list.add(t);
			}

		}
		return list;
	}

}
